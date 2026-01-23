package domain;

public class Rental {
    private int clientId;
    private int carId;
    private String startDate;
    private String endDate;
    private int totalCost;

    public Rental(int clientId, int carId, String startDate, String endDate, int totalCost) {
        this.clientId = clientId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
    }

    public int getClientId() {
        return clientId;
    }

    public int getCarId() {
        return carId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
