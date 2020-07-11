package cabinvoice.service;
import cabinvoice.model.InvoiceSummary;
import cabinvoice.model.Ride;
import cabinvoice.utility.RideCategory;
import java.util.HashMap;
import java.util.Map;

public class InvoiceGenerator {
    private static int farePerKilometer;
    private static int farePerMinute;
    private static double minimumFare;
    private Map<String, InvoiceSummary> invoiceSummaryMap;
    /**
     * Initialized the map.
     */
    public InvoiceGenerator(){
        invoiceSummaryMap = new HashMap<>();;
    }
    /**
     * calculate the fare according to distance,time and ride category
     * @param distance
     * @param time
     * @param category
     * @return
     */

    public double fareCalculator(double distance, int time, RideCategory category) {
        this.fareCategory(category);
        double fare = distance * InvoiceGenerator.farePerKilometer + time * farePerMinute;
        return Math.max(fare, minimumFare);
    }
    /**
     * details of ride according to ridecategory using switch case.
     * @param category
     */
    private void fareCategory(RideCategory category) {
        switch (category) {
            case NORMAL:
                farePerMinute = 1;
                farePerKilometer = 10;
                minimumFare = 5.0;
                break;
            case PREMIUM:
                farePerKilometer = 15;
                farePerMinute = 2;
                minimumFare = 20.0;
                break;
        }
    }
    /**
     * calculation of total fare of the rides taken by user.
     * @param rides
     * @return
     */
    public InvoiceSummary totalFareCalculator(Ride[] rides) {
        double totalFare = 0;
        for (Ride ride:rides ){
            totalFare += this.fareCalculator(ride.distance, ride.time, ride.rideCategory);
        }
        return new InvoiceSummary(rides.length, totalFare);
    }
    /**
     * loading user specific ride information in map.
     * @param userRides
     * @param user
     */
    public void setUserSpecificInvoice(Ride[] userRides, String user) {
        invoiceSummaryMap.put(user, this.totalFareCalculator(userRides));
    }

    /**
     * return invoice summary of the particular userID
     * @param userID
     * @return
     */

        public InvoiceSummary getUserInvoiceSummary(String userID) {
        return invoiceSummaryMap.get(userID);
    }
}

