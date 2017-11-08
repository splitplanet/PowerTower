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
import com.SplitPlanet.BareBones.Enemies.statusEffect;

public class SlowTile extends baseTile
{
	
	Image tileImage = Assets.basicWaterTile;
	
	public SlowTile()
	{
	    super();
	    type       = TileType.SLOW_TILE;
	    damage     = 100;
	    maxCount   = 20;
	    menuItems.add(MIAssets.getMenuItem(MIType.BLANK_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK));
	    canAttack  = true;
	    canPower   = true;
	    canProducePower = false;
	    distance   = 200;
	    disSquared = distance * distance;
	    
	    // power constants
	    powerUse = 0;
	    maxPower = 500;
	}

	@Override
	public Image getTileImage()
	{
		return Assets.basicSlowTile;
	}

    @Override
    public Tile _onBack(MIType backType)
    {
        // default is to return itself
        Tile retTile = new SlowTile();

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
        inEnemy.setTargeted(
                new statusEffect(50,
                                 StatusEffectEnum.SPEED,
                                 0.5));
        powerCount -= powerUse;
    }

}
