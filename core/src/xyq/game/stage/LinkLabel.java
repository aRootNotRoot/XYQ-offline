package xyq.game.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LinkLabel extends Label{
	float oraX;
	float oraY;
	LinkLabelClickAction clickAction;
	public LinkLabel(float x,float y,CharSequence text, LabelStyle style,final LinkLabelClickAction clickAction) {
		super(text, style);
		getStyle().fontColor=new Color(0, 1, 0, 1);
		oraX=x;
		oraY=y;
		setX(x);
		setY(y);
		this.clickAction=clickAction;
		addListener(new InputListener(){
			@Override
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				setX(oraX+1);
				setY(oraY-1);
				clickAction.click();
	            return false;
	        }
	        @Override
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	        	setX(oraX);
				setY(oraY);
	        }
	        @Override
	        public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
	        	getStyle().fontColor=new Color(1, 0, 0, 1);
	    	}
	    	@Override
	    	public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
	    		getStyle().fontColor=new Color(0, 1, 0, 1);
	    		setX(oraX);
				setY(oraY);
	    	}
		});
	}
	
	public LinkLabel(float x,float y,CharSequence text, LabelStyle style,final LinkLabelClickAction clickAction,final Color fontColor,final Color onColor) {
		super(text, style);
		getStyle().fontColor=fontColor;
		oraX=x;
		oraY=y;
		setX(x);
		setY(y);
		this.clickAction=clickAction;
		addListener(new InputListener(){
			@Override
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				setX(oraX+1);
				setY(oraY-1);
				clickAction.click();
	            return false;
	        }
	        @Override
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	        	setX(oraX);
				setY(oraY);
	        }
	        @Override
	        public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
	        	getStyle().fontColor=onColor;
	    	}
	    	@Override
	    	public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
	    		getStyle().fontColor=fontColor;
	    		setX(oraX);
				setY(oraY);
	    	}
		});
	}
    
}
