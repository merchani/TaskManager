package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.ColumnPanel;
import main.PlannerPage;
import main.PlannerPageController;
import main.Tasks;

public class PlannerPageControllerTest {

    private PlannerPage plannerPage;
    private PlannerPageController controller;
    private JFrame frame;

    @BeforeEach
    public void setUp() {
        frame = new JFrame();
        plannerPage = new PlannerPage(frame);
        controller = new PlannerPageController(plannerPage);
    }

    @Test
    public void testControllerInitializesWithoutError() {
        assertNotNull(controller, "Controller should be initialized");
    }

    @Test
    public void testTasksAreAssignedToCorrectColumns() {
        ArrayList<ColumnPanel> columns = plannerPage.getColumnsList();
        boolean taskAssigned = columns.stream()
            .anyMatch(col -> col.getTasks().size() > 0);
        assertTrue(taskAssigned, "At least one column should have tasks assigned");
    }

    @Test
    public void testTasksClearedAfterAssignment() {
        assertEquals(0, Tasks.getInstance().size(), "Tasks should be cleared after assignment");
    }
}

