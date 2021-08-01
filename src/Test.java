import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		
		// 3 methods(maxFlowMinCut, bfs, dfs) in Graph class are taken from https://www.geeksforgeeks.org/minimum-cut-in-a-directed-graph/ and updated.
		
		Graph graph= new Graph();
		
		writeTxtToGraph("graph.txt", graph);
		
		int[][] adjacencyMatrix = graph.createAdjacencyMatrix();
		
		graph.maxFlowMinCut(adjacencyMatrix, "HG", "KA"); //maxFlowMinCut(int[][] graph, String sourceVertex, String destinationVertex)

	}
	
	public static void writeTxtToGraph(String txt, Graph g) {
		
		BufferedReader reader;
		try {
			
			reader = new BufferedReader(new FileReader(txt));
			
			String line = reader.readLine();
			while (line != null) {
				
				String [] temp = line.split("\t");
				g.addEgde(temp[0], temp[1], Integer.parseInt(temp[2]));
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
