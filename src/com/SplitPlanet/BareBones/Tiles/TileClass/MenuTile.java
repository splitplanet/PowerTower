package com.SplitPlanet.BareBones.Tiles.TileClass;

import com.SplitPlanet.BareBones.Assets;
import com.SplitPlanet.BareBones.MenuItems.MIAssets;
import com.SplitPlanet.BareBones.MenuItems.MIType;
import com.SplitPlanet.BareBones.MenuItems.menuItem;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.BareBones.Tiles.TileState;
import com.SplitPlanet.BareBones.Tiles.TileType;
import com.SplitPlanet.BareBones.Tiles.baseTile;
import com.SplitPlanet.framework.Image;

public class MenuTile extends baseTile
{
	public MenuTile()
	{
	    super();
	    type = TileType.MENU_TILE;
	    menuItems.add(MIAssets.getMenuItem(MIType.START));
	    menuItems.add(MIAssets.getMenuItem(MIType.DISPLAY_POWERLINES));
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK_TO_MAIN_MENU));
	    menuItems.add(MIAssets.getMenuItem(MIType.BACK));
	}

	@Override
	public Image getTileImage() {
		return Assets.menuTile;
	}

    @Override
    public Tile _onBack(MIType backType)
    {
        // default is to return itself
        Tile retTile = new MenuTile();
        
        switch(backType)
        {
            case START:
                retTile.setTileState(TileState.START_GAME);
                break;
            case DISPLAY_POWERLINES:
                retTile.setTileState(TileState.DISPLAY_POWERLINES);
                break;
            case BACK_TO_MAIN_MENU:
                retTile.setTileState(TileState.BACK_TO_MAIN_MENU);
                break;
        }

        return retTile;
    }

}
