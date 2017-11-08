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

public class ArrowTower extends baseTile
{
	
	Image tileImage = Assets.basicWaterTile;
	
	public ArrowTower()
	{
	    super();
	    type       = TileType.ARROW_TILE;
	    damage     = 100;
	    maxCount   = 10;
	    menuItems.add(MIAssets.getMenuItem(MIType.BLANK_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK));
	    canAttack  = true;
	    canPower   = true;
	    canProducePower = false;
	    distance   = 200;
	    disSquared = distance * distance;
	    canRotate  = true;
	    rotationAngle  = 0;
	    
	    // power constants
	    powerUse = 2000;
	    maxPower = 10000;
	}

	@Override
	public Image getTileImage()
	{
		return Assets.arrowTower;
	}

	@Override
	public Image getTileImage2()
	{
		return Assets.basicWaterTile;
	}

    @Override
    public Tile _onBack(MIType backType)
    {
        // default is to return itself
        Tile retTile = new ArrowTower();

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
