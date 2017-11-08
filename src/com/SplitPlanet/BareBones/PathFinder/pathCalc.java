package com.SplitPlanet.BareBones.PathFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.SplitPlanet.BareBones.Tiles.Tile;


class position
{
    public int     steps;
    public boolean canWalk,
                   inProgress;
    public position(boolean inBoo){canWalk = inBoo;
                                   steps = 0;}
}



public class pathCalc
{
    static ArrayList<ArrayList<position>> posMap;
    
    
     // used by external classes to generate the path
    public ArrayList<xy> calcPath(ArrayList<ArrayList<Tile>> inMap,
                                  xy                         startPoint1,
                                  xy                         endPoint1)
    {
        return _calcPath(inMap,startPoint1,endPoint1,new xy(-1,-1));
    }

    
    
    
    // used by external classes to generate the path
    public ArrayList<xy> calcFuturePath(ArrayList<ArrayList<Tile>> inMap,
                                        xy                         startPoint1,
                                        xy                         endPoint1,
                                        xy                         extra)
    {
        return _calcPath(inMap,startPoint1,endPoint1,extra);
    }
    
    // used by external classes to generate the path
    public ArrayList<xy> _calcPath(ArrayList<ArrayList<Tile>> inMap,
                                   xy                         startPoint1,
                                   xy                         endPoint1,
                                   xy                         extra)
    {
        // we swap the start and end points so we don't have to flip at the end
        xy startPoint = endPoint1,
           endPoint   = startPoint1;
        
        // first we generate a bool map to see where
        // we can go in the map
        posMap = new ArrayList<ArrayList<position>>();

        // we populate it from our input tile map
        int i = 0, j = 0;
        for (ArrayList<Tile> arrayList : inMap)
        {
            posMap.add(new ArrayList<position>());
            for (Tile tile : arrayList)
            {
                j++;
                posMap.get(i).add(new position (tile.canWalk()));
            }
            j=0;
            i++;
        }
        
        // if we are validating extra then we need to set that to 
        // false as well
        if (extra.y > -1 && extra.y < posMap.size() &&
            extra.x > -1 && extra.x < posMap.get(extra.y).size())
        {
            posMap.get(extra.y).get(extra.x).canWalk = false;
        }
        
        // next we generate the number of steps need to get to
        // each free point
        // this is done in a loop fashion
        int curStep = 0;
        ArrayList<xy> nextXy = new ArrayList<xy>(),
                      temp   = new ArrayList<xy>();
        nextXy.add(startPoint);

        while (nextXy.size() > 0)
        {
            for (xy curXy : nextXy)
            {
                temp.addAll(nextStep(curStep,curXy));
            }
            curStep++;
            nextXy = temp;
            temp = new ArrayList<xy>();
        }
       
        // generate and return the found path
        // the path will be of length 1 if a 
        // path wasn't found
        ArrayList<xy> ret = new ArrayList<xy>();
        if (posMap.get(startPoint1.y).get(startPoint1.x).steps > 0)
        {
            // if a path has been found then set it up!
            ret =getPath(endPoint,startPoint);
        }
        return ret;
    }
    
    
    // returns a list of all unvisited positions
    // in the map that are next to the stepIn position
    private ArrayList<xy> nextStep(int stepIn,
                                   xy  xyIn)
    {
        int xIn = xyIn.x,
            yIn = xyIn.y;
        ArrayList<xy> ret = new ArrayList<xy>();
        int curStep = stepIn + 1;
        
        if ( yIn > -1 && yIn < posMap.size() &&
             xIn > -1 && xIn < posMap.get(yIn).size())
        {
            if (posMap.get(yIn).get(xIn).canWalk)
            {
                posMap.get(yIn).get(xIn).steps = curStep;
                
                // now look in all 4 directions
                // DOWN
                if (yIn > 0 &&
                    posMap.get(yIn - 1).get(xIn).steps == 0)
                {
                    ret.add(new xy(xIn,yIn-1));
                    posMap.get(yIn - 1).get(xIn).steps = -1;
                }

                // UP
                if (yIn + 1 < posMap.size() &&
                    posMap.get(yIn + 1).get(xIn).steps == 0)
                {
                    ret.add(new xy(xIn,yIn+1));
                    posMap.get(yIn + 1).get(xIn).steps = -1;
                }
                
                // LEFT
                if (xIn > 0 &&
                    posMap.get(yIn).get(xIn - 1).steps == 0)
                {
                    ret.add(new xy(xIn-1,yIn));
                    posMap.get(yIn).get(xIn - 1).steps = -1;
                }

                // RIGHT
                if (xIn + 1 < posMap.get(yIn).size() &&
                    posMap.get(yIn).get(xIn + 1).steps == 0)
                {
                    ret.add(new xy(xIn+1,yIn));
                    posMap.get(yIn).get(xIn + 1).steps = -1;
                }
                
            }
            else
            {
                posMap.get(yIn).get(xIn).steps = -1;
            }
        }
        
        return ret;
    }
    
    
    // finds the path backwards assuming the
    // steps to the end position have been found
    // will return a length 1 path if the no
    // valid path was found
    private ArrayList<xy> getPath(xy endPoint,
                                  xy startPoint)
    {
        ArrayList<xy> ret = new ArrayList<xy>();
        // we start at the end point 
        // and work our way back
        ret.add(endPoint);
        
        
        // start at the end point
        xy tempXy = endPoint;
        int curStep = posMap.get(endPoint.y)
                            .get(endPoint.x)
                            .steps;

        // Random object for paths selection
        Random ran = new Random();
        if (curStep > 1)
        {
            while (!(tempXy.x == startPoint.x &&
                     tempXy.y == startPoint.y))
            {
                // now look in all 4 directions
                ArrayList<Integer> possibleMoves = new ArrayList<Integer>();

                // DOWN
                if (tempXy.y > 0 &&
                    posMap.get(tempXy.y - 1)
                          .get(tempXy.x).steps == curStep - 1)
                {
                    possibleMoves.add(1);
                }
                
                // UP
                if (tempXy.y + 1 < posMap.size() &&
                    posMap.get(tempXy.y + 1)
                          .get(tempXy.x).steps == curStep - 1)
                {
                    possibleMoves.add(2);
                }
                
                // LEFT (yes it's weird)
                if (tempXy.x > 0 &&
                    posMap.get(tempXy.y)
                          .get(tempXy.x - 1).steps == curStep - 1)
                {
                    possibleMoves.add(3);
                }

                // RIGHT
                if (tempXy.x + 1 < posMap.get(tempXy.y).size() &&
                    posMap.get(tempXy.y)
                          .get(tempXy.x + 1).steps == curStep - 1)
                {
                    possibleMoves.add(4);
                }

                // randomly get one of our possible moves
                int pos = possibleMoves.get(ran.nextInt(possibleMoves.size()));
                
                // and make the move
                if (pos == 1)      {tempXy = new xy(tempXy.x,tempXy.y-1);} // DOWN
                else if (pos == 2) {tempXy = new xy(tempXy.x,tempXy.y+1);} // UP
                else if (pos == 3) {tempXy = new xy(tempXy.x-1,tempXy.y);} // LEFT
                else if (pos == 4) {tempXy = new xy(tempXy.x+1,tempXy.y);} // RIGHT
                
                ret.add(tempXy);
                curStep--;
            }
        }
        return ret;   
    }

}
