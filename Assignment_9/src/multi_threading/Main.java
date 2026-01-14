package multi_threading;

import domain.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class PatientWorker extends Thread {
    private int startIdx, endIdx;
    private List<Patient> patients;

    public PatientWorker(int startIdx, int endIdx, List<Patient> patients) {
        this.startIdx = startIdx;
        this.endIdx = endIdx;
        this.patients = patients;
    }

    @Override
    public void run() {
        for (int i = this.startIdx; i < this.endIdx; i++) {
            Patient p = patients.get(i);
            if (p.getAge() > 60) {
                p.setHealthRisk("high risk");
            }
        }
    }
}
public class Main {
    public static List<Patient> generatePatients(int count) {
        List<Patient> patients = new ArrayList<>(count);
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int age = rand.nextInt(90) + 1;
            patients.add(new Patient(i, "Name" + i, "email" + i, "000", age, "lower risk"));
        }
        return patients;
    }

    public static void runWithThreads(List<Patient> patients, int noThreads) {
        int n = patients.size();
        int intervalLength = (int)(n / noThreads);
        int remainder = n % noThreads;

        PatientWorker[] threads = new PatientWorker[noThreads];
        int start = 0;

        for (int i = 0; i < noThreads; i++) {
            int end = start + intervalLength;
            if (i == noThreads - 1)
                end = end + remainder;

            PatientWorker t = new PatientWorker(start, end, patients);
            t.start();
            threads[i] = t;
            start = end;
        }

        for (int i = 0; i < noThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void runWithExecutor(List<Patient> patients, int noThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(noThreads);
        int n = patients.size();
        int intervalLength = n / noThreads;
        int remainder = n % noThreads;

        int start = 0;
        for (int i = 0; i < noThreads; i++) {
            int end = start + intervalLength;
            if (i == noThreads - 1)
                end = end + remainder;

            final int startIdx = start;
            final int endIdx = end;

            executor.submit(() -> {
                for (int k = startIdx; k < endIdx; k++) {
                    Patient p = patients.get(k);
                    if (p.getAge() > 60) {
                        p.setHealthRisk("high risk");
                    }
                }
            });
            start = end;
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    public static void main(String[] args) {
        int n = 100_000;
        int noThreads = 10;

        List<Patient> dataSet1 = generatePatients(n);
        System.out.println("Generated " + n + " patients for Thread test.");

        long start1 = System.currentTimeMillis();
        runWithThreads(dataSet1, noThreads);
        long end1 = System.currentTimeMillis();

        System.out.println("Traditional Threads Time: " + (end1 - start1) + " ms");

        List<Patient> dataSet2 = generatePatients(n);
        System.out.println("Generated " + n + " patients for Executor test.");

        long start2 = System.currentTimeMillis();
        runWithExecutor(dataSet2, noThreads);
        long end2 = System.currentTimeMillis();

        System.out.println("ExecutorService Time: " + (end2 - start2) + " ms");
    }
}
