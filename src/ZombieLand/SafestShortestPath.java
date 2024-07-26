package ZombieLand;


import java.util.ArrayList;
import java.util.LinkedList;

import ZombieLand.system.Tile;
import ZombieLand.tiles.MetroTile;


public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
		generateGraph();
	}


	public void generateGraph() {
		// TODO Auto-generated method stub
		this.costGraph = new Graph(GraphTraversal.BFS(super.source));
		for (Tile t : costGraph.vertices) {
			for (Tile n : t.neighbors) {
				if (t.isWalkable() && n.isWalkable()) {
					if (n instanceof MetroTile && t instanceof MetroTile) {
						((MetroTile) n).fixMetro(t);
						costGraph.edges.add(new Graph.Edge(t, n, ((MetroTile) n).metroDistanceCost));
					}
					else {
						costGraph.edges.add(new Graph.Edge(t, n, n.distanceCost));
					}
				}
			}
		}

		this.damageGraph = new Graph(GraphTraversal.BFS(super.source));
		for (Tile t : damageGraph.vertices) {
			for (Tile n : t.neighbors) {
				if (t.isWalkable() && n.isWalkable()) {
					if (n instanceof MetroTile && t instanceof MetroTile) {
						((MetroTile) n).fixMetro(t);
					}
					damageGraph.edges.add(new Graph.Edge(t, n, n.damageCost));
				}
			}
		}

		this.aggregatedGraph = new Graph(GraphTraversal.BFS(super.source));
		for (Tile t : aggregatedGraph.vertices) {
			for (Tile n : t.neighbors) {
				if (t.isWalkable() && n.isWalkable()) {
					if (n instanceof MetroTile && t instanceof MetroTile) {
						((MetroTile) n).fixMetro(t);
					}
					aggregatedGraph.edges.add(new Graph.Edge(t, n, n.damageCost));
				}
			}
		}
	}

	@Override
	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {

		g = costGraph;
		ArrayList<Tile> costGraphPath = super.findPath(start, waypoints);
		double costGraphPathDistanceCost = g.computePathCost(costGraphPath);
		double costGraphPathDamageCost = damageGraph.computePathCost(costGraphPath);

		if (costGraphPathDamageCost < health) {
			return costGraphPath;
		}

		g = damageGraph;
		ArrayList<Tile> damageGraphPath = super.findPath(start, waypoints);
		double damageGraphPathDamageCost = g.computePathCost(damageGraphPath);
		double damageGraphPathDistanceCost = costGraph.computePathCost(damageGraphPath);

		if (damageGraphPathDamageCost > health) {
			return null;
		}

		double multiplier = 0.0;
		ArrayList<Tile> aggregatedGraphPath = new ArrayList<Tile>();
		double aggregatedGraphPathCost = 0.0;
		double aggregatedGraphPathDistanceCost = 0.0;
		double aggregatedGraphPathDamageCost = 0.0;
		double costGraphPathAggregatedCost = 0.0;

		while (true) {

			multiplier = Math.abs((costGraphPathDistanceCost - damageGraphPathDistanceCost) / (damageGraphPathDamageCost-costGraphPathDamageCost));

			for (Graph.Edge e : aggregatedGraph.edges) {
				if (e.origin instanceof MetroTile && e.destination instanceof MetroTile) {
					e.weight = ((MetroTile) e.destination).metroDistanceCost + multiplier * e.destination.damageCost;
				}
				else {
					e.weight = e.destination.distanceCost + multiplier * e.destination.damageCost;
				}
			}

			g = aggregatedGraph;
			aggregatedGraphPath = super.findPath(start, waypoints);
			aggregatedGraphPathCost = g.computePathCost(aggregatedGraphPath);
			aggregatedGraphPathDistanceCost = costGraph.computePathCost(aggregatedGraphPath);
			aggregatedGraphPathDamageCost = damageGraph.computePathCost(aggregatedGraphPath);
			costGraphPathAggregatedCost = g.computePathCost(costGraphPath);

			if (aggregatedGraphPathCost == costGraphPathAggregatedCost) {
				return damageGraphPath;
			}
			else if (aggregatedGraphPathDamageCost <= health) {
				damageGraphPath = aggregatedGraphPath;
				damageGraphPathDistanceCost = aggregatedGraphPathDistanceCost;
				damageGraphPathDamageCost = aggregatedGraphPathDamageCost;
			}
			else {
				costGraphPath = aggregatedGraphPath;
				costGraphPathDistanceCost = aggregatedGraphPathDistanceCost;
				costGraphPathDamageCost = aggregatedGraphPathDamageCost;
			}
		}
	}
}

