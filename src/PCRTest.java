import structure.IBSTData;

import java.time.LocalDate;

public class PCRTest implements IBSTData<PCRTest> {
    // dátum a čas testu
    // unikátne číslo pacienta – reťazec
    // unikátny náhodný kód PCR testu - celé číslo
    // unikátny kód pracoviska, ktoré PCR test vykonalo – celé číslo
    // kód okresu – celé číslo
    // kód kraja – celé číslo
    // výsledok testu – boolean
    // hodnota testu - double
    // poznámka - reťazec
    private LocalDate timeOfTest;
    private String personID;
    private int testCode;
    private int workplace;
    private int region;
    private int district;
    private boolean testResult;
    private double testValue;
    private String note;

    @Override
    public int compareTo(PCRTest comparedData) {
        return 0;
    }
}
