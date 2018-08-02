package xyq.game.stage.UI.dialog;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.stage.WasActor;

public class ConfirmRemoveItemDlg extends Group{
	XYQGame game;
	
	WasActor panel;
	Label label;
	TextButton  yesBtn;
	TextButton  noBtn;

	int waitToRemoveIndex=-1;
	
	ConfirmRemoveItemDlg me;
	public ConfirmRemoveItemDlg(final XYQGame game){
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
            	confirmRemove();
            	setVisible(false);
            }
        });
        addActor(yesBtn); 
        
        noBtn=new TextButton("取消", game.rs.getLongCommonTextButtonStyle());
        noBtn.setPosition(180, 20);
        noBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	cancelRemove();
            	setVisible(false);
            }
        });
        addActor(noBtn); 
        
        label = new Label("要移除这个格子的东西吗？", game.rs.getUILabelStyle());
        label.setBounds(15, 70, 350, 30);
		addActor(label);
	
	
		
	}
	
	@Override
	public void act(float delta){

		//GdxAI.getTimepiece().update(delta);
		
	}
	void reDrawUI(){
		String itemName=game.ls.ifm.ITEM_getItemName(game.ls.ifm.PLAYER_getItem(waitToRemoveIndex));
		label.setText("要扔掉这位置的所有["+itemName+"]吗?");
	}
	public void confirmRemoveItem(int itemIndex){
		this.waitToRemoveIndex=itemIndex;
		reDrawUI();
	}
	void confirmRemove(){
		game.cs.ACT_removeItem(waitToRemoveIndex);
		waitToRemoveIndex=-1;
	}
	void cancelRemove(){
		waitToRemoveIndex=-1;
	}
}