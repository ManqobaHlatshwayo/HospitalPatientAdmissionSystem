//Code attribution
//Title: Hospital Patient Admission System - Java console application (Patient
//management, bed management, inheritance, exception handling, JUnit testing)
//Author: Anthropic
//Date: 07 October 2026
//Version: Claude Sonnet 4.5
//Availability: Developed with the assistance of Claude (Anthropic), an AI
//language model, https://claude.ai

package hospitalsystem;

public enum PatientCategory {
    INPATIENT,
    OUTPATIENT,
    EMERGENCY;

    public String getDisplayName() {
        switch (this) {
            case INPATIENT:
                return "Inpatient";
            case OUTPATIENT:
                return "Outpatient";
            case EMERGENCY:
                return "Emergency";
            default:
                return this.name();
        }
    }
}
