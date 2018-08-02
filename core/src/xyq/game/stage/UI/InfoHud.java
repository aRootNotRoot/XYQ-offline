package xyq.game.stage.UI;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.data.Player;
import xyq.game.data.PlayerData;
import xyq.game.data.Summon;
import xyq.game.stage.WasActor;
import xyq.game.stage.WasIconActor;

public class InfoHud extends Group{
	XYQGame game;
	
	public WasActor mapPanel;
	public Button sMapBtn;
	public Button bMapBtn;
	
	public Label mapNameLabel;
	public Label mapCoorLabel;
	
	public WasActor headImg;
	
	public WasActor emptyHPPanel;
	public WasActor emptyMPPanel;
	public WasActor emptySPPanel;
	public WasActor emptyEXPPanel;

	public WasActor emptyHeadImgPanel;

	public WasActor emptyPetImgPanel;

	public WasActor emptyPetHPPanel;
	public WasActor emptyPetMPPanel;
	public WasActor emptyPetEXPPanel;
	
	public WasActor barButtonPanel;
	public Button bagBtn;
	public Button giveBtn;
	public Button tradeBtn;
	public Button teamBtn;
	public Button petBtn;
	public Button taskBtn;
	public Button friendBtn;
	public Button actBtn;
	public Button systemBtn;
	
	public Image inputPanel;
	public TextField inputTextField;
	public String lastSaidWords;
	
	
	public Image HPMaxBar;
	public Image HPBar;
	public Image MPBar;
	public Image SPBar;
	public Image EXPBar;
	
	public Image PetHPBar;
	public Image PetMPBar;
	public Image PetEXPBar;
	
	public WasActor findMyRole;
	
	public WasIconActor timeWas;
	String lastTime="";
	
	public InfoHud(final XYQGame game){
		super();
		this.game=game;
		this.setPosition(0, 0);
		mapPanel=new WasActor(game.rs,"wzife.wdf","UI\\左上角楼饰");
		mapPanel.setPosition(0, 641);
		addActor(mapPanel);
		
		String text="未知位置";
        mapNameLabel = new Label(text, game.rs.getUILabelStyle());
        mapNameLabel.setAlignment(Align.center);
        mapNameLabel.setBounds(8, 690, 100, 20);
        addActor(mapNameLabel);
        
        text="X:--- Y:---";
        mapCoorLabel = new Label(text, game.rs.getUILabelStyle());
        mapCoorLabel.setAlignment(Align.center);
        mapCoorLabel.setBounds(6, 639, 100, 20);
        addActor(mapCoorLabel);
		
		ButtonStyle style= new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(game.rs.getFrames("wzife.wdf", "UI\\灯笼按钮").getFrame(0, 0).image));
		style.down = new TextureRegionDrawable(new TextureRegion(game.rs.getFrames("wzife.wdf", "UI\\灯笼按钮").getFrame(0, 1).image));
		style.over = new TextureRegionDrawable(new TextureRegion(game.rs.getFrames("wzife.wdf", "UI\\灯笼按钮").getFrame(0, 2).image));
		style.pressedOffsetX=1;
		style.pressedOffsetY=-1;
		sMapBtn = new Button(style);

