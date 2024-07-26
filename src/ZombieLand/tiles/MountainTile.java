package ZombieLand.tiles;

import ZombieLand.system.Tile;
import ZombieLand.system.TileType;

public class MountainTile extends Tile {
    //TODO level 0: finish constructor
    public MountainTile() {
        super(100,100,100); // calls constructor from Tile class
        this.type = TileType.Moutain; // sets type to Mountain
    }
}
