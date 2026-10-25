import java.util.*;
import java.util.concurrent.*;

public class InventoryCatalogManager {
    private static final int NUM_THREADS = 10;
    private static final int OPS_PER_THREAD = 50000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Starting Concurrency Benchmark ---");
        benchmarkSynchronizedMap();
        benchmarkConcurrentMap();
    }

    public static void benchmarkSynchronizedMap() throws InterruptedException {
        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
        for (int i = 0; i < 1000; i++) syncMap.put("PROD_" + i, 100);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.currentTimeMillis();

        for (int t = 0; t < NUM_THREADS; t++) {
            executor.execute(() -> {
                for (int i = 0; i < OPS_PER_THREAD; i++) {
                    String key = "PROD_" + (i % 1000);
                    synchronized (syncMap) {
                        Integer stock = syncMap.get(key);
                        syncMap.put(key, stock + 1);
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long duration = System.currentTimeMillis() - startTime;
        long totalOps = (long) NUM_THREADS * OPS_PER_THREAD;

        System.out.printf("[SynchronizedMap] Time: %d ms | Throughput: %,d ops/sec%n",
                duration, (totalOps * 1000) / Math.max(duration, 1));
    }

    public static void benchmarkConcurrentMap() throws InterruptedException {
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 1000; i++) concurrentMap.put("PROD_" + i, 100);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.currentTimeMillis();

        for (int t = 0; t < NUM_THREADS; t++) {
            executor.execute(() -> {
                for (int i = 0; i < OPS_PER_THREAD; i++) {
                    String key = "PROD_" + (i % 1000);
                    concurrentMap.compute(key, (k, v) -> v == null ? 1 : v + 1);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long duration = System.currentTimeMillis() - startTime;
        long totalOps = (long) NUM_THREADS * OPS_PER_THREAD;

        System.out.printf("[ConcurrentHashMap] Time: %d ms | Throughput: %,d ops/sec%n",
                duration, (totalOps * 1000) / Math.max(duration, 1));
    }
}