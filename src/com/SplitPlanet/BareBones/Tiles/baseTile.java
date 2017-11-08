package com.SplitPlanet.BareBones.Tiles;

import java.util.ArrayList;

import com.SplitPlanet.BareBones.MenuItems.MIType;
import com.SplitPlanet.BareBones.MenuItems.menuItem;
import com.SplitPlanet.BareBones.PathFinder.pathCalc;
import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.TileClass.BlankTile;
import com.SplitPlanet.framework.Image;

import android.graphics.Color;

import com.SplitPlanet.BareBones.GameScreen;
import com.SplitPlanet.BareBones.Enemies.enemy;

public abstract class baseTile implements Tile
{

    protected TileType type;
    
    protected int distance, // max radius for attack
                  disSquared, // distance squared
                  damage,
                  maxCount,
                  enemy,
                  goldUse,
                  powerUse,
                  powerCount,
                  maxPower,
                  powerProd,
                  maxTowersToPower,
                  maxPowerTransfer,
                  rotationAngle,
                  updateAngle;
    public int                    count;
    protected ArrayList<menuItem> menuItems;
    protected boolean             canWalk,
                                  canAttack,
                                  canPower,
                                  canProducePower,
                                  startLevel,
                                  canRotate;
    protected TileState           myState;
    //protected ArrayList<xy>       quadants;
    protected ArrayList<xy>       tilesToPower,
                                  tilesToCheckForAttack;
    protected double              powerAnimation;
	
    protected baseTile()
    {
        menuItems             = new ArrayList<menuItem>();
        tilesToPower          = new ArrayList<xy>();
        tilesToCheckForAttack = new ArrayList<xy>();
        canWalk          = false;
        startLevel       = false;
        myState          = TileState.FINE;
        count            = 0;
        maxCount         = 10; // default max count
        canAttack        = false; // default is not to attack for now
        canPower         = false; // can the tower be powered
        canProducePower  = false;
        canRotate        = false;
        enemy            = -1; // no default enemy
        distance         = 0;
        disSquared       = distance * distance;
        goldUse          = 0;
        powerUse         = 0;
        powerCount       = 0; 
        maxPower         = 1; // can't be zero!
        powerProd        = 0;
        maxTowersToPower = 0;
        maxPowerTransfer = 0;
        powerAnimation   = 0;
        rotationAngle    = 0;
        updateAngle      = 0;
        //quadants   = new ArrayList<xy>();
    }

	@Override
	public TileType getType()
	{
		return type;
	}

	@Override
	public Image getTileImage2()
	{
		return getTileImage();
	}
	
	@Override
	public ArrayList<menuItem> getMenuItems()
	{
	    return menuItems;
	}

	@Override
	public void setType(TileType typeInt)
	{
	}
	
	@Override
	public boolean canWalk()
	{
	    return canWalk;
	}

    @Override
    public Tile onBack(MIType                     backType,
                       xy                         pos,
                       ArrayList<enemy>           enemies,
                       ArrayList<ArrayList<Tile>> map,
                       GameScreen                 inGameScreen)
    {
        Tile ret = this;
        
        if (enemies.size() == 0 && this.type == TileType.MENU_TILE)
        {
            ret = _onBack(backType);
        }
        else
        {
	        for (enemy en : enemies)
	        {
		      	pathCalc pathDummy = new pathCalc();
		      	xy startPoint = en.getStartPos(),
		      	   endPoint   = en.getEndPos();
		        
		      	// validates that a path can still be made
		      	// that the position isn't the start or end point
		      	// OR
		      	// if it is the menu type if (backType == 1)
		      	if (backType == MIType.BACK) // back override
		      	{
		      	    ret.setTileState(TileState.BACK);
		      	}
		      	else if ((pathDummy.calcFuturePath(map, startPoint, endPoint, pos).size() > 0 &&
		      	         !(pos.x == startPoint.x && pos.y == startPoint.y)                    &&
		      	         !(pos.x == endPoint.x   && pos.y == endPoint.y)                      ) ||
		      	         this.type == TileType.MENU_TILE)
		      	{
		            ret = _onBack(backType);
		            if (!inGameScreen.useGold(ret.getCost()))
		            {
		                // if there isn't enough gold then we cancel the change
		                ret     = this;
		                myState = TileState.NO_GOLD;
		            }
		      	}
		      	else
		      	{
		      	    // there is no path
		      	    myState = TileState.NO_PATH;
		      	}
	        }
        }
        return ret;
    }
    
