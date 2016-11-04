import java.util.*;

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

        GraphType graph = readCapacityGraph();

        GraphType maxGraph = edmonds_Karp(graph);

        writeFlowGraph(maxGraph);

        io.close();
    }

    private GraphType readCapacityGraph(){
        int v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        int e = io.getInt();
        GraphType graph = new Graph(v);
        graph.setS(s);
        graph.setT(t);

        for(int i = 0; i<e; i++){
            int a = io.getInt();
            int b = io.getInt();
            int f = io.getInt();
            graph.addEdge(a,b,f);
        }
        return graph;
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
            int r = 0;
            for (GraphEdgeType e : p)
                if (e.getValue() < r) r = e.getValue();

            for (GraphEdgeType edge : p) {
                int u = edge.getFrom(); int v = edge.getTo();
                GraphEdgeType fuv = flowGraph.getEdge(u,v).get();
                fuv.setValue(fuv.getValue() + r);
                fuv.reversed().setValue(-fuv.getValue());

                GraphEdgeType cuv = capacityGraph.getEdge(u,v).get();
                int cuvVal = cuv.getValue();
                GraphEdgeType cfuv = residualGraph.getEdge(u,v).get();
                cfuv.setValue(cuvVal-fuv.getValue());
                cfuv.reversed().setValue(cuv.reversed().getValue() - fuv.reversed().getValue());
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
        int[] parents = new int[graph.vertexCount()+1];
        int[] values = new int[graph.vertexCount()+1];
        parents[from] = -1;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(from);
        while (!queue.isEmpty()) {
            int u = queue.poll();

            if (u == to) {
                LinkedList<GraphEdgeType> path = new LinkedList<>();
                while (u != from) {
                    int p = parents[u];
                    GraphEdgeType edge = new Graph.Edge(p,u,values[u]);
                    path.addFirst(edge);
                    u = p;
                }
                return Optional.of(path);
            }

            for (GraphEdgeType e : graph.edgesForVertex(u)) {
                if (e.getValue() <= 0) continue;
                int v = e.getTo();
                int val = e.getValue();
                if (parents[v] == 0) {
                    parents[v] = u;
                    values[v] = val;
                    queue.add(v);
                }
            }
        }
        return Optional.empty();
    }

    private void writeFlowGraph(GraphType graph) {
        int vertexCount = graph.vertexCount();
        io.println(vertexCount);
        int s = graph.getS(), t = graph.getT();
        int totFlow = 0;
        for (GraphEdgeType sEdge : graph.edgesForVertex(s))
            totFlow += sEdge.getValue();
        io.println(s + " " + t + " " + totFlow);

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
}
