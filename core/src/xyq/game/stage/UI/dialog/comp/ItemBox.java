package xyq.game.stage.UI.dialog.comp;

import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;
import xyq.game.stage.MyActor;
import xyq.game.stage.WasActor;

public class ItemBox  extends Group{
	XYQGame game;

	MyActor debugPanel;
	WasActor selectBox;
	Group itemgroup;
	
	HashMap<Integer,ItemStackData> items;
	WasActor[] itemsImg;
	ShadowLabel[] itemCounter;
	boolean needRefresh;
	
	int idX=-1;
	int idY=-1;

	int beginItemIndex;
	int endItemIndex;
	int itemXCount;
	int itemYCount;
	
	public ItemBox(final XYQGame game,int XCount,int YCount,boolean isdebug){
		super();
		this.game=game;
		float width=XCount*52;
		float height=YCount*52;
		itemXCount=XCount;
		itemYCount=YCount;
		
		setWidth(width);
		setHeight(height);
		
		if(isdebug){
			debugPanel=new MyActor(game.rs.getRect(width,height));
			addActor(debugPanel);
		}
		selectBox=new WasActor(game.rs, "wzife.wdf", "UI\\道具选择框大", null);
		selectBox.setVisible(false);
		addActor(selectBox);
		
		itemgroup=new Group();
		itemgroup.setBounds(0, 0, width, height);
		addActor(itemgroup);
		
		ShadowLabel label=new ShadowLabel("12",game,0,0,32,16);
		addActor(label);
		
		//itemsImg=new WasActor[itemCountInEachRow*itemCountRows];
		//itemCounter=new ShadowLabel[itemCountInEachRow*itemCountRows];
		
		addListener(new InputListener(){
			public boolean mouseMoved (InputEvent event, float x, float y) {
				y=getHeight()-y;
				idX=(int)(x/52f);
				idY=(int)(y/52f);
				selectBox.setPosition(idX*51-1,getHeight()-54-(idY*51)+2);
				//System.out.println("current select:"+getMouseOnItemIndex());
				return true;
			}
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				selectBox.setVisible(true);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				selectBox.setVisible(false);
				idX=-1;
				idY=-1;
			}
		});
	}
	public int getMouseOnItemIndex(){
		if(idX==-1||idY==-1){
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
			for(WasActor me:itemsImg){
				if(me!=null)
					me.remove();
			}
		itemsImg=new WasActor[itemXCount*itemYCount];
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
			
			itemsImg[currentItemImgArrayIndex]=new WasActor(game.rs, "item.wdf",game.db.getItemIcon(tempItem, true));
			float offsetX=(52-itemsImg[currentItemImgArrayIndex].getImg().getW())/2;
			if(offsetX<0)offsetX=0;
			float imgPosX=xIndex*52+offsetX;
			
			float offsetY=(52-itemsImg[currentItemImgArrayIndex].getImg().getH())/2;
			if(offsetY<0)offsetY=0;
			float imgPoxY=getHeight()-(yIndex*52)-52+offsetY;
			
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
				itemCounter[currentItemImgArrayIndex]=new ShadowLabel(num, game, xIndex*52+3,getHeight()-(yIndex*52)+35, 16, 16);
				itemgroup.addActor(itemCounter[currentItemImgArrayIndex]);
			}
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
}
