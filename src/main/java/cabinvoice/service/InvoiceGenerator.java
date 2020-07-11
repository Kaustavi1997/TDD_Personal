package cabinvoice.service;
import cabinvoice.exception.InvoiceGeneratorException;
import cabinvoice.model.InvoiceSummary;
import cabinvoice.model.Ride;
import cabinvoice.utility.RideCategory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InvoiceGenerator {
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
        double fare = distance * category.farePerKilometer + time * category.farePerMinute;
        return Math.max(fare, category.minimumFare);
    }
    /**
     * calculation of total fare of the rides taken by user.
     * @param rides
     * @return
     */
    public InvoiceSummary totalFareCalculator(Ride[] rides) {
        double totalFare = Arrays.stream(rides).mapToDouble(ride -> this.fareCalculator
                (ride.distance, ride.time, ride.rideCategory)).sum();
        return new InvoiceSummary(rides.length, totalFare);
    }
    /**
     * loading user specific ride information in map and checking user already exists or not.
     * @param userRides
     * @param user
     */
    public void setUserSpecificInvoice(Ride[] userRides, String user) throws InvoiceGeneratorException {
        if ((invoiceSummaryMap.containsKey(user))){
            throw new InvoiceGeneratorException(InvoiceGeneratorException.ExceptionType.KEY_ALREADY_EXISTS,"UserID already exists");
        }
        invoiceSummaryMap.put(user, this.totalFareCalculator(userRides));
    }

    /**
     * checking if the map is empty.
     * @param checkMap
     * @throws InvoiceGeneratorException
     */
    private void checkEmpty(Map checkMap) throws InvoiceGeneratorException {
        if(checkMap == null || checkMap.size() == 0){
            throw new InvoiceGeneratorException(InvoiceGeneratorException.ExceptionType.EMPTY_MAP,"No Invoice to display");
        }
    }

    /**
     * return invoice summary of the particular userID and checking if user id not present.
     * @param userID
     * @return
     */
     public InvoiceSummary getUserInvoiceSummary(String userID) throws InvoiceGeneratorException {
         checkEmpty(invoiceSummaryMap);
         if (!(invoiceSummaryMap.containsKey(userID))){
                throw new InvoiceGeneratorException(InvoiceGeneratorException.ExceptionType.NO_SUCH_KEY,"No such user Id present");
         }
         return invoiceSummaryMap.get(userID);
    }
}

