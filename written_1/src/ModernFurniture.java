public class ModernFurniture extends KitchenFurniture {

    public ModernFurniture(int number_of_drawers) {
        super(number_of_drawers);
    }

    @Override
    public String getDescription() {
        return "modern kitchen furniture";
    }

    @Override
    public int getCoefficient() {
        return 600;
    }
}
