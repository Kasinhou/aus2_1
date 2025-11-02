package data;

import structure.IBSTData;

public class TestByCode implements IBSTData<TestByCode> {
    private PCRTest test;

    public TestByCode(PCRTest test) {
        this.test = test;
    }

    @Override
    public int compareTo(TestByCode comparedData) {
        return Integer.compare(this.test.getTestCode(), comparedData.getTest().getTestCode());
    }

    public PCRTest getTest() {
        return this.test;
    }
}
