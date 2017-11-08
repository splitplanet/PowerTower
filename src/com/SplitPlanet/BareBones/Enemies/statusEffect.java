package com.SplitPlanet.BareBones.Enemies;

public class statusEffect
{
    public int time,
               count;
    public double quantEffect;
    public StatusEffectEnum statEff;
    public int posDamage,
               posTime;
    
    public statusEffect (int              inTime,
                         StatusEffectEnum effect,
                         double           inQuant)
    {
        count       = 0;
        time        = inTime;
        statEff     = effect;
        quantEffect = inQuant;
    }
    
    public static statusEffect statusEffectPos (int inTime,
                                         int inPosDam,
                                         int inPosTime)
    {
        statusEffect ret = new statusEffect(inTime,
                                            StatusEffectEnum.POISON,
                                            0);
        ret.posDamage = inPosDam;
        ret.posTime   = inPosTime;
        
        return ret;
    }
}
