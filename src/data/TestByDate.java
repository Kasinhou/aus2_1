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
        LocalDate lcCompared = comparedData.getTest().getTimeOfTest();//TODO takto??? treba nejaku kontrolu na null, netreba tu pridat este aj najvyssiu hodnotu testu pre 14
//        if (lcThis == null) {
//            return Integer.compare(this.test.getTestCode(), comparedData.getTest().getTestCode());
//        }
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
