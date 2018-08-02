package xyq.game.stage.UI.dialog;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.data.PlayerData;
import xyq.game.stage.WasActor;
import xyq.game.stage.UI.DialogHud;

public class PlayerInfoDlg extends Group{
	XYQGame game;
	
	public final int TIZHI=0;
	public final int LILIANG=1;
	public final int MOLI=2;
	public final int NAILI=3;
	public final int MINJIE=4;
	
	WasActor panel;
	Button clzBtn;
	
	Label DJ;
	Label Name;
	Label Title;
	Label BangPai;
	Label MenPai;
	Label RenQi;
	Label BangGong;
	Label MenGong;
	
	Label HP;
	Label MP;
	Label SP;
	Label HL;
	Label TL;
	
	TextButton  chengweiBtn;

	Label mingzhong;
	Label shanghai;
	Label fangyu;
	Label sudu;
	Label duobi;
	Label lingli;
	
	Label tizhi;
	Label liliang;
	Label moli;
	Label naili;
	Label minjie;

	Label qianli;
	

	Label shengjiEXP;
	Label huodeEXP;
	
	TextButton  GengGaiShuXingBtn;
	TextButton  ShengJiBtn;
	TextButton  JiNengBtn;
	
	public Button tizhiA;
	public Button tizhiM;
	
	public Button liliangA;
	public Button liliangM;
	
	public Button moliA;
	public Button moliM;
	
	public Button nailiA;
	public Button nailiM;
	
	public Button minjieA;
	public Button minjieM;
	
