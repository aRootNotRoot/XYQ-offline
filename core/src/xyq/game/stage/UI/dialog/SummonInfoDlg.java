package xyq.game.stage.UI.dialog;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.data.ShapeData;
import xyq.game.data.Summon;
import xyq.game.data.SummonData;
import xyq.game.stage.WasActor;
import xyq.game.stage.UI.DialogHud;
import xyq.system.ItemDB;
import xyq.system.assets.PPCData;

public class SummonInfoDlg extends Group{
	XYQGame game;
	
	public final int TIZHI=0;
	public final int LILIANG=1;
	public final int MOLI=2;
	public final int NAILI=3;
	public final int MINJIE=4;
	
	WasActor panel;
	Button clzBtn;
	
	WasActor summonShape;
	WasActor shadow;
	
	Label DJ;
	//Label Name;
	TextField Name;
	Label PickCount;
	Label InBattleLevel;
	Label stat;
	
	Label HP;
	Label MP;
	
	Label gongji;
	Label fangyu;
	Label sudu;
	Label lingli;
	
	Label tizhi;
	Label liliang;
	Label moli;
	Label naili;
	Label minjie;

	Label qianli;
	

	Label shengjiEXP;
	Label huodeEXP;
	Label loyal;
	
	Label summonList_one;
	Label summonList_two;
	Label summonList_three;
	Label summonList_four;
	Label summonList_five;
	Label[] summonList;
	int[] list_index={-1,-1,-1,-1,-1};
	
	TextButton  GengGaiShuXingBtn;
	TextButton  GengGaiNameBtn;
	TextButton  XunYangBtn;
	TextButton  JiNengBtn;
	TextButton  CanZhanBtn;
	
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
	
