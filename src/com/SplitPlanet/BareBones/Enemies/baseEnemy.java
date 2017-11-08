package com.SplitPlanet.BareBones.Enemies;

import java.util.ArrayList;
import java.util.Random;

import com.SplitPlanet.BareBones.PathFinder.pathCalc;
import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.framework.Image;

import android.graphics.Color;

public abstract class baseEnemy  implements enemy
{
    
    protected int maxHealth,
                  curHealth,
                  step,
                  count,
                  startCount,
                  targetedCount;
    
    protected xy startPos,
                 endPos;
    
    protected double curSpeed,
                     curPayout;
    
    protected double x, 
                     y,
                     speed,
                     payout;
    protected ArrayList<xy> myPath;
    protected ArrayList<statusEffect> myEffects;
    protected boolean isActive, isTargeted;
    
    protected static pathCalc myPathCalc = new pathCalc();
    
    public baseEnemy(ArrayList<xy> inXys,
                     int           inStartCount)
    {
        step       = 0;
        isActive   = false; 
        myPath     = inXys;
        count      = 0;
        startCount = inStartCount;
        isTargeted = false;
        targetedCount = 0;
        payout     = 0;
        
        myEffects = new ArrayList<statusEffect>();
        updateCurrents();

        // set up initial position
        if (inXys.size() > 1)
        {
            // note we have to swap from
            // grid indices to map coordinates
            x = (inXys.get(0).x * 80) + 40 + (new Random()).nextInt(10) - 5;
            y = (inXys.get(0).y * 80) + 40 + (new Random()).nextInt(10) - 5;
        }
    }

    public baseEnemy(int inStartCount)
    {
    
        step       = 0;
        isActive   = false; 
        myPath     = new ArrayList<xy>();
        count      = 0;
        startCount = inStartCount;
        isTargeted = false;
        targetedCount = 0;
        payout     = 0;
    
        myEffects = new ArrayList<statusEffect>();
        updateCurrents();
        // note we have to swap from
        // grid indices to map coordinates
        x = 0;
        y = 0;
    }

    public baseEnemy(int inStartCount,
    		         xy  startP,
    		         xy  endP,
    		         ArrayList<ArrayList<Tile>> map)
    {
        step       = 0;
        isActive   = false; 
        myPath     = new ArrayList<xy>();
        myPath     = myPathCalc.calcPath(map, startP, endP);
        count      = 0;
        startCount = inStartCount;
        isTargeted = false;
        targetedCount = 0;
        payout     = 0;
        
        startPos = startP;
        endPos   = endP;
    
        myEffects = new ArrayList<statusEffect>();
        updateCurrents();
        // note we have to swap from
        // grid indices to map coordinates
        x = (startPos.x * 80) + 40;
        y = (startPos.y * 80) + 40;
    }
    
    @Override
    public double getSpeed()
    {
        return curSpeed;
    }

    @Override
    public boolean updateHealth(int damage)
    {
        return false;
    }
    
    @Override
    public void setPos(int xIn, int yIn)
    {
        x = xIn;
        y = yIn;
    }
    
    @Override
    public xy getPos()
    {
        return new xy((int) x,
                      (int) y);
    }
    
    @Override
    public xy getNextPos()
    {
        xy ret = new xy(-1,-1);
        if (step +1 < myPath.size())
        {
            ret = myPath.get(step+1);
        }
        return ret;
    }
    
    @Override
    public void updatePos()
    {
        // we need to update the current stats everyturn
        // with the position
        updateCurrents();
        
        if (step + 1 < myPath.size())
        {
            // get the right square
            xy target = myPath.get(step + 1);
            double tempX = target.x,
                   tempY = target.y;
            
            // we swap the grid indices to map coordinates
            //int temp = tempY;
            //tempY = tempX;
            //tempX = temp;
            
            // convert to real map coordinates
            tempX = (tempX * 80) + 40;
            tempY = (tempY * 80) + 40;
            
            // we don't care if there is a small correction to make
            if (Math.abs(tempX - x) < 10 )
            {
                tempX = x;
            }
            if (Math.abs(tempY - y) < 10 )
            {
                tempY = y;
            }           

            // update the position
            x += Math.signum(tempX - x) * curSpeed;
            y += Math.signum(tempY - y) * curSpeed;
        }
    }
    
