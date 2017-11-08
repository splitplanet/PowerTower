package com.SplitPlanet.BareBones.Tiles.TileClass;

import java.util.ArrayList;

import com.SplitPlanet.BareBones.Assets;
import com.SplitPlanet.BareBones.MenuItems.MIAssets;
import com.SplitPlanet.BareBones.MenuItems.MIType;
import com.SplitPlanet.BareBones.MenuItems.menuItem;
import com.SplitPlanet.BareBones.PathFinder.pathCalc;
import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.BareBones.Tiles.TileState;
import com.SplitPlanet.BareBones.Tiles.TileType;
import com.SplitPlanet.BareBones.Tiles.baseTile;
import com.SplitPlanet.framework.Image;

public class PowerTowerLong extends baseTile
{
	

	public PowerTowerLong()
	{
	    super();
	    type             = TileType.POWERLONG_TILE;
	    canProducePower  = true;
	    canPower         = true;
	    powerProd        = 0;
	    maxCount         = 1000;
	    maxPower         = 30000;
	    maxPowerTransfer = 1000;
	    maxTowersToPower = 1;
	    distance         = 1000;
	    disSquared       = distance * distance;
	    menuItems.add(MIAssets.getMenuItem(MIType.SELECT_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BLANK_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK));
	}

	@Override
	public Image getTileImage()
    {
        return Assets.basicPowerLongTile;
    }

    @Override
    public Tile _onBack(MIType backType)
    {
        Tile  ret = new PowerTowerLong();
        
        switch (backType)
        {
            case SELECT_TOWER:
                ret.setTileState(TileState.SELECT_TOWER);
                break;
            case BLANK_TOWER:
                ret = new BlankTile();
                break;
        }

        return ret;
    }
	
	

}
