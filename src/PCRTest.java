import java.time.LocalDate;

public class PCRTest {
    // dátum a čas testu
    // unikátne číslo pacienta – reťazec
    // unikátny náhodný kód PCR testu - celé číslo
    // unikátny kód pracoviska, ktoré PCR test vykonalo – celé číslo
    private LocalDate timeOfTest;
    private String personID;
    private int testCode;
    private int workplace;
    private int region;
    private int district;
    private boolean testResult;
    private double testValue;
    private String note;

    public PCRTest(LocalDate timeOfTest, String personID, int testCode,
                   int workplace, int region, int district,
                   boolean testResult, double testValue, String note) {
        this.timeOfTest = timeOfTest;
        this.personID = personID;
        this.testCode = testCode;
        this.workplace = workplace;
        this.region = region;
        this.district = district;
        this.testResult = testResult;
        this.testValue = testValue;
        this.note = note;
    }

    public LocalDate getTimeOfTest() {
        return this.timeOfTest;
    }

    public String getPersonID() {
        return this.personID;
    }

    public int getTestCode() {
        return this.testCode;
    }

    public int getWorkplace() {
        return this.workplace;
    }

    public int getRegion() {
        return this.region;
    }

    public int getDistrict() {
        return this.district;
    }

    public boolean getTestResult() {
        return this.testResult;
    }

    public double getTestValue() {
        return this.testValue;
    }

    public String getTestInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Test ID: ").append(this.testCode).append("\nPerson ID: ").append(this.personID);
        sb.append("\nTest positive: ").append(this.testResult).append("\nValue: ").append(this.testValue);
        sb.append("\nDate and time: ").append(this.timeOfTest).append("\nRegion: ").append(this.region);
        sb.append("\nDistrict: ").append(this.district).append("\nWorkspace: ").append(this.workplace).append("\nNote: ").append(this.note);
        return sb.toString();
    }
}
