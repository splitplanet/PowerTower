package com.SplitPlanet.BareBones.Enemies.types;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import com.SplitPlanet.BareBones.Assets;
import com.SplitPlanet.BareBones.Enemies.baseEnemy;
import com.SplitPlanet.BareBones.Enemies.enemy;
import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.framework.Image;

public class basicEnemy extends baseEnemy
{
    int myMaxHealth = 1000;
    
    public basicEnemy(ArrayList<xy> inXys,
                      int           inStartCount)
    {
        super(inXys,inStartCount);
        speed      = 2;
        maxHealth  = myMaxHealth;
        curHealth  = maxHealth;
        payout     = 100;
    }
    
    public basicEnemy(int inStartCount)
    {
        super(inStartCount);
        speed      = 2;
        maxHealth  = myMaxHealth;
        curHealth  = maxHealth;
        payout     = 100;
    }

    
    public basicEnemy(int                inStartCount,
    		          xy                 startPos,
    		          xy                 endPos,
    		          ArrayList<ArrayList<Tile>> map)
    {
        super(inStartCount, startPos, endPos, map);
        speed      = 2;
        maxHealth  = myMaxHealth;
        curHealth  = maxHealth;
        payout     = 100;
    }
    
    @Override
    public Image getImage()
    {
        return Assets.basic_enemy;
    }
    
    protected void _onDeath(ArrayList<enemy>           enemies,
                            ArrayList<ArrayList<Tile>> tiles)
    {
        ArrayList<xy> xys = myPathCalc.
                                calcPath(tiles,
                                         new xy((int) (x / 80) ,
                                                (int) (y / 80)),
                                         myPath.get(myPath.size() - 1));
        enemies.add(new basicEnemy2(xys,0));
        enemies.add(new basicEnemy2(xys,5));
    }

}
