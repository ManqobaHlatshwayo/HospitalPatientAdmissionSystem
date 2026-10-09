//Code attribution
//Title: Hospital Patient Admission System - Java console application (Patient
//management, bed management, inheritance, exception handling, JUnit testing)
//Author: Anthropic
//Date: 07 October 2026
//Version: Claude Sonnet 4.5
//Availability: Developed with the assistance of Claude (Anthropic), an AI
//language model, https://claude.ai

package hospitalsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HospitalManagementSystem {

    private ArrayList<Patient> patients;
    private Ward ward;

    public HospitalManagementSystem() {
        patients = new ArrayList<>();
        ward = new Ward();
    }

    public void registerPatient(Patient patient) throws DuplicatePatientIDException {
        if (findPatientById(patient.getPatientId()) != null) {
            throw new DuplicatePatientIDException(
                    "A patient with ID " + patient.getPatientId() + " already exists.");
        }
        patients.add(patient);
    }

    public Patient searchPatientById(String patientId) throws PatientNotFoundException {
        Patient found = findPatientById(patientId);
        if (found == null) {
            throw new PatientNotFoundException("No patient found with ID " + patientId);
        }
        return found;
    }

    private Patient findPatientById(String patientId) {
        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(patientId)) {
                return p;
            }
        }
        return null;
    }

    public void updatePatient(String patientId, String firstName, String lastName,
                               int age, String gender, String medicalCondition) throws PatientNotFoundException {
        Patient patient = searchPatientById(patientId);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);
    }

    public void deletePatient(String patientId) throws PatientNotFoundException {
        Patient patient = searchPatientById(patientId);
        if (patient instanceof Inpatient) {
            try {
                ward.releaseBedByPatientId(patientId);
            } catch (BedUnavailableException e) {
                // Was an Inpatient but not occupying a bed - safe to ignore.
            }
        }
        patients.remove(patient);
    }

    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients are currently registered.");
            return;
        }
        System.out.println("\n========== ALL REGISTERED PATIENTS (" + patients.size() + ") ==========");
        for (Patient p : patients) {
            p.displayDetails();
        }
        System.out.println("=========================================================\n");
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public Ward getWard() {
        return ward;
    }

    public String allocateBed(String patientId) throws PatientNotFoundException, BedUnavailableException {
        Patient patient = searchPatientById(patientId);
        if (!(patient instanceof Inpatient)) {
            throw new BedUnavailableException(
                    "Only Inpatients may be allocated a bed. " + patientId + " is registered as "
                            + patient.getPatientCategory().getDisplayName() + ".");
        }
        return ward.allocateBed((Inpatient) patient);
    }

    public void releaseBed(String patientId) throws PatientNotFoundException, BedUnavailableException {
        Patient patient = searchPatientById(patientId);
        if (!(patient instanceof Inpatient)) {
            throw new BedUnavailableException(patientId + " is not an Inpatient and cannot occupy a bed.");
        }
        ward.releaseBedByPatientId(patientId);
    }

    public void displayWardOccupancyReport() {
        int occupied = ward.getOccupiedBedCount();
        int total = ward.getTotalBedCount();
        double occupancyPercentage = (total == 0) ? 0 : ((double) occupied / total) * 100;

        System.out.println("\n============ WARD OCCUPANCY REPORT ============");
        System.out.println("Total Registered Patients : " + patients.size());
        System.out.println("Total Beds in Ward        : " + total);
        System.out.println("Total Occupied Beds       : " + occupied);
        System.out.println("Total Available Beds      : " + (total - occupied));
        System.out.printf("Ward Occupancy Percentage  : %.2f%%%n", occupancyPercentage);
        System.out.println("=================================================\n");
    }

    public ArrayList<Patient> getPatientsSortedBySurname() {
        ArrayList<Patient> sorted = new ArrayList<>(patients);
        Collections.sort(sorted, Comparator.comparing(Patient::getLastName, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    public ArrayList<Patient> getPatientsSortedById() {
        ArrayList<Patient> sorted = new ArrayList<>(patients);
        Collections.sort(sorted, Comparator.comparing(Patient::getPatientId, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }
}
