package domain;

public class Package {
    private String recipient;
    private String address;
    private int x_coord;
    private int y_coord;
    private boolean status;

    public Package(String recipient, String address, int x_coord, int y_coord, boolean status) {
        this.recipient = recipient;
        this.address = address;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.status = status;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getAddress() {
        return address;
    }

    public int getLocationX() {
        return x_coord;
    }

    public int getLocationY() {
        return y_coord;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Package:" +
                "recipient = '" + recipient + '\'' +
                ", address = '" + address + '\'' +
                ", x_coord = " + x_coord +
                ", y_coord = " + y_coord +
                ", status = " + status;
    }
}
