package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import javax.swing.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.ColumnPanel;
import main.PlannerPage;

public class PlannerPageTest {

    private JFrame testFrame;
    private PlannerPage plannerPage;

    @BeforeEach
    public void setUp() {
        testFrame = new JFrame();
        plannerPage = new PlannerPage(testFrame);
    }

    @Test
    public void testColumnsListInitialization() {
        ArrayList<ColumnPanel> columns = plannerPage.getColumnsList();
        assertNotNull(columns, "Columns list should not be null");
        assertEquals(4, columns.size(), "There should be 4 columns initialized");
    }

    @Test
    public void testColumnPanelsNotNull() {
        JPanel columnPanels = plannerPage.getColumnPanels();
        assertNotNull(columnPanels, "Column panels should not be null");
    }

    @Test
    public void testRevalidateFrameDoesNotThrow() {
        assertDoesNotThrow(() -> plannerPage.revalidateFrame(), "revalidateFrame should not throw any exceptions");
    }
}

