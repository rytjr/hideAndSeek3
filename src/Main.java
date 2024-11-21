import java.util.*;
import java.io.*;

public class Main {
    static class Node implements Comparable<Node> {
        int vertex, cost;

        Node(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }

    static int N, E;
    static List<List<Node>> graph = new ArrayList<>();
    static final int INF = 200000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            graph.get(a).add(new Node(b, c));
            graph.get(b).add(new Node(a, c));
        }

        st = new StringTokenizer(br.readLine());
        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());

        // 다익스트라 실행
        int result = findShortestPath(v1, v2);
        System.out.println(result);
    }

    static int dijkstra(int start, int end) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF);
        dist[start] = 0;
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int curVertex = current.vertex;
            int curCost = current.cost;

            if (curCost > dist[curVertex]) continue;

            for (Node next : graph.get(curVertex)) {
                int nextVertex = next.vertex;
                int nextCost = curCost + next.cost;

                if (nextCost < dist[nextVertex]) {
                    dist[nextVertex] = nextCost;
                    pq.add(new Node(nextVertex, nextCost));
                }
            }
        }

        return dist[end];
    }

    static int findShortestPath(int v1, int v2) {
        int path1 = 0, path2 = 0;

        // 1 -> v1 -> v2 -> N
        path1 += dijkstra(1, v1);
        path1 += dijkstra(v1, v2);
        path1 += dijkstra(v2, N);

        // 1 -> v2 -> v1 -> N
        path2 += dijkstra(1, v2);
        path2 += dijkstra(v2, v1);
        path2 += dijkstra(v1, N);

        // 둘 중 최소 경로 선택, 불가능한 경로는 INF 체크
        if (path1 >= INF && path2 >= INF) return -1;
        return Math.min(path1, path2);
    }
}
