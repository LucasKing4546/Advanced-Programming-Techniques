abstract public class KitchenFurniture {
    protected int number_of_drawers;

    public KitchenFurniture(int number_of_drawers){
        this.number_of_drawers = number_of_drawers;
    }

    public int getNumber_of_drawers(){
        return number_of_drawers;
    }

    public abstract String getDescription();
    public abstract int getCoefficient();
    public double getPrice(){
        return this.getCoefficient() * number_of_drawers;
    }
}


