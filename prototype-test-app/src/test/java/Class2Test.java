import java.time.Duration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Class2Test {

    @Test
    void test() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(10).toMillis());
        assertEquals("Class2", new Class2().method());
    }
}
