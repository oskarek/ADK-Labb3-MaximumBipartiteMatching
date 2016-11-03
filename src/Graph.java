import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by RobertLorentz on 30/10/16.
 */
public class Graph implements GraphType {
    private ArrayList<Vertex> vertexes;
    private int s, t;
    private int edgeNum;

    public Graph(int v){
        vertexes = new ArrayList<>(v);
        s = v+1;
        t = s+1;
        loadVertexes(v);
    }

    public ArrayList<Vertex> getVertexes() { return vertexes; }
    public int getNumberOfVertexes() { return vertexes.size(); }
    public int getEdgeNum() { return edgeNum; }
    public int getS() { return s; }
    public int getT() { return t; }

    public int vertexCount() {
        return vertexes.size();
    }

    public ArrayList<GraphEdgeType> edgesForVertex(int vertex) {
        return getVertex(vertex).getEdges();
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
        return vertexes.get(value+1);
    }

    public ArrayList<GraphEdgeType> edges() {
        ArrayList<GraphEdgeType> edges = new ArrayList<>();
        for(Vertex v : vertexes){
            for(GraphEdgeType edge : v.getEdges())
                edges.add(edge);
        }
        return edges;
    }

    public void addEdge(int from, int edgeTo, int capacity){
        Vertex v = getVertex(from);
        Vertex r = getVertex(edgeTo);
        ArrayList<GraphEdgeType> edges = r.getEdges();
        Boolean found = false;
        for(GraphEdgeType edge : edges){
            if(edge.getTo() == from) {
                edge.reversed().setValue(capacity);
                found = true;
            }
        }
        if(!found)
            v.addEdge(from,edgeTo,capacity);
    }

    public void removeEdge(int from, int to) {
        Vertex v = getVertex(from);
        v.removeEdge(to);
    }

    public Optional<GraphEdgeType> getEdge(int from, int to) {
        return null;
    }

    public class Vertex {
        private int value;
        private ArrayList<GraphEdgeType> edges;
        public int getValue() { return value; }
        public ArrayList<GraphEdgeType> getEdges() { return edges; }

        public Vertex(int value) {
            this.value = value;
            edges = new ArrayList<>();
        }

        public Edge addEdge(int edgeFrom, int edgeTo, int capacity){
            edgeNum++;
            Edge e = new Edge(edgeFrom, edgeTo, capacity);
            Edge re = new Edge(edgeTo, edgeFrom, 0);
            e.setReversedEdge(re);
            re.setReversedEdge(e);
            edges.add(re);
            edges.add(e);
            return e;
        }

        public void removeEdge(int to) {
            for(GraphEdgeType edge : edges){
                if(edge.getTo() == to)
                    edges.remove(edge);
            }
        }
    }

    public static class Edge implements GraphEdgeType {
        private int edgeFrom;
        private int edgeTo;
        private int capacity;
        private Edge reversedEdge;

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

        public void setReversedEdge(Edge reversedEdge){
            this.reversedEdge = reversedEdge;
        }
    }
}