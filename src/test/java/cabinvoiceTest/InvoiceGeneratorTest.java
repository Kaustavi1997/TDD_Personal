package cabinvoiceTest;
import cabinvoice.exception.InvoiceGeneratorException;
import cabinvoice.service.InvoiceGenerator;
import cabinvoice.model.InvoiceSummary;
import cabinvoice.utility.RideCategory;
import cabinvoice.model.Ride;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class InvoiceGeneratorTest {
    InvoiceGenerator invoiceGenerator;
    @Before
    public void initializer() {
        invoiceGenerator = new InvoiceGenerator();
    }
    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare() {
        double distance = 3.0;
        int time = 2;
        double fare = invoiceGenerator.fareCalculator(distance, time, RideCategory.NORMAL);
        Assert.assertEquals(32, fare, 0.0);
    }
    @Test
    public void givenLessDistanceAndTime_ShouldReturnMinimumFare(){
        double fare = invoiceGenerator.fareCalculator(0.1,1, RideCategory.NORMAL);
        Assert.assertEquals(5, fare, 0.0);
    }
    @Test
    public void givenMultipleRides_ShouldReturnTotalFare() {
        Ride[] rides = { new Ride(3.0, 2, RideCategory.NORMAL),
                new Ride(0.1, 1, RideCategory.NORMAL)};
        InvoiceSummary summary = invoiceGenerator.totalFareCalculator(rides);
        InvoiceSummary expectedSummary = new InvoiceSummary(2,37);
        Assert.assertEquals(expectedSummary,summary);
    }
    @Test
    public void givenUserId_ShouldReturnInvoiceOfGivenUserId() throws InvoiceGeneratorException {
        Ride[] user1Rides = { new Ride(3.0, 2, RideCategory.NORMAL),
                new Ride(0.1, 1, RideCategory.NORMAL)};
        invoiceGenerator.setUserSpecificInvoice(user1Rides, "User1");
        Ride []user2Rides = {new Ride(5.0, 2, RideCategory.NORMAL),
                new Ride(6.1, 3, RideCategory.NORMAL)};
        invoiceGenerator.setUserSpecificInvoice(user2Rides, "User2");
        Assert.assertEquals(new InvoiceSummary(2,37), invoiceGenerator
                .getUserInvoiceSummary("User1"));
    }
    @Test
    public void givenUserCategory_PREMIUM_ShouldReturnPREMIUM() throws InvoiceGeneratorException {
        Ride[] user1Rides = { new Ride(3.0, 2, RideCategory.NORMAL),
                new Ride(0.1, 1, RideCategory.NORMAL)};
        invoiceGenerator.setUserSpecificInvoice(user1Rides, "User1");
        Ride []user2Rides = { new Ride(5.0, 2, RideCategory.PREMIUM),
                new Ride(6.1, 3, RideCategory.NORMAL)};
        invoiceGenerator.setUserSpecificInvoice(user2Rides, "User2");
        Assert.assertEquals(new InvoiceSummary(2,143), invoiceGenerator
                .getUserInvoiceSummary("User2"));
    }
    @Test
    public void givenUserId_WhenDuplicate_ShouldThrowException() throws InvoiceGeneratorException {
        try {
            Ride[] user1Rides = {new Ride(3.0, 2, RideCategory.NORMAL),
                    new Ride(0.1, 1, RideCategory.NORMAL)};
            invoiceGenerator.setUserSpecificInvoice(user1Rides, "User1");
            Ride[] user2Rides = {new Ride(5.0, 2, RideCategory.NORMAL),
                    new Ride(6.1, 3, RideCategory.NORMAL)};
            invoiceGenerator.setUserSpecificInvoice(user2Rides, "User1");
        }catch(InvoiceGeneratorException e){
            System.out.println(e.getMessage());
        }

    }
    @Test
    public void givenUserId_WhenDoesntExists_ShouldThrowException() throws InvoiceGeneratorException {
        try {
            Ride[] user1Rides = {new Ride(3.0, 2, RideCategory.NORMAL),
                    new Ride(0.1, 1, RideCategory.NORMAL)};
            invoiceGenerator.setUserSpecificInvoice(user1Rides, "User1");
            Ride[] user2Rides = {new Ride(5.0, 2, RideCategory.NORMAL),
                    new Ride(6.1, 3, RideCategory.NORMAL)};
            invoiceGenerator.setUserSpecificInvoice(user2Rides, "User2");
            Assert.assertEquals(new InvoiceSummary(2,143), invoiceGenerator
                                .getUserInvoiceSummary("User3"));
        }catch(InvoiceGeneratorException e){
            System.out.println(e.getMessage());
        }

    }
    @Test
    public void givenUserId_WhenNoDataPresent_ShouldThrowException() throws InvoiceGeneratorException {
        try {
            invoiceGenerator.getUserInvoiceSummary("user3");
        }catch(InvoiceGeneratorException e){
            Assert.assertEquals(InvoiceGeneratorException.ExceptionType.EMPTY_MAP,e.exceptionType);
        }

    }
}
