package Modules.UseCases;

import Modules.Entities.Organizer;
import Modules.Exceptions.UserNotFoundException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OrganizerManagerTest {
    @Test
    public void testIsUser(){
        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        Organizer organizer = new Organizer("Michael Scott", "DunderMifflin", "o123");
        assertFalse(organizerManager.isUser("Michael Scott"));
        organizerManager.addOrganizer(organizer);
        assertTrue(organizerManager.isUser("Michael Scott"));
    }

    @Test
    public void testValidatePassword(){
        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        assertFalse(organizerManager.validatePassword("Michael Scott", "DunderMifflin"));
        Organizer organizer = new Organizer("Michael Scott", "DunderMifflin", "o123");
        organizerManager.addOrganizer(organizer);
        assertTrue(organizerManager.validatePassword("Michael Scott", "DunderMifflin"));
        assertFalse(organizerManager.validatePassword("Michael Scott", "DunderMiff"));
        assertFalse(organizerManager.validatePassword("Dwight Schrute", "DunderMifflin"));
    }

    @Test
    public void testGetters() {
        // Test .getNumberOfOrganizers()
        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        assertEquals(0, organizerManager.getNumberOfOrganizers());
        organizerManager.createOrganizerAccount("Michael Scott", "DunderMifflin", "o123");
        assertEquals(1, organizerManager.getNumberOfOrganizers());
        organizerManager.createOrganizerAccount("Dwight Schrute", "Beets", "o124");
        organizerManager.createOrganizerAccount("Jim Halpert", "Beets", "o125");
        assertEquals(3, organizerManager.getNumberOfOrganizers());

        //Test .getListOfOrganizers
        assertEquals("Michael Scott", organizerManager.getListOfOrganizers().get(0).getUsername());
        assertEquals("DunderMifflin", organizerManager.getListOfOrganizers().get(0).getPassword());
        assertEquals("Beets", organizerManager.getListOfOrganizers().get(1).getPassword());
        assertNotEquals("beets", organizerManager.getListOfOrganizers().get(1).getPassword());
        assertEquals("Beets", organizerManager.getListOfOrganizers().get(1).getPassword());
        assertNotEquals("jim Halpert", organizerManager.getListOfOrganizers().get(2).getUsername());
        assertEquals("o125", organizerManager.getListOfOrganizers().get(2).getID());

        // Test .getUserID
        assertEquals("o123", organizerManager.getListOfOrganizers().get(0).getID());
        assertNotEquals("o123", organizerManager.getListOfOrganizers().get(1).getID());
        assertEquals("o125", organizerManager.getListOfOrganizers().get(2).getID());
    }

    @Test (expected = UserNotFoundException.class)
    public void testAddToOrganizedEvent(){
        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        organizerManager.createOrganizerAccount("Michael Scott", "DunderMifflin", "o123");
        organizerManager.createOrganizerAccount("Dwight Schrute", "Beets", "o124");
        organizerManager.createOrganizerAccount("Jim Halpert", "Beets", "o125");

        organizerManager.addToOrganizedEvents("o127", "e123");
        organizerManager.addToOrganizedEvents("o123", "e123");
    }

}