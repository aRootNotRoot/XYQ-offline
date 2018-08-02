package xyq.game.stage.UI.dialog.comp;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.system.utils.TextUtil;

/**一次显示多行的滑动字符块组件*/
public class ScrollLabelFx extends Label{
	
	ArrayList<String> lines;
	int currentBeginLine=0;
	int currentEndLine=0;
	int fontsize;
	int showLines;
	String orgText;
	XYQGame game;
	int addedCount;
	
	public ScrollLabelFx(XYQGame game,String text,LabelStyle style,int width,int height,int fontsize,int showLines) {
		super("", style);
		this.orgText=text;
		this.fontsize=fontsize;
		this.game=game;
		setWidth(width);
		setHeight(height);
		this.setAlignment(Align.topLeft);
		
		lines=TextUtil.autoMutiLineDealN(text,fontsize,width,true);
		
		addedCount=0;
		
		currentBeginLine=0;
		currentEndLine=0+showLines;
		this.showLines=showLines;
		
		this.setText(txt());
	}

	String txt(){
		StringBuilder sb=new StringBuilder();
		for(int i=currentBeginLine;i<currentEndLine;i++){
			if(i>=lines.size()){
				break;
			}else{
				
				if(!lines.get(i).equals("\n")&&!lines.get(i).equals("")){
					sb.append(lines.get(i));
					sb.append("\n");
				}
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

	public void addText(String text,boolean alwaysButtom) {
		orgText=orgText+text;
		addedCount++;
		LabelStyle style=getStyle();
		style=game.rs.getBlackLabelStyle(orgText, 12);
		setStyle(style);
		
	
		lines=TextUtil.autoMutiLineDealN(orgText,fontsize,(int)getWidth(),true);
		for(int i=0;i<lines.size();i++){
			if(lines.equals("\n"))
				lines.remove(i);
		}
		currentBeginLine=0;
		currentEndLine=0+showLines;
		this.setText(txt());
		 
		if(alwaysButtom){
			for(int i=0;i<addedCount;i++){
				nextLine();nextLine();
			}
		}
	}
}
