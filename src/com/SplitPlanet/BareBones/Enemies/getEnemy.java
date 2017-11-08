package com.SplitPlanet.BareBones.Enemies;

import java.util.ArrayList;

import com.SplitPlanet.BareBones.Enemies.types.basicEnemy;
import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.Tile;

public class getEnemy
{

	public static enemy getOneEnemy(String    enType,
			                        xy        startPos,
                                    xy        endPos,
			                        int       time,
			                        ArrayList<ArrayList<Tile>> map)
	{
		enemy en = null;
		if (enType.equals("BASIC_ENEMY"))
		{
			en = new basicEnemy(time, startPos, endPos, map);
		}
		return en;
	}
	
}
