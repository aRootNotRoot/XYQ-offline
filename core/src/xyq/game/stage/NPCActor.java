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

import xyq.game.data.NPC;
import xyq.game.data.ShapeData;
import xyq.game.stage.UI.dialog.comp.ChatBubble;
import xyq.game.stage.UI.dialog.comp.SaleBoard;
import xyq.system.assets.PPCData;

public class NPCActor  extends Group{
	final static Color nameLabelRed=new Color(1,0,0,1);
	final static Color nameLabelOrange=new Color(1,0.8f,0,1);
	final static Color nameLabelBlue=new Color(0.2f,0.74f,0.9f,1);
	final static Color nameLabelGreen=new Color(0,0.88f,0.47f,1);

	NPC me;
	PPCData pp;
	WasActor roleActor;
	WasActor shadow;

	SaleBoard saleBoard;
	
	Label nameLabel;
	Label nameLabelShadow;

	Label titleLabel;
	Label titleLabelShadow;

	
	
	ChatBubble chatBubble;
	
	boolean showingChat=false;
	float showedTime=0;
	final float showChatTimeCount=4;
	public boolean useGreenName;
	
	public NPCActor(final NPC me){
		this.me=me;
		this.pp=null;
		shadow= new WasActor(me.game.rs,"shape.wdf", "人物阴影");
		shadow.setPosition(-35, -20);
        addActor(shadow);
		
        remakeRoleActor();
		
		
	}
	public NPCActor(final NPC me,PPCData pp){
		this.me=me;
		this.pp=pp;
		shadow= new WasActor(me.game.rs,"shape.wdf", "人物阴影");
		shadow.setPosition(-35, -20);
        addActor(shadow);
		
        remakeRoleActor();
		
		
	}
	public void saleON(String name){
		if(saleBoard!=null)
			saleBoard.remove();
		saleBoard=new SaleBoard(me.game, name);
		saleBoard.setPosition(0, 100);
		addActor(saleBoard);
		saleBoard.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(button==1)
					me.game.cs.UI_callShop(me.data.shopData);
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
		ShapeData shape=me.data.shape;
		if(roleActor!=null)
			roleActor.remove();
		roleActor= new WasActor(me.game.rs,shape.pack, shape.getWas(getCurrentAction()),pp);
		roleActor.setColor(0.9f,0.9f,0.9f,1);
		roleActor.setPosition(shape.offsetX, shape.offsetY);
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
				if(me.game.is_Debug){
					Gdx.app.log("[ XYQ ]", "[地图] -> 调试点击了一个NPC角色:"+me.data.name);
				}
				if(me.NCA!=null){
					if(me.game.ls.ifm.NPC_isNearByPlr(me)){
						if(me.game.ls.isGiving)
							me.NCA.give();
						else	if(me.game.ls.isTrading)
							me.NCA.trade();
						else
							me.NCA.click();
					}
					else{
						if(me.game.is_Debug){
							Gdx.app.log("[ XYQ ]", "[地图] -> NPC角色:"+me.data.name+"离玩家太远了");
						}
					}
				}
				me.game.senceStage.map.justClickARole=true;
				return false;
			}
		});
	}
	public void say(String words){
		if(chatBubble!=null){
			chatBubble.remove();
			chatBubble=null;
		}
		chatBubble =new ChatBubble();
		chatBubble.makeBubble(words, me.game);
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
	public void updateNPCName(){
		String name=me.data.name;
		LabelStyle ls=me.game.rs.getLabelStyle(name);
		if(useGreenName)
			ls.fontColor=nameLabelGreen;
		else
		ls.fontColor=nameLabelOrange;
		nameLabel = new Label(name, ls);
		if(titleLabel==null)
			nameLabel.setPosition(-(nameLabel.getWidth()/2), -20);
		else
			nameLabel.setPosition(-(nameLabel.getWidth()/2), -40);
		LabelStyle ls2=me.game.rs.getLabelStyle(name);
		ls2.fontColor=new Color(0,0,0,1);
		nameLabelShadow = new Label(name, ls2);
		nameLabelShadow.setPosition(-(nameLabelShadow.getWidth()/2), -21);
		
		addActor(nameLabelShadow);
		addActor(nameLabel);
		
	}
	public void updateNPCTitle(){
		String title=me.data.title;
		if(title==null||title.equals("")){
			hideNPCTitle();
			return;
		}
		LabelStyle ls=me.game.rs.getLabelStyle(title);
		ls.fontColor=nameLabelBlue;
		titleLabel = new Label(title, ls);
		titleLabel.setPosition(-(titleLabel.getWidth()/2), -20);
		if(nameLabel!=null){
			nameLabel.setPosition(-(nameLabel.getWidth()/2), -40);
			nameLabelShadow.setPosition(-(nameLabel.getWidth()/2)+1, -41);
		}
		
		LabelStyle ls2=me.game.rs.getLabelStyle(title);
		ls2.fontColor=new Color(0,0,0,1);
		titleLabelShadow = new Label(title, ls2);
		titleLabelShadow.setPosition(-(titleLabelShadow.getWidth()/2)+1, -21);
		
		addActor(titleLabelShadow);
		addActor(titleLabel);
	}

	public void hideNPCTitle(){
		if(titleLabel!=null&&titleLabelShadow!=null){
			titleLabel.setVisible(false);
			titleLabelShadow.setVisible(false);
			if(nameLabel!=null&&nameLabelShadow!=null){
				nameLabel.setPosition(-(nameLabel.getWidth()/2), -20);
				nameLabelShadow.setPosition(-(nameLabel.getWidth()/2)+1, -21);
			}
		}
	}
	public void showNPCTitle(){
		if(titleLabel!=null&&titleLabelShadow!=null){
			titleLabel.setVisible(true);
			titleLabelShadow.setVisible(true);
			if(nameLabel!=null&&nameLabelShadow!=null){
				nameLabel.setPosition(-(nameLabel.getWidth()/2), -40);
				nameLabelShadow.setPosition(-(nameLabel.getWidth()/2)+1, -41);
			}
		}
	}

	void setCurrentStat(String stat){
		me.data.currentAction = stat;
	}
	public String getCurrentAction() {
		return me.data.currentAction;
	}
	public void switchAction(){
		String action=me.data.currentAction;
		ShapeData shape=me.data.shape;
		if(shape.getWas(action).equals("")||shape.getWas(action)==null){
			Gdx.app.log("[ XYQ ]", "[ NPCActor ] -> 设定NPC动作状态失败了,因为当前动作was数据为空:"+action);
			return;
		}
		setCurrentStat(action);
		remakeRoleActor();
		//设定，如果玩家改变了动作，新的动作是四向的，则重新设定
		int currentFaceTo=getNPCFaceTo();
		if(ShapeData.isFourDirAction(action))
			currentFaceTo=me.game.ls.ifm.MATH_eightToFourDir(currentFaceTo);
		setActorFaceTo(currentFaceTo);
	}
	
	public WasActor getCurrentWasActor(){
		return roleActor;
	}
	public void setActorFaceTo(int direct){
		roleActor.setFaceTo(direct);
	}
	public int getNPCFaceTo(){
		return me.data.currentFaceTo;
	}
	public int getWasDirects(){
		return getCurrentWasActor().img.getDirect();
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
    }
	@Override
    public void draw(Batch batch, float parentAlpha) {
		if(me.game.is_Debug)
			batch.draw(me.game.rs.getWhiteLineBlockTexture(), me.data.logicX*20, me.data.logicY*20);
        super.draw(batch, parentAlpha);
    }
	
	public void drawBubble(Batch batch, float parentAlpha){
		if(showingChat)
			chatBubble.draw(batch, parentAlpha);
	}
	
}
