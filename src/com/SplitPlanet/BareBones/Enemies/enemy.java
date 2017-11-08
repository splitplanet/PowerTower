package com.SplitPlanet.BareBones.Enemies;

import java.util.ArrayList;

import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.framework.Image;

public interface enemy
{
    
    // return the speed of the enemy
    public double getSpeed();
    
    // update the health of the enemy
    // returns false if the enemy has died
    public boolean updateHealth(int damage);
    
    // get the enemies image
    public Image getImage();
    
    // set the position
    public void setPos(int xIn, int yIn);
    
    // get the position
    public xy getPos();
    
    // get the start position
    public xy getStartPos();
    
    // get the end position
    public xy getEndPos();
    
    // get the next position
    public xy getNextPos();
    
    // update the position
    public void updatePos();
    
    // update the step
    public boolean updateStep();
    
    // get if the enemy is active
    public boolean getActive();
    
    // set if the enemy is active
    public void setActive(boolean inActive);
    
    // set if the enemy is active
    public void setPath(ArrayList<xy> inPath);
    
    // set curHealth
    public void setCurHealth(int inHealth);
    
    // set curHealth
    public void doDamage(int damage);
    
    // get curHealth
    public int getCurHealth();
    
    // get maxHealth
    public int getMaxHealth();
    
    // update Counter
    public void updateCount();
    
    // is the enemy targeted?
    public boolean isTargeted();
    
    // is the enemy targeted?
    public void setTargeted(statusEffect inStatEff);
    
    // is the enemy targeted?
    public void setTargetedSimple(int length);
    
    // update the targeted counter
    public void updateTargeted();
    
    // payout amount
    public int getPayout();
    
    // payout amount
    public int statusEffectColor(StatusEffectEnum inEff);
    
    // get statuses
    public ArrayList<statusEffect> getStatuses();
    
    // on death
    public void onDeath(ArrayList<enemy>            enemies,
                        ArrayList<ArrayList<Tile>>  tiles);

}
