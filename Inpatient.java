//Code attribution
//Title: Hospital Patient Admission System - Java console application (Patient
//management, bed management, inheritance, exception handling, JUnit testing)
//Author: Anthropic
//Date: 07 October 2026
//Version: Claude Sonnet 4.5
//Availability: Developed with the assistance of Claude (Anthropic), an AI
//language model, https://claude.ai

package hospitalsystem;

public class Inpatient extends Patient {

    private String wardNumber;
    private String bedNumber;

    public Inpatient(String patientId, String firstName, String lastName, int age,
                      String gender, String medicalCondition) {
        super(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = "Not Allocated";
        this.bedNumber = "Not Allocated";
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Ward Number      : " + wardNumber);
        System.out.println("Bed Number       : " + bedNumber);
    }
}
