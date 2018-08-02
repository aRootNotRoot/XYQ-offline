package xyq.game.stage.UI.dialog;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.stage.MyActor;
import xyq.game.stage.WasActor;
import xyq.game.stage.WasIconActor;
import xyq.system.ingame.fishing.FishPool;
import xyq.system.utils.RandomUT;

/**钓鱼-鱼竿突然开始发光了对话框*/
public class FishLightDlg extends Group{
	public XYQGame game;
	FishingDlg dlg;

	WasIconActor bgPanel;
	Button clzBtn;

	WasIconActor fishRodPa;
	Rectangle fishRodRect;
	MyActor rect2;
	
	WasIconActor fishPa;
	Rectangle fishPaRect;
	MyActor rect;
	
	float paX=40;
	float paY=41;
	
	float rodX=40;
	float rodY=41;
	float minRodX=40;
	float maxRodX=390;
	float maxPaX=400;
	
	float speed=50f;//每秒移动的量
	
	
	public boolean debug=false;
	boolean gaming=false;
	
	float point=40f;
	/**
	 * 创建一个钓鱼钓到的鱼的游戏对话框
	 * */
	public FishLightDlg(final XYQGame game,FishingDlg dlg){
		super();
		this.game=game;
		this.dlg=dlg;
		setSize(499, 129);

		bgPanel=new WasIconActor(game.rs,"wzife.wdf","UI\\长长的输入对话框");
		bgPanel.setPosition(0, 0);
		bgPanel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(button==1){
            		setVisible(false);
            	}
            	return false;
            }
        });
		addActor(bgPanel);
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(479, 109);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
		//addActor(clzBtn); 
     
		WasActor bq=new WasActor(game.rs,"wzife.wdf","表情\\#047");
		bq.setPosition(30, 70);
		addActor(bq);
		
		Label label=new Label("加油，不要让鱼群跑掉了", game.rs.getLabelStyle("加油，不要让鱼群跑掉了"));
		label.setPosition(65, 70);
		addActor(label);
		
		fishRodPa=new WasIconActor(game.rs,"wzife.wdf","按钮\\蓝色通用圆角按钮长");
		fishRodPa.setPosition(40, 41);
		addActor(fishRodPa);
		fishRodRect=new Rectangle(rodX, rodY, 74, 20);
		
		fishPa=new WasIconActor(game.rs,"goods.wdf","道具\\星辰");
		fishPa.setPosition(getPaX(), getPaY());
		addActor(fishPa);
		
		fishPaRect=new Rectangle(paX, paY, 35, 20);
		
		rect=game.rs.getDebugAreaActor(74, 20, 40, 41);
		rect.setVisible(false);
		addActor(rect);
		
		rect2=game.rs.getDebugAreaActor(30, 20, 40, 41);
		rect2.setVisible(false);
		addActor(rect2);
		
		Button fishBtn= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\0人帮",false)); 
		fishBtn.setPosition(230, 5);
		fishBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	addOn();
            }
        });
		addActor(fishBtn);
	}
	public float getPaX(){
		return paX-10;
	}
	public float getPaY(){
		return paY-16;
	}
	public float getRectPaX(float x){
		return x+10;
	}
	public float getRectPaY(float y){
		return y+16;
	}
	public void updateDraw(){
		fishRodPa.setPosition(rodX, rodY);
		//fishPa.setPosition(getPaX(), getPaY());
		rect.setPosition(rodX, rodY);
		rect2.setPosition(getRectPaX(fishPa.getX()), getRectPaY(fishPa.getY()));
		
		fishPaRect.setPosition(getRectPaX(fishPa.getX()), getRectPaY(fishPa.getY()));
		fishRodRect.setPosition(rodX, rodY);
	}
	public void newGame(){
		gaming=true;
		paX=40;
		paY=41;
		
		rodX=40;
		rodY=41;
		minRodX=40;
		maxRodX=390;
		maxPaX=400;
		
		point=40f;
		speed=50f;//每秒移动的量
		fishMoveTo(200);
	}
	public void stopGame(){
		gaming=false;
		paX=40;
		paY=41;
		
		rodX=40;
		rodY=41;
		minRodX=40;
		maxRodX=390;
		maxPaX=400;
		
		point=40f;
		speed=50f;//每秒移动的量
		fishPa.clearActions();
		fishPa.setX(paX);
	}
	public void addOn(){
		if(gaming){
			speed+=45f;
		}
	}
	public void fishMoveTo(float x){
		if(!gaming)
			return;
		fishPa.clearActions();
		SequenceAction sequenceAction = Actions.sequence();
		MoveToAction moveTo = Actions.moveTo(x,25, 5f);
		sequenceAction.addAction(moveTo);
		RunnableAction runnable = Actions.run(new Runnable() {
            @Override
            public void run() {
            	int pos=RandomUT.getRandom(40, 400);
            	fishMoveTo(pos);
            }
        });
        sequenceAction.addAction(runnable);
		fishPa.addAction(sequenceAction);
	}
	public void got(){
		if(!gaming)
			return;
		setVisible(false);
		stopGame();
		game.cs.Sound_playSE1("./sound/SE/gotfish.ogg");
		int count=RandomUT.getRandom(2, 7);
		FishPool wantFish;
        int shot=0;
        if(dlg.where.equals("长寿村")){
			shot=RandomUT.probabilityShot(dlg.fish.fishchance_CS);
			if(shot>=dlg.fish.fishpool_CS.size())
				wantFish=dlg.fish.fishpool_CS.get(0);
			else
				wantFish=dlg.fish.fishpool_CS.get(shot);
		}else{
			shot=RandomUT.probabilityShot(dlg.fish.fishchance_AL);
			if(shot>=dlg.fish.fishpool_AL.size())
				wantFish=dlg.fish.fishpool_AL.get(0);
			else
				wantFish=dlg.fish.fishpool_AL.get(shot);
		}
        String name=dlg.xyqgame.itemDB.getItemName(wantFish.type, wantFish.id);
        dlg.fish.harvestFish(wantFish,"和鱼群角力,拉上来"+count+"个",count);
		game.cs.UI_showChatDialog("", "", "经过和鱼群的一番角力,最终拉上来"+count+"个"+name);
	}
	public void lost(){
		if(!gaming)
			return;
		gaming=false;
		setVisible(false);
		stopGame();
		game.cs.UI_showChatDialog("", "", "啊！鱼群跑掉了");
	}
	@Override
	public void act (float delta){
		super.act(delta);
		if(!gaming)
			return;
		fishPa.act(delta);
		if(debug){
			rect.setVisible(true);
			rect2.setVisible(true);
		}else{
			rect.setVisible(false);
			rect2.setVisible(false);
		}
		rodX+=speed*delta;
		speed-=3f;
		if(rodX<minRodX){
			rodX=minRodX;
			speed=0;
		}
		if(rodX>maxRodX){
			rodX=maxRodX;
			speed=0;
		}
		if(fishPaRect.overlaps(fishRodRect)){
			point+=0.2f;
			if(point>100){
				point=100;
				got();
			}
		}else{
			point-=0.3f;
			if(point<0){
				point=0;
				lost();
			}
		}
		updateDraw();
	}
	
}
