package data;

import structure.IBSTData;

public class TestByDistrictDate implements IBSTData<TestByDistrictDate> {
    private PCRTest test;

    public TestByDistrictDate(PCRTest test) {
        this.test = test;
    }
    @Override
    public int compareTo(TestByDistrictDate comparedData) {
        if (this.test.getDistrict() < comparedData.getTest().getDistrict()) {
            return -1;
        } else if (this.test.getDistrict() > comparedData.getTest().getDistrict()) {
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
