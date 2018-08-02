package xyq.system.ingame.fishing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

import xyq.game.stage.UI.dialog.FishingDlg;


public enum FishingState implements State<FishingDlg> {
	/**起竿状态*/
	NOFHISHING{
		@Override
		public void enter(FishingDlg entity) {
			entity.myrod.setIndexArea(0, 0);
			entity.myrod.restart();
		}
	
		@Override
		public void update(FishingDlg entity) {
			//entity.getStateMachine().changeState(PersonState.RUN);
		}
	
		@Override
		public void exit(FishingDlg entity) {
			
		}
	
		@Override
		public boolean onMessage(FishingDlg entity, Telegram telegram) {
			return false;
		}
	},
	/**抛竿中*/
	THROWING{
		@Override
		public void enter(FishingDlg entity) {
			entity.myrod.setIndexArea(1, 20);
			entity.myrod.restart();
			entity.xyqgame.cs.Sound_playSE1("./sound/SE/2283施法_扔竿.ogg");
		}
	
		@Override
		public void update(FishingDlg entity) {
			if(entity.myrod.getCurrentIndex()==20)
				entity.fish.logic.changeState(FishingState.WAITING);
		}
	
		@Override
		public void exit(FishingDlg entity) {
			
		}
	
		@Override
		public boolean onMessage(FishingDlg entity, Telegram telegram) {
			return false;
		}
	},
	/**等鱼上钩中*/
	WAITING{
		@Override
		public void enter(FishingDlg entity) {
			entity.myrod.setIndexArea(20, 39);
			entity.myrod.restart();
			entity.fish.makeANewFish();
		}
	
		@Override
		public void update(FishingDlg entity) {
			
		}
	
		@Override
		public void exit(FishingDlg entity) {
			
		}
	
		@Override
		public boolean onMessage(FishingDlg entity, Telegram telegram) {
			return false;
		}
	},
	/**鱼已经上钩*/
	EATTING{
		@Override
		public void enter(FishingDlg entity) {
			entity.myrod.setIndexArea(40, 64);
			entity.myrod.restart();
		}
	
		@Override
		public void update(FishingDlg entity) {
			
		}
	
		@Override
		public void exit(FishingDlg entity) {
			
		}
	
		@Override
		public boolean onMessage(FishingDlg entity, Telegram telegram) {
			return false;
		}
	},
	/**正在拉杆*/
	CATCHING{
		@Override
		public void enter(FishingDlg entity) {
			entity.xyqgame.ts.delTask("钓鱼上鱼事件");
			entity.xyqgame.ts.delTask("钓鱼跑鱼事件");
			entity.xyqgame.ts.delTask("钓鱼提示1");
			entity.xyqgame.ts.delTask("钓鱼提示2");
			entity.xyqgame.ts.delTask("钓鱼提示3");
			entity.xyqgame.ts.delTask("钓鱼提示4");
			entity.xyqgame.ts.delTask("钓鱼提示5");
			if(entity.fish.way==FishingGame.yuhui){
				entity.myrod.setIndexArea(65, 97);
				entity.myrod.restart();
			}else if(entity.fish.way==FishingGame.kuaisu){
				entity.myrod.setIndexArea(88, 97);
				entity.myrod.restart();
			}
			if(entity.xyqgame.is_Debug)
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ] -> 开始收竿");
		}
	
		@Override
		public void update(FishingDlg entity) {
			if(entity.myrod.getCurrentIndex()==97){
				if(entity.xyqgame.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 钓鱼 ] -> 收竿完毕，检查钓上来鱼没有");
				int code=entity.fish.isGotFish();
				if(code==1){
					entity.fish.logic.changeState(FishingState.GOT);
				}else if(code==2){
					entity.fish.logic.changeState(FishingState.GOTSPECIAL);
				}else{
					entity.fish.logic.changeState(FishingState.NOFHISHING);
					if(entity.fish.logic.getCurrentState()!=FishingState.CATCHING){
						entity.xyqgame.cs.UI_showSystemMessage("鱼儿跑掉了");
						entity.fishInfos[entity.pos].addText("鱼儿跑掉了...\n",true);
					}
					else{
						entity.xyqgame.cs.UI_showSystemMessage("机智的鱼儿脱钩逃走了");
						entity.fishInfos[entity.pos].addText("机智的鱼儿脱钩逃走了..\n",true);
					}
				}
			}
		}
	
