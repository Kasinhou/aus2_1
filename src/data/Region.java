package data;

import structure.AVLTree;
import structure.IBSTData;

public class Region implements IBSTData<Region> {
    private int regionCode;
    private AVLTree<TestByDate> positiveTestsByDate;

    public Region(int code) {
        this.regionCode = code;
        this.positiveTestsByDate = new AVLTree<>();
    }

    @Override
    public int compareTo(Region comparedData) {
        return Integer.compare(this.regionCode, comparedData.getRegionCode());
    }

    public int getRegionCode() {
        return this.regionCode;
    }

    public AVLTree<TestByDate> getPositiveTestsByDate() {
        return this.positiveTestsByDate;
    }
}
