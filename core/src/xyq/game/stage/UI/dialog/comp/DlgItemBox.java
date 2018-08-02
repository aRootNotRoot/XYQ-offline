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

/**紧凑型物品框*/
public class DlgItemBox  extends Group{
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
	ShadowLabel[] itemCounter;
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
	public DlgItemBox(final XYQGame game,int XCount,int YCount,boolean isdebug){
		super();
		this.game=game;
		debug=isdebug;
		float width=XCount*blockWH;
		float height=YCount*blockWH;
		itemXCount=XCount;
		itemYCount=YCount;
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
				selectBox.setPosition(idX*51-1,getHeight()-54-(idY*51)+2);
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
		if(id>=itemCounter.length)
			return;
		if(itemCounter[id]!=null){
			//px=itemCounter[id].getX();
			//py=itemCounter[id].getY();
			itemCounter[id].remove();
		}
		ItemStackData tempItem=game.ls.player.getItems().get(id+beginItemIndex);
		if(tempItem==null){
			if(itemsImg[id]!=null)
				itemsImg[id].remove();
			return;
		}
		String num=tempItem.getItemCount()+"";
		if(tempItem.getItemCount()>1){
			itemCounter[id]=new ShadowLabel(num, game, idX*51+3,getHeight()-(idY*51)-52+35, 16, 16);
			itemgroup.addActor(itemCounter[id]);
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
		if(itemCounter!=null)
			for(ShadowLabel me:itemCounter){
				if(me!=null)
					me.remove();
			}
		itemCounter=new ShadowLabel[itemXCount*itemYCount];
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
			itemgroup.addActor(itemsImg[currentItemImgArrayIndex]);
			
			String num=tempItem.getItemCount()+"";
			if(tempItem.getItemCount()>1){
				itemCounter[currentItemImgArrayIndex]=new ShadowLabel(num, game, xIndex*51+3,getHeight()-(yIndex*51)-52+35, 16, 16);
				itemgroup.addActor(itemCounter[currentItemImgArrayIndex]);
			}
			/*
			WasActor ws=new WasActor(game.rs, "item.wdf","武器\\鞭\\070雷鸣嗜血鞭_小");
			ws.setPosition(-ws.getImg().getCx(), ws.getImg().getCy());
			System.err.println("WH is--");
			System.err.println(ws.getImg().getW());
			System.err.println(ws.getImg().getH());
			System.err.println("WaHa is--");
			System.err.println(ws.getWidth());
			System.err.println(ws.getHeight());
			System.err.println("XY is--");
			System.err.println(ws.getX());
			System.err.println(ws.getY());
			itemgroup.addActor(ws);
			*/
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
