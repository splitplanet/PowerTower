package com.SplitPlanet.framework;



import android.view.View;

import com.SplitPlanet.BareBones.GameScreen;
//import com.kilobolt.robotgame.R;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.NavUtils;

public abstract class Screen   {
	
	

	protected final Game game;

	private static Screen instance;
	
	



	

//	public Context getApplicationContext()
//	{
//		return null;
//		
//	}
	
	 

//    public static Context getAppContext() {
//    	
//        return Screen.context;
//    }

    public Screen() {
    	game = null;
    }
	
	
    public Screen(Game game) {
    	
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void paint(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
    
	public abstract void backButton();

	
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }
	

}
