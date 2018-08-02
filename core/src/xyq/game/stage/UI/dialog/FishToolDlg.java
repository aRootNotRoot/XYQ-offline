package xyq.game.stage.UI.dialog;



import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.stage.ChatOption;
import xyq.game.stage.LinkLabel;
import xyq.game.stage.LinkLabelClickAction;
import xyq.game.stage.WasIconActor;

/**钓鱼法宝框*/
public class FishToolDlg extends Group{
	public XYQGame game;
	FishingDlg dlg;

	WasIconActor bgPanel;
	Button clzBtn;

	public LinkLabel[] linkLabels;
	final int[][] linksPos={
			{25,80},
			{25, 60},
			{25,40},
			{25,20}
	};
	/**
	 * 创建一个钓鱼钓到的鱼的游戏对话框
	 * */
	public FishToolDlg(final XYQGame game,FishingDlg dlg){
		super();
		this.game=game;
		this.dlg=dlg;
		setSize(172, 134);

		bgPanel=new WasIconActor(game.rs,"wzife.wdf","钓鱼\\法宝小框");
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

		clzBtn.setPosition(152, 114);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
		addActor(clzBtn); 
        
		linkLabels=new LinkLabel[4];
		ChatOption[] opts=new ChatOption[4];
		int birdLeft=dlg.xyqgame.ls.player.playerData().moneys[7];
		int gigLeft=dlg.xyqgame.ls.player.playerData().moneys[8];
		int netLeft=dlg.xyqgame.ls.player.playerData().moneys[9];
		int oilLeft=dlg.xyqgame.ls.player.playerData().moneys[10];
	    opts[0]=new ChatOption("鱼鹰    "+birdLeft,new LinkLabelClickAction() {
			@Override
			public void click() {
				useTool(0);	
			}
	    });
	    opts[1]=new ChatOption("鱼叉    "+gigLeft,new LinkLabelClickAction() {
			@Override
			public void click() {
				useTool(1);	
			}
	    });
	    opts[2]=new ChatOption("渔网    "+netLeft,new LinkLabelClickAction() {
			@Override
			public void click() {
				useTool(2);
			}
	    });
	    opts[3]=new ChatOption("香油    "+oilLeft,new LinkLabelClickAction() {
			@Override
			public void click() {
				useTool(3);	
			}
	    });
		for(int i=0;i<linkLabels.length;i++){
			linkLabels[i]=new LinkLabel(linksPos[i][0], linksPos[i][1], opts[i].labelText, game.rs.getUILabelStyle(), opts[i].clickAction,new Color(0, 0, 0, 1),new Color(1, 0, 0, 1));
			addActor(linkLabels[i]);
		}
	}

	public void useTool(int i) {
		int toolLeft=dlg.xyqgame.ls.player.playerData().moneys[i+7];
		if(toolLeft<=0){
			dlg.xyqgame.cs.UI_showSystemMessage("你没有这个法宝");
			return;
		}
		
		
		
		if(i==0){
			
			if(!dlg.fish.gaming){
				dlg.xyqgame.cs.UI_showSystemMessage("哎呀，鱼鹰逃跑了!");
				return;
			}
			if(dlg.fish.useBird()){
				toolLeft--;
				dlg.fishInfos[dlg.pos].addText("放出了鱼鹰\n", true);
			}
			dlg.xyqgame.ls.player.playerData().moneys[i+7]=toolLeft;
			linkLabels[i].setText("鱼鹰    "+toolLeft);
		}else if(i==1){
			dlg.fishInfos[dlg.pos].addText("使用了鱼叉\n", true);
			dlg.fish.useGig();
			
			toolLeft--;
			dlg.xyqgame.ls.player.playerData().moneys[i+7]=toolLeft;
			linkLabels[i].setText("鱼叉    "+toolLeft);
		}else if(i==2){
			dlg.fishInfos[dlg.pos].addText("使用了渔网\n", true);
			dlg.fish.useNet();
			
			toolLeft--;
			dlg.xyqgame.ls.player.playerData().moneys[i+7]=toolLeft;
			linkLabels[i].setText("渔网    "+toolLeft);
		}else if(i==3){
			if(!dlg.fish.gaming){
				dlg.xyqgame.cs.UI_showSystemMessage("哎呀，香油打翻了");
				return;
			}
			
			if(dlg.fish.useOil()){
				dlg.fishInfos[dlg.pos].addText("使用了香油\n", true);
				toolLeft--;
			}
			dlg.xyqgame.ls.player.playerData().moneys[i+7]=toolLeft;
			linkLabels[i].setText("香油    "+toolLeft);
		}
		
	}

	@Override
	public void act (float delta){
		super.act(delta);
		
	}
	
}
