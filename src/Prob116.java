import java.util.Scanner;

/**
 * Created by phand on 9/30/16.
 */

/* Sample Case

5 6
3 4 1 2 8 6
6 1 8 2 7 4
5 9 3 9 9 5
8 4 1 3 2 6
3 7 2 8 6 4

5 6
3 4 1 2 8 6
6 1 8 2 7 4
5 9 3 9 9 5
8 4 1 3 2 6
3 7 2 1 2 3

2 2
9 10 9 10


3 3
9 9 0
9 9 9
0 0 -1

3 3
9 0  0
9 9 9
0 0 -1

4 4
-1 -1 0 0
0 1 9 0
0 -1 1 -5
0 1 9 0
 */
class Prob116 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (s.hasNextInt()) {
            int rows = s.nextInt();
            int cols = s.nextInt();
            int[] pathCosts = new int[rows];

            int[][] costs = new int[rows][cols];
            for (int r = 0; r < rows; r++) {
                pathCosts[r] = 0;
                for (int c = 0; c < cols; c++) {
                    costs[r][c] = s.nextInt();
                }
            }

            String[] pathStrs = new String[rows];

            for (int c = 0; c < cols; c++) {
                int[] newPathCosts = new int[rows];
                String[] newPaths = new String[rows];
                for (int r = 0; r < rows; r++) {
                    if (c == 0) {
                        newPathCosts[r] = costs[r][c];
                        newPaths[r] = "" + (r + 1);
                    } else {
                        int aboveLeft = (r - 1 + rows) % rows;
                        int left = r;
                        int belowLeft = (r + 1) % rows;

                        int minPathProg = pathCosts[aboveLeft];
                        int minProg = aboveLeft;
                        if (pathCosts[left] < minPathProg || (pathCosts[left] == minPathProg && left < minProg)) {
                            minPathProg = pathCosts[left];
                            minProg = left;
                        }
                        if (pathCosts[belowLeft] < minPathProg || (pathCosts[belowLeft] == minPathProg && belowLeft < minProg)) {
                            minPathProg = pathCosts[belowLeft];
                            minProg = belowLeft;
                        }

                        newPathCosts[r] = costs[r][c] + minPathProg;
                        newPaths[r] = pathStrs[minProg] + " " + (r + 1);
                    }
                }

                pathCosts = newPathCosts;
                pathStrs = newPaths;
            }

            int minCost = pathCosts[0];
            String minStr = pathStrs[0];

            for (int i = 1; i < pathCosts.length; i++) {
                if (pathCosts[i] < minCost || (pathCosts[i] == minCost && pathStrs[i].compareTo(minStr) < 0)) {
                    minCost = pathCosts[i];
                    minStr = pathStrs[i];
                }
            }

            System.out.println(minStr);
            System.out.println(minCost);
        }
    }
}
