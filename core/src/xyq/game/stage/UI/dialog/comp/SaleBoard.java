package xyq.game.stage.UI.dialog.comp;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.stage.WasIconActor;

public class SaleBoard extends Group{
	XYQGame game;
	WasIconActor panel;
	Label nameLabel;
	public SaleBoard(XYQGame game,String text){
		this.game=game;
		setSize(108, 24);
		
		panel=new WasIconActor(game.rs, "addon.wdf", "平面\\摊位标签3");
		panel.setPosition(0, 0);
		/*
		panel.addListener(new InputListener(){
				@Override
				public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
					panel.setColor(1,1,1,1);
				}
				@Override
				public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
					panel.setColor(0.9f,0.9f,0.9f,1);
				}
				@Override
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					
					return false;
				}
			});
		*/
		addActor(panel);
		
		nameLabel =new Label(text, game.rs.getDarkBlueLabelStyle(text));
		nameLabel.setSize(88, 16);
		nameLabel.setPosition(-44, -14);
		nameLabel.setAlignment(Align.top);
		addActor(nameLabel);
	}
	public void saleNameChange(String newName) {
		if(nameLabel!=null){
			nameLabel.remove();
			nameLabel =new Label(newName, game.rs.getDarkBlueLabelStyle(newName));
			nameLabel.setSize(88, 16);
			nameLabel.setPosition(-44, -14);
			nameLabel.setAlignment(Align.top);
			addActor(nameLabel);
		}
		
	}
}
