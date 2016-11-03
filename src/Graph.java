import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by RobertLorentz on 30/10/16.
 */
public class Graph {
    private ArrayList<Vertex> vertexes;
    private int s, t;
    private int v;
    private int edgeNum;

    public Graph(int v){
        vertexes = new ArrayList<>(v);
        this.v = v;
        s = v+1;
        t = s+1;

        loadVertexes();
    }

    public ArrayList<Vertex> getVertexes() { return vertexes; }
    public int getNumberOfVertexes() { return vertexes.size(); }
    public int getEdgeNum() { return edgeNum; }
    public int getS() { return s; }
    public int getT() { return t; }
    public int getV() { return v; }

    void loadVertexes() {
        for(int i = 1; i<=v; i++){
            addVertex(i);
        }
    }

    public Vertex getVertex(int repr){
        for(Vertex vertex : vertexes) {
            if(vertex.getRepresentation() == repr)
                return vertex;
        }
        return null;
    }

    public void addVertex(int repr){
        vertexes.add(new Vertex(repr));
    }

    public Edge addEdge(int from, int edgeTo, int capacity){
        for(Vertex v : vertexes){
            if(v.getRepresentation() == from) {
                Edge e = v.addEdge(from, edgeTo, capacity);
                return e;
            }
        }
        return null;
    }

    public class Vertex {
        private int representation;
        private ArrayList<Edge> edges;
        public int getRepresentation() { return representation; }
        public ArrayList<Edge> getEdges() { return edges; }

        public Vertex(int representation) {
            this.representation = representation;
            edges = new ArrayList<>();
        }

        public Edge addEdge(int edgeFrom, int edgeTo, int capacity){
            edgeNum++;
            Edge e = new Edge(edgeFrom, edgeTo, capacity);
            edges.add(e);
            return e;
        }
    }

    public static class Edge {
        private int edgeFrom;
        private int edgeTo;
        private int capacity;
        private Edge reversedEdge;

        public Edge(int edgeFrom, int edgeTo, int capacity){
            this.edgeFrom = edgeFrom;
            this.edgeTo = edgeTo;
            this.capacity = capacity;
        }

        public int getCapacity() { return capacity; }
        public int getEdgeTo() { return edgeTo; }
        public Edge getReversedEdge() { return reversedEdge; }
        public void addReversedEdge(Edge reversedEdge) { this.reversedEdge = reversedEdge; }
    }
}