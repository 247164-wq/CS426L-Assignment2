import java.util.Random;

public class SensorGridMonitorApp {
    public static void main(String[] args) throws InterruptedException {
        int totalElements = 50000;
        double[] sensorGrid = new double[totalElements];
        Random rand = new Random();

        for (int i = 0; i < totalElements; i++) {
            sensorGrid[i] = 10.0 + (rand.nextDouble() * 90.0);
        }

        int quarter = totalElements / 4;
        ParallelMinSearch[] minTasks = new ParallelMinSearch[4];
        ParallelMaxSearch[] maxTasks = new ParallelMaxSearch[4];
        Thread[] threads = new Thread[8];

        long startTime = System.nanoTime();

        for (int i = 0; i < 4; i++) {
            int start = i * quarter;
            int end = (i == 3) ? totalElements : (i + 1) * quarter;

            minTasks[i] = new ParallelMinSearch(sensorGrid, start, end);
            maxTasks[i] = new ParallelMaxSearch(sensorGrid, start, end);

            threads[i] = new Thread(minTasks[i]);
            threads[i + 4] = new Thread(maxTasks[i]);

            threads[i].start();
            threads[i + 4].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        double globalMin = Double.MAX_VALUE;
        double globalMax = -Double.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            if (minTasks[i].getMinValue() < globalMin) globalMin = minTasks[i].getMinValue();
            if (maxTasks[i].getMaxValue() > globalMax) globalMax = maxTasks[i].getMaxValue();
        }

        long endTime = System.nanoTime();

        System.out.printf("Global Minimum Ambient Temperature: %.2f°C%n", globalMin);
        System.out.printf("Global Peak Vibration Spike: %.2f%n", globalMax);
        System.out.printf("Parallel Reduction Execution Time: %.3f ms%n", (endTime - startTime) / 1e6);
    }
}