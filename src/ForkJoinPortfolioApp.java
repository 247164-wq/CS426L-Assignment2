import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int size = 10000;
        double[] assets = new double[size];
        Random rand = new Random(42);

        for (int i = 0; i < size; i++) {
            assets[i] = 100 + rand.nextDouble() * 900;
        }

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.nanoTime();
        ParallelRiskCalculator riskTask = new ParallelRiskCalculator(assets, 0, assets.length);
        double totalRisk = pool.invoke(riskTask);
        long endTime = System.nanoTime();

        System.out.printf("Total Calculated Risk Score: $%.2f%n", totalRisk);
        System.out.printf("Parallel Risk Calculation Time: %.3f ms%n", (endTime - startTime) / 1e6);

        long stressStartTime = System.nanoTime();
        PortfolioThresholdTask stressTask = new PortfolioThresholdTask(assets, 0, assets.length, 500.0);
        pool.invoke(stressTask);
        long stressEndTime = System.nanoTime();

        System.out.printf("Stress-Test Penalty Applied In: %.3f ms%n", (stressEndTime - stressStartTime) / 1e6);
    }
}