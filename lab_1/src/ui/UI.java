package ui;
import services.Inventory;
public class UI {
    private Inventory inventory;

    public UI(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void showMenu(){
        System.out.println("1 - Buy Car");
        System.out.println("2 - Sell Car");
        System.out.println("3 - Show all CarParks");
        System.out.println("0 - Exit");
    }

    public void buyCar(){
        System.out.println("Enter Car Park ID");
        inventory.showAllCarParks();

    }
    
}
