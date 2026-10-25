public class PacketSymmetryAuditor {
    public static void main(String[] args) {
        String payload = "RADAR"; // Example symmetrical payload
        System.out.println("Auditing Network Packet Payload: " + payload);

        TransactionStack stack = new TransactionStack(payload.length());
        RequestQueue queue = new RequestQueue(payload.length());

        for (int i = 0; i < payload.length(); i++) {
            char ch = payload.charAt(i);
            stack.push(ch);
            queue.enqueue(ch);
        }

        boolean isSymmetric = true;
        while (!stack.isEmpty() && !queue.isEmpty()) {
            char popped = stack.pop();
            char dequeued = queue.dequeue();
            if (popped != dequeued) {
                isSymmetric = false;
                break;
            }
        }

        if (isSymmetric) {
            System.out.println("RESULT: Packet verified successfully! Bi-directional symmetry detected.");
        } else {
            System.out.println("RESULT: Security Alert! Packet payload is asymmetrical.");
        }
    }
}