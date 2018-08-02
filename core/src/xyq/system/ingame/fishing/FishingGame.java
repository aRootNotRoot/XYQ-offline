package xyq.system.ingame.fishing;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

import xyq.game.data.ItemStackData;
import xyq.game.stage.ChatOption;
import xyq.game.stage.LinkLabelClickAction;
import xyq.game.stage.UI.dialog.FishingDlg;
import xyq.system.ItemDB;
import xyq.system.TimeEventAdapter;
import xyq.system.XYQDataBase;
import xyq.system.utils.RandomUT;

public class FishingGame {

	public StateMachine<FishingDlg, FishingState> logic;
	FishingDlg dlg;
	
	public static final int xiaoli=0;
	public static final int zhongli=1;
	public static final int dali=2;
	
	public static final int zhigan=0;
	public static final int xiegan=1;
	
	public static final int kuaisu=0;
	public static final int yuhui=1;

	public int fishPosition;
	
	final String[] specialTags={"水面上好像有动静","鱼漂上下起浮","水面微波荡漾","鱼钩好象碰到了什么","鱼漂突然沉了下去"};
	final String[] beikeTags={"水面上好像有动静","鱼漂上下起浮","水面微波荡漾","鱼钩好象碰到了什么","鱼竿开始晃动"};
	
	final String[] shadingyuTags={"好象有鱼出现了","鱼漂微微动了一下","水面微波荡漾","鱼钩好象碰到了什么","鱼竿开始下弯了"};
	final String[] duixiaTags={"好象有鱼出现了","鱼漂微微动了一下","水面微波荡漾","鱼钩好象碰到了什么","鱼漂上起下浮"};
	final String[] dahuangyuTags={"好象有鱼出现了","鱼漂微微动了一下","水面微波荡漾","鱼竿开始下弯了","水面溅起浪花"};
	final String[] xiaohuangyuTags={"好象有鱼出现了","鱼漂微微动了一下","水面微波荡漾","鱼竿开始下弯了","鱼漂上起下浮 "};
	final String[] maoxieTags={"鱼竿开始晃动","好象有鱼出现了","鱼钩好象被什么咬住了","水面溅起浪花","鱼竿开始下弯"};
	final String[] jinqiangyuTags={"鱼竿开始晃动","好象有鱼出现了","鱼钩好象被什么咬住了","水面溅起浪花","鱼漂上起下浮"};
	final String[] jiayuTags={"鱼竿开始晃动","好象有鱼出现了","鱼钩好象被什么咬住了","鱼漂突然沉了一下","鱼竿弯得厉害"};
	final String[] haixingTags={"鱼钩好象被什么咬住了","水面溅起浪花","鱼竿开始晃动","鱼漂上下起浮","鱼竿开始下弯"};
	final String[] haimaTags={"鱼钩好象被什么咬住了","水面溅起浪花","鱼竿开始晃动","鱼漂上下起浮","鱼漂突然沉了下去"};
	
	final String[] niqiuTags={"鱼漂微微动了一下","水面微波荡漾","好象有鱼出现了","鱼竿开始下弯了","鱼竿开始晃动"};
	final String[] hexiaTags={"鱼漂微微动了一下","水面微波荡漾","好象有鱼出现了","鱼竿开始下弯了","鱼漂上起下浮"};
	final String[] caoyuTags={"鱼漂微微动了一下","水面微波荡漾","好象有鱼出现了","鱼漂突然沉了一下","鱼漂上起下浮"};
	final String[] hexieTags={"鱼漂微微动了一下","水面微波荡漾","好象有鱼出现了","鱼漂突然沉了一下","鱼竿开始下弯"};
	final String[] jiyuTags={"水面上好像有动静","鱼漂上下起浮","好象有鱼出现了","鱼竿开始下弯了","鱼漂沉下去了"};
	final String[] liyuTags={"水面上好像有动静","鱼漂上下起浮","好象有鱼出现了","鱼竿开始下弯了","鱼钩被什么咬住"};
	final String[] dazhaxieTags={"水面上好像有动静","鱼漂上下起浮，好象有鱼出现了","鱼竿开始下弯了","水面溅起浪花"};
	final String[] wawayuTags={"水面溅起浪花","鱼漂上下起浮","鱼竿开始下弯了","鱼漂突然沉了一下","鱼竿开始晃动"};
	final String[] hetunTags={"水面溅起浪花","鱼漂上下起浮","鱼竿开始下弯了","鱼漂突然沉了一下","鱼竿弯得厉害"};
	
	public ArrayList<FishPool> fishpool_CS=new ArrayList<FishPool>();
	public ArrayList<Integer> fishchance_CS=new ArrayList<Integer>();
	
	public ArrayList<FishPool> fishpool_AL=new ArrayList<FishPool>();
	public ArrayList<Integer> fishchance_AL=new ArrayList<Integer>();
	
	public final int specialChance=3;
	
	public int power=0;
	public int angle=0;
	public int way=0;
	
	public int currentGot=0;
	
	public boolean gaming=false;
	public FishPool aFishNow;
	public  boolean aFishIsSpecial;
	
	/**是否正在使用钓鱼法宝香油*/
	public boolean usingOil;
	/**香油剩余时间*/
	public int oil_left_time;
	

	/**是否正在使用钓鱼法宝鱼鹰*/
	public boolean usingBird;
	TimeEventAdapter birdTask;

	int CZJD_wantCount=1;
	FishPool CZJD_wantFish;
	public FishingGame(FishingDlg dlg,int pos){
		this.dlg=dlg;
		this.fishPosition=pos;
		fishpool_CS.add(new FishPool(XYQDataBase.ITEM_BASIC, 63,xiaoli,zhigan,kuaisu,25,niqiuTags));//泥鳅
		fishpool_CS.add(new FishPool(XYQDataBase.ITEM_BASIC, 64,zhongli,zhigan,kuaisu,25,hexiaTags));//河虾
		fishpool_CS.add(new FishPool(XYQDataBase.ITEM_BASIC, 65,xiaoli,xiegan,kuaisu,15,caoyuTags));//草鱼
		fishpool_CS.add(new FishPool(XYQDataBase.ITEM_BASIC, 66,zhongli,xiegan,kuaisu,15,hexieTags));//河蟹
		fishpool_CS.add(new FishPool(XYQDataBase.ITEM_BASIC, 67,zhongli,zhigan,yuhui,10,jiyuTags));//鲫鱼
		fishpool_CS.add(new FishPool(XYQDataBase.ITEM_BASIC, 68,dali,zhigan,yuhui,5,liyuTags));//鲤鱼
		fishpool_CS.add(new FishPool(XYQDataBase.ITEM_BASIC, 69,zhongli,xiegan,yuhui,6,hetunTags));//河豚
		fishpool_CS.add(new FishPool(XYQDataBase.ITEM_BASIC, 70,zhongli,xiegan,yuhui,5,wawayuTags));//娃娃鱼
		fishpool_CS.add(new FishPool(XYQDataBase.ITEM_BASIC, 71,zhongli,zhigan,kuaisu,4,dazhaxieTags));//大闸蟹
		fishpool_CS.add(new FishPool(XYQDataBase.ITEM_BASIC, 77,xiaoli,zhigan,kuaisu,3,beikeTags));//珍珠
		
		
		
		fishpool_AL.add(new FishPool(XYQDataBase.ITEM_BASIC, 54,zhongli,zhigan,kuaisu,25,duixiaTags));//对虾
		fishpool_AL.add(new FishPool(XYQDataBase.ITEM_BASIC, 55,xiaoli,zhigan,kuaisu,25,shadingyuTags));//沙丁鱼
		fishpool_AL.add(new FishPool(XYQDataBase.ITEM_BASIC, 56,xiaoli,xiegan,kuaisu,13,dahuangyuTags));//大黄鱼
		fishpool_AL.add(new FishPool(XYQDataBase.ITEM_BASIC, 57,zhongli,xiegan,kuaisu,10,xiaohuangyuTags));//小黄鱼
		fishpool_AL.add(new FishPool(XYQDataBase.ITEM_BASIC, 58,dali,zhigan,yuhui,6,jinqiangyuTags));//金枪鱼
		fishpool_AL.add(new FishPool(XYQDataBase.ITEM_BASIC, 59,zhongli,zhigan,yuhui,12,maoxieTags));//毛蟹
		fishpool_AL.add(new FishPool(XYQDataBase.ITEM_BASIC, 60,dali,xiegan,yuhui,5,haixingTags));//海星
		fishpool_AL.add(new FishPool(XYQDataBase.ITEM_BASIC, 61,zhongli,xiegan,yuhui,5,haimaTags));//海马
		fishpool_AL.add(new FishPool(XYQDataBase.ITEM_BASIC, 62,zhongli,zhigan,kuaisu,3,jiayuTags));//甲鱼
		fishpool_AL.add(new FishPool(XYQDataBase.ITEM_BASIC, 77,xiaoli,zhigan,kuaisu,3,beikeTags));//珍珠
		
		clacChance();
	}
	public boolean isNoFishing(){
		return (logic.getCurrentState()==FishingState.NOFHISHING);
	}
	/**根据录入的鱼池数据，计算概率表*/
	void clacChance() {
		int count=0;
		for(int i=0;i<fishpool_CS.size();i++){
			int chance=fishpool_CS.get(i).chanceValue;
			fishchance_CS.add(count+chance);
			count+=chance;
		}
		//fishchance_CS.add(count);
		fishchance_CS.add(count+specialChance);
		if(dlg.xyqgame.is_Debug){
			System.out.println("长寿鱼区 鱼类概率表： ");
			for(int i=0;i<fishchance_CS.size();i++){
				int chance=fishchance_CS.get(i);
				System.out.print(" "+chance);
			}
	
			System.out.print("\n");
		}
		count=0;
		for(int i=0;i<fishpool_AL.size();i++){
			int chance=fishpool_AL.get(i).chanceValue;
			fishchance_AL.add(count+chance);
			count+=chance;
		}
		//fishchance_AL.add(count);
		fishchance_AL.add(count+specialChance);
		if(dlg.xyqgame.is_Debug){
			System.out.println("傲来鱼区 鱼类概率表： ");
			for(int i=0;i<fishchance_AL.size();i++){
				int chance=fishchance_AL.get(i);
				System.out.print(" "+chance);
			}
	
			System.out.print("\n");
		}
	}
	/**开始一个新的钓鱼游戏*/
	public void newGame(){
		logic = new DefaultStateMachine<FishingDlg, FishingState>(dlg, FishingState.NOFHISHING);
		logic.changeState(FishingState.NOFHISHING);
		gaming=true;
		currentGot=0;
		usingOil=false;
		oil_left_time=0;
		dlg.nowGot[dlg.pos].setText(currentGot+"");
		
		dlg.birdIcon.setVisible(false);
		usingBird=false;
		
		clearTimeTask();
	}
	public void stopGame(){
		gaming=false;
		usingOil=false;
		oil_left_time=0;
		dlg.nowGot[dlg.pos].setText(currentGot+"");
		dlg.birdIcon.setVisible(false);
		usingBird=false;
		clearTimeTask();
	}
	public void updateGame(){
		if(!gaming)
			return;
		logic.update();
	}
	/**抛竿*/
	public void throwRod(){
		if(!gaming){
			if(dlg.xyqgame.is_Debug)
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]->无法抛竿，游戏没有开始");
			return;
		}
		if(dlg.lightDlg.isVisible()){
			if(dlg.xyqgame.is_Debug)
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]->无法抛竿，正在和鱼奋战");
			return;
		}
		if(logic.getCurrentState()==FishingState.WAITING){
			logic.changeState(FishingState.CATCHING_WITH_NOFISH);
		}else if(logic.getCurrentState()==FishingState.EATTING){
			logic.changeState(FishingState.CATCHING);
		}else if(logic.getCurrentState()==FishingState.NOFHISHING){
			logic.changeState(FishingState.THROWING);
		}else{
			if(dlg.xyqgame.is_Debug)
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]->无法抛竿或者收杆，因为当前不是起竿状态，等鱼状态或者上鱼状态");
		}
		
	}
	public void toggolePower() {
		power++;
		if(power>2)
			power=xiaoli;
	}
	public void toggoleAngle() {
		if(angle==zhigan)
			angle=xiegan;
		else 
			angle=zhigan;
	}
	public void toggoleWay() {
		if(way==kuaisu)
			way=yuhui;
		else 
			way=kuaisu;
	}
	/**生成一个鱼*/
	public void makeANewFish() {
		
		int shot=-1;
		aFishIsSpecial=false;
		if(dlg.where.equals("长寿村")){
			shot=RandomUT.probabilityShot(fishchance_CS);
			if(shot>=fishpool_CS.size())
				aFishIsSpecial=true;
			else
				aFishNow=fishpool_CS.get(shot);
		}else{
			shot=RandomUT.probabilityShot(fishchance_AL);
			if(shot>=fishpool_AL.size())
				aFishIsSpecial=true;
			else
				aFishNow=fishpool_AL.get(shot);
		}
		
		int time =RandomUT.getRandom(3000, 180000);
		if(usingOil){
			if(dlg.xyqgame.is_Debug)
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]-> 生成了一条鱼:本来鱼将在"+time+"毫秒后上钩");
			time=(int)((float)time*0.75f);
			if(dlg.xyqgame.is_Debug)
				Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]-> 因为香油，鱼将在"+time+"毫秒后上钩");
		}
		if(aFishIsSpecial&&dlg.xyqgame.is_Debug){
			Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]-> 生成了一条鱼:会是一个特殊事件，"+time+"毫秒后出现");
		}
		else if(dlg.xyqgame.is_Debug){
			String fishName=dlg.xyqgame.itemDB.getItemName(aFishNow.type,aFishNow.id);
			Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]-> 生成了一条鱼:"+aFishNow.type+"&"+aFishNow.id+" | 鱼将在"+time+"毫秒后上钩");
			Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]-> 生成的这条鱼是【"+fishName+"】上鱼方式应该是"+aFishNow.getWay());
		}
		dlg.xyqgame.ts.addTask("钓鱼上鱼事件", time, new TimeEventAdapter(){
			@Override
			public void act(){
				gotFish();
			}
		});
		
		dlg.xyqgame.ts.addTask("钓鱼提示1", time, new TimeEventAdapter(){
			@Override
			public void act(){
				Gdx.app.log("[ XYQ ]", "[ 钓鱼提示 ] ->"+aFishNow.catchTags[0]);
				dlg.fishInfos[fishPosition].addText(aFishNow.catchTags[0]+"\n",true);
				}
		});
		dlg.xyqgame.ts.addTask("钓鱼提示2", time+2000, new TimeEventAdapter(){
			@Override
			public void act(){
				Gdx.app.log("[ XYQ ]", "[ 钓鱼提示 ] ->"+aFishNow.catchTags[1]);
				dlg.fishInfos[fishPosition].addText(aFishNow.catchTags[1]+"\n",true);
				
			}
		});
		dlg.xyqgame.ts.addTask("钓鱼提示3", time+4000, new TimeEventAdapter(){
			@Override
			public void act(){
				Gdx.app.log("[ XYQ ]", "[ 钓鱼提示 ] ->"+aFishNow.catchTags[2]);
				dlg.fishInfos[fishPosition].addText(aFishNow.catchTags[2]+"\n",true);
				
			}
		});
		dlg.xyqgame.ts.addTask("钓鱼提示4", time+6000, new TimeEventAdapter(){
			@Override
			public void act(){
				Gdx.app.log("[ XYQ ]", "[ 钓鱼提示 ] ->"+aFishNow.catchTags[3]);	
				dlg.fishInfos[fishPosition].addText(aFishNow.catchTags[3]+"\n",true);
				
			}
		});
		dlg.xyqgame.ts.addTask("钓鱼提示5", time+8000, new TimeEventAdapter(){
			@Override
			public void act(){
				Gdx.app.log("[ XYQ ]", "[ 钓鱼提示 ] ->"+aFishNow.catchTags[4]);
				dlg.fishInfos[fishPosition].addText(aFishNow.catchTags[4]+"\n",true);
			}
		});
		
		if(aFishIsSpecial){
			aFishNow=fishpool_AL.get(2);
		}
	}
	
	public void gotFish() {
		logic.changeState(FishingState.EATTING);
		int time =RandomUT.getRandom(13000, 28000);
		if(!aFishIsSpecial)
			Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]->鱼上钩啦！当前的鱼："+aFishNow.type+"&"+aFishNow.id+" | 鱼将在"+time+"毫秒后脱钩");
		else
			Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]->鱼上钩啦！当前的是特殊事件 | 将在"+time+"毫秒后脱钩");
		
		dlg.xyqgame.ts.addTask("钓鱼跑鱼事件", time, new TimeEventAdapter(){
			@Override
			public void act(){
				lostFish();
			}
		});
	}
	public void lostFish() {
		logic.changeState(FishingState.NOFHISHING);
		Gdx.app.log("[ 钓鱼 ]", "[ 钓鱼 ]->鱼跑啦");
		dlg.xyqgame.cs.UI_showSystemMessage("鱼儿逃跑了");
		dlg.fishInfos[fishPosition].addText("鱼儿逃跑了"+"\n",true);
		
	}
	/**是否钓上了鱼
	 * @return 0-未上鱼，1-上鱼，2-特殊事件
	 * */
	public int isGotFish() {
		if(logic.getCurrentState()!=FishingState.CATCHING)
			return 0;
		if(dlg.xyqgame.is_Debug){
			StringBuilder sBuilder=new StringBuilder();
			sBuilder.append("【");
			if(power==FishingGame.xiaoli){
				sBuilder.append("小力");sBuilder.append(",");
			}else if(power==FishingGame.zhongli){
				sBuilder.append("中力");sBuilder.append(",");
			}else if(power==FishingGame.dali){
				sBuilder.append("大力");sBuilder.append(",");
			}
			
			if(angle==FishingGame.zhigan){
				sBuilder.append("直竿");sBuilder.append(",");
			}else if(angle==FishingGame.xiegan){
				sBuilder.append("斜竿");sBuilder.append(",");
			}
			
			if(way==FishingGame.kuaisu){
				sBuilder.append("快速");
			}else if(way==FishingGame.yuhui){
				sBuilder.append("迂回");
			}
			
			sBuilder.append("】");
			Gdx.app.log("[ XYQ ]", "[ 钓鱼 ] ->当前钓鱼的参数为"+sBuilder.toString());
		}
		if(aFishIsSpecial){
			if(FishingGame.xiaoli==power&&FishingGame.kuaisu==way&&FishingGame.xiegan==angle){//如果当前钓鱼方式正确
				if(isLostFish())//正确的话有20分之1的概率跑掉
					return 0;
				else
					return 2;//其余19/20的概率上特殊事件
			}else{//钓鱼方式不正确，仍有1/35的概率钓上鱼
				if(RandomUT.isGot(35))
				return 2;
			}
		}else if(aFishNow.power==power&&aFishNow.way==way&&aFishNow.angle==angle){//如果当前钓鱼方式正确
			if(isLostFish())//正确的话有20分之1的概率跑掉
				return 0;
			else
				return 1;//其余19/20的概率上鱼
		}else{//钓鱼方式不正确，仍有1/35的概率钓上鱼
			if(RandomUT.isGot(35))
			return 1;
		}
		return 0;
	}
	/**是否让鱼跑了*/
	public boolean isLostFish(){
		return RandomUT.isGot(20);
	}
	/**使用鱼鹰
	 * @return true如果使用成功
	 * */
	public boolean useBird() {
		if(usingBird){
			dlg.xyqgame.cs.UI_showSystemMessage("鱼鹰太多了不好");
			return false;
		}
		dlg.xyqgame.ts.delTask("钓鱼法宝鱼鹰事件");
		usingBird=true;
		dlg.birdIcon.setVisible(true);
		int birdtime=RandomUT.getRandom(50000,70000);
		birdTask = new TimeEventAdapter(){
			@Override
			public void act(){
				if(!usingBird)
					return;
				boolean isDead=RandomUT.isGot(5);
				if(isDead){
					usingBird=false;
					dlg.birdIcon.setVisible(false);
					dlg.xyqgame.cs.UI_showSystemMessage("鱼鹰飞走了");
					dlg.fishInfos[dlg.pos].addText("鱼鹰飞走了\n",true);
					
				}else{
					int shot=-1;
					boolean badLuck=false;
					FishPool aFish = null;
					if(dlg.where.equals("长寿村")){
						shot=RandomUT.probabilityShot(fishchance_CS);
						if(shot>=fishpool_CS.size())
							badLuck=true;
						else
							aFish=fishpool_CS.get(shot);
					}else{
						shot=RandomUT.probabilityShot(fishchance_AL);
						if(shot>=fishpool_AL.size())
							badLuck=true;
						else
							aFish=fishpool_AL.get(shot);
					}
					if(badLuck){
						dlg.fishInfos[dlg.pos].addText("鱼鹰悄悄把鱼吃掉了\n", true);
					}else{
						harvestFish(aFish,"鱼鹰抓到了一个",false);
					}
				}
			}
		};
		dlg.xyqgame.ts.addCycleTask("钓鱼法宝鱼鹰事件", birdtime,birdTask);
		if(dlg.xyqgame.is_Debug){
			Gdx.app.log("[ XYQ ]", "[ 钓鱼 ] ->使用了鱼鹰");
			Gdx.app.log("[ XYQ ]", "[ 钓鱼 ]->鱼鹰将以"+birdtime+"毫秒间隔去抓鱼");
		}
		return true;
	}
	public void useGig() {
		int shot=-1;
		boolean badLuck=false;
		FishPool aFish = null;
		if(dlg.where.equals("长寿村")){
			shot=RandomUT.probabilityShot(fishchance_CS);
			if(shot>=fishpool_CS.size())
				badLuck=true;
			else
				aFish=fishpool_CS.get(shot);
		}else{
			shot=RandomUT.probabilityShot(fishchance_AL);
			if(shot>=fishpool_AL.size())
				badLuck=true;
			else
				aFish=fishpool_AL.get(shot);
		}
		if(badLuck){
			dlg.fishInfos[dlg.pos].addText("哎呀,鱼叉一滑掉进了水里\n", true);
		}else{
			aFishNow=aFish;
			harvestFish(aFish,"叉到个",false);
		}
	}
	public void useNet() {
		int shot=-1;
		boolean badLuck=false;
		FishPool aFish = null;
		int count=RandomUT.getRandom(1, 5);
		if(dlg.where.equals("长寿村")){
			shot=RandomUT.probabilityShot(fishchance_CS);
			if(shot>=fishpool_CS.size())
				badLuck=true;
			else
				aFish=fishpool_CS.get(shot);
		}else{
			shot=RandomUT.probabilityShot(fishchance_AL);
			if(shot>=fishpool_AL.size())
				badLuck=true;
			else
				aFish=fishpool_AL.get(shot);
		}
		if(badLuck){
			dlg.fishInfos[dlg.pos].addText("渔网挂到石头损坏了\n", true);
		}else{
			aFishNow=aFish;
			harvestFish(aFish,"网到"+count+"个",count);
		}
		
	}
	public boolean useOil() {
		if(!gaming)
			return false;
		if(usingOil){
			dlg.xyqgame.cs.UI_showSystemMessage("当前已经有香油生效了");
			return false;
		}
		oil_left_time=RandomUT.getRandom(300000, 600000);
		usingOil=true;
		dlg.xyqgame.ts.addTask("钓鱼法宝香油到期", oil_left_time, new TimeEventAdapter(){
			@Override
			public void act(){
				usingOil=false;
				oil_left_time=0;
				dlg.xyqgame.cs.UI_showSystemMessage("香油已经失效了");
				dlg.fishInfos[dlg.pos].addText("香油已经失效了\n",true);
			}
		});
		if(dlg.xyqgame.is_Debug){
			Gdx.app.log("[ XYQ ]", "[ 钓鱼 ] ->使用了香油，将在"+oil_left_time+"毫秒后失效");
		}
		return true;
	}
	/**收获一条鱼，显示对话框*/
	public void harvestFish(FishPool fish,String gotWay){
		harvestFish(fish,gotWay,true);
	}
	/**收获一条鱼，显示对话框*/
	public void harvestFish(FishPool fish,String gotWay,boolean showDlg){

		String fishName=dlg.xyqgame.itemDB.getItemName(fish.type,fish.id);
		dlg.fishInfos[dlg.pos].addText(gotWay+"["+fishName+"]\n",true);
		dlg.fish.currentGot++;
		dlg.xyqgame.ls.player.playerData().moneys[5]++;
		dlg.nowGot[dlg.pos].setText(dlg.fish.currentGot+"");
		if(showDlg){
			dlg.gotDlg.setFish(fish);
			dlg.gotDlg.setVisible(showDlg);
		}else{
			if(gotWay.equals("钓上"))
				putFishIn(fish,1,true);
			else
				putFishIn(fish,1,false);
		}
		
	}
	/**收获多条鱼，显示对话框*/
	public void harvestFish(FishPool fish,String gotWay,int count){

		String fishName=dlg.xyqgame.itemDB.getItemName(fish.type,fish.id);
		int maxCount=dlg.xyqgame.itemDB.getMaxCount( fish.type, fish.id);
		if(count>maxCount)
			count=maxCount;
		dlg.fishInfos[dlg.pos].addText(gotWay+"["+fishName+"]\n",true);
		dlg.fish.currentGot+=count;
		dlg.xyqgame.ls.player.playerData().moneys[5]+=count;
		dlg.nowGot[dlg.pos].setText(dlg.fish.currentGot+"");
		if(gotWay.equals("钓上"))
			putFishIn(fish,count,true);
		else
			putFishIn(fish,count,false);
		
	}
	public void putFishIn(FishPool fish,int count,boolean callEvent){
		//nowFish
		int index=dlg.xyqgame.ls.ifm.PLAYER_getPlrExistISDIndex(fish.type, fish.id);
		if(index==-1){
			dlg.xyqgame.cs.UI_showSystemMessage("糟糕！背包满了！");
			return;
		}
		if(fish.type==XYQDataBase.ITEM_BASIC){
			ItemStackData item =dlg.xyqgame.itemDB.makeBasicItem(index, fish.type, fish.id, count);
			dlg.xyqgame.cs.ACT_addItem(item);
		}
		if(callEvent)
			callEvent();
		
	}
	public void clearTimeTask(){
		dlg.xyqgame.ts.delTask("钓鱼上鱼事件");
		dlg.xyqgame.ts.delTask("钓鱼跑鱼事件");
		dlg.xyqgame.ts.delTask("钓鱼提示1");
		dlg.xyqgame.ts.delTask("钓鱼提示2");
		dlg.xyqgame.ts.delTask("钓鱼提示3");
		dlg.xyqgame.ts.delTask("钓鱼提示4");
		dlg.xyqgame.ts.delTask("钓鱼提示5");

		dlg.xyqgame.ts.delTask("钓鱼法宝香油到期");
		dlg.xyqgame.ts.delTask("钓鱼法宝鱼鹰事件");
	}
	public void callEvent(){
		if(RandomUT.isGot(13)){//有1/13的概率遇到特殊事件，乞丐，财主家丁，宫廷吏使，大嘴熊，海盗，老渔翁	
			int m=RandomUT.getRandom(1, 6);
			if(m==1){
				dlg.fishInfos[dlg.pos].addText("遇到了乞丐\n",true);
				callBeggar();
			}else if(m==2){
				dlg.fishInfos[dlg.pos].addText("遇到了财主家丁\n",true);
				callServer();
			}else if(m==3){
				dlg.fishInfos[dlg.pos].addText("遇到了宫廷吏使\n",true);
				callServerPlus();
			}else if(m==4){
				dlg.fishInfos[dlg.pos].addText("遇到了大嘴熊\n",true);
				callDaZuiXiong();
			}else if(m==5){
				dlg.fishInfos[dlg.pos].addText("遇到了海盗\n",true);
				callPirate();
			}else if(m==6){
				dlg.fishInfos[dlg.pos].addText("遇到了老渔翁\n",true);
				callFishMan();
			}
			
		}
	}
	/**乞丐出现对话框*/
	public void callBeggar(){
		if(aFishNow==null){
			return;
		}
		 ChatOption[] opts=new ChatOption[2];
	        opts[0]=new ChatOption("给你吧",new LinkLabelClickAction() {
					@Override
					public void click() {
						if(dlg.xyqgame.cs.ACT_delItem(aFishNow.type,aFishNow.id,1)){
							dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\乞丐","谢谢你，你真是好人");
							ItemStackData itemStackData=dlg.xyqgame.itemDB.makeSimpleItem(ItemDB.ITEM_BASIC, 3, 1);
							dlg.xyqgame.cs.ACT_addItem(itemStackData);
						}else{
							dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\乞丐","你明明就没有,何必骗我呢");
						}
					}
	        });
	        opts[1]=new ChatOption("不行",new LinkLabelClickAction() {
					@Override
					public void click() {
						dlg.xyqgame.cs.UI_hideChatDialog();
					}
	       });
	       dlg.xyqgame.cs.UI_showChatDialogOptions("wzife.wdf","人物头像\\乞丐","好心人请行行好,我好几天没饭吃了,这个可以给我吗?",opts);
	}
	/**财主家丁出现对话框*/
	public void callServer(){

		 ChatOption[] opts=new ChatOption[2];
	        opts[0]=new ChatOption("有",new LinkLabelClickAction() {
					@Override
					public void click() {
						if(dlg.xyqgame.cs.ACT_delItem(CZJD_wantFish.type,CZJD_wantFish.id,CZJD_wantCount)){
					        String name=dlg.xyqgame.itemDB.getItemName(CZJD_wantFish.type, CZJD_wantFish.id);
					        int money=RandomUT.getRandom(2000, 9000)*CZJD_wantCount;
							dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\店小二","哈,果然新鲜！你卖给我"+CZJD_wantCount+"个"+name+",这是给你的银两"+money);
							dlg.xyqgame.cs.ACT_MoneyUp(money);
						}else{
							dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\店小二","没有那么多啊....");
						}
					}
	        });
	        opts[1]=new ChatOption("没有",new LinkLabelClickAction() {
					@Override
					public void click() {
						dlg.xyqgame.cs.UI_hideChatDialog();
					}
	       });
	        CZJD_wantCount=RandomUT.getRandom(1, 5);
	        int shot=0;
	        if(dlg.where.equals("长寿村")){
				shot=RandomUT.probabilityShot(fishchance_CS);
				if(shot>=fishpool_CS.size())
					CZJD_wantFish=fishpool_CS.get(0);
				else
					CZJD_wantFish=fishpool_CS.get(shot);
			}else{
				shot=RandomUT.probabilityShot(fishchance_AL);
				if(shot>=fishpool_AL.size())
					CZJD_wantFish=fishpool_AL.get(0);
				else
					CZJD_wantFish=fishpool_AL.get(shot);
			}
	        String name=dlg.xyqgame.itemDB.getItemName(CZJD_wantFish.type, CZJD_wantFish.id);
	       dlg.xyqgame.cs.UI_showChatDialogOptions("wzife.wdf","人物头像\\店小二","我家主人今天要举办宴席,需要"+CZJD_wantCount+"个新鲜的"+name+",有卖吗?",opts);
	}
	/**财主家丁出现对话框*/
	public void callServerPlus(){

		 ChatOption[] opts=new ChatOption[2];
	        opts[0]=new ChatOption("有",new LinkLabelClickAction() {
					@Override
					public void click() {
						if(dlg.xyqgame.cs.ACT_delItem(CZJD_wantFish.type,CZJD_wantFish.id,CZJD_wantCount)){
					        String name=dlg.xyqgame.itemDB.getItemName(CZJD_wantFish.type, CZJD_wantFish.id);
					        int money=RandomUT.getRandom(3000, 14000)*CZJD_wantCount;
							dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\男大胡子","嗯....招待用还可以，收你"+CZJD_wantCount+"个"+name+",这是给你的银两"+money);
							dlg.xyqgame.cs.ACT_MoneyUp(money);
						}else{
							dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\男大胡子","才那么点,不够....");
						}
					}
	        });
	        opts[1]=new ChatOption("没有",new LinkLabelClickAction() {
					@Override
					public void click() {
						dlg.xyqgame.cs.UI_hideChatDialog();
					}
	       });
	        CZJD_wantCount=RandomUT.getRandom(1, 5);
	        int shot=0;
	        if(dlg.where.equals("长寿村")){
				shot=RandomUT.probabilityShot(fishchance_CS);
				if(shot>=fishpool_CS.size())
					CZJD_wantFish=fishpool_CS.get(0);
				else
					CZJD_wantFish=fishpool_CS.get(shot);
			}else{
				shot=RandomUT.probabilityShot(fishchance_AL);
				if(shot>=fishpool_AL.size())
					CZJD_wantFish=fishpool_AL.get(0);
				else
					CZJD_wantFish=fishpool_AL.get(shot);
			}
	        String name=dlg.xyqgame.itemDB.getItemName(CZJD_wantFish.type, CZJD_wantFish.id);
	       dlg.xyqgame.cs.UI_showChatDialogOptions("wzife.wdf","人物头像\\男大胡子","喂!打鱼的!我们老爷需要"+CZJD_wantCount+"个新鲜的"+name+",你有没有?",opts);
	}
	/**海盗出现对话框*/
	public void callPirate(){

		 ChatOption[] opts=new ChatOption[2];
	        opts[0]=new ChatOption("玩一局，你先请",new LinkLabelClickAction() {
					@Override
					public void click() {
						int mine=RandomUT.getRandom(1, 6);
						int him=RandomUT.getRandom(1, 6);
						if(him>=mine){//输了
							dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\强盗","真遗憾，你的点数"+mine+"没我的"+him+"大,就不能送你礼物啦。");
						      
						}else{//赢了
							int left=mine-him;
							int shot=0;
							FishPool fishPool;
						        if(dlg.where.equals("长寿村")){
									shot=RandomUT.probabilityShot(fishchance_CS);
									if(shot>=fishpool_CS.size())
										fishPool=fishpool_CS.get(0);
									else
										fishPool=fishpool_CS.get(shot);
								}else{
									shot=RandomUT.probabilityShot(fishchance_AL);
									if(shot>=fishpool_AL.size())
										fishPool=fishpool_AL.get(0);
									else
										fishPool=fishpool_AL.get(shot);
								}
						      String name=dlg.xyqgame.itemDB.getItemName(fishPool.type, fishPool.id);
						      ItemStackData item=dlg.xyqgame.itemDB.makeSimpleItem(fishPool.type, fishPool.id, left);
						      dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\强盗","你掷出"+mine+"点，运气不错啊，比我的"+him+"点高出"+left+"点，那就送你"+left+"个"+name+"吧。");
						      dlg.xyqgame.cs.ACT_addItem(item);
						}

					}
	        });
	        opts[1]=new ChatOption("我没兴趣",new LinkLabelClickAction() {
					@Override
					public void click() {
						dlg.xyqgame.cs.UI_hideChatDialog();
					}
	       });
	       dlg.xyqgame.cs.UI_showChatDialogOptions("wzife.wdf","人物头像\\强盗","哈哈伙计,一直钓鱼多无聊啊.来陪我玩一会比点数的游戏吧。来来来，我只是想找个人打发时间。即使你输了我也不要你东西，赌博不提倡啊。如果你赢了，我送一些小礼物给你，怎么样？嘿嘿",opts);
	}
	/**大嘴熊出现对话框*/
	public void callDaZuiXiong(){
		if(aFishNow==null){
			return;
		}
		 ChatOption[] opts=new ChatOption[2];
	        opts[0]=new ChatOption("给你吧",new LinkLabelClickAction() {
					@Override
					public void click() {
						/*
						String name=dlg.xyqgame.itemDB.getItemName(aFishNow.type,aFishNow.id);
						System.err.println(name);
						*/
						if(dlg.xyqgame.cs.ACT_delItem(aFishNow.type,aFishNow.id,1)){
							boolean giveSth=RandomUT.isGot(3);
							if(giveSth){
								int[][] pool={
										{ItemDB.ITEM_BASIC,81},
										{ItemDB.ITEM_BASIC,82},
										{ItemDB.ITEM_BASIC,83},
										{ItemDB.ITEM_BASIC,84},
										{ItemDB.ITEM_BASIC,85},
										{ItemDB.ITEM_BASIC,86},
										{ItemDB.ITEM_BASIC,87},
										{ItemDB.ITEM_BASIC,88},
										{ItemDB.ITEM_BASIC,89},
										{ItemDB.ITEM_BASIC,91},
										{ItemDB.ITEM_BASIC,94},
										{ItemDB.ITEM_BASIC,21},
										{ItemDB.ITEM_BASIC,22},
										{ItemDB.ITEM_BASIC,23},
										{ItemDB.ITEM_BASIC,24},
										{ItemDB.ITEM_BASIC,25},
										{ItemDB.ITEM_BASIC,26},
										{ItemDB.ITEM_BASIC,27},
										{ItemDB.ITEM_BASIC,28},
										{ItemDB.ITEM_BASIC,29},
										{ItemDB.ITEM_BASIC,30},
										{ItemDB.ITEM_BASIC,31},
										{ItemDB.ITEM_BASIC,32},
										{ItemDB.ITEM_BASIC,33},
										{ItemDB.ITEM_BASIC,34},
										{ItemDB.ITEM_BASIC,35},
										{ItemDB.ITEM_BASIC,36},
										{ItemDB.ITEM_BASIC,37},
										{ItemDB.ITEM_BASIC,38},
										{ItemDB.ITEM_BASIC,39},
										{ItemDB.ITEM_BASIC,40}
								};
								if(RandomUT.isGot(30)){
									dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\白熊","真好，我在田里找到了一张破纸，就送给你吧");
									ItemStackData item=dlg.xyqgame.itemDB.makeTreasureMap(-1);
									dlg.xyqgame.cs.ACT_addItem(item);
								}else{
									int whichOne=RandomUT.getRandom(0, pool.length-1);
									String name=dlg.xyqgame.itemDB.getItemName(pool[whichOne][0], pool[whichOne][1]);
									dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\白熊","真好，我在田里找到了"+name+"，就送给你吧");
									ItemStackData item=dlg.xyqgame.itemDB.makeSimpleItem(pool[whichOne][0], pool[whichOne][1], 1);
									dlg.xyqgame.cs.ACT_addItem(item);
								}
								
							}else{
								int money=RandomUT.getRandom(3000, 10000);
								dlg.xyqgame.cs.ACT_MoneyUp(money);
								dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\白熊","真好，我在田里找到了银两"+money+"，就送给你吧");
								
							}
							return;
						}else{
							dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\白熊","东西呢?东西呢?");
						}
					}
	        });
	        opts[1]=new ChatOption("不行",new LinkLabelClickAction() {
					@Override
					public void click() {

						dlg.xyqgame.cs.UI_hideChatDialog();
					}
	       });
	       dlg.xyqgame.cs.UI_showChatDialogOptions("wzife.wdf","人物头像\\白熊","啊，好美味，钓到的这个可以给我吗?",opts);
	}
	/**老渔翁出现*/
	public void callFishMan(){
		String[] toolsName={"鱼鹰","鱼叉","渔网","香油"};
		int t=RandomUT.getRandom(0, 3);
		dlg.xyqgame.cs.UI_showChatDialog("wzife.wdf","人物头像\\老渔翁","年轻人，我是常年在这一带垂钓的老渔翁。看你的钓鱼本领不错，这里有个"+toolsName[t]+"送给你吧，对你捕鱼会有帮助的");
		
		int toolLeft=dlg.xyqgame.ls.player.playerData().moneys[t+7];
		toolLeft++;
		if(t==0){
			dlg.xyqgame.ls.player.playerData().moneys[t+7]=toolLeft;
			dlg.toolDlg.linkLabels[t].setText("鱼鹰    "+toolLeft);
		}else if(t==1){
			dlg.xyqgame.ls.player.playerData().moneys[t+7]=toolLeft;
			dlg.toolDlg.linkLabels[t].setText("鱼叉    "+toolLeft);
		}else if(t==2){
			dlg.xyqgame.ls.player.playerData().moneys[t+7]=toolLeft;
			dlg.toolDlg.linkLabels[t].setText("渔网    "+toolLeft);
		}else if(t==3){
			dlg.xyqgame.ls.player.playerData().moneys[t+7]=toolLeft;
			dlg.toolDlg.linkLabels[t].setText("香油    "+toolLeft);
		}
	}
	/**钓上来神秘事件，可能是破箱子，可能是美人鱼，可能是其他*/
	public void callSpecial() {
		int ran=RandomUT.getRandom(1, 2);
		if(ran==1){
			dlg.lightDlg.setVisible(true);
			dlg.lightDlg.newGame();
			return;
		}
		 ChatOption[] opts=new ChatOption[2];
	        opts[0]=new ChatOption("打开",new LinkLabelClickAction() {
					@Override
					public void click() {
						if(dlg.xyqgame.cs.ACT_TiliDown(30)){
							if(RandomUT.isGot(4)){
								dlg.xyqgame.cs.UI_showChatDialog("","","箱子里的味道吸引来了一个老人");
								callFishMan();
							}else{
								int[][] pool={
										{ItemDB.ITEM_BASIC,81},
										{ItemDB.ITEM_BASIC,82},
										{ItemDB.ITEM_BASIC,83},
										{ItemDB.ITEM_BASIC,84},
										{ItemDB.ITEM_BASIC,87},
										{ItemDB.ITEM_BASIC,91},
										{ItemDB.ITEM_BASIC,92},
										{ItemDB.ITEM_BASIC,94},
										{ItemDB.ITEM_BASIC,88}
								};
								if(RandomUT.isGot(4)){
									dlg.xyqgame.cs.UI_showChatDialog("","","在箱子里发现一张藏宝图");
									ItemStackData item=dlg.xyqgame.itemDB.makeTreasureMap(-1);
									dlg.xyqgame.cs.ACT_addItem(item);
								}else{
									int whichOne=RandomUT.getRandom(0, pool.length-1);
									String name=dlg.xyqgame.itemDB.getItemName(pool[whichOne][0], pool[whichOne][1]);
									dlg.xyqgame.cs.UI_showChatDialog("","","在箱子里发现了一个"+name);
									ItemStackData item=dlg.xyqgame.itemDB.makeSimpleItem(pool[whichOne][0], pool[whichOne][1], 1);
									dlg.xyqgame.cs.ACT_addItem(item);
								}
							}
						}else{
							dlg.xyqgame.cs.UI_showChatDialog("","","你的体力不足以打开它");
						}
						
					}
	        });
	        opts[1]=new ChatOption("丢弃",new LinkLabelClickAction() {
					@Override
					public void click() {
						dlg.xyqgame.cs.UI_hideChatDialog();
					}
	       });
	       dlg.xyqgame.cs.UI_showChatDialogOptions("","","发现一个破箱子,看起来打开它需要点力气。要打开它吗?",opts);
	
	}
}
