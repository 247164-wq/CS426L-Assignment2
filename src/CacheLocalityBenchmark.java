public class CacheLocalityBenchmark {
    private static final int SIZE = 2000;
    private static double[][] matrix = new double[SIZE][SIZE];

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = 1.0;
            }
        }

        // Warm-up JVM
        traverseRowMajor();
        traverseColumnMajor();

        long rowTotalTime = 0;
        long colTotalTime = 0;
        int iterations = 10;

        for (int k = 0; k < iterations; k++) {
            long start = System.nanoTime();
            traverseRowMajor();
            rowTotalTime += (System.nanoTime() - start);

            start = System.nanoTime();
            traverseColumnMajor();
            colTotalTime += (System.nanoTime() - start);
        }

        double avgRowTimeMs = (rowTotalTime / (double) iterations) / 1e6;
        double avgColTimeMs = (colTotalTime / (double) iterations) / 1e6;
        double speedup = avgColTimeMs / avgRowTimeMs;

        System.out.printf("Row-Major Avg Execution Time: %.3f ms%n", avgRowTimeMs);
        System.out.printf("Column-Major Avg Execution Time: %.3f ms%n", avgColTimeMs);
        System.out.printf("Row-Major Speedup Factor: %.2fx faster%n", speedup);
    }

    public static double traverseRowMajor() {
        double sum = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public static double traverseColumnMajor() {
        double sum = 0;
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
}