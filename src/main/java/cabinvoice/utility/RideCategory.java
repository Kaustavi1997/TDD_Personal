package cabinvoice.utility;
/**
 * TASK:taken a enum for ride type
 */
public enum RideCategory {
    PREMIUM(15,2,20),NORMAL(10,1,5);
    public int farePerKilometer;
    public int farePerMinute;
    public double minimumFare;

    RideCategory(int farePerKilometer, int farePerMinute, double minimumFare) {
        this.farePerKilometer = farePerKilometer;
        this.farePerMinute = farePerMinute;
        this.minimumFare = minimumFare;
    }
}
