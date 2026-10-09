//Code attribution
//Title: Hospital Patient Admission System - Java console application (Patient
//management, bed management, inheritance, exception handling, JUnit testing)
//Author: Anthropic
//Date: 07 October 2026
//Version: Claude Sonnet 4.5
//Availability: Developed with the assistance of Claude (Anthropic), an AI
//language model, https://claude.ai

package hospitalsystem;

public class Ward {

    public static final int ROWS = 4;
    public static final int COLUMNS = 5;
    public static final String WARD_NUMBER = "Ward 1";

    private String[][] bedLabels;
    private Inpatient[][] bedOccupants;

    public Ward() {
        bedLabels = new String[ROWS][COLUMNS];
        bedOccupants = new Inpatient[ROWS][COLUMNS];
        int bedCounter = 1;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                bedLabels[row][col] = String.format("B%02d", bedCounter);
                bedCounter++;
            }
        }
    }

    public String allocateBed(Inpatient inpatient) throws BedUnavailableException {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (bedOccupants[row][col] == null) {
                    bedOccupants[row][col] = inpatient;
                    inpatient.setWardNumber(WARD_NUMBER);
                    inpatient.setBedNumber(bedLabels[row][col]);
                    return bedLabels[row][col];
                }
            }
        }
        throw new BedUnavailableException("No beds are available. The ward is full (20/20 occupied).");
    }

    public void allocateSpecificBed(String bedNumber, Inpatient inpatient) throws BedUnavailableException {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (bedLabels[row][col].equalsIgnoreCase(bedNumber)) {
                    if (bedOccupants[row][col] != null) {
                        throw new BedUnavailableException("Bed " + bedNumber + " is already occupied.");
                    }
                    bedOccupants[row][col] = inpatient;
                    inpatient.setWardNumber(WARD_NUMBER);
                    inpatient.setBedNumber(bedLabels[row][col]);
                    return;
                }
            }
        }
        throw new BedUnavailableException("Bed " + bedNumber + " does not exist.");
    }

    public void releaseBedByPatientId(String patientId) throws BedUnavailableException {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Inpatient occupant = bedOccupants[row][col];
                if (occupant != null && occupant.getPatientId().equalsIgnoreCase(patientId)) {
                    bedOccupants[row][col] = null;
                    occupant.setWardNumber("Not Allocated");
                    occupant.setBedNumber("Not Allocated");
                    return;
                }
            }
        }
        throw new BedUnavailableException("Patient " + patientId + " is not currently occupying a bed.");
    }

    public void releaseBedByBedNumber(String bedNumber) throws BedUnavailableException {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (bedLabels[row][col].equalsIgnoreCase(bedNumber)) {
                    if (bedOccupants[row][col] == null) {
                        throw new BedUnavailableException("Bed " + bedNumber + " is already unoccupied.");
                    }
                    Inpatient occupant = bedOccupants[row][col];
                    occupant.setWardNumber("Not Allocated");
                    occupant.setBedNumber("Not Allocated");
                    bedOccupants[row][col] = null;
                    return;
                }
            }
        }
        throw new BedUnavailableException("Bed " + bedNumber + " does not exist.");
    }

    public void displayWardLayout() {
        System.out.println("\n=========== WARD LAYOUT (" + WARD_NUMBER + ") ===========");
        for (int row = 0; row < ROWS; row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 0; col < COLUMNS; col++) {
                String status = (bedOccupants[row][col] == null)
                        ? "[Free]"
                        : "[" + bedOccupants[row][col].getPatientId() + "]";
                line.append(bedLabels[row][col]).append(status).append("  ");
            }
            System.out.println(line.toString());
        }
        System.out.println("=======================================\n");
    }

    public void displayAvailableBeds() {
        System.out.println("\n--- Available Beds ---");
        boolean anyAvailable = false;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (bedOccupants[row][col] == null) {
                    System.out.println(bedLabels[row][col]);
                    anyAvailable = true;
                }
            }
        }
        if (!anyAvailable) {
            System.out.println("No beds are currently available.");
        }
    }

    public void displayOccupiedBeds() {
        System.out.println("\n--- Occupied Beds ---");
        boolean anyOccupied = false;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (bedOccupants[row][col] != null) {
                    System.out.println(bedLabels[row][col] + " - " + bedOccupants[row][col].getPatientId()
                            + " (" + bedOccupants[row][col].getFirstName() + " " + bedOccupants[row][col].getLastName() + ")");
                    anyOccupied = true;
                }
            }
        }
        if (!anyOccupied) {
            System.out.println("No beds are currently occupied.");
        }
    }

    public int getOccupiedBedCount() {
        int count = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (bedOccupants[row][col] != null) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getTotalBedCount() {
        return ROWS * COLUMNS;
    }

    public boolean hasAvailableBed() {
        return getOccupiedBedCount() < getTotalBedCount();
    }
}