	SummonInfoDlg me;
	int currentShowIndex=0;
	public SummonInfoDlg(final XYQGame game){
		this.game=game;
		me=this;
		
		setSize(282, 459);
		panel=new WasActor(game.rs,"wzife.wdf","UI\\召唤兽属性");
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1){
            		setVisible(false);
            		if(game.senceStage.dialogUIHud.summonZiZhiDlg!=null){
            			game.senceStage.dialogUIHud.summonZiZhiDlg.setVisible(false);
            		}
            	}
            	else{
            		onTop();
            	}
            	return false;
            }
        });
		addActor(panel);
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(260, 437);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
        
        summonList_one= new Label("召唤兽一", game.rs.getBlackUILabelStyle());
        summonList_one.setBounds(18, 388, 80, 20);
        summonList_one.addListener(new InputListener(){
        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
        		summonList_one.setPosition(20, 388);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				summonList_one.setPosition(18, 388);
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				showDetail(list_index[0]);
				checkAndShowZiZhiDlg();
        		return false;
        	}
		});
		addActor(summonList_one);
        
		summonList_two= new Label("召唤兽二", game.rs.getBlackUILabelStyle());
		summonList_two.setBounds(18, 367, 80, 20);
		summonList_two.addListener(new InputListener(){
        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
        		summonList_two.setPosition(20, 367);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				summonList_two.setPosition(18, 367);
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				showDetail(list_index[1]);
				checkAndShowZiZhiDlg();
        		return false;
        	}
		});
		addActor(summonList_two);
		

		summonList_three= new Label("召唤兽三", game.rs.getBlackUILabelStyle());
		summonList_three.setBounds(18, 346, 80, 20);
		summonList_three.addListener(new InputListener(){
        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
        		summonList_three.setPosition(20, 346);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				summonList_three.setPosition(18, 346);
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				showDetail(list_index[2]);
				checkAndShowZiZhiDlg();
        		return false;
        	}
		});
		addActor(summonList_three);

		summonList_four= new Label("召唤兽四", game.rs.getBlackUILabelStyle());
		summonList_four.setBounds(18, 325, 80, 20);
		summonList_four.addListener(new InputListener(){
        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
        		summonList_four.setPosition(20, 325);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				summonList_four.setPosition(18, 325);
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				showDetail(list_index[3]);
				checkAndShowZiZhiDlg();
        		return false;
        	}
		});
		addActor(summonList_four);

		summonList_five= new Label("召唤兽五", game.rs.getBlackUILabelStyle());
		summonList_five.setBounds(18, 304, 80, 20);
		summonList_five.addListener(new InputListener(){
        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
        		summonList_five.setPosition(20, 304);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				summonList_five.setPosition(18, 304);
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				showDetail(list_index[4]);
				checkAndShowZiZhiDlg();
        		return false;
        	}
		});
		addActor(summonList_five);
		
		summonList=new Label[5];
		summonList[0]=summonList_one;
		summonList[1]=summonList_two;
		summonList[2]=summonList_three;
		summonList[3]=summonList_four;
		summonList[4]=summonList_five;
		
        DJ= new Label("000", game.rs.getBlackUILabelStyle());
        DJ.setBounds(183, 290, 30, 30);
		addActor(DJ);
		
		CanZhanBtn= new TextButton("参战", game.rs.getShortCommonTextButtonStyle());
		CanZhanBtn.pad(1);
		CanZhanBtn.setPosition(223, 293);
		CanZhanBtn.setDisabled(true);
		CanZhanBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!CanZhanBtn.isDisabled())
            		toggleCanZhan();
            }
		});
		addActor(CanZhanBtn);
		
		stat= new Label("", game.rs.getDarkBlueLabelStyle());
		stat.setBounds(150, 405, 30, 30);
		//addActor(stat);
		
		Name = new TextField("", game.rs.getBlackTextFieldStyle(30));
		Name.setSize(110, 30);
        // 设置文本框的位置
		Name.setPosition(56, 243);
        // 文本框中的文字居中对齐
		Name.setAlignment(Align.left);
		addActor(Name);
        
		
		
		PickCount= new Label("0", game.rs.getUILabelStyle());
		PickCount.setBounds(94, 266, 50, 30);
		addActor(PickCount);
		
		InBattleLevel= new Label("0", game.rs.getUILabelStyle());
		InBattleLevel.setBounds(220, 266, 50, 30);
		addActor(InBattleLevel);
		
		
		HP=new Label("00000/00000", game.rs.getBlackUILabelStyle());
		HP.setBounds(53, 218, 80, 30);
		addActor(HP);
		
		MP=new Label("00000/00000", game.rs.getBlackUILabelStyle());
		MP.setBounds(53, 194, 80, 30);
		addActor(MP);
	
		tizhi=new Label("0000", game.rs.getBlackUILabelStyle());
		tizhi.setBounds(185, 218, 80, 30);
		addActor(tizhi);
		
		moli=new Label("0000", game.rs.getBlackUILabelStyle());
		moli.setBounds(185, 194, 80, 30);
		addActor(moli);
		
		naili=new Label("0000", game.rs.getBlackUILabelStyle());
		naili.setBounds(185, 146, 80, 30);
		addActor(naili);
		
		liliang=new Label("0000", game.rs.getBlackUILabelStyle());
		liliang.setBounds(185, 171, 80, 30);
		addActor(liliang);
		
		minjie=new Label("0000", game.rs.getBlackUILabelStyle());
		minjie.setBounds(185, 123, 80, 30);
		addActor(minjie);
		
		qianli=new Label("000", game.rs.getBlackUILabelStyle());
		qianli.setBounds(185, 98, 80, 30);
		addActor(qianli);
		
		gongji=new Label("0000", game.rs.getBlackUILabelStyle());
		gongji.setBounds(56, 170, 80, 30);
		addActor(gongji);
		
		fangyu=new Label("0000", game.rs.getBlackUILabelStyle());
		fangyu.setBounds(56, 146, 80, 30);
		addActor(fangyu);
		
		sudu=new Label("0000", game.rs.getBlackUILabelStyle());
		sudu.setBounds(56, 122, 80, 30);
		addActor(sudu);
		
		
		lingli=new Label("0000", game.rs.getBlackUILabelStyle());
		lingli.setBounds(56, 98, 80, 30);
		addActor(lingli);
		
	
		
		shengjiEXP=new Label("∞", game.rs.getBlackUILabelStyle());
		shengjiEXP.setBounds(87, 51, 80, 30);
		addActor(shengjiEXP);
		
		huodeEXP=new Label("0000000000", game.rs.getBlackUILabelStyle());
		huodeEXP.setBounds(87, 75, 80, 30);
		addActor(huodeEXP);
		
		loyal=new Label("100", game.rs.getBlackUILabelStyle());
		loyal.setBounds(53, 27, 80, 30);
		addActor(loyal);
		
		GengGaiShuXingBtn= new TextButton(" 更改属性 ", game.rs.getCommonTextButtonStyle());
		GengGaiShuXingBtn.pad(1);
		GengGaiShuXingBtn.setPosition(175, 79);
		GengGaiShuXingBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	confirmPoint();
            }
		});
		addActor(GengGaiShuXingBtn);
		
		GengGaiNameBtn= new TextButton(" 更改名称 ", game.rs.getCommonTextButtonStyle());
		GengGaiNameBtn.pad(1);
		GengGaiNameBtn.setPosition(195, 247);
		GengGaiNameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!GengGaiNameBtn.isDisabled())
            		changeName();
            }
		});
		addActor(GengGaiNameBtn);
		
		JiNengBtn= new TextButton(" 查看资质 ", game.rs.getCommonTextButtonStyle());
		JiNengBtn.pad(1);
		JiNengBtn.setPosition(175, 32);
		JiNengBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	showZiZhiDlg();
            }
		});
		addActor(JiNengBtn);
		
		XunYangBtn= new TextButton("驯养", game.rs.getShortCommonTextButtonStyle());
		XunYangBtn.pad(1);
		XunYangBtn.setPosition(95, 32);
		XunYangBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!XunYangBtn.isDisabled())
            		XunYang();
            }
		});
		addActor(XunYangBtn);
		
		tizhiA= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\加点",true));
		tizhiA.setPosition(230, 225);
		tizhiA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!tizhiA.isDisabled())
            		ctrlPoint(TIZHI,true);
            	
            }
        });
		tizhiA.setDisabled(true);
        addActor(tizhiA);  
        
        tizhiM= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\减点",true));
        tizhiM.setPosition(253, 225);
        tizhiM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!tizhiM.isDisabled())
            		ctrlPoint(TIZHI,false);
            		
            }
        });
        tizhiM.setDisabled(true);
        addActor(tizhiM);  
        
        liliangA= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\加点",true));
        liliangA.setPosition(230, 178);
        liliangA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!liliangA.isDisabled())
            		ctrlPoint(LILIANG,true);
            }
        });
        liliangA.setDisabled(true);
        addActor(liliangA);  
        
        liliangM= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\减点",true));
        liliangM.setPosition(253, 178);
        liliangM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!liliangM.isDisabled())
            		ctrlPoint(LILIANG,false);
            }
        });
        liliangM.setDisabled(true);
        addActor(liliangM); 
        
        moliA= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\加点",true));
        moliA.setPosition(230, 201);
        moliA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!moliA.isDisabled())
            		ctrlPoint(MOLI,true);
            }
        });
        moliA.setDisabled(true);
        addActor(moliA);  
        
        moliM= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\减点",true));
        moliM.setPosition(253, 201);
        moliM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!moliM.isDisabled())
            		ctrlPoint(MOLI,false);
            }
        });
        moliM.setDisabled(true);
        addActor(moliM);  
        
        nailiA= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\加点",true));
        nailiA.setPosition(230, 154);
        nailiA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!nailiA.isDisabled())
            		ctrlPoint(NAILI,true);
            }
        });
        nailiA.setDisabled(true);
        addActor(nailiA);  
        
        nailiM= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\减点",true));
        nailiM.setPosition(253, 154);
        nailiM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!nailiM.isDisabled())
            		ctrlPoint(NAILI,false);
            		
            }
        });
        nailiM.setDisabled(true);
        addActor(nailiM); 
        
        minjieA= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\加点",true));
        minjieA.setPosition(230, 130);
        minjieA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!minjieA.isDisabled())
            		ctrlPoint(MINJIE,true);
            		
            }
        });
        minjieA.setDisabled(true);
        addActor(minjieA);  
        
        minjieM= new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\减点",true));
        minjieM.setPosition(253, 130);
        minjieM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!minjieM.isDisabled())
            		ctrlPoint(MINJIE,false);
            		
            }
        });
        minjieM.setDisabled(true);
        addActor(minjieM); 
	}
	public void showZiZhiDlg(){
		game.cs.UI_showDialog(DialogHud.SummonZiZhiDlg_ID);
	   	game.cs.UI_showSummonZZ(currentShowIndex);

	}
	public void checkAndShowZiZhiDlg(){
		if(game.senceStage.dialogUIHud.summonZiZhiDlg==null)
			return;
		if(game.senceStage.dialogUIHud.summonZiZhiDlg.isVisible()){
	    	game.cs.UI_showSummonZZ(currentShowIndex);
		}
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

	/**根据传玩家信息来更新显示*/
	public void updataInfo() {
		ArrayList<Summon> summons=game.ls.player.getSummons();
		summonList_one.setVisible(false);
		summonList_two.setVisible(false);
		summonList_three.setVisible(false);
		summonList_four.setVisible(false);
		summonList_five.setVisible(false);
		for(int i=0;i<5;i++){
			list_index[i]=-1;
		}
		int flag=0;
		for(int i=0;i<summons.size();i++){
			if(flag>4)
				break;
			if(summons.get(i).getSet_index().equals("携带")||summons.get(i).getSet_index().equals("参战")){
				summonList[flag].setVisible(true);
				summonList[flag].setText(summons.get(i).data().getName());
				list_index[flag]=i;
				flag++;
			}
		}
		PickCount.setText(String.valueOf(flag));
		
		if(summons.size()>0){
			showDetail(0);
			if(game.senceStage.dialogUIHud.summonZiZhiDlg!=null)
				game.senceStage.dialogUIHud.summonZiZhiDlg.setVisible(false);
		}else{
			currentShowIndex=-1;
			disableAllAMBtn();
		}
		
	}
	void XunYang(){
		ArrayList<Summon> summons=game.ls.player.getSummons();
		int index=currentShowIndex;
		if(summons.get(index)==null){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ SummonInfoDlg ] -> 驯养第"+index+"个召唤兽失败，因为没这个召唤兽");
			}
			return;
		}
		if(summons.get(index).data().loyal>=100){
			game.cs.UI_showSystemMessage("这个召唤兽已经很忠诚了");
		}else if(game.cs.ACT_delItem(ItemDB.ITEM_BASIC, 83, 1)){//宠物口粮
			summons.get(index).data().loyal+=2;
			if(summons.get(index).data().loyal>100)
				summons.get(index).data().loyal=100;
			showDetail(index);
    	}else if(game.cs.ACT_delItem(ItemDB.ITEM_BASIC, 97, 1)){//高级宠物口粮
    		summons.get(index).data().loyal+=10;
    		if(summons.get(index).data().loyal>100)
				summons.get(index).data().loyal=100;
			showDetail(index);
    	}else{
    		game.cs.UI_showSystemMessage("你的背包里没有口粮了,宠物才不想饿肚子去驯养召唤兽呢");
    	}
	}
	void changeName(){
		ArrayList<Summon> summons=game.ls.player.getSummons();
		int index=currentShowIndex;
		if(summons.get(index)==null){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ SummonInfoDlg ] -> 更改第"+index+"个召唤兽名称失败，因为没这个召唤兽");
			}
			return;
		}
		String name=Name.getText();
		
		summons.get(index).data().setName(name);
		
		updataInfo();
	}
	void toggleCanZhan(){
		ArrayList<Summon> summons=game.ls.player.getSummons();
		int index=currentShowIndex;
		if(summons.get(index)==null){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ SummonInfoDlg ] -> 切换第"+index+"个召唤兽参战信息失败，因为没这个召唤兽");
			}
			return;
		}
		String currentCanZhan=summons.get(index).getSet_index();
		if(!currentCanZhan.equals("携带")&&!currentCanZhan.equals("参战")){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ SummonInfoDlg ] -> 切换第"+index+"个召唤兽参战信息失败，因为这个召唤兽不是携带也不是参战状态");
			}
			return;
		}
		if(currentCanZhan.equals("携带")){
			if(game.summonDB.getModel(summons.get(index).data().getType_id()).level>game.ls.player.playerData().level){//如果当前召唤兽的携带等级比自己大
				game.cs.UI_showSystemMessage(summons.get(index).data().getName()+"的参战等级高过了少侠等级");
				showDetail(index);
				return;
			}
			
			else{
				for(int i=0;i<summons.size();i++){
					if(summons.get(i).getSet_index().equals("参战"))
						summons.get(i).setSet_index("携带");
				}
				summons.get(index).setSet_index("参战");
			}
		}else if(currentCanZhan.equals("参战")){
			summons.get(index).setSet_index("携带");
		}
		showDetail(index);
	}
	/**显示第几个召唤兽的信息*/
	public void showDetail(int index){
		ArrayList<Summon> summons=game.ls.player.getSummons();
		CanZhanBtn.setDisabled(true);
		if(summons.get(index)==null){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ SummonInfoDlg ] -> 显示第"+index+"个召唤兽信息失败，因为没这个召唤兽");
			}
			return;
		}
		if(!summons.get(index).getSet_index().equals("携带")&&!summons.get(index).getSet_index().equals("参战")){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ SummonInfoDlg ] -> 显示第"+index+"个召唤兽信息失败，因为这个召唤兽不是携带也不是参战状态");
			}
			emptyAllInfo();
			
			return;
		}
		SummonData data=summons.get(index).data();
		
		DJ.setText(String.valueOf(data.getLevel()));
		InBattleLevel.setText(String.valueOf(game.summonDB.getModel(data.getType_id()).level));
		Name.setText(data.getName());
		stat.setText(summons.get(index).getSet_index());
		
		if(summons.get(index).getSet_index().equals("携带")){
			CanZhanBtn.setText("参战");
			CanZhanBtn.setDisabled(false);
		}else if(summons.get(index).getSet_index().equals("参战")){
			CanZhanBtn.setText("休息");
			CanZhanBtn.setDisabled(false);
		}
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
		
		gongji.setText(String.valueOf(data.Gongji));
		fangyu.setText(String.valueOf(data.Fangyu));
		sudu.setText(String.valueOf(data.Sudu));
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
		
		huodeEXP.setText(String.valueOf(data.exp));
		loyal.setText(String.valueOf(data.loyal));
		shengjiEXP.setText(String.valueOf(game.db.loadSummonLevelUPExp(data.getLevel())));
		
		
		if(shadow!=null){
			shadow.remove();
		}
		shadow= new WasActor(game.rs,"shape.wdf", "人物阴影");
		shadow.setPosition(173, 320);
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
			
		summonShape.setPosition(210+shapeData.offsetX, 345+shapeData.offsetY);
		addActor(summonShape);
		
		currentShowIndex=index;
		
		autoCtrlBtn(data);
			
		CanZhanBtn.remove();
		addActor(CanZhanBtn);
	}
	public void emptyAllInfo(){
		DJ.setText("");
		InBattleLevel.setText("0");
		Name.setText("");
		stat.setText("");
		HP.setText("");
		MP.setText("");
		gongji.setText("");
		fangyu.setText("");
		sudu.setText("");
		lingli.setText("");
		tizhi.setText("");
		liliang.setText("");
		moli.setText("");
		naili.setText("");
		minjie.setText("");
		
		qianli.setText("");
		huodeEXP.setText("");
		loyal.setText("");
		shengjiEXP.setText("");
		
		if(shadow!=null){
			shadow.remove();
		}
		if(summonShape!=null){
			summonShape.remove();
		}
		currentShowIndex=-1;
		disableAllAMBtn();
	}
	/**操作加点
	 * @param 加点加啥
	 * @param isAdd 是否是加点，false就是减点
	 * */
	void ctrlPoint(int btn,boolean isAdd){
		if(currentShowIndex==-1)
			return;
		SummonData data=game.ls.player.getSummons().get(currentShowIndex).data();
		if(data==null)
			return;
		if(btn==TIZHI){
			if(isAdd){
				if(data.Qianli>0){
					data.Qianli--;
					data.addedTizhi++;
				}
			}else{
				data.Qianli++;
				data.addedTizhi--;
			}
		}else if(btn==LILIANG){
			if(isAdd){
				if(data.Qianli>0){
					data.Qianli--;
					data.addedLiliang++;
				}
			}else{
				data.Qianli++;
				data.addedLiliang--;
			}
		}else if(btn==MOLI){
			if(isAdd){
				if(data.Qianli>0){
					data.Qianli--;
					data.addedMoli++;
				}
			}else{
				data.Qianli++;
				data.addedMoli--;
			}
		}else if(btn==NAILI){
			if(isAdd){
				if(data.Qianli>0){
					data.Qianli--;
					data.addedNaili++;
				}
			}else{
				data.Qianli++;
				data.addedNaili--;
			}
		}else if(btn==MINJIE){
			if(isAdd){
				if(data.Qianli>0){
					data.Qianli--;
					data.addedMinjie++;
				}
			}else{
				data.Qianli++;
				data.addedMinjie--;
			}
		}
		showDetail(currentShowIndex);
	}
	void confirmPoint(){
		if(currentShowIndex==-1)
			return;
		SummonData data=game.ls.player.getSummons().get(currentShowIndex).data();
		if(data==null)
			return;
		
		data.Tizhi+=data.addedTizhi;
		data.addedTizhi=0;
		data.Liliang+=data.addedLiliang;
		data.addedLiliang=0;
		data.Moli+=data.addedMoli;
		data.addedMoli=0;
		data.Naili+=data.addedNaili;
		data.addedNaili=0;
		data.Minjie+=data.addedMinjie;
		data.addedMinjie=0;
		
		data=game.summonDB.reCalcAttr(data);
		
		showDetail(currentShowIndex);
	}
	void autoCtrlBtn(SummonData data){
		if(data.Qianli>0){
			activeAllABtn();
		}else{
			disableAllABtn();
		}
		
		if(data.addedTizhi>0)
			tizhiM.setDisabled(false);
		else
			tizhiM.setDisabled(true);

		if(data.addedLiliang>0)
			liliangM.setDisabled(false);
		else
			liliangM.setDisabled(true);
		
		if(data.addedMoli>0)
			moliM.setDisabled(false);
		else
			moliM.setDisabled(true);
		
		if(data.addedNaili>0)
			nailiM.setDisabled(false);
		else
			nailiM.setDisabled(true);
		
		if(data.addedMinjie>0)
			minjieM.setDisabled(false);
		else
			minjieM.setDisabled(true);
	}
	/**置顶显示*/
	public void onTop(){
		Group group=getParent();
		remove();
		group.addActor(me);
		if(game.senceStage.dialogUIHud.summonZiZhiDlg!=null){
			game.senceStage.dialogUIHud.summonZiZhiDlg.onTop();
		}
	}
}
