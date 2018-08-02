package xyq.game.stage.UI.dialog.comp;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;
import xyq.game.stage.InfoActor;
import xyq.game.stage.WasIconActor;

public class ItemDetailPanel   extends Group{
	XYQGame game;
	
	final float WIDTH=410;
	final float HEIGHT=262;
	final int NAME=0;
	final int DESCRIPTION=1;
	final int FUNCTIONDESCRIPTION=2;
	final int TYPE=3;
	final int CHATACTER=4;
	final int LEVEL=5;

	final int LIMITTIME=0;
	
	final int defaultHeight=242;
	
	InfoActor panel;


	/**物品附加数据数组，绘制用*/
	String[] itemTail;
	WasIconActor itemBigIcon;
	Label nameLabel;
	Label descLabel;
	Label tag1Label;
	Label tag2Label;
	Label tag3Label;
	Label tag4Label;
	Label tag5Label;
	public ItemDetailPanel(final XYQGame game){
		this.game=game;
		
		setSize(WIDTH, HEIGHT);
		
		panel=new InfoActor(game.rs); 
		panel.setSize(WIDTH, HEIGHT);
		addActor(panel);
	}
	public String autoRowChangeInsert(String str,float fontSize,float width){
		StringBuilder sb=new StringBuilder();
		int cc=0;
		for(int i=0;i<str.length();i++){
			sb.append(str.charAt(i));
			if(FreeTypeFontGenerator.DEFAULT_CHARS.indexOf(String.valueOf(str.charAt(i)))!=-1)
				cc+=fontSize/2;
			else
				cc+=fontSize;
			if(cc>=width){
				cc=0;
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	public int rowsCount(String text){
		int r=0;
		for(int i=0;i<text.length();i++){
			if(String.valueOf(text.charAt(i)).equals("\n"))
				r++;
		}
		return r;
	}
	/**根据物品信息来更新面板*/
	public void updateItem(ItemStackData item) {

		if(itemBigIcon!=null){
			itemBigIcon.remove();
		}
		itemBigIcon=new WasIconActor(game.rs, "item.wdf", game.db.getItemIcon(item,false), null);
		itemBigIcon.setPosition(8, -40-itemBigIcon.getHeight());
		addActor(itemBigIcon);
		
		if(nameLabel!=null){
			nameLabel.remove();
		}
		String name=game.itemDB.getItemName(item);
		nameLabel=new Label(name, game.rs.getYellowLabelStyle(name,20));
		nameLabel.setPosition(150, -30);
		addActor(nameLabel);
		
		if(descLabel!=null){
			descLabel.remove();
		}
		String desc=game.itemDB.getItemDesc(item);
		String rowText=autoRowChangeInsert(desc,18,240);
		descLabel = new Label(rowText, game.rs.getLabelStyle(desc,18));
		float descPosY=-55-rowsCount(rowText)*20;
		descLabel.setPosition(150, descPosY);
		descLabel.setAlignment(Align.topLeft);
		addActor(descLabel);
		
		float printY=-75f-100f;
		if(tag1Label!=null)
			tag1Label.remove();
		if(!(item.getTag1().equals("")||item.getTag1().equals("null"))){
			tag1Label=new Label(item.getTag1(), game.rs.getYellowLabelStyle(item.getTag1(),18));
			tag1Label.setPosition(150, printY);
			addActor(tag1Label);
			printY-=20;
		}
		if(tag2Label!=null)
			tag2Label.remove();
		if(!(item.getTag2().equals("")||item.getTag2().equals("null"))){
			tag2Label=new Label(item.getTag2(), game.rs.getYellowLabelStyle(item.getTag2(),18));
			tag2Label.setPosition(150, printY);
			addActor(tag2Label);
			printY-=20;
		}
		if(tag3Label!=null)
			tag3Label.remove();
		if(!(item.getTag3().equals("")||item.getTag3().equals("null"))){	
			tag3Label=new Label(item.getTag3(), game.rs.getGreenLabelStyle(item.getTag3(),18));
			tag3Label.setPosition(150, printY);
			addActor(tag3Label);
			printY-=20;
		}
		if(tag4Label!=null)
			tag4Label.remove();
		if(!(item.getTag4().equals("")||item.getTag4().equals("null"))){
			tag4Label=new Label(item.getTag4(), game.rs.getBlueLabelStyle(item.getTag4(),18));
			tag4Label.setPosition(150, printY);
			addActor(tag4Label);
			printY-=20;
		}
		if(tag5Label!=null)
			tag5Label.remove();
		if(!(item.getTag5().equals("")||item.getTag5().equals("null"))){
			tag5Label=new Label(item.getTag5(), game.rs.getLabelStyle(item.getTag5(),18));
			tag5Label.setPosition(150, printY);
			addActor(tag5Label);
			printY-=20;
		}
	}
}
