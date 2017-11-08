package com.SplitPlanet.BareBones.MenuItems;

import java.io.IOException;
import java.io.InputStream;

import com.SplitPlanet.BareBones.Assets;
import com.SplitPlanet.framework.FileIO;
import com.SplitPlanet.framework.Game;

public class menuItem
{
    
    public MIType     miEnum;
    public String     miText;
    public String     miInfo;
    public String     miLevelKey;
    public boolean    hasInfo;
    
    public menuItem (MIType inEnum, String inString)
    {
        miText  = inString;
        miEnum  = inEnum;
        miInfo  = "";
        hasInfo = false;
    }
    
    public menuItem (MIType inEnum, String inString, String keyString, String dummy)
    {
        miText  = inString;
        miEnum  = inEnum;
        miInfo  = "";
        hasInfo = false;
        miLevelKey = keyString;
    }
    
    public menuItem (MIType inEnum, String inString, String infoString)
    {
        miText  = inString;
        miEnum  = inEnum;
        miInfo  = infoString;
        hasInfo = true;
    }
    
    public InputStream menuMenuOnClick(Game game)
    {
        InputStream local = null;
        try {
	        local = game.getFileIO().readAsset(miLevelKey);
		} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
		}
    	return local;
    }
}
