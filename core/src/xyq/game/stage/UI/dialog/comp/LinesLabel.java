package xyq.game.stage.UI.dialog.comp;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import xyq.system.utils.TextUtil;

public class LinesLabel extends Label{
	
	ArrayList<String> lines;
	int currentLine=0;
	
	public LinesLabel(LabelStyle style,int width,int height) {
		super("", style);
		setWidth(width);
		setHeight(height);
	}
	
	public void setText(String text){
		int wid=(int) (getWidth());
		lines=TextUtil.autoMutiLine(text,16,wid);
		currentLine=0;
		this.setText(lines.get(currentLine));
	}
	
	public void preLine(){
		if(lines==null)//TODO 没设定text的时候
			return;
		currentLine--;
		if(currentLine<0)
			currentLine=0;
		freshNowLineText();
	}
	public void nextLine(){
		if(lines==null)
			return;
		currentLine++;
		if(currentLine>=lines.size())
			currentLine=lines.size()-1;
		freshNowLineText();
	}
	public void setLine(int line){
		if(lines==null)
			return;
		if(line<0||line>=lines.size())
			return;
		currentLine=line;
		freshNowLineText();
	}
	public void freshNowLineText(){
		if(lines==null)
			return;
		this.setText(lines.get(currentLine));
	}
}
