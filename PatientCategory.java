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