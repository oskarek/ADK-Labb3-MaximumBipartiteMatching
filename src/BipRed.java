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
        graph.setS(x+y+1);
        graph.setT(x+y+2);
        // Läs in kanterna
        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            graph.addEdge(a,b,1);
        }
        return graph;
    }

        void writeMaxFlowSolution(GraphType graph) {
        // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
        // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
        // skickade iväg)
        io.println(x + " " + y);
        int s = graph.getS();
        int t = graph.getT();
        int e = graph.edges().size();
        ArrayList<String> matchings = new ArrayList<>();

        for(GraphEdgeType edge : graph.edges()){
            int from = edge.getFrom();
            int to = edge.getTo();
            if(edge.getFlowCapacity() > 0 && from != s && from != t && to != s && to != t){
                matchings.add(from + " " + to);
            }
        }
        io.println(matchings.size());
        for(String str : matchings)
            io.println(str);
    }

    public Graph matchingToFlow(Graph graph) {
        int s = graph.getS();
        graph.addVertex(s);
        for(int i = 1; i<=x; i++) {
            graph.addEdge(s, i, 1);
        }

        int t = graph.getT();
        graph.addVertex(t);
        for(int i = x+1; i<=x+y; i++) {
            graph.addEdge(i, t, 1);
        }

        return graph;
    }

    BipRed() {
        io = new Kattio(System.in, System.out);

        //Graph graph = readBipartiteGraph();

        Graph graph = new Graph(5);
        graph.setT(7);
        graph.setS(6);
        x = 2; y = 3;
        graph.addEdge(1,3,1);
        graph.addEdge(1,4,1);
        graph.addEdge(2,3,1);
        graph.addEdge(2,5,1);

        Graph newGraph = matchingToFlow(graph);

        FlowSolver fs = new FlowSolver();
        GraphType maxGraph = fs.edmonds_Karp(newGraph);

        writeMaxFlowSolution(maxGraph);

        //Kom ihåg att stänga ner Kattio-klassen
        io.close();
    }

    public static void main(String args[]) {
        new BipRed();
    }
}