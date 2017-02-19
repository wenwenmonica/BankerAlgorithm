import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of processes: ");
        int process = Integer.parseInt(sc.nextLine().trim());
        System.out.println("Enter the number of resources: ");
        int resource = Integer.parseInt(sc.nextLine().trim());
        //System.out.println();

        // Enter two matrix
        int[][] assignedResource = initializeData(process, resource, sc, "assigned");
        //System.out.println();
        int[][] needResource = initializeData(process, resource, sc, "needed");
        //System.out.println();

        // Enter available resources
        int[] available = new int[resource];
        System.out.println(String.format("Please enter %d numbers, ", resource) +
                "every number should be separated by whitespace: ");
        System.out.println("(these numbers are the available numbers for every resource)");
        for (int i = 0; i < resource; i++) {
            available[i] = sc.nextInt();
        }
        System.out.println();

        runProcess(process, resource, sc, assignedResource, needResource, available);
        System.out.println();

        System.out.println("Would you want to try another starting process? Y / N");
        //System.out.println();
        if (sc.next().equalsIgnoreCase("Y")) {
            runProcess(process, resource, sc, assignedResource, needResource, available);
        }
    }

    private static int[][] initializeData(int process, int resource, Scanner sc, String type) {
        int[][] inputMatrix = new int[process][resource];
        System.out.println(String.format("Let's enter the %s resource matrix row by row,", type));
        for (int i = 0; i < process; i++) {
            System.out.println(String.format("Please enter %d numbers, ", resource) +
                    "every number should be separated by whitespace: ");
            System.out.println(String.format("(these %d %s resource are for process %d)", resource, type, i));
            String[] elems = sc.nextLine().split(" ");
            if (elems.length != resource) {
                throw new IllegalArgumentException(
                        String.format("The %d inputs does not match the defined number of resource %d!",
                                elems.length, resource));
            }

            for (int j = 0; j < resource; j++) {
                inputMatrix[i][j] = Integer.parseInt(elems[j]);
            }
        }
        return inputMatrix;
    }

    private static void runProcess(int process, int resource, Scanner sc, int[][] assignedResource,
                                   int[][] needResource, int[] available) {
        // Enter start process
        System.out.println("Finally, please enter the process number which you want to start run firstly, " +
                String.format("the range must be from %d to %d: ", 0, process - 1));
        int input = sc.nextInt();
        System.out.println();
        // run
        System.out.println("The running results: ");
        Bank bank = new Bank(process, resource, assignedResource, needResource, available, input);
        bank.run();
    }
}
