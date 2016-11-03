import java.util.*;
import java.util.stream.Stream;

/**
 * Class to calculate the maximum flow in a graph.
 * Created by oskarek on 2016-11-02.
 */
public class FlowSolver {
    Kattio io;

    public static void main(String[] args) {
        new FlowSolver();
    }

    FlowSolver() {
        io = new Kattio(System.in, System.out);

        io.println(123456);

        GraphType graph = readCapacityGraph();

        GraphType maxGraph = edmonds_Karp(graph);

        writeFlowGraph(maxGraph);
    }

    GraphType edmonds_Karp(GraphType capacityGraph) {
        int vCount = capacityGraph.vertexCount();
        GraphType flowGraph = new Graph(vCount);
        flowGraph.setS(capacityGraph.getS());
        flowGraph.setT(capacityGraph.getT());
        GraphType residualGraph = new Graph(vCount);
        residualGraph.setS(capacityGraph.getS());
        residualGraph.setT(capacityGraph.getT());
        for (GraphEdgeType edge : capacityGraph.edges()) {
            residualGraph.addEdge(edge.getFrom(), edge.getTo(), edge.getValue());
            //residualGraph.addEdge(edge.getTo(), edge.getFrom(), edge.reversed().getValue());

            flowGraph.addEdge(edge.getFrom(), edge.getTo(), 0);
        }

        Optional<LinkedList<GraphEdgeType>> path;
        // While loop ends when no path can be found from s to t in the residual graph.
        while ((path = BFS_path(residualGraph,capacityGraph.getS(), capacityGraph.getT())).isPresent()) {
            List<GraphEdgeType> p = path.get();
            // r is the minimum capacity of the edges in p
            int r = p.stream()
                    .map(GraphEdgeType::getValue)
                    .min(Integer::compareTo)
                    .get();

            for (GraphEdgeType edge : p) {
                int u = edge.getFrom(); int v = edge.getTo();
                GraphEdgeType fuv = flowGraph.getEdge(u,v).get();
                int fuvVal = fuv.getValue();
                fuv.setValue(fuvVal + r);
                fuv.reversed().setValue(-fuvVal);

                GraphEdgeType cuv = capacityGraph.getEdge(u,v).get();
                int cuvVal = cuv.getValue();
                GraphEdgeType cfuv = residualGraph.getEdge(u,v).get();
                cfuv.setValue(cuvVal-fuvVal);
                cfuv.reversed().setValue(cuv.reversed().getValue() - fuv.reversed().getValue());
                if (cfuv.getValue() == 0) residualGraph.removeEdge(cfuv.getFrom(),cfuv.getTo());
                if (cfuv.reversed().getValue() == 0) residualGraph.removeEdge(cfuv.getTo(),cfuv.getFrom());
            }
        }
        return flowGraph;
    }

    /**
     * Get the shortest path between two vertices in a graph, if it exists. Optional.empty() otherwise.
     * @param graph the graph to search for the path in.
     * @param from The starting vertex.
     * @param to The target vertex.
     * @return A list with the vertices in the path, beginning with start,
     *         or Optinal.empty() if no such path exists.
     */
    private Optional<LinkedList<GraphEdgeType>> BFS_path(GraphType graph, int from, int to) {
        Queue<LinkedList<GraphEdgeType>> queue = new LinkedList<>();
        LinkedList<GraphEdgeType> l = new LinkedList<>();
        l.addAll(graph.edgesForVertex(from));
        queue.add(l);

        while (!queue.isEmpty()) {
            LinkedList<GraphEdgeType> path = queue.poll();
            if (path.isEmpty()) continue;

            GraphEdgeType edge = path.getLast();
            int endVertex = edge.getTo();
            if (endVertex == to) return Optional.of(path);

            for (GraphEdgeType newEdge : graph.edgesForVertex(endVertex)) {
                LinkedList<GraphEdgeType> newPath = new LinkedList<>(path);
                newPath.add(newEdge);
                queue.add(newPath);
            }
        }
        return Optional.empty();
    }

    private GraphType readCapacityGraph(){
        Kattio io = new Kattio(System.in, System.out);
        int v = 4;// io.getInt();
        int s = 1;// io.getInt();
        int t = 4;// io.getInt();
        int e = 5; // io.getInt();
        GraphType graph = new Graph(v);
        graph.setS(s);
        graph.setT(t);

        graph.addEdge(1,2,1);
        graph.addEdge(1,3,2);
        graph.addEdge(2,4,2);
        graph.addEdge(3,2,2);
        graph.addEdge(3,4,1);
//        for(int i = 0; i<e; i++){
//            int a = io.getInt();
//            int b = io.getInt();
//            int f = io.getInt();
//            graph.addEdge(a,b,f);
//        }
        return graph;
    }

    private void writeFlowGraph(GraphType graph) {
        int vertexCount = graph.vertexCount();
        io.println(vertexCount);
        int s = graph.getS(), t = graph.getT();
        int totFlow = 0;
        for (GraphEdgeType sEdge : graph.edgesForVertex(s))
            totFlow += sEdge.getValue();
        io.write(s + " " + t + " " + totFlow);

        int positiveEdgesCount = 0;
        LinkedList<GraphEdgeType> positiveEdges = new LinkedList<>();
        for (GraphEdgeType edge : graph.edges()) {
            if (edge.getValue() > 0) {
                positiveEdgesCount += 1;
                positiveEdges.add(edge);
            }
        }

        io.println(positiveEdgesCount);

        for (GraphEdgeType e : positiveEdges)
            io.println(e.getFrom() + " " + e.getTo() + " " + e.getValue());
    }

    private void writeFlowGraph_ROBERT_HATAR(GraphType graph) {
        int vertexCount = graph.vertexCount();
        io.println(vertexCount);
        int s = graph.getS(), t = graph.getT();
        int totFlow = graph.edgesForVertex(s).stream()
                .mapToInt(GraphEdgeType::getValue)
                .sum();
        io.write(s + " " + t + " " + totFlow);

        Stream<GraphEdgeType> positiveFlowEdges = graph.edges().stream()
                .filter(e -> e.getValue() > 0);

        io.println(positiveFlowEdges.count());

        positiveFlowEdges
                .forEach(e -> io.println(e.getFrom() + " " + e.getTo() + " " + e.getValue()));
    }
}
