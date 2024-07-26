package ZombieLand.tiles;

import ZombieLand.system.Tile;
import ZombieLand.system.TileType;

public class ZombieInfectedRuinTile extends Tile {
    //TODO level 0: finish constructor
    public ZombieInfectedRuinTile() {
        super(1,3,5); // calls constructor from Tile class
        this.type = TileType.ZombieInfectedRuin; // sets type to Zombie Infected Ruin
    }
}
