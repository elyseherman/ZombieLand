package ZombieLand.tiles;

import ZombieLand.system.Tile;
import ZombieLand.system.TileType;

public class MetroTile extends Tile {
	public double metroTimeCost = 100;
	public double metroDistanceCost = 100;
	public double metroCommuteFactor = 0.2;
	
    //TODO level 0: finish constructor
    public MetroTile() {
        super(1,1,2); // calls constructor from Tile class
        this.type = TileType.Metro; // sets type to Metro
    }
    
    // TODO level 7: updates the distance and time cost differently between metro tiles
    public void fixMetro(Tile node) {
        if (node instanceof MetroTile) {
            double manhattanDistance = Math.abs(this.xCoord - node.xCoord) + Math.abs(this.yCoord - node.yCoord);

            this.metroTimeCost = manhattanDistance * metroCommuteFactor;
            ((MetroTile) node).metroTimeCost = manhattanDistance * metroCommuteFactor;

            this.metroDistanceCost = manhattanDistance / metroCommuteFactor;
            ((MetroTile) node).metroDistanceCost = manhattanDistance / metroCommuteFactor;
        }
    }
}
