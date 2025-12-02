package part1;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class Student {
    private String name;
    private Integer age;
    private Double grade;

    public Student(String name, Integer age, Double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Double getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", grade=" + grade +
                '}';
    }
}

class Test {
    public static <T> void printElements(List<T> elems, Predicate<T> predicate)
    {
        for (T elem: elems)
            if (predicate.test(elem))
                System.out.println(elem);
    }

    public static void main(String[] args) {
        Predicate<Student> passingStudentsPr = st -> st.getGrade() >= 5;
        Predicate<Student> olderThan21Pr = st -> st.getAge() >= 21;
        Predicate<Student> nameContainsA = st -> st.getName().toLowerCase().contains("a");

        Student s1 = new Student("Anna", 22, 9.0);
        Student s2 = new Student("John", 20, 4.0);
        Student s3 = new Student("Andrew", 19, 7.5);
        List<Student> studentsList = Arrays.asList(s1, s2, s3);

        System.out.println("Students who have passed: ");
        printElements(studentsList, passingStudentsPr);
        System.out.println("Students older than 21: ");
        printElements(studentsList, olderThan21Pr);
        System.out.println("Students whose name contains 'a': ");
        printElements(studentsList, nameContainsA);
    }
}