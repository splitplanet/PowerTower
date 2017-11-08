package com.SplitPlanet.BareBones;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.SplitPlanet.framework.Game;
import com.SplitPlanet.framework.Graphics;
import com.SplitPlanet.framework.Screen;
import com.SplitPlanet.framework.Input.TouchEvent;
import com.SplitPlanet.framework.implementation.AndroidGame;
import com.SplitPlanet.BareBones.Animations.MultiAnimation;
import com.SplitPlanet.BareBones.Enemies.StatusEffectEnum;
import com.SplitPlanet.BareBones.Enemies.enemy;
import com.SplitPlanet.BareBones.Enemies.statusEffect;
import com.SplitPlanet.BareBones.Enemies.types.Troop1;
import com.SplitPlanet.BareBones.Enemies.types.basicEnemy;
import com.SplitPlanet.BareBones.MenuItems.MIAssets;
import com.SplitPlanet.BareBones.MenuItems.MIType;
import com.SplitPlanet.BareBones.MenuItems.menuItem;
import com.SplitPlanet.BareBones.PathFinder.pathCalc;
import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.TileClass.BlankTile;
import com.SplitPlanet.BareBones.Tiles.TileClass.MenuTile;
import com.SplitPlanet.BareBones.Tiles.TileClass.WaterTile;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.BareBones.Tiles.TileState;
import com.SplitPlanet.BareBones.Map.MapPlayer;
import com.SplitPlanet.BareBones.Map.ReadLevel;
import com.SplitPlanet.SaveGame.SaveGame;

public class GameScreen extends Screen
{
	
	static Context context = AndroidGame.getContext();
	
	enum GameState {
		Ready,
		Running,
		GameRunning,
		TileMenu,
		GameRunningEnemies,
		Paused,
		GameOver,
		infoScreen
	}
	// Variable Setup

	//private static Activity act;
	
	public boolean check;
	public String Score = "0";
	public String name ="";
	private int timer = 0;
	public int gameoverscreen;
	public static GameState state = GameState.Ready;
	public int longhold,startx,starty,starty2;
	public double ti;
	static int slowDown = 0;
	
	private int  menuItemTileX,menuItemTileY;
	private Tile menuItemTile = new MenuTile();
	
	private int revelvanttime = 0;
	
	//private static sql sql;
	public int touchcount;
	private static Screen instance;
    private static Background bg1, bg2;
	private static boolean menuTop,
	                       displayPowerLines;
	int livesLeft = 1;
	Paint paint, paint2, paint3;
	pathCalc pathDummy = new pathCalc();
	public ReadLevel RL = new ReadLevel();
	public MapPlayer ML = new MapPlayer();
	

	private String infoText = "";
	
	/************* class variables **********/
	private ArrayList<menuItem>           myMenuItems  = new ArrayList<menuItem>();
	private ArrayList<enemy>              enemies      = new ArrayList<enemy>();
	private ArrayList<MultiAnimation>     animations   = new ArrayList<MultiAnimation>();
	private ArrayList<ArrayList<Tile>>    tilearray    = new ArrayList<ArrayList<Tile>>();
	private
	    ArrayList<ArrayList<ArrayList<Integer>>> 
	                                      enemyInTiles = new ArrayList<ArrayList<ArrayList<Integer>>>();
	private ArrayList<ArrayList<String>>  level        = new ArrayList<ArrayList<String>>();
	//private ArrayList<ArrayList<ArrayList<Integer>>> quadantArray = new ArrayList<ArrayList<ArrayList<Integer>>>();

	/************* class consts *************/
	private static int width          = 12,
	                   height         = 12,
	                   quadWidth      = 2,
	                   quadHeight     = 2,
	                   realWidth      = 800,
	                   realHeight     = 480,
	                   funds          = 0,
	                   lives          = 10,
	                   quadRealWidth  = 600,
	                   quadRealHeight = 440;

	private static xy  startPoint = new xy(0,0),
	                   endPoint   = new xy(height-1,width-1);
	
    public static Screen getInstance()
    {
        return instance;
    }
    
    public GameScreen()
    {
    	super();
    }