		sMapBtn.setPosition(95, 644);
		sMapBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(game.is_Debug)
            		Gdx.app.log("[ XYQ ]", "小地图按钮被点击了@"+x+","+y);
                game.cs.UI_showCurrentSmallMap();
            }
        });
        addActor(sMapBtn); 
		bMapBtn= new Button(style);

		bMapBtn.setPosition(95, 665);
		bMapBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(game.is_Debug)
            		Gdx.app.log("[ XYQ ]", "大地图按钮被点击了@"+x+","+y);
            	game.cs.UI_showDialog(DialogHud.worldMapDlg_ID);
            }
        });
        addActor(bMapBtn); 
		
        emptyHPPanel=new WasActor(game.rs,"wzife.wdf","UI\\空血槽");
        emptyHPPanel.setPosition(1210, 700);
		addActor(emptyHPPanel);
		
		HPMaxBar = game.rs.makeNewBarImage(1f,1222,703,"wzife.wdf", "UI\\伤势条");
        addActor(HPMaxBar); 
		
		HPBar = game.rs.makeNewBarImage(1f,1222,703,"wzife.wdf", "UI\\血条");
        addActor(HPBar); 
		
		emptyMPPanel=new WasActor(game.rs,"wzife.wdf","UI\\空血槽");
        emptyMPPanel.setPosition(1210, 686);
		addActor(emptyMPPanel);
		
		MPBar = game.rs.makeNewBarImage(1,1222,689,"wzife.wdf", "UI\\魔法条");
        addActor(MPBar); 
		
		emptySPPanel=new WasActor(game.rs,"wzife.wdf","UI\\空血槽");
        emptySPPanel.setPosition(1210, 672);
		addActor(emptySPPanel);
		
		SPBar = game.rs.makeNewBarImage(1f,1222,675,"wzife.wdf", "UI\\愤怒条");
        addActor(SPBar); 
		
		emptyEXPPanel=new WasActor(game.rs,"wzife.wdf","UI\\空血槽");
		emptyEXPPanel.setPosition(1210, 658);
		addActor(emptyEXPPanel);
		
		EXPBar = game.rs.makeNewBarImage(1f,1222,661,"wzife.wdf", "UI\\经验条");
        addActor(EXPBar); 
		
		emptyHeadImgPanel=new WasActor(game.rs,"wzife.wdf","UI\\空小方框头像");
		emptyHeadImgPanel.setPosition(1157, 663);
		addActor(emptyHeadImgPanel);
		
		emptyPetImgPanel=new WasActor(game.rs,"wzife.wdf","UI\\空宠物血条");
		emptyPetImgPanel.setPosition(1050, 675);
		emptyPetImgPanel.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				emptyPetImgPanel.setPosition(1050, 674);
				if(game.ls.ifm.SYSTEM_isBattling())
					game.cs.UI_showSystemMessage("当前正在战斗中");
				else
					game.cs.UI_showDialog(DialogHud.SummonInfoDlg_ID);
				return true;
			}

			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				emptyPetImgPanel.setPosition(1050, 675);
			}
		});
		addActor(emptyPetImgPanel);
		
		emptyPetHPPanel=new WasActor(game.rs,"wzife.wdf","UI\\空血槽");
		emptyPetHPPanel.setPosition(1087, 699);
		addActor(emptyPetHPPanel);

		emptyPetMPPanel=new WasActor(game.rs,"wzife.wdf","UI\\空血槽");
		emptyPetMPPanel.setPosition(1087, 686);
		addActor(emptyPetMPPanel);

		emptyPetEXPPanel=new WasActor(game.rs,"wzife.wdf","UI\\空血槽");
		emptyPetEXPPanel.setPosition(1087, 674);
		addActor(emptyPetEXPPanel);
		
		PetHPBar = game.rs.makeNewBarImage(1f,1099,702,"wzife.wdf", "UI\\血条");
        addActor(PetHPBar); 
        
        PetMPBar = game.rs.makeNewBarImage(1f,1099,689,"wzife.wdf", "UI\\魔法条");
        addActor(PetMPBar); 
        
        PetEXPBar = game.rs.makeNewBarImage(1f,1099,677,"wzife.wdf", "UI\\经验条");
        addActor(PetEXPBar); 
		
		inputPanel=new Image(new TextureRegion(game.rs.getFrames("wzife.wdf", "UI\\聊天输入框").getFrame(0, 0).image));
        inputPanel.setBounds(0, 0, 350, 23);
		addActor(inputPanel);
		
		inputTextField = new TextField("", game.rs.getWhiteTextFieldStyle(24));
		inputTextField.setSize(300, 24);
        // 设置文本框的位置
		inputTextField.setPosition(25, 0);
        // 文本框中的文字居中对齐
		inputTextField.setAlignment(Align.left);
		inputTextField.addListener(new InputListener(){
			@Override
	        public boolean keyDown(InputEvent event, int keycode) {
	            switch (keycode) {
	                case Input.Keys.ENTER: {
	                	lastSaidWords=inputTextField.getText();
	                	game.cs.Player_Say(game.ls.player, lastSaidWords);
	                	inputTextField.setText("");
	                    break;
	                }
	                case Input.Keys.UP: {
	                	inputTextField.setText(lastSaidWords);
	                    break;
	                }
	                default: {
	                    //Gdx.app.log("[ XYQ ]", "其他按键, KeyCode: " + keycode);
	                    break;
	                }
	            }
	            return false;
		}});
		addActor(inputTextField);
		
		barButtonPanel=new WasActor(game.rs,"wzife.wdf","UI\\城墙");
		barButtonPanel.setPosition(962, 0);
		addActor(barButtonPanel);
		
		bagBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\包裹按钮",true));
		bagBtn.setPosition(970, 2);
		bagBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!bagBtn.isDisabled()){
            		if(game.is_Debug)
            			Gdx.app.log("[ XYQ ]", "包裹按钮被点击了@"+x+","+y);
            		game.cs.UI_showSystemMessage("道具索引系统需要优化");
            		game.cs.UI_showDialog(DialogHud.playerBagDlg_ID);
            	}
            }
        });
		bagBtn.setDisabled(false);
        addActor(bagBtn);
          
        giveBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\给予按钮",true));
        giveBtn.setPosition(1000, 2);
        giveBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!giveBtn.isDisabled()){
            		if(game.is_Debug)
            			Gdx.app.log("[ XYQ ]", "给予按钮被点击了@"+x+","+y);
            		game.cs.ACT_wantGive();
            	}
            }
        });
        giveBtn.setDisabled(false);
        addActor(giveBtn);  
        
        
        tradeBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\交易按钮",true));
        tradeBtn.setPosition(1030, 2);
        tradeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!tradeBtn.isDisabled()){
            		if(game.is_Debug)
            			Gdx.app.log("[ XYQ ]", "交易按钮被点击了@"+x+","+y);
            		game.cs.ACT_wantTrade();
            	}
            }
        });
        tradeBtn.setDisabled(false);
        addActor(tradeBtn); 
        
        teamBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\队伍按钮",true));
        teamBtn.setPosition(1060, 2);
        teamBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!teamBtn.isDisabled())
            		Gdx.app.log("[ XYQ ]", "队伍按钮被点击了@"+x+","+y);
            }
        });
        teamBtn.setDisabled(false);
        addActor(teamBtn);  
        
        petBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\宠物按钮",true));
        petBtn.setPosition(1090, 2);
        petBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!petBtn.isDisabled())
            		Gdx.app.log("[ XYQ ]", "宠物按钮被点击了@"+x+","+y);
            }
        });
        petBtn.setDisabled(false);
        addActor(petBtn);  
        
        taskBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\任务按钮",true));
        taskBtn.setPosition(1120, 2);
        taskBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!taskBtn.isDisabled())
            		Gdx.app.log("[ XYQ ]", "任务按钮被点击了@"+x+","+y);
            }
        });
        taskBtn.setDisabled(false);
        addActor(taskBtn);  
        
        friendBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\好友按钮",true));
        friendBtn.setPosition(1150, 2);
        friendBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!friendBtn.isDisabled())
            		Gdx.app.log("[ XYQ ]", "好友按钮被点击了@"+x+","+y);
            }
        });
        friendBtn.setDisabled(false);
        addActor(friendBtn);
        
        actBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\动作按钮",true));
        actBtn.setPosition(1180, 2);
        actBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!actBtn.isDisabled())
            		Gdx.app.log("[ XYQ ]", "动作按钮被点击了@"+x+","+y);
            }
        });
        actBtn.setDisabled(false);
        addActor(actBtn);   
        
        systemBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\系统按钮",true));
        systemBtn.setPosition(1210, 2);
        systemBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(!systemBtn.isDisabled()){
            		if(game.is_Debug)
            			Gdx.app.log("[ XYQ ]", "系统按钮被点击了@"+x+","+y);
            		//game.senceStage.dialogUIHud.showDialog(DialogHud.systemDlg_ID);
            		game.cs.UI_showDialog(DialogHud.systemDlg_ID);
            	}
            }
        });
        systemBtn.setDisabled(false);
        addActor(systemBtn);
        
        findMyRole=new WasActor(game.rs, "wzife.wdf", "UI\\红小方框");
        findMyRole.setPosition(0, 0);
        findMyRole.setVisible(false);
        addActor(findMyRole);
        
        timeWas=new WasIconActor(game.rs, "wzife.wdf", "时间\\午时");
        timeWas.setPosition(3, 653);
        timeWas.setVisible(true);
        addActor(timeWas);
    	
	}
	public void setHeadImg(String pack,String was){
		if(headImg!=null)
			headImg.remove();
		headImg=new WasActor(game.rs,pack,was);
		headImg.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				headImg.setPosition(1160, 664);
				if(game.ls.ifm.SYSTEM_isBattling())
					game.cs.UI_showSystemMessage("当前正在战斗中");
				else
					game.cs.UI_showDialog(DialogHud.playerInfoDlg_ID);
				return true;
			}

			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				headImg.setPosition(1159, 665);
			}
		});
		headImg.setPosition(1159, 665);
		addActor(headImg);
	}
	@Override
	public void act (float delta) {
		super.act(delta);
		game.ls.player.updateInfoHud();
		findMyRole.setPosition(game.ls.player.actor.getX()+game.ls.ifm.MAP_getCurrentMap().getX()-26, game.ls.player.actor.getY()+game.ls.ifm.MAP_getCurrentMap().getY());
		checkTime();
	}
	public void checkTime(){
		String time=game.ts.getGameTime();
		if(!time.equals(lastTime)){
			lastTime=time;
			
			timeWas.remove();
			
			timeWas=new WasIconActor(game.rs, "wzife.wdf", "时间\\"+time);
		    timeWas.setPosition(3, 653);
		    timeWas.setVisible(true);
		    addActor(timeWas);

			game.cs.System_timePass(time);
		}
	}
	public void setPlayerCoor(int[] location) {
		String text="X:"+location[0]+" Y:"+location[1];
		mapCoorLabel.setText(text);
	}

	public void setPlayerMapName(String mapName) {
		mapNameLabel.setText(mapName);
		
	}
	public void setBarPercent(Player plr) {
		PlayerData data=plr.playerData();
		
		if(HPMaxBar!=null)
			HPMaxBar.remove();
		HPMaxBar = game.rs.makeNewBarImage(game.ls.ifm.MATH_getPercent((float)data.HPTempMax/(float)data.HPMax),1222,703,"wzife.wdf", "UI\\伤势条");
        addActor(HPMaxBar); 
        
        if(HPBar!=null)
        	HPBar.remove();
        HPBar = game.rs.makeNewBarImage(game.ls.ifm.MATH_getPercent((float)data.HP/(float)data.HPMax),1222,703,"wzife.wdf", "UI\\血条");
        addActor(HPBar); 
        
        if(MPBar!=null)
        	MPBar.remove();
        MPBar = game.rs.makeNewBarImage(game.ls.ifm.MATH_getPercent((float)data.MP/(float)data.MPMax),1222,689,"wzife.wdf", "UI\\魔法条");
        addActor(MPBar); 
		
        if(SPBar!=null)
        	SPBar.remove();
		SPBar = game.rs.makeNewBarImage(game.ls.ifm.MATH_getPercent((float)data.SP/(float)data.SPMax),1222,675,"wzife.wdf", "UI\\愤怒条");
        addActor(SPBar); 
		
        if(EXPBar!=null)
        	EXPBar.remove();
		EXPBar = game.rs.makeNewBarImage(game.ls.ifm.MATH_getPercent((float)data.Exp/(float)game.ls.player.levelUpExp),1222,661,"wzife.wdf", "UI\\经验条");
        addActor(EXPBar); 
        
        Summon summon=null;
        ArrayList<Summon> summons=plr.getSummons();
        for(int i=0;i<summons.size();i++){
        	if(summons.get(i).getSet_index().equals("参战")){
        		summon=summons.get(i);
        		break;
        	}
        }
        
        if(PetHPBar!=null)
        	PetHPBar.remove();
        if(PetMPBar!=null)
        	PetMPBar.remove();
        if(PetEXPBar!=null)
        	PetEXPBar.remove();
        if(summon!=null){
	        PetHPBar = game.rs.makeNewBarImage(game.ls.ifm.MATH_getPercent((float)summon.data().HP/(float)summon.data().HPMax),1099,702,"wzife.wdf", "UI\\血条");
	        addActor(PetHPBar); 
	
	        PetMPBar = game.rs.makeNewBarImage(game.ls.ifm.MATH_getPercent((float)summon.data().MP/(float)summon.data().MPMax),1099,689,"wzife.wdf", "UI\\魔法条");
	        addActor(PetMPBar); 
	        
	        PetEXPBar = game.rs.makeNewBarImage(game.ls.ifm.MATH_getPercent((float)summon.data().exp/(float)summon.getLevelUpExp()),1099,667,"wzife.wdf", "UI\\经验条");
	        addActor(PetEXPBar); 
        }
		//setPercent(HPMaxBar,game.ls.getPercent((float)data.HPTempMax/(float)data.HPMax));
		//setPercent(HPBar,game.ls.getPercent((float)data.HP/(float)data.HPMax));
		//setPercent(MPBar,game.ls.getPercent((float)data.MP/(float)data.MPMax));
		//setPercent(SPBar,game.ls.getPercent((float)data.SP/(float)data.SPMax));
		//setPercent(EXPBar,game.ls.getPercent((float)data.Exp/(float)game.ls.player.levelUpExp));
		
	}

}
