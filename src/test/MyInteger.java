package test;

import structure.IBSTData;

public class MyInteger implements IBSTData<MyInteger> {
    private int i;

    public MyInteger(int i) {
        this.i = i;
    }

    @Override
    public int compareTo(MyInteger comparedInt) {
        return Integer.compare(this.i, comparedInt.getI());
    }

    public int getI() {
        return i;
    }
}
