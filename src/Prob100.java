import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by phand on 9/28/16.
 */
class Main {
    private static final Map<Long, Integer> nMemo = new HashMap<>();

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (s.hasNextInt()) {
            int inputI = s.nextInt();
            int inputJ = s.nextInt();

            int i = Math.min(inputI, inputJ);
            int j = Math.max(inputI, inputJ);

            long ms = System.currentTimeMillis();
            int maxCycle = 0;
            for (int k = i; k <= j; k++) {
                maxCycle = Math.max(maxCycle, findCycleLength(k));
            }

            System.out.println(String.format("%d %d %d", inputI, inputJ, maxCycle));
        }
    }

    /*
        Find compute 3n+1 problem using memo-ization
     */
    private static int findCycleLength(long n) {
        if (nMemo.containsKey(n))
            return nMemo.get(n);

        int res = 0;
        if (n == 1)
            res = 1;
        else if (n % 2 == 0)
            res = 1 + findCycleLength(n >> 1);
        else
            res = 1 + findCycleLength(3 * n + 1);

        nMemo.put(n, res);
        return res;
    }

}
