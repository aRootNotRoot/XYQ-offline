package xyq.game.stage.UI.dialog.comp;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;
import xyq.game.stage.WasActor;
import xyq.game.stage.WasIconActor;
import xyq.game.stage.UI.dialog.RoleShopDlg;
import xyq.game.stage.UI.dialog.SystemShopDlg;
import xyq.system.bussiness.GoodsData;

/**单个道具内容*/
public class ItemShopUnit extends Group{
	XYQGame game;
	ItemShopUnit me;
	/**道具选择框*/
	WasActor selectBox;
	/**道具选中框*/
	WasActor selectedBox;
	/**道具图像*/
	WasIconActor itemImg;
	/**道具计数*/
	ShadowLabel itemCounter;
	/**道具*/
	//ItemStackData item;
	int itemStackIndex;
	
	Object parentFrame;
	Class<?> parentClass;
	
	boolean mouse_on;
	
	/**构建一个商店商品单元
	 * @param game 整个游戏对象
	 * @param parentFrame 商品单元所在父框（一般用于控制点一个商品取消其他商品选中的）
	 * @param parentClass 父框所在类，可能是角色商店（摆摊）框，可能是系统商店框，可能是其他的
	 * */
	public ItemShopUnit(final XYQGame game,Object parentFrame,Class<?> parentClass){
		super();
		this.game=game;
		me=this;
		this.parentFrame=parentFrame;
		this.parentClass=parentClass;
		
		setSize(51, 51);
		
		selectBox=new WasActor(game.rs, "wzife.wdf", "UI\\道具选择框大", null);
		selectBox.setVisible(false);
		addActor(selectBox);
		
		selectedBox=new WasActor(game.rs, "wzife.wdf", "UI\\红小方框", null);
		selectedBox.setVisible(false);
		addActor(selectedBox);
		
		addListener(new InputListener(){
			public boolean mouseMoved (InputEvent event, float x, float y) {
				return true;
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				GoodsData item=null;
				//如果父框是摆摊框
				if(me.parentClass.isAssignableFrom(RoleShopDlg.class)){
					item=((RoleShopDlg)me.parentFrame).getGoods(itemStackIndex);
				}else if(me.parentClass.isAssignableFrom(SystemShopDlg.class)){
					item=((SystemShopDlg)me.parentFrame).getGoods(itemStackIndex);
				}
				if(game.is_Debug){
					if(button==0)
						Gdx.app.log("[ XYQ ]", "[ ItemShopUnit ] -> 当前道具：位置索引="+itemStackIndex+",售价="+item.getPrice()+",是否无限供货="+item.isInfinite()+",商品id="+item.getItem().getItemID()+",商品堆叠="+item.getItem().getItemCount());
				}
				if(button==1){
					if(item!=null&&item.getItem().getItemCount()>0){
						//game.cs.ACT_bagItemUse(item.getStackIndex(), 1);
						updateItem();
					}
					unselect();
				}
				if(button==0){
					//如果父框是摆摊框
					if(me.parentClass.isAssignableFrom(RoleShopDlg.class)){
						if(((RoleShopDlg)me.parentFrame).selectGoods(itemStackIndex))//如果成功选中
							select();
						else{
							
						}
					}else if(me.parentClass.isAssignableFrom(SystemShopDlg.class)){
						if(((SystemShopDlg)me.parentFrame).selectGoods(itemStackIndex))//如果成功选中
							select();
						else{
							
						}
					}
					
				}
				if(button==1){
					//如果父框是摆摊框
					if(me.parentClass.isAssignableFrom(RoleShopDlg.class)){
						((RoleShopDlg)me.parentFrame).resetSelected();
							
					}else if(me.parentClass.isAssignableFrom(SystemShopDlg.class)){
						((SystemShopDlg)me.parentFrame).resetSelected();
					}
					
				}
				return false;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				
			}
			
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				selectBox.setVisible(true);
				
				mouse_on=true;
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				selectBox.setVisible(false);
				mouse_on=false;
			}
		});
		
	}
	public void unselect(){
		selectedBox.setVisible(false);
	}
	public void select(){
		selectedBox.setVisible(true);
	}
	public boolean isSelect(){
		return selectedBox.isVisible();
	}
	public void setNoItem(){
		if(itemImg!=null){
			itemImg.remove();
			itemImg=null;
		}
		if(itemCounter!=null){
			itemCounter.remove();
			itemCounter=null; 
		}
		itemStackIndex=-1;
	}
	/**鼠标是否在当前的道具上*/
	public boolean isMouseON(){
		return this.mouse_on;
	}
	/**设定道具*/
	public void setItem(int index){
		this.itemStackIndex=index;
		ItemStackData item=null;
		//如果父框是摆摊框
		if(parentClass.isAssignableFrom(RoleShopDlg.class)){
			GoodsData goodsData=((RoleShopDlg)parentFrame).getGoods(itemStackIndex);
			if(goodsData==null){
				setNoItem();
				return;
			}
			item=goodsData.getItem();
		}else if(parentClass.isAssignableFrom(SystemShopDlg.class)){
			GoodsData goodsData=((SystemShopDlg)parentFrame).getGoods(itemStackIndex);
			if(goodsData==null){
				setNoItem();
				return;
			}
			item=goodsData.getItem();
		}
		if(item==null){//当前无道具
			setNoItem();
			return;
		}/*else if(item.getStackIndex()==-1){//当前道具索引为空[用不着用这个因为商品索引都给了负]
			setNoItem();
			return;
		}*/else if(item.getItemCount()<1){//当前道具没有数量
			setNoItem();
			return;
		}
		if(itemImg!=null)
			itemImg.remove();
		itemImg=new WasIconActor(game.rs, "item.wdf",game.db.getItemIcon(item, true));
		itemImg.setPosition(0, 0);
		addActor(itemImg);
		
		String num=item.getItemCount()+"";
		if(itemCounter!=null)
			itemCounter.remove();
		if(item.getItemCount()>1){
			itemCounter=new ShadowLabel(num, game, 3,35, 16, 16);
			addActor(itemCounter);
		}else if(itemCounter!=null){
			itemCounter.remove();
		}

	}
	public void updateItem(){
		if(itemCounter!=null){
			itemCounter.remove();
		}
		setItem(itemStackIndex);
	}
	/**获取当前道具*/
	public ItemStackData item(){
		ItemStackData item=null;
		//如果父框是摆摊框
		if(parentClass.isAssignableFrom(RoleShopDlg.class)){
			GoodsData goodsData=((RoleShopDlg)parentFrame).getGoods(itemStackIndex);
			if(goodsData==null){
				return null;
			}
			item=goodsData.getItem();
		}else  if(parentClass.isAssignableFrom(SystemShopDlg.class)){
				GoodsData goodsData=((SystemShopDlg)parentFrame).getGoods(itemStackIndex);
				if(goodsData==null){
					return null;
				}
				item=goodsData.getItem();
			}
		return item;
	}
}
