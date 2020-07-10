package cabinvoiceTest;
import cabinvoice.service.CabInvoiceGenerator;
import org.junit.Assert;
import org.junit.Test;
public class CabInvoiceGeneratorTest {
    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare() {
        CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
        double distance = 3.0;
        int time = 2;
        double fare = cabInvoiceGenerator.fareCalculator(distance, time);
        Assert.assertEquals(32, fare, 0.0);
    }
}