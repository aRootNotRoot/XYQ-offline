package xyq.game.stage.UI.dialog;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.stage.WasActor;
import xyq.game.stage.WasCtrlActor;
import xyq.game.stage.WasIconActor;
import xyq.game.stage.UI.dialog.comp.ScrollLabelFx;
import xyq.system.ItemDB;
import xyq.system.assets.PPCData;
import xyq.system.ingame.fishing.FishingGame;
import xyq.system.utils.RandomUT;

public class FishingDlg extends Group{
	public XYQGame xyqgame;
	FishingDlg me;
	public FishingGame fish;
	
	Group backGroundGroup;
	Group ControlGroup;
	
	WasIconActor panel;
	WasIconActor titlePanel;
	WasIconActor bgPanel;
	Button clzBtn;

	Button fishBtn;
	Button ldBtn;
	WasIconActor ldPanel;
	Label ldLabel;
	Button fsBtn_ZG;
	Button jdBtn_KS;
	Button fsBtn_XG;
	Button jdBtn_YH;

	Button colorBtn;
	Button toolBtn;
	Button helpBtn;
	Button changeBtn;
	
	public ScrollLabelFx[] fishInfos;
	public WasIconActor[] headImgs;
	public Label[] historyGotText;
	public Label[] historyGot;
	public Label[] nowGotText;
	public Label[] nowGot;
	
	public String where;
	//////////////
	public WasCtrlActor myrod;
	int myRodPos=0;
	final String[] rodsWas={
			"钓鱼\\鱼竿\\左1",
			"钓鱼\\鱼竿\\左2",
			"钓鱼\\鱼竿\\中方位",
			"钓鱼\\鱼竿\\右1",
			"钓鱼\\鱼竿\\右2"
	};
	final int[][] rodsPos={
			{15,150},
			{133,150},
			{221,150},
			{300,150},
			{364,150}
	};
	final int[][] infoPos={
			{13,17},
			{140,17},
			{267,17},
			{393,17},
			{523,17}
	};
	final int[][] headImgPos={
			{13,90},
			{140,90},
			{267,90},
			{393,90},
			{523,90}
	};
	final String[] hisCountStr={
			"☆",
			"★",
			"★☆",
			"★★",
			"★★☆",
			"★★★",
			"★★★☆",
			"★★★★",
			"★★★★☆",
			"★★★★★"
	};
	final int info_width=90;
	final int info_height=55;
	//////////////

	/**钓鱼的位置*/
	public int pos;
	
	public FishGotDlg gotDlg;
	public FishToolDlg toolDlg;
	public FishLightDlg lightDlg;
	public WasIconActor birdIcon;
	
	/**当前鱼竿颜色混色序号*/
	private int currColor;
	
	/**
	 * 创建一个钓鱼游戏对话框。
	 * @param where 是长寿村还是傲来国
	 * @param pos 玩家在的位置[0-4]
	 * */
	public FishingDlg(final XYQGame game,String where,final int pos){
		super();
		this.xyqgame=game;
		this.pos=pos;
		me=this;
		setSize(640, 448);

		backGroundGroup=new Group();

		ControlGroup=new Group();
		ControlGroup.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1){
            		//stopGame();
            		//setVisible(false);
            	}else if(button==0){
            		Group group=getParent();
            		remove();
            		group.addActor(me);
            	}
            	return false;
            }
        });
		backGroundGroup.setSize(640, 448);
		backGroundGroup.setPosition(0, 0);
		ControlGroup.setSize(640, 448);
		ControlGroup.setPosition(0, 0);
		
		addActor(backGroundGroup);
		addActor(ControlGroup);
		
		if(pos>-1&&pos<5)
			this.myRodPos=pos;
		else{
			this.myRodPos=0;
		}
		panel=new WasIconActor(game.rs,"wzife.wdf","钓鱼\\对话框\\傲来");
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1){
            		stopGame();
            		setVisible(false);
            	}else if(button==2){
            		Group group=getParent();
            		remove();
            		group.addActor(me);
            	}
            	return false;
            }
        });
		backGroundGroup.addActor(panel);
		
		if(where.equals("长寿村")){
			titlePanel=new WasIconActor(game.rs,"wzife.wdf","钓鱼\\长寿渔场标题");
			titlePanel.setPosition(0, 424);
			titlePanel.addListener(new ClickListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	if(button==2){
	            		Group group=getParent();
	            		remove();
	            		group.addActor(me);
	            	}
	            	return false;
	            }
	        });
			backGroundGroup.addActor(titlePanel);
			
			int bgCode=RandomUT.getRandom(1, 2);
			bgPanel=new WasIconActor(game.rs,"wzife.wdf","钓鱼\\背景\\长寿"+bgCode);
			bgPanel.setPosition(13, 150);
			backGroundGroup.addActor(bgPanel);
			
			if(bgCode==1){//游鱼
				WasActor fishAC=new WasActor(game.rs,"wzife.wdf","钓鱼\\长寿游鱼");
				fishAC.setPosition(13, 130);
				backGroundGroup.addActor(fishAC);
			}else{//青蛙
				WasActor fishAC=new WasActor(game.rs,"wzife.wdf","钓鱼\\装饰\\青蛙");
				fishAC.setPosition(585, 277);
				backGroundGroup.addActor(fishAC);
			}
		}else{
			int bgCode=RandomUT.getRandom(1, 2);
			bgPanel=new WasIconActor(game.rs,"wzife.wdf","钓鱼\\背景\\傲来"+bgCode);
			bgPanel.setPosition(13, 150);
			backGroundGroup.addActor(bgPanel);
			if(bgCode==1){//海鸟
				WasActor fishAC=new WasActor(game.rs,"wzife.wdf","钓鱼\\海鸟");
				fishAC.setPosition(160, 330);
				backGroundGroup.addActor(fishAC);
			}else{//芦苇
				WasActor fishAC=new WasActor(game.rs,"wzife.wdf","钓鱼\\装饰\\芦苇");
				fishAC.setPosition(470, 150);
				backGroundGroup.addActor(fishAC);
			}
		}
		this.where=where;
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(618, 427);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	stopGame();
                setVisible(false);
            }
        });
		ControlGroup.addActor(clzBtn); 
        
        
        fishBtn= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\拉杆按钮",false)); 
        fishBtn.setPosition(585, 384);
        fishBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	fish.throwRod();
            }
        });
        ControlGroup.addActor(fishBtn); 
        
        ldBtn= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\力度按钮",false)); 
        ldBtn.setPosition(552, 390);
        ldBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	fish.toggolePower();
            	if(fish.power==FishingGame.xiaoli)
            		ldLabel.setText("小");
            	else if(fish.power==FishingGame.zhongli)
            		ldLabel.setText("中");
            	else
            		ldLabel.setText("大");
            }
        });
        ControlGroup.addActor(ldBtn); 
        
        ldPanel=new WasIconActor(game.rs,"wzife.wdf","UI\\打勾框");
        ldPanel.setPosition(522, 392);
        ControlGroup.addActor(ldPanel);
		
		ldLabel=new Label("小", game.rs.getUILabelStyle());
		ldLabel.setPosition(527, 397);
		ControlGroup.addActor(ldLabel);
		
		fsBtn_ZG= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\拉杆方式\\直杆",false)); 
        fsBtn_ZG.setPosition(562, 360);
        fsBtn_ZG.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	fish.toggoleAngle();
            	if(fish.angle==FishingGame.zhigan){
            		fsBtn_ZG.setVisible(true);
            		fsBtn_XG.setVisible(false);
            	}else{
            		fsBtn_ZG.setVisible(false);
            		fsBtn_XG.setVisible(true);
            	}
            }
        });
        ControlGroup.addActor(fsBtn_ZG); 
        
        fsBtn_XG= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\拉杆方式\\斜竿",false)); 
        fsBtn_XG.setPosition(562, 360);
        fsBtn_XG.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	fish.toggoleAngle();
            	if(fish.angle==FishingGame.zhigan){
            		fsBtn_ZG.setVisible(true);
            		fsBtn_XG.setVisible(false);
            	}else{
            		fsBtn_ZG.setVisible(false);
            		fsBtn_XG.setVisible(true);
            	}
            }
        });
        ControlGroup.addActor(fsBtn_XG); 
        fsBtn_XG.setVisible(false);
        
        jdBtn_KS= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\快速拉杆图标",false)); 
        jdBtn_KS.setPosition(593, 352);
        jdBtn_KS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	fish.toggoleWay();
            	if(fish.way==FishingGame.kuaisu){
            		jdBtn_KS.setVisible(true);
            		jdBtn_YH.setVisible(false);
            	}else{
            		jdBtn_KS.setVisible(false);
            		jdBtn_YH.setVisible(true);
            	}
            }
        });
        ControlGroup.addActor(jdBtn_KS); 
        
        jdBtn_YH= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\迂回拉杆图标",false)); 
        jdBtn_YH.setPosition(593, 352);
        jdBtn_YH.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	fish.toggoleWay();
            	if(fish.way==FishingGame.kuaisu){
            		jdBtn_KS.setVisible(true);
            		jdBtn_YH.setVisible(false);
            	}else{
            		jdBtn_KS.setVisible(false);
            		jdBtn_YH.setVisible(true);
            	}
            }
        });
        ControlGroup.addActor(jdBtn_YH); 
        jdBtn_YH.setVisible(false);
		
        toolBtn= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\法宝按钮",false)); 
        toolBtn.setPosition(15, 384);
        toolBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toolDlg.setVisible(true);
            }
        });
        ControlGroup.addActor(toolBtn);
        
        //game.rs.debugAllWdfWasIndex("wzife.wdf");
        
        colorBtn= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\换色按钮",false)); 
        colorBtn.setPosition(53, 390);
        colorBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	changeColor();
            }
        });
        ControlGroup.addActor(colorBtn);
       
        helpBtn= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\帮助",false)); 
        helpBtn.setPosition(46, 360);
        helpBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	int r=RandomUT.getRandom(0, 6);
            	if(r==0)
            		xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\老渔翁","年轻人，让我给你点建议吧。箭头按钮可以从背包里使用鱼竿，色块按钮可以改变鱼竿颜色，法宝按钮可以使用自己的钓鱼工具。右上方是鱼竿操作按钮。仔细观察水面的情况，根据鱼的不同，采用不同的策略，这样就能钓上鱼来，熟能生巧！");
            	else if(r==1){
            		xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\老渔翁","老夫钓鱼钓太久了，遇到那些高价买鱼的财主家丁都不想理了。就算宫廷的来了也不想理。");
            	}else if(r==2){
            		xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\老渔翁","有一天我钓鱼的时候钓起来一个破箱子，打开一看里面有好大一颗红宝石，可把我老伴高兴坏了");
            	}else if(r==4){
            		xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\老渔翁","有时候会遇到很难缠的大鱼，不要放弃，和它周旋，消耗它的体力先。");
            	}else if(r==3){
            		xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\老渔翁","有一天我钓鱼的时候钓起来一个美人鱼，她给了我一个吻，可把我老伴给气坏了");
            	}else if(r==5){
            		xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\老渔翁","油炸泥鳅很好吃，不过大夫说少吃一点对身体好。如果你想钓泥鳅，就在咬勾了以后，直直的快速一拉，十有八九能起来，记得别太用力，拉破了泥鳅皮它就跑啦。");
            	}else if(r==6){
            		xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\老渔翁","香油很容易洒出来，还是先把鱼竿架好再拿香油吧。");
            	}
            }
        });
        ControlGroup.addActor(helpBtn);
        
        changeBtn= new Button(game.rs.getButtonStyle("wzife.wdf","钓鱼\\力度选择",false)); 
        changeBtn.setPosition(15, 353);
        changeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(fish.gaming)
            		return;
            	if(game.cs.ACT_delItem(ItemDB.ITEM_BASIC, 95, 1)){
                	newGame();
            	}else if(game.cs.ACT_delItem(ItemDB.ITEM_BASIC, 96, 1)){
                	newGame();
            	}else{
            		game.cs.UI_showSystemMessage("你的背包里没有鱼竿");
            	}
            }
        });
        ControlGroup.addActor(changeBtn);
        
        fishInfos=new ScrollLabelFx[5];
        fishInfos[pos]=new ScrollLabelFx(xyqgame,"",game.rs.getBlackLabelStyle("",12), info_width, info_height,12,4);
        fishInfos[pos].setEndLine(9);
        fishInfos[pos].setPosition(infoPos[pos][0], infoPos[pos][1]);
        fishInfos[pos].setAlignment(Align.topLeft);
        fishInfos[pos].addListener(new ClickListener() {
			@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(button==1)
            		fishInfos[pos].preLine();
            	else
            		fishInfos[pos].nextLine();
            	return false;
            }
        });
       
        ControlGroup.addActor(fishInfos[pos]);
        

        headImgs=new WasIconActor[5];
        headImgs[pos]=new WasIconActor(game.rs,"wzife.wdf","方框小头像\\"+game.ls.player.playerData().Role);
        headImgs[pos].setPosition(headImgPos[pos][0], headImgPos[pos][1]);
        ControlGroup.addActor(headImgs[pos]);
        
        historyGotText=new Label[5];
        historyGotText[pos]=new Label("历史收获", game.rs.getBlackLabelStyle("历史收获", 12));
        historyGotText[pos].setPosition(headImgPos[pos][0]+45, headImgPos[pos][1]+35);
        ControlGroup.addActor(historyGotText[pos]);
        
        historyGot=new Label[5];
        historyGot[pos]=new Label(getHisStar(), game.rs.getBlackLabelStyle("★☆", 12));
        historyGot[pos].setPosition(headImgPos[pos][0]+45, headImgPos[pos][1]+21);
        ControlGroup.addActor(historyGot[pos]);
        
        nowGotText=new Label[5];
        nowGotText[pos]=new Label("当前收获", game.rs.getBlackLabelStyle("当前收获", 12));
        nowGotText[pos].setPosition(headImgPos[pos][0]+45, headImgPos[pos][1]+7);
        ControlGroup.addActor(nowGotText[pos]);
        
        nowGot=new Label[5];
        nowGot[pos]=new Label("0", game.rs.getBlackLabelStyle("1345678902", 12));
        nowGot[pos].setPosition(headImgPos[pos][0]+55, headImgPos[pos][1]-9);
        ControlGroup.addActor(nowGot[pos]);
        /*
        MyActor debugPanel=new MyActor(game.rs.getRect(info_width+2, info_height+2));
		debugPanel.setPosition(infoPos[pos][0]-1, infoPos[pos][1]-1);
		ControlGroup.addActor(debugPanel);
		*/
        
        gotDlg=new FishGotDlg(game,this);
        gotDlg.setPosition(186, 83);
        gotDlg.setVisible(false);
        //gotDlg.setFish(new FishPool(XYQDataBase.ITEM_EDIBLE, 65,1,1,1,15,null));
        ControlGroup.addActor(gotDlg);
        
        toolDlg=new FishToolDlg(game,this);
        toolDlg.setPosition(85, 285);
        toolDlg.setVisible(false);
        ControlGroup.addActor(toolDlg);
        
        lightDlg=new FishLightDlg(game,this);
        lightDlg.setPosition(80, 150);
        lightDlg.setVisible(false);
        lightDlg.stopGame();
        ControlGroup.addActor(lightDlg);
        
        birdIcon=new WasIconActor(game.rs, "wzife.wdf", "钓鱼\\鱼鹰图标");
        birdIcon.setPosition(20, 153);
        birdIcon.setVisible(false);
        ControlGroup.addActor(birdIcon);
        
		fish=new FishingGame(this,pos);
	}
	public void changeColor() {
		if(fish.gaming==false)
			return;
		if(!fish.isNoFishing())
			return;
		//if(xyqgame.is_Debug)
		currColor++;
		if(currColor>16)
			currColor=1;
		if(xyqgame.is_Debug){
			Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]->切换鱼竿颜色:"+currColor);
		}
		if(currColor==16){
			int[] colorations = new int[]{0};
			PPCData pp = new PPCData(xyqgame.rs.reader.readByName("shape.wd1", "单色换色"+1),colorations);
			newRod(pp);
			if(xyqgame.is_Debug){
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]->切换鱼竿颜色: 原始颜色");
			}
		}else{
			int[] colorations = new int[]{1};
			PPCData pp = new PPCData(xyqgame.rs.reader.readByName("shape.wd1", "单色换色"+currColor),colorations);
			newRod(pp);
			if(xyqgame.is_Debug){
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]->切换鱼竿颜色:"+currColor);
			}
		}
		
	}
	@Override
	public void act (float delta){
		super.act(delta);
		if(fish!=null)
			fish.updateGame();
	}
	public void switchPlace(String where){
		if(titlePanel!=null)
			titlePanel.remove();
		if(where.equals("长寿村")){
			titlePanel=new WasIconActor(xyqgame.rs,"wzife.wdf","钓鱼\\长寿渔场标题");
			titlePanel.setPosition(0, 424);
			ControlGroup.addActor(titlePanel);
		}else{
			
		}
		
	}
	public void stopGame() {
		fish.stopGame();
		xyqgame.cs.ACT_deForzenPlayer();
		if(myrod!=null)
			myrod.remove();
	}

	public void newGame(){
		newRod();
		newInfos();
		fish=new FishingGame(this,pos);
		fish.newGame();
		
		xyqgame.cs.ACT_forzenPlayer();
	}
	
	public void newRod(){
		if(myrod!=null)
			myrod.remove();

		myrod=new WasCtrlActor(xyqgame.rs,"wzife.wdf",rodsWas[myRodPos]);
		myrod.setPosition(rodsPos[myRodPos][0], rodsPos[myRodPos][1]);

		backGroundGroup.addActor(myrod);
		myrod.setCurrentIndex(0);
		myrod.setIndexArea(0,0);
	}
	public void newRod(PPCData pp){
		if(myrod!=null)
			myrod.remove();
		
		myrod=new WasCtrlActor(xyqgame.rs,"wzife.wdf",rodsWas[myRodPos],pp);
		myrod.setPosition(rodsPos[myRodPos][0], rodsPos[myRodPos][1]);

		backGroundGroup.addActor(myrod);
		myrod.setCurrentIndex(0);
		myrod.setIndexArea(0,0);
	}
	public void newInfos(){
		for(ScrollLabelFx iff : fishInfos){
			if(iff!=null)
				iff.remove();
		}
		fishInfos=new ScrollLabelFx[5];
        fishInfos[pos]=new ScrollLabelFx(xyqgame,"",xyqgame.rs.getBlackLabelStyle("",12), info_width, info_height,12,4);
        fishInfos[pos].setEndLine(9);
        fishInfos[pos].setPosition(infoPos[pos][0], infoPos[pos][1]);
        fishInfos[pos].setAlignment(Align.topLeft);
        fishInfos[pos].addListener(new ClickListener() {
			@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(button==1)
            		fishInfos[pos].preLine();
            	else
            		fishInfos[pos].nextLine();
            	return false;
            }
        });
        ControlGroup.addActor(fishInfos[pos]);
	}
	public String getHisStar(){
		int id=0;
		int hisGotCount=xyqgame.ls.player.playerData().moneys[5];
		if(hisGotCount<50){
			id=0;
		}else if(hisGotCount>=50&&hisGotCount<100){
			id=1;
		}else if(hisGotCount>=100&&hisGotCount<150){
			id=2;
		}else if(hisGotCount>=150&&hisGotCount<200){
			id=3;
		}else if(hisGotCount>=200&&hisGotCount<250){
			id=4;
		}else if(hisGotCount>=250&&hisGotCount<300){
			id=5;
		}else if(hisGotCount>=300&&hisGotCount<350){
			id=6;
		}else if(hisGotCount>=350&&hisGotCount<450){
			id=7;
		}else if(hisGotCount>=450&&hisGotCount<500){
			id=8;
		}else{
			id=9;
		}
		
		return hisCountStr[id];
	}
}
