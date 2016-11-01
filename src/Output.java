import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by RobertLorentz on 31/10/16.
 */
public class Output {
    private int xNum;
    private int yNum;

    /**
     * The resulting output of the flow-graph in step 1.
     */
    public void flowOutput(Graph graph) {
        int size = graph.getxVertexes().size() + graph.getyVertexes().size();
        System.out.println(size);
        System.out.println(graph.getS() + " " + graph.getT());
        System.out.println(graph.getEdgeNum());
        ArrayList<Graph.Vertex> same = graph.getxVertexes();
        same.addAll(graph.getyVertexes());
        for(Graph.Vertex vertex : same){
            for(Graph.Edge edge : vertex.getEdges())
                System.out.println(vertex.getRepresentation() + " " + edge.getEdgeTo() + " " + edge.getCapacity());
        }
        System.out.flush();
    }

    /**
     * A method which reads standard input and tries to interpret it into a flow graph. It then prints out
     * all the matchings.
     */
    public void flowToMatching(){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            br.readLine();
            String[] row = br.readLine().split("\\s+");
            int s = Integer.parseInt(row[0]);
            int t = Integer.parseInt(row[1]);
            int numEdges = Integer.parseInt(br.readLine());
            System.out.println(xNum + " " + yNum);
            ArrayList<String> matchings = new ArrayList<>();
            for(int i = 0; i<numEdges; i++){
                row = br.readLine().split("\\s+");
                int first = Integer.parseInt(row[0]);
                int second = Integer.parseInt(row[1]);
                int flow = Integer.parseInt(row[2]);
                if(flow > 0 && first != s && first != t && second != s && second != t ){
                    matchings.add(first + " " + second);
                }
            }
            System.out.println(matchings.size());
            for(String str : matchings)
                System.out.println(str);
            System.out.flush();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method which reads standard input and tries to interpret it as a bipartite graph. It then puts the
     * edges and vertexes into a Graph class.
     * @return A graph object
     */
    public Graph matchingInputReader() {
        String[] row;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] firstRow = br.readLine().split("\\s+");
            xNum = Integer.parseInt(firstRow[0]);
            yNum = Integer.parseInt(firstRow[1]);
            Graph bg = new Graph(xNum,yNum);
            int numEdges = Integer.parseInt(br.readLine());

            for(int i = 0; i<numEdges; i++){
                row = br.readLine().split("\\s+");
                int x = Integer.parseInt(row[0]);
                int y = Integer.parseInt(row[1]);
                if(!bg.vertexExists(0,x)) {
                    bg.addVertex(0, x);
                }
                if(!bg.vertexExists(1,y)) {
                    bg.addVertex(1, y);

                }
                //Adds both the edges and a pointer for each to the responding reversed edge.
                Graph.Edge edge = bg.addEdge(0,x,y,1);
                //Graph.Edge revEdge = bg.addEdge(1,y,x,0);
                //edge.addReversedEdge(revEdge);
                //revEdge.addReversedEdge(edge);
            }
            br.close();
            return bg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
