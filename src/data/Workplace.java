package data;

import structure.AVLTree;
import structure.IBSTData;

public class Workplace implements IBSTData<Workplace> {
    private int workplaceCode;
    private int district;
    private AVLTree<TestByDate> testsByDate;


    public Workplace(int code, int district) {
        this.workplaceCode = code;
        this.district = district;
        this.testsByDate = new AVLTree<>();
    }

    @Override
    public int compareTo(Workplace comparedData) {
        return Integer.compare(this.workplaceCode, comparedData.getWorkplaceCode());
    }

    public int getWorkplaceCode() {
        return this.workplaceCode;
    }

    public int getDistrict() {
        return this.district;
    }

    public AVLTree<TestByDate> getTestsByDate() {
        return this.testsByDate;
    }
}
