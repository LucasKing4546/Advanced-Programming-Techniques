public abstract class FurnitureDecorator extends KitchenFurniture {
    private KitchenFurniture furniture;

    public FurnitureDecorator(KitchenFurniture furniture){
        super(furniture.number_of_drawers);
        this.furniture = furniture;
    }

    @Override
    public String getDescription(){
        return  furniture.getDescription();
    }

    @Override
    public int getCoefficient(){
        return furniture.getCoefficient();
    }

    @Override
    public double getPrice(){
        return furniture.getPrice();
    }
}
