package ZombieLand;

import java.util.ArrayList;


import ZombieLand.system.Tile;


public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	ArrayList<Tile> queue; // priority queue ArrayList
	// TODO level 3: implement the constructor for the priority queue
	public TilePriorityQ (ArrayList<Tile> vertices) {
		this.queue = buildMinHeap(vertices); // creates a min-heap priority queue using input vertices
	}

    // adds input Tile to input ArrayList
	private void addVertex (ArrayList<Tile> list, Tile tile) {
		int size = list.size(); // size of input list
		list.add(tile); // adds input tile to input list
		int i = size; // index number equal to input list size
		Tile tmp; // temporary variable

        // i is greater than 1 and cost estimate of current tile is less than its parent
		while (i>1 && list.get(i).costEstimate < list.get(i/2).costEstimate) {
			tmp = list.get(i); // temporarily stores current tile
			list.set(i, list.get(i/2)); // stores parent tile at current tile's position
			list.set(i/2, tmp); // stores current tile at parent tile's position
			i = i/2; // sets i to index of parent tile
		}
	}

    // builds a min heap ArrayList from input ArrayList
	private ArrayList<Tile> buildMinHeap (ArrayList<Tile> list) {
		ArrayList<Tile> minHeapList = new ArrayList<> (list.size()+1); // initializes min heap list with size 1 greater than input ArrayList
        minHeapList.add(null); // puts null at index 0

        // iterates through tiles in input ArrayList
		for (int i=0; i<list.size(); i++) {
			addVertex(minHeapList, list.get(i)); // calls addVertex function to create a min heap list
		}
		return minHeapList;
	}

    // performs down-heap on queue
    private void downHeap (int start, int end) {
        int i=start; // sets parent index to start
        int child; // child index
        Tile tmp; // temporary variable

        // parent index is less than or equal to end index
        while (2*i <= end) {
            child = 2*i; // sets child index

            // child index is less than end index
            if (child < end) {

                // cost estimate of right-child tile is less than cost estimate of left-child tile
                if (queue.get(child+1).costEstimate < queue.get(child).costEstimate) {
                    child += 1; // sets child right child
                }
            }

            // cost estimate of child tile is less than cost estimate at parent
            if (queue.get(child).costEstimate < queue.get(i).costEstimate) {
                tmp = queue.get(child); // temporarily stores child tile
                queue.set(child,queue.get(i)); // stores parent tile at child index
                queue.set(i,tmp); // stores child tile at parent index
                i = child; // sets parent index to child index
            }

            // down-heap is complete
            else {
                break;
            }
        }
    }

    // performs up-heap on queue
    private void upHeap (int start, int end) {
        int i = start; // sets child index to start
        int parent; // parent index
        Tile tmp; // temporary variable

        // child index is greater than or equal to end index
        while (i/2 >= end) {
            parent = i/2; // sets parent index

            // cost estimate of parent tile is greater than cost estimate of child tile
            if (queue.get(parent).costEstimate > queue.get(i).costEstimate) {
                tmp = queue.get(parent); // temporarily stores parent tile
                queue.set(parent,queue.get(i)); // stores child tile at parent child index
                queue.set(i,tmp); // stores parent tile at child index
                i = parent; // sets child index to parent index
            }

            // up-heap is complete
            else {
                break;
            }
        }
    }
	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {

        // queue has more than one tile remaining (null object at index 0)
        if (queue.size() > 2) {
            Tile tmp = queue.get(1); // temporary variable that stores first tile in queue
            queue.set(1, queue.remove(queue.size() - 1)); // stores last tile in queue at index 1
            downHeap(1, queue.size() - 1); // performs down-heap to maintain min heap properties
            return tmp; // returns former first tile in queue
        }

        // queue has one tile remaining
        else {
            return queue.get(1); // returns first tile in queue
        }
	}
	
	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {

        // queue contains t and t is not null
        if (queue.contains(t) && t != null) {
            t.predecessor = newPred; // updates t's predecessor
            t.costEstimate = newEstimate; // updates t's cost estimate

            // if queue has more than one tile remaining in queue
            if (queue.size() > 2) {
                int i = queue.indexOf(t); // index of t

                // cost estimate of t is greater than cost estimate of its left child and left child index is not out of bounds
                if (2 * i < queue.size() && t.costEstimate > queue.get(2 * i).costEstimate) {
                    downHeap(i, queue.size() - 1); // perform down-heap
                }

                // cost estimate of t is greater than cost estimate of its right child and right child index is not out of bounds
                else if (2 * i + 1 < queue.size() && t.costEstimate > queue.get(2 * i + 1).costEstimate) {
                    downHeap(i, queue.size() - 1); // perform down-heap
                }

                // parent of t is not null and cost estimate of t is less than cost estimate of its parent
                else if (queue.get(i/2) != null && t.costEstimate < queue.get(i / 2).costEstimate) {
                    upHeap(i, 1); // perform upheap
                }
            }
        }
	}
}
