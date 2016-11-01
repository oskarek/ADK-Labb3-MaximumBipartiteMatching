/**
 * Main class of the program.
 * Created by oskarek on 2016-10-30.
 */
public class Main {
    public static void main(String[] args) {
        Output op = new Output();
        MatchingToFlow mtf = new MatchingToFlow();
        Graph flowgraph = mtf.translate(op.matchingInputReader());
        op.flowOutput(flowgraph);
        op.flowToMatching();
    }
}
