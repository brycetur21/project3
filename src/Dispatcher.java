public class Dispatcher extends Thread {
    public int id;
    public readyQueue queue;

    public Dispatcher(int id, readyQueue queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {

        while (!queue.isEmpty()) {
            Main.dispatcher_semaphores[id].acquireUninterruptibly();

            // Pop a process off the list, send it to the CPU
            queue.acquire();
            processThreads process = queue.remove();
            process.dispatch_num = this.id;
            queue.release();

            // Run it
            CPU(process);
            sleepNow(process.max_burst);
        }
    }

    public static void sleepNow(int sleep_time) {
        for (int i = 0; i < sleep_time; i++) {
            Thread.yield();
        }
    }

    public void CPU(processThreads process) {

        while (!process.complete) {
            Main.cpu_semaphore[process.dispatch_num].acquireUninterruptibly();

            // *** update burst goal ***

            System.out.println("Dispatcher: " + this.id + " | Using CPU " + this.id);
            System.out.println("Dispatcher: " + this.id + " | Running process " + process.id);
            Main.threadSemaphore[process.id].release();
            Main.threadFinished[process.id].acquireUninterruptibly();
            Main.dispatcher_semaphores[process.dispatch_num].release();
        }

    }
}
