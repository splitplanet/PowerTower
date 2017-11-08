package com.SplitPlanet.BareBones.Map;

import java.util.ArrayList;

import com.SplitPlanet.BareBones.Enemies.enemy;
import com.SplitPlanet.BareBones.Enemies.types.basicEnemy;
import com.SplitPlanet.BareBones.PathFinder.pathCalc;
import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.Tile;

import android.R.integer;

public class MapPlayer {
	
	private int whereamI = 0,kepttime,noitems;
	private String eventname;
	private boolean getchanged=true,end=false;
	private ArrayList<enemy> myenemy = new ArrayList<enemy>();
	private static pathCalc mypath = new pathCalc();

	public MapPlayer () {
		
	}
	
	public void PlayMap (ArrayList<ArrayList<String>> events,int time,ArrayList<ArrayList<Tile>>    tilearray,xy startpoint ,xy endpoint) {
	
		/*Play Map*/
		
		/*Get details of where I am*/
		if (getchanged==true) {
		if (whereamI < events.size()){
		eventname=events.get(whereamI).get(0);
		noitems=Integer.parseInt(events.get(whereamI).get(1));
		getchanged=false;
		}else {
			end=true;
			
		}
		}
		
		if  (eventname.equals("TIMER") && end == false) {
			noitems = noitems - 1;
			if (noitems == 0 ) {
				whereamI++;
				getchanged=true;			
			}
		}
		
		if  (eventname.equals("BASICENEMY") && end == false) {
			
			
			
			for (int i=0;i<noitems;i++) {
				myenemy.add(new basicEnemy(mypath.calcPath(tilearray,startpoint,endpoint),i*20));				
			}
			whereamI++;
			getchanged=true;			
		}
		
		
/*		******************* START LEVEL *******************
		TIMER 1000
		BASICENEMY 20
		TIMER 1000
		BASICENEMY 50
		******************* END LEVEL *******************

	*/	
		
		
		
		
	}

	public ArrayList<enemy> getMyenemy() {
		return myenemy;
	}

	public void setMyenemy(ArrayList<enemy> myenemy) {
		this.myenemy = myenemy;
	}
	
	
	

}
