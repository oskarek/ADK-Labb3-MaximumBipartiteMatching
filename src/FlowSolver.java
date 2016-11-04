import java.util.*;
import java.util.stream.Stream;

/**
 * Class to calculate the maximum flow in a graph.
 * Created by oskarek on 2016-11-02.
 */
public class FlowSolver {
    Kattio io = new Kattio(System.in, System.out);

    GraphType edmonds_Karp(GraphType capacityGraph) {
        io.print("Hej");
        int vCount = capacityGraph.vertexCount();

        Optional<LinkedList<GraphEdgeType>> path;
        // While loop ends when no path can be found from s to t in the residual graph.
        while ((path = BFS_path(capacityGraph,capacityGraph.getS(), capacityGraph.getT())).isPresent()) {
            List<GraphEdgeType> p = path.get();

            // r is the minimum capacity of the edges in p
            int r = p.stream().map(GraphEdgeType::getValue).min(Integer::compareTo).get();

            for (GraphEdgeType edge : p) {
                int u = edge.getFrom(); int v = edge.getTo();
                GraphEdgeType fuv = capacityGraph.getEdge(u,v).get();
                fuv.setFlowCapacity(fuv.getFlowCapacity() + r);
                fuv.reversed().setFlowCapacity(-fuv.getFlowCapacity());

                GraphEdgeType cuv = capacityGraph.getEdge(u, v).get();
                int cuvVal = cuv.getValue();
                GraphEdgeType cfuv = capacityGraph.getEdge(u, v).get();
                cfuv.setResidualCapacity(cuvVal-fuv.getFlowCapacity());
                cfuv.reversed().setResidualCapacity(cuv.reversed().getResidualCapacity()
                        - fuv.reversed().getFlowCapacity());
            }
        }
        return capacityGraph;
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
        parents[from] = -1;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(from);
        while (!queue.isEmpty()) {
            int u = queue.poll();

            if (u == to) {
                LinkedList<GraphEdgeType> path = new LinkedList<>();
                while (u != from) {
                    int p = parents[u];
                    GraphEdgeType edge = graph.getEdge(p,u).get();
                    path.addFirst(edge);
                    u = p;
                }
                return Optional.of(path);
            }

            for (int v : graph.neighboursForVertex(u)) {
                if (parents[v] == 0) {
                    parents[v] = u;
                    queue.add(v);
                }
            }
        }
        return Optional.empty();
    }
}
