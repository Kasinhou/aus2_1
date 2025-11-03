package data;

import structure.AVLTree;
import structure.IBSTData;

public class District implements IBSTData<District> {
    private int districtCode;
    private int region;
    private AVLTree<TestByDate> positiveTestsByDate;

    public District(int code, int region) {
        this.districtCode = code;
        this.region = region;
        this.positiveTestsByDate = new AVLTree<>();
    }

    @Override
    public int compareTo(District comparedData) {
        return Integer.compare(this.districtCode, comparedData.getDistrictCode());
    }

    public int getDistrictCode() {
        return this.districtCode;
    }

    public AVLTree<TestByDate> getPositiveTestsByDate() {
        return this.positiveTestsByDate;
    }
}
