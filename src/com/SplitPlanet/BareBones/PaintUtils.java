package com.SplitPlanet.BareBones;

import java.util.ArrayList;

import com.SplitPlanet.BareBones.Animations.MultiAnimation;
import com.SplitPlanet.BareBones.Enemies.StatusEffectEnum;
import com.SplitPlanet.BareBones.Enemies.enemy;
import com.SplitPlanet.BareBones.Enemies.statusEffect;
import com.SplitPlanet.BareBones.MenuItems.menuItem;
import com.SplitPlanet.BareBones.PathFinder.xy;
import com.SplitPlanet.BareBones.Tiles.Tile;
import com.SplitPlanet.framework.Graphics;

import android.graphics.Color;
import android.graphics.Paint;

public class PaintUtils
{

    
	/******************** PAINT MENU ******************/
   	public static void paintMenu(Graphics g,
   	                             boolean  menuTop)
	{
	    if (menuTop)
	    {
		    g.drawImage(Assets.menuTile, 0, 0);
	    }
	    else
	    {
		    g.drawImage(Assets.menuTile, 720, 400);
	    }
    }
   	
   	/******************** PAINT TILES *****************/
	public static void paintTiles(Graphics                   g,
	                              ArrayList<ArrayList<Tile>> tileArray,
	                              Background                 bg1,
	                              ArrayList<enemy>           enemies)
	{
	    int i=0,j=0;

		for ( ArrayList<Tile> tileVec : tileArray )
		{
			for (Tile t : tileVec)
			{
				int realativex,realativey;
				/*Needs to be relative to where the map is on the screen*/
				realativex= (j * 80)+ bg1.getBackX();
				realativey= (i * 80)+ bg1.getBackY();
				
				if ( realativex > -80 && realativex < 800 &&
					 realativey > -80 && realativey < 480)
				{
				    if (t.canRotate())
				    {
					     g.drawImage(t.getTileImage2(), realativex, realativey);
					     g.drawImage(t.getTileImage(), realativex , realativey, -t.getAngle());
					     double tangle = (double) t.getAngle()  ;
					     int xx2 = realativex + 40 + ((int) (80 * (Math.cos(Math.toRadians(tangle)))));
					     int yy2 = realativey + 40 - ((int) (80 * (Math.sin(Math.toRadians(tangle)))));
					     g.drawLine(realativex + 40, realativey + 40, xx2, yy2, Color.GREEN);
					     
					     int enInt = t.getCurEnemy();
					     if (enInt >= 0 && enInt < enemies.size() && enemies.get(enInt).isTargeted())
					     {
					         int xx3 = enemies.get(enInt).getPos().x;
					         int yy3 = enemies.get(enInt).getPos().y;
					         g.drawLine(realativex + 40, realativey + 40, xx3, yy3, Color.BLUE);
					         double tangle2 = Math.toDegrees(Math.atan2(-(yy3 - (realativey + 40)), xx3 - (realativex + 40)));
					         double temp = tangle2 + 1;
					     }
				    }
				    else
				    {
					     g.drawImage(t.getTileImage(), realativex, realativey);
				    }
				}

				if (t.canPower())
				{
                    //g.drawRect(realativex + 5, realativey, 70, 5, Color.BLACK);
                    g.drawRect(realativex + 8, realativey + 1, 64, 5, Color.RED);
    
                    double powerLength = 64 * ((double) (t.getCurPower()) / ((double) t.getMaxPower()));
                    g.drawRect(realativex + 8, realativey + 1, (int) powerLength, 5, Color.GREEN);
                }
	            j++;
			}
		    i++;j=0;
        }
    }
	
	/******************** PAINT menuItems *****************/
	public static void paintMenuItems(Graphics            g,
	                                  ArrayList<menuItem> myMenuItems,
	                                  Background          bg2,
	                                  Paint               paint,
	                                  int                 realWidth)
	{
	    
	    // first draw the background
	    g.drawImage(Assets.blackBackground, -50, -50); 
	    
	    int j=0;
		for (menuItem mI : myMenuItems)
		{
			/*Needs to be relative to where the map is on the screen*/
			int relativey= (j * 80)+ bg2.getBackY();
			
			if ( relativey > -80 && relativey < 480)
			{
			    // squares are 80 height * 800 width 
				//g.drawImage(mI.miImage, 0, relativey);
			    g.drawRect(20, relativey + 10, realWidth-40, 60,Color.WHITE);
			    
			    if (mI.hasInfo)
			    {
			        g.drawString(mI.miText, 110, relativey + 60, paint);
			        g.drawRect(20, relativey + 10, 70, 60,Color.CYAN);
			        g.drawString("Info", 30, relativey + 60, paint);
			    }
			    else
			    {
			        g.drawString(mI.miText, 30, relativey + 60, paint);
			    }
			}
			j++;
		}
	}

	/******************** PAINT enemies ******************/
	public static void paintHeader(Graphics g,
	                               Paint    paint2,
	                               int      lives,
	                               int      funds)
	{
	    String header = String.format("%2d L %4d G", lives,funds);
	    g.drawString(header, 500, 40, paint2);
	}

