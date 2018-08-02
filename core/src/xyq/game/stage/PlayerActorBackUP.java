package xyq.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import xyq.game.XYQGame;
import xyq.system.assets.PPCData;
import xyq.system.utils.TextUtil;

public class PlayerActorBackUP  extends Group{
	public final static String[] actStr={"静立","行走"};
	public final static int STAND=0;
	public final static int WALK=1;
	final static Color nameLabelGreen=new Color(0,0.88f,0.47f,1);
	final static Color nameLabelOrange=new Color(1,0.8f,0,1);
	final static Color nameLabelBlue=new Color(0.2f,0.74f,0.9f,1);
	
	XYQGame game;
	String roleType;
	String name;
	String title;
	
	WasActor[] roleImgs;
	WasActor shadow;
	
	WasActor[] weapon;
	WasActor addon;
	
	int currentStat=STAND;
	
	Label nameLabel;
	Label nameLabelShadow;

	Label titleLabel;
	Label titleLabelShadow;
	
	int inMapLogicX;
	int inMapLogicY;
	
	Label chatLabel;
	ChatActor chatPanel;
	Group chatBubble;
	
	boolean showingChat=false;
	float showedTime=0;
	final float showChatTimeCount=4;
	
	boolean showingAddon=false;
	
	public PlayerActorBackUP(final XYQGame game,String role,PPCData pp){
		this.game=game;
		this.roleType=role;
		this.roleImgs=new WasActor[actStr.length];
		

		shadow= new WasActor(game.rs,"shape.wdf", "人物阴影");
		shadow.setPosition(-35, -20);
        addActor(shadow);
		
		for(int i=0;i<actStr.length;i++){
			if(pp==null)
				roleImgs[i]= new WasActor(game.rs,"shape.wdf", "人物\\"+role+"_"+actStr[i]);
			else
				roleImgs[i]= new WasActor(game.rs,"shape.wdf", "人物\\"+role+"_"+actStr[i],pp);
			roleImgs[i].setColor(0.9f,0.9f,0.9f,1);
			roleImgs[i].setPosition(-40, -13);
			roleImgs[i].setVisible(false);
	        addActor(roleImgs[i]);
		}
        
		addListener(new InputListener(){
			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				getCurrentWasActor().setColor(1,1,1,1);
			}
			@Override
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				getCurrentWasActor().setColor(0.9f,0.9f,0.9f,1);
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(game.is_Debug){
					Gdx.app.log("[ XYQ ]", "[地图] -> 调试点击了一个角色:"+name);
					game.senceStage.map.justClickARole=true;
				}
				return false;
			}
		});
		
	}
	public void say(String words){
		if(chatBubble!=null){
			chatBubble.remove();
			chatBubble=null;
		}
		
		int pos=0;
		int height=20;
		for(int i=0;i<words.length();i++){
			pos++;
			if(pos==8){
				pos=0;
				height+=18;
			}
		}
		String wd=TextUtil.autoMultiLine(words, 16, 134);
		LabelStyle ls=game.rs.getLabelStyle(wd);
		chatLabel = new Label(wd, ls);
		if(chatPanel==null){
			chatPanel=new ChatActor(game.rs);
		}
		chatBubble=new Group();
		chatBubble.setPosition(-75, 70);
		
		chatLabel.setBounds(6, 2, 142, height-4);
		
		chatPanel.setPosition(0, height);
		chatPanel.setSize(150, height);
		chatBubble.addActor(chatPanel);
		chatBubble.addActor(chatLabel);
		
		addActor(chatBubble);
		showingChat=true;
	}
	public void setPlayerName(String name){
		this.name=name;
		LabelStyle ls=game.rs.getLabelStyle(name);
		ls.fontColor=nameLabelGreen;
		nameLabel = new Label(name, ls);
		if(titleLabel==null)
			nameLabel.setPosition(-(nameLabel.getWidth()/2), -20);
		else
			nameLabel.setPosition(-(nameLabel.getWidth()/2), -40);
		LabelStyle ls2=game.rs.getLabelStyle(name);
		ls2.fontColor=new Color(0,0,0,1);
		nameLabelShadow = new Label(name, ls2);
		nameLabelShadow.setPosition(-(nameLabelShadow.getWidth()/2), -21);
		
		addActor(nameLabelShadow);
		addActor(nameLabel);
		
	}
	public void setPlayerTitle(String title){
		this.title=title;
		LabelStyle ls=game.rs.getLabelStyle(title);
		ls.fontColor=nameLabelBlue;
		titleLabel = new Label(title, ls);
		titleLabel.setPosition(-(titleLabel.getWidth()/2), -20);
		if(nameLabel!=null){
			nameLabel.setPosition(-(nameLabel.getWidth()/2), -40);
			nameLabelShadow.setPosition(-(nameLabel.getWidth()/2)+1, -41);
		}
		
		LabelStyle ls2=game.rs.getLabelStyle(title);
		ls2.fontColor=new Color(0,0,0,1);
		titleLabelShadow = new Label(title, ls2);
		titleLabelShadow.setPosition(-(titleLabelShadow.getWidth()/2)+1, -21);
		
		addActor(titleLabelShadow);
		addActor(titleLabel);
	}
	public void hidePlayerTitle(){
		if(titleLabel!=null&&titleLabelShadow!=null){
			titleLabel.setVisible(false);
			titleLabelShadow.setVisible(false);
			if(nameLabel!=null&&nameLabelShadow!=null){
				nameLabel.setPosition(-(nameLabel.getWidth()/2), -20);
				nameLabelShadow.setPosition(-(nameLabel.getWidth()/2)+1, -21);
			}
		}
	}
	public void toggleTitle(){
		if(titleLabel!=null&&titleLabelShadow!=null){
			if(titleLabel.isVisible())
				hidePlayerTitle();
			else
				showPlayerTitle();
		}
	}
	public void showPlayerTitle(){
		if(titleLabel!=null&&titleLabelShadow!=null){
			titleLabel.setVisible(true);
			titleLabelShadow.setVisible(true);
			if(nameLabel!=null&&nameLabelShadow!=null){
				nameLabel.setPosition(-(nameLabel.getWidth()/2), -40);
				nameLabelShadow.setPosition(-(nameLabel.getWidth()/2)+1, -41);
			}
		}
	}
	public void setLogicXY(int x,int y){
		this.inMapLogicX=x;
		this.inMapLogicY=y;
	}
	void setCurrentStat(int stat){
		if(stat>=actStr.length||stat<0){
			this.currentStat=STAND;
			Gdx.app.log("[ XYQ ]", "[PlayerActor] -> 当前玩家动画组设定的范围超出了范围");
		}
		this.currentStat=stat;
	}
	public void switchStat(int stat){
		//Gdx.app.log("[ XYQ ]", "[PlayerActor debug] -> 当前玩家状态为"+currentStat);
		roleImgs[currentStat].setVisible(false);
		if(weapon!=null)
			weapon[currentStat].setVisible(false);
		//Gdx.app.log("[ XYQ ]", "[PlayerActor debug] -> 当前设定状态为"+stat);
		currentStat=stat;
		roleImgs[currentStat].setVisible(true);
		if(weapon!=null)
			weapon[currentStat].setVisible(true);
		//Gdx.app.log("[ XYQ ]", "[PlayerActor debug] -> 当前设定后状态为"+currentStat);
		
	}
	public WasActor getCurrentWasActor(){
		return roleImgs[currentStat];
	}
	public void setPlayerFaceTo(int direct){
		for(WasActor was:roleImgs){
			was.setFaceTo(direct);
		}
		if(weapon!=null)
			for(WasActor was:weapon){
				was.setFaceTo(direct);
			}
		//getCurrentWasActor().setFaceTo(direct);
	}
	public int getPlayerFaceTo(){
		return getCurrentWasActor().img.currentDirect;
	}
	public int getWasDirects(){
		return getCurrentWasActor().img.getDirect();
	}
	public void setWeaponWas(String pack,String was,float xOff,float yOff){
		if(weapon!=null){
			for(WasActor wass:weapon)
				wass.remove();
		}
		weapon=new WasActor[2];
		
		for(int i=0;i<actStr.length;i++){
			weapon[i]= new WasActor(game.rs,pack, was+"_"+actStr[i],null);
			weapon[i].setPosition(-40+xOff, -13+yOff);
			weapon[i].setVisible(false);
	        addActor(weapon[i]);
		}
		weapon[currentStat].setVisible(true);
	}

	public void showAddonAnimation(String pack,String was,float xOff,float yOff){
		if(addon!=null){
			addon.remove();
		}
		addon = new WasActor(game.rs, pack, was);
		addon.setPosition(-(addon.getImg().getW()/2)+xOff, -13+yOff);
		addActor(addon);
		showingAddon=true;
	}
	@Override
    public void act(float delta) {
        super.act(delta);
        if(showingChat){
        	showedTime+=delta;
        	if(showedTime>showChatTimeCount){
        		showingChat=false;
        		chatBubble.remove();
        		showedTime=0;
        	}
        }
        if(showingAddon){
        	if(addon.getImg().getCurrentIndex()>=addon.getImg().getCurrDirTextureFrameCount()-1){
        		addon.setVisible(false);
        		showingAddon=false;
        	}
        }
    }
	@Override
    public void draw(Batch batch, float parentAlpha) {
		if(game.is_Debug)
			batch.draw(game.rs.getWhiteLineBlockTexture(), inMapLogicX*20, inMapLogicY*20);
        super.draw(batch, parentAlpha);
    }
}
