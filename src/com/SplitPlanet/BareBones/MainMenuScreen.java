package com.SplitPlanet.BareBones;

import java.util.List;








import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import com.SplitPlanet.framework.Game;
import com.SplitPlanet.framework.Graphics;
import com.SplitPlanet.framework.Screen;
import com.SplitPlanet.framework.Input.TouchEvent;
import com.SplitPlanet.framework.implementation.AndroidGame;
import com.SplitPlanet.BareBones.GameScreen;

public class MainMenuScreen extends Screen {
	public MainMenuScreen(Game game) {
		super(game);
	}
	static Context context = AndroidGame.getContext();
	
	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		

		int len = touchEvents.size();

		for (int i = 0; i < len; i++)
		{
			TouchEvent event = touchEvents.get(i);

		    if (event.type == TouchEvent.TOUCH_UP)
		    {
		    	// screen is 800 x 480
				if (inBounds(event, 0, 0, 1000, 1000))
		        {
                    game.setScreen(new levelSelect(game));
			    }
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

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawImage(Assets.menu, 0, 0);
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
