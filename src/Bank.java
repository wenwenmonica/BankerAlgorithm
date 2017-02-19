import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bank {
    int process;
    int resource;
    // Three essential elements: assigned resource matrix, needed resource matrix and available source
    int[][] assignedResource;
    int[][] needResource;
    int[] available;
    // Keep track of process checked or not
    boolean[] done;
    // user input for starting process
    int input;
    // Store the process which has run safely
    List<Integer> processOrder = new ArrayList<>();
    // Store unmarked processes
    Set<Integer> hs = new HashSet<>();
    boolean flag;

    // Initialize
    Bank(int process, int resource, int[][] assignedResource, int[][] needResource, int[] available, int input) {
        this.process = process;
        this.resource = resource;
        this.assignedResource = cloneArray(assignedResource);
        this.needResource = cloneArray(needResource);
        this.available = new int[available.length];
        System.arraycopy(available, 0, this.available, 0, available.length);
        this.input = input;
        done = new boolean[process];
    }

    public void run() {
        // If the starting process user input can't be satisfied, simply return
        if (!check(input, available)) {
            System.out.println(String.format("Process %d can't be satisfied, because there are not enough resources.", input));
            return;
        }

        // If starting process can run safely, return all its resources, update available resource
        done[input] = true;
        processOrder.add(input);
        for (int i = 0; i < resource; i++) {
            available[i] += assignedResource[input][i];
        }

        // Continuously check the next processes
        for (int i = 0; i < process; i++) {
            if (!done[i] && check(i, available)) {
                done[i] = true;
                processOrder.add(i);
                for (int k = 0; k < resource; k++) {
                    available[k] += assignedResource[i][k];
                }
                if (processOrder.size() == process) {
                    // All the processes have run safely
                    System.out.println("Processes can run safely in the order ");
                    for (int p : processOrder) {
                        System.out.println(String.format("%d", p));
                    }
                }
                if (hs.contains(i)) {
                    hs.remove(i);
                }
                flag = true;
            } else if (!done[i]) {
                if ((flag == false && hs.contains(i)) || processOrder.size() == 0) {
                    // Not all the processes can run safely, list the unmarked process
                    System.out.println(String.format("Starting from process %d is not safe: ", input));
                    if (!hs.isEmpty()) {
                        System.out.print("because finally process ");
                        for (int p : hs) {
                            System.out.print(String.format("%d, ", p));
                        }
                    }
                    System.out.println("will be still unmarked.");
                    return;
                }
                hs.add(i);
                flag = false;
            }
            if (i == process - 1 && processOrder.size() != process) {
                i = -1;
            }
        }
    }

    // Check whether there are enough resources for certain process
    public boolean check(int p, int[] cur) {
        for (int i = 0; i < resource; i++) {
            if (needResource[p][i] > cur[i]) {
                return false;
            }
        }
        return true;
    }

    // For the try again function in Main class, avoiding data is changed after the first running
    private int[][] cloneArray(int[][] source) {
        int[][] result = new int[source.length][source[0].length];
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, result[i], 0, source[i].length);
        }
        return result;
    }
}

