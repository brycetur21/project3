import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    public static Random rand_int = new Random();
    public static Semaphore[] cpu_semaphore;
    public static Semaphore[] dispatcher_semaphores;
    public static Semaphore[] threadSemaphore;
    public static Semaphore[] threadFinished;
    public static Dispatcher[] dispatchers;
    public static processThreads[] threadsArray;
    public static readyQueue queue;
    public static int number_of_cores;

    public static void printReadyQueue(readyQueue queue) {
        System.out.println();
        System.out.println("--------------- Ready Queue ---------------");
        for (processThreads s : queue) {
            System.out.println(s.toString());
        }
        System.out.println("-------------------------------------------");
        System.out.println();
    }

    public static void createThreadsArray(int num_threads) {
        threadSemaphore = new Semaphore[num_threads];
        threadFinished = new Semaphore[num_threads];
        for (int i = 0; i < num_threads; i++) {
            threadFinished[i] = new Semaphore(0);
            threadSemaphore[i] = new Semaphore(0);
        }
        threadsArray = new processThreads[num_threads];
        for (int i = 0; i < num_threads; i++) {
            System.out.println("Main Thread | Creating process thread " + i);
            threadsArray[i] = new processThreads(i, rand_int.nextInt(1, 51), 0, 0,false);
            threadsArray[i].start();
        }
        queue.addAll(Arrays.asList(threadsArray).subList(0, num_threads));
        printReadyQueue(queue);
    }

    public static void createDispatchers(int number_of_cores) {

        dispatcher_semaphores = new Semaphore[number_of_cores];
        dispatchers = new Dispatcher[number_of_cores];
        for (int i = 0; i < dispatchers.length; i++) {
            dispatcher_semaphores[i] = new Semaphore(1);
            System.out.println("Main thread | Forking dispatcher " + i);
            dispatchers[i] = new Dispatcher(i, queue, new Semaphore(1));
            dispatchers[i].start();
        }
        System.out.println();
    }

    // this function takes care of checking the input arguments for validity as entered
    public static String[] argument_checker(String[] args) {

        // possible algorithms
        String[] algorithms = {"FCFS", "RR", "NPSJF", "PSJF"};
        String current_algorithm = null;

        int number_of_cores = 1; // default number of cores
        int time_quantum = 0; // time quantum for round robin

        int num_args = args.length;
        int invalid = 0; // variable to keep track of valid input

        // case where we have "-S <num>" or "-C <num>"
        // note that "-S 2" is not possible here because we need a time quantum
        if (num_args == 2) {

            switch (args[0]) {
                case "-S" -> {
                    int algorithm_num = 0;

                    // parse the algorithm number as long as it is not a char
                    try {
                        algorithm_num = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ex) {
                        invalid = 1;
                    }

                    if (algorithm_num > 0 && algorithm_num < 5 && algorithm_num != 2)
                        current_algorithm = algorithms[algorithm_num - 1];
                }
                case "-C" -> {

                    // parse the number of cores as long as it is not a char
                    try {
                        number_of_cores = Integer.parseInt(args[0]);
                    } catch (NumberFormatException ex) {
                        invalid = 1;
                    }
                }
            }
        }
        // case where we have "-S 2 <num>"
        else if (num_args == 3) {

            if (Objects.equals(args[0], "-S") && Objects.equals(args[1], "2")) {
                current_algorithm = algorithms[1];

                // parse the time quantum as long as it is not a char
                try {
                    time_quantum = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }
            }
        }
        // case where we have "-S 1 -C 2" or "-C 2 -S 1"
        else if (num_args == 4) {

            // first two arguments
            if (Objects.equals(args[0], "-S") && Objects.equals(args[2], "-C")) {
                int algorithm_num = 0;

                // parse algorithm as long as it is not a char
                try {
                    algorithm_num = Integer.parseInt(args[1]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }

                if (algorithm_num > 0 && algorithm_num < 5 && algorithm_num != 2)
                    current_algorithm = algorithms[algorithm_num-1];

                // parse number of cores as long as it is not a char
                try {
                    number_of_cores = Integer.parseInt(args[3]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }
            }

            // second two arguments
            if (Objects.equals(args[2], "-S") || Objects.equals(args[0], "-C")) {
                int algorithm_num = 0;

                // parse algorithm as long as it is not a char
                try {
                    algorithm_num = Integer.parseInt(args[3]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }

                if (algorithm_num > 0 && algorithm_num < 5 && algorithm_num != 2)
                    current_algorithm = algorithms[algorithm_num-1];

                // parse number of cores as long as it is not a char
                try {
                    number_of_cores = Integer.parseInt(args[1]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }
            }
        }
        // case were we have "-S 2 <num> -C 2" or "-C 2 -S 2 <num>"
        else if (num_args == 5) {

            if (Objects.equals(args[0], "-S") && Objects.equals(args[3], "-C")) {
                int algorithm_num = 0;

                // parse algorithm number as long as it is not a char
                try {
                    algorithm_num = Integer.parseInt(args[1]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }

                // check that the algorithm number is 2
                if (algorithm_num == 2)
                    current_algorithm = algorithms[1];

                // parse time quantum as long as it is not a char
                try {
                    time_quantum = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }

                // parse the number of cores as long as it is not a char
                try {
                    number_of_cores = Integer.parseInt(args[4]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }
            }

            if (Objects.equals(args[0], "-C") || Objects.equals(args[2], "-S")) {
                int algorithm_num = 0;

                // parse algorithm number as long as it is not a char
                try {
                    algorithm_num = Integer.parseInt(args[3]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }

                // check that the algorithm number is 2
                if (algorithm_num == 2)
                    current_algorithm = algorithms[1];

                // parse time quantum as long as it is not a char
                try {
                    time_quantum = Integer.parseInt(args[4]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }

                // parse the number of cores as long as it is not a char
                try {
                    number_of_cores = Integer.parseInt(args[1]);
                } catch (NumberFormatException ex) {
                    invalid = 1;
                }
            }
        }

        if (invalid == 0)
            return new String[]{current_algorithm, Integer.toString(time_quantum), Integer.toString(number_of_cores)};
        return null;
    }

    public static void main(String[] args) throws InterruptedException {

        String algorithm;
        int time_quantum;


        String[] parameters = argument_checker(args);
        if (parameters != null) {
            algorithm = parameters[0];
            time_quantum = Integer.parseInt(parameters[1]);
            number_of_cores = Integer.parseInt(parameters[2]);

            // algorithm validity
            if (algorithm != null)
                System.out.println("algorithm: " + algorithm);
            else
                System.out.println("algorithm is invalid");

            // number of cores validity
            if (number_of_cores < 1 || number_of_cores > 4)
                System.out.println("number of cores is invalid");
            else
                System.out.println("Number of cores: " + number_of_cores);

            // time quantum validity
            if (time_quantum < 0 || time_quantum > 100)
                System.out.println("Time quantum is invalid");
            else if (time_quantum < 1 && Objects.equals(algorithm, "RR"))
                System.out.println("time quantum must be greater than 0 for RR");
            else
                System.out.println("time quantum: " + time_quantum);
        }
        else
            System.out.println("invalid input, check input parameters");


        int num_threads = 15;

        System.out.println("Number of threads to fork: " + num_threads);
        System.out.println();

        // Create a readyQueue
        queue = new readyQueue(new Semaphore(1));

        // Create CPU locks and populate
        cpu_semaphore = new Semaphore[number_of_cores];
        for (int i = 0; i < number_of_cores; i++) {
            cpu_semaphore[i] = new Semaphore(1);
        }

        // Create threads
        createThreadsArray(num_threads);

        // Create dispatchers
        createDispatchers(number_of_cores);

        // Join threads
        for (processThreads threads : threadsArray) {
            threads.join();
        }


    }
}