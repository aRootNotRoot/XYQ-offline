package xyq.game.stage.UI.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;
import xyq.game.stage.WasActor;
import xyq.game.stage.WasIconActor;
import xyq.game.stage.UI.dialog.comp.ItemDetailPanel;
import xyq.game.stage.UI.dialog.comp.ItemUnit;

/**玩家包裹对话框*/
public class PlayerBagDlg extends Group{
	public final int MAX_ITEMS_NUM=46;
	
	final int ITEMS_BAG_BEGIN_INDEX=6;
	final int ITEMS_BAG_END_INDEX=25;
	
	final int ITEMS_BAG2_BEGIN_INDEX=26;
	final int ITEMS_BAG2_END_INDEX=45;
	
	final int[][] ITEMS_UNIT_POS={
			//装备栏位置
			{197,372},{250,372},
			{197,318},{250,318},
			{197,264},{250,264},
			//道具栏位置
			{27,201},{78,201},{129,201},{180,201},{231,201},
			{27,150},{78,150},{129,150},{180,150},{231,150},
			{27,99},{78,99},{129,99},{180,99},{231,99},
			{27,48},{78,48},{129,48},{180,48},{231,48},
			//行囊栏位置
			{27,201},{78,201},{129,201},{180,201},{231,201},
			{27,150},{78,150},{129,150},{180,150},{231,150},
			{27,99},{78,99},{129,99},{180,99},{231,99},
			{27,48},{78,48},{129,48},{180,48},{231,48},
	};
	
	XYQGame game;
	PlayerBagDlg me;
	
	WasActor panel;
	WasActor panel2;

	//DlgEquipBox equipBox;
	//DlgItemBox itemBox;
	ItemUnit[] itemUnits;
	
	WasIconActor headImg;
	
	Button clzBtn;
	TextButton moneyBtn;
	Label moenyLabel;
	Label stroredMoneyLabel;
	boolean coloredMoney;
	Label delItemLabel;
	
	/**包裹按钮*/
	TextButton bagBtn;
	/**行囊按钮*/
	TextButton bag2Btn;
	/**道具详情框*/
	ItemDetailPanel detailPanel;
	
