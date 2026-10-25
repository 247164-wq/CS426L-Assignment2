public class ParallelMinSearch extends SensorSearchEngine implements Runnable {
    private double minValue = Double.MAX_VALUE;

    public ParallelMinSearch(double[] sensorData, int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        double min = Double.MAX_VALUE;
        for (int i = startIdx; i < endIdx; i++) {
            if (sensorData[i] < min) {
                min = sensorData[i];
            }
        }
        return min;
    }

    @Override
    public void run() {
        this.minValue = processGrid();
    }

    public double getMinValue() {
        return minValue;
    }
}