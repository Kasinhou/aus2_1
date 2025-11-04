package data;

import structure.IBSTData;

import java.time.LocalDate;

public class TestByDate implements IBSTData<TestByDate> {
    private PCRTest test;

    public TestByDate(PCRTest test) {
        this.test = test;
    }

    @Override
    public int compareTo(TestByDate comparedData) {
        LocalDate lcThis = this.test.getTimeOfTest();
        LocalDate lcCompared = comparedData.getTest().getTimeOfTest();
        if (lcThis.isBefore(lcCompared)) {
            return -1;
        } else if (lcThis.isAfter(lcCompared)) {
            return 1;
        } else {
            return Integer.compare(this.test.getTestCode(), comparedData.getTest().getTestCode());
        }
    }

    public PCRTest getTest() {
        return this.test;
    }
}
