package xyq.game.stage.UI.dialog;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.stage.WasActor;

public class ConfirmDlg extends Group{
	XYQGame game;
	
	WasActor panel;
	Label label;
	TextButton  yesBtn;
	TextButton  noBtn;

	ConfirmDlg me;
	public ConfirmDlg(final XYQGame game){
		super();
		this.game=game;
		me=this;
		setSize(321, 139);
		
		panel=new WasActor(game.rs,"wzife.wdf","UI\\通用对话框小");
		panel.setPosition(0, 0);
		addActor(panel);
		
		
        
        yesBtn=new TextButton("确定", game.rs.getLongCommonTextButtonStyle());
        yesBtn.setPosition(50, 20);
        yesBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	
            }
        });
        addActor(yesBtn); 
        
        noBtn=new TextButton("取消", game.rs.getLongCommonTextButtonStyle());
        noBtn.setPosition(50, 20);
        noBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	
            }
        });
        addActor(noBtn); 
        
        label = new Label("", game.rs.getRedLabelStyle(""));
        label.setBounds(95, 100, 350, 30);
		addActor(label);
	
	
		
	}
	
	@Override
	public void act(float delta){

		//GdxAI.getTimepiece().update(delta);
		
	}
}