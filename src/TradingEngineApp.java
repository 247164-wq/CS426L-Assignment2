public class TradingEngineApp {
    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();

        // Polymorphic Array using Superclass references
        TradeEvent[] events = new TradeEvent[3];
        events[0] = new TradeEvent("Generic-Gateway", currentTime, 100.0);
        events[1] = new FIXOrderEvent("FIX-Gateway-01", currentTime, 500.0, "ORD_9912");
        events[2] = new ReutersQuoteEvent("Reuters-Feed-02", currentTime, 250.0, 249.50, 251.20);

        System.out.println("--- Dispatching Financial Events ---");
        for (TradeEvent event : events) {
            event.dispatch(); // Dynamic Method Dispatch
        }
    }
}