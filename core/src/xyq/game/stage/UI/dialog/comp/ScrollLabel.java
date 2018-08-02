package xyq.game.stage.UI.dialog.comp;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.system.utils.TextUtil;

/**一次显示多行的滑动字符块组件*/
public class ScrollLabel extends Label{
	
	ArrayList<String> lines;
	int currentBeginLine=0;
	int currentEndLine=0;
	int maxlines=0;
	int fontsize;
	String orgText;
	XYQGame game;
	public ScrollLabel(XYQGame game,String text,LabelStyle style,int width,int height,int fontsize) {
		super("", style);
		this.orgText=text;
		this.fontsize=fontsize;
		this.game=game;
		setWidth(width);
		setHeight(height);
		this.setAlignment(Align.topLeft);
		maxlines=(int)(height/fontsize)-1;
		if(maxlines<=0)
			maxlines=1;
		lines=TextUtil.autoMutiLine(text,fontsize,width);
		
		currentBeginLine=0;
		if(lines.size()>=maxlines)
			currentEndLine=maxlines;
		else
			currentEndLine=lines.size();
		this.setText(txt());
	}
	
	public void showMore(int rows){
		maxlines+=rows;
	}
	String txt(){
		StringBuilder sb=new StringBuilder();
		for(int i=currentBeginLine;i<currentEndLine;i++){
			if(i>=lines.size()){
				sb.append("\n");
			}else{
				sb.append(lines.get(i));
				sb.append("\n");
			}
			
		}
		return sb.toString();
	}
	public void preLine(){
		if(lines==null)
			return;
		if(currentBeginLine==0)
			return;
		
		currentBeginLine--;
		currentEndLine--;

		freshNowLineText();
	}
	public void nextLine(){
		if(lines==null)
			return;
		if(currentEndLine>=lines.size())
			return;
		currentBeginLine++;
		currentEndLine++;
		freshNowLineText();
	}
	public void setLine(int line,int endline){
		if(lines==null)
			return;
		if(line<0||line>=lines.size())
			return;
		if(endline<0||endline>=lines.size())
			return;
		if(line>endline)
			return;
		currentBeginLine=line;
		currentEndLine=endline;
		freshNowLineText();
	}
	public void freshNowLineText(){
		if(lines==null)
			return;
		this.setText(txt());
	}


	public void setEndLine(int i) {
		if(i>=lines.size())
			i=lines.size()-1;
		else
			currentEndLine=i;
		
	}

	public void addText(String text) {
		orgText=orgText+text;
		LabelStyle style=getStyle();
		style=game.rs.getBlackLabelStyle(orgText, 12);
		setStyle(style);
		
		maxlines=(int)(getHeight()/fontsize)-1;
		if(maxlines<=0)
			maxlines=1;
		lines=TextUtil.autoMutiLine(orgText,fontsize,(int)getWidth());
		
		currentBeginLine=0;
		if(lines.size()>=maxlines)
			currentEndLine=maxlines;
		else
			currentEndLine=lines.size();
		this.setText(txt());
		 
	}
}
