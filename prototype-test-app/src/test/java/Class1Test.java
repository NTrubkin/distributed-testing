import java.time.Duration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Class1Test {

    @Test
    void longTest() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(10).toMillis());
        assertEquals("Class1", new Class1().method());
    }
}
