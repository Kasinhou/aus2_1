package data;

import structure.IBSTData;

public class Workplace implements IBSTData<Workplace> {
    private int workplaceCode;


    public Workplace(int code) {
        this.workplaceCode = code;
    }

    @Override
    public int compareTo(Workplace comparedData) {
        return Integer.compare(this.workplaceCode, comparedData.getWorkplaceCode());
    }

    public int getWorkplaceCode() {
        return this.workplaceCode;
    }
}
