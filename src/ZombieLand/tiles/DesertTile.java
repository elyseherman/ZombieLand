package ZombieLand.tiles;

import ZombieLand.system.Tile;
import ZombieLand.system.TileType;

public class DesertTile extends Tile {
    //TODO level 0: finish constructor
    public DesertTile() {
        super(2,6,3); // calls constructor in Tile class
        this.type = TileType.Desert; // sets type to Desert
    }
}
