import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
	
	private HashMap<String,Vertex> vertices;
	private HashMap<String,Edge> edges;

	Graph() {
		this.vertices = new HashMap<>();
		this.edges = new HashMap<>();
	}

	public void addEgde(String source, String destination, int weight) {

		if(edges.get(source + "-" + destination) == null && edges.get(destination + "-" + source) == null)
		{
			Vertex source_v, destination_v;

			if(vertices.get(source) == null) 
				{
					source_v  = new Vertex(source);
					vertices.put(source, source_v);
				}
			else source_v = vertices.get(source);

			if(vertices.get(destination) == null) 
				{
				destination_v  = new Vertex(destination);
				vertices.put(destination, destination_v );
				}
			else destination_v = vertices.get(destination);

			Edge edge = new Edge(source_v, destination_v, weight);
			source_v.addEdge(edge);
			destination_v.addEdge(edge);
			edges.put(source + "-" + destination, edge);
		}
		else // weights of repeating edges are collected
		{
			for(Edge e : edges.values()) {
				if(e.getSource().getName().equals(source) && e.getDestination().getName().equals(destination))
					e.setWeight(e.getWeight() + weight);
			}
		}
		
	}

	public int [][] createAdjacencyMatrix(){
		
		System.out.println("Adjacency Matrix is being created. Please wait.\n-----------------------------------------------\n");
		
		int [][] adjMatrix = new int [vertices.size()][vertices.size()] ;
		
		for(Vertex ver1 : vertices.values()) {
			
			for (Vertex ver2 : vertices.values()) {
				
				if(ver1 == ver2)
					adjMatrix[ver1.getVertexId()][ver2.getVertexId()] = 0;
				else {
					
					if(edges.get(ver1.getName() + "-" + ver2.getName()) == null)
						adjMatrix[ver1.getVertexId()][ver2.getVertexId()] = 0;		
					else
						adjMatrix[ver1.getVertexId()][ver2.getVertexId()] = edges.get(ver1.getName() + "-" + ver2.getName()).getWeight();
					
				}
				
			}
					
		}
		return adjMatrix;
	}
	
	// 3 methods below(maxFlowMinCut, bfs, dfs) are taken from https://www.geeksforgeeks.org/minimum-cut-in-a-directed-graph/ and updated.
	
	// Returns true if there is a path
	// from source 's' to sink 't' in residual
	// graph. Also fills parent[] to store the path
	public boolean bfs(int[][] rGraph, int s, int t, int[] parent) {
		
		// Create a visited array and mark 
		// all vertices as not visited	 
		boolean[] visited = new boolean[rGraph.length]; 
			
		// Create a queue, enqueue source vertex 
		// and mark source vertex as visited	 
		Queue<Integer> q = new LinkedList<Integer>(); 
		q.add(s); 
		visited[s] = true; 
		parent[s] = -1; 
			
		// Standard BFS Loop	 
		while (!q.isEmpty()) { 
			int v = q.poll(); 
			for (int i = 0; i < rGraph.length; i++) { 
				if (rGraph[v][i] > 0 && !visited[i]) { 
					q.offer(i); 
					visited[i] = true; 
					parent[i] = v; 
				} 
			} 
		} 
			
		// If we reached sink in BFS starting 
		// from source, then return true, else false	 
		return (visited[t] == true); 
	} 
		
	// A DFS based function to find all reachable 
	// vertices from s. The function marks visited[i] 
	// as true if i is reachable from s. The initial 
	// values in visited[] must be false. We can also 
	// use BFS to find reachable vertices 
	public void dfs(int[][] rGraph, int s, boolean[] visited) { 
		
		visited[s] = true; 
		for (int i = 0; i < rGraph.length; i++) { 
				if (rGraph[s][i] > 0 && !visited[i]) { 
					dfs(rGraph, i, visited); 
				} 
		} 
	} 

	// Prints the max flow and the minimum s-t cut 
	public void maxFlowMinCut(int[][] graph, String sourceVertex, String destinationVertex) { 
		
		int s = 0, t = 0;
		for(Vertex v : vertices.values()) {
			
			if(sourceVertex.equals(v.getName()))
				s = v.getVertexId();
			
			if(destinationVertex.equals(v.getName()))
				t = v.getVertexId();
				
		}
		
		int u,v; int maxFlow = 0;
			
		// Create a residual graph and fill the residual 
		// graph with given capacities in the original 
		// graph as residual capacities in residual graph 
		// rGraph[i][j] indicates residual capacity of edge i-j 
		int[][] rGraph = new int[graph.length][graph.length]; 
		for (int i = 0; i < graph.length; i++) { 
			for (int j = 0; j < graph.length; j++) { 
				rGraph[i][j] = graph[i][j]; 
			} 
		} 

		// This array is filled by BFS and to store path 
		int[] parent = new int[graph.length]; 
			
		// Augment the flow while tere is path from source to sink	 
		while (bfs(rGraph, s, t, parent)) { 
				
			// Find minimum residual capacity of the edhes 
			// along the path filled by BFS. Or we can say 
			// find the maximum flow through the path found. 
			int pathFlow = Integer.MAX_VALUE;		 
			for (v = t; v != s; v = parent[v]) { 
				u = parent[v]; 
				pathFlow = Math.min(pathFlow, rGraph[u][v]); 
			} 
			maxFlow += pathFlow;
			// update residual capacities of the edges and 
			// reverse edges along the path 
			for (v = t; v != s; v = parent[v]) { 
				u = parent[v]; 
				rGraph[u][v] = rGraph[u][v] - pathFlow; 
				rGraph[v][u] = rGraph[v][u] + pathFlow; 
			} 
		} 
		
		System.out.println("Maximum Packages : " + maxFlow);	
			
		// Flow is maximum now, find vertices reachable from s	 
		boolean[] isVisited = new boolean[graph.length];	 
		dfs(rGraph, s, isVisited); 
		
		String node1 = null, node2 = null;
		
		// Print all edges that are from a reachable vertex to 
		// non-reachable vertex in the original graph	
		System.out.println("\nEdges that need to increase their capacities : \n");
		for (int i = 0; i < graph.length; i++) { 
			for (int j = 0; j < graph.length; j++) { 
				if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) { 
					
					for(Vertex ver : vertices.values()) {
						
						if(i == ver.getVertexId())
							node1 = ver.getName();
						
						if(j == ver.getVertexId())
							node2 = ver.getName();
							
					}
					
					System.out.println(node1 + " - " + node2 + " (weight of edge : " + graph[i][j] + ")"); 
				} 
			} 
		} 
	} 

	public void print(){

		System.out.println("Source\tDestination\tWeight");
		for (Edge e : edges.values()) {
			System.out.println(e.getSource().getName() + "\t" + e.getDestination().getName() + "\t\t" + e.getWeight()+ " ");
		}
	}
	
	public void printVertices() {
		
		System.out.println("Vertices : ");
		for (Vertex v2 : vertices.values()) {
			System.out.println(v2.getVertexId() + ". vertex : " + v2.getName()); // vertexId --> [0, 14847]
		}
		
		System.out.println("Total number of vertices : " + vertices.size());
		
	}
	
	public HashMap<String, Vertex> getVertices() {
		return vertices;
	}

	public HashMap<String, Edge> getEdges() {
		return edges;
	}

	public int size()
	{
		return vertices.size();
	}
}
