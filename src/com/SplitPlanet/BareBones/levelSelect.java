package com.SplitPlanet.BareBones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;








import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.SplitPlanet.framework.Game;
import com.SplitPlanet.framework.Graphics;
import com.SplitPlanet.framework.Screen;
import com.SplitPlanet.framework.Input.TouchEvent;
import com.SplitPlanet.framework.implementation.AndroidGame;
import com.SplitPlanet.BareBones.GameScreen;
import com.SplitPlanet.BareBones.PaintUtils;
import com.SplitPlanet.BareBones.MenuItems.MIAssets;
import com.SplitPlanet.BareBones.MenuItems.MIType;
import com.SplitPlanet.BareBones.MenuItems.menuItem;

public class levelSelect extends Screen
{
	Paint paint;
	private static int realWidth = 800;
	ArrayList<menuItem> myMenuItems  = new ArrayList<menuItem>();
	private static Background bg1;
	private static int longhold = 0,
			           starty   = 0;
	private static int height         = 12,
                       realHeight     = 480;

	
	
	public levelSelect(Game game)
	{
		super(game);
		
		myMenuItems.add(MIAssets.getMenuItem("Level 1", "level1.txt"));
		myMenuItems.add(MIAssets.getMenuItem("Level 2", "level2.txt"));
		myMenuItems.add(MIAssets.getMenuItem("Level 3", "level2.txt"));
		myMenuItems.add(MIAssets.getMenuItem("Level 4", "level2.txt"));
		myMenuItems.add(MIAssets.getMenuItem("Level 5", "level2.txt"));
		myMenuItems.add(MIAssets.getMenuItem("Level 6", "level2.txt"));
		myMenuItems.add(MIAssets.getMenuItem("Level 7", "level2.txt"));
		myMenuItems.add(MIAssets.getMenuItem("Level 8", "level2.txt"));
		myMenuItems.add(MIAssets.getMenuItem("Level 9", "level2.txt"));
		myMenuItems.add(MIAssets.getMenuItem("Level 10", "level2.txt"));
		
		bg1 = new Background(0, 0);
        paint  = new Paint();

    	/* setting up the second paint object */
        paint.setTextSize(50);
        paint.setTypeface(Typeface.SERIF);
        paint.setColor(Color.BLACK);
	}
	static Context context = AndroidGame.getContext();
	
	@Override
	public void update(float deltaTime) 
	{
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		

		int len = touchEvents.size();

		for (int i = 0; i < len; i++)
		{
			TouchEvent event = touchEvents.get(i);

			int longHoldLimit = 3;

			    if (event.type == TouchEvent.TOUCH_UP &&
					longhold   < longHoldLimit)
			    {
			    	// screen is 800 x 480
					if (inBounds(event, 0, 0, 1000, 1000))
			        {
						menuItem curLevel = calcTileMenuPos(event.y);
	                    try {
							game.setScreen(new GameScreen(game,curLevel.menuMenuOnClick(game)));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
	            }
			    else if (event.type == TouchEvent.TOUCH_DRAGGED)
				{
				    if (longhold > 0)
				    {
				        /*update background*/
				       	bg1.setBackY(bg1.getBackY() - (starty - event.y),
				       	           -(height*80) + 3 + realHeight,-3);
				    }


				    /*Remember starting position*/
				    starty = event.y;
					
					// detect long hold
				    if (longhold <= longHoldLimit)
				    {
					    longhold ++ ;
				    }
				}
				else
				{
				    longhold = 0; 
				}
	        }
        }
		
		private boolean inBounds(TouchEvent event,
				                 int        x,
				                 int        y,
				                 int        width,
				                 int        height)
		{
			if (event.x > x && event.x < x + width - 1 && event.y > y
					&& event.y < y + height - 1)
				return true;
			else
				return false;
		}
		private menuItem calcTileMenuPos(int y)
		{
			int curY = (y)/80;
			menuItem ret = MIAssets.getMenuItem(MIType.DEFAUT);
			
			if (curY > -1 &&
			    curY < myMenuItems.size())
			{
			    ret = myMenuItems.get(curY);
			}
			
			return ret;
		}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

        PaintUtils.paintMenuItems(g,
                myMenuItems,
                bg1,
                paint,
                realWidth);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub
		
	}

}
