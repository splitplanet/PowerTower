package com.SplitPlanet.BareBones.Tiles;

import com.SplitPlanet.framework.Image;

import java.util.ArrayList;

import com.SplitPlanet.BareBones.GameScreen;
import com.SplitPlanet.BareBones.Enemies.enemy;
import com.SplitPlanet.BareBones.MenuItems.MIType;
import com.SplitPlanet.BareBones.MenuItems.menuItem;
import com.SplitPlanet.BareBones.PathFinder.xy;

public interface Tile
{
    // get the tile image
	public Image getTileImage();
    // get the tile image
	public Image getTileImage2();

	// set the tile image 
	//public void setTileImage(Image titleImage);

	// get the menu items available for this tile
	public ArrayList<menuItem> getMenuItems();

	// get the type of the tile
    public TileType getType();

    // set the type (this is not needed really)
	public void setType(TileType typeInt);

    // Can the image be rotated
	public boolean canRotate();

    // Can the image be rotated
	public int getAngle();

    // Can the image be rotated
	public int getUpdateAngle();

    // get the current enemy being targeted
	public int getCurEnemy();
	
	// what to do on back, returns if it was successful
	// i.e. lack of gold would cause this to fail
	// returns a non-null tile if the the tile is to change
	public Tile onBack(MIType                     backTypet,
	                   xy                         pos,
	                   ArrayList<enemy>           enemies,
	                   ArrayList<ArrayList<Tile>> map,
	                   GameScreen                 inGameScreen);
	
	// Tile specific on back
	//Tile _onBack(int backTypetp);
	
	
	// can we walk on the tile?
	public boolean canWalk();
	
	// set the tileState
	public void setTileState(TileState stateIn);
	
	// set the tileState
	public TileState getTileState();
	
	// Update Tile (attacking)
	public void TileUpdate(ArrayList<enemy>           enemies,
	                       ArrayList<ArrayList<Tile>> tiles,
             ArrayList<ArrayList<ArrayList<Integer>>> enemiesInTiles,
	                       xy                         tower);
	
	// returns true if the tower is ready to fire
	public boolean updateCounter();
	
	// Resets the counter
	public void resetCounter();
	
	// can attack?
	public boolean canAttack();
	
	// can power?
	public boolean canPower();
	
	// Get towers to power
	public ArrayList<xy> getTowersToPower();
	
	// add Power
	public void addPower(int inPower);
	
	// Can produce power?
	public boolean canProducePower();
	
	// can use gold?
	public int getCost();
	
	// get maximum storable power
	public int getMaxPower();
	
	// get current stored power
	public int getCurPower();

	// use to add/remove tiles from 'power' list
	public Tile updateTowersToPower(xy                         pos,
	                                xy                         myPos,
                                    ArrayList<ArrayList<Tile>> tiles);
	
    // updates the power animation double and returns it
	public double getPowerAnimation();
	
	// get colour for power animation
	public int getPowerColor();
	
	// is the tower fully powered
	public boolean isTowerFullyPowered();

	// set up the tiles we're interested in
    public void setUpTileToWatchToAttack(ArrayList<ArrayList<Tile>> tiles,
                                         xy                         pos);

}
