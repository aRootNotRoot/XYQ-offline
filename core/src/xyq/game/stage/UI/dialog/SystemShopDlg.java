package xyq.game.stage.UI.dialog;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.stage.WasIconActor;
import xyq.game.stage.UI.dialog.comp.ItemDetailPanel;
import xyq.game.stage.UI.dialog.comp.ItemShopUnit;
import xyq.game.stage.UI.dialog.comp.MoneyLabel;
import xyq.system.bussiness.GoodsData;
import xyq.system.bussiness.ShopData;

/**摆摊商店界面*/
public class SystemShopDlg extends Group{
	public final int MAX_ITEMS_NUM=25;
	
	final int[][] ITEMS_UNIT_POS={
			//道具栏位置
			{7,337},{58,337},{109,337},{160,337},{211,337},
			{7,286},{58,286},{109,286},{160,286},{211,286},
			{7,235},{58,235},{109,235},{160,235},{211,235},
			{7,184},{58,184},{109,184},{160,184},{211,184},
			{7,133},{58,133},{109,133},{160,133},{211,133},
	};
	
	XYQGame game;
	ShopData shop;
	SystemShopDlg me;
	
	WasIconActor panel;
	Button clzBtn;
	
	TextButton buyBtn;
	
	MoneyLabel singlePriceLabel;
	MoneyLabel totalPriceLabel;
	Label countLabel;
	MoneyLabel moneyLabel;
	
	
	Group goodsGP;
	/**道具详情框*/
	ItemDetailPanel detailPanel;
	
	ItemShopUnit[] itemUnits;
	
	/**选中的道具数量*/
	int selectedCount=0;
	/**上一次选中的商品索引*/
	int lastSelectedIndex=0;
	
	/**摆摊购买对话框*/
	public SystemShopDlg(XYQGame game,ShopData shop){
		super();
		this.game=game;
		this.shop=shop;
		this.me=this;
		
		if(true){
			setSize(272, 720);
			setPosition(494, 146);
			panel=new WasIconActor(game.rs,"wzife.wdf","UI\\购买");
			panel.setPosition(0, 0);
			panel.addListener(new ClickListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	//右键关闭对话框
	            	if(button==1)
	            		setVisible(false);
	            	else
	            		onTop();
	            	return false;
	            }
	        });
			addActor(panel);
			
			clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

