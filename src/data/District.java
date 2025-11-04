package data;

import structure.AVLTree;
import structure.IBSTData;

public class District implements IBSTData<District> {
    private int districtCode;
    private int count;//anything
    private double value;
    private PCRTest test;

    public District(int code) {
        this.districtCode = code;
        this.count = 0;
        this.value = 0.0;
    }

    @Override
    public int compareTo(District comparedData) {
        if (this.count < comparedData.getCount()) {
            return -1;
        } else if (this.count > comparedData.getCount()) {
            return 1;
        } else {
            if (Double.compare(this.value, comparedData.value) < 0) {
                return -1;
            } else if (Double.compare(this.value, comparedData.value) > 0) {
                return 1;
            } else {
                return Integer.compare(this.districtCode, comparedData.getDistrictCode());
            }
        }
    }

    public int getDistrictCode() {
        return this.districtCode;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTest(PCRTest test) {
        this.test = test;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCount() {
        return this.count;
    }

    public PCRTest getTest() {
        return this.test;
    }

    public double getValue() {
        return this.value;
    }
}
