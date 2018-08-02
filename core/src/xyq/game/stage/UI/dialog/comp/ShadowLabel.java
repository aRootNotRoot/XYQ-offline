package xyq.game.stage.UI.dialog.comp;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import xyq.game.XYQGame;

public class ShadowLabel extends Group{
	Label label;
	Label blackLabel;
	
	public ShadowLabel(String text,XYQGame game,float x,float y,float width,float height){

		blackLabel=new Label(text, game.rs.getBlackLabelStyle(text));
		blackLabel.setBounds(x+1, y-1, width, height);
		addActor(blackLabel);
		
		label=new Label(text, game.rs.getLabelStyle(text));
		label.setBounds(x, y, width, height);
		addActor(label);
	}
}
