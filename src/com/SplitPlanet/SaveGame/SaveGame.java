package com.SplitPlanet.SaveGame;

import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import java.io.File;
import java.io.FileNotFoundException;

public class SaveGame {
	
	public SaveGame(String FN) throws IOException{
		try {
			FileOutputStream writer = new FileOutputStream(FN);
			writer.write("test".getBytes());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