	public PlayerBagDlg(final XYQGame game){
		super();
		this.game=game;
		me=this;
		setSize(472, 167);
		
		panel=new WasActor(game.rs,"wzife.wdf","UI\\道具行囊");
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	else if(button==0){
            		onTop();
            	}
            	return false;
            }
        });
		addActor(panel);
		
		panel2=new WasActor(game.rs,"wzife.wdf","UI\\装备贴图");
		panel2.setPosition(-1, 254);
		addActor(panel2);
		
		/*
		itemBox = new DlgItemBox(game,5,4,false);
		itemBox.setPosition(27, 48);
		itemBox.setItemIndexArea(6,26);
		//itemBox.setItems(game.db.loadRoleItemStackData(game.ls.player.id));
		itemBox.setItems(game.ls.player.getItems());
		addActor(itemBox);
		
		equipBox = new DlgEquipBox(game,false);
		equipBox.setPosition(199, 269);
		equipBox.setItemIndexArea(0,5);
		//itemBox.setItems(game.db.loadRoleItemStackData(game.ls.player.id));
		equipBox.setItems(game.ls.player.getItems());
		addActor(equipBox);
		*/
		
		itemUnits=new ItemUnit[MAX_ITEMS_NUM];
		for(int i=0;i<MAX_ITEMS_NUM;i++){
			itemUnits[i]=new ItemUnit(game,this);
			if(i>=0&&i<=5){
				/**如果是装备，则额外有背景图*/
				itemUnits[i].hasBG(true);
			}
			
			itemUnits[i].setPosition(ITEMS_UNIT_POS[i][0], ITEMS_UNIT_POS[i][1]);
			addActor(itemUnits[i]);
			
			itemUnits[i].setItem(i);
			if(i>=ITEMS_BAG2_BEGIN_INDEX)
				itemUnits[i].setVisible(false);
		}
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(290, panel.getImg().getH()-20);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
		
        headImg=new WasIconActor(game.rs,"wzife.wdf","道具栏头像\\"+game.ls.player.playerData().Role);
        headImg.setPosition(20+(130-headImg.getImg().getW())/2, 307);
        headImg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	onTop();
            }
		});
		addActor(headImg);
        
		moneyBtn= new TextButton("现金", game.rs.getCommonTextButtonStyle());
		moneyBtn.pad(1);
		moneyBtn.setPosition(3, 280);
		moneyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	toggleColor();
            }
		});
		addActor(moneyBtn);
		
		bagBtn= new TextButton("包裹", game.rs.getCommonTextButtonStyle());
		bagBtn.pad(1);
		bagBtn.setDisabled(true);
		bagBtn.setPosition(20, 10);
		bagBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	for(int i=ITEMS_BAG_BEGIN_INDEX;i<=ITEMS_BAG_END_INDEX;i++){
        			itemUnits[i].setVisible(true);
            	}
            	for(int i=ITEMS_BAG2_BEGIN_INDEX;i<=ITEMS_BAG2_END_INDEX;i++){
        			itemUnits[i].setVisible(false);
            	}
            	bagBtn.setDisabled(true);
            	bag2Btn.setDisabled(false);
            }
		});
		addActor(bagBtn);
		
		bag2Btn= new TextButton("行囊", game.rs.getCommonTextButtonStyle());
		bag2Btn.pad(1);
		bag2Btn.setPosition(90, 10);
		bag2Btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	for(int i=ITEMS_BAG_BEGIN_INDEX;i<=ITEMS_BAG_END_INDEX;i++){
        			itemUnits[i].setVisible(false);
            	}
            	for(int i=ITEMS_BAG2_BEGIN_INDEX;i<=ITEMS_BAG2_END_INDEX;i++){
        			itemUnits[i].setVisible(true);
            	}
            	bagBtn.setDisabled(false);
            	bag2Btn.setDisabled(true);
            }
		});
		addActor(bag2Btn);
		
		
		moenyLabel= new Label(game.ls.player.playerData().moneys[0]+"", game.rs.getBlackUILabelStyle());
		moenyLabel.setBounds(65, 280, 80, 20);
		addActor(moenyLabel);
		
		stroredMoneyLabel= new Label(game.ls.player.playerData().moneys[1]+"", game.rs.getBlackUILabelStyle());
		stroredMoneyLabel.setBounds(65, 258, 80, 20);
		addActor(stroredMoneyLabel);
		
		delItemLabel= new Label("[X]", game.rs.getUILabelStyle());
		delItemLabel.setBounds(260, 10, 80, 20);
		delItemLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	delItem();
            }
		});
		//addActor(delItemLabel);
		
		TextButton delBtn= new TextButton("扔掉", game.rs.getCommonTextButtonStyle());
		delBtn.pad(1);
		delBtn.setPosition(240, 10);
		delBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	delItem();
            }
		});
		addActor(delBtn);
		
		/*
		ItemUnit unit=new ItemUnit(game);
		unit.setPosition(0, 0);
		addActor(unit);
		*/
		detailPanel=new ItemDetailPanel(game);
		detailPanel.setVisible(false);
		//detailPanel.updateItem(itemUnits[6].item());
		addActor(detailPanel);
		
		
		
		addListener(new InputListener(){
			public boolean mouseMoved (InputEvent event, float x, float y) {
				detailPanel.setPosition(x+30, y+30);
				return true;
			}
			
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				detailPanel.setVisible(false);
				for(int i=0;i<MAX_ITEMS_NUM;i++){
					if(itemUnits[i].isMouseON()){
						detailPanel.setVisible(true);
						if(itemUnits[i].item()!=null)
							detailPanel.updateItem(itemUnits[i].item());
						else
							detailPanel.setVisible(false);
						break;
					}
				}
				
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				detailPanel.setVisible(false);
			}
		});
		
	}
	public void unselectAll(){
		for(int i=0;i<MAX_ITEMS_NUM;i++){
			if(itemUnits[i]!=null){
				itemUnits[i].unselect();
			}
		}
	}
	public void selectItem(int index){
		unselectAll();
		itemUnits[index].select();
	}
	/**是否选中了除了selectID以外的物品*/
	public boolean isSelectOtherItem(int selectID){
		for(int i=0;i<MAX_ITEMS_NUM;i++){
			if(selectID==i)
				continue;
			if(itemUnits[i]!=null){
				if(itemUnits[i].isSelect())
					return true;
			}
		}
		return false;
	}
	public int getSelectedItemIndex(){
		for(int i=0;i<MAX_ITEMS_NUM;i++){
			if(itemUnits[i]!=null){
				if(itemUnits[i].isSelect())
					return i;
			}
		}
		return -1;
	}
	/**是否选中了除了selectID以外的物品的index*/
	public int getSelectedOtherItemIndex(int selectID){
		for(int i=0;i<MAX_ITEMS_NUM;i++){
			if(selectID==i)
				continue;
			if(itemUnits[i]!=null){
				if(itemUnits[i].isSelect())
					return i;
			}
		}
		return -1;
	}
	public void showDetail(float x,float y,ItemStackData item){
		detailPanel.setVisible(true);
		detailPanel.updateItem(item);
		detailPanel.setPosition(x, y);
	}
	public void followDetail(float x,float y){
		detailPanel.setPosition(x, y);
	}
	public void hideDetail(){
		detailPanel.setVisible(false);
	}
	public void toggleColor(){
		if(!coloredMoney){
			if(moenyLabel!=null)
				moenyLabel.remove();
			int mo=game.ls.player.playerData().moneys[0];
			String moStr=mo+"";
			if(mo>=10000&&mo<100000){//蓝色万
				moenyLabel= new Label(moStr, game.rs.getDarkBlueLabelStyle(moStr));
			}else if(mo>=100000&&mo<1000000){//绿色十万
				moenyLabel= new Label(moStr, game.rs.getDarkGreenLabelStyle(moStr));
			}else if(mo>=1000000&&mo<10000000){//红色百万
				moenyLabel= new Label(moStr, game.rs.getRedLabelStyle(moStr));
			}else if(mo>=10000000&&mo<100000000){//紫色千万
				moenyLabel= new Label(moStr, game.rs.getPurpleLabelStyle(moStr));
			}else if(mo>=100000000){//棕色亿
				moenyLabel= new Label(moStr, game.rs.getBrownLabelStyle(moStr));
			}else
				moenyLabel= new Label(moStr, game.rs.getBlackUILabelStyle());
			moenyLabel.setBounds(65, 280, 80, 20);
			addActor(moenyLabel);
		}else{
			if(moenyLabel!=null)
				moenyLabel.remove();
			moenyLabel= new Label(game.ls.player.playerData().moneys[0]+"", game.rs.getBlackUILabelStyle());
			moenyLabel.setBounds(65, 280, 80, 20);
			addActor(moenyLabel);
		}
		coloredMoney=!coloredMoney;
	}
	public void refreshAll(){
		//equipBox.refresh();
		//sitemBox.refresh();
		for(int i=0;i<MAX_ITEMS_NUM;i++){
			if(itemUnits[i].item()!=null){
				itemUnits[i].updateItem();
			}else{
				itemUnits[i].setNoItem();
				detailPanel.remove();
				addActor(detailPanel);
			}
		}
		moenyLabel.setText(game.ls.player.playerData().moneys[0]+"");
		stroredMoneyLabel.setText(game.ls.player.playerData().moneys[1]+"");
	}
	/**置顶显示*/
	public void onTop(){
		Group group=getParent();
		remove();
		group.addActor(me);
	}
	public void delItem(){
		if(getSelectedItemIndex()==-1){
			game.cs.UI_showSystemMessage("请先选择一个要扔掉的东西");
		}else{
			int index=getSelectedItemIndex();
			game.cs.ACT_confirmRemoveItem(index);
			unselectAll();
		}
	}
}
