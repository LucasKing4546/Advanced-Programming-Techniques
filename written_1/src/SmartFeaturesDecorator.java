public class SmartFeaturesDecorator extends FurnitureDecorator{
    private String feature;
    public SmartFeaturesDecorator(KitchenFurniture furniture,String feature) {
        super(furniture);
        this.feature = feature;
    }

    @Override
    public double getPrice(){
        if (feature.equals("Smart LED Lighting")){
            return super.getPrice() + 300;
        }
        else if (feature.equals("Temperature-controlled storage")){
            return super.getPrice() + 500;
        }
        return 0;
    }

    @Override
    public String getDescription(){
        return super.getDescription() + " " + feature;
    }
}
