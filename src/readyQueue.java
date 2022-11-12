import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class readyQueue extends LinkedList<processThreads> {
    public static Semaphore readyQueueSem;

    public readyQueue(Semaphore readyQueueSem) {
        readyQueue.readyQueueSem = readyQueueSem;
    }

    void acquire() {
        //System.out.println("Sem acquired");
        readyQueueSem.acquireUninterruptibly();
    }

    void release() {
        //System.out.println("Sem acquired");
        readyQueueSem.release();
    }
}
