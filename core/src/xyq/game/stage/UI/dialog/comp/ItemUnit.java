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
import xyq.game.stage.UI.dialog.PlayerBagDlg;

/**单个道具内容*/
public class ItemUnit extends Group{
	XYQGame game;
	ItemUnit me;
	/**道具选择框*/
	WasActor selectBox;
	/**道具选中框*/
	WasActor selectedBox;
	/**道具背景图像*/
	WasIconActor itemBGImg;
	/**道具图像*/
	WasIconActor itemImg;
	/**道具计数*/
	ShadowLabel itemCounter;
	/**道具*/
	//ItemStackData item;
	/**所在母框*/
	PlayerBagDlg playerBagDlg;
	int itemStackIndex;
	
	boolean hasBG;
	boolean mouse_on;
	public ItemUnit(final XYQGame game,final PlayerBagDlg playerBagDlg){
		super();
		this.game=game;
		this.playerBagDlg=playerBagDlg;
		me=this;
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
				ItemStackData item=game.ls.player.getItem(itemStackIndex);
				if(game.is_Debug){
					if(button==0&&item!=null)
						Gdx.app.log("[ XYQ ]", "[ ItemUnit ] -> 当前道具：index="+item.getStackIndex()+",count="+item.getItemCount()+",id="+item.getItemID());
				}
				if(button==1){
					if(item!=null&&item.getItemCount()>0){
						game.cs.ACT_bagItemUse(item.getStackIndex(), 1);
						updateItem();
					}
					playerBagDlg.unselectAll();
				}
				if(button==0){
					//如果选中了其他的道具，则交换，否则则选中
					if(playerBagDlg.isSelectOtherItem(itemStackIndex)){
						//System.err.println("准备交换");
						int otherSelectedIndex=playerBagDlg.getSelectedOtherItemIndex(itemStackIndex);
						//System.err.println("itemStackIndex is "+otherSelectedIndex);
						//System.err.println("otherSelectedIndex is "+otherSelectedIndex);
						game.cs.ACT_bagItemSwitch(itemStackIndex, otherSelectedIndex);
						playerBagDlg.unselectAll();
					}else{
						//System.err.println("不交换，直接选中");
						if(item==null)
							;//空格子不能被选中
						else if(itemStackIndex>-1&&itemStackIndex<6){
							;//装备不能选中
						}
						else
							playerBagDlg.selectItem(itemStackIndex);
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
		if(itemBGImg!=null){
			itemBGImg.remove();
			itemBGImg=null;
		}
		if(itemCounter!=null){
			itemCounter.remove();
			itemCounter=null; 
		}
	}
	/**鼠标是否在当前的道具上*/
	public boolean isMouseON(){
		return this.mouse_on;
	}
	/**设定道具*/
	public void setItem(int index){
		this.itemStackIndex=index;
		ItemStackData item=game.ls.player.getItem(index);
		if(item==null){//当前无道具
			setNoItem();
			return;
		}else if(item.getStackIndex()==-1){//当前道具索引为空
			setNoItem();
			return;
		}else if(item.getItemCount()<1){//当前道具没有数量
			setNoItem();
			return;
		}
		if(hasBG){
			if(itemBGImg!=null)
				itemBGImg.remove();
			itemBGImg=new WasIconActor(game.rs, "wzife.wdf","UI\\空道具栏");
			itemBGImg.setPosition(3, 2);
			addActor(itemBGImg);
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
		return game.ls.player.getItem(itemStackIndex);
	}
	/**设定是否有空白背景图*/
	public void hasBG(boolean b) {
		this.hasBG=b;
	}
}