    @Override
    public int getCost() {return goldUse;}

    @Override
    public void setTileState(TileState inState) { myState = inState; }

    @Override
    public TileState getTileState() {return myState;}

    @Override
    public int getAngle() {return rotationAngle;}

    @Override
    public int getUpdateAngle() {return updateAngle;}

    @Override
    public int getCurEnemy() {return enemy;}
    
    @Override
    public boolean canProducePower() {return canProducePower;}

    @Override
    public ArrayList<xy> getTowersToPower() {return tilesToPower;}
    
    @Override
    public void TileUpdate(ArrayList<enemy>           enemies,
                           ArrayList<ArrayList<Tile>> tiles,
             ArrayList<ArrayList<ArrayList<Integer>>> enemiesInTiles,
                           xy                         tower)
    {
        boolean found = false;

        if (canProducePower)
        {
            addPower(powerProd);
            int powerToTransfer = powerCount;
            if ( powerToTransfer > maxPowerTransfer )
            {
                powerToTransfer = maxPowerTransfer;
            }
            int numberOfTowersToPower = 0;

            for (xy xys : tilesToPower)
            {
                if (!tiles.get(xys.y).get(xys.x).isTowerFullyPowered())
                {
                    numberOfTowersToPower ++;
                }
            }

            if (numberOfTowersToPower > 0)
            {
                powerCount -= powerToTransfer;
                for (xy xys : tilesToPower)
                {
                    tiles.get(xys.y)
                         .get(xys.x)
                         .addPower(powerToTransfer/numberOfTowersToPower);
                }
            }
        }
        else
        {
            if (powerCount >= powerUse)
            {
                if (enemy > -1             &&
                    enemy < enemies.size() &&
                    calcDistance(enemies.get(enemy).getPos(),tower) < disSquared)
                {
                    _onAttack(enemies, enemies.get(enemy));
                    found = true; 
                    updateRotation(enemies.get(enemy).getPos(), tower);
                }
                else
                {
                    enemy = -1;
                    //for (int i = 0; i< enemies.size() && !found; i++)
                    //{
                    //    if (calcDistance(enemies.get(i).getPos(),tower) < disSquared)
                    //    {
                    //        _onAttack(enemies,enemies.get(i));
                    //        enemy = i;
                    //        found = true; 
                    //    }
                    //}
                    for (xy xy : tilesToCheckForAttack)
                    {
                        if (tiles.get(xy.y).get(xy.x).canWalk())
                        {
                            for (int i = 0;
                           !found && i < enemiesInTiles.get(xy.y).get(xy.x).size();
                                     i++)
                            {
                                int enInt = enemiesInTiles.get(xy.y).get(xy.x).get(i);
                                enemy en = enemies.get(enInt);
                                if (calcDistance(en.getPos(),tower) < disSquared)
                                {
                                    _onAttack(enemies,en);
                                    enemy = enInt;
                                    found = true;
                                    updateRotation(en.getPos(), tower);
                                }
                                
                            }
                        }
                    }
                }
            }
        }
        
        if (found)
        {
            resetCounter();
        }
    }
    
    private void updateRotation(xy en, xy tower)
    {
        int tempAngle = (int) ((int) Math.toDegrees(Math.atan2(
                               -( en.y  - ((80 * tower.y) + 40)) ,
                                ( en.x  - ((80 * tower.x) + 40))) )) ;
        int tempY = -( en.y  - ((80 * tower.y) + 40));
        int tempX =  ( en.x  - ((80 * tower.x) + 40));
        updateAngle   = tempAngle - rotationAngle;
        rotationAngle = tempAngle;
    }
    
    private int calcDistance(xy enemy,
                             xy tower)
    {
        return ((enemy.x - ((tower.x * 80) + 40)) * (enemy.x - ((tower.x * 80) + 40)))
               +
               ((enemy.y - ((tower.y * 80) + 40)) * (enemy.y - ((tower.y * 80) + 40)));
    }
    
    @Override
    public boolean updateCounter()
    {
        boolean ret = false;
        
        if (count >= maxCount)
        {
            ret = true;
        }
        else
        {
            count++;
        }

        return ret;
    }
    
