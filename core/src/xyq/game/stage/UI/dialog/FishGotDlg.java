package xyq.game.stage.UI.dialog;



import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.data.ItemData;
import xyq.game.stage.WasIconActor;
import xyq.game.stage.UI.dialog.comp.ScrollLabelFx;
import xyq.system.ingame.fishing.FishPool;

public class FishGotDlg extends Group{
	public XYQGame game;
	FishingDlg dlg;

	WasIconActor bgPanel;
	Button clzBtn;

	TextButton putinBtn;
	TextButton throwBtn;
	
	WasIconActor fishImg;
	ScrollLabelFx fishinfo;
	Label fishName;
	Label fishData;
	
	FishPool nowFish;
	
	/**
	 * 创建一个钓鱼钓到的鱼的游戏对话框
	 * */
	public FishGotDlg(final XYQGame game,FishingDlg dlg){
		super();
		this.game=game;
		this.dlg=dlg;
		setSize(268, 283);

		bgPanel=new WasIconActor(game.rs,"wzife.wdf","钓鱼\\收获");
		bgPanel.setPosition(0, 0);
		bgPanel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
            	return false;
            }
        });
		addActor(bgPanel);
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(248, 263);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	dontWantFish();
                setVisible(false);
            }
        });
		addActor(clzBtn); 
        
		putinBtn = new TextButton("收取", game.rs.getCommonTextButtonStyle());
		putinBtn.pad(20);
		putinBtn.setPosition(70, 15);
		putinBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	wantFish();
                setVisible(false);
            }
        });
        addActor(putinBtn);
        
        throwBtn = new TextButton("放生", game.rs.getCommonTextButtonStyle());
        throwBtn.pad(20);
        throwBtn.setPosition(140, 15);
        throwBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	dontWantFish();
                setVisible(false);
            }
        });
        addActor(throwBtn);
	}
	public void dontWantFish() {
		setVisible(false);
	}
	public void wantFish() {
		this.dlg.fish.putFishIn(nowFish,1,true);
		setVisible(false);
	}
	@Override
	public void act (float delta){
		super.act(delta);
		
	}
	/**设定当前鱼信息*/
	public void setFish(FishPool aFishNow) {
		ItemData fishdata=(ItemData)game.itemDB.getItem(aFishNow.type,aFishNow.id);
		String fishImgWas=game.db.loadItemIconByTypeID(aFishNow.type,aFishNow.id, false);
		
		if(fishImg!=null)
			fishImg.remove();
		fishImg=new WasIconActor(game.rs, "item.wdf", fishImgWas);
		fishImg.setPosition(13, 127);
		addActor(fishImg);
		
		if(fishName!=null)
			fishName.remove();
		fishName=new Label(fishdata.getName(), game.rs.getDarkGreenLabelStyle(fishdata.getName()));
		fishName.setPosition(147, 232);
		addActor(fishName);
			
		if(fishinfo!=null)
			fishinfo.remove();
		fishinfo=new ScrollLabelFx(game,fishdata.getDescription()+"\n【功效】"+fishdata.getFunctionDescription(),game.rs.getBlackLabelStyle(fishdata.getDescription()+"\n【功效】"+fishdata.getFunctionDescription(),14), 84, 100,14,6);
		fishinfo.setEndLine(6);
		fishinfo.setPosition(147, 130);
		fishinfo.setAlignment(Align.topLeft);
		fishinfo.addListener(new ClickListener() {
			@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(button==1)
            		fishinfo.preLine();
            	else
            		fishinfo.nextLine();
            	
            	return false;
            }
        });
		addActor(fishinfo);
		
		//addActor(game.rs.getDebugAreaActor(84, 100, 147, 130));
		
		if(fishData!=null)
			fishData.remove();
		fishData=new Label("", game.rs.getBlackLabelStyle("体长 25厘米"));
		fishData.setPosition(20, 100);
		addActor(fishData);
		
		this.nowFish=aFishNow;
		
		
	}
	
}
