/**
 * Created by oskarek on 2016-11-02.
 */
public class FlowSolver {
    public static void main(String[] args) {

    }

    void solve(){
        Kattio io = new Kattio(System.in, System.out);
        int v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        int e = io.getInt();
        Graph graph = new Graph(v);
        for(int i = 0; i<e; i++){
            int a = io.getInt();
            int b = io.getInt();
            int f = io.getInt();
        }
    }
}
