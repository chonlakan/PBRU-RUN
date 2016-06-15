package pbru.chonlakan.yaemsak.pbru_run;


public class MyData {

    //Explicit
    private int[] avataInts = new int[] {R.drawable.bird48,
            R.drawable.doremon48, R.drawable.kon48, R.drawable.nobita48, R.drawable.rat48};
    private double latADouble = 13.070897, lngADouble = 99.977243;
    private double[] buildLatDoubles = new double[]{13.06909026, 13.07228823};
    private double[] buildLngDoubles = new double[]{99.97545719, 99.97256041};
    private int[] buildIconInts = new int[]{R.drawable.th, R.drawable.vn};

    public double[] getBuildLatDoubles() {
        return buildLatDoubles;
    }

    public double[] getBuildLngDoubles() {
        return buildLngDoubles;
    }

    public int[] getBuildIconInts() {
        return buildIconInts;
    }

    public int[] getAvataInts() {
        return avataInts;
    }

    public double getLatADouble() {
        return latADouble;
    }

    public double getLngADouble() {
        return lngADouble;
    }
}   // Main Class
