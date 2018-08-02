package xyq.game.desktop;

import java.io.File;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import xyq.game.XYQGame;

/**桌面程序启动器*/
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="梦幻西游 - offline -";
		config.addIcon("assets/icon.png", FileType.Internal);
		config.width=1280;
		config.height=720;
		config.resizable=false;
		File file=new File("full.txt");
		if(!file.exists())    
			config.fullscreen=false;
		else
			config.fullscreen=true;
		new LwjglApplication(new XYQGame(), config);
	}
}
