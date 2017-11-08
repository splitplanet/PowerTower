package com.SplitPlanet.BareBones;

import java.io.IOException;
import java.util.HashMap;

import com.SplitPlanet.BareBones.Tiles.TileState;
import com.SplitPlanet.framework.FileIO;
import com.SplitPlanet.framework.Game;
import com.SplitPlanet.framework.Graphics;
import com.SplitPlanet.framework.Screen;
import com.SplitPlanet.framework.Graphics.ImageFormat;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game)
	{
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		
		Graphics g = game.getGraphics();
		FileIO FIO = game.getFileIO();
		Assets.menu                  = g.newImage("menu.png", ImageFormat.RGB565);

		/*************** backgrounds *************/
		Assets.basicBackground       = g.newImage("Basic_game_background.png", ImageFormat.RGB565);
		Assets.blackBackground       = g.newImage("Black_Background.png", ImageFormat.RGB565);
		
		/*************** tiles *******************/
		Assets.basicBlankTile        = g.newImage("Blank_Tile.png", ImageFormat.RGB565);
		Assets.basicWaterTile        = g.newImage("Basic_water.png", ImageFormat.RGB565);
		Assets.basicWaterTile2       = g.newImage("Basic_water2.png", ImageFormat.RGB565);
		Assets.arrowTower            = g.newImage("ArrowTower.png", ImageFormat.RGB565);
		Assets.basicSlowTile         = g.newImage("Basic_slow.png", ImageFormat.RGB565);
		Assets.basicMoneyIncTile     = g.newImage("Basic_MoneyInc.png", ImageFormat.RGB565);
		Assets.basicPoisonTile       = g.newImage("Basic_PoisonTower.png", ImageFormat.RGB565);
		Assets.basicPowerTile        = g.newImage("Basic_power_long.png", ImageFormat.RGB565);
		Assets.basicPowerLongTile    = g.newImage("Basic_power_multi.png", ImageFormat.RGB565);
		Assets.basicPowerMultiTile   = g.newImage("Basic_power.png", ImageFormat.RGB565);
		Assets.menuTile              = g.newImage("menuTile.png", ImageFormat.RGB565);
        Assets.basicRoadTile         = g.newImage("basic_road.png", ImageFormat.RGB565);
		        
        /*************** Levels *****************/
        try
        {
            Assets.level1               = FIO.readAsset("level1.txt");
            Assets.level2               = FIO.readAsset("level2.txt");
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Assets.levelStore = FIO;
        //Assets.levelMap.put("level1",Assets.level1);
        //Assets.levelMap.put("level2",Assets.level2);
		
		/*************** enemies *****************/
		Assets.basic_enemy           = g.newImage("basicEnemy.png", ImageFormat.RGB565);
		Assets.basic_enemy2          = g.newImage("basicEnemy2.png", ImageFormat.RGB565);
		Assets.troop1_1              = g.newImage("Enemy1/Image1.png", ImageFormat.RGB565);
		Assets.troop1_2              = g.newImage("Enemy1/Image2.png", ImageFormat.RGB565);
		Assets.troop1_3              = g.newImage("Enemy1/Image3.png", ImageFormat.RGB565);
		Assets.troop1_4              = g.newImage("Enemy1/Image4.png", ImageFormat.RGB565);
		
		/*************** error codes *************/
		Assets.errorCode = new HashMap<TileState, String>();
		Assets.errorCode.put(TileState.NO_PATH, "There is no viable path");
		Assets.errorCode.put(TileState.NO_GOLD, "Not enough gold");
		

		game.setScreen(new MainMenuScreen(game));

	}

	@Override
	public void paint(float deltaTime)
	{
		Graphics g = game.getGraphics();
		g.drawImage(Assets.splash, 0, 0);
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public void backButton()
    {
	}


}