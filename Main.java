package hospitalsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static HospitalManagementSystem system = new HospitalManagementSystem();

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("=====================================================");
        System.out.println(" WELCOME TO THE MEDICARE HOSPITAL ADMISSION SYSTEM");
        System.out.println("=====================================================");

        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        registerPatientMenu();
                        break;
                    case 2:
                        searchPatientMenu();
                        break;
                    case 3:
                        updatePatientMenu();
                        break;
                    case 4:
                        deletePatientMenu();
                        break;
                    case 5:
                        system.displayAllPatients();
                        break;
                    case 6:
                        allocateBedMenu();
                        break;
                    case 7:
                        releaseBedMenu();
                        break;
                    case 8:
                        system.getWard().displayWardLayout();
                        break;
                    case 9:
                        system.getWard().displayAvailableBeds();
                        break;
                    case 10:
                        system.getWard().displayOccupiedBeds();
                        break;
                    case 11:
                        system.displayWardOccupancyReport();
                        break;
                    case 12:
                        sortPatientsMenu();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Goodbye. Thank you for using the MediCare Hospital System.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a number from the menu.");
                }
            } catch (DuplicatePatientIDException | PatientNotFoundException | BedUnavailableException e) {
                System.out.println("ERROR: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n-------------------- MAIN MENU --------------------");
        System.out.println("1.  Register a new patient");
        System.out.println("2.  Search for a patient (by Patient ID)");
        System.out.println("3.  Update an existing patient's details");
        System.out.println("4.  Delete a patient");
        System.out.println("5.  Display all registered patients");
        System.out.println("6.  Allocate a bed to an Inpatient");
        System.out.println("7.  Release a bed (discharge)");
        System.out.println("8.  Display complete ward layout");
        System.out.println("9.  Display available beds");
        System.out.println("10. Display occupied beds");
        System.out.println("11. Display ward occupancy report");
        System.out.println("12. Display patients sorted (surname / ID)");
        System.out.println("0.  Exit");
        System.out.println("-----------------------------------------------------");
    }

    private static void registerPatientMenu() throws DuplicatePatientIDException {
        System.out.println("\n--- Register New Patient ---");
        String id = readString("Enter Patient ID: ");
        String firstName = readString("Enter First Name: ");
        String lastName = readString("Enter Last Name: ");
        int age = readInt("Enter Age: ");
        String gender = readString("Enter Gender: ");
        String condition = readString("Enter Medical Condition: ");
        PatientCategory category = readPatientCategory();

        Patient newPatient;
        if (category == PatientCategory.INPATIENT) {
            newPatient = new Inpatient(id, firstName, lastName, age, gender, condition);
        } else {
            newPatient = new Patient(id, firstName, lastName, age, gender, condition, category);
        }

        system.registerPatient(newPatient);
        System.out.println("Patient " + id + " registered successfully as " + category.getDisplayName() + ".");
    }

    private static void searchPatientMenu() throws PatientNotFoundException {
        String id = readString("Enter Patient ID to search: ");
        Patient found = system.searchPatientById(id);
        System.out.println("\n--- Patient Found ---");
        found.displayDetails();
    }

    private static void updatePatientMenu() throws PatientNotFoundException {
        String id = readString("Enter Patient ID to update: ");
        system.searchPatientById(id).displayDetails();

        String firstName = readString("Enter new First Name: ");
        String lastName = readString("Enter new Last Name: ");
        int age = readInt("Enter new Age: ");
        String gender = readString("Enter new Gender: ");
        String condition = readString("Enter new Medical Condition: ");

        system.updatePatient(id, firstName, lastName, age, gender, condition);
        System.out.println("Patient " + id + " updated successfully.");
    }

    private static void deletePatientMenu() throws PatientNotFoundException {
        String id = readString("Enter Patient ID to delete: ");
        system.deletePatient(id);
        System.out.println("Patient " + id + " deleted successfully.");
    }

    private static void allocateBedMenu() throws PatientNotFoundException, BedUnavailableException {
        String id = readString("Enter Patient ID of the Inpatient: ");
        String bedNumber = system.allocateBed(id);
        System.out.println("Bed " + bedNumber + " allocated to patient " + id + ".");
    }

    private static void releaseBedMenu() throws PatientNotFoundException, BedUnavailableException {
        String id = readString("Enter Patient ID being discharged: ");
        system.releaseBed(id);
        System.out.println("Bed released for patient " + id + ".");
    }

    private static void sortPatientsMenu() {
        System.out.println("Sort by: 1. Surname   2. Patient ID");
        int choice = readInt("Enter your choice: ");
        ArrayList<Patient> sorted;
        if (choice == 1) {
            sorted = system.getPatientsSortedBySurname();
            System.out.println("\n--- Patients Sorted by Surname ---");
        } else {
            sorted = system.getPatientsSortedById();
            System.out.println("\n--- Patients Sorted by Patient ID ---");
        }
        if (sorted.isEmpty()) {
            System.out.println("No patients are currently registered.");
        }
        for (Patient p : sorted) {
            p.displayDetails();
        }
    }

    private static PatientCategory readPatientCategory() {
        while (true) {
            System.out.println("Select Patient Category: 1. Inpatient   2. Outpatient   3. Emergency");
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    return PatientCategory.INPATIENT;
                case 2:
                    return PatientCategory.OUTPATIENT;
                case 3:
                    return PatientCategory.EMERGENCY;
                default:
                    System.out.println("Invalid category. Please enter 1, 2 or 3.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
}