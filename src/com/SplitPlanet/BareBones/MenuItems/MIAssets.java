package com.SplitPlanet.BareBones.MenuItems;

public class MIAssets
{

    public static menuItem getMenuItem(MIType miEnum)
    {
        // default return is blank
        menuItem ret = new menuItem(MIType.DEFAUT,"");
        
        switch (miEnum)
        {
            /********** BACK BUTTON **********/
            case BACK:
                ret = new menuItem(MIType.BACK,
                                   "Back");
                break;
            /********** BACK BUTTON **********/
            case TOWER_INFO:
                ret = new menuItem(MIType.TOWER_INFO,
                                   "tower infomation");
                break;

            /********** blank tower **********/
            case BLANK_TOWER:
                ret = new menuItem(MIType.BLANK_TOWER,
                                   "Blank tower");
                break;

            /********** water tower **********/
            case WATER_TOWER:
	            ret = new menuItem(MIType.WATER_TOWER,
	                               "Attack tower",
	                               "Sample text\n"
	                               + "More sample text");
                break;

            /********** water tower **********/
            case WATER_TOWER2:
	            ret = new menuItem(MIType.WATER_TOWER2,
	                               "Attack tower 2",
	                               "Sample text\n"
	                               + "More sample text\n"
	                               + "yet more Sample Text");
                break;

            /********** slow tower **********/
            case SLOW_TOWER:
                ret = new menuItem(MIType.SLOW_TOWER,
                                   "Slow tower");
                break;

            /********** slow tower **********/
            case ARROW_TOWER:
                ret = new menuItem(MIType.ARROW_TOWER,
                                   "Arrow Tower");
                break;

            /********** poison tower **********/
            case POISON_TOWER:
                ret = new menuItem(MIType.POISON_TOWER,
                                   "Poison tower");
                break;

            /********** Power tower **********/
            case POWER_TOWER:
                ret = new menuItem(MIType.POWER_TOWER,
                                   "Power tower");
                break;

            /********** Power multi tower **********/
            case POWERMULTI_TOWER:
                ret = new menuItem(MIType.POWERMULTI_TOWER,
                                   "Power tower - multiple towers");
                break;

            /********** Power long tower **********/
            case POWERLONG_TOWER:
                ret = new menuItem(MIType.POWERLONG_TOWER,
                                   "Power tower - long range");
                break;

            /********** Payment tower **********/
            case PAYMENT_TOWER:
                ret = new menuItem(MIType.PAYMENT_TOWER,
                                   "payment increase tower");
                break;

            /********** Payment tower **********/
            case START:
                ret = new menuItem(MIType.START,
                                   "Start level");
                break;

            /********** Payment tower **********/
            case DISPLAY_POWERLINES:
                ret = new menuItem(MIType.DISPLAY_POWERLINES,
                                   "Turn on/off power lines display");
                break;

            /********** Payment tower **********/
            case SELECT_TOWER:
                ret = new menuItem(MIType.SELECT_TOWER,
                                   "Select towers");
                break;
            
            /********** level **********/
            case LEVEL:
                ret = new menuItem(MIType.LEVEL,
                                   "Level 1");
                break;
                
            /********** level **********/
            case BACK_TO_MAIN_MENU:
                ret = new menuItem(MIType.BACK_TO_MAIN_MENU,
                                   "Back to main menu");
                break;
        }

        return ret;
    }
    
    public static menuItem getMenuItem(String levelName, String levelKey)
    {
    	return new menuItem(MIType.LEVEL, levelName, levelKey, "");
    }

}
