//Code attribution
//Title: Hospital Patient Admission System - Java console application (Patient
//management, bed management, inheritance, exception handling, JUnit testing)
//Author: Anthropic
//Date: 07 October 2026
//Version: Claude Sonnet 4.5
//Availability: Developed with the assistance of Claude (Anthropic), an AI
//language model, https://claude.ai

package hospitalsystem;

public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
