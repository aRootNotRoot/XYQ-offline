package xyq.game.stage.UI.dialog;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;
import xyq.game.data.Summon;
import xyq.game.data.SummonData;
import xyq.game.stage.WasActor;
import xyq.system.utils.RandomUT;

public class InGameDebugerDlg extends Group{
	XYQGame game;
	WasActor panel;
	Button clzBtn;
	
	TextField equipID;
	
	InGameDebugerDlg me;
	public InGameDebugerDlg(XYQGame xyqgame){
		this.game=xyqgame;
		me=this;
		setSize(538, 443);
		
		panel=new WasActor(game.rs,"wzife.wdf","UI\\帮助中心");
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	else if(button==0){
            		Group group=getParent();
            		remove();
            		group.addActor(me);
            	}
            	return false;
            }
        });
		addActor(panel);
		
		TextButton enterBattleBtn= new TextButton("空战斗", game.rs.getCommonTextButtonStyle());
		enterBattleBtn.pad(1);
		enterBattleBtn.setPosition(15, 400);
		enterBattleBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.cs.BATTLE_enterBattle(null);
            }
		});
		addActor(enterBattleBtn);
		
		TextButton endBattleBtn= new TextButton("结束空战斗", game.rs.getLongCommonTextButtonStyle());
		endBattleBtn.pad(1);
		endBattleBtn.setPosition(130, 400);
		endBattleBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.cs.BATTLE_endBattle();
            }
		});
		addActor(endBattleBtn);
		
		TextButton randomSummonBtn= new TextButton("随机召唤兽", game.rs.getLongCommonTextButtonStyle());
		randomSummonBtn.pad(1);
		randomSummonBtn.setPosition(15, 370);
		randomSummonBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	SummonData data=game.summonDB.makeSummonData(1);
				data.getAddedLiliang();
				int id=RandomUT.getRandom(2, 50);
				SummonData data2=game.summonDB.makeSummonData(id,4,false,false);
				data2.getAddedLiliang();
				SummonData data3=game.summonDB.makeSummonData(98,2,true,false);
				data3.getAddedLiliang();
				id=RandomUT.getRandom(2, 50);
				SummonData data4=game.summonDB.makeSummonData(id,4,false,false);
				data4.getAddedLiliang();
				id=RandomUT.getRandom(2, 50);
				SummonData data5=game.summonDB.makeSummonData(id,2,true,false);
				data5.getAddedLiliang();
				
				Summon newone=game.summonDB.makeSummon(data2);
				game.ls.player.getSummons().add(newone);
				newone=game.summonDB.makeSummon(data3);
				game.ls.player.getSummons().add(newone);
				newone=game.summonDB.makeSummon(data4);
				game.ls.player.getSummons().add(newone);
				newone=game.summonDB.makeSummon(data5);
				game.ls.player.getSummons().add(newone);
				game.cs.UI_refreshSummonBlg();
            }
		});
		addActor(randomSummonBtn);
		
		TextButton equipBtn= new TextButton("生成未鉴定装备", game.rs.getLongCommonTextButtonStyle());
		equipBtn.pad(1);
		equipBtn.setPosition(15, 340);
		equipBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	int ID=Integer.valueOf(equipID.getText());
				ItemStackData itemStackData=game.itemDB.makeUndefinedEquip(ID,false);
				game.cs.ACT_addItem(itemStackData);
            }
		});
		addActor(equipBtn);
		
		equipID = new TextField("装备id", game.rs.getBlackTextFieldStyle(30));
		equipID.setSize(110, 30);
        // 设置文本框的位置
		equipID.setPosition(135, 337);
        // 文本框中的文字居中对齐
		equipID.setAlignment(Align.left);
		addActor(equipID);
		
		TextButton equip2Btn= new TextButton("生成强化打造", game.rs.getLongCommonTextButtonStyle());
		equip2Btn.pad(1);
		equip2Btn.setPosition(15, 310);
		equip2Btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	int ID=Integer.valueOf(equipID.getText());
				ItemStackData itemStackData=game.itemDB.makeUndefinedEquip(ID,true);
				game.cs.ACT_addItem(itemStackData);
            }
		});
		addActor(equip2Btn);
	}
}
