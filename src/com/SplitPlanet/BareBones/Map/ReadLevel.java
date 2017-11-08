package com.SplitPlanet.BareBones.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Enemies.enemy;
import com.SplitPlanet.BareBones.Enemies.getEnemy;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.BareBones.Tiles.TileAdd;
import com.SplitPlanet.BareBones.Tiles.TileClass.BlankTile;
import com.SplitPlanet.BareBones.Tiles.TileClass.RoadTile;
import com.SplitPlanet.BareBones.Tiles.TileClass.WaterTile;

public class ReadLevel {
	
	/*Map**/

	
	private ArrayList<ArrayList<Tile>> tilearray = new ArrayList<ArrayList<Tile>>();
	private ArrayList<enemy> level = new ArrayList<enemy>();
	private String type =  "BLANK";
	
	public  ReadLevel () {
	}
	
	
    public void ReadLevelMethod(InputStream inputfi)
    {
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputfi));
		StringBuilder responseData = new StringBuilder();
		
		////Do read
		int lineno=0,lineno2=0;
		int point,point2;
		char text;
		try
		{
			while((line = reader.readLine()) != null) {
	
				if (line.indexOf("END_MAP") > 0) type=" ";

				if (type.equals("MAP"))
				{
					tilearray.add(new ArrayList<Tile>());
				    for(point=0;point<line.length();point++)
				    {
                           point2=point+1;
                           text= line.charAt(point);
                           tilearray.get(lineno).add(TileAdd.tileSelector(text)); 	   	
                    }
				    lineno++;
				} 

				if (line.indexOf("START_MAP") > 0) type="MAP";
				
                if (line.indexOf("END_LEVEL") > 0) type=" ";
				
				if (type.equals("LEVEL") && ! tilearray.isEmpty())
				{
				
					String[] text2 = line.replaceAll("\\s+", " ").split(" ");
					for (int i = 0; i < Integer.parseInt(text2[1]); i++)
					{
						// text2 is
						// [0] enemy type
						// [1] number of enemies
						// [2] start pos
						// [3] end pos
						// [4] timer

						xy start = new xy(Integer.parseInt(text2[2].split(",")[0]),
						                  Integer.parseInt(text2[2].split(",")[1]));
						
						xy end = new xy(Integer.parseInt(text2[3].split(",")[0]),
						                Integer.parseInt(text2[3].split(",")[1]));

						// note: 4000 is roughly 1 minute
						int timeToStart = Integer.parseInt(text2[4]);
						level.add(getEnemy.getOneEnemy(text2[0],
								                       start,
								                       end,
								                       timeToStart,
								                       tilearray));
					}
					
					lineno2++;
				}		
				if (line.indexOf("START_LEVEL") > 0) type="LEVEL";

			

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int j =0;

		
	}


	public ArrayList<enemy> getLevel() {
		return level;
	}


	public void setLevel(ArrayList<enemy> level) {
		this.level = level;
	}


	public ArrayList<ArrayList<Tile>> getTilearray() {
		return tilearray;
	}


	public void setTilearray(ArrayList<ArrayList<Tile>> tilearray) {
		this.tilearray = tilearray;
	}


}



