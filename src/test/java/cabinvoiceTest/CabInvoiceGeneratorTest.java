package cabinvoiceTest;
import cabinvoice.service.CabInvoiceGenerator;
import cabinvoice.service.InvoiceSummary;
import cabinvoice.utility.RideCategory;
import cabinvoice.service.Ride;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class CabInvoiceGeneratorTest {
    CabInvoiceGenerator cabInvoiceGenerator;
    @Before
    public void initializer() {
        cabInvoiceGenerator = new CabInvoiceGenerator();
    }
    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare() {
        double distance = 3.0;
        int time = 2;
        double fare = cabInvoiceGenerator.fareCalculator(distance, time, RideCategory.NORMAL);
        Assert.assertEquals(32, fare, 0.0);
    }
    @Test
    public void givenLessDistanceAndTime_ShouldReturnMinimumFare(){
        double fare = cabInvoiceGenerator.fareCalculator(0.1,1, RideCategory.NORMAL);
        Assert.assertEquals(5, fare, 0.0);
    }
    @Test
    public void givenMultipleRides_ShouldReturnTotalFare() {
        Ride[] rides = { new Ride(3.0, 2, RideCategory.NORMAL),
                new Ride(0.1, 1, RideCategory.NORMAL)};
        InvoiceSummary summary = cabInvoiceGenerator.totalFareCalculator(rides);
        InvoiceSummary expectedSummary = new InvoiceSummary(2,37);
        Assert.assertEquals(expectedSummary,summary);
    }
    @Test
    public void givenUserId_ShouldReturnInvoiceOfGivenUserId() {
        Ride[] user1Rides = { new Ride(3.0, 2, RideCategory.NORMAL),
                new Ride(0.1, 1, RideCategory.NORMAL)};
        cabInvoiceGenerator.setUserSpecificInvoice(user1Rides, "User1");
        Ride []user2Rides = {new Ride(5.0, 2, RideCategory.NORMAL),
                new Ride(6.1, 3, RideCategory.NORMAL)};
        cabInvoiceGenerator.setUserSpecificInvoice(user2Rides, "User2");
        Assert.assertEquals(new InvoiceSummary(2,37), cabInvoiceGenerator
                .getUserInvoiceSummary("User1"));
    }
    @Test
    public void givenUserCategory_PREMIUM_ShouldReturnPREMIUM(){
        Ride[] user1Rides = { new Ride(3.0, 2, RideCategory.NORMAL),
                new Ride(0.1, 1, RideCategory.NORMAL)};
        cabInvoiceGenerator.setUserSpecificInvoice(user1Rides, "User1");
        Ride []user2Rides = { new Ride(5.0, 2, RideCategory.PREMIUM),
                new Ride(6.1, 3, RideCategory.NORMAL)};
        cabInvoiceGenerator.setUserSpecificInvoice(user2Rides, "User2");
        Assert.assertEquals(new InvoiceSummary(2,143), cabInvoiceGenerator
                .getUserInvoiceSummary("User2"));
    }
}
