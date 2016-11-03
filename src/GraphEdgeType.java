/**
 * Should be implemented by the Edge class for the Graph.
 */
interface GraphEdgeType {
    /**
     * The vertex that the edge is pointing from.
     * @return The source vertex of the edge.
     */
    int getFrom();

    /**
     * The vertex that the edge is pointing to.
     * @return The target vertex.
     */
    int getTo();

    /**
     * The value of the edge.
     * @return The value of the edge.
     */
    int getValue();

    /**
     * Set the value of the edge.
     * @param value The value to be set.
     */
    void setValue(int value);

    /**
     * The edge pointing in opposite direction, between the sam vertices.
     * @return The reversed edge.
     */
    GraphEdgeType reversed();

    void setReversedEdge(GraphEdgeType edge);
}