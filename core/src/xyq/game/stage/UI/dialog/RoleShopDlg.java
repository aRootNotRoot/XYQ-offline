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
import xyq.game.data.Summon;
import xyq.game.stage.WasIconActor;
import xyq.game.stage.UI.dialog.comp.ItemDetailPanel;
import xyq.game.stage.UI.dialog.comp.ItemShopUnit;
import xyq.game.stage.UI.dialog.comp.MoneyLabel;
import xyq.game.stage.UI.dialog.comp.ShadowLabel;
import xyq.system.bussiness.GoodsData;
import xyq.system.bussiness.ShopData;
import xyq.system.utils.RandomUT;

/**摆摊商店界面*/
public class RoleShopDlg extends Group{
	public final int MAX_ITEMS_NUM=20;
	
	final int[][] ITEMS_UNIT_POS={
			//道具栏位置
			{17,265},{68,265},{119,265},{170,265},{221,265},
			{17,214},{68,214},{119,214},{170,214},{221,214},
			{17,163},{68,163},{119,163},{170,163},{221,163},
			{17,112},{68,112},{119,112},{170,112},{221,112},
	};
	
	XYQGame game;
	ShopData shop;
	RoleShopDlg me;
	
	WasIconActor panel;
	Button clzBtn;
	
	TextButton buyBtn;
	TextButton chatBtn;
	
	ShadowLabel nameLabel;
	Label ownerLabel;
	Label ownerIDLabel;
	
	MoneyLabel singlePriceLabel;
	MoneyLabel totalPriceLabel;
	Label countLabel;
	MoneyLabel moneyLabel;
	
	TextButton goodsBtn;
	TextButton petsBtn;
	TextButton paperBtn;
	
	Group goodsGP;
	WasIconActor goodsBG;
	/**道具详情框*/
	ItemDetailPanel detailPanel;
	
	ItemShopUnit[] itemUnits;
	
	Group summonsGP;
	WasIconActor summonsBP;
	WasIconActor summonsMoneyBP;
	Button sumclzBtn;
	
	Label summonList_one;
	Label summonList_two;
	Label summonList_three;
	Label summonList_four;
	Label summonList_five;
	Label[] summonList;
	int[] list_index={-1,-1,-1,-1,-1};
	
	Label sumName;
	Label sumLevel;
	Label sumLoyal;
	Label sumHP;
	Label sumMP;
	
	
	Group makerGP;
	WasIconActor makerBP;
	Button makerclzBtn;
	
	/**选中的道具数量*/
	int selectedCount=0;
	/**上一次选中的商品索引*/
	int lastSelectedIndex=0;

	/**上一次选中的召唤兽索引*/
	int lastSelectedSummonIndex=-1;
	/**当前购买的是啥*/
	String buyType="商品";
	/**摆摊购买对话框*/
	public RoleShopDlg(XYQGame game,ShopData shop){
		super();
		this.game=game;
		this.shop=shop;
		this.me=this;
		
		if(true){
			setSize(291, 428);
			setPosition(494, 146);
			panel=new WasIconActor(game.rs,"wzife.wdf","UI\\摊位框");
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

			clzBtn.setPosition(271, 408);
			clzBtn.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                setVisible(false);
	            }
	        });
	        addActor(clzBtn); 
	        nameLabel= new ShadowLabel(shop.getName(), game, 115, 372, 120, 30);
			addActor(nameLabel);
	        
	        buyBtn= new TextButton("购买", game.rs.getCommonTextButtonStyle());
	        buyBtn.pad(1);
	        buyBtn.setPosition(210, 13);
	        buyBtn.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            	dobuy();
	            }
			});
			addActor(buyBtn);
			
			chatBtn= new TextButton("摊位聊天室", game.rs.getLongCommonTextButtonStyle());
			chatBtn.pad(1);
			chatBtn.setPosition(30, 13);
			chatBtn.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            	
	            }
			});
			addActor(chatBtn);
			
			ownerLabel= new Label(shop.getOwner(), game.rs.getBlackLabelStyle(shop.getOwner()));
			ownerLabel.setBounds(57, 346, 80, 30);
			addActor(ownerLabel);
			
			ownerIDLabel= new Label(String.valueOf(shop.getOwnerID()), game.rs.getBlackUILabelStyle());
			ownerIDLabel.setBounds(210, 346, 80, 30);
			addActor(ownerIDLabel);
			
			moneyLabel= new MoneyLabel(game,game.ls.ifm.PLAYER_getMoney());
			moneyLabel.setBounds(195, 42, 80, 30);
			addActor(moneyLabel);
			
			goodsBtn= new TextButton("物品类", game.rs.getCommonTextButtonStyle());
			goodsBtn.pad(1);
			goodsBtn.setPosition(35, 322);
			goodsBtn.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            	
	            }
			});
			goodsBtn.setDisabled(true);
			addActor(goodsBtn);
			
			petsBtn= new TextButton("召唤兽类", game.rs.getLongCommonTextButtonStyle());
			petsBtn.pad(1);
			petsBtn.setPosition(100, 322);
			petsBtn.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            	makerGP.setVisible(false);
	            	summonsGP.setVisible(true);
	            }
			});
			petsBtn.setDisabled(false);
			addActor(petsBtn);
			
			paperBtn= new TextButton("制造类", game.rs.getCommonTextButtonStyle());
			paperBtn.pad(1);
			paperBtn.setPosition(200, 322);
			paperBtn.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            	summonsGP.setVisible(false);
	            	makerGP.setVisible(true);
	            }
			});
			paperBtn.setDisabled(false);
			addActor(paperBtn);
			
			summonsGP=new Group();
			summonsGP.setBounds(291, 40, 169, 346);
			summonsGP.setVisible(false);
			addActor(summonsGP);
			
			summonsBP=new WasIconActor(game.rs,"wzife.wdf","UI\\召唤兽简要信息");
			summonsBP.setPosition(0, 0);
			summonsGP.addActor(summonsBP);
			
			summonsMoneyBP=new WasIconActor(game.rs,"wzife.wdf","UI\\一口价");
			summonsMoneyBP.setPosition(15, 27);
			//summonsGP.addActor(summonsMoneyBP);
			
			sumclzBtn= new Button(game.rs.getClzBlueCommonButtonStyle());
			sumclzBtn.setPosition(149, 326);
			sumclzBtn.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            	summonsGP.setVisible(false);
	            }
	        });
			summonsGP.addActor(sumclzBtn); 
			
			summonList_one= new Label("召唤兽一", game.rs.getBlackUILabelStyle());
	        summonList_one.setBounds(28, 268, 80, 20);
	        summonList_one.addListener(new InputListener(){
	        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
	        		summonList_one.setPosition(30, 268);
				}

				public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
					summonList_one.setPosition(28, 268);
				}
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					int myIndex=0;
					if(button==0){//左键显示价格
						showSummonPrice(myIndex);
					}else if(button==1){//右键显示详情
						me.game.cs.UI_seeSummon(me.shop.getSummons().get(list_index[myIndex]));
					}
	        		return false;
	        	}
			});
	        summonsGP.addActor(summonList_one);
	        
			summonList_two= new Label("召唤兽二", game.rs.getBlackUILabelStyle());
			summonList_two.setBounds(28, 247, 80, 20);
			summonList_two.addListener(new InputListener(){
	        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
	        		summonList_two.setPosition(30, 247);
				}

				public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
					summonList_two.setPosition(28, 247);
				}
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					int myIndex=1;
					if(button==0){//左键显示价格
						showSummonPrice(myIndex);
					}else if(button==1){//右键显示详情
						me.game.cs.UI_seeSummon(me.shop.getSummons().get(list_index[myIndex]));
					}
	        		return false;
	        	}
			});
			summonsGP.addActor(summonList_two);
			

			summonList_three= new Label("召唤兽三", game.rs.getBlackUILabelStyle());
			summonList_three.setBounds(28, 226, 80, 20);
			summonList_three.addListener(new InputListener(){
	        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
	        		summonList_three.setPosition(30, 226);
				}

				public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
					summonList_three.setPosition(28, 226);
				}
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					int myIndex=2;
					if(button==0){//左键显示价格
						showSummonPrice(myIndex);
					}else if(button==1){//右键显示详情
						me.game.cs.UI_seeSummon(me.shop.getSummons().get(list_index[myIndex]));
					}
	        		return false;
	        	}
			});
			summonsGP.addActor(summonList_three);

			summonList_four= new Label("召唤兽四", game.rs.getBlackUILabelStyle());
			summonList_four.setBounds(28, 205, 80, 20);
			summonList_four.addListener(new InputListener(){
	        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
	        		summonList_four.setPosition(30, 205);
				}

				public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
					summonList_four.setPosition(28, 205);
				}
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					int myIndex=3;
					if(button==0){//左键显示价格
						showSummonPrice(myIndex);
					}else if(button==1){//右键显示详情
						me.game.cs.UI_seeSummon(me.shop.getSummons().get(list_index[myIndex]));
					}
	        		return false;
	        	}
			});
			summonsGP.addActor(summonList_four);

			summonList_five= new Label("召唤兽五", game.rs.getBlackUILabelStyle());
			summonList_five.setBounds(28, 184, 80, 20);
			summonList_five.addListener(new InputListener(){
	        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
	        		summonList_five.setPosition(30, 184);
				}

				public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
					summonList_five.setPosition(28, 184);
				}
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					int myIndex=4;
					if(button==0){//左键显示价格
						showSummonPrice(myIndex);
					}else if(button==1){//右键显示详情
						me.game.cs.UI_seeSummon(me.shop.getSummons().get(list_index[myIndex]));
					}
	        		return false;
	        	}
			});
			summonsGP.addActor(summonList_five);
			
			summonList=new Label[5];
			summonList[0]=summonList_one;
			summonList[1]=summonList_two;
			summonList[2]=summonList_three;
			summonList[3]=summonList_four;
			summonList[4]=summonList_five;
			
			sumName= new Label("", game.rs.getBlackUILabelStyle());
			sumName.setBounds(62, 150, 80, 20);
			summonsGP.addActor(sumName);
			
			sumLevel= new Label("", game.rs.getBlackUILabelStyle());
			sumLevel.setBounds(65, 123, 80, 20);
			summonsGP.addActor(sumLevel);
			
			sumLoyal= new Label("", game.rs.getBlackUILabelStyle());
			sumLoyal.setBounds(65, 98, 80, 20);
			summonsGP.addActor(sumLoyal);
			
			sumHP= new Label("", game.rs.getBlackUILabelStyle());
			sumHP.setBounds(65, 73, 80, 20);
			summonsGP.addActor(sumHP);
			
			sumMP= new Label("", game.rs.getBlackUILabelStyle());
			sumMP.setBounds(65, 48, 80, 20);
			summonsGP.addActor(sumMP);
			
			
			makerGP=new Group();
			makerGP.setBounds(291, 80, 308, 239);
			makerGP.setVisible(false);
			addActor(makerGP);
			
			makerBP=new WasIconActor(game.rs,"wzife.wdf","UI\\制造类别");
			makerBP.setPosition(0, 0);
			makerGP.addActor(makerBP);
			
			makerclzBtn= new Button(game.rs.getClzBlueCommonButtonStyle());
			makerclzBtn.setPosition(288, 219);
			makerclzBtn.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            	makerGP.setVisible(false);
	            }
	        });
			makerGP.addActor(makerclzBtn); 
			
			goodsGP=new Group();
			goodsGP.setBounds(18, 110, 256, 209);
			addActor(goodsGP);
			
			goodsBG=new WasIconActor(game.rs,"wzife.wdf","UI\\20格道具容器");
			goodsBG.setPosition(0, 0);
			goodsBG.addListener(new ClickListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	
	            	return false;
	            }
	        });
			goodsGP.addActor(goodsBG);
			
			itemUnits=new ItemShopUnit[MAX_ITEMS_NUM];
			for(int i=0;i<MAX_ITEMS_NUM;i++){
				itemUnits[i]=new ItemShopUnit(game,this,RoleShopDlg.class);
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
			

			refreshAll();
		}
	}
	void showSummonPrice(int di){
		int index=list_index[di];
		if(index==-1)
			return;
		unselectAll();

		Summon summon=shop.getSummons().get(index);
		int price=Integer.valueOf(summon.getCode_tag());
		if(singlePriceLabel!=null)
			singlePriceLabel.remove();
		singlePriceLabel = new MoneyLabel(game,price);
		singlePriceLabel.setBounds(65, 70, 80, 30);
		addActor(singlePriceLabel);
		
		if(totalPriceLabel!=null)
			totalPriceLabel.remove();
		totalPriceLabel = new MoneyLabel(game,price);
		totalPriceLabel.setBounds(65, 42, 80, 30);
		addActor(totalPriceLabel);
		
		if(countLabel!=null)
			countLabel.remove();
		countLabel = new Label("1",game.rs.getBlackUILabelStyle());
		countLabel.setBounds(195,70, 80, 30);
		addActor(countLabel);
		
		sumName.setText(summon.data().name);
		sumLevel.setText(String.valueOf(summon.data().level));
		sumLoyal.setText(String.valueOf(summon.data().loyal));
		sumHP.setText(String.valueOf(summon.data().HPMax));
		sumMP.setText(String.valueOf(summon.data().MPMax));
		
		lastSelectedSummonIndex=index;
		buyType="召唤兽";
	}
	/**获取当前摆摊位里的某一个商品
	 * @param index 商品索引id
	 * */
	public GoodsData getGoods(int index){
		if(shop==null){
			Gdx.app.error("[ XYQ ]", "[ RoleShopDlg ] -> 获取摆摊页面的一个商品失败了，因为当前摊位没有商店数据");
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
		singlePriceLabel.setBounds(65, 70, 80, 30);
		addActor(singlePriceLabel);
		
		if(totalPriceLabel!=null)
			totalPriceLabel.remove();
		totalPriceLabel = new MoneyLabel(game,totalPrice);
		totalPriceLabel.setBounds(65, 42, 80, 30);
		addActor(totalPriceLabel);
		
		if(countLabel!=null)
			countLabel.remove();
		countLabel = new Label(String.valueOf(selectedCount),game.rs.getBlackUILabelStyle());
		countLabel.setBounds(195,70, 80, 30);
		addActor(countLabel);
		
		//totalPriceLabel;
		//countLabel;
		buyType="商品";
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
		singlePriceLabel.setBounds(65, 70, 80, 30);
		addActor(singlePriceLabel);
		
		if(totalPriceLabel!=null)
			totalPriceLabel.remove();
		totalPriceLabel = new MoneyLabel(game,totalPrice);
		totalPriceLabel.setBounds(65, 42, 80, 30);
		addActor(totalPriceLabel);
		
		if(countLabel!=null)
			countLabel.remove();
		countLabel = new Label(String.valueOf(selectedCount),game.rs.getBlackUILabelStyle());
		countLabel.setBounds(195,70, 80, 30);
		addActor(countLabel);
		
		//totalPriceLabel;
		//countLabel;
		moneyLabel.remove();
		moneyLabel= new MoneyLabel(game,game.ls.ifm.PLAYER_getMoney());
		moneyLabel.setBounds(195, 42, 80, 30);
		addActor(moneyLabel);
		
		unselectAll();
	}
	/**确认购买*/
	void dobuy(){
		if(buyType.equals("商品")){
			if(selectedCount<1){
				if(game.is_Debug)
					Gdx.app.error("[ XYQ ]", "[ RoleShopDlg ]-> 确认购买失败，因为没有选择商品，请至少选择一个商品");
				return;
			}
			GoodsData goodsData=getGoods(lastSelectedIndex);
			int totalPrice=goodsData.getPrice()*selectedCount;
			game.cs.ACT_ShopBuy(goodsData,totalPrice,selectedCount);
			refreshAll();
			resetSelected();
		}else if(buyType.equals("召唤兽")){
			Summon summon=shop.getSummons().get(lastSelectedSummonIndex);
			if(summon==null){
				if(game.is_Debug)
					Gdx.app.error("[ XYQ ]", "[ RoleShopDlg ]-> 确认购买失败，因为选中的召唤兽是空的");
				return;
			}
			int money=Integer.valueOf(summon.getCode_tag());
			
			summon.setSet_index("携带");
			int havedCount=game.ls.ifm.PLAYER_getPickSummonCount();
			if(havedCount>=5){
				if(game.is_Debug)
					Gdx.app.error("[ XYQ ]", "[ RoleShopDlg ]-> 确认购买失败，因为身上带了5个召唤兽了，装不下了");
				return;
			}
			if(game.cs.ACT_minusMoney(money)){
				summon.setCode_tag(RandomUT.getRandomStr(8));
				if(game.cs.ACT_addSummon(summon)){
					shop.getSummons().remove(lastSelectedSummonIndex);
				}else{
					summon.setCode_tag(money+"");
					game.cs.UI_showSystemMessage("购买召唤兽失败了，可能是携带满了。钱是不退了~");
				}
			}else{
				summon.setCode_tag(money+"");
				game.cs.UI_showSystemMessage("少侠，你的现金不够买这只召唤兽");
			}
			refreshAll();
			resetSelected();
		}
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
		
		for(int i=0;i<5;i++)
			summonList[i].setVisible(false);
		if(shop.getSummons()==null||shop.getSummons().size()<=0){//如果商店数据里，召唤兽数据是空的或者没卖召唤兽
			list_index[0]=-1;
			list_index[1]=-1;
			list_index[2]=-1;
			list_index[3]=-1;
			list_index[4]=-1;
		}else{
			int flag=0;
			for(int i=0;i<shop.getSummons().size()&&flag<6;i++){
				Summon sum=shop.getSummons().get(i);
				summonList[flag].setVisible(true);
				String listName="";
				listName=game.summonDB.getModel(sum.data().getType_id()).name;
				if(sum.data().is_BY)
					listName="变异"+listName;
				summonList[flag].setText(listName);
				list_index[flag]=i;
				flag++;
			}
		}
	}
	public void unselectAll(){
		for(int i=0;i<MAX_ITEMS_NUM;i++){
			if(itemUnits[i]!=null){
				itemUnits[i].unselect();
			}
		}
	}
}
