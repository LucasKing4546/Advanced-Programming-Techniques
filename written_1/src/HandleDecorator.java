import javax.swing.plaf.PanelUI;

public class HandleDecorator extends FurnitureDecorator{
    private int number_of_handles;
    private String handleType;

    public HandleDecorator(KitchenFurniture furniture, int number_of_handles, String handleType) {
        super(furniture);
        this.number_of_handles = number_of_handles;
        this.handleType = handleType;
    }

    @Override
    public double getPrice(){
        if (handleType == "Plastic"){
            return super.getPrice() + 15 * number_of_handles;
        }
        else if (handleType == "Metal"){
            return super.getPrice() + 20 * number_of_handles;
        }
        else if (handleType == "Wooden"){
            return super.getPrice() + 30 * number_of_handles;
        }
        return 0;
    }

    @Override
    public String getDescription(){
        return super.getDescription() + " " + handleType + " " + number_of_handles;
    }
}
