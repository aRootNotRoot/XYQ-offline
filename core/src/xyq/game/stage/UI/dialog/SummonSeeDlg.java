package xyq.game.stage.UI.dialog;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.data.ShapeData;
import xyq.game.data.Summon;
import xyq.game.data.SummonData;
import xyq.game.stage.WasActor;
import xyq.game.stage.UI.dialog.comp.SummonSkillIconUnit;
import xyq.system.assets.PPCData;

public class SummonSeeDlg extends Group{
	XYQGame game;
	
	public final int TIZHI=0;
	public final int LILIANG=1;
	public final int MOLI=2;
	public final int NAILI=3;
	public final int MINJIE=4;
	public final int MAX_SKILL_COUNT=12;
	
	public final int[][] SKILL_ICON_POS={
		{267,100},{309,100},{351,100},{393,100},
		{267,58},{309,58},{351,58},{393,58},
		{267,16},{309,16},{351,16},{393,16}
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
	
	
	Label DJ;
	Label Name;
	Label InBattleLevel;
	
	Label HP;
	Label MP;
	
	Label gongji_attr;
	Label fangyu_attr;
	Label sudu_attr;
	Label lingli;
	
	Label tizhi;
	Label liliang;
	Label moli;
	Label naili;
	Label minjie;

	Label qianli;

	Label loyal;
	
	WasActor summonShape;
	WasActor shadow;
	
	SummonSeeDlg me;
	public SummonSeeDlg(final XYQGame game){
		this.game=game;
		me=this;

		setSize(452, 381);
		panel=new WasActor(game.rs,"wzife.wdf","UI\\召唤兽观察");
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

		clzBtn.setPosition(432, 361);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
        
		gongji=new Label("", game.rs.getBlackUILabelStyle());
		gongji.setBounds(383, 325, 80, 30);
		addActor(gongji);
		
		fangyu=new Label("", game.rs.getBlackUILabelStyle());
		fangyu.setBounds(383, 304, 80, 30);
		addActor(fangyu);
		
		tili=new Label("", game.rs.getBlackUILabelStyle());
		tili.setBounds(383, 281, 80, 30);
		addActor(tili);
		
		fali=new Label("", game.rs.getBlackUILabelStyle());
		fali.setBounds(383, 258, 80, 30);
		addActor(fali);
		
		sudu=new Label("", game.rs.getBlackUILabelStyle());
		sudu.setBounds(383, 236, 80, 30);
		addActor(sudu);
		
		duoshan=new Label("", game.rs.getBlackUILabelStyle());
		duoshan.setBounds(383, 214, 80, 30);
		addActor(duoshan);
		
		life=new Label("", game.rs.getBlackUILabelStyle());
		life.setBounds(383, 190, 80, 30);
		addActor(life);
		
		grow=new Label("", game.rs.getBlackUILabelStyle());
		grow.setBounds(383, 167, 80, 30);
		addActor(grow);
		
		element=new Label("", game.rs.getBlackUILabelStyle());
		element.setBounds(393, 145, 80, 30);
		addActor(element);

		
		DJ= new Label("等级", game.rs.getBlackUILabelStyle());
        DJ.setBounds(218, 151, 30, 30);
		addActor(DJ);

		
		Name = new Label("名字", game.rs.getBlackUILabelStyle());
		Name.setSize(110, 30);
        // 设置文本框的位置
		Name.setPosition(70,179);
        // 文本框中的文字居中对齐
		Name.setAlignment(Align.left);
		addActor(Name);
		
		InBattleLevel= new Label("参战", game.rs.getBlackUILabelStyle());
		InBattleLevel.setBounds(115, 151, 50, 30);
		addActor(InBattleLevel);
		
		
		HP=new Label("00000/00000", game.rs.getBlackUILabelStyle());
		HP.setBounds(60, 126, 80, 30);
		addActor(HP);
		
		MP=new Label("0000/0000", game.rs.getBlackUILabelStyle());
		MP.setBounds(60, 102, 80, 30);
		addActor(MP);
	
		gongji_attr=new Label("0006", game.rs.getBlackUILabelStyle());
		gongji_attr.setBounds(60, 78, 80, 30);
		addActor(gongji_attr);
		
		fangyu_attr=new Label("0007", game.rs.getBlackUILabelStyle());
		fangyu_attr.setBounds(60, 54, 80, 30);
		addActor(fangyu_attr);
		
		sudu_attr=new Label("0008", game.rs.getBlackUILabelStyle());
		sudu_attr.setBounds(60, 30, 80, 30);
		addActor(sudu_attr);
		
		lingli=new Label("0009", game.rs.getBlackUILabelStyle());
		lingli.setBounds(60, 6, 80, 30);
		addActor(lingli);
		
		
		
		tizhi=new Label("0000", game.rs.getBlackUILabelStyle());
		tizhi.setBounds(208, 126, 80, 30);
		addActor(tizhi);
		
		moli=new Label("0001", game.rs.getBlackUILabelStyle());
		moli.setBounds(208, 102, 80, 30);
		addActor(moli);
		
		naili=new Label("0002", game.rs.getBlackUILabelStyle());
		naili.setBounds(208, 54, 80, 30);
		addActor(naili);
		
		liliang=new Label("0003", game.rs.getBlackUILabelStyle());
		liliang.setBounds(208, 78, 80, 30);
		addActor(liliang);
		
		minjie=new Label("0004", game.rs.getBlackUILabelStyle());
		minjie.setBounds(208, 30, 80, 30);
		addActor(minjie);
		
		qianli=new Label("005", game.rs.getBlackUILabelStyle());
		qianli.setBounds(208, 6, 80, 30);
		addActor(qianli);
		
		
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
		
		DJ.setText(String.valueOf(data.getLevel()));
		InBattleLevel.setText(String.valueOf(game.summonDB.getModel(data.getType_id()).level));
		Name.setText(data.getName());
		
		StringBuilder sb=new StringBuilder();
		sb.append(data.HP);
		sb.append("/");
		sb.append(data.HPMax);
		HP.setText(sb.toString());
		sb.delete(0, sb.length());
		
		sb.append(data.MP);
		sb.append("/");
		sb.append(data.MPMax);
		MP.setText(sb.toString());
		sb.delete(0, sb.length());
		
		gongji_attr.setText(String.valueOf(data.Gongji));
		fangyu_attr.setText(String.valueOf(data.Fangyu));
		sudu_attr.setText(String.valueOf(data.Sudu));
		lingli.setText(String.valueOf(data.Lingli));
		
		tizhi.setText(String.valueOf(data.Tizhi+data.addedTizhi));
		if(data.addedTizhi>0)
			setRedColor(tizhi);
		else
			setBlackColor(tizhi);
		liliang.setText(String.valueOf(data.Liliang+data.addedLiliang));
		if(data.addedLiliang>0)
			setRedColor(liliang);
		else
			setBlackColor(liliang);
		moli.setText(String.valueOf(data.Moli+data.addedMoli));
		if(data.addedMoli>0)
			setRedColor(moli);
		else
			setBlackColor(moli);
		naili.setText(String.valueOf(data.Naili+data.addedNaili));
		if(data.addedNaili>0)
			setRedColor(naili);
		else
			setBlackColor(naili);
		minjie.setText(String.valueOf(data.Minjie+data.addedMinjie));
		if(data.addedMinjie>0)
			setRedColor(minjie);
		else
			setBlackColor(minjie);
		qianli.setText(String.valueOf(data.Qianli));
		
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
		
		if(shadow!=null){
			shadow.remove();
		}
		shadow= new WasActor(game.rs,"shape.wdf", "人物阴影");
		shadow.setPosition(85, 205);
        addActor(shadow);
		
		if(summonShape!=null){
			summonShape.remove();
		}
		
		String shapeName=game.db.loadSummonShapeName(data.getShapeID());
		ShapeData shapeData=game.shapeManager.getShape(shapeName);
		
		if(data.is_BY){//如果变异了
			int[] colorations={1};
			PPCData pp=game.rs.getPPData(shapeData.getColoration_pack(),shapeData.getColoration_was(),colorations);
			summonShape=new WasActor(game.rs,shapeData.pack,shapeData.wases.get(ShapeData.STAND),pp);
		
		}else
			summonShape=new WasActor(game.rs,shapeData.pack,shapeData.wases.get(ShapeData.STAND));
			
		summonShape.setPosition(122+shapeData.offsetX, 230+shapeData.offsetY);
		addActor(summonShape);
		
	}	
	/**置顶显示*/
	public void onTop(){
		Group group=getParent();
		remove();
		group.addActor(me);
	}


	public void setSeenSummon(Summon summon) {
		updataSummonZizhi(summon.data());
		
	}
}
