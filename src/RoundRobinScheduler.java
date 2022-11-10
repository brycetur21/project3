public class RoundRobinScheduler {
    private int time_quantum;
    Dispatcher cpu_dispatcher; // dispatcher object
    ReadyQueue ready_queue; // ready queue object

    // constructor
    public RoundRobinScheduler(ReadyQueue rq, Dispatcher dp, int tq) {
        this.cpu_dispatcher = dp;
        this.ready_queue = rq;
        this.time_quantum = tq;
    }

    // algorithm
    public void rr_algorithm() {
        // grab the next task from the ready queue along with its burst time

        // allow the task to run for the time quantum specified

        // place the task back in the ready queue if not finished

        // grab the next task and continue this cycle until all tasks are finished
    }

    // getter for time quantum
    public int get_time_quantum() {
        return this.time_quantum;
    }
}
