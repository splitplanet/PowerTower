package com.SplitPlanet.BareBones;

public class Background {
	
	private int BackX,BackY,speedY,speedX;

	public Background(int x, int y)
	{
		BackX=x;
		BackY=y;
	}

	public int getBackX()
	{
		return BackX;
	}

	public void setBackX(int backX,
	                     int lowLimit,
	                     int highLimit)
	{
		BackX = backX;
		if (backX < lowLimit)  BackX=lowLimit;
		if (backX > highLimit) BackX=highLimit;
	}

	public int getBackY()
	{
		return BackY;
	}

	public void setBackY(int backY,
	                     int lowLimit,
	                     int highLimit)
	{
		BackY = backY;
		if (backY < lowLimit)  BackY=lowLimit;
		if (backY > highLimit) BackY=highLimit;
	}

	public int getSpeedY()
	{
		return speedY;
	}

	public void setSpeedY(int speedY)
	{
		this.speedY = speedY;
	}

	public int getSpeedX()
	{
		return speedX;
	}

	public void setSpeedX(int speedX)
	{
		this.speedX = speedX;
	}

	
	
}
