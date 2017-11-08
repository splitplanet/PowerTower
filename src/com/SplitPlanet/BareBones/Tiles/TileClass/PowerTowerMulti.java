package com.SplitPlanet.BareBones.Tiles.TileClass;


import com.SplitPlanet.BareBones.Assets;
import com.SplitPlanet.BareBones.MenuItems.MIAssets;
import com.SplitPlanet.BareBones.MenuItems.MIType;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.BareBones.Tiles.TileState;
import com.SplitPlanet.BareBones.Tiles.TileType;
import com.SplitPlanet.BareBones.Tiles.baseTile;
import com.SplitPlanet.framework.Image;

public class PowerTowerMulti extends baseTile
{

	public PowerTowerMulti()
	{
	    super();
	    type             = TileType.POWERMULTI_TILE;
	    canProducePower  = true;
	    canPower         = true;
	    powerProd        = 0;
	    maxCount         = 1000;
	    maxPower         = 30000;
	    maxPowerTransfer = 600;
	    maxTowersToPower = 10;
	    distance         = 400;
	    disSquared       = distance * distance;
	    menuItems.add(MIAssets.getMenuItem(MIType.SELECT_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BLANK_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK));
	}

	@Override
	public Image getTileImage()
    {
        return Assets.basicPowerMultiTile;
    }

    @Override
    public Tile _onBack(MIType backType)
    {
        Tile  ret = new PowerTowerMulti();
        
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
