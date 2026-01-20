public class ClassicFurniture extends KitchenFurniture{

    public ClassicFurniture(int number_of_drawers){
        super(number_of_drawers);
    }

    @Override
    public String getDescription(){
        return "classic kitchen furniture";
    }

    @Override
    public int getCoefficient(){
        return 500;
    }
}
