package xyq.system;

import xyq.game.XYQGame;
import xyq.game.data.Player;

public class LogicSystem {
	XYQGame game;
	//玩家移动速度，多少移动一个像素多少秒
	public static float plrMoveSpeed=6f;
	public Player player;
	public int maxLevelLimit=145;
	/**信息获取器，用于获取游戏内的数据方便调用*/
	public Information ifm;
	/**游戏内的事件管理系统，用于控制游戏里的事件，开关，任务*/
	public EventSystem event;
	
	/**当前鼠标状态是否是给予状态*/
	public boolean isGiving;
	/**当前鼠标状态是否是交易状态*/
	public boolean isTrading;
	
	public LogicSystem(XYQGame game,String plrID){
		this.game=game;
		player=new Player(game, plrID);
		ifm=new Information(game);
		event=new EventSystem(game);
	}
	
	
	
	
	
	
	
	
	
	
	public void update(float deltaTime) {
		player.update(deltaTime);
		
	}


	
	
	


}
