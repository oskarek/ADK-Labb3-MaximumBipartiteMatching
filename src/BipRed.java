import java.util.ArrayList;

/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class BipRed {
    Kattio io;

    private int x;
    private int y;

    Graph readBipartiteGraph() {
        // Läs antal hörn och kanter
        x = io.getInt();
        y = io.getInt();
        int e = io.getInt();

        Graph graph = new Graph(x+y);

        // Läs in kanterna
        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            graph.addEdge(a,b,1);
        }
        return graph;
    }


    void writeFlowGraph(Graph graph) {

        int e = graph.getEdgeNum();
        int s = graph.getS();
        int t = graph.getT();

        // Skriv ut antal hörn och kanter samt källa och sänka
        io.println(graph.getNumberOfVertexes());
        io.println(s + " " + t);
        io.println(e);
        ArrayList<Graph.Vertex> vertexes = graph.getVertexes();
        for(Graph.Vertex vertex : vertexes){
            for(GraphEdgeType edge : vertex.getEdges())
                io.println(vertex.getValue() + " " + edge.getTo() + " " + edge.getValue());
        }
        io.flush();
    }


    ArrayList<String> readMaxFlowSolution() {
        // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
        // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
        // skickade iväg)
        int v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        int totflow = io.getInt();
        int e = io.getInt();
        ArrayList<String> matchings = new ArrayList<>();
        for (int i = 0; i < e; ++i) {
            // Flöde f från a till b
            int a = io.getInt();
            int b = io.getInt();
            int f = io.getInt();
            if(a != s && a != t && b != s && b != t){
                matchings.add(a + " " + b);
            }
        }
        return matchings;
    }


    void writeBipMatchSolution(ArrayList<String> solutionList) {

        // Skriv ut antal hörn och storleken på matchningen
        io.println(x + " " + y);
        io.println(solutionList.size());
        for(String m : solutionList)
            io.println(m);
    }

    BipRed() {
        io = new Kattio(System.in, System.out);

        Graph graph = readBipartiteGraph();

        MatchingToFlow mtf = new MatchingToFlow();
        Graph newGraph = mtf.translate(graph, x, y);

        writeFlowGraph(newGraph);

        ArrayList<String> solutionList = readMaxFlowSolution();

        writeBipMatchSolution(solutionList);


        //Kom ihåg att stänga ner Kattio-klassen
        io.close();
    }

    public static void main(String args[]) {
        new BipRed();
    }
}