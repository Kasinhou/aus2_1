package data;

import structure.IBSTData;

public class TestByRegionDate implements IBSTData<TestByRegionDate> {
    private PCRTest test;

    public TestByRegionDate(PCRTest test) {
        this.test = test;
    }
    @Override
    public int compareTo(TestByRegionDate comparedData) {
        if (this.test.getRegion() < comparedData.getTest().getRegion()) {
            return -1;
        } else if (this.test.getRegion() > comparedData.getTest().getRegion()) {
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