package com.SplitPlanet.BareBones.Tiles.TileClass;

import com.SplitPlanet.BareBones.Assets;
import com.SplitPlanet.BareBones.MenuItems.MIAssets;
import com.SplitPlanet.BareBones.MenuItems.MIType;
import com.SplitPlanet.BareBones.MenuItems.menuItem;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.BareBones.Tiles.TileType;
import com.SplitPlanet.BareBones.Tiles.baseTile;
import com.SplitPlanet.framework.Image;

public class BlankTile extends baseTile
{
	public BlankTile()
	{
	    super();
	    type    = TileType.BLANK_TILE;
	    canWalk = true;
	    menuItems.add(MIAssets.getMenuItem(MIType.WATER_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.SLOW_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.PAYMENT_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.POISON_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.POWER_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.POWERLONG_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.POWERMULTI_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.ARROW_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.TOWER_INFO));
	    menuItems.get(menuItems.size()-1).miInfo = "This is a blank tower";
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK));
	}

	@Override
	public Image getTileImage()
	{
		return Assets.basicBlankTile;
	}

    @Override
    public Tile _onBack(MIType backType)
    {

        Tile  ret = new BlankTile();
        switch (backType)
        {
            case WATER_TOWER:
                ret = new WaterTile();
                break;
            case SLOW_TOWER:
                ret = new SlowTile();
                break;
            case PAYMENT_TOWER:
                ret = new MoneyIncTile();
                break;
            case POISON_TOWER:
                ret = new PoisonTile();
                break;
            case POWER_TOWER:
                ret = new PowerTower();
                break;
            case POWERLONG_TOWER:
                ret = new PowerTowerLong();
                break;
            case POWERMULTI_TOWER:
                ret = new PowerTowerMulti();
                break;
            case ARROW_TOWER:
                ret = new ArrowTower();
                break;
        }

        return ret;
    }
}