    @Override
    public void resetCounter() {count = 0;}
    
    @Override
    public boolean canAttack() {return canAttack;}
    
    @Override
    public void addPower(int inPower)
    {
        powerCount += inPower;
        if (powerCount > maxPower)
        {
            powerCount = maxPower;
        }
    }
    
	abstract protected Tile _onBack(MIType backTypetp);
	
	protected void _onAttack(ArrayList<enemy> enemies,
	                         enemy            target)
	{}
	
	@Override
	public boolean canPower() {return canPower;}
	
	@Override
	public int getMaxPower() {return maxPower;}
	
	@Override
	public int getCurPower() {return powerCount;}
	
	@Override
	public boolean canRotate() {return canRotate;}
	
	
	public Tile updateTowersToPower(xy                         pos,
	                                xy                         myPos,
	                                ArrayList<ArrayList<Tile>> tiles)
	{
	    int disToTower = (((myPos.x - pos.x) * 80) * ((myPos.x - pos.x) * 80)) +
	                     (((myPos.y - pos.y) * 80) * ((myPos.y - pos.y) * 80));

	    if (tilesToPower.size() <= maxTowersToPower &&
	        tiles.get(pos.y).get(pos.x).canPower() &&
	        disToTower <= disSquared)
	    {
	        boolean found = false;
	        xy temp = new xy(0,0);
	        for (int i = 0; i<tilesToPower.size() && !found; i++)
	        {
	            temp = tilesToPower.get(i);
	            if (temp.x == pos.x &&
	                temp.y == pos.y)
	            {
	                found = true;
	                tilesToPower.remove(i);
	            }
            }
	        
	        if (!found)
	        {
	            tilesToPower.add(pos);
	        }
	    }
	    
	    return new BlankTile();
	}
	
	@Override
	public double getPowerAnimation()
	{
	    powerAnimation += 0.005;
	    if (powerAnimation > 1)
	    {
	        powerAnimation -= 1.05;
	    }
	    
	    return powerAnimation;
	}
	
	@Override
	public int getPowerColor()
	{
	    int ret = Color.GREEN; // default is green
	    
	    if ( maxPowerTransfer > 200 )
	    {
	        ret = Color.YELLOW;
	    }
	    else if ( maxPowerTransfer > 500)
	    {
	        ret = Color.rgb(255, 165, 0); // orange
	    }
	    else if (maxPowerTransfer > 1000)
	    {
	        ret = Color.RED;
	    }
	    
	    
	    return ret;
	}
	
	@Override
	public boolean isTowerFullyPowered()
	{
	    return powerCount == maxPower;
	}
	
	public void setUpTileToWatchToAttack(ArrayList<ArrayList<Tile>> tiles,
	                                     xy                         pos)
	{
	    for (int y = 0; y < tiles.size(); y++)
	    {
	        for (int x = 0; x < tiles.get(y).size(); x++)
	        {
	            int offsetX = 40, offsetY = 40;
	            if      (x > pos.x) { offsetX -= 40;}
	            else if (x < pos.x) { offsetX += 40;}
	            if      (y > pos.y) { offsetY -= 40;}
	            else if (y < pos.y) { offsetY += 40;}
	            
	            int disToTile = (((pos.x * 80) + 40 - ((x * 80) + offsetX)) *
	                             ((pos.x * 80) + 40 - ((x * 80) + offsetX))) +
	                            (((pos.y * 80) + 40 - ((y * 80) + offsetY)) *
	                             ((pos.y * 80) + 40 - ((y * 80) + offsetY)));
	            if (disToTile < disSquared)
	            {
	                tilesToCheckForAttack.add(new xy(x,y));
	            }
	        }
	    }
	    
	}
	
	
  /*protected ArrayList<xy> setUpQuadants(int quadWidth,
	                                      int quadHeight,
	                                      xy  pos)
	{
	    ArrayList<xy> ret = new ArrayList<xy>();
	    xy realPos = new xy((pos.x * 80) + 40,(pos.y * 80) + 40);
	    xy firstQuad = new xy(realPos.x/quadWidth,
	                          realPos.y/quadHeight);
	    ret.add(firstQuad);
	    
	    
	    // now we need to check if we reach into other quadrants
	    if (firstQuad.x == 0)
	    {
	        
	    }
	    
	    
	    
	    return ret;
	}*/
	
}
