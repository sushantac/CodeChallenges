package Solution1_Shifting_Array_Problem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FinalSolution {

    // Complete the rotLeft function below.
    static int[] rotLeft(int[] a, int d) {
        if(d == 0){
            return a;
        }
        int len = a.length;
        if(d >= len){
            //d = 2%5   7%5=2
            d = d%len;
        }
        

        //1 2 3 4 5
        //d = 2
        //take first section
        int[] first = new int[d];
        for(int i = 0; i < d; i++){
            first[i] = a[i];
        }
        //first 1 2

        //swap sections
        //take last section 5 - 2
        int[] last = new int[len];
        for(int i = d, j = 0; i < len; i++,j++){
            last[j] = a[i];
        }
        //last 3 4 5

        for(int i = 0, j = (len - d); i < first.length; i++, j++){
            last[j] = first[i];
        }
        
        return last;
        

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nd = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nd[0]);

        int d = Integer.parseInt(nd[1]);

        int[] a = new int[n];

        String[] aItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int aItem = Integer.parseInt(aItems[i]);
            a[i] = aItem;
        }

        int[] result = rotLeft(a, d);

        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(String.valueOf(result[i]));

            if (i != result.length - 1) {
                bufferedWriter.write(" ");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
