package xyq.game.stage.UI.dialog;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.stage.WasActor;
import xyq.game.stage.WasIconActor;
import xyq.game.stage.UI.dialog.comp.DlgEquipBox;
import xyq.game.stage.UI.dialog.comp.DlgItemBox;

public class PlayerBagDlg_old extends Group{
	XYQGame game;
	PlayerBagDlg_old me;
	
	WasActor panel;
	WasActor panel2;

	DlgEquipBox equipBox;
	DlgItemBox itemBox;
	
	WasIconActor headImg;
	
	Button clzBtn;
	TextButton moneyBtn;
	Label moenyLabel;
	Label stroredMoneyLabel;
	boolean coloredMoney;
	public PlayerBagDlg_old(final XYQGame game){
		super();
		this.game=game;
		me=this;
		setSize(482, 167);
		
		panel=new WasActor(game.rs,"wzife.wdf","UI\\道具行囊");
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	else if(button==2){
            		Group group=getParent();
            		remove();
            		group.addActor(me);
            	}
            	return false;
            }
        });
		addActor(panel);
		
		panel2=new WasActor(game.rs,"wzife.wdf","UI\\装备贴图");
		panel2.setPosition(-1, 254);
		addActor(panel2);
		
		itemBox = new DlgItemBox(game,5,4,false);
		itemBox.setPosition(27, 48);
		itemBox.setItemIndexArea(6,26);
		//itemBox.setItems(game.db.loadRoleItemStackData(game.ls.player.id));
		itemBox.setItems(game.ls.player.getItems());
		addActor(itemBox);
		
		equipBox = new DlgEquipBox(game,false);
		equipBox.setPosition(199, 269);
		equipBox.setItemIndexArea(0,5);
		//itemBox.setItems(game.db.loadRoleItemStackData(game.ls.player.id));
		equipBox.setItems(game.ls.player.getItems());
		addActor(equipBox);
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(290, panel.getImg().getH()-20);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
		
        headImg=new WasIconActor(game.rs,"wzife.wdf","道具栏头像\\"+game.ls.player.playerData().Role);
        headImg.setPosition(20+(130-headImg.getImg().getW())/2, 307);
		addActor(headImg);
        
		moneyBtn= new TextButton("现金 ", game.rs.getCommonTextButtonStyle());
		moneyBtn.pad(1);
		moneyBtn.setPosition(3, 280);
		moneyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	toggleColor();
            }
		});
		addActor(moneyBtn);
		
		moenyLabel= new Label(game.ls.player.playerData().moneys[0]+"", game.rs.getBlackUILabelStyle());
		moenyLabel.setBounds(65, 280, 80, 20);
		addActor(moenyLabel);
		
		stroredMoneyLabel= new Label(game.ls.player.playerData().moneys[1]+"", game.rs.getBlackUILabelStyle());
		stroredMoneyLabel.setBounds(65, 258, 80, 20);
		addActor(stroredMoneyLabel);
	}
	public void toggleColor(){
		if(!coloredMoney){
			if(moenyLabel!=null)
				moenyLabel.remove();
			int mo=game.ls.player.playerData().moneys[0];
			String moStr=mo+"";
			if(mo>=10000&&mo<100000){//蓝色万
				moenyLabel= new Label(moStr, game.rs.getDarkBlueLabelStyle(moStr));
			}else if(mo>=100000&&mo<1000000){//绿色十万
				moenyLabel= new Label(moStr, game.rs.getDarkGreenLabelStyle(moStr));
			}else if(mo>=1000000&&mo<10000000){//红色百万
				moenyLabel= new Label(moStr, game.rs.getRedLabelStyle(moStr));
			}else if(mo>=10000000&&mo<100000000){//紫色千万
				moenyLabel= new Label(moStr, game.rs.getPurpleLabelStyle(moStr));
			}else if(mo>=100000000){//棕色亿
				moenyLabel= new Label(moStr, game.rs.getBrownLabelStyle(moStr));
			}else
				moenyLabel= new Label(moStr, game.rs.getBlackUILabelStyle());
			moenyLabel.setBounds(65, 280, 80, 20);
			addActor(moenyLabel);
		}else{
			if(moenyLabel!=null)
				moenyLabel.remove();
			moenyLabel= new Label(game.ls.player.playerData().moneys[0]+"", game.rs.getBlackUILabelStyle());
			moenyLabel.setBounds(65, 280, 80, 20);
			addActor(moenyLabel);
		}
		coloredMoney=!coloredMoney;
	}
	public void refreshAll(){
		equipBox.refresh();
		itemBox.refresh();
		moenyLabel.setText(game.ls.player.playerData().moneys[0]+"");
		stroredMoneyLabel.setText(game.ls.player.playerData().moneys[1]+"");
	}
}
