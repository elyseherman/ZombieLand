package ZombieLand;

import ZombieLand.system.Tile;
import ZombieLand.tiles.MetroTile;

public class ShortestPath extends PathFindingService {
    //TODO level 4: find distance prioritized path
    public ShortestPath(Tile start) {
        super(start); // calls constructor from PathFindingService class
        generateGraph(); // calls generate graph to initiate g field
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
        this.g = new Graph(GraphTraversal.BFS(super.source)); // sets g field to Graph object with input vertices of BFS traversal from source

        // iterates through vertices in graph
        for (Tile t : g.vertices) {

            // iterates through neighbors of t
            for (Tile n : t.neighbors) {

                // tile and its neighbor is walkable
                if (t.isWalkable() && n.isWalkable()) {

                    // neighbor and t are both Metro tiles
                    if (n instanceof MetroTile && t instanceof MetroTile) {
                        ((MetroTile) n).fixMetro(t); // calls fixMetro method
                        g.edges.add(new Graph.Edge(t, n, ((MetroTile) n).metroDistanceCost)); // adds edge using neighbor's metro distance cost
                    } else {
                        g.edges.add(new Graph.Edge(t, n, n.distanceCost)); // adds edge using neighbor's distance cost
                    }
                }
            }
        }
	}
}