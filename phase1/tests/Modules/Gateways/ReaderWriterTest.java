package Modules.Gateways;

import Modules.Entities.Event;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ReaderWriterTest {

    @Test
    public void testReadSerFile() {

    }

    @Test
    public void testWriteSerFile() {
        LocalDateTime e1Time = LocalDateTime.of(2020, 11, 5, 11, 0, 0);
        LocalDateTime e2Time = LocalDateTime.of(2020, 11, 6, 11, 0, 0);
        Event e1 = new Event("2", e1Time);
        Event e2 = new Event("3", e2Time);
    }
}
