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
import xyq.game.data.ShapeData;
import xyq.game.stage.UI.dialog.comp.ChatBubble;
import xyq.game.stage.UI.dialog.comp.SaleBoard;
import xyq.system.assets.PPCData;

/**玩家演员*/
public class PlayerActor  extends Group{
	public final static String[] actStr={"静立","行走"};

	final static Color nameLabelGreen=new Color(0,0.88f,0.47f,1);
	final static Color nameLabelOrange=new Color(1,0.8f,0,1);
	final static Color nameLabelBlue=new Color(0.2f,0.74f,0.9f,1);
	
	XYQGame game;
	ShapeData shapeData;
	ShapeData weaponShapeData;
	
	/**当前的动作,比如ShapeData.STAND*/
	String currentAction;
	int currentFaceTo;
	String name;
	String title;
	
	WasActor shadow;
	WasActor roleActor;
	/**roleActor的换色数据*/
	PPCData pp;
	
	
	WasActor weaponActor;
	WasActor addonActor;

	
	SaleBoard saleBoard;
	
	Label nameLabel;
	Label nameLabelShadow;

	Label titleLabel;
	Label titleLabelShadow;
	
	int inMapLogicX;
	int inMapLogicY;
	
	//Label chatLabel;
	//ChatActor chatPanel;
	//Group chatBubble;
	ChatBubble chatBubble;
	
	boolean showingChat=false;
	float showedTime=0;
	final float showChatTimeCount=4;
	
	boolean showingAddon=false;
	
	public PlayerActor(final XYQGame game,ShapeData shape,PPCData pp){
		this.game=game;
		this.shapeData=shape;
		this.currentAction=ShapeData.STAND;
		this.pp=pp;
		shadow= new WasActor(game.rs,"shape.wdf", "人物阴影");
		shadow.setPosition(-35, -20);
        addActor(shadow);
		
        remakeRoleActor();
        /*
        roleActor= new WasActor(game.rs,shape.pack, shape.getWas(ShapeData.STAND),pp);
        roleActor.setColor(0.9f,0.9f,0.9f,1);
        roleActor.setPosition(shape.offsetX,shape.offsetY);
        roleActor.setVisible(true);
	    addActor(roleActor);
		
        
	    roleActor.addListener(new InputListener(){
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
		*/
	}
	public void setWeaponShape(ShapeData weaponShapeData){
		this.weaponShapeData=weaponShapeData;
		remakeRoleActor();
	}
	public void saleON(String name){
		if(saleBoard!=null)
			saleBoard.remove();
		saleBoard=new SaleBoard(game, name);
		saleBoard.setPosition(0, 100);
		addActor(saleBoard);
		saleBoard.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(button==1)
					game.cs.UI_showSystemMessage("这个摊位还没准备好");
				return false;
			}
		});
	}
	public void saleNameChange(String newName){
		if(saleBoard!=null)
			saleBoard.saleNameChange(newName);
	}
	public void saleOFF(){
		if(saleBoard!=null)
			saleBoard.remove();
		saleBoard=null;
		
	}
	public void remakeRoleActor(){
		if(roleActor!=null)
			roleActor.remove();
		roleActor= new WasActor(game.rs,shapeData.pack, shapeData.getWas(currentAction),pp);
        roleActor.setColor(0.9f,0.9f,0.9f,1);
        roleActor.setPosition(shapeData.offsetX,shapeData.offsetY);
        roleActor.setVisible(true);
        roleActor.setFaceTo(0);
	    addActor(roleActor);
		
        
	    roleActor.addListener(new InputListener(){
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
				}
				game.senceStage.map.justClickARole=true;
				return false;
			}
		});
	    
	    if(weaponShapeData!=null&&weaponShapeData.getWas(currentAction)!=null&&!weaponShapeData.getWas(currentAction).equals("")){
	    	if(weaponActor!=null)
	    		weaponActor.remove();
	    	weaponActor= new WasActor(game.rs,weaponShapeData.pack, weaponShapeData.getWas(currentAction));
	    	weaponActor.setPosition(weaponShapeData.offsetX,weaponShapeData.offsetY);
	    	weaponActor.setVisible(true);
		    addActor(weaponActor);
	    }
	}
	public void say(String words){
		if(chatBubble!=null){
			chatBubble.remove();
			chatBubble=null;
		}
		chatBubble =new ChatBubble();
		chatBubble.makeBubble(words, game);
		addActor(chatBubble);
		showingChat=true;
		/*
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
		chatBubble.setVisible(false);
		addActor(chatBubble);*/
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
	/**设定玩家当前的状态/动作*/
	void setCurrentStat(String action){
		this.currentAction=action;
	}
	/**切换玩家当前的状态/动作*/
	public void switchStat(String action){
		if(currentAction.equals(action))
			return;
		if(shapeData.getWas(action).equals("")||shapeData.getWas(action)==null){
			Gdx.app.log("[ XYQ ]", "[ PlayerActor ] -> 设定玩家动作状态失败了,因为当前动作was数据为空:"+action);
			return;
		}
		setCurrentStat(action);
		remakeRoleActor();
		//设定，如果玩家改变了动作，新的动作是四向的，则重新设定
		if(ShapeData.isFourDirAction(action))
			currentFaceTo=game.ls.ifm.MATH_eightToFourDir(currentFaceTo);
		setPlayerFaceTo(currentFaceTo);
		
		if(weaponShapeData!=null&&weaponActor!=null)
			weaponActor.setFaceTo(currentFaceTo);
		
	}
	public WasActor getCurrentWasActor(){
		return roleActor;
	}
	public void setPlayerFaceTo(int direct){
		currentFaceTo=direct;
		roleActor.setFaceTo(currentFaceTo);
	}
	public int getPlayerFaceTo(){
		return currentFaceTo;
	}
	public int getWasDirects(){
		return getCurrentWasActor().img.getDirect();
	}

	public void showAddonAnimation(String pack,String was,float xOff,float yOff){
		if(addonActor!=null){
			addonActor.remove();
		}
		addonActor = new WasActor(game.rs, pack, was);
		addonActor.setPosition(-(addonActor.getImg().getW()/2)+xOff, -13+yOff);
		addActor(addonActor);
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
        	if(addonActor.getImg().getCurrentIndex()>=addonActor.getImg().getCurrDirTextureFrameCount()-1){
        		addonActor.setVisible(false);
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
	public void drawBubble(Batch batch, float parentAlpha){
		if(showingChat)
			chatBubble.draw(batch, parentAlpha);
	}
}