			clzBtn.setPosition(251, 398);
			clzBtn.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                setVisible(false);
	            }
	        });
	        addActor(clzBtn); 
	        
	        buyBtn= new TextButton("购买", game.rs.getCommonTextButtonStyle());
	        buyBtn.pad(1);
	        buyBtn.setPosition(190, 10);
	        buyBtn.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            	dobuy();
	            }
			});
			addActor(buyBtn);
			
			moneyLabel= new MoneyLabel(game,game.ls.ifm.PLAYER_getMoney());
			moneyLabel.setBounds(75,5, 80, 30);
			addActor(moneyLabel);
		
			
			goodsGP=new Group();
			goodsGP.setBounds(18, 110, 256, 209);
			addActor(goodsGP);
			
			itemUnits=new ItemShopUnit[MAX_ITEMS_NUM];
			for(int i=0;i<MAX_ITEMS_NUM;i++){
				itemUnits[i]=new ItemShopUnit(game,this,SystemShopDlg.class);
				itemUnits[i].setPosition(ITEMS_UNIT_POS[i][0]-goodsGP.getX(), ITEMS_UNIT_POS[i][1]-goodsGP.getY());
				itemUnits[i].setItem(i);
				goodsGP.addActor(itemUnits[i]);
			}
			
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
	}
	/**获取当前摆摊位里的某一个商品
	 * @param index 商品索引id
	 * */
	public GoodsData getGoods(int index){
		if(shop==null){
			Gdx.app.error("[ XYQ ]", "[ SystemShopDlg ] -> 获取系统商店的一个商品失败了，因为当前商店没有商店数据");
			return null;
		}
		GoodsData goodsData=shop.getGoods().get(index);
		return goodsData;
	}
	/**置顶显示*/
	public void onTop(){
		Group group=getParent();
		remove();
		group.addActor(me);
	}
	/**选择当前位置的商品数据
	 * @param index 商品索引id
	 * */
	public boolean selectGoods(int itemStackIndex) {
		GoodsData goodsData=getGoods(itemStackIndex);
		if(goodsData==null)
			return false;
		//如果上一次选中的也是这个道具，则多选一个
		int price=0;
		int totalPrice=0;
		if(goodsData.getItem()==null)
			return false;
		if(goodsData.getItem().getItemCount()<1)
			return false;
		if(lastSelectedIndex==itemStackIndex){
			selectedCount++;
			if(selectedCount>goodsData.getItem().getItemCount())//如果选的超过了上架数量，则不再增加
				selectedCount=goodsData.getItem().getItemCount();
			price	=goodsData.getPrice();
			totalPrice=price*selectedCount;
		}else{//如果上一次选中的和这次不一样，则从新选
			lastSelectedIndex=itemStackIndex;
			selectedCount=1;
			price	=goodsData.getPrice();
			totalPrice=goodsData.getPrice();
		}
		if(singlePriceLabel!=null)
			singlePriceLabel.remove();
		singlePriceLabel = new MoneyLabel(game,price);
		singlePriceLabel.setBounds(75, 77, 80, 30);
		addActor(singlePriceLabel);
		
		if(totalPriceLabel!=null)
			totalPriceLabel.remove();
		totalPriceLabel = new MoneyLabel(game,totalPrice);
		totalPriceLabel.setBounds(75, 29, 80, 30);
		addActor(totalPriceLabel);
		
		if(countLabel!=null)
			countLabel.remove();
		countLabel = new Label(String.valueOf(selectedCount),game.rs.getBlackUILabelStyle());
		countLabel.setBounds(75,53, 80, 30);
		addActor(countLabel);
		
		//totalPriceLabel;
		//countLabel;
		
		unselectAll();
		
		return true;
	}
	public void resetSelected(){
		int price=0;
		int totalPrice=0;
		lastSelectedIndex=-1;
		selectedCount=0;
		if(singlePriceLabel!=null)
			singlePriceLabel.remove();
		singlePriceLabel = new MoneyLabel(game,price);
		singlePriceLabel.setBounds(75, 77, 80, 30);
		addActor(singlePriceLabel);
		
		if(totalPriceLabel!=null)
			totalPriceLabel.remove();
		totalPriceLabel = new MoneyLabel(game,totalPrice);
		totalPriceLabel.setBounds(75, 29, 80, 30);
		addActor(totalPriceLabel);
		
		if(countLabel!=null)
			countLabel.remove();
		countLabel = new Label(String.valueOf(selectedCount),game.rs.getBlackUILabelStyle());
		countLabel.setBounds(75,53, 80, 30);
		addActor(countLabel);
		
		//totalPriceLabel;
		//countLabel;
		moneyLabel.remove();
		moneyLabel= new MoneyLabel(game,game.ls.ifm.PLAYER_getMoney());
		moneyLabel.setBounds(75, 5, 80, 30);
		addActor(moneyLabel);
		
		unselectAll();
	}
	/**确认购买*/
	void dobuy(){
		if(selectedCount<1){
			if(game.is_Debug)
				Gdx.app.error("[ XYQ ]", "[ SystemShopDlg ]-> 确认购买失败，因为没有选择商品，请至少选择一个商品");
			return;
		}
		GoodsData goodsData=getGoods(lastSelectedIndex);
		int totalPrice=goodsData.getPrice()*selectedCount;
		game.cs.ACT_ShopBuy(goodsData,totalPrice,selectedCount);
		refreshAll();
		resetSelected();
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
		resetSelected();
	}
	public void unselectAll(){
		for(int i=0;i<MAX_ITEMS_NUM;i++){
			if(itemUnits[i]!=null){
				itemUnits[i].unselect();
			}
		}
	}
}
