package com.SplitPlanet.BareBones.Animations;

import java.util.ArrayList;

import com.SplitPlanet.framework.Image;

public class MultiAnimation
{

    public ArrayList<AnimationSingle> images;
    public int x, y;

    public MultiAnimation(int xIn,
                          int yIn,
                          ArrayList<AnimationSingle> imagesIn)
    {
        x = xIn;
        y = yIn;
        images = imagesIn;
    }
    
    public boolean update()
    {
        boolean ret = true;
        if (images.size() > 0)
        {
            if (!images.get(0).update())
            {
                images.remove(0);
                if (images.size() == 0)
                {
                    ret = false;
                }
            }
        }
        return ret;
    }
    
    public Image getImage()
    {
        return images.get(0).myImage;
    }
    
}
