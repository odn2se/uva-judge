import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by phand on 9/29/16.
 */
class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        while (s.hasNextInt()) {
            int numBoxes = s.nextInt();
            int numDimensions = s.nextInt();

            ArrayList<Box> boxes = new ArrayList<>();

            for (int i = 0; i < numBoxes; i++) {
                ArrayList<Integer> dimensions = new ArrayList<>(numDimensions);
                for (int dim = 0; dim < numDimensions; dim++) {
                    dimensions.add(s.nextInt());
                }

                boxes.add(new Box(dimensions));
            }

            ArrayList<Box> sortedBoxes = new ArrayList<>(boxes);
            Collections.sort(sortedBoxes, new Comparator<Box>() {
                @Override
                public int compare(Box o1, Box o2) {
                    for (int i = 0; i < o1.dims.size(); i++) {
                        if (o1.dims.get(i) < o2.dims.get(i))
                            return -1;
                        if (o1.dims.get(i) > o2.dims.get(i))
                            return 1;
                    }

                    return 0;
                }
            });

            Map<Integer, Integer> originalIndexMapping = new HashMap<>();
            for (int i = 0; i < numBoxes; i++) {
                Box b = sortedBoxes.get(i);
                originalIndexMapping.put(i, boxes.indexOf(b));
            }

            int[][] dp = new int[numBoxes][numBoxes];
            int[] backMaster = new int[numBoxes];
            int[] maxArrMaster = new int[numBoxes];
            int maxMaster = 0;
            for (int i = 0; i < numBoxes; i++) {
                int[] back = new int[numBoxes];
                int maxForI = 1;
                for (int j = i; j < numBoxes; j++) {
                    if (i == j)
                        dp[i][j] = 1;
                    else {
                        int max = 1;
                        int b = j;
                        for (int k = i; k < j; k++) {
                            Box bj = sortedBoxes.get(j);
                            Box bk = sortedBoxes.get(k);

                            int lengthWithK = 1;
                            if (bk.fitsInside(bj))
                                lengthWithK = dp[i][k] + 1;

                            if (max < lengthWithK) {
                                b = k;
                                max = lengthWithK;
                            }
                        }
                        dp[i][j] = max;
                        back[j] = b;
                        maxForI = Math.max(maxForI, max);
                    }
                }

                if (maxMaster < maxForI) {
                    maxMaster = maxForI;
                    backMaster = back;
                    maxArrMaster = dp[i];
                }
            }

            int i = 0;
            for (i = 0; i < maxArrMaster.length; i++) {
                if (maxArrMaster[i] == maxMaster)
                    break;
            }
            System.out.println(maxMaster);

            ArrayList<Integer> result = new ArrayList<>();
            result.add(originalIndexMapping.get(i) + 1);
            while (backMaster[i] != i) {
                i = backMaster[i];
                result.add(originalIndexMapping.get(i) + 1);
            }
            Collections.reverse(result);
            System.out.println(String.join(" ", result.stream().map(e -> "" + e).collect(Collectors.toList())));
        }
    }


    static class Box {
        final ArrayList<Integer> dims;

        public Box(ArrayList<Integer> dimensions) {
            this.dims = dimensions;

            Collections.sort(dims);
        }

        public String toString() {
            return String.join(",", dims.stream().map(e -> "" + e).collect(Collectors.toList()));
        }

        public boolean fitsInside(Box b) {
            for (int i = 0; i < this.dims.size(); i++) {
                if (this.dims.get(i) >= b.dims.get(i))
                    return false;
            }

            return true;
        }
    }
}
