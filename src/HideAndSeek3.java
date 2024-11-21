import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class HideAndSeek3 {
    static class Node implements Comparable<Node> {
        int vertex, weight;

        public Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }

    }

    static int N,M;
    static int INF = Integer.MAX_VALUE;
    static int[] map;

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] strno = bf.readLine().split(" ");

        N = Integer.parseInt(strno[0]);
        M = Integer.parseInt(strno[1]);

        map = new int[100001];
        Arrays.fill(map,INF);

        System.out.println(FindYou(N, M));
    }

    public static int FindYou(int start, int end) {
        PriorityQueue<Node> pri = new PriorityQueue<>();
        pri.offer(new Node(start, 0));
        map[start] = 0;

        while(!pri.isEmpty()) {
            Node node = pri.poll();
            int x = node.vertex;
            int y = node.weight;

            if(y > map[x]) continue;

            if(x == end) {
                return map[x];
            }

            if(x - 1 >= 0 && map[x - 1] > y + 1) {
                pri.offer(new Node(x - 1, y +  1));
                map[x - 1] = y + 1;
            }
            if(x + 1 <100001 && map[x + 1] > y + 1) {
                pri.offer(new Node(x + 1, y +  1));
                map[x + 1] = y + 1;
            }
            if(x * 2 < 100001 && map[x * 2] > y) {
                pri.offer(new Node(x * 2, y ));
                map[x * 2] = y;
            }
        }
        return -1;
    }
}