	public GameScreen(Game game,InputStream level) throws IOException
    {

        super(game);
        
    	try {
    		 SaveGame SG = new SaveGame("");
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

        //basicBackground = Assets.basicBackground;
        bg1 = new Background(0, 0);
        menuTop = false;  
        displayPowerLines = true;
        /*Load map in*/
        //loadMap();
        RL.ReadLevelMethod(level);
        try {
			level.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        level = null;
        tilearray=RL.getTilearray();

        enemies=RL.getLevel();
        setupEnemiesInTiles(tilearray);
        
        updateParams();
		
        paint  = new Paint();
        paint2 = new Paint();
        paint3 = new Paint();

        /* set up the paint object */
        paint.setTextSize(30);

    	/* setting up the second paint object */
        paint2.setTextSize(50);
        paint2.setTypeface(Typeface.MONOSPACE);
        paint2.setColor(Color.RED);
        
        paint3.setStrokeWidth(4);
    	state = GameState.Ready;
		
	}
	
	private void updateParams()
	{
		height   = tilearray.size();
		width    = tilearray.get(0).size();
		endPoint = new xy(width-1,height-1);
	}
	
	/********************************* DEAL WITH MAP **********************/
	
	private void loadMap()
	{
		
	    // load tiles
		for (int i = 0; i < width; i++)
		{
			tilearray.add(new ArrayList<Tile>());
			for (int j=0 ; j < height; j++)
			{
				Tile t = new BlankTile();
				tilearray.get(i).add(t);
			}
		}

        //// load quadrants
        //for (int i = 0; i < quadWidth; i++)
        //{
        //	quadantArray.add(new ArrayList<ArrayList<Integer>>());
        //	for (int j=0 ; j < quadHeight; j++)
        //	{
        //		quadantArray.get(i).add(new ArrayList<Integer>());
        //	}
        //}	

        // load enemies
		//for (int i = 0; i < 1; i++)
		//{
        //    enemies.add(new basicEnemy(getPath(),0));
        //    enemies.add(new basicEnemy(getPath(),10));
        //    enemies.add(new basicEnemy(getPath(),20));
        //    enemies.add(new basicEnemy(getPath(),30));
        //    enemies.add(new Troop1(getPath(),40));
        //}
	}
	




	@Override
	public void update(float deltaTime)
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.
		
		switch (state)
		{
            case Ready:
                updateReady(touchEvents);
                break;
                
            case GameRunning:
			    try
			    {
			    	updateRunning(touchEvents);
			    } catch (InterruptedException e) {
			    	// TODO Auto-generated catch block
			    	e.printStackTrace();
			    }
                break;

            case Running:
                updateGameRunning(touchEvents, deltaTime, true);
                break;
                
            case TileMenu:
		        updateTileMenu(touchEvents, deltaTime);
                break;
                
            case infoScreen:
		        updateInfoScreen(touchEvents, deltaTime);
                break;
                
            case GameRunningEnemies:
                slowDown++;
                if (slowDown > 0)
                {
            	     ti++;
		             updateEnemies(touchEvents, deltaTime);
		             updateTowers();
                     updateGameRunning(touchEvents, deltaTime, false);
                     updateAnimations();
                     slowDown=0;
                }
                break;

            default:
                break;
        }

		/*if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
		*/
	}

	private void updateReady(List<TouchEvent> touchEvents)
	{
		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!
		Score = "0";
		state = GameState.Running;
		if (touchEvents.size() > 0)
		{
			Score="0";
			state = GameState.Running;
			touchcount++;
		}
	}

    /************************ BOUNDS *********************/
    //private boolean inBounds(TouchEvent event,
    //                         int        x,
    //                         int        y,
    //                         int        width,
    //		                 int        height)
    //{
    //	if (event.x > x && event.x < x + width - 1 && event.y > y
    //			&& event.y < y + height - 1)
    //		return true;
    //	else
    //		return false;
    //}
    
    private void updateRunning(List<TouchEvent> touchEvents) throws InterruptedException
    {
		/**************************** MAP **********************************/
		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		Thread.sleep(50);
		
		if (touchEvents.size() > 0)
			state = GameState.GameRunning;
		
		Thread.sleep(50);
		
	}

	private void updateGameRunning(List<TouchEvent> touchEvents,
	                               float            deltaTime,
	                               boolean          canInteract)
	{
	
		/**************************** BACKGROUND **********************************/
		//  screen dimensions
	    //  width 800
		//  height 480
		
		
		/************************** Moving around event ***************************/
		
        //if (ti%20==0 )
        //{
        //    revelvanttime ++;
        //    ML.PlayMap(level,revelvanttime,tilearray,startPoint,endPoint);
        //    enemies=ML.getMyenemy();
        //    if (enemies.size() > 0 ) state = GameState.GameRunningEnemies;
        //}
        int longHoldLimit = 3;
		
		
		/************************** Moving around event ***************************/
		for (TouchEvent event : touchEvents)
		{
			if (event.type == TouchEvent.TOUCH_DRAGGED)
			{
			    if (longhold > 0)
			    {
			        /*update background*/
			       	bg1.setBackX(bg1.getBackX() - (startx - event.x),
			       	           -(width*80) + 3 + realWidth,-3);
			       	bg1.setBackY(bg1.getBackY() - (starty - event.y),
			       	           -(height*80) + 3 + realHeight,-3);
			       	
			       	// decide where to put the menu
			       	placeMenu();
			    }

			    /*Remember starting position*/
			    startx = event.x;
			    starty = event.y;
				
				// detect long hold
			    if (longhold <= longHoldLimit)
			    {
				    longhold ++ ;
			    }
			}
			else if (event.type == TouchEvent.TOUCH_UP &&
					 longhold   < longHoldLimit &&
					 canInteract)
			{
			    if (calcMenu(event.x, event.y))
			    {
			        // store the menuTile
			        menuItemTile = new MenuTile();
			        // we have hit the menu
			        myMenuItems = menuItemTile.getMenuItems();
			        menuItemTileX = 0; menuItemTileY = 0;
			    }
			    else if (menuItemTile.getTileState() == TileState.SELECT_TOWER)
			    {
			        int curX = (event.x - bg1.getBackX())/80,
		                curY = (event.y - bg1.getBackY())/80;
			        tilearray.get(menuItemTileY)
			                 .get(menuItemTileX)
			                 .updateTowersToPower
			                     (new xy(curX,curY),
			                      new xy(menuItemTileX,menuItemTileY),
			                      tilearray);
			        menuItemTile.setTileState(TileState.FINE);
			    }
			    else
			    {
			        // here we want to bring up the games menu.
				    myMenuItems = calcPos(event.x, event.y).getMenuItems();
			    }
				bg2 = new Background(0, 0);
				state = GameState.TileMenu;
			}
			else
			{
			    longhold = 0; 
			}
		}
	}
	
    private void updateTileMenu(List<TouchEvent> touchEvents,
                                float            deltaTime)
	{
		/**************************** BACKGROUND **********************************/
		//  screen dimensions
	    //  width 800
		//  height 480
		/************************** Moving around event ***************************/
        int longHoldLimit = 10;
		
		for (TouchEvent event : touchEvents)
		{
			if (event.type == TouchEvent.TOUCH_DRAGGED &&
			    myMenuItems.size() > 6)
			{
			    if (longhold >= 1)
			    {
				    /*update background*/
				    bg2.setBackY(bg2.getBackY() - (starty2 - event.y),
				                 -((myMenuItems.size()-1) * 80)+3,-3);
			    }

				/*Remember starting position*/
				starty2 = event.y;

				/* Detect drag event */
				if (longhold < longHoldLimit)
				{
				    longhold ++;
				}
			}
			else if (event.type == TouchEvent.TOUCH_UP &&
			         longhold < longHoldLimit)
			{
			    menuItem mI = calcTileMenuPos(event.y);
			    
			    // miInt needs to be greater than zero
			    if (mI.miEnum != MIType.DEFAUT)
			    {
			        if ((event.x < 80 && mI.hasInfo) ||
			            mI.miEnum == MIType.TOWER_INFO)
			        {
			            infoText = mI.miInfo;
			            if (infoText.length() > 0)
			            {
			                state = GameState.infoScreen;
			            }
			        }
			        else
			        {
			            Tile temp = menuItemTile.
			                          onBack(mI.miEnum,
			                                 new xy(menuItemTileX,
			                                        menuItemTileY), // need to swap x and y
			                                 enemies,
			                                 tilearray,
			                                 this);

			            switch (temp.getTileState())
			            {
                            case FINE:
			                    if (menuItemTile.getType() != temp.getType())
			                    {
	                                tilearray
			                              .get(menuItemTileY)
			                              .set(menuItemTileX, temp);
	                                if (!temp.canPower())
	                                {
	                                    removePowerlines();
	                                }
	                                if (temp.canAttack())
	                                {
	                                    temp.
	                                      setUpTileToWatchToAttack(tilearray,
	                                                               new xy(menuItemTileX,
	                                                                      menuItemTileY));
	                                }
			                    }

                                state = GameState.Running;
                                break;

                            case SELECT_TOWER:
			                    menuItemTile = temp;
			                    state = GameState.Running;
                                break;

                            case START_GAME:
			                    //if (enemies.size() == 0)
			                    //{
			                    //    enemies.add(new basicEnemy(getPath(),0));
			                    //    enemies.add(new basicEnemy(getPath(),20));
			                    //    enemies.add(new basicEnemy(getPath(),40));
			                    //    enemies.add(new basicEnemy(getPath(),60));
			                    //    enemies.add(new Troop1(getPath(),80));
			                    //}
			                    updatePath();
			                    state = GameState.GameRunningEnemies; 
                                break;

                            case DISPLAY_POWERLINES:
                                displayPowerLines = displayPowerLines ^ true;

                                state = GameState.Running;
                                break;

                            case NO_PATH:
                            case NO_GOLD:
			                    // if we violate the path then we indicate this
			                    // by resetting the text
			                    mI.miText = Assets.
			                                errorCode.
			                                get(temp.getTileState());
			                    
                                break;

                            case BACK:
			                    // override for back button only! (type 1)
                                state = GameState.Running;
                                break;
                    
                            case MORE_MENU_ITEMS:
                                myMenuItems = temp.getMenuItems();
                                break;
                                
                            case BACK_TO_MAIN_MENU:
                                game.setScreen(new SplashLoadingScreen(game));
                            	break;
                        }
			        }
			    }
			}
			else
			{
			    longhold = 0;
			}
		}
	}
    private void updateInfoScreen(List<TouchEvent> touchEvents,
                                  float            deltaTime)
	{
		for (TouchEvent event : touchEvents)
		{
			 if (event.type == TouchEvent.TOUCH_UP)
			{
			    state = GameState.TileMenu;
			}
		}
	}  
    /********************* Update enemies ************************/
    private void updateEnemies(List<TouchEvent> touchEvents,
                               float            deltaTim)
    {
        //for (ArrayList<ArrayList<Integer>> arrayList : quadantArray) {
        //    for (ArrayList<Integer> arrayList2 : arrayList) {
        //        arrayList2.clear();
        //    }
        //}
        updateEnemiesInTiles();
        
        for (int j = enemies.size() -1;
                 j >= 0;
                 j--)
        {
            enemy en = enemies.get(j);
            if (en.getActive())
            {
                // update position
                en.updatePos();

                //// update quadrant
                //xy quadXy = calcQuad(enemy.getPos());
                //quadantArray.get(quadXy.x).get(quadXy.y).add(i);
                
                // update targeted
                en.updateTargeted();
            
                // Do we need to delete the enemy?
                // this can happen in two cases
                // 1) the enemy has died (delete + payout)
                // 2) the enemy has reached the end of the map (delete)
                if (en.getCurHealth() < 0)
                {
                    funds += en.getPayout();
                    en.onDeath(enemies,tilearray);
                    enemies.remove(j);
                }
                else if (!en.updateStep())
                {
                    enemies.remove(j);
                    lives--;
                }
            }
            else
            {
                en.updateCount();
            }
        }
        
        // if no enemies are left then we 
        // are back in the normal mode
        if (enemies.size() == 0)
        {
            state = GameState.Running ;
        }
        else
        {
            // else we update their tile location
            for (int k = 0; k < enemies.size(); k++)
            {
                xy pos = enemies.get(k).getPos();
                enemyInTiles.get(pos.y / 80).get(pos.x / 80).add(k);
            }
        }
    }
    
    
    /********************* Update path **************************/
    private void updateTowers()
    {
        int i = 0, j = 0;
        // we only update towers that you can't walk on
        // we only care if the tile is at it's attack time
        for (ArrayList<Tile> tiles  : tilearray)
        {
            for (Tile tile : tiles)
            {
                if ((tile.canAttack() &&
                     tile.updateCounter()) ||
                    tile.canProducePower())
                {
                    tile.TileUpdate(enemies,
                                    tilearray,
                                    enemyInTiles,
                                    new xy(j,i));
                }
                j++;
            }
            i++; j=0;
        }
    }

    /********************* Update path **************************/
    private void updatePath()
    {
        for (enemy en : enemies)
        {
            en.setPath(getPath(en));
        }
    }

    /********************* Update path **************************/
    private void updateAnimations()
    {
        for (int i = animations.size() - 1;
                 i > 0;
                 i--)
        {
            if (!animations.get(i).update())
            {
                animations.remove(i);
            }
        }
    }
    
    /******************* Get path *******************************/
    private ArrayList<xy> getPath(enemy en)
    {
        return pathDummy.calcPath(tilearray, en.getStartPos(), en.getEndPos());
    }

	@Override
	public void paint(float deltaTime)
	{
        Graphics g = game.getGraphics();

        switch (state)
        {
            case Running:
                PaintUtils.paintTiles(g,tilearray,bg1,enemies);
                PaintUtils.paintPoweredTowers(g,
                                              tilearray,
                                              bg1,
                                              paint3,
                                              displayPowerLines);
                PaintUtils.paintMenu(g,menuTop);
                PaintUtils.paintHeader(g, paint2, lives, funds);
                break;

            case TileMenu:
                PaintUtils.paintMenuItems(g,
                                          myMenuItems,
                                          bg2,
                                          paint,
                                          realWidth);
                break;

            case GameRunningEnemies:
                PaintUtils.paintTiles(g,tilearray,bg1,enemies);
                PaintUtils.paintEnemies(g, enemies, bg1);
                PaintUtils.paintPoweredTowers(g,
                                              tilearray,
                                              bg1,
                                              paint3,
                                              displayPowerLines);
                PaintUtils.paintAnimations(g, animations);
                PaintUtils.paintMenu(g,menuTop);
                PaintUtils.paintHeader(g, paint2, lives, funds);
                break;

            case infoScreen:
                PaintUtils.paintInfo(g, infoText, realWidth, realHeight, paint);
                break;

            default:
                break;
        }
    }
	
	private Tile calcPos(int x, int y)
	{
		int curX = (x - bg1.getBackX())/80,
		    curY = (y - bg1.getBackY())/80;	

		menuItemTile = tilearray.get(curY).get(curX);
		menuItemTileX = curX;
		menuItemTileY = curY;
		return menuItemTile;
	}
	
	//private xy calcQuad(xy pos)
	//{
	//	return new xy(pos.x/quadRealWidth, pos.y/quadRealHeight);
	//}
	
	private menuItem calcTileMenuPos(int y)
	{
		int curY = (y - bg2.getBackY())/80;
		menuItem ret = MIAssets.getMenuItem(MIType.DEFAUT);
		
		if (curY > -1 &&
		    curY < myMenuItems.size())
		{
		    ret = myMenuItems.get(curY);
		}
		
		return ret;
	}
	
	private void placeMenu()
	{
	    if (bg1.getBackX() < -200 &&
		    bg1.getBackY() < -235)
		{
		    menuTop = true;
		}
		else
		{
		    menuTop = false;
		}
	}
	
	private boolean calcMenu (int x, int y)
	{
	    return (menuTop  && x < 80  && y < 80) ||
	           (!menuTop && x > 720 && y > 400);
	}
	
	public boolean useGold(int cost)
	{
	    boolean ret = false;
	    
	    if (funds >= cost)
	    {
	        funds -= cost;
	        ret = true;
	    }
	    
	    return ret;
	}
	
	private void removePowerlines()
	{
	    for (ArrayList<Tile> arrayList : tilearray)
	    {
            for (Tile tile : arrayList)
            {
                if (tile.canProducePower())
                {
                    ArrayList<xy> tileXys = tile.getTowersToPower();
                    for (int i = tileXys.size() - 1;
                             i >= 0;
                             i--)
                    {
                        xy curXy = tileXys.get(i);
                        if (curXy.x == menuItemTileX &&
                            curXy.y == menuItemTileY)
                        {
                            tileXys.remove(i);
                        }
                    }
                }
            }
        }
	}

	@Override
	public void pause()
	{
		if (state == GameState.GameRunning)
		{
			state = GameState.Paused;
		}
	}
	
	private void setupEnemiesInTiles(ArrayList<ArrayList<Tile>> tiles)
	{
	    int i =0, j=0;
	    for (ArrayList<Tile> arrayList : tiles)
	    {
	        enemyInTiles.add(new ArrayList<ArrayList<Integer>>());
            for (Tile tile : arrayList)
            {
               enemyInTiles.get(i).add(new ArrayList<Integer>());
            }
            i++;
        }
	}
	
	private void updateEnemiesInTiles()
	{
	    for (ArrayList<ArrayList<Integer>> arrayList : enemyInTiles)
	    {
            for (ArrayList<Integer> arrayList2 : arrayList)
            {
                arrayList2.clear();
            }
        }
	}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

	@Override
	public void backButton() {}

	private void goToMenu() {}
}