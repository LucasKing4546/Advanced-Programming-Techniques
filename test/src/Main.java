//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int sum = 0;
        try{
            if (Integer.parseInt(args[0]) == 1){
               sum = Integer.parseInt(args[1]) + Integer.parseInt(args[2]);
            }
            else if (Integer.parseInt(args[0]) == 2){
                sum = Integer.parseInt(args[1]) - Integer.parseInt(args[2]);

            }
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println(sum);

    }
}