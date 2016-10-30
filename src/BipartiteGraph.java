import java.util.ArrayList;

/**
 * Created by RobertLorentz on 30/10/16.
 */
public class BipartiteGraph {
    private ArrayList<Vertex>  xVertexes;
    private ArrayList<Vertex> yVertexes;

    public BipartiteGraph(int xNum, int yNum){
        xVertexes = new ArrayList<>(xNum);
        yVertexes = new ArrayList<>(yNum);
    }

    public void addVertex(int xOry, int repr, ArrayList<Edge> edges){
        if(xOry == 1){
            //The vertex is on the X-side
            xVertexes.add(new Vertex(repr, edges));
        } else {
            //The vertes is on the Y-side
            yVertexes.add(new Vertex(repr, edges));
        }
    }

    public void addEdge(int xOry, int repr, int edgeTo, int capacity){
        ArrayList<Vertex> searchList;
        if(xOry == 1){
            //The vertex is on the X-side
            searchList = xVertexes;
        } else {
            //The vertex is on the Y-side
            searchList = yVertexes;
        }
        for(Vertex v : searchList){
            if(v.getRepresentation() == repr)
                v.addEdge(edgeTo, capacity);
        }
    }

    public class Vertex {
        private int representation;
        private ArrayList<Edge> edges;

        public int getRepresentation() { return representation; }
        public ArrayList<Edge> getEdges() { return edges; }

        public Vertex(int representation, ArrayList<Edge> edges) {
            this.representation = representation;
            this.edges = edges;
        }

        public void addEdge(int edgeTo, int capacity){
            edges.add(new Edge(edgeTo, capacity));
        }
    }

    public class Edge {
        private int edgeTo;
        private int capacity;
        private int reversedCapacity;

        public Edge(int edgeTo, int capacity){
            this.edgeTo = edgeTo;
            this.capacity = capacity;
            reversedCapacity = -capacity;
        }

        public int getCapacity() { return capacity; }
        public int getEdgeTo() { return edgeTo; }
        public int getReversedCapacity() { return reversedCapacity; }
    }
}
