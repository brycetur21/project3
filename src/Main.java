import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        String[] algorithms = {"FCFS", "RR", "NPSJF", "PSJF"};
        String current_algorithm = null;

        int number_of_cores = 1; // default number of cores
        int time_quantum = 0; // time quantum for round robin

        int num_args = args.length;

        // case where we have "-S <num>" or "-C <num>"
        // note that "-S 2" is not possible here
        if (num_args == 2) {

            switch (args[0]) {
                case "-S" -> {
                    int algorithm_num = 0;

                    // parse the algorithm number as long as it is not a char
                    try {
                        algorithm_num = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ex) {
                        System.out.println("char entered instead of number");
                    }

                    if (algorithm_num > 0 && algorithm_num < 5 && algorithm_num != 2)
                        current_algorithm = algorithms[algorithm_num - 1];
                }
                case "-C" -> {

                    // parse the number of cores as long as it is not a char
                    try {
                        number_of_cores = Integer.parseInt(args[0]);
                    } catch (NumberFormatException ex) {
                        System.out.println("char entered instead of number");
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
                    System.out.println("char entered instead of number");
                }
            }
        }
        // case where we have "-S 1 -C 2" or "-C 2 -S 1"
        else if (num_args == 4) {

            // first two arguments
            if (Objects.equals(args[0], "-S") || Objects.equals(args[0], "-C")) {
                if (args[0].equals("-S")) {
                    int algorithm_num = 0;

                    // parse algorithm number as long as it is not a char
                    try{
                        algorithm_num = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ex) {
                        System.out.println("char entered instead of number");
                    }

                    if (algorithm_num > 0 && algorithm_num < 5 && algorithm_num != 2)
                        current_algorithm = algorithms[algorithm_num-1];
                }
                else if (args[0].equals("-C")) {

                    // parse the number of cores as long as it is not a char
                    try {
                        number_of_cores = Integer.parseInt(args[0]);
                    } catch (NumberFormatException ex) {
                        System.out.println("char entered instead of number");
                    }
                }
            }

            // second two arguments
            if (Objects.equals(args[2], "-S") || Objects.equals(args[2], "-C")) {
                if (args[2].equals("-S")) {
                    int algorithm_num = 0;

                    // parse algorithm number as long as it is not a char
                    try{
                        algorithm_num = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ex) {
                        System.out.println("char entered instead of number");
                    }

                    if (algorithm_num > 0 && algorithm_num < 5 && algorithm_num != 2)
                        current_algorithm = algorithms[algorithm_num-1];

                }
                else if (args[2].equals("-C")) {

                    // parse the number of cores as long as it is not a char
                    try {
                        number_of_cores = Integer.parseInt(args[0]);
                    } catch (NumberFormatException ex) {
                        System.out.println("char entered instead of number");
                    }
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
                    System.out.println("char entered instead of number");
                }

                // check that the algorithm number is 2
                if (algorithm_num == 2)
                    current_algorithm = algorithms[1];

                // parse time quantum as long as it is not a char
                try {
                    time_quantum = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {
                    System.out.println("char entered instead of number");
                }

                // parse the number of cores as long as it is not a char
                try {
                    number_of_cores = Integer.parseInt(args[4]);
                } catch (NumberFormatException ex) {
                    System.out.println("char entered instead of a number");
                }
            }

            if (Objects.equals(args[0], "-C") || Objects.equals(args[2], "-S")) {
                int algorithm_num = 0;

                // parse algorithm number as long as it is not a char
                try {
                    algorithm_num = Integer.parseInt(args[3]);
                } catch (NumberFormatException ex) {
                    System.out.println("char entered instead of number");
                }

                // check that the algorithm number is 2
                if (algorithm_num == 2)
                    current_algorithm = algorithms[1];

                // parse time quantum as long as it is not a char
                try {
                    time_quantum = Integer.parseInt(args[4]);
                } catch (NumberFormatException ex) {
                    System.out.println("char entered instead of number");
                }

                // parse the number of cores as long as it is not a char
                try {
                    number_of_cores = Integer.parseInt(args[1]);
                } catch (NumberFormatException ex) {
                    System.out.println("char entered instead of a number");
                }
            }
        }

        System.out.println("Algorithm: " + current_algorithm); // algorithm produces null if invalid

        // check that the number of cores is greater than 0, but less than 5
        if (number_of_cores < 1 || number_of_cores > 4)
            System.out.println("number of cores is invalid");
        else
            System.out.println("Number of cores: " + number_of_cores);

        // check that the time quantum is 0 or greater and is bounded to 100
        if (time_quantum < 0 || time_quantum > 100)
            System.out.println("Time quantum is invalid");
        else
            System.out.println("time quantum: " + time_quantum);

    }
}