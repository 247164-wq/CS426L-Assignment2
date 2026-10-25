public class RequestQueue {
    private char[] queueArray;
    private int front;
    private int rear;
    private int count;

    public RequestQueue(int capacity) {
        queueArray = new char[capacity];
        front = 0;
        rear = -1;
        count = 0;
    }

    public void enqueue(char c) {
        if (count < queueArray.length) {
            rear = (rear + 1) % queueArray.length;
            queueArray[rear] = c;
            count++;
        }
    }

    public char dequeue() {
        if (!isEmpty()) {
            char item = queueArray[front];
            front = (front + 1) % queueArray.length;
            count--;
            return item;
        }
        return '\0';
    }

    public boolean isEmpty() {
        return count == 0;
    }
}