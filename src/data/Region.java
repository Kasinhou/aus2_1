package data;

import structure.IBSTData;

public class Region implements IBSTData<Region> {
    private int regionCode;
    private int count;//could be anything

    public Region(int code) {
        this.regionCode = code;
        this.count = 0;
    }

    @Override
    public int compareTo(Region comparedData) {
        if (this.count < comparedData.getCount()) {
            return -1;
        } else if (this.count > comparedData.getCount()) {
            return 1;
        }
        return Integer.compare(this.regionCode, comparedData.getRegionCode());
    }

    public int getRegionCode() {
        return this.regionCode;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }
}
