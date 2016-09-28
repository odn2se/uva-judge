import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by phand on 9/28/16.
 */
class Prob101 {
    private static ArrayList<LinkedList<Integer>> stacks = new ArrayList();

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int numBlocks = s.nextInt();

        for (int i = 0; i < numBlocks; i++) {
            LinkedList<Integer> blocks = new LinkedList<>();
            blocks.add(i);
            stacks.add(blocks);
        }

        Pattern aOntoB = Pattern.compile("move (\\d+) onto (\\d+)");
        Pattern aOverB = Pattern.compile("move (\\d+) over (\\d+)");
        Pattern aPileOntoB = Pattern.compile("pile (\\d+) onto (\\d+)");
        Pattern aPileOverB = Pattern.compile("pile (\\d+) over (\\d+)");

        while (s.hasNextLine()) {
            String line = s.nextLine().trim();
            if ("quit".equals(line))
                break;

            Matcher m = aOntoB.matcher(line);
            if (m.matches()) {
                int a = Integer.parseInt(m.group(1));
                int b = Integer.parseInt(m.group(2));

                int aPos = whereIs(a);
                int bPos = whereIs(b);
                if (aPos == bPos)
                    continue;
                List<Integer> aStack = stacks.get(aPos);
                List<Integer> bStack = stacks.get(bPos);
                int lastEvaled;

                do {
                    lastEvaled = bStack.remove(bStack.size() - 1);
                    if (lastEvaled == b)
                        bStack.add(b);
                    else
                        stacks.get(lastEvaled).add(lastEvaled);
                } while (lastEvaled != b);

                do {
                    lastEvaled = aStack.remove(aStack.size() - 1);
                    if (lastEvaled == a)
                        bStack.add(a);
                    else
                        stacks.get(lastEvaled).add(lastEvaled);
                } while (lastEvaled != a);
            }

            m = aOverB.matcher(line);
            if (m.matches()) {
                int a = Integer.parseInt(m.group(1));
                int b = Integer.parseInt(m.group(2));

                int aPos = whereIs(a);
                int bPos = whereIs(b);
                if (aPos == bPos)
                    continue;
                List<Integer> aStack = stacks.get(aPos);
                List<Integer> bStack = stacks.get(bPos);
                int lastEvaled;
                do {
                    lastEvaled = aStack.remove(aStack.size() - 1);
                    if (lastEvaled == a)
                        bStack.add(a);
                    else
                        stacks.get(lastEvaled).add(lastEvaled);
                } while (lastEvaled != a);
            }

            m = aPileOntoB.matcher(line);
            if (m.matches()) {
                int a = Integer.parseInt(m.group(1));
                int b = Integer.parseInt(m.group(2));

                int aPos = whereIs(a);
                int bPos = whereIs(b);
                if (aPos == bPos)
                    continue;
                List<Integer> aStack = stacks.get(aPos);
                List<Integer> bStack = stacks.get(bPos);

                int lastEvaled;
                do {
                    lastEvaled = bStack.remove(bStack.size() - 1);
                    if (lastEvaled == b)
                        bStack.add(b);
                    else
                        stacks.get(lastEvaled).add(lastEvaled);
                } while (lastEvaled != b);

                int aInAStack = aStack.indexOf(a);
                while (aStack.size() > aInAStack) {
                    bStack.add(aStack.remove(aInAStack));
                }
            }

            m = aPileOverB.matcher(line);
            if (m.matches()) {
                int a = Integer.parseInt(m.group(1));
                int b = Integer.parseInt(m.group(2));

                int aPos = whereIs(a);
                int bPos = whereIs(b);
                if (aPos == bPos)
                    continue;
                List<Integer> aStack = stacks.get(aPos);
                List<Integer> bStack = stacks.get(bPos);

                int aInAStack = aStack.indexOf(a);
                while (aStack.size() > aInAStack) {
                    bStack.add(aStack.remove(aInAStack));
                }
            }
        }

        for (int i = 0; i < stacks.size(); i++) {
            System.out.print(i + ":");
            for (int el : stacks.get(i))
                System.out.print(" " + el);
            System.out.println();
        }
    }

    private static int whereIs(int a) {
        for (int i = 0; i < stacks.size(); i++)
            if (stacks.get(i).contains(a))
                return i;

        return -1;
    }
}