	/******************** PAINT enemies ******************/
	public static void paintEnemies(Graphics         g,
	                                ArrayList<enemy> enemies,
	                                Background       bg1)
	{
	    
		for (enemy curEn : enemies)
		{
			/*Needs to be relative to where the map is on the screen*/
			xy pos = curEn.getPos();
			
			// need to take background into account to get the relative position
			// also need an off set so x,y is the actual centre of the image
            int posX = pos.x + bg1.getBackX() - 20,	
                posY = pos.y + bg1.getBackY() - 20;	
				
            if ( posX > -40 && posX < 800 &&
                 posY > -40 && posY < 480 &&
                 curEn.getActive())
            {
                // draw enemy
                g.drawImage(curEn.getImage(), posX, posY);
                
                // draw health bar
                g.drawRect(posX, posY - 20, 40, 5, Color.RED);
                
                // draw how much an enemy has lost
                double curWidth = 40 * ((double) curEn.getCurHealth()) / ((double) curEn.getMaxHealth());
                g.drawRect(posX, posY - 20, (int) curWidth, 5, Color.GREEN);
                
                if (curEn.isTargeted())
                {
                    g.drawUnfilledCircle(posX+20, posY+20, 30, Color.YELLOW);
                    
                    int statusY = posY - 30,
                        statusX = posX + 5;
                    
                    for (statusEffect eff : curEn.getStatuses())
                    {
                        if (eff.statEff != StatusEffectEnum.NONE)
                        {
                            g.drawFilledCircle(statusX,
                                               statusY,
                                               5,
                                               curEn.statusEffectColor(eff.statEff));
                            statusX += 10;
                        }
                    }
                }
            }		
		}
	}

    /******************** PAINT enemies ******************/
	public static void paintPoweredTowers(Graphics                   g,
	                                      ArrayList<ArrayList<Tile>> tileArray,
	                                      Background                 bg1,
	                                      Paint                      paint3,
	                                      boolean                    displayLines)
	{
        if (displayLines)
        {
            int i = 0, j = 0;
            for ( ArrayList<Tile> tileVec : tileArray )
            {
            	for (Tile t : tileVec)
            	{
            	    if (t.canProducePower())
                       {
                        double curPercentage = t.getPowerAnimation();
                        for (xy xy : t.getTowersToPower())
                        {
                            /*Needs to be relative to where the map is on the screen*/
                            double rx1 = (j * 80)    + bg1.getBackX() + 40,
                                   ry1 = (i * 80)    + bg1.getBackY() + 40,
                                   rx2 = (xy.x * 80) + bg1.getBackX() + 40,
                                   ry2 = (xy.y * 80) + bg1.getBackY() + 40;
                    
                            int numberOfSections = 4;
                            double spacing = 1 / ((double) numberOfSections);
                            int myColor = t.getPowerColor();
     
                            for (int k = 0; k<numberOfSections; k++)
                            {
                                int x1 = (int) ( rx1 +  curPercentage         * (rx2 - rx1));
                                int x2 = (int) ( rx1 + (curPercentage + 0.05) * (rx2 - rx1));

                                int y1 = (int) (ry1 +  curPercentage         * (ry2 - ry1));
                                int y2 = (int) (ry1 + (curPercentage + 0.05) * (ry2 - ry1));

                                if ((x1 > -80 && x1 < 800 &&
                                     y1 > -80 && y1 < 480 ) ||
                                    (x2 > -80 && x2 < 800 &&
                                     y2 > -80 && y2 < 480 ))
                                {
                                    if ((x2 > rx2 && Math.signum(rx2 - rx1)>0) ||
                                        (x2 < rx2 && Math.signum(rx2 - rx1)<0)) {x2 = (int) rx2;}
                                    if ((x1 < rx1 && Math.signum(rx2 - rx1)>0) ||
                                        (x1 > rx1 && Math.signum(rx2 - rx1)<0)) {x1 = (int) rx1;} 
                                
                                    if ((y2 > ry2 && Math.signum(ry2 - ry1)>0) ||
                                        (y2 < ry2 && Math.signum(ry2 - ry1)<0)) {y2 = (int) ry2;}
                                    if ((y1 < ry1 && Math.signum(ry2 - ry1)>0) ||
                                        (y1 > ry1 && Math.signum(ry2 - ry1)<0)) {y1 = (int) ry1;}

                                    g.drawLinePaint(x1,y1,
                                                    x2,y2,
                                                    myColor,
                                                    paint3);
                                }
                                curPercentage += spacing;
                                if (curPercentage > 1)
                                {
                                    curPercentage -= 1.05;
                                }
                            }
            	        }
            	    }
                    j++;
            	}
            	i++; j = 0;
            }
        }
	}
	
	public static void paintInfo(Graphics g,
	                             String   inText,
	                             int      width,
	                             int      height,
	                             Paint    paint)
	{
	    g.drawRect(-3, -3, width + 6, height + 6, Color.WHITE);
		g.drawString(inText, 10, 50, paint);
	}
	
	public static void paintAnimations(Graphics                  g,
	                                   ArrayList<MultiAnimation> animations)
	{
	    for (MultiAnimation anim : animations)
	    {
            g.drawImage(anim.getImage(), anim.x, anim.y);
        }
	}
}
