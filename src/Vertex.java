import java.util.ArrayList;

public class Vertex {
	
	private int vertexId;
	private String name;
	private ArrayList<Edge> edges;
	private Vertex parent;
	
	private static int vertexCount = -1;
	
	 public Vertex(String name) {
	     
		 this.vertexId = ++vertexCount;
		 this.name = name;
		 edges = new ArrayList();
		 parent = null;
	        
	 }
	 
	 public void addEdge(Edge e)
	 {
		 edges.add(e);  
	 }
	 
	 public ArrayList<Edge> getEdges()
	 {
		 return this.edges;
	 }
	 
	public int getVertexId() {
		return vertexId;
	}

	public void setVertexId(int vertexId) {
		this.vertexId = vertexId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vertex getParent() {
		return parent;
	}

	public void setParent(Vertex parent) {
		this.parent = parent;
	}
}
