package com.SplitPlanet.BareBones.Tiles.TileClass;

import java.util.ArrayList;

import com.SplitPlanet.BareBones.Assets;
import com.SplitPlanet.BareBones.MenuItems.MIAssets;
import com.SplitPlanet.BareBones.MenuItems.MIType;
import com.SplitPlanet.BareBones.MenuItems.menuItem;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.BareBones.Tiles.TileType;
import com.SplitPlanet.BareBones.Tiles.baseTile;
import com.SplitPlanet.framework.Image;
import com.SplitPlanet.BareBones.Enemies.StatusEffectEnum;
import com.SplitPlanet.BareBones.Enemies.enemy;

public class WaterTile2 extends baseTile
{
	
	Image tileImage = Assets.basicWaterTile;
	
	public WaterTile2()
	{
	    super();
	    type       = TileType.WATER_TILE2;
	    damage     = 200;
	    maxCount   = 20;
	    menuItems.add(MIAssets.getMenuItem(MIType.BLANK_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK));
	    canAttack  = true;
	    canPower   = true;
	    canProducePower = false;
	    distance   = 300;
	    disSquared = distance * distance;
	    
	    // power constants
	    powerUse = 3000;
	    maxPower = 20000;
	}

	@Override
	public Image getTileImage()
	{
		return Assets.basicWaterTile2;
	}

    @Override
    public Tile _onBack(MIType backType)
    {
        // default is to return itself
        Tile retTile = new WaterTile2();

        switch (backType)
        {
            case BLANK_TOWER:
                retTile = new BlankTile();
                break;
        }

        return retTile;
    }
    
    protected void _onAttack(ArrayList<enemy> enemies,
                             enemy            inEnemy)
    {
        inEnemy.doDamage(damage);
        inEnemy.setTargetedSimple(2);
        powerCount -= powerUse;
    }

}
