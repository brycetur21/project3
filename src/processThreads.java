public class processThreads extends Thread {

    public int id;
    public int max_burst;
    public int current_burst;
    public int dispatch_num;
    public boolean complete;


    public processThreads(int id, int max_burst, int current_burst, int dispatch_num, boolean complete) {
        this.id = id;
        this.max_burst = max_burst;
        this.current_burst = current_burst;
        this.dispatch_num = dispatch_num;
        this.complete = complete;
    }

    @Override
    public void run() {

        while (this.current_burst < this.max_burst - 1) {

            // Wait for a ticket
            Main.threadSemaphore[id].acquireUninterruptibly();
            for (int i = 0; i < max_burst; i++) {
                current_burst = i;
                System.out.println("Proc. Thread " + this.id + " | on Burst: " + current_burst);
            }
            System.out.println();
            this.complete = true;
            Main.threadFinished[id].release();

            // Release CPU semaphore
            Main.cpu_semaphore[dispatch_num].release();
        }
    }

    @Override
    public String toString() {
        return "ID:" + id + ", Max Burst:" + max_burst + ", Current Burst:" + current_burst;
    }
}
