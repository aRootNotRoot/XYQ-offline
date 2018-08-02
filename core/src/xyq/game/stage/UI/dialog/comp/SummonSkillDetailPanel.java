package xyq.game.stage.UI.dialog.comp;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.stage.InfoActor;

public class SummonSkillDetailPanel   extends Group{
	XYQGame game;
	
	final float WIDTH=310;
	final float HEIGHT=140;
	final int NAME=0;
	final int DESCRIPTION=1;
	
	final int defaultHeight=102;
	
	InfoActor panel;


	Label nameLabel;
	Label descLabel;
	
	String lastShowed="";
	public SummonSkillDetailPanel(final XYQGame game){
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
	/**根据信息来更新面板*/
	public void updateSkill(String skillName) {
		if(lastShowed.equals(skillName))
			return;
		lastShowed=skillName;
		
		if(nameLabel!=null){
			nameLabel.remove();
		}
		
		nameLabel=new Label(skillName, game.rs.getYellowLabelStyle(skillName,20));
		nameLabel.setPosition(20, -30);
		addActor(nameLabel);
		
		if(descLabel!=null){
			descLabel.remove();
		}
		String desc=game.db.loadSummonSkillDesc(skillName);
		String rowText=autoRowChangeInsert(desc,18,250);
		descLabel = new Label(rowText, game.rs.getLabelStyle(desc,18));
		float descPosY=-55-rowsCount(rowText)*20;
		descLabel.setPosition(20, descPosY);
		descLabel.setAlignment(Align.topLeft);
		addActor(descLabel);
		
	}
}
