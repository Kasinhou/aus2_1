import structure.IBSTData;

public class TestsByRegionDate implements IBSTData<TestsByRegionDate> {
    private PCRTest test;

    public TestsByRegionDate(PCRTest test) {
        this.test = test;
    }
    @Override
    public int compareTo(TestsByRegionDate comparedData) {
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