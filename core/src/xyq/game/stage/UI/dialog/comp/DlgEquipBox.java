package xyq.game.stage.UI.dialog.comp;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;
import xyq.game.stage.MyActor;
import xyq.game.stage.WasActor;
import xyq.game.stage.WasIconActor;

/**物品对话框装备框*/
public class DlgEquipBox  extends Group{
	XYQGame game;
	boolean debug;
	/**调试用于查看范围的框*/
	MyActor debugPanel;
	MyActor[] debugItemPanel;
	/**道具选择框*/
	WasActor selectBox;
	/**道具详情框*/
	ItemDetailPanel detailPanel;
	/**道具框显示集合*/
	Group itemgroup;
	
	HashMap<Integer,ItemStackData> items;
	WasIconActor[] itemsImg;
	WasIconActor[] itemsBGImg;
	
	boolean needRefresh;
	boolean justUseItem=false;
	int idX=-1;
	int idY=-1;

	int beginItemIndex;
	int endItemIndex;
	int itemXCount;
	int itemYCount;
	int itemCounts;
	/**道具方块的大小*/
	final int blockWH=51;
	int lastDetailIndex=-1;
	int nowDetailIndex=-1;
	/***/
	public DlgEquipBox(final XYQGame game,boolean isdebug){
		super();
		this.game=game;
		debug=isdebug;
		float width=2*blockWH;
		float height=3*blockWH;
		itemXCount=2;
		itemYCount=3;
		itemCounts=itemXCount*itemYCount;
		
		setWidth(width);
		setHeight(height);
		
		if(isdebug){
			debugPanel=new MyActor(game.rs.getRect(width+2,height+2));
			debugPanel.setPosition(-1, -1);
			addActor(debugPanel);
			
			debugItemPanel=new MyActor[itemCounts];
			for(int i=0;i<itemCounts;i++){
				debugItemPanel[i]=new MyActor(game.rs.getRect(blockWH,blockWH,0,1,0,1));
				debugItemPanel[i].setX(getXPosByBlock(i));
				debugItemPanel[i].setY(getYPosByBlock(i));
				addActor(debugItemPanel[i]);
			}
		}
		selectBox=new WasActor(game.rs, "wzife.wdf", "UI\\道具选择框大", null);
		selectBox.setVisible(false);
		addActor(selectBox);
		
		itemgroup=new Group();
		itemgroup.setBounds(0, 0, width, height);
		addActor(itemgroup);
		
		detailPanel=new ItemDetailPanel(game);
		detailPanel.setVisible(false);
		addActor(detailPanel);
		
		
		//ShadowLabel label=new ShadowLabel("12",game,0,0,32,16);
		//addActor(label);
		
		//itemsImg=new WasActor[itemCountInEachRow*itemCountRows];
		//itemCounter=new ShadowLabel[itemCountInEachRow*itemCountRows];
		
		addListener(new InputListener(){
			public boolean mouseMoved (InputEvent event, float x, float y) {
				
				///////////////////////////////////////////////////////////////////////////////
				detailPanel.setPosition(x+35, y+20);
				
				
				detailPanel.setVisible(true);
				//更新物品详情面板的位置，并可见
				
				
				///////////////////////////////////////////////////////////////////////////////
				y=getHeight()-y;
				
				if(y>getHeight()){
					selectBox.setVisible(false);
					detailPanel.setVisible(false);
					return true;
				}//如果鼠标移到了框下面，则隐藏选择框，物品详情面板
				idX=(int)(x/blockWH);
				idY=(int)(y/blockWH);
				//更新当前鼠标所在的物品索引上
				selectBox.setPosition(idX*51-1,getHeight()-54-(idY*52)+2);
				//System.out.println("current select:"+getMouseOnItemIndex());
				selectBox.setVisible(true);
				//更新选择框的位置
				////////////////////////////////////////////////////////////////////////////////
				//System.out.println("current select:"+getMouseOnItemIndex());
				if(isStackEmpty(getMouseOnItemIndex()+beginItemIndex)){
					detailPanel.setVisible(false);
				}//如果鼠标在不存在物品的格子上，则隐藏详情面板
				else{
					nowDetailIndex=getMouseOnItemIndex()+beginItemIndex;
					if(nowDetailIndex!=lastDetailIndex){
						ItemStackData item=items.get(nowDetailIndex);
						detailPanel.updateItem(item);
					}
					lastDetailIndex=nowDetailIndex;
				}
				return true;
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(debug){
					if(button==0)
						Gdx.app.log("[ XYQ ]", "[ DlgItemBox ] -> 当前鼠标在的物品索引为："+idX+","+idY);
				}
				if(button==1){
					int id=getMouseOnItemIndex()+beginItemIndex;
					if(id<beginItemIndex)
						;
					else{
						game.cs.ACT_bagItemUse(id, 1);
						 justUseItem=true;
						 updateItemCount(id-beginItemIndex);
					}
				}
				return false;
			}
			
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				selectBox.setVisible(true);
				detailPanel.setVisible(true);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				selectBox.setVisible(false);
				detailPanel.setVisible(false);
				if( justUseItem){
					justUseItem=false;
					selectBox.setVisible(true);
					detailPanel.setVisible(true);
				}
				//idX=-1;
				//idY=-1;
			}
		});
	}
	/**使用物品之后，更新数量的界面显示*/
	void updateItemCount(int id) {
		//float px;
	//	float py;
		
		ItemStackData tempItem=game.ls.player.getItems().get(id+beginItemIndex);
		if(tempItem==null){
			if(itemsImg[id]!=null)
				itemsImg[id].remove();
			if(itemsBGImg[id]!=null)
				itemsBGImg[id].remove();
			return;
		}
	}
	/**根据第几个物品，返回是第几块，X。比如第3个返回0,1*/
	public float getXByBlock(int i){
		int c=0;
		c=i%itemXCount;
		return c;
	}
	public float getYByBlock(int i){
		int r=0;
		r=(int)(i/itemXCount);
		return r;
	}
	/**根据第几个物品，返回X坐标。比如第3个返回104*/
	public float getXPosByBlock(int i){
		int c=0;
		c=i%itemXCount;
		c=c*blockWH;
		return c;
	}
	public float getYPosByBlock(int i){
		float r=0;
		r=(int)(i/itemXCount);
		r=getHeight()-r*blockWH-blockWH;
		return r;
	}
	public boolean isMouseOnStackEmpty(){
		int i=getMouseOnItemIndex();
		if(items.get(i)==null||i==-1)
			return true;
		else
			return false;
	}
	public boolean isStackEmpty(int i){
		if(items.get(i)==null||i==-1)
			return true;
		else
			return false;
	}
	/**获取鼠标现在在的物品的索引，返回比如第12个.如果要获取对应的物品id，请加上beginIndex*/
	public int getMouseOnItemIndex(){
		if(idX==-1||idY==-1||!selectBox.isVisible()){
			return -1;
		}
		return idY*itemXCount+idX;
	}
	public void setItems(HashMap<Integer,ItemStackData> items){
		this.items=items;
		needRefresh=true;
	}
	public void reMake(){
		if(items==null)
			return;
		if(itemsImg!=null)
			for(WasIconActor me:itemsImg){
				if(me!=null)
					me.remove();
			}
		itemsImg=new WasIconActor[itemXCount*itemYCount];
		
		if(itemsBGImg!=null)
			for(WasIconActor me:itemsBGImg){
				if(me!=null)
					me.remove();
			}
		itemsBGImg=new WasIconActor[itemXCount*itemYCount];
		
		ItemStackData tempItem;
		for(int i=beginItemIndex;i<endItemIndex;i++){
			tempItem=items.get(i);
			if(tempItem==null)
				continue;
			int xIndex=0;
			int yIndex=0;
			
			int currentItemImgArrayIndex=i-beginItemIndex;
			
			xIndex=currentItemImgArrayIndex%itemXCount;
			yIndex=(int)(currentItemImgArrayIndex/itemXCount);
			
			itemsImg[currentItemImgArrayIndex]=new WasIconActor(game.rs, "item.wdf",game.db.getItemIcon(tempItem, true));
			itemsBGImg[currentItemImgArrayIndex]=new WasIconActor(game.rs, "wzife.wdf","UI\\空道具栏");
			
			float offsetX=0*(52-itemsImg[currentItemImgArrayIndex].getImg().getW())/2;
			float imgPosX=xIndex*51+offsetX;
			
			float offsetY=0*(52-itemsImg[currentItemImgArrayIndex].getImg().getH())/2;
			float imgPoxY=getHeight()-(yIndex*51)-52+offsetY;
			
			/*
			System.out.println("/-/-/-/-/-/-/-/-/-/-/-/-");
			System.out.println(xIndex+"##"+yIndex);
			System.out.println(itemsImg[currentItemImgArrayIndex].getImg().getW()+"##"+itemsImg[currentItemImgArrayIndex].getImg().getH());
			System.out.println(offsetX+"oo"+offsetY);
			System.out.println(imgPosX+"oo"+imgPoxY);
			*/
			
			itemsImg[currentItemImgArrayIndex].setPosition(imgPosX,imgPoxY);
			itemsBGImg[currentItemImgArrayIndex].setPosition(imgPosX+i%2*2,imgPoxY+i%2*2);
			itemgroup.addActor(itemsBGImg[currentItemImgArrayIndex]);
			itemgroup.addActor(itemsImg[currentItemImgArrayIndex]);
			
			
		}
	}

	public void setItemIndexArea(int i, int j) {
		this.beginItemIndex=i;
		this.endItemIndex=j;
		
		
	}
	@Override
	public void act(float delta){
		super.act(delta);
		if(!needRefresh)
			return;
		reMake();
		needRefresh=false;
	}
	public void refresh() {
		setItems(game.ls.player.getItems());
		selectBox.setVisible(false);
		detailPanel.setVisible(false);
	}
}
