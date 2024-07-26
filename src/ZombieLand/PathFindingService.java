package ZombieLand;

import ZombieLand.system.Tile;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class PathFindingService {
	Tile source;
	Graph g;
	public PathFindingService(Tile start) {
    	this.source = start;
    }

	public abstract void generateGraph();

    private void initSingleSource (ArrayList<Tile> queue, Tile start) {
        for (int i=1; i<queue.size(); i++) {
            queue.get(i).costEstimate = Double.POSITIVE_INFINITY;
            queue.get(i).predecessor = null;
        }
        start.costEstimate = 0.0;
    }

    private void relax (TilePriorityQ queue, Tile start, Tile end, double weight) {
        if (end.costEstimate > start.costEstimate + weight) {
            queue.updateKeys(end, start, start.costEstimate + weight);
        }
    }

    private double getEdgeWeight (Tile origin, Tile destination) {
        double edgeWeight = 0.0;
        for (Graph.Edge e : g.edges) {
            if (e.origin == origin && e.destination == destination) {
                edgeWeight = e.weight;
                break;
            }
        }
        return edgeWeight;
    }

    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {
        TilePriorityQ priorityQ = new TilePriorityQ(GraphTraversal.BFS(startNode));
        ArrayList<Tile> pathComputed = new ArrayList<>();
        ArrayList<Tile> path = new ArrayList<>();

        initSingleSource(priorityQ.queue, startNode);
        Tile source;

        while (!priorityQ.queue.isEmpty()) {

            if (priorityQ.queue.size() == 2) {
                source = priorityQ.queue.get(1);
                priorityQ.queue.remove(1);
                priorityQ.queue.remove(0);
            }
            else {
                source = priorityQ.removeMin();
            }

            pathComputed.add(source);
            for (Tile n : g.getNeighbors(source)) {
                relax(priorityQ, source, n, getEdgeWeight(source, n));
            }
        }
        Tile destination = null;
        for (Tile t : pathComputed) {
            if (t.isDestination) {
                destination = t;
                break;
            }
        }

        while (destination!= null) {
            path.add(0, destination);
            destination = destination.predecessor;
        }

    	return path;
    }
    
    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        ArrayList<Tile> vertices = GraphTraversal.BFS(start);

        ArrayList<Tile> pathComputed = new ArrayList<>();
        ArrayList<Tile> path = new ArrayList<>();


        initSingleSource(vertices, start);
        TilePriorityQ priorityQ = new TilePriorityQ(vertices);
        Tile source;

        while (!priorityQ.queue.isEmpty()) {

            if (priorityQ.queue.size() == 2) {
                source = priorityQ.queue.get(1);
                priorityQ.queue.remove(1);
                priorityQ.queue.remove(0);
            }
            else {
                source = priorityQ.removeMin();
            }

            pathComputed.add(source);
            for (Tile n : g.getNeighbors(source)) {
                relax(priorityQ, source, n, getEdgeWeight(source, n));
            }
        }
        while (end != start) {
            path.add(0, end);
            end = end.predecessor;
        }
        path.add(0,start);
        return path;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
        ArrayList<Tile> originalPath = findPath(start);

        ArrayList<Tile> pathWithWaypoints = new ArrayList<>();

        for (Tile t : waypoints) {
            pathWithWaypoints.addAll(findPath(start, t));
            pathWithWaypoints.remove(pathWithWaypoints.size()-1);
            start = t;
        }
        Tile end = originalPath.get(originalPath.size()-1);
        pathWithWaypoints.addAll(findPath(start,end));

    	return pathWithWaypoints;
    }
}

