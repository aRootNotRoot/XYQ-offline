package xyq.game.stage.UI.dialog;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.data.MagicData;
import xyq.game.data.SkillData;
import xyq.game.stage.WasActor;
import xyq.game.stage.WasIconActor;
import xyq.game.stage.UI.DialogHud;
import xyq.game.stage.UI.dialog.comp.ScrollLabel;

public class JQSkillDlg extends Group{
	XYQGame game;
	
	final int id=0;
	final int name=1;
	final int description=2;
	final int effection=3;
	final int magicSkill=4;
	final int ismain=5;
	final int[][] pointPos={
			{17,130},
			{77,130},
			{137,130},
			{197,130},
			{257,130},
			{317,130},
			{377,130},
			
			{17,81},
			{77,81},
			{137,81},
			{197,81},
			{257,81},
			{317,81},
			{377,81}
	};//0-基础技能学会的点数位置
	final int[][] magicPos={
			{17,150},
			{77,150},
			{137,150},
			{197,150},
			{257,150},
			{317,150},
			{377,150},
			
			{17,101},
			{77,101},
			{137,101},
			{197,101},
			{257,101},
			{317,101},
			{377,101}
	};
	WasActor panel;
	Button clzBtn;

	/**师门主技能信息*/
	ArrayList<SkillData> SMSkillInfo;
	HashMap<String,Integer> learnedPoint;
	/**师门学到的技能信息*/
	String[][] SMLearnedSkillInfo;
	/**师门主技能图标组*/
	WasIconActor[] mainSkillIcons;
	/**师门主技能技能点数*/
	Label[] mainSkillPoints;
	Label jqPoints;
	
	/**学会的可用技能图标组*/
	WasIconActor[] learnedSkillIcons;
	/**装备特技图标组*/
	WasIconActor[] equipSkillIcons;
	
	/**绿色的技能名字*/
	Label skillNameLabel;
	/**技能详细内容表*/
	ScrollLabel detailLabel;

	private JQSkillDlg me;
	
	public JQSkillDlg(final XYQGame game){
		super();
		this.game=game;
		me=this;
		setSize(451, 465);
		
		panel=new WasActor(game.rs,"wzife.wdf","UI\\辅助技能2");
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
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(431, 447);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
        

		
		TextButton  smBtn = new TextButton("师门技能", game.rs.getLongCommonTextButtonStyle());
		smBtn.pad(20);
		smBtn.setPosition(40, 415);
		smBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                game.cs.UI_showDialog(DialogHud.smSkillDlg_ID);
            }
        });
        addActor(smBtn);
        
        TextButton  fzBtn = new TextButton("辅助技能", game.rs.getLongCommonTextButtonStyle());
        fzBtn.pad(20);
        fzBtn.setPosition(190, 415);
        fzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	setVisible(false);
                game.cs.UI_showDialog(DialogHud.fzSkillDlg_ID);
            	
            }
        });
        addActor(fzBtn);
        
        TextButton  jqBtn = new TextButton("剧情技能", game.rs.getLongCommonTextButtonStyle());
        jqBtn.pad(20);
        jqBtn.setPosition(340, 415);
        jqBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	setVisible(false);
                game.cs.UI_showDialog(DialogHud.jqSkillDlg_ID);
            }
        });
        addActor(jqBtn);
		
        TextButton  cookBtn = new TextButton("烹饪", game.rs.getCommonTextButtonStyle());
        cookBtn.pad(20);
        cookBtn.setPosition(20, 10);
        cookBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.cs.ACT_cookBySelf();
            }
        });
        addActor(cookBtn);
        
        TextButton  medcBtn = new TextButton("炼药", game.rs.getCommonTextButtonStyle());
        medcBtn.pad(20);
        medcBtn.setPosition(80, 10);
        medcBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.cs.UI_showDialog(DialogHud.makePillDlg_ID);
            }
        });
        addActor(medcBtn);
        
        TextButton  sellerBtn = new TextButton("摆摊", game.rs.getCommonTextButtonStyle());
        sellerBtn.pad(20);
        sellerBtn.setPosition(140, 10);
        sellerBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.cs.ACT_sellBaiTan();
            }
        });
        addActor(sellerBtn);
        
        
        
	}
	
	public void load(){
		clearData();
		loadLearnedJQ();
	}
	void loadLearnedJQ() {
		String menpai="剧情";
		ArrayList<SkillData> learnedMagics=game.db.loadLearnedJQSkill();
		learnedSkillIcons=new WasIconActor[14];
		if(learnedMagics.size()==0){
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[辅助技能对话框] -> 当前角色未学习任何剧情技能，不用加载已经学会的技能");
			}
			return;
		}
		learnedPoint=game.db.loadLearnedSkillPoint_toMap();
		mainSkillPoints=new Label[14];
		for(int i=0;i<learnedMagics.size();i++){
			final SkillData mData=learnedMagics.get(i);
			
			String pack="wzife.wdf";
			String waString="技能\\"+menpai+"\\"+mData.name;
			
			learnedSkillIcons[i]=new WasIconActor(game.rs,pack,waString);
			learnedSkillIcons[i].setPosition(magicPos[i][0]-3,magicPos[i][1]-1);
			learnedSkillIcons[i].addListener(new ClickListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            		showDetail(mData);
	            	return false;
	            }
	        });
			addActor(learnedSkillIcons[i]);
			
			mainSkillPoints[i]=new Label(learnedPoint.get(mData.id)+"",game.rs.getBlackUILabelStyle());
			mainSkillPoints[i].setPosition(pointPos[i][0]+3,pointPos[i][1]-8);
			mainSkillPoints[i].setWidth(27);mainSkillPoints[i].setHeight(18);
			mainSkillPoints[i].setAlignment(Align.center);
			addActor(mainSkillPoints[i]);
		}
		
		jqPoints=new Label(game.ls.player.playerData().jqPoint+"",game.rs.getBlackUILabelStyle());
		jqPoints.setPosition(160,217);
		jqPoints.setWidth(27);jqPoints.setHeight(18);
		jqPoints.setAlignment(Align.center);
		addActor(jqPoints);
	}
	public void clearData(){
		if(mainSkillIcons!=null)
			for(WasIconActor icon : mainSkillIcons)
				if(icon!=null)icon.remove();
		/**师门主技能技能点数*/
		if(mainSkillPoints!=null)
			for(Label label : mainSkillPoints)
				if(label!=null)label.remove();
		/**学会的可用技能图标组*/
		if(learnedSkillIcons!=null)
			for(WasIconActor icon : learnedSkillIcons)
				if(icon!=null)icon.remove();
		/**装备特技图标组*/
		if(equipSkillIcons!=null)
			for(WasIconActor icon : equipSkillIcons)
				if(icon!=null)icon.remove();
	}
	void showDetail(SkillData data){
		if(detailLabel!=null)
			detailLabel.remove();
		if(skillNameLabel!=null)
			skillNameLabel.remove();
		skillNameLabel=new Label(data.name, game.rs.getDarkGreenLabelStyle(data.name));
		skillNameLabel.setPosition(250, 385);
		addActor(skillNameLabel);
		StringBuilder desc=new StringBuilder();
		desc.append(data.description);
		if(!data.effection.equals("0"))
			desc.append(" [学习效果]"+data.effection);
		if(!data.magic_skill.equals("0"))
			desc.append("  [解锁技能]"+data.magic_skill);
		detailLabel=new ScrollLabel(game,desc.toString(),game.rs.getBlackLabelStyle(desc.toString()), 130, 170,16);
		detailLabel.setEndLine(9);
		detailLabel.setPosition(250, 212);
		detailLabel.setAlignment(Align.topLeft);
		detailLabel.addListener(new ClickListener() {
			@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(button==1)
            		detailLabel.preLine();
            	else
            		detailLabel.nextLine();
            	
            	return false;
            }
        });
		addActor(detailLabel);
		
		/*
		MyActor debugPanel=new MyActor(game.rs.getRect(130+2,170+2));
		debugPanel.setPosition(250-1, 208-1);
		addActor(debugPanel);
		*/
	}
	
	void showMagicDetail(MagicData data){
		if(detailLabel!=null)
			detailLabel.remove();
		if(skillNameLabel!=null)
			skillNameLabel.remove();
		skillNameLabel=new Label(data.name, game.rs.getDarkGreenLabelStyle(data.name));
		skillNameLabel.setPosition(250, 385);
		addActor(skillNameLabel);
		StringBuilder desc=new StringBuilder();
		desc.append(data.description);
		desc.append(data.effection);
		desc.append(data.conditions);
		desc.append(data.consumption);
		
		detailLabel=new ScrollLabel(game,desc.toString(),game.rs.getBlackLabelStyle(desc.toString()), 130, 170,16);
		detailLabel.setEndLine(9);
		detailLabel.setPosition(250, 212);
		detailLabel.addListener(new ClickListener() {
			@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(button==1)
            		detailLabel.preLine();
            	else
            		detailLabel.nextLine();
            	
            	return false;
            }
        });
		addActor(detailLabel);
		
		/*
		MyActor debugPanel=new MyActor(game.rs.getRect(130+2,170+2));
		debugPanel.setPosition(250-1, 208-1);
		addActor(debugPanel);
		*/
	}
}
