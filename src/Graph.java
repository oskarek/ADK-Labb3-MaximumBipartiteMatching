import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

/**
 * A graph implementation.
 * Created by RobertLorentz on 30/10/16.
 */
public class Graph implements GraphType {
    private ArrayList<Vertex> vertexes;
    private int s = 0, t = 0;
    private int edgeNum;

    public Graph(int v){
        vertexes = new ArrayList<>(v);
        loadVertexes(v);
    }

    public ArrayList<Vertex> getVertexes() { return vertexes; }
    public int getNumberOfVertexes() { return vertexes.size(); }
    public int getEdgeNum() { return edgeNum; }
    public int getS() { return s; }
    public int getT() { return t; }
    public void setS(int value) { s = value; }
    public void setT(int value) { t = value; }

    public int vertexCount() {
        return vertexes.size();
    }

    public LinkedList<GraphEdgeType> edgesForVertex(int vertex) {
        return getVertex(vertex).getEdges();
    }

    public LinkedList<Integer> neighboursForVertex(int vertex) {
        LinkedList<Integer> neighbours = new LinkedList<>();
        for (GraphEdgeType edge : edgesForVertex(vertex)) {
            if (edge.getValue() > 0)
                neighbours.add(edge.getTo());
        }
        return neighbours;
    }

    void loadVertexes(int v) {
        for(int i = 1; i<=v; i++){
            addVertex(i);
        }
    }

    public void addVertex(int repr){
        vertexes.add(new Vertex(repr));
    }

    public Vertex getVertex(int value) {
        return vertexes.get(value-1);
    }

    public LinkedList<GraphEdgeType> edges() {
        LinkedList<GraphEdgeType> edges = new LinkedList<>();
        for(Vertex v : vertexes){
            for(GraphEdgeType edge : v.getEdges())
                edges.add(edge);
        }
        return edges;
    }

    public void addEdge(int from, int edgeTo, int capacity){
        Vertex v = getVertex(from);
        Vertex r = getVertex(edgeTo);
        LinkedList<GraphEdgeType> edges = r.getEdges();
        Boolean found = false;
        for(GraphEdgeType edge : edges){
            if(edge.getTo() == from) {
                edge.reversed().setValue(capacity);
                found = true;
            }
        }
        if(!found) {
            GraphEdgeType edge = v.addEdge(from, edgeTo, capacity);
            GraphEdgeType revEdge = r.addEdge(edgeTo, from, 0);
            edge.setReversedEdge(revEdge);
            revEdge.setReversedEdge(edge);
        }

    }

    public void removeEdge(int from, int to) {
        Vertex v = getVertex(from);
        v.removeEdge(to);
    }

    public Optional<GraphEdgeType> getEdge(int from, int to) {
        return getVertex(from).getEdge(to);
    }

    public class Vertex {
        private int value;
        private LinkedList<GraphEdgeType> edges;
        public int getValue() { return value; }
        public LinkedList<GraphEdgeType> getEdges() { return edges; }

        public Optional<GraphEdgeType> getEdge(int to) {
            for(GraphEdgeType edge : edges){
                if(edge.getTo() == to)
                    return Optional.of(edge);
            }
            return Optional.empty();
        }

        public Vertex(int value) {
            this.value = value;
            edges = new LinkedList<>();
        }

        public Edge addEdge(int edgeFrom, int edgeTo, int capacity){
            edgeNum++;
            Edge e = new Edge(edgeFrom, edgeTo, capacity);
            edges.add(e);
            return e;
        }

        public void removeEdge(int to) {
            for(GraphEdgeType edge : edges){
                if(edge.getTo() == to) {
                    edges.remove(edge);
                    break;
                }
            }
        }
    }

    public static class Edge implements GraphEdgeType {
        private int edgeFrom;
        private int edgeTo;
        private int capacity;
        private GraphEdgeType reversedEdge;

        public Edge(int edgeFrom, int edgeTo, int capacity){
            this.edgeFrom = edgeFrom;
            this.edgeTo = edgeTo;
            this.capacity = capacity;
        }

        public int getFrom() { return edgeFrom; }

        public int getTo() { return edgeTo; }

        public int getValue() { return capacity; }

        public void setValue(int value) { capacity = value; }

        public GraphEdgeType reversed() {
            return reversedEdge;
        }

        public void setReversedEdge(GraphEdgeType reversedEdge){
            this.reversedEdge = reversedEdge;
        }
    }
}