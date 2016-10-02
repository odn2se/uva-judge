import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by phand on 9/30/16.
 */
class Prob102 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        while (s.hasNextInt()) {
            Bin b1 = new Bin(s.nextInt(), s.nextInt(), s.nextInt());
            Bin b2 = new Bin(s.nextInt(), s.nextInt(), s.nextInt());
            Bin b3 = new Bin(s.nextInt(), s.nextInt(), s.nextInt());

            int minMoves = Integer.MAX_VALUE;
            String minString = "";
            for (Color c1 : Color.values()) {
                for (Color c2 : Color.values()) {
                    if (c2 == c1)
                        continue;
                    for (Color c3 : Color.values()) {
                        if (c3 == c1 || c3 == c2)
                            continue;

                        int moves = b2.get(c1) + b3.get(c1)
                                + b1.get(c2) + b3.get(c2)
                                + b1.get(c3) + b2.get(c3);

                        String order = c1.name().charAt(0) + "" + c2.name().charAt(0) + c3.name().charAt(0);
                        if (moves < minMoves || (moves == minMoves && order.compareTo(minString) < 0)) {
                            minMoves = moves;
                            minString = order;
                        }
                    }
                }
            }

            System.out.println(minString + " " + minMoves);
        }
    }

    enum Color {
        BROWN, GREEN, CLEAR
    }

    static class Bin {
        Map<Color, Integer> quantities = new HashMap<>();

        public Bin(int b, int g, int c) {
            quantities.put(Color.BROWN, b);
            quantities.put(Color.CLEAR, c);
            quantities.put(Color.GREEN, g);
        }

        public int get(Color c1) {
            return quantities.get(c1);
        }
    }
}
