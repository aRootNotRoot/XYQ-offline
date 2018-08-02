package xyq.game.stage.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.stage.InfoActor;
import xyq.system.utils.TextUtil;

public class SystemInfoHud extends Group{
	XYQGame game;
	Label textLabel;
	InfoActor panel;
	
	boolean showing=false;
	float showedTime=0;
	final float showTimeCount=4;
	
	public SystemInfoHud(XYQGame game){
		super();
		this.game=game;
		this.setPosition(600, 350);
		panel=new InfoActor(game.rs);
		//panel.setSize(160, 16);
		//addActor(panel);
		
		this.setVisible(false);
		addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	setVisible(false);
            	return false;
            }
        });
	}
	@Override
	public void act(float delta){
		super.act(delta);
		 if(showing){
	        	showedTime+=delta;
	        	if(showedTime>showTimeCount){
	        		showing=false;
	        		setVisible(false);
	        		showedTime=0;
	        	}
	        }
	}
	public void hide(){
		showing=false;
		setVisible(false);
		showedTime=0;
	}
	/**显示一条系统信息*/
	public void showMessage(String text){
		 if(textLabel!=null){
			 textLabel.remove();
			 textLabel=null;
		 }
		 if(panel!=null){
			 panel.remove();
			 panel=null;
		 }
		panel=new InfoActor(game.rs);
		panel.setY(20);
		panel.setSize(TextUtil.getStrLength(text, 16)+20, 26);
		setX(XYQGame.V_WIDTH/2-(text.length()*17+20)/2);
		addActor(panel);
		
		textLabel=new Label(text, game.rs.getLabelStyle(text));
		textLabel.setX(10);
		textLabel.getStyle().fontColor=new Color(1, 1, 0, 1);
		addActor(textLabel);
		
		setVisible(true);
		showedTime=0;
		showing=true;
	}
}
