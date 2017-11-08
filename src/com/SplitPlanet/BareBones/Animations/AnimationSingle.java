package com.SplitPlanet.BareBones.Animations;

import com.SplitPlanet.framework.Image;

public class AnimationSingle
{
    public int duration;
    public Image myImage;
    
    public AnimationSingle(int durIn, Image imageIn)
    {
        duration = durIn;
        myImage  = imageIn;
    }
    
    public boolean update()
    {
        boolean ret = true;
        
        duration--;
        if (duration < 0)
        {
            ret = false;
        }
        
        return ret;
    }
}
