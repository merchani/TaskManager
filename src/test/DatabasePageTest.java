package test;
import javax.swing.*;

import main.BeforeEach;
import main.DatabasePage;
import main.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabasePageTest {

    private DatabasePage databasePage;
    private JFrame frame;

    @BeforeEach
    void setUp() {
        frame = new JFrame();
        databasePage = new DatabasePage(frame);
    }

    @Test
    void testAddNewTaskReturnsPanel() {
        JPanel panel = databasePage.addNewTask();
        assertNotNull(panel, "addNewTask should return a non-null JPanel");
    }

    @Test
    void testSearchButtonExists() {
        JButton searchButton = databasePage.getSearchButton();
        assertNotNull(searchButton);
        assertEquals("Search", searchButton.getText());
    }

    @Test
    void testClearSearchButtonInitiallyHidden() {
        JButton clearSearchButton = databasePage.getClearSearchButton();
        assertFalse(clearSearchButton.isVisible(), "Clear Search button should be hidden initially");
    }

    @Test
    void testHighlightSearchModeChangesUI() {
        databasePage.highlightSearchMode();
        assertTrue(databasePage.getClearSearchButton().isVisible());
    }

    @Test
    void testClearSearchHighlightResetsUI() {
        databasePage.highlightSearchMode(); // First highlight
        databasePage.clearSearchHighlight(); // Then clear
        assertFalse(databasePage.getClearSearchButton().isVisible());
    }
}

