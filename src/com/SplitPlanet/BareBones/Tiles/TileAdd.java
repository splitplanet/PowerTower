package com.SplitPlanet.BareBones.Tiles;

import com.SplitPlanet.BareBones.Tiles.TileClass.BlankTile;
import com.SplitPlanet.BareBones.Tiles.TileClass.RoadTile;
import com.SplitPlanet.BareBones.Tiles.TileClass.WaterTile3;

public class TileAdd
{
    public TileAdd () {}

    public static Tile tileSelector (char in)
    {
        Tile ret = new BlankTile();
        switch (in)
        {
            case '1' :
                ret = new RoadTile();
                break;

            case 'x' :
                ret = new BlankTile();
                break;

            case 'W' :
                ret = new WaterTile3();
                break;
        }
        return ret;
    }

}
