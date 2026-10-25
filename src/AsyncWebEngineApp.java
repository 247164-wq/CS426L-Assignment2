public class AsyncWebEngineApp {
    public static void main(String[] args) {
        System.out.println("Master application started on thread: " + Thread.currentThread().getName());

        TelemetryThread telemetryWorker = new TelemetryThread();
        telemetryWorker.setName("Telemetry-Worker");

        LogProcessorTask logTask = new LogProcessorTask();
        Thread logWorker = new Thread(logTask, "Log-Worker");

        System.out.println("Spawning concurrent execution threads...");
        telemetryWorker.start();
        logWorker.start();

        System.out.println("Active Threads Spawned. Telemetry State: " + telemetryWorker.getState() + ", Log State: " + logWorker.getState());
    }
}