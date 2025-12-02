package part1;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@FunctionalInterface
interface Processor<T, E> {
    T process(E element);
}

interface StringProcessor<T> extends Processor<T, String> {
}

interface NumberProcessor<T> extends Processor<T, Double> {
}

class Main {
    public static <T, E> void printProcessed(List<E> list, Processor<T, E> processor) {
        for (E elem : list) {
            System.out.println(processor.process(elem));
        }
    }

    public static void main(String[] args) {
        StringProcessor<String> UpperCaseProcessor = s -> s.toUpperCase();
        // System.out.println(UpperCaseProcessor.process("hello"));
        // System.out.println(UpperCaseProcessor.process("goodbye"));

        List<String> stringList = Arrays.asList("Hello", "Good Bye");
        printProcessed(stringList, UpperCaseProcessor);

        StringProcessor<Boolean> ContainsSubstringProcessor = s -> s.contains("he");
        System.out.println(ContainsSubstringProcessor.process("hello"));
        System.out.println(ContainsSubstringProcessor.process("goodbye"));

        StringProcessor<Integer> CounterProcessor = c -> {
            String[] list = c.split(" ");
            return list.length;
        };
        System.out.println(CounterProcessor.process("Hello everyone"));
        System.out.println(CounterProcessor.process(""));

        NumberProcessor<Double> numberProcessor = num -> Math.sqrt(num);
//        System.out.println(numberProcessor.process(16.0));
//        System.out.println(numberProcessor.process(15.0));

        List<Double> numbersList = Arrays.asList(16.0, 15.0);
        printProcessed(numbersList, numberProcessor);

        NumberProcessor<Boolean> primeProcessor = num -> {
            if (num < 2)
                return false;
            if (num % 2 == 0 && num != 2)
                return false;
            for (int i = 3; i * i <= num; i += 2) {
                if (num % i == 0) {
                    return false;
                }
            }
            return true;
        };
        System.out.println(primeProcessor.process(17.0));
        System.out.println(primeProcessor.process(1.0));

        NumberProcessor<String> currencyProcessor = num -> "$" + num.toString();
        System.out.println(currencyProcessor.process(1.0));

    }
}