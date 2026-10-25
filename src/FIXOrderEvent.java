public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;

    public FIXOrderEvent(String senderId, long timestamp, double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }

    @Override
    public void dispatch() {
        double totalCost = basePrice * (1 + 0.0015); // 0.15% transaction fee
        System.out.printf("[FIX ORDER] Client Order ID: %s | Sender: %s | Total Price (incl. 0.15%% fee): $%.2f%n",
                clOrdId, senderId, totalCost);
    }
}