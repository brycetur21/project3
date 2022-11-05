import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        String[] algorithms = {"FCFS", "RR", "NPSJF", "PSJF"};
        String current_algorithm = null;

        int number_of_cores = 1; // default number of cores
        int time_quantum = 0;

        int num_args = args.length;

        // case where we have "-S <num>" or "-C <num>"
        // note that "-S 2" is not possible here
        if (num_args == 2) {

            switch (args[0]) {
                case "-S" -> {
                    int algorithm_num = Integer.parseInt(args[1]);
                    if (algorithm_num > 0 && algorithm_num < 5 && algorithm_num != 2)
                        current_algorithm = algorithms[algorithm_num - 1];
                    else
                        System.out.println("invalid algorithm number");
                }
                case "-C" -> {
                    number_of_cores = Integer.parseInt(args[0]);
                    if (number_of_cores < 1 || number_of_cores > 4)
                        System.out.println("invalid number of cores");
                }
                default -> System.out.println("invalid input");
            }
        }
        // case where we have "-S 2 <num>"
        else if (num_args == 3) {

            if (Objects.equals(args[0], "-S") && Objects.equals(args[1], "2")) {
                current_algorithm = algorithms[1];
                time_quantum = Integer.parseInt(args[2]);

                if (time_quantum < 1)
                    System.out.println("time quantum invalid");
            }
            else
                System.out.println("invalid input");
        }
        // case where we have "-S 1 -C 2" or "-C 2 -S 1"
        else if (num_args == 4) {

            // first two arguments
            if (Objects.equals(args[0], "-S") || Objects.equals(args[0], "-C")) {
                if (args[0].equals("-S")) {
                    int algorithm_num = Integer.parseInt(args[1]);

                    if (algorithm_num > 0 && algorithm_num < 5 && algorithm_num != 2)
                        current_algorithm = algorithms[algorithm_num-1];
                    else
                        System.out.println("invalid algorithm number");
                }
                else if (args[0].equals("-C")) {
                    number_of_cores = Integer.parseInt(args[1]);

                    if (number_of_cores < 1 || number_of_cores > 4)
                        System.out.println("invalid number of cores");
                }
            }
            else
                System.out.println("invalid input");

            // second two arguments
            if (Objects.equals(args[2], "-S") || Objects.equals(args[2], "-C")) {
                if (args[2].equals("-S")) {
                    int algorithm_num = Integer.parseInt(args[3]);

                    if (algorithm_num > 0 && algorithm_num < 5 && algorithm_num != 2)
                        current_algorithm = algorithms[algorithm_num-1];
                    else
                        System.out.println("invalid algorithm number");
                }
                else if (args[2].equals("-C")) {
                    number_of_cores = Integer.parseInt(args[3]);

                    if (number_of_cores < 1 || number_of_cores > 4)
                        System.out.println("invalid number of cores");
                }
            }
            else
                System.out.println("invalid input");
        }
        // case were we have "-S 2 <num> -C 2" or "-C 2 -S 2 <num>"
        else if (num_args == 5) {

            if (Objects.equals(args[0], "-S") || Objects.equals(args[0], "-C")) {
                if (args[0].equals("-S") && args[1].equals("2")) {
                    current_algorithm = algorithms[1];
                    time_quantum = Integer.parseInt(args[2]);

                    if (time_quantum < 1)
                        System.out.println("time quantum invalid");
                }
                else if (args[0].equals("-C")) {
                    number_of_cores = Integer.parseInt(args[2]);

                    if (number_of_cores < 1 || number_of_cores > 4)
                        System.out.println("invalid number of cores");
                }
            }
            else
                System.out.println("invalid input");

            if (Objects.equals(args[2], "-S") || Objects.equals(args[3], "-C")) {
                if (args[2].equals("-S") && args[3].equals("2")) {
                    time_quantum = Integer.parseInt(args[2]);

                    if (time_quantum < 1)
                        System.out.println("time quantum invalid");
                }
                else if (args[3].equals("-C")) {
                    number_of_cores = Integer.parseInt(args[4]);

                    if (number_of_cores < 1 || number_of_cores > 4)
                        System.out.println("invalid number of cores");
                }
            }
            else
                System.out.println("invalid input");
        }
        else
            System.out.println("invalid input");

        System.out.println("Algorithm: " + current_algorithm);
        System.out.println("Time Quantum: " + time_quantum);
        System.out.println("Number of cores: " + number_of_cores);
    }
}