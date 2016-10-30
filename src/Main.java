import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Main class of the program.
 * Created by oskarek on 2016-10-30.
 */
public class Main {
    public static void main(String[] args) {

        MatchingToFlow mtf = new MatchingToFlow();

    }

    public void inputReader() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] row;
        try {
            String[] firstRow = br.readLine().split("\\s+");
            int x = Integer.parseInt(firstRow[0]);
            int y = Integer.parseInt(firstRow[1]);
            int numEdges = Integer.parseInt(br.readLine());

            for(int i = 0; i<numEdges; i++){
                row = br.readLine().split("\\s+");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
