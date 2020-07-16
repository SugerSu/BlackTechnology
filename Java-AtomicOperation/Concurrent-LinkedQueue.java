import java.util.concurrent.atomic.AtomicReference;

class LinkedQueue<E> {
    private static class Node<E> {
        final E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }
    }

    private final Node<E> dummy = new Node<E>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<Node<E>>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<Node<E>>(dummy);

    public boolean put(E item) {
        Node<E> newNode = new Node<E>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext != null) {
                    // the queue is at intermediate state
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    // the queue is at quiescent state
                    if (curTail.next.compareAndSet(null, newNode)) {
                        // do not care the result to this operation.
                        // Each thread first check it and process it.
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }

    public E getHead() {
        return head.get().next.get() == null ? null : head.get().next.get().item;
    }

    public E getTail() {
        return tail.get().item;
    }
}

class TestConcurrentLinkedQueue {
    public static void main(String[] args) {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.put("Hello");
        System.out.println(queue.getHead());
        System.out.println(queue.getTail());
        queue.put("Atomic");
        System.out.println(queue.getHead());
        System.out.println(queue.getTail());
        queue.put("Queue!");
        System.out.println(queue.getHead());
        System.out.println(queue.getTail());

    }
}