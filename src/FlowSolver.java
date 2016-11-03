import java.util.*;

/**
 * Created by oskarek on 2016-11-02.
 */
public class FlowSolver {
    public static void main(String[] args) {
        new FlowSolver();
    }

    FlowSolver() {
        GraphType graph = readCapacityGraph();
        GraphType maxGraph = edmonds_Karp(graph);

    }

    GraphType edmonds_Karp(GraphType graph) {
        int vCount = graph.vertexCount();
        GraphType flowGraph; // should be set to a graph with vCount number of vertices
        GraphType residualGraph; // so should this
        for (GraphEdgeType edge : graph.edges()) {
            // Add this edge and its reverse to the residualGraph
            // Add this edge and its reverse but with 0 capacity to the flowGraph
        }

        Optional<LinkedList<GraphEdgeType>> path = Optional.empty();
        // While loop ends when no path can be found from s to t in the residual graph.
        while ((path = BFS_path(residualGraph,graph.getS(), graph.getT())).isPresent()) {
            List<GraphEdgeType> p = path.get();
            // r is the minimum capacity of the edges in p
            int r = p.stream()
                    .map(GraphEdgeType::value)
                    .min(Integer::compareTo)
                    .get();

            for (GraphEdgeType edge : p) {
                int u = edge.getFrom(); int v = edge.getTo();
                GraphEdgeType fuv = flowGraph.edge(u,v).get();
                int fuvVal = fuv.value();
                fuv.setValue(fuvVal + r);
                fuv.reversed().setValue(-fuvVal);

                GraphEdgeType cuv = graph.edge(u,v).get();
                int cuvVal = cuv.value();
                GraphEdgeType cfuv = residualGraph.edge(u,v).get();
                cfuv.setValue(cuvVal-fuvVal);
                cfuv.reversed().setValue(cuv.reversed().value() - fuv.reversed().value());
                if (cfuv.value() == 0) residualGraph.removeEdge(cfuv.getFrom(),cfuv.getTo());
                if (cfuv.reversed().value() == 0) residualGraph.removeEdge(cfuv.getTo(),cfuv.getFrom());
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
    Optional<LinkedList<GraphEdgeType>> BFS_path(GraphType graph, int from, int to) {
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

    GraphType readCapacityGraph(){
        Kattio io = new Kattio(System.in, System.out);
        int v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        int e = io.getInt();
        Graph graph = new Graph(v);
        for(int i = 0; i<e; i++){
            int a = io.getInt();
            int b = io.getInt();
            int f = io.getInt();
        }
        return null;
    }
}
