//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Checker checker1 = new FirstChecker();
        Checker checker2 = new SecondChecker();
        Section section1 = new Section("A1", "Mathematics.Java and IT", checker1);
        Section section2 = new Section("A1 Section", "Mathematics", checker2);
        System.out.println(section1.generate());
        System.out.println(section2.generate());

    }
}