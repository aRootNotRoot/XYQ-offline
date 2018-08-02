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

public class SMSkillDlg extends Group{
	XYQGame game;
	
	final int id=0;
	final int name=1;
	final int description=2;
	final int effection=3;
	final int magicSkill=4;
	final int ismain=5;
	final int[][] iconPos={
			{105,295},
			{105,364},
			{165,330},
			{165,261},
			{105,226},
			{45,261},
			{45,330}
	};//0-基础技能位置
	final int[][] pointPos={
			{110,275},
			{110,344},
			{170,310},
			{170,241},
			{110,206},
			{50,241},
			{50,310}
	};//0-基础技能学会的点数位置
	final int[][] magicPos={
			{30,150},
			{90,150},
			{150,150},
			{210,150},
			{270,150},
			{330,150},
			{390,150},
			
			{30,101},
			{90,101},
			{150,101},
			{210,101},
			{270,101},
			{330,101},
			{390,101}
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
	
	/**学会的可用技能图标组*/
	WasIconActor[] learnedSkillIcons;
	/**装备特技图标组*/
	WasIconActor[] equipSkillIcons;
	
	/**绿色的技能名字*/
	Label skillNameLabel;
	/**技能详细内容表*/
	ScrollLabel detailLabel;

	private SMSkillDlg me;
	
	public SMSkillDlg(final XYQGame game){
		super();
		this.game=game;
		me=this;
		setSize(451, 465);
		
		panel=new WasActor(game.rs,"wzife.wdf","UI\\主技能");
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
		loadSM();
		loadLearnedSM();
		loadEquip();
	}
	void loadLearnedSM() {
		String menpai=game.ls.player.playerData().Menpai;
		if(menpai.equals("无门派")){
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[师门技能对话框] -> 当前角色无门派，不用加载门派已经学会的技能");
			}
			return;
		}
		ArrayList<MagicData> learnedMagics=game.db.loadUnlockedSMMagic();
		learnedSkillIcons=new WasIconActor[14];
		for(int i=0;i<learnedMagics.size();i++){
			final MagicData mData=learnedMagics.get(i);
			
			String pack="wzife.wdf";
			String waString="技能\\"+menpai+"\\"+mData.name;
			
			learnedSkillIcons[i]=new WasIconActor(game.rs,pack,waString);
			learnedSkillIcons[i].setPosition(magicPos[i][0]-3,magicPos[i][1]-1);
			learnedSkillIcons[i].addListener(new ClickListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	
	            	if(button==1)
	            		game.cs.ACT_normalUseMagic(mData);
	            	else
	            		showMagicDetail(mData);
	            	return false;
	            }
	        });
			addActor(learnedSkillIcons[i]);
			
		}
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

	void loadSM(){
		String menpai=game.ls.player.playerData().Menpai;
		if(menpai.equals("无门派")){
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[师门技能对话框] -> 当前角色无门派，不用加载门派技能");
			}
			return;
		}
		SMSkillInfo=game.db.loadSMAllSkill(menpai);
		learnedPoint=game.db.loadLearnedSMBasicSkillPoint_toMap();
		if(learnedPoint.size()!=7){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[师门技能对话框]->数据库读取的学会的师门技能点数长度不对，可能有没学的师门技能。应该学会7个师门主技能");
			}
			return;
		}
		int basicIndex=-1;
		for(int i=0;i<7;i++){
			SkillData skill=SMSkillInfo.get(i);
			if(skill.isBasic){
				basicIndex=i;
				break;
			}
		}
		if(basicIndex!=0||basicIndex==-1){
			//交换主技能信息到第一位
			SkillData basicData=SMSkillInfo.get(basicIndex);
			SkillData zeroData=SMSkillInfo.get(0);
			SMSkillInfo.set(0, basicData);
			SMSkillInfo.set(basicIndex, zeroData);
			//
		}
		mainSkillIcons=new WasIconActor[7];
		mainSkillPoints=new Label[7];
		//Gdx.app.error("sadadasda", "sssssssssssssssssssssssssss");
		for(int i=0;i<7;i++){
			final SkillData data=SMSkillInfo.get(i);
			String pack="wzife.wdf";
			String waString="技能\\"+menpai+"\\"+data.name;
			
			mainSkillIcons[i]=new WasIconActor(game.rs,pack,waString);
			mainSkillIcons[i].setPosition(iconPos[i][0],iconPos[i][1]);
			mainSkillIcons[i].addListener(new ClickListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	showDetail(data);
	            	return false;
	            }
	        });
			addActor(mainSkillIcons[i]);
			
			mainSkillPoints[i]=new Label(learnedPoint.get(data.id)+"",game.rs.getBlackUILabelStyle());
			mainSkillPoints[i].setPosition(iconPos[i][0]+6,iconPos[i][1]-24);
			mainSkillPoints[i].setWidth(27);mainSkillPoints[i].setHeight(18);
			mainSkillPoints[i].setAlignment(Align.center);
			addActor(mainSkillPoints[i]);
			
			/*
			MyActor debugPanel=new MyActor(game.rs.getRect(27,18));
			debugPanel.setPosition(iconPos[i][0]+6,iconPos[i][1]-24);
			addActor(debugPanel);
			*/
		}

	}
	void loadEquip(){
		
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
		detailLabel=new ScrollLabel(game,desc.toString(),game.rs.getBlackUILabelStyle(), 130, 170,16);
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
		
		detailLabel=new ScrollLabel(game,desc.toString(),game.rs.getBlackUILabelStyle(), 130, 170,16);
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
}
