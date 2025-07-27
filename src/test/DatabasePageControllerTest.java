package test;
import org.junit.jupiter.api.Test;

import main.DatabasePageController;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DatabasePageControllerTest {

    @Test
    void testSortDateStringsAscending() {
        Set<String> dates = new HashSet<>(Arrays.asList("12/05/2023", "01/01/2022", "15/08/2024"));
        List<String> sorted = DatabasePageController.sortDateStringsAscending(dates);
        assertEquals(Arrays.asList("01/01/2022", "12/05/2023", "15/08/2024"), sorted);
    }

    @Test
    void testSortDateStringsDescending() {
        Set<String> dates = new HashSet<>(Arrays.asList("12/05/2023", "01/01/2022", "15/08/2024"));
        List<String> sorted = DatabasePageController.sortDateStringsDescending(dates);
        assertEquals(Arrays.asList("15/08/2024", "12/05/2023", "01/01/2022"), sorted);
    }
}

