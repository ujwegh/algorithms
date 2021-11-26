/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 1;
        String champ = null;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (champ == null) {
                champ = s;
            }
            if (StdRandom.bernoulli((double) 1 / i)) {
                champ = s;
            }
            i++;
        }
        StdOut.println(champ);
    }
}
