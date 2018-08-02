package xyq.system;

import xyq.game.XYQGame;

public class CameraControl {
	/**触发镜头移动的位置：右x*/
	final int ACTRIGHTX=762;
	final int ACTLEFTX=262;
	/**触发镜头移动的位置：上y*/
	final int ACTUPX=504;
	final int ACTDOWNX=264;
	
	XYQGame game;
	boolean moveToPlayerHor=false;
	boolean moveToPlayerVer=false;
	
	boolean lock=false;
	
	public CameraControl(XYQGame game){
		this.game=game;
	}
	public void forzen(){
		lock=true;
	}
	public void deforzen(){
		lock=false;
	}
	public void update(float dt) {
		if(lock)
			return;
		//镜头移动，如果太靠外了，2倍速移动，如果稍近，1倍速移动
		//不知道盯久了会不会晕
		float plrX=game.ls.player.actor.getX()+game.maps.map().getX();
		float plrY=game.ls.player.actor.getY()+game.maps.map().getY();
		
		if(moveToPlayerHor){
			if(plrX>732)
				game.maps.map().mapMoveRIGHTBig();
			else if(plrX>532)
				game.maps.map().mapMoveRIGHT();
			else if(plrX<292)
				game.maps.map().mapMoveLEFTBig();
			else if(plrX<492)
				game.maps.map().mapMoveLEFT();
			if(plrX<532&&plrX>492)
				moveToPlayerHor=false;
		}else{
		
			if(plrX>ACTRIGHTX)
				moveToPlayerHor=true;
			else if(plrX<ACTLEFTX)
				moveToPlayerHor=true;
			
		}
		
		if(moveToPlayerVer){
			if(plrY>454)
				game.maps.map().mapMoveUPBig();
			else if(plrY>404)
				game.maps.map().mapMoveUP();
			else if(plrY<314)
				game.maps.map().mapMoveDOWNBig();
			else if(plrY<364)
				game.maps.map().mapMoveDOWN();
			
			if(plrY<404&&plrY>364)
				moveToPlayerVer=false;
		}else{
			if(plrY>ACTUPX)
				moveToPlayerVer=true;
			else if(plrY<ACTDOWNX)
				moveToPlayerVer=true;
			
		}
		
	}
}
