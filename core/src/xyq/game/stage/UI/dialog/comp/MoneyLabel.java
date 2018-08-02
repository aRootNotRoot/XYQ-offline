package xyq.game.stage.UI.dialog.comp;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import xyq.game.XYQGame;

public class MoneyLabel extends Label{
	XYQGame game;
	int money;
	public MoneyLabel(XYQGame game,int money) {
		super(String.valueOf(money), game.rs.getBlackUILabelStyle());
		this.money=money;
		String moStr=String.valueOf(money);
		if(money>=10000&&money<100000){//蓝色万
			setStyle(game.rs.getDarkBlueLabelStyle(moStr));
		}else if(money>=100000&&money<1000000){//绿色十万
			setStyle(game.rs.getDarkGreenLabelStyle(moStr));
		}else if(money>=1000000&&money<10000000){//红色百万
			setStyle(game.rs.getRedLabelStyle(moStr));
		}else if(money>=10000000&&money<100000000){//紫色千万
			setStyle(game.rs.getPurpleLabelStyle(moStr));
		}else if(money>=100000000){//棕色亿
			setStyle(game.rs.getBrownLabelStyle(moStr));
		}else
			setStyle(game.rs.getBlackUILabelStyle());
		
	}
}
