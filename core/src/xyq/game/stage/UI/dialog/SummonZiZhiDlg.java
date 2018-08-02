package xyq.game.stage.UI.dialog;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.data.SummonData;
import xyq.game.stage.WasActor;
import xyq.game.stage.UI.dialog.comp.SummonSkillIconUnit;

public class SummonZiZhiDlg extends Group{
	XYQGame game;
	
	public final int TIZHI=0;
	public final int LILIANG=1;
	public final int MOLI=2;
	public final int NAILI=3;
	public final int MINJIE=4;
	public final int MAX_SKILL_COUNT=12;
	
	public final int[][] SKILL_ICON_POS={
		{21,100},{63,100},{105,100},{147,100},
		{21,58},{63,58},{105,58},{147,58},
		{21,16},{63,16},{105,16},{147,16}
	};
	
	WasActor panel;
	Button clzBtn;
	SummonSkillIconUnit[] skill_Icons;
	
	Label gongji;
	Label fangyu;
	Label tili;
	Label fali;
	Label sudu;
	Label duoshan;
	

	Label life;
	Label grow;
	Label element;
	
	SummonZiZhiDlg me;
	public SummonZiZhiDlg(final XYQGame game){
		this.game=game;
		me=this;

		setSize(207, 454);
		panel=new WasActor(game.rs,"wzife.wdf","UI\\召唤兽资质");
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	else{
            		onTop();
            	}
            	return false;
            }
            
        });
		addActor(panel);
		
		skill_Icons=new SummonSkillIconUnit[MAX_SKILL_COUNT];
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(187, 433);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
        
		gongji=new Label("", game.rs.getBlackUILabelStyle());
		gongji.setBounds(136, 330, 80, 30);
		addActor(gongji);
		
		fangyu=new Label("", game.rs.getBlackUILabelStyle());
		fangyu.setBounds(136, 307, 80, 30);
		addActor(fangyu);
		
		tili=new Label("", game.rs.getBlackUILabelStyle());
		tili.setBounds(136, 284, 80, 30);
		addActor(tili);
		
		fali=new Label("", game.rs.getBlackUILabelStyle());
		fali.setBounds(136, 261, 80, 30);
		addActor(fali);
		
		sudu=new Label("", game.rs.getBlackUILabelStyle());
		sudu.setBounds(136, 238, 80, 30);
		addActor(sudu);
		
		duoshan=new Label("", game.rs.getBlackUILabelStyle());
		duoshan.setBounds(136, 215, 80, 30);
		addActor(duoshan);
		
		life=new Label("", game.rs.getBlackUILabelStyle());
		life.setBounds(136, 193, 80, 30);
		addActor(life);
		
		grow=new Label("", game.rs.getBlackUILabelStyle());
		grow.setBounds(136, 170, 80, 30);
		addActor(grow);
		
		element=new Label("", game.rs.getBlackUILabelStyle());
		element.setBounds(146, 148, 80, 30);
		addActor(element);

	}
	

	public void setRedColor(Label label){
		Label.LabelStyle style = label.getStyle();
        style.fontColor = new Color(1, 0, 0, 1);
        label.setStyle(style);
	}
	public void setBlackColor(Label label){
		Label.LabelStyle style = label.getStyle();
        style.fontColor = new Color(0, 0, 0, 1);
        label.setStyle(style);
	}

	/**根据传进来的信息来更新显示*/
	public void updataSummonZizhi(SummonData data) {
		gongji.setText(String.valueOf(data.attack));
		fangyu.setText(String.valueOf(data.defence));
		tili.setText(String.valueOf(data.healthy));
		fali.setText(String.valueOf(data.magic));
		sudu.setText(String.valueOf(data.speed));
		duoshan.setText(String.valueOf(data.miss));
		
		life.setText(String.valueOf(data.life));
		grow.setText(String.valueOf(data.grow));
		element.setText(data.element);
		
		for(int i=0;i<MAX_SKILL_COUNT;i++){
			if(skill_Icons[i]!=null)
				skill_Icons[i].remove();
		}
		
		for(int i=data.getSkillName().size()-1;i>=0;i--){
			if(i>=MAX_SKILL_COUNT)
				break;
			final String name=data.getSkillName().get(i);
			if(name==null||name.equals(""))
				continue;
			skill_Icons[i]=new SummonSkillIconUnit(game, "wzife.wdf", name);
			skill_Icons[i].setPosition(SKILL_ICON_POS[i][0], SKILL_ICON_POS[i][1]);
			addActor(skill_Icons[i]);
		}
		
	}
	/**置顶显示*/
	public void onTop(){
		Group group=getParent();
		remove();
		group.addActor(me);
	}
}