		@Override
		public void exit(FishingDlg entity) {
			
		}
	
		@Override
		public boolean onMessage(FishingDlg entity, Telegram telegram) {
			return false;
		}
	},/**正在拉杆，但是拉不到鱼*/
	CATCHING_WITH_NOFISH{
		@Override
		public void enter(FishingDlg entity) {
			entity.xyqgame.ts.delTask("钓鱼上鱼事件");
			entity.xyqgame.ts.delTask("钓鱼跑鱼事件");
			entity.xyqgame.ts.delTask("钓鱼提示1");
			entity.xyqgame.ts.delTask("钓鱼提示2");
			entity.xyqgame.ts.delTask("钓鱼提示3");
			entity.xyqgame.ts.delTask("钓鱼提示4");
			entity.xyqgame.ts.delTask("钓鱼提示5");
			if(entity.fish.way==FishingGame.yuhui){
				entity.myrod.setIndexArea(65, 97);
				entity.myrod.restart();
			}else if(entity.fish.way==FishingGame.kuaisu){
				entity.myrod.setIndexArea(88, 97);
				entity.myrod.restart();
			}
			if(entity.xyqgame.is_Debug)
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ] -> 开始收竿");
		}
	
		@Override
		public void update(FishingDlg entity) {
			if(entity.myrod.getCurrentIndex()==97){
				entity.fish.logic.changeState(FishingState.NOFHISHING);
				entity.xyqgame.cs.UI_showSystemMessage("空空的鱼钩，什么也没有");
				entity.fishInfos[entity.pos].addText("空空的鱼钩...\n",true);
			}
		}
	
		@Override
		public void exit(FishingDlg entity) {
			
		}
	
		@Override
		public boolean onMessage(FishingDlg entity, Telegram telegram) {
			return false;
		}
	},/**钓上来了鱼*/
	GOT{
		@Override
		public void enter(FishingDlg entity) {
			if(entity.xyqgame.is_Debug)
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ] -> 钓上了鱼！");
			entity.fish.logic.changeState(FishingState.NOFHISHING);
			entity.xyqgame.cs.Sound_playSE1("./sound/SE/gotfish.ogg");
			entity.fish.harvestFish(entity.fish.aFishNow, "钓上");
			
			
		}
	
		@Override
		public void update(FishingDlg entity) {
			
		}
	
		@Override
		public void exit(FishingDlg entity) {
			
		}
	
		@Override
		public boolean onMessage(FishingDlg entity, Telegram telegram) {
			return false;
		}
	},/**钓上来了特殊事件*/
	GOTSPECIAL{
		@Override
		public void enter(FishingDlg entity) {
			if(entity.xyqgame.is_Debug)
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]->钓上了特殊事件！");
			entity.fish.logic.changeState(FishingState.NOFHISHING);
			entity.fishInfos[entity.pos].addText("好像发现了什么\n",true);
			entity.fish.currentGot++;
			entity.nowGot[entity.pos].setText(entity.fish.currentGot+"");
			entity.fish.callSpecial();
		}
	
		@Override
		public void update(FishingDlg entity) {
			
		}
	
		@Override
		public void exit(FishingDlg entity) {
			
		}
	
		@Override
		public boolean onMessage(FishingDlg entity, Telegram telegram) {
			return false;
		}
	},/**不能钓鱼状态*/
	CANNOTFISH{
		@Override
		public void enter(FishingDlg entity) {
			
		}
	
		@Override
		public void update(FishingDlg entity) {
			
		}
	
		@Override
		public void exit(FishingDlg entity) {
			
		}
	
		@Override
		public boolean onMessage(FishingDlg entity, Telegram telegram) {
			return false;
		}
	}
	
}
