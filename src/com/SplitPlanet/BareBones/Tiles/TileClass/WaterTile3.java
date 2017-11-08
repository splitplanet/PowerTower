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

public class WaterTile3 extends baseTile
{
	
	Image tileImage = Assets.basicWaterTile;
	
	public WaterTile3()
	{
		super();
		canWalk = false;

        menuItems.add(MIAssets.getMenuItem(MIType.BACK));

	}

	@Override
	public Image getTileImage()
	{
		return Assets.basicWaterTile;
	}

    @Override
    public Tile _onBack(MIType backType)
    {
        // default is to return itself
        Tile retTile = new WaterTile3();

        return retTile;
    }

}
