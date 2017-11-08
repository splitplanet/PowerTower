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

public class PowerTower extends baseTile
{
	

	public PowerTower()
	{
	    super();
	    type             = TileType.POWER_TILE;
	    canProducePower  = true;
	    powerProd        = 300;
	    maxCount         = 1000;
	    maxPower         = 1000;
	    maxPowerTransfer = 300;
	    maxTowersToPower = 5;
	    distance         = 200;
	    disSquared       = distance * distance;
	    menuItems.add(MIAssets.getMenuItem(MIType.SELECT_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BLANK_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK));
	}

	@Override
	public Image getTileImage()
    {
        return Assets.basicPowerTile;
    }

    @Override
    public Tile _onBack(MIType backType)
    {
        Tile  ret = new PowerTower();
        
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
