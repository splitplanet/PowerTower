package com.SplitPlanet.BareBones.Tiles.TileClass;

import com.SplitPlanet.BareBones.Assets;
import com.SplitPlanet.BareBones.MenuItems.MIAssets;
import com.SplitPlanet.BareBones.MenuItems.MIType;
import com.SplitPlanet.BareBones.MenuItems.menuItem;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.BareBones.Tiles.TileType;
import com.SplitPlanet.BareBones.Tiles.baseTile;
import com.SplitPlanet.framework.Image;

public class RoadTile extends baseTile
{
	public RoadTile()
	{
	    super();
	    type    = TileType.ROAD_TILE;
	    canWalk = true;
	    menuItems.add(MIAssets.getMenuItem(MIType.BLANK_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK));
	}

	@Override
	public Image getTileImage()
    {
        return Assets.basicRoadTile;
    }

    @Override
    public Tile _onBack(MIType backType)
    {
        Tile  ret = new RoadTile();

        switch (backType)
        {
            case BLANK_TOWER:
                ret = new BlankTile();
                break;
        }

        return ret;
    }
}
