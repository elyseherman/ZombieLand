package ZombieLand;

import java.util.ArrayList;

import ZombieLand.system.Tile;

public class Graph {
	// TODO level 2: Add fields that can help you implement this data type
    ArrayList<Tile> vertices; // ArrayList of vertices in graph
    ArrayList<Edge> edges; // ArrayList of edges in graph
    // TODO level 2: initialize and assign all variables inside the constructor
	public Graph(ArrayList<Tile> vertices) {
        this.vertices = vertices; // sets vertices to input ArrayList
        this.edges = new ArrayList<>(); // initializes edges ArrayList
	}
	
    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight){

        // origin tile is walkable and destination tile is walkable
        if (origin.isWalkable() && destination.isWalkable()) {
            Edge edge = new Edge(origin,destination,weight); // creates new edge
            edges.add(edge); // adds edge to edges field of graph
        }
    }
    
    // TODO level 2: return a list of all edges in the graph
	public ArrayList<Edge> getAllEdges() {
		return edges; // returns edges of graph
	}
  
	// TODO level 2: return list of tiles adjacent to t
	public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> walkableNeighbors = new ArrayList<>(); // ArrayList of walkable neighbors of tile t

        // iterates through edges of graph
        for (Edge e : edges) {

            // edge's origin is t
            if (e.origin == t) {
                walkableNeighbors.add(e.destination); // adds edge's destination (t's neighbor) to walkable neighbors
            }
        }

    	return walkableNeighbors;
    }
	
	// TODO level 2: return total cost for the input path
	public double computePathCost(ArrayList<Tile> path) {
        double pathCost = 0.0; // path cost

        // iterates over tiles in path
        for (int i=0; i<path.size()-1;i++) {

            // iterates over edges of  graph
            for (int j=0; j<edges.size();j++) {

                // current tile and next tile are current edge's origin and destination, respectively
                if (edges.get(j).origin == path.get(i) && edges.get(j).destination == path.get(i+1)) {
                    pathCost += edges.get(j).weight; // adds weight of edge to path cost
                    break;
                }
            }
        }

		return pathCost;
	}
	
   
    public static class Edge{
    	Tile origin;
    	Tile destination;
    	double weight;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d, double cost){
        	this.origin = s; // sets edge origin
            this.destination = d; // sets edge destination
            this.weight = cost; // sets edge weight
        }
        
        // TODO level 2: getter function 1
        public Tile getStart(){
            return origin; // returns origin of edge
        }

        
        // TODO level 2: getter function 2
        public Tile getEnd() {
            return destination; // returns destination of edge
        }
    }
}