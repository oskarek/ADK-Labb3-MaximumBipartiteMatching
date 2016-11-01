import java.util.ArrayList;

/**
 * Created by RobertLorentz on 30/10/16.
 */
public class Graph {
    private ArrayList<Vertex>  xVertexes;
    private ArrayList<Vertex> yVertexes;
    private int s, t;
    private int edgeNum;

    public Graph(int xNum, int yNum){
        xVertexes = new ArrayList<>(xNum);
        yVertexes = new ArrayList<>(yNum);
    }

    public ArrayList<Vertex> getxVertexes() { return xVertexes; }
    public ArrayList<Vertex> getyVertexes() { return yVertexes; }
    public int getEdgeNum() { return edgeNum; }
    public int getS() { return s; }
    public int getT() { return t; }


    public void addVertex(int xOry, int repr){
        if(xOry == 0){
            //The vertex is on the X-side
            xVertexes.add(new Vertex(repr));
        } else {
            //The vertes is on the Y-side
            yVertexes.add(new Vertex(repr));
        }
        if(repr >= t) {
            s = repr+1;
            t = s+1;
        }
    }

    public void addS(int s) {
        this.s = s;
        xVertexes.add(new Vertex(s));
    }

    public void addT(int t){
        this.t = t;
        yVertexes.add(new Vertex(t));
    }

    public Edge addEdge(int xOry, int repr, int edgeTo, int capacity){
        ArrayList<Vertex> searchList;
        edgeNum++;
        if(xOry == 0){
            //The vertex is on the X-side
            searchList = xVertexes;
        } else {
            //The vertex is on the Y-side
            searchList = yVertexes;
        }
        for(Vertex v : searchList){
            if(v.getRepresentation() == repr) {
                Edge e = v.addEdge(repr, edgeTo, capacity);
                return e;
            }
        }
        return null;
    }

    public Boolean vertexExists(int xOry, int repr) {
        ArrayList<Vertex> searchList;
        if(xOry == 0){
            //The vertex is on the X-side
            searchList = xVertexes;
        } else {
            //The vertex is on the Y-side
            searchList = yVertexes;
        }
        for(Vertex v : searchList){
            if(v.getRepresentation() == repr)
                return true;
        }
        return false;
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
