package com.SplitPlanet.BareBones.Enemies.types;

import java.util.ArrayList;

import com.SplitPlanet.BareBones.Assets;
import com.SplitPlanet.BareBones.Enemies.baseEnemy;
import com.SplitPlanet.BareBones.Enemies.enemy;
import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.framework.Image;

public class basicEnemy2 extends baseEnemy
{
    int myMaxHealth = 500;

    public basicEnemy2(ArrayList<xy> inXys,
                       int           inStartCount)
    {
        super(inXys,inStartCount);
        speed      = 5;
        maxHealth  = myMaxHealth;
        curHealth  = maxHealth;
        payout     = 50;
    }
    
    public basicEnemy2(int inStartCount)
    {
        super(inStartCount);
        speed      = 5;
        maxHealth  = myMaxHealth;
        curHealth  = maxHealth;
        payout     = 50;
    }
    
    @Override
    public Image getImage()
    {
        return Assets.basic_enemy2;
    }

    @Override
    protected void _onDeath(ArrayList<enemy>           enemies,
                            ArrayList<ArrayList<Tile>> tiles)
    {
    }
}
