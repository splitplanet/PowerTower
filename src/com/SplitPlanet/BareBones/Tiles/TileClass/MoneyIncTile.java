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

public class MoneyIncTile extends baseTile
{
	
	Image tileImage = Assets.basicWaterTile;
	
	public MoneyIncTile()
	{
	    super();
	    type       = TileType.PAYMENT_TILE;
	    damage     = 100;
	    maxCount   = 50;
	    menuItems.add(MIAssets.getMenuItem(MIType.BLANK_TOWER));
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK));
	    canAttack  = true;
	    canPower   = true;
	    distance   = 200;
	    disSquared = distance * distance;
	    
	    // power constants
	    powerUse = 0;
	    maxPower = 500;
	}

	@Override
	public Image getTileImage()
	{
		return Assets.basicMoneyIncTile;
	}

    @Override
    public Tile _onBack(MIType backType)
    {
        // default is to return itself
        Tile retTile = new MoneyIncTile();

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
                new statusEffect(500,
                                 StatusEffectEnum.PAYOUT,
                                 1.5));
        powerCount -= powerUse;
    }

}
