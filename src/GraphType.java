import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Should be implemented be the Graph class.
 */
public interface GraphType {
    /**
     * @return The edges of the graph.
     */
    List<GraphEdgeType> edges();

    /**
     * Add an edge to the graph.
     * @param from The start vertex for the edge.
     * @param to The target vertex for the edge.
     * @param value The value of the edge (could for example represent capacity or flow,
     *              depending on the graph type)
     */
    void addEdge(int from, int to, int value);

    /**
     * Remove an edge from the graph.
     * @param from The start vertex for the edge.
     * @param to The target vertex for the edge.
     */
    void removeEdge(int from, int to);

    /**
     * Return the edge leaving the `from` vertex and entering `to` vertex.
     * @param from The start vertex for the edge.
     * @param to The target vertex for the edge.
     * @return The edge if it exists, otherwise Optional.empty().
     */
    Optional<GraphEdgeType> getEdge(int from, int to);

    /**
     * @return The s vertex.
     */
    int getS();

    /**
     * @return The t vertex.
     */
    int getT();

    /**
     * @return The number of vertices in the graph.
     */
    int vertexCount();

    /**
     * Get the edges leaving the given vertex.
     * @param vertex The vertex who's edges should be returned.
     * @return The edges from the given vertex.
     */
    List<GraphEdgeType> edgesForVertex(int vertex);
}