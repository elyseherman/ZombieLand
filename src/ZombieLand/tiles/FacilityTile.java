package ZombieLand.tiles;

import ZombieLand.system.Tile;
import ZombieLand.system.TileType;

public class FacilityTile extends Tile {
    //TODO level 0: finish constructor
    public FacilityTile() {
        super(1,2,0); // calls constructor from Tile Class
        this.type = TileType.Facility; // sets type to Facility
    }
}
