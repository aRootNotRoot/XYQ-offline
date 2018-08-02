package xyq.game.stage.UI.dialog.comp;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.stage.ChatActor;
import xyq.system.utils.TextUtil;

public class ChatBubble extends Group{
	
		Label chatLabel;
		ChatActor chatPanel;	
	
		public void makeBubble(String words,XYQGame game){
			float width=TextUtil.getMaxStrWidth(words, 16,142);
			ArrayList<String> wordsline=TextUtil.autoMutiLine(words, 16, (int)width-12);
			if(wordsline.get(wordsline.size()-1).equals(""))
				wordsline.remove(wordsline.size()-1);
			float height=4+18*wordsline.size();
			words=TextUtil.autoMultiLine(words, 16,  (int)width-12);
			width+=16;
			if(width<28)
				width=28;
			setPosition((-width/2f), height+70);
			setSize(width, height);
			chatPanel=new ChatActor(game.rs);
			chatPanel.setSize(width, height);
			chatPanel.setPosition(0, 0);
			addActor(chatPanel);
			
			chatLabel=new Label(words, game.rs.getLabelStyle(words));
			chatLabel.setBounds(8, 2-height, width-16, height-4);
			chatLabel.setAlignment(Align.top);
			addActor(chatLabel);
			
			setVisible(false);
		}
}
