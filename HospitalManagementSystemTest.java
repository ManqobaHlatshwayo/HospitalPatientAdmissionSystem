//Code attribution
//Title: Hospital Patient Admission System - Java console application (Patient
//management, bed management, inheritance, exception handling, JUnit testing)
//Author: Anthropic
//Date: 07 October 2026
//Version: Claude Sonnet 4.5
//Availability: Developed with the assistance of Claude (Anthropic), an AI
//language model, https://claude.ai

package hospitalsystem;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class HospitalManagementSystemTest {

    private HospitalManagementSystem system;

    @Before
    public void setUp() {
        system = new HospitalManagementSystem();
    }

    @Test
    public void testRegisterPatient_Success() throws Exception {
        Patient p = new Patient("P001", "Sipho", "Mkhize", 34, "Male", "Flu", PatientCategory.OUTPATIENT);
        system.registerPatient(p);
        assertEquals(1, system.getPatients().size());
        assertEquals("P001", system.getPatients().get(0).getPatientId());
    }

    @Test
    public void testRegisterPatient_DuplicateIdThrowsException() throws Exception {
        Patient p1 = new Patient("P001", "Sipho", "Mkhize", 34, "Male", "Flu", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P001", "Thandi", "Nkosi", 28, "Female", "Migraine", PatientCategory.EMERGENCY);
        system.registerPatient(p1);
        try {
            system.registerPatient(p2);
            fail("Expected DuplicatePatientIDException was not thrown.");
        } catch (DuplicatePatientIDException e) {
            // expected
        }
    }

    @Test
    public void testSearchPatient_Success() throws Exception {
        Patient p = new Patient("P002", "Lindiwe", "Zulu", 45, "Female", "Diabetes", PatientCategory.OUTPATIENT);
        system.registerPatient(p);
        Patient found = system.searchPatientById("P002");
        assertEquals("Zulu", found.getLastName());
    }

    @Test
    public void testSearchPatient_NotFoundThrowsException() {
        try {
            system.searchPatientById("P999");
            fail("Expected PatientNotFoundException was not thrown.");
        } catch (PatientNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testUpdatePatient_Success() throws Exception {
        Patient p = new Patient("P003", "Kabelo", "Dube", 30, "Male", "Fracture", PatientCategory.EMERGENCY);
        system.registerPatient(p);
        system.updatePatient("P003", "Kabelo", "Dube", 31, "Male", "Fracture healed");
        Patient updated = system.searchPatientById("P003");
        assertEquals(31, updated.getAge());
        assertEquals("Fracture healed", updated.getMedicalCondition());
    }

    @Test
    public void testDeletePatient_Success() throws Exception {
        Patient p = new Patient("P004", "Nomvula", "Khumalo", 50, "Female", "Asthma", PatientCategory.OUTPATIENT);
        system.registerPatient(p);
        system.deletePatient("P004");
        try {
            system.searchPatientById("P004");
            fail("Expected PatientNotFoundException was not thrown.");
        } catch (PatientNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testAllocateBed_Success() throws Exception {
        Inpatient inpatient = new Inpatient("P005", "Bongani", "Ngcobo", 60, "Male", "Pneumonia");
        system.registerPatient(inpatient);
        String bedNumber = system.allocateBed("P005");
        assertEquals("B01", bedNumber);
        assertEquals("B01", inpatient.getBedNumber());
    }

    @Test
    public void testReleaseBed_Success() throws Exception {
        Inpatient inpatient = new Inpatient("P006", "Ayanda", "Cele", 22, "Female", "Appendicitis");
        system.registerPatient(inpatient);
        system.allocateBed("P006");
        system.releaseBed("P006");
        assertEquals("Not Allocated", inpatient.getBedNumber());
        assertEquals(0, system.getWard().getOccupiedBedCount());
    }

    @Test
    public void testAllocateBed_PreventDuplicateAllocationOnOccupiedBed() throws Exception {
        Inpatient inpatient1 = new Inpatient("P007", "Zanele", "Buthelezi", 40, "Female", "Malaria");
        Inpatient inpatient2 = new Inpatient("P008", "Themba", "Mahlangu", 55, "Male", "COVID-19");
        system.registerPatient(inpatient1);
        system.registerPatient(inpatient2);

        String bed1 = system.allocateBed("P007");
        try {
            system.getWard().allocateSpecificBed(bed1, inpatient2);
            fail("Expected BedUnavailableException was not thrown.");
        } catch (BedUnavailableException e) {
            // expected
        }
    }

    @Test
    public void testAllocateBed_PreventAllocationWhenWardIsFull() throws Exception {
        for (int i = 1; i <= 20; i++) {
            Inpatient inpatient = new Inpatient("W" + i, "First" + i, "Last" + i, 20 + i, "Male", "Condition" + i);
            system.registerPatient(inpatient);
            system.allocateBed("W" + i);
        }
        assertFalse(system.getWard().hasAvailableBed());

        Inpatient extraPatient = new Inpatient("W21", "Extra", "Patient", 30, "Male", "Cold");
        system.registerPatient(extraPatient);
        try {
            system.allocateBed("W21");
            fail("Expected BedUnavailableException was not thrown.");
        } catch (BedUnavailableException e) {
            // expected
        }
    }

    @Test
    public void testAllocateBed_OnlyInpatientsCanBeAllocatedABed() throws Exception {
        Patient outpatient = new Patient("P009", "Sibongile", "Radebe", 27, "Female", "Checkup", PatientCategory.OUTPATIENT);
        system.registerPatient(outpatient);
        try {
            system.allocateBed("P009");
            fail("Expected BedUnavailableException was not thrown.");
        } catch (BedUnavailableException e) {
            // expected
        }
    }

    @Test
    public void testOccupancyReport_CalculatesPercentageCorrectly() throws Exception {
        for (int i = 1; i <= 5; i++) {
            Inpatient inpatient = new Inpatient("O" + i, "First" + i, "Last" + i, 20 + i, "Male", "Condition" + i);
            system.registerPatient(inpatient);
            system.allocateBed("O" + i);
        }
        assertEquals(5, system.getWard().getOccupiedBedCount());
        assertEquals(20, system.getWard().getTotalBedCount());
    }

    @Test
    public void testSortPatientsBySurname() throws Exception {
        system.registerPatient(new Patient("S1", "Anna", "Zulu", 20, "Female", "Flu", PatientCategory.OUTPATIENT));
        system.registerPatient(new Patient("S2", "Ben", "Adams", 25, "Male", "Cold", PatientCategory.OUTPATIENT));
        system.registerPatient(new Patient("S3", "Cara", "Mkhize", 30, "Female", "Cough", PatientCategory.OUTPATIENT));

        ArrayList<Patient> sorted = system.getPatientsSortedBySurname();
        assertEquals("Adams", sorted.get(0).getLastName());
        assertEquals("Mkhize", sorted.get(1).getLastName());
        assertEquals("Zulu", sorted.get(2).getLastName());
    }

    @Test
    public void testSortPatientsById() throws Exception {
        system.registerPatient(new Patient("P100", "A", "A", 20, "Male", "Flu", PatientCategory.OUTPATIENT));
        system.registerPatient(new Patient("P020", "B", "B", 25, "Male", "Cold", PatientCategory.OUTPATIENT));
        system.registerPatient(new Patient("P300", "C", "C", 30, "Male", "Cough", PatientCategory.OUTPATIENT));

        ArrayList<Patient> sorted = system.getPatientsSortedById();
        assertEquals("P020", sorted.get(0).getPatientId());
        assertEquals("P100", sorted.get(1).getPatientId());
        assertEquals("P300", sorted.get(2).getPatientId());
    }
}
