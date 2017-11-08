package com.SplitPlanet.BareBones;

import java.io.InputStream;
import java.util.Map;

import com.SplitPlanet.BareBones.Tiles.TileState;
import com.SplitPlanet.framework.FileIO;
import com.SplitPlanet.framework.Image;
import com.SplitPlanet.framework.Music;
import com.SplitPlanet.framework.Sound;

public class Assets
{
	public Assets () {}

   	/*************** backgrounds *************/
	public static Image basicBackground,
	                    blackBackground;

	/*************** tiles *******************/
	public static Image basicRoadTile,
                        basicBlankTile,
	                    basicWaterTile,
	                    basicWaterTile2,
	                    arrowTower,
	                    basicSlowTile,
	                    basicMoneyIncTile,
	                    basicPoisonTile,
	                    menuTile,
	                    basicPowerTile,
	                    basicPowerLongTile,
	                    basicPowerMultiTile;
	
	/*************** enemies *****************/
	public static Image basic_enemy,
	                    basic_enemy2;

	/*************** enemies *****************/ /* troop1 */
	public static Image troop1_1,
	                    troop1_2,
	                    troop1_3,
	                    troop1_4;

    /*************** levels *****************/
    public static  InputStream level1, level2;
    public static  FileIO      levelStore;

   	/*************** misc ********************/
	public static Image menu,
	                    splash;
	public static Map<TileState,String> errorCode;

	// public static Music theme;

	public static void load(SampleGame sampleGame) {
		// TODO Auto-generated method stub
		// theme = sampleGame.getAudio().createMusic("menutheme.mp3");
		// theme.setLooping(true);
		// theme.setVolume(0.85f);
		// theme.play();
	}

}
