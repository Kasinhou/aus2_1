package data;

import structure.IBSTData;

public class TestByWorkplaceDate implements IBSTData<TestByWorkplaceDate> {
    private PCRTest test;

    public TestByWorkplaceDate(PCRTest test) {
        this.test = test;
    }
    @Override
    public int compareTo(TestByWorkplaceDate comparedData) {
        if (this.test.getWorkplace() < comparedData.getTest().getWorkplace()) {
            return -1;
        } else if (this.test.getWorkplace() > comparedData.getTest().getWorkplace()) {
            return 1;
        } else {
            if (this.test.getTimeOfTest().isBefore(comparedData.getTest().getTimeOfTest())) {
                return -1;
            } else if (this.test.getTimeOfTest().isAfter(comparedData.getTest().getTimeOfTest())) {
                return 1;
            } else {
                return Integer.compare(this.test.getTestCode(), comparedData.getTest().getTestCode());
            }
        }
    }

    public PCRTest getTest() {
        return this.test;
    }
}
