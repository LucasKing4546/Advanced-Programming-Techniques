//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        KitchenFurniture classic = new ClassicFurniture(4);
        HandleDecorator handleDecorator = new HandleDecorator(classic, 12, "Wooden");

        KitchenFurniture modern = new ModernFurniture(5);
        HandleDecorator modernHandle = new HandleDecorator(modern, 10, "Metal");
        SmartFeaturesDecorator smartFeaturesDecorator1 =new SmartFeaturesDecorator(modernHandle, "Smart LED Lighting");
        SmartFeaturesDecorator smartFeaturesDecorator2 = new SmartFeaturesDecorator(smartFeaturesDecorator1, "Temperature-controlled storage");

        System.out.println(handleDecorator.getDescription());
        System.out.println(handleDecorator.getPrice());

        System.out.println(smartFeaturesDecorator2.getDescription());
        System.out.println(smartFeaturesDecorator2.getPrice());

    }
}