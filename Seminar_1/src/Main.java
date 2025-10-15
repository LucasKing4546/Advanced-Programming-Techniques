import domain.Doctor;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Doctor doctor = new Doctor(1, "Michael", "Cardiology", "Cluj", 4.5);
        System.out.println(doctor);
    }
}