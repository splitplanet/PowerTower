package com.SplitPlanet.BareBones.Enemies.types;

import java.util.ArrayList;
import java.util.Random;

import com.SplitPlanet.BareBones.Assets;
import com.SplitPlanet.BareBones.Enemies.baseEnemy;
import com.SplitPlanet.BareBones.Enemies.enemy;
import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.framework.Image;

public class Troop1 extends baseEnemy
{
    int myMaxHealth = 1000;
    private int imageCount = -1; 
    
    public Troop1(ArrayList<xy> inXys,
                  int           inStartCount)
    {
        super(inXys,inStartCount);
        speed      = 2.5;
        maxHealth  = myMaxHealth;
        curHealth  = maxHealth;
        payout     = 100;
    }
    
    public Troop1(int inStartCount)
    {
        super(inStartCount);
        speed      = 2.5;
        maxHealth  = myMaxHealth;
        curHealth  = maxHealth;
        payout     = 100;
    }
    
    @Override
    public Image getImage()
    {
        Image ret = Assets.troop1_1;
        imageCount++;
        int diff = 20;

        if (imageCount > diff && imageCount <= 2 * diff)
        {
            ret = Assets.troop1_2;
        }
        else if (imageCount > 2 * diff && imageCount <= 3 * diff)
        {
            ret = Assets.troop1_3;
        }
        else if (imageCount > 3 * diff && imageCount <= 4 * diff)
        {
            ret = Assets.troop1_4;
        }
        else if (imageCount > 4 * diff)
        {
            imageCount = -1;
        }

        return ret;
    }
    
    protected void _onDeath(ArrayList<enemy>           enemies,
                            ArrayList<ArrayList<Tile>> tiles)
    {
    }
}
