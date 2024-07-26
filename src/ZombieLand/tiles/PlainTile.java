package ZombieLand.tiles;

import ZombieLand.system.Tile;
import ZombieLand.system.TileType;

public class PlainTile extends Tile {
    //TODO level 0: finish constructor
    public PlainTile() {
        super(3,1,0); // calls constructor from Tile class
        this.type = TileType.Plain; // sets type to Plain
    }
}
