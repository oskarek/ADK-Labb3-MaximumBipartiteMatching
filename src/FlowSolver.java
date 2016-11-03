import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by oskarek on 2016-11-02.
 */
public class FlowSolver {
    Kattio io;

    public static void main(String[] args) {
        new FlowSolver();
    }

    FlowSolver() {
        io = new Kattio(System.in, System.out);

        GraphType graph = readCapacityGraph();

        GraphType maxGraph = edmonds_Karp(graph);

        writeFlowGraph(maxGraph);
    }

    GraphType edmonds_Karp(GraphType capacityGraph) {
        int vCount = capacityGraph.vertexCount();
        GraphType flowGraph; // should be set to a graph with vCount number of vertices
        GraphType residualGraph; // so should this
        for (GraphEdgeType edge : capacityGraph.edges()) {
            // Add this edge and its reverse to the residualGraph
            // Add this edge and its reverse but with 0 capacity to the flowGraph
        }

        Optional<LinkedList<GraphEdgeType>> path = Optional.empty();
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
        int v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        int e = io.getInt();
        GraphType graph;
        for(int i = 0; i<e; i++){
            int a = io.getInt();
            int b = io.getInt();
            int f = io.getInt();
            graph.addEdge(a,b,f);
        }
        return graph;
    }

    private void writeFlowGraph(GraphType graph) {
        int vertexCount = graph.vertexCount();
        io.println(vertexCount);
        int s = graph.getS(), t = graph.getT();
        int totFlow = 0;
        for (GraphEdgeType sEdge : graph.edgesForVertex(s))
            totFlow += sEdge.value();
        io.write(s + " " + t + " " + totFlow);

        int positiveEdgesCount = 0;
        LinkedList<GraphEdgeType> positiveEdges = new LinkedList<>();
        for (GraphEdgeType edge : graph.edges()) {
            if (edge.value() > 0) {
                positiveEdgesCount += 1;
                positiveEdges.add(edge);
            }
        }

        io.println(positiveEdgesCount);

        for (GraphEdgeType e : positiveEdges)
            io.println(e.getFrom() + " " + e.getTo() + " " + e.value());
    }

    private void writeFlowGraph_ROBERT_HATAR(GraphType graph) {
        int vertexCount = graph.vertexCount();
        io.println(vertexCount);
        int s = graph.getS(), t = graph.getT();
        int totFlow = graph.edgesForVertex(s).stream()
                .mapToInt(GraphEdgeType::value)
                .sum();
        io.write(s + " " + t + " " + totFlow);

        Stream<GraphEdgeType> positiveFlowEdges = graph.edges().stream()
                .filter(e -> e.value() > 0);

        io.println(positiveFlowEdges.count());

        positiveFlowEdges
                .forEach(e -> io.println(e.getFrom() + " " + e.getTo() + " " + e.value()));
    }
}