    @Override
    public boolean updateStep()
    {
        boolean ret = true;

        // if we are trying to step beyond the path
        // then return false
        if (step + 1 >= myPath.size())
        {
            ret = false;
        }
        else
        {
            xy target = myPath.get(step+1);
            int tempX = target.x,
                tempY = target.y;

            // we swap the grid indices to map coordinates
            //int temp = tempY;
            //tempY    = tempX;
            //tempX    = temp;
            
            // convert to real map coordinates
            tempX = (tempX * 80) + 40;
            tempY = (tempY * 80) + 40;
            
            int xx = (int) x,
                yy = (int) y;

            int distance =  ((xx - tempX) * (xx - tempX)) +
                            ((yy - tempY) * (yy - tempY));
            
            // if the distance is less than a certain amount
            // then we have hit out step! so update the step
            if (distance < 200)
            {
                step++;
            }

            // if the new step is the last step
            // then we have got to the end and 
            // return false
            if (step + 1 >= myPath.size())
            {
                ret = false;
            }
        }
        
        return ret;
    }
    
    @Override
    public boolean getActive()
    {
        return isActive;
    }
    
    @Override
    public void setActive(boolean inActive)
    {
        isActive = inActive;
    }
    
    @Override
    public void setPath(ArrayList<xy> inPath)
    {
        myPath = inPath;
    }
    
    // set curHealth
    @Override
    public void setCurHealth(int inHealth) {curHealth = inHealth;};
    
    // get curHealth
    @Override
    public int getCurHealth() {return curHealth;};
    
    // get maxHealth
    @Override
    public int getMaxHealth() {return maxHealth;};
    
    // do some damage
    @Override
    public void doDamage(int damage)
    {
        curHealth -= damage;
    }
    
    // update the counter
    @Override
    public void updateCount()
    {
        count++;
        if (count > startCount)
        {
            isActive = true;
        }
    }
    
    @Override
    public boolean isTargeted() {return isTargeted;}
    
    @Override
    public ArrayList<statusEffect> getStatuses() {return myEffects;}
    
    @Override
    public void setTargeted(statusEffect inStatEff)
    {
        isTargeted = true;
        boolean added = false;
        
        for (int i = 0;
                 i < myEffects.size() && !added;
                 i++)
        {
            if (myEffects.get(i).statEff == inStatEff.statEff)
            {
                added = true;
                myEffects.set(i,inStatEff);
            }
        }
        
        if (!added)
        {
            myEffects.add(inStatEff);
        }
    }
    
    @Override
    public void setTargetedSimple(int length)
    {
        setTargeted(new statusEffect(length, StatusEffectEnum.NONE, 0));
    }
    
    @Override
    public void updateTargeted()
    {
        for (int i = myEffects.size() - 1;
                 i >= 0;
                 i--)
        {
            myEffects.get(i).count++;
            
            if (myEffects.get(i).count >= myEffects.get(i).time )
            {
                myEffects.remove(i);
            }
        }
        
        if (myEffects.size() <= 0)
        {
            isTargeted = false;
        }
    }
    
    // get payout
    @Override
    public int getPayout() {return (int) curPayout;}
    
    // what to do on death
    @Override
    public void onDeath(ArrayList<enemy>            enemies,
                        ArrayList<ArrayList<Tile>>  tiles)
    {
        _onDeath(enemies,tiles);
    }
    
    abstract protected void _onDeath(ArrayList<enemy>            enemies,
                                     ArrayList<ArrayList<Tile>>  tiles);
    
    private void updateCurrents()
    {
        curSpeed  = speed;
        curPayout = payout;
        
        for (statusEffect statusEffect : myEffects)
        {
            switch (statusEffect.statEff)
            {
                case SPEED:
                    curSpeed = speed * statusEffect.quantEffect;
                    break;
                case PAYOUT:
                    curPayout = payout * statusEffect.quantEffect;
                    break;
                case POISON:
                    if (statusEffect.count % statusEffect.posTime == 0)
                    {
                        doDamage(statusEffect.posDamage);
                    }
                    break;
                case NONE:
                default:
                    break;
                
            }
        }
    }
    
    public int statusEffectColor(StatusEffectEnum inEffect)
    {
        int ret = Color.BLACK;
        
        switch (inEffect)
        {
            case SPEED:
                ret = Color.BLUE; 
                break;
            case PAYOUT:
                ret = Color.YELLOW;
                break;
            case POISON:
                ret = Color.GREEN;
                break;
            case NONE:
                ret = -1;
                break;
        }

        return ret;
    }
    
    public xy getStartPos()
    {
    	return startPos;
    }
    
    public xy getEndPos()
    {
    	return endPos;
    }
    
}
