package xyq.game.stage.UI.dialog;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.stage.WasActor;
import xyq.game.stage.XYQMapActor;

public class SmallMapDlg extends Group{
	XYQGame game;
	
	WasActor panel;
	Button clzBtn;
	public SmallMapDlg(final XYQGame game){
		super();
		this.game=game;
		
		setSize(482, 167);
		
		panel=new WasActor(game.rs,"wzife.wdf","UI\\通用对话框中");
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	return false;
            }
        });
		addActor(panel);
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(460, 147);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
        
		
	}
	
	public void showCurrentMapSmallMap(){

		

		panel.remove();
		panel=new WasActor(game.rs,game.maps.currentMap().smallMapPack,game.maps.currentMap().smallMapWas);
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	return false;
            }
        });
		addActor(panel);
		
		clzBtn.remove();
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(panel.getImg().getW()-21, panel.getImg().getH()-18);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 

        setSize(panel.getImg().getW(), panel.getImg().getH());
	}

	public void showMapSmallMap(XYQMapActor currentMap) {
		panel.remove();
		panel=new WasActor(game.rs,currentMap.smallMapPack,currentMap.smallMapWas);
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	return false;
            }
        });
		addActor(panel);
		
		clzBtn.remove();
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(panel.getImg().getW()-21, panel.getImg().getH()-18);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 

        setSize(panel.getImg().getW(), panel.getImg().getH());
	}
}