	PlayerInfoDlg me;
	public PlayerInfoDlg(final XYQGame game){
		this.game=game;
		me=this;

		panel=new WasActor(game.rs,"wzife.wdf","UI\\人物状态");
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

		clzBtn.setPosition(235, 430);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
        
        DJ= new Label("000", game.rs.getBlackUILabelStyle());
        DJ.setBounds(55, 395, 30, 30);
		addActor(DJ);
        
		Name= new Label("未知姓名", game.rs.getBlackLabelStyle("未知姓名"));
		Name.setBounds(140, 395, 80, 30);
		addActor(Name);
        
		Title= new Label("未知称谓", game.rs.getBlackLabelStyle("未知称谓"));
		Title.setBounds(55, 373, 80, 30);
		addActor(Title);
		
		RenQi= new Label("000", game.rs.getBlackUILabelStyle());
		RenQi.setBounds(210, 373, 80, 30);
		addActor(RenQi);
		
		BangPai= new Label("无帮派", game.rs.getBlackLabelStyle("无帮派"));
		BangPai.setBounds(55, 350, 80, 30);
		addActor(BangPai);
		
		BangGong= new Label("000", game.rs.getBlackUILabelStyle());
		BangGong.setBounds(210, 350, 80, 30);
		addActor(BangGong);
		
		MenPai= new Label("无门派", game.rs.getBlackUILabelStyle());
		MenPai.setBounds(55, 327, 80, 30);
		addActor(MenPai);
		
		MenGong= new Label("000", game.rs.getBlackUILabelStyle());
		MenGong.setBounds(210, 327, 80, 30);
		addActor(MenGong);
		
		chengweiBtn = new TextButton("称谓", game.rs.getShortCommonTextButtonStyle());
		chengweiBtn.pad(5);
		chengweiBtn.setPosition(5, 377);
		chengweiBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.cs.Player_toggleTitle(game.ls.player);
            }
		});
		addActor(chengweiBtn);
		
		
		HP=new Label("00000/00000/00000", game.rs.getBlackUILabelStyle());
		HP.setBounds(55, 295, 80, 30);
		addActor(HP);
		
		MP=new Label("00000/00000", game.rs.getBlackUILabelStyle());
		MP.setBounds(55, 272, 80, 30);
		addActor(MP);
		
		SP=new Label("000/150", game.rs.getBlackUILabelStyle());
		SP.setBounds(55, 249, 80, 30);
		addActor(SP);
		
		HL=new Label("000/100", game.rs.getBlackUILabelStyle());
		HL.setBounds(55, 226, 80, 30);
		addActor(HL);
		
		TL=new Label("000/100", game.rs.getBlackUILabelStyle());
		TL.setBounds(55, 203, 80, 30);
		addActor(TL);
		
		mingzhong=new Label("0000", game.rs.getBlackUILabelStyle());
		mingzhong.setBounds(55, 173, 80, 30);
		addActor(mingzhong);
		
		tizhi=new Label("0000", game.rs.getBlackUILabelStyle());
		tizhi.setBounds(150, 173, 80, 30);
		addActor(tizhi);
		
		shanghai=new Label("0000", game.rs.getBlackUILabelStyle());
		shanghai.setBounds(55, 150, 80, 30);
		addActor(shanghai);
		
		moli=new Label("0000", game.rs.getBlackUILabelStyle());
		moli.setBounds(150, 150, 80, 30);
		addActor(moli);
		
		fangyu=new Label("0000", game.rs.getBlackUILabelStyle());
		fangyu.setBounds(55, 127, 80, 30);
		addActor(fangyu);
		
		liliang=new Label("0000", game.rs.getBlackUILabelStyle());
		liliang.setBounds(150, 127, 80, 30);
		addActor(liliang);
		
		sudu=new Label("0000", game.rs.getBlackUILabelStyle());
		sudu.setBounds(55, 104, 80, 30);
		addActor(sudu);
		
		naili=new Label("0000", game.rs.getBlackUILabelStyle());
		naili.setBounds(150, 104, 80, 30);
		addActor(naili);
		
		duobi=new Label("0000", game.rs.getBlackUILabelStyle());
		duobi.setBounds(55, 81, 80, 30);
		addActor(duobi);
		
		minjie=new Label("0000", game.rs.getBlackUILabelStyle());
		minjie.setBounds(150, 81, 80, 30);
		addActor(minjie);
		
		lingli=new Label("0000", game.rs.getBlackUILabelStyle());
		lingli.setBounds(55, 58, 80, 30);
		addActor(lingli);
		
		qianli=new Label("000", game.rs.getBlackUILabelStyle());
		qianli.setBounds(140, 58, 80, 30);
		addActor(qianli);
		
		shengjiEXP=new Label("0000000000", game.rs.getBlackUILabelStyle());
		shengjiEXP.setBounds(85, 28, 80, 30);
		addActor(shengjiEXP);
		
		huodeEXP=new Label("0000000000", game.rs.getBlackUILabelStyle());
		huodeEXP.setBounds(85, 5, 80, 30);
		addActor(huodeEXP);
		
		GengGaiShuXingBtn= new TextButton(" 更改属性 ", game.rs.getCommonTextButtonStyle());
		GengGaiShuXingBtn.pad(1);
		GengGaiShuXingBtn.setPosition(175, 62);
		GengGaiShuXingBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.cs.ACT_confirmPoint(me);
            }
		});
		addActor(GengGaiShuXingBtn);
		
		JiNengBtn= new TextButton("  技 能  ", game.rs.getCommonTextButtonStyle());
		JiNengBtn.pad(1);
		JiNengBtn.setPosition(175, 8);
		JiNengBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.cs.UI_showDialog(DialogHud.smSkillDlg_ID);
            }
		});
		addActor(JiNengBtn);
		
		ShengJiBtn= new TextButton("  升 级  ", game.rs.getCommonTextButtonStyle());
		ShengJiBtn.pad(1);
		ShengJiBtn.setPosition(175, 30);
		ShengJiBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!ShengJiBtn.isDisabled())
            		game.cs.ACT_levelUP();
            }
		});
		addActor(ShengJiBtn);
		
		tizhiA= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\加点",true));
		tizhiA.setPosition(197, 180);
		tizhiA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!tizhiA.isDisabled())
            		game.cs.ACT_addPoint(TIZHI, me);
            	
            }
        });
		tizhiA.setDisabled(true);
        addActor(tizhiA);  
        
        tizhiM= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\减点",true));
        tizhiM.setPosition(220, 180);
        tizhiM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!tizhiM.isDisabled())
            		game.cs.ACT_minusPoint(TIZHI, me);
            		
            }
        });
        tizhiM.setDisabled(true);
        addActor(tizhiM);  
        
        liliangA= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\加点",true));
        liliangA.setPosition(197, 134);
        liliangA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!liliangA.isDisabled())
            		game.cs.ACT_addPoint(LILIANG, me);
            }
        });
        liliangA.setDisabled(true);
        addActor(liliangA);  
        
        liliangM= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\减点",true));
        liliangM.setPosition(220, 134);
        liliangM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!liliangM.isDisabled())
            		game.cs.ACT_minusPoint(LILIANG, me);
            }
        });
        liliangM.setDisabled(true);
        addActor(liliangM); 
        
        moliA= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\加点",true));
        moliA.setPosition(197, 157);
        moliA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!moliA.isDisabled())
            		game.cs.ACT_addPoint(MOLI, me);
            }
        });
        moliA.setDisabled(true);
        addActor(moliA);  
        
        moliM= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\减点",true));
        moliM.setPosition(220, 157);
        moliM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!moliM.isDisabled())
            		game.cs.ACT_minusPoint(MOLI, me);
            }
        });
        moliM.setDisabled(true);
        addActor(moliM);  
        
        nailiA= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\加点",true));
        nailiA.setPosition(197, 111);
        nailiA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!nailiA.isDisabled())
            		game.cs.ACT_addPoint(NAILI, me);
            		
            }
        });
        nailiA.setDisabled(true);
        addActor(nailiA);  
        
        nailiM= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\减点",true));
        nailiM.setPosition(220, 111);
        nailiM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!nailiM.isDisabled())
            		game.cs.ACT_minusPoint(NAILI, me);
            		
            }
        });
        nailiM.setDisabled(true);
        addActor(nailiM); 
        
        minjieA= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\加点",true));
        minjieA.setPosition(197, 88);
        minjieA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!minjieA.isDisabled())
            		game.cs.ACT_addPoint(MINJIE, me);
            		
            }
        });
        minjieA.setDisabled(true);
        addActor(minjieA);  
        
        minjieM= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\减点",true));
        minjieM.setPosition(220, 88);
        minjieM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!minjieM.isDisabled())
            		game.cs.ACT_minusPoint(MINJIE, me);
            		
            }
        });
        minjieM.setDisabled(true);
        addActor(minjieM); 
	}
	
	public void disableAllAMBtn(){
		tizhiA.setDisabled(true);
		liliangA.setDisabled(true);
		moliA.setDisabled(true);
		nailiA.setDisabled(true);
		minjieA.setDisabled(true);
		
		tizhiM.setDisabled(true);
		liliangM.setDisabled(true);
		moliM.setDisabled(true);
		nailiM.setDisabled(true);
		minjieM.setDisabled(true);
	}
	public void activeAllAMBtn(){
		tizhiA.setDisabled(false);
		liliangA.setDisabled(false);
		moliA.setDisabled(false);
		nailiA.setDisabled(false);
		minjieA.setDisabled(false);
		
		tizhiM.setDisabled(false);
		liliangM.setDisabled(false);
		moliM.setDisabled(false);
		nailiM.setDisabled(false);
		minjieM.setDisabled(false);
	}
	public void disableAllABtn(){
		tizhiA.setDisabled(true);
		liliangA.setDisabled(true);
		moliA.setDisabled(true);
		nailiA.setDisabled(true);
		minjieA.setDisabled(true);
	}
	public void disableAllMBtn(){
		tizhiM.setDisabled(true);
		liliangM.setDisabled(true);
		moliM.setDisabled(true);
		nailiM.setDisabled(true);
		minjieM.setDisabled(true);
	}
	public void activeAllABtn(){
		tizhiA.setDisabled(false);
		liliangA.setDisabled(false);
		moliA.setDisabled(false);
		nailiA.setDisabled(false);
		minjieA.setDisabled(false);
	}
	public void activeAllMBtn(){
		tizhiM.setDisabled(false);
		liliangM.setDisabled(false);
		moliM.setDisabled(false);
		nailiM.setDisabled(false);
		minjieM.setDisabled(false);
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
	public void setNameSoSo(String name,String title,String bangpai){
		if(Name!=null)
			Name.remove();
		Name= new Label(name, game.rs.getBlackLabelStyle(name));
		Name.setBounds(140, 395, 80, 30);
		addActor(Name);
        
		if(Title!=null)
			Title.remove();
		Title= new Label(title, game.rs.getBlackLabelStyle(title));
		Title.setBounds(55, 373, 80, 30);
		addActor(Title);
		
		if(BangPai!=null)
			BangPai.remove();
		BangPai= new Label(bangpai, game.rs.getBlackLabelStyle(bangpai));
		BangPai.setBounds(55, 350, 80, 30);
		addActor(BangPai);
	}
	/**根据传进来的玩家信息来更新显示，一般用在游戏中，不会更新头像数据，因为这不会换*/
	public void updataPlrBasicInfo(PlayerData data) {
		
		DJ.setText(String.valueOf(data.level));
		
		//Name.setText(data.name);
		
		RenQi.setText(String.valueOf(data.popularoty));
		//BangPai.setText(data.Bangpai);
		MenPai.setText(data.Menpai);
		//Title.setText(data.Title);
		
		BangGong.setText(String.valueOf(data.Banggong));
		MenGong.setText(String.valueOf(data.Mengong));
		
		StringBuilder sb=new StringBuilder();
		sb.append(data.HP);
		sb.append("/");
		sb.append(data.HPTempMax);
		sb.append("/");
		sb.append(data.HPMax);
		HP.setText(sb.toString());
		sb.delete(0, sb.length());
		
		sb.append(data.MP);
		sb.append("/");
		sb.append(data.MPMax);
		MP.setText(sb.toString());
		sb.delete(0, sb.length());
		
		sb.append(data.SP);
		sb.append("/");
		sb.append(data.SPMax);
		SP.setText(sb.toString());
		sb.delete(0, sb.length());
		
		sb.append(data.Huoli);
		sb.append("/");
		sb.append(data.HuoliMax);
		HL.setText(sb.toString());
		sb.delete(0, sb.length());
		
		sb.append(data.Tili);
		sb.append("/");
		sb.append(data.TiliMax);
		TL.setText(sb.toString());
		sb.delete(0, sb.length());
		
		mingzhong.setText(String.valueOf(data.Mingzhong));
		shanghai.setText(String.valueOf(data.Shanghai));
		fangyu.setText(String.valueOf(data.Fangyu));
		sudu.setText(String.valueOf(data.Sudu));
		duobi.setText(String.valueOf(data.Duoshan));
		lingli.setText(String.valueOf(data.Lingli));
		
		
		tizhi.setText(String.valueOf(data.addedTizhi+data.Tizhi));
		if(data.addedTizhi>0)
			setRedColor(tizhi);
		else
			setBlackColor(tizhi);
		
		moli.setText(String.valueOf(data.addedMoli+data.Moli));
		if(data.addedMoli>0)
			setRedColor(moli);
		else
			setBlackColor(moli);
		
		liliang.setText(String.valueOf(data.addedLiliang+data.Liliang));
		if(data.addedLiliang>0)
			setRedColor(liliang);
		else
			setBlackColor(liliang);
		
		naili.setText(String.valueOf(data.addedNaili+data.Naili));
		if(data.addedNaili>0)
			setRedColor(naili);
		else
			setBlackColor(naili);
		
		minjie.setText(String.valueOf(data.addedMinjie+data.Minjie));
		if(data.addedMinjie>0)
			setRedColor(minjie);
		else
			setBlackColor(minjie);
		
		qianli.setText(String.valueOf(data.Qianli));
		
		
		
		/*
		if(data.Qianli>0){
			playerInfoDlg.disabledJDBtn();
			playerInfoDlg.activeJDABtn();
		}else{
			playerInfoDlg.disabledJDBtn();
		}
		*/
		if(data.Qianli>0)
			activeAllABtn();
			
		huodeEXP.setText(String.valueOf(data.Exp));
			
		shengjiEXP.setText(String.valueOf(game.ls.player.levelUpExp));
		
	}
}
