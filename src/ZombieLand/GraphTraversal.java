package ZombieLand;

import ZombieLand.system.Tile;

import java.util.ArrayList;

public class GraphTraversal
{

	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) {
		ArrayList<Tile> queue = new ArrayList<>(); // queue ArrayList
		ArrayList<Tile> listOfTiles = new ArrayList<>(); // ArrayList of tiles in BFS order
		Tile currentTile = s; // current tile

		listOfTiles.add(s); // adds first tile to list of tiles
		queue.add(s); // adds first tile to queue

		// queue is not empty
		while (!queue.isEmpty()){
			currentTile = queue.remove(0); // removes first tile in queue

			// iterates through current tile's neighbors
			for (int i = 0; i < currentTile.neighbors.size(); i++) {

				// list of tiles does not already contain neighbor and this neighbor is walkable
				if (!listOfTiles.contains(currentTile.neighbors.get(i)) && currentTile.neighbors.get(i).isWalkable()) {
					listOfTiles.add(currentTile.neighbors.get(i)); // adds neighbor to list of tiles
					queue.add(currentTile.neighbors.get(i)); // adds neighbor to queue
				}
			}
		}
		return listOfTiles;
	}


	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {
		ArrayList<Tile> stack = new ArrayList<>(); // stack ArrayList
		ArrayList<Tile> listOfTiles = new ArrayList<>(); // ArrayList of tiles in DFS order
		Tile currentTile = s; // current tile

		stack.add(currentTile); // adds current tile to stack

		// stack is not empty
		while (!stack.isEmpty()) {
			currentTile = stack.remove(stack.size()-1); // removes last tile in stack

			// list of tiles does not already contain current tile and current tile is walkable
			if (!listOfTiles.contains(currentTile) && currentTile.isWalkable()){
				listOfTiles.add(currentTile); // adds current tile to list of tiles
			}

			// iterates through current tile's neighbors
			for (int i=0; i<currentTile.neighbors.size(); i++) {

				// list of tiles does not already contain neighbor and neighbor is walkable
				if (!listOfTiles.contains(currentTile.neighbors.get(i)) && currentTile.neighbors.get(i).isWalkable()) {
					stack.add(currentTile.neighbors.get(i)); // adds neighbor to stack
				}
			}
		}
		return listOfTiles;
	}
}  