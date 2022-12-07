import com.example.pricemanager.db.DataBaseConnection;
import org.junit.Test;

public class DataBaseConnectionTest {
    @Test(timeout = 1000)
    public void getConnectionShouldBeFasterThenOneSecond() {
        DataBaseConnection.getConnection();
    }
}

