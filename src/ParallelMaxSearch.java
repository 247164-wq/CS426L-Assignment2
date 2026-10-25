public class ParallelMaxSearch extends SensorSearchEngine implements Runnable {
    private double maxValue = -Double.MAX_VALUE;

    public ParallelMaxSearch(double[] sensorData, int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        double max = -Double.MAX_VALUE;
        for (int i = startIdx; i < endIdx; i++) {
            if (sensorData[i] > max) {
                max = sensorData[i];
            }
        }
        return max;
    }

    @Override
    public void run() {
        this.maxValue = processGrid();
    }

    public double getMaxValue() {
        return maxValue;
    }
}