package ZombieLand;

import ZombieLand.system.Tile;
import ZombieLand.tiles.MetroTile;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
        super(start); // calls constructor from PathFindingService class
        generateGraph(); // calls generate graph to set g field of graph
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
        this.g = new Graph(GraphTraversal.BFS(super.source)); // sets g field to Graph object with tiles obtained by calling BFS on source tile

        // iterates through vertices of graph
        for (Tile t : g.vertices) {

            // iterates through neighbors of current tile
            for (Tile n : t.neighbors) {

                // tile and its neighbor is walkable
                if (t.isWalkable() && n.isWalkable()) {


                    // current tile and neighbor are both Metro tiles
                    if (n instanceof MetroTile && t instanceof MetroTile) {
                        ((MetroTile) n).fixMetro(t); // calls fixMetro to update metroDistanceCost and metroTimeCost of both tiles
                        g.edges.add(new Graph.Edge(t, n, ((MetroTile) n).metroTimeCost)); // adds edge using neighbor's metro distance cost as edge weight
                    }
                    else {
                        g.edges.add(new Graph.Edge(t, n, n.timeCost)); // adds edge using neighbor's time cost as edge weight
                    }
                }
            }
        }
	}
}
