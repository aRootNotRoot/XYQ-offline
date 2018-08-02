package xyq.system;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;

import xyq.game.XYQGame;
import xyq.game.data.BattleData;
import xyq.game.data.ItemStackData;
import xyq.game.data.MagicData;
import xyq.game.data.NPC;
import xyq.game.data.Player;
import xyq.game.data.PlayerData;
import xyq.game.data.ShapeData;
import xyq.game.data.Summon;
import xyq.game.data.SummonData;
import xyq.game.stage.ChatOption;
import xyq.game.stage.LinkLabelClickAction;
import xyq.game.stage.UI.DialogHud;
import xyq.game.stage.UI.dialog.PlayerInfoDlg;
import xyq.game.stage.UI.dialog.SummonZiZhiDlg;
import xyq.system.ai.MapGraphPath;
import xyq.system.bussiness.GoodsData;
import xyq.system.bussiness.ShopData;
import xyq.system.utils.RandomUT;

public class CommandSystem {
	XYQGame game;
	public FunctionDataBase funcDB;
	public CommandSystem(XYQGame game){
		this.game=game;
		funcDB=new FunctionDataBase(game);
	}
	public void MAP_SwitchMap(int toMapID,int toX,int toY){
		if(!game.maps.isMapChanging()){
			game.maps.switchMap(toMapID, toX, toY);
		}
	}
	public void UI_showChatDialog(String headImgPack,String headImgWas,String chatwords){
		UI_hideChatDialog();
		game.senceStage.mesHud.showChatDlg(headImgPack,headImgWas,chatwords);
	}
	public void UI_showChatDialogOptions(String headImgPack,String headImgWas,String chatwords,ChatOption[] opts){
		UI_hideChatDialog();
		game.senceStage.mesHud.showOptionChatDlg(headImgPack,headImgWas,chatwords,opts);
	}
	public void UI_hideChatDialog(){
		game.senceStage.mesHud.hide();
	}
	public void UI_showSystemMessage(String mess){
		game.senceStage.systemMessage.showMessage(mess);
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]", "[CommandSystem] -> UI ->显示系统提示消息："+mess);
		}
	}
	public void UI_hideSystemMessage(){
		game.senceStage.systemMessage.hide();
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]", "[CommandSystem] -> UI ->隐藏系统提示消息");
		}
	}
	/**显示某个召唤兽的资质*/
	public void UI_showSummonZZ(int index) {
		if(game.senceStage.dialogUIHud.summonZiZhiDlg==null){
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[CommandSystem] -> UI ->不能显示某个召唤兽的资质信息，因为资质信息面板没有打开");
			}
			return;
		}
		if(index==-1){
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[CommandSystem] -> UI ->不能显示某个召唤兽的资质信息，传递的是不存在的索引-1");
			}
			return;
		}
		ArrayList<Summon> summons=game.ls.player.getSummons();
		if(summons.get(index)==null){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ CommandSystem ] -> UI -> 显示第"+index+"个召唤兽信息失败，因为没这个召唤兽");
			}
			return;
		}
		SummonData data=summons.get(index).data();
		
		SummonZiZhiDlg dlg=game.senceStage.dialogUIHud.summonZiZhiDlg;
		dlg.updataSummonZizhi(data);
	}
	public void UI_showDialog(int id){
		game.senceStage.dialogUIHud.showDialog(id);
	}
	public void UI_hideDialog(int id){
		game.senceStage.dialogUIHud.hideDialog(id);
	}
	/**显示钓鱼界面
	 * @param where 长寿村还是傲来国
	 * @param pos 钓鱼的人在第几号位置[0-4]
	 * */
	public void UI_showFishDialog(String where,int pos){
		if(!game.ls.player.saling){
			int can_fish_level=50;
			if(!where.equals("长寿村")&&!where.equals("傲来国")){
				UI_showSystemMessage("只有长寿村和傲来国可以钓鱼哦");
			}else if(game.ls.ifm.PLAYER_getLevel()<can_fish_level){
				UI_showSystemMessage("要至少"+can_fish_level+"级才可以钓鱼");
			}else
				game.senceStage.dialogUIHud.showFishDlg(where,pos);
		}
		else
			UI_showSystemMessage("摆摊的时候就别钓鱼了，小心东西被偷");
	}
	public void UI_showCurrentSmallMap(){
		if(game.maps.currentMap().smallMapWas.equals(""))
			return;
		game.senceStage.dialogUIHud.showSmallMap(game.maps.currentMap());
	}
	/**刷新玩家背包界面*/
	public void UI_refreshPlrBag() {
		game.senceStage.dialogUIHud.refreshPlrBagDlg();
	}
	/**刷新召唤兽界面*/
	public void UI_refreshSummonBlg() {
		game.senceStage.dialogUIHud.refreshSummonBlg();
	}
	/**禁用除了给予按钮以外的按钮*/
	public void UI_disableInfohudBtns_Ex_Give() {
		
		game.senceStage.infoHud.giveBtn.setDisabled(false);
		game.senceStage.infoHud.tradeBtn.setDisabled(true);
		game.senceStage.infoHud.teamBtn.setDisabled(true);
		game.senceStage.infoHud.petBtn.setDisabled(true);
		game.senceStage.infoHud.taskBtn.setDisabled(true);
		game.senceStage.infoHud.friendBtn.setDisabled(true);
		game.senceStage.infoHud.actBtn.setDisabled(true);
		
		game.ts.addTask("给予按钮后自动启用", 3000, new TimeEventAdapter(){
			@Override
			public void act(){
				UI_undisableInfohudBtns();
				game.ls.isGiving=false;
				game.ls.isTrading=false;
			}
		});
	}
	/**禁用除了交易按钮以外的按钮*/
	public void UI_disableInfohudBtns_Ex_Trade() {
		
		game.senceStage.infoHud.giveBtn.setDisabled(true);
		game.senceStage.infoHud.tradeBtn.setDisabled(false);
		game.senceStage.infoHud.teamBtn.setDisabled(true);
		game.senceStage.infoHud.petBtn.setDisabled(true);
		game.senceStage.infoHud.taskBtn.setDisabled(true);
		game.senceStage.infoHud.friendBtn.setDisabled(true);
		game.senceStage.infoHud.actBtn.setDisabled(true);
		
		game.ts.addTask("交易按钮后自动启用", 3000, new TimeEventAdapter(){
			@Override
			public void act(){
				UI_undisableInfohudBtns();
				game.ls.isGiving=false;
				game.ls.isTrading=false;
			}
		});
	}
	/**战斗时禁用按钮*/
	public void UI_disableInfohudBtns_Battle() {
		game.senceStage.infoHud.bagBtn.setDisabled(true);
		game.senceStage.infoHud.giveBtn.setDisabled(true);
		game.senceStage.infoHud.tradeBtn.setDisabled(true);
		game.senceStage.infoHud.teamBtn.setDisabled(true);
		game.senceStage.infoHud.petBtn.setDisabled(true);
		game.senceStage.infoHud.taskBtn.setDisabled(true);
		game.senceStage.infoHud.friendBtn.setDisabled(true);
		game.senceStage.infoHud.actBtn.setDisabled(true);
		
	}
	/**战斗时禁用按钮*/
	public void UI_activeInfohudBtns_Battle() {
		
		UI_undisableInfohudBtns();
		
	}
	/**启用所有信息面板的按钮*/
	public void UI_undisableInfohudBtns(){
		game.senceStage.infoHud.bagBtn.setDisabled(false);
		game.senceStage.infoHud.giveBtn.setDisabled(false);
		game.senceStage.infoHud.tradeBtn.setDisabled(false);
		game.senceStage.infoHud.teamBtn.setDisabled(false);
		game.senceStage.infoHud.petBtn.setDisabled(false);
		game.senceStage.infoHud.taskBtn.setDisabled(false);
		game.senceStage.infoHud.friendBtn.setDisabled(false);
		game.senceStage.infoHud.actBtn.setDisabled(false);
	}
	/**显示商店面板*/
	public void UI_callShop(ShopData shop) {
		game.senceStage.dialogUIHud.callShop(shop);
		
	}
	/**显示观察某个召唤兽的面板*/
	public void UI_seeSummon(Summon summon) {
		game.senceStage.dialogUIHud.showDialog(DialogHud.SummonSeeDlg_ID);
		game.senceStage.dialogUIHud.setSeenSummon(summon);
	}
	/**冻结地图的镜头移动*/
	public void UI_forzenMapCamera(){
		game.maps.cam.forzen();
	}
	/**解冻冻结地图的镜头移动*/
	public void UI_deforzenMapCamera(){
		game.maps.cam.deforzen();
	}
	/**将某个dlg置顶显示*/
	public void UI_topShowDlg(Group dlg){
		Group group=dlg.getParent();
		dlg.remove();
		group.addActor(dlg);
	} 
	/**命令 执行人物加点数据
	 * @param type 加点类型0体质1力量2魔力3耐力4敏捷
	 * @param dlg 玩家信息的对话框，用于控制显示和按钮的
	 * */
	public void ACT_addPoint(int type, PlayerInfoDlg dlg) {
		//TODO 数据计算的时候要剔除添加的加点数据，免得影响人物属性
		if(game.ls.player.playerData().Qianli<=0){
			Gdx.app.error("[ XYQ ]","[指令]  -> 加点失败,因为玩家潜力小于等于0，加点类型："+type);
			return;
		}
		PlayerData data=game.ls.player.playerData();
		switch(type){
			case 0:
				data.Qianli--;
				data.addedTizhi++;
				dlg.tizhiM.setDisabled(false);
				if(data.Qianli<=0)
					dlg.disableAllABtn();
			break;
			case 1:
				data.Qianli--;
				data.addedLiliang++;
				dlg.liliangM.setDisabled(false);
				if(data.Qianli<=0)
					dlg.disableAllABtn();
			break;
			case 2:
				data.Qianli--;
				data.addedMoli++;
				dlg.moliM.setDisabled(false);
				if(data.Qianli<=0)
					dlg.disableAllABtn();
			break;
			case 3:
				data.Qianli--;
				data.addedNaili++;
				dlg.nailiM.setDisabled(false);
				if(data.Qianli<=0)
					dlg.disableAllABtn();
			break;
			case 4:
				data.Qianli--;
				data.addedMinjie++;
				dlg.minjieM.setDisabled(false);
				if(data.Qianli<=0)
					dlg.disableAllABtn();
			break;
		}
		if(game.is_Debug)
			Gdx.app.log("[ XYQ ]", "[CommandSystem] -> ACT ->执行加点操作，类型"+type);
	}


	/**命令 执行人物减点数据
	 * @param type 减点类型0体质1力量2魔力3耐力4敏捷
	 * @param dlg 玩家信息的对话框，用于控制显示和按钮的
	 * */
	public void ACT_minusPoint(int type, PlayerInfoDlg dlg) {
		
		PlayerData data=game.ls.player.playerData();
		switch(type){
			case 0:
				if(data.addedTizhi>0){
					data.addedTizhi--;
					data.Qianli++;
				}
				if(data.addedTizhi<=0)
					dlg.tizhiM.setDisabled(true);
			break;
			case 1:
				if(data.addedLiliang>0){
					data.addedLiliang--;
					data.Qianli++;
				}
				if(data.addedLiliang<=0)
					dlg.liliangM.setDisabled(true);
			break;
			case 2:
				if(data.addedMoli>0){
					data.addedMoli--;
					data.Qianli++;
				}
				if(data.addedMoli<=0)
					dlg.minjieM.setDisabled(true);
			break;
			case 3:
				if(data.addedNaili>0){
					data.addedNaili--;
					data.Qianli++;
				}
				if(data.addedNaili<=0)
					dlg.nailiM.setDisabled(true);
			break;
			case 4:
				if(data.addedMinjie>0){
					data.addedMinjie--;
					data.Qianli++;
				}
				if(data.addedMinjie<=0)
					dlg.minjieM.setDisabled(true);
			break;
		}
		if(game.is_Debug)
			Gdx.app.log("[ XYQ ]", "[CommandSystem] -> ACT ->执行减点操作，类型"+type);
	}

	/**确认加点并更新玩家信息*/
	public void ACT_confirmPoint(PlayerInfoDlg me) {
		PlayerData data=game.ls.player.playerData();
		
		data.Tizhi+=data.addedTizhi;
		data.addedTizhi=0;
		me.tizhiM.setDisabled(true);
		
		data.Naili+=data.addedNaili;
		data.addedNaili=0;
		me.nailiM.setDisabled(true);
		
		data.Liliang+=data.addedLiliang;
		data.addedLiliang=0;
		me.liliangM.setDisabled(true);
		
		data.Minjie+=data.addedMinjie;
		data.addedMinjie=0;
		me.minjieM.setDisabled(true);
		
		data.Moli+=data.addedMoli;
		data.addedMoli=0;
		me.moliM.setDisabled(true);

		if(game.is_Debug)
			Gdx.app.log("[ XYQ ]", "[CommandSystem] -> ACT ->执行确认加点操作");
	}
	/**命令 升级(将判断合法性，一般前台发送此命令)*/
	public void ACT_levelUP(){
		PlayerData data=game.ls.player.playerData();
		int currentExp=data.Exp;
		int needExp=game.ls.player.levelUpExp;
		int currentLevel=data.level;
		if(currentExp<needExp){
			this.UI_showSystemMessage("经验不足,无法升级");
			return;
		}
		if(currentLevel>=game.ls.maxLevelLimit){
			this.UI_showSystemMessage("等级达到了目前最高等级,无法升级");
			return;
		}
		currentLevel++;
		currentExp-=needExp;
		data.Exp=currentExp;
		data.level=currentLevel;
		data.Qianli+=5;
		
		game.ls.player.reloadLevelUpExp();
		
		game.ls.player.showAddonAnimation("addon.wdf","非战斗\\升级",-30f,0f);
		Sound_playSE1("./sound/SE/升级.ogg");
		
		
		
		
	}
	/**命令 增加经验*/
	public void ACT_EXPUp(int EXP){
		game.ls.player.playerData().Exp+=EXP;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 增加经验值 "+EXP);
		}
	}
	/**命令 增加现金*/
	public void ACT_MoneyUp(int money){
		game.ls.player.playerData().moneys[0]+=money;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 增加当前银两 "+money);
		}
		UI_refreshPlrBag();
	}
	/**命令 恢复体力*/
	public void ACT_TiliHeal(int tl){
		if(tl<1){
			Gdx.app.error("[ XYQ ]", "[指令] 准备恢复的体力小于1");
			return;
		}
		PlayerData data=game.ls.player.playerData();
		int currentTL=data.Tili;
		int currentTLMax=data.TiliMax;
		currentTL+=tl;
		if(currentTL>currentTLMax){
			currentTL=currentTLMax;
		}
		data.Tili=currentTL;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 恢复体力 "+tl);
		}
	}
	/**命令 消耗体力
	 * @return true成功消耗体力
	 * */
	public boolean ACT_TiliDown(int tl){
		PlayerData data=game.ls.player.playerData();
		if(tl>data.TiliMax){
			Gdx.app.error("[ XYQ ]", "[指令] 准备消耗的体力"+tl+"大于最大体力"+data.TiliMax);
			return false;
		}
		if(tl>data.Tili){
			Gdx.app.error("[ XYQ ]", "[指令] 准备消耗的体力"+tl+"大于当前体力"+data.Tili);
			return false;
		}
		int currentTL=data.Tili;
		currentTL-=tl;
		if(currentTL<0){
			currentTL=0;
		}
		data.Tili=currentTL;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 消耗体力 "+tl);
		}
		return true;
	}
	/**命令 恢复活力*/
	public void ACT_HuoliHeal(int hl){
		if(hl<1){
			Gdx.app.error("[ XYQ ]", "[指令] 准备恢复的活力小于1");
			return;
		}
		PlayerData data=game.ls.player.playerData();
		int currentHL=data.Huoli;
		int currentHLMax=data.HuoliMax;
		currentHL+=hl;
		if(currentHL>currentHLMax){
			currentHL=currentHLMax;
		}
		data.Huoli=currentHL;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 恢复活力 "+hl);
		}
	}
	/**命令 消耗体力*/
	public boolean ACT_HuoliDown(int hl){
		PlayerData data=game.ls.player.playerData();
		if(hl>data.HuoliMax){
			Gdx.app.error("[ XYQ ]", "[指令] 准备消耗的活力"+hl+"大于最大活力"+data.HuoliMax);
			return false;
		}
		if(hl>data.Huoli){
			Gdx.app.error("[ XYQ ]", "[指令] 准备消耗的活力"+hl+"大于当前活力"+data.Huoli);
			return false;
		}
		int currentTL=data.Huoli;
		currentTL-=hl;
		if(currentTL<0){
			currentTL=0;
		}
		data.Huoli=currentTL;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 消耗活力 "+hl);
		}
		return true;
	}
	/**命令 恢复愤怒*/
	public void ACT_AngryHeal(int ang){
		if(ang<1){
			Gdx.app.error("[ XYQ ]", "[指令] 准备恢复的愤怒小于1");
			return;
		}
		PlayerData data=game.ls.player.playerData();
		int currentANG=data.SP;
		int currentANGMax=data.SPMax;
		currentANG+=ang;
		if(currentANG>currentANGMax){
			currentANG=currentANGMax;
		}
		data.SP=currentANG;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 恢复愤怒 "+ang);
		}
	}
	/**命令 消耗愤怒*/
	public boolean ACT_AngryDown(int ang){
		PlayerData data=game.ls.player.playerData();
		if(ang>data.SPMax){
			Gdx.app.error("[ XYQ ]", "[指令] 准备消耗的愤怒"+ang+"大于最大愤怒"+data.SPMax);
			return false;
		}
		if(ang>data.SP){
			Gdx.app.error("[ XYQ ]", "[指令] 准备消耗的愤怒"+ang+"大于当前愤怒"+data.SP);
			return false;
		}
		int currentTL=data.SP;
		currentTL-=ang;
		if(currentTL<0){
			currentTL=0;
		}
		data.SP=currentTL;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 消耗愤怒 "+ang);
		}
		return true;
	}
	/**命令 恢复HP*/
	public void ACT_HPHeal(int HP){
		if(HP<1){
			Gdx.app.error("[ XYQ ]", "[指令] 准备恢复的HP小于1");
			return;
		}
		PlayerData data=game.ls.player.playerData();
		int currentHP=data.HP;
		int currentHPTempMax=data.HPTempMax;
		currentHP+=HP;
		if(currentHP>currentHPTempMax){
			currentHP=currentHPTempMax;
		}
		data.HP=currentHP;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 恢复气血 "+HP);
		}
	}
	/**命令 恢复HP临时上限*/
	public void ACT_HPTempMaxHeal(int HP){
		if(HP<1){
			Gdx.app.error("[ XYQ ]", "[指令] 准备恢复的HPTempMax小于1");
			return;
		}
		PlayerData data=game.ls.player.playerData();
		int currentHPTempMax=data.HPTempMax;
		int currentHPMax=data.HPMax;
		currentHPTempMax+=HP;
		if(currentHPTempMax>currentHPMax){
			currentHPTempMax=currentHPMax;
		}
		data.HPTempMax=currentHPTempMax;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 恢复临时气血上限 "+HP);
		}
	}
	/**命令 恢复MP*/
	public void ACT_MPHeal(int MP){
		if(MP<1){
			Gdx.app.error("[ XYQ ]", "[指令] 准备恢复的MP小于1");
			return;
		}
		PlayerData data=game.ls.player.playerData();
		int currentMPMax=data.MPMax;
		int currentMP=data.MP;
		currentMP+=MP;
		if(currentMP>currentMPMax){
			currentMP=currentMPMax;
		}
		data.MP=currentMP;
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 恢复魔法 "+MP);
		}
		
	}
	/**命令 解除酒类异常状态*/
	public void ACT_WineDebuff(){
		
	}
	public void ACT_sellBaiTan() {
		if(game.ls.player.saling)
			game.ls.player.saleOFF();
		else
			game.ls.player.saleON(game.ls.player.myZhaoPaiName);
	}
	public void ACT_sellBaiTanChangeName(String newName) {
		if(game.ls.player.saling)
			game.ls.player.saleNameChange(newName);
	}
	
	void ACT_cook(int cookCount){
		int cookPoint=game.ls.ifm.PLAYER_getLearnedSkillPoint("烹饪技巧");
		int HuoLi=cookPoint;
		ArrayList<Integer> pool=new ArrayList<Integer>();
		if(cookPoint>=0&&cookPoint<=4){
			pool.add(53);//包子
			 HuoLi=5;
		}else if(cookPoint>=5&&cookPoint<=9){
			pool.add(53);//包子
			pool.add(98);
		}else if(cookPoint>=10&&cookPoint<=14){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
		}else if(cookPoint>=15&&cookPoint<=19){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
		}else if(cookPoint>=20&&cookPoint<=24){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
			pool.add(101);
			pool.add(102);
		}else if(cookPoint>=25&&cookPoint<=29){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
			pool.add(101);
			pool.add(102);
			pool.add(103);
		}else if(cookPoint>=30&&cookPoint<=34){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
			pool.add(101);
			pool.add(102);
			pool.add(103);
			pool.add(104);
		}else if(cookPoint>=35&&cookPoint<=39){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
			pool.add(101);
			pool.add(102);
			pool.add(103);
			pool.add(104);
			pool.add(105);
			pool.add(106);
		}else if(cookPoint>=40&&cookPoint<=44){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
			pool.add(101);
			pool.add(102);
			pool.add(103);
			pool.add(104);
			pool.add(105);
			pool.add(106);
			pool.add(107);
		}else if(cookPoint>=45&&cookPoint<=49){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
			pool.add(101);
			pool.add(102);
			pool.add(103);
			pool.add(104);
			pool.add(105);
			pool.add(106);
			pool.add(107);
			pool.add(108);
		}else if(cookPoint>=50&&cookPoint<=54){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
			pool.add(101);
			pool.add(102);
			pool.add(103);
			pool.add(104);
			pool.add(105);
			pool.add(106);
			pool.add(107);
			pool.add(108);
			pool.add(109);
		}else if(cookPoint>=55&&cookPoint<=59){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
			pool.add(101);
			pool.add(102);
			pool.add(103);
			pool.add(104);
			pool.add(105);
			pool.add(106);
			pool.add(107);
			pool.add(108);
			pool.add(109);
			pool.add(110);
		}else if(cookPoint>=60&&cookPoint<=64){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
			pool.add(101);
			pool.add(102);
			pool.add(103);
			pool.add(104);
			pool.add(105);
			pool.add(106);
			pool.add(107);
			pool.add(108);
			pool.add(109);
			pool.add(110);
			pool.add(111);
		}else if(cookPoint>=65){
			pool.add(53);//包子
			pool.add(98);
			pool.add(99);
			pool.add(100);
			pool.add(101);
			pool.add(102);
			pool.add(103);
			pool.add(104);
			pool.add(105);
			pool.add(106);
			pool.add(107);
			pool.add(108);
			pool.add(109);
			pool.add(110);
			pool.add(111);
			pool.add(112);
		}else{
			pool.add(53);//包子
			 HuoLi=5;
		}
		if(HuoLi>80)
			HuoLi=80;
		if(game.ls.player.playerData().Huoli<HuoLi){
			UI_showSystemMessage("活力不够了，不能烹饪");
			return ;
		}else{
			game.ls.player.playerData().Huoli-=HuoLi;
		}
		
		int i=0;
		i=RandomUT.getRandom(i, pool.size()-1);
		int made=53;
		made=pool.get(i);
		ItemStackData item=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, made, cookCount);
		if(ACT_addItem(item)){
			String ItemName=game.itemDB.getItemName(item);
			UI_showSystemMessage("经过一阵忙碌,你做出了一份 "+ItemName);
		}else{
			UI_showSystemMessage("哎呀，烹饪失败了");
		}
	}
	public void ACT_cookBySelf() {
		ACT_cook(1);
	}
	public void ACT_cookInHouse() {
		if(RandomUT.isGot(6))
			ACT_cook(2);
		else
			ACT_cook(1);
	}
	/**命令 使用东西，一般从背包使用
	 * @param stackIndex 玩家物品索引【】
	 * @param usedCount 使用数量
	 * */
	public void ACT_bagItemUse(int stackIndex,int usedCount) {
		if(stackIndex==-1){
			Gdx.app.error("[ XYQ ]", "[玩家] 使用了不存在的物品，传递的物品索引为-1");
			return;
		}
		if(usedCount<=0)
			usedCount=1;
		if(usedCount>99)
			usedCount=99;
		ItemStackData nowItem=game.ls.player.getItem(stackIndex);
		if(nowItem==null){
			Gdx.app.error("[ XYQ ]","[玩家] 使用的对应位置的物品没有数据 in "+stackIndex);
			return;
		}
		int nowCount=nowItem.getItemCount();
		
		if(stackIndex>=0&&stackIndex<=5){//如果使用的是装备栏里面的装备，则需要卸下来，如果找不到空位则不动
			
			game.ls.player.deEquipItem(nowItem);
			game.senceStage.dialogUIHud.refreshPlrBagDlg();
			if(game.is_Debug){
				String itemName=game.itemDB.getItemName(nowItem);
				Gdx.app.log("[ XYQ ]","[玩家] 卸下了栈内索引为 "+stackIndex+" 的装备:"+itemName);
			}
			return;
		}
		
		boolean minusLock=false;//锁定物品减少，一般用于装备切换的控制.true就是要消耗
		minusLock=funcDB.useItem(nowItem);
		if(nowItem.getItemType()==XYQDataBase.ITEM_EQUIP&&game.is_Debug){
			String itemName=game.itemDB.getItemName(nowItem);
			Gdx.app.log("[ XYQ ]","[玩家] 装上了栈内索引为 "+stackIndex+" 的装备:"+itemName);
		}
		/*
		switch(nowType){
			case XYQDataBase.ITEM_EDIBLE:
				funcDB.useItem(nowItem);
			break;
			case XYQDataBase.ITEM_BASIC:
				funcDB.useItem(nowItem);
			break;
			case XYQDataBase.ITEM_EQUIP://如果使用的是包裹里的装备，则需要装备上去
				game.ls.player.equipItem(nowItem);
				if(game.is_Debug){
					String itemName=game.db.loadItemDetails(nowItem)[0];
					Gdx.app.log("[ XYQ ]","[玩家] 装上了栈内索引为 "+stackIndex+" 的装备:"+itemName);
				}
				game.senceStage.dialogUIHud.refreshPlrBagDlg();
				minusLock=true;
		}
		*/
		if(nowCount<usedCount){
			Gdx.app.error("[ XYQ ]","[玩家] 使用的物品数量超过了拥有的数量，准备使用"+usedCount+"个，但只拥有"+nowCount+"个，物品索引为"+stackIndex);
			return;
		}
		if(!minusLock)
			;
		else
			game.ls.player.minusItem(nowItem,usedCount);
		
		if(game.is_Debug){
			String itemName=game.itemDB.getItemName(nowItem);
			Gdx.app.log("[ XYQ ]","[玩家] 使用了"+usedCount+"个栈内索引为 "+stackIndex+" 的物品:"+itemName+" - 剩余了"+nowCount+"个");
		}
	}
	/**命令 交换背包里两个道具的位置
	 * @param from 交换的道具1的statck_index
	 * @param to 交换的道具2的statck_index
	 * */
	public void ACT_bagItemSwitch(int from,int to) {
		if(from==-1){
			Gdx.app.error("[ XYQ ]", "[玩家] 不能交换道具1，传递的物品索引为-1");
			return;
		}
		if(to==-1){
			Gdx.app.error("[ XYQ ]", "[玩家] 不能交换道具2，传递的物品索引为-1");
			return;
		}
		
		ItemStackData fromItem=game.ls.player.getItem(from);
		ItemStackData toItem=game.ls.player.getItem(to);
		if(fromItem!=null&&toItem!=null){//如果两个都不是空的道具
			if(!game.ls.ifm.ITEM_isTheSameItem(fromItem, toItem)){//r如果是不同类型的东西，则交换位置
				fromItem.setStackIndex(to);
				game.ls.player.setItem(to, fromItem);
				toItem.setStackIndex(from);
				game.ls.player.setItem(from, toItem);
			}else{//如果是同类的东西，则叠加
				ItemStackData newItem=fromItem.copy();
				//先清理掉原来的道具
				game.ls.player.clearItem(from);
				game.ls.player.clearItem(to);
				newItem.setItemCount(fromItem.getItemCount()+toItem.getItemCount());
				//计算需要分出来的份数，比如最大堆叠15个，32个就堆叠为2份
				int devideCount=(newItem.getItemCount()/game.itemDB.getMaxCount(newItem))+1;
				int lastDevideNumCount=newItem.getItemCount()%game.itemDB.getMaxCount(newItem);
				for(int i=0;i<devideCount-1;i++){//渐次分开，最后一份数量为
					ItemStackData lastItem=newItem.copy();
					int newindex=game.ls.ifm.PLAYER_getPlayerFirstEmptyItemStackIndex();
					if(newindex==-1){
						Gdx.app.error("[ XYQ ]", "[玩家] 交换道具的时候不能存放多余的道具，背包没空位了！");
						return;
					}
					lastItem.setItemCount(game.itemDB.getMaxCount(newItem));
					lastItem.setStackIndex(newindex);
					ACT_putItem(lastItem);
				}
				int newindex=game.ls.ifm.PLAYER_getPlayerFirstEmptyItemStackIndex();
				if(newindex==-1){
					Gdx.app.error("[ XYQ ]", "[玩家] 交换道具的时候不能存放多余的道具，背包没空位了！");
					return;
				}
				ItemStackData lastItem=newItem.copy();
				lastItem.setItemCount(lastDevideNumCount);
				lastItem.setStackIndex(newindex);
				ACT_putItem(lastItem);
			}
		}
		else if(fromItem!=null&&toItem==null){
			fromItem.setStackIndex(to);
			game.ls.player.setItem(to, fromItem);
			game.ls.player.clearItem(from);
		}else if(fromItem==null&&toItem!=null){
			toItem.setStackIndex(from);
			game.ls.player.setItem(from, toItem);
			game.ls.player.clearItem(to);
		}
		game.senceStage.dialogUIHud.refreshPlrBagDlg();
		if(game.is_Debug&&fromItem!=null&&toItem!=null){
			String itemName=game.itemDB.getItemName(fromItem);
			String itemName2=game.itemDB.getItemName(toItem);
			Gdx.app.log("[ XYQ ]","[玩家] 交换了"+from+"索引【"+itemName+"】和 "+to+" 索引的物品【"+itemName2+"】");
		}
	}
	/**命令 购买东西
	 * @param goodsData 购买的商品信息
	 * @param totalPrice 购买总价
	 * @param totalPrice 购买数量
	 * */
	public boolean ACT_ShopBuy(GoodsData goodsData, int totalPrice, int selectedCount) {
		int haveMoney=game.ls.ifm.PLAYER_getMoney();
		if(totalPrice>haveMoney){
			if(game.is_Debug)
				Gdx.app.log("[ XYQ ]", "[ CommandSystem ] -> 购买失败，玩家拥有的钱"+game.ls.ifm.PLAYER_getMoney()+"不足以支付:"+totalPrice);
			UI_showSystemMessage("少侠的钱不够呀");
			return false;
		}
		
		ItemStackData good=goodsData.getItem();
		ItemStackData itemToBy=good.copy();
		itemToBy.setItemCount(selectedCount);
		itemToBy.setStackIndex(-1);
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]", "[ 指令 ] -> 购买准备，商品上架数量"+good.getItemCount()+"，购买数量:"+itemToBy.getItemCount());
			Gdx.app.log("[ XYQ ]", "[ 指令 ] -> 购买准备，购买总价"+totalPrice+"，购买索引:"+itemToBy.getStackIndex());
		}
		if(ACT_addItem(itemToBy)){
			ACT_minusMoney(totalPrice);
			if(!goodsData.isInfinite())//如果不是无限供货的，则减少对应数量的商品
				good.setItemCount(good.getItemCount()-selectedCount);
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[ 指令 ] -> 购买后续，商品上架剩余数量"+good.getItemCount());
			}
		}else{
			UI_showSystemMessage("购买失败,包裹满了？还是东西坏了？");
			UI_refreshPlrBag();
			return false;
		}
		UI_refreshPlrBag();

		
		return true;
	}
	/**设定玩家的现金银两*/
	public void ACT_setMoney(int money){
		game.ls.player.playerData().moneys[0]=money;
	}
	/**减少玩家的现金银两*/
	public boolean ACT_minusMoney(int money){
		int haveMoney=game.ls.ifm.PLAYER_getMoney();
		if(haveMoney<money){
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[ 指令 ] -> 减少现金银两失败，玩家拥有的钱"+haveMoney+"不足以支付:"+money);
			}
			return false;
		}
		int leftMoney=haveMoney-money;
		game.ls.player.playerData().moneys[0]=leftMoney;
		UI_refreshPlrBag();
		return true;
	}
	/**将一个道具放到背包里,如果有同样的物品在，则叠加,叠加超过最大堆叠数则另放一格子]
	 * */
	public boolean ACT_addItem(ItemStackData item) {
		//TODO 放道具进去不好用
		if(item==null){
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[指令] 把物品放到背包里失败了，传递的是个不存在的东西，null");
			}
			return false;
		}
		int currentItemInBagIndex=game.ls.ifm.PLAYER_getPlayerOneItemStackIndex(item,false);
		if(game.is_Debug&&currentItemInBagIndex==-1){
			Gdx.app.log("[ XYQ ]","[指令] 准备将道具放到背包，背包里没有这个道具[id]"+item.getItemID()+"[type]"+item.getItemType());
		}
		
		if(currentItemInBagIndex==-1){//如果背包里没有这个道具，则要新找一个位置
			int emptyIndex=game.ls.ifm.PLAYER_getPlayerFirstEmptyItemStackIndex();
			if(emptyIndex==-1){
				if(game.is_Debug){
					Gdx.app.error("[ XYQ ]","[指令] 准备将道具放到背包，背包里没有这个道具[id]"+item.getItemID()+"[type]"+item.getItemType()+"，而且没空位置了");
				}
				return false;
			}
			//找到了空位
			item.setStackIndex(emptyIndex);
			ACT_putItem(item);
			return true;
		}else{//如果背包里有这个道具，则叠加
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[指令] 准备将道具放到背包，背包里有这个道具[id]"+item.getItemID()+"[type]"+item.getItemType()+"，在原位置叠加");
			}
			item.setStackIndex(currentItemInBagIndex);//找到了这个道具，则准备叠加，设定叠加位置
			ItemStackData currPlrBagItem=game.ls.player.getItem(currentItemInBagIndex);//获取当前位置的道具
			int currPlrBagItemCount=currPlrBagItem.getItemCount();
			int nowCount=item.getItemCount();
			int maxCount=game.itemDB.getMaxCount(item);
			if(nowCount+currPlrBagItemCount>maxCount){//如果堆叠后大于当前物品的最大堆叠数
				ItemStackData anotherItem=item.copy();
				anotherItem.setItemCount(nowCount+currPlrBagItemCount-maxCount);
				int anIndex=game.ls.ifm.PLAYER_getPlayerFirstEmptyItemStackIndex();
				String name=game.itemDB.getItemName(item.getItemType(), item.getItemID());
				//TODO 完善重复堆叠，堆叠99，再堆叠11个，再在11个上堆叠
				anotherItem.setStackIndex(anIndex);
				item.setItemCount(maxCount);
				ACT_putItem(item);
				if(anIndex!=-1)
					ACT_putItem(anotherItem);
				else{
					UI_showSystemMessage("多余的["+name+"]放不了,背包没空格了");
					if(game.is_Debug)
						Gdx.app.error("[ XYQ ]","[指令] 将多余的["+name+"]道具放到背包里失败了，因为背包满了");
				}
			}else{//如果没有超过最大堆叠数，则叠加数据，并
				item.setItemCount(nowCount+currPlrBagItemCount);
				ACT_putItem(item);
				return true;
			}
		}
		
		
		return true;
	}
	/**将一个道具放到背包里,如果有同样的物品在，则叠加,叠加超过最大堆叠数则另放一格子]
	 * */
	boolean ACT_addItem2(ItemStackData item) {
		if(item==null){
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[指令] 把物品放到背包里失败了，传递的是个不存在的东西，null");
			}
			return false;
		}
		if(item.getStackIndex()==-1){
			int index=game.ls.ifm.PLAYER_getPlayerOneItemStackIndex(item,true);
			if(index==-1){
				if(game.is_Debug){
					Gdx.app.log("[ XYQ ]","[指令] 把物品放到背包里失败了，传递的东西，索引为-1且找不到空背包了");
				}
				return false;
			}
			item.setStackIndex(index);
		}
		ItemStackData currPlrBagItem=game.ls.player.getItem(item.getStackIndex());
		if(currPlrBagItem==null){//如果当前位置是空的，没有东西，就直接放进去
			ACT_putItem(item);
			return true;
		}
		String name=game.itemDB.getItemName(item.getItemType(), item.getItemID());
		//如果当前位置有东西了
		if(currPlrBagItem.getItemType()!=item.getItemType()||currPlrBagItem.getItemID()!=item.getItemID())
			if(game.is_Debug){
				String toname=game.itemDB.getItemName(currPlrBagItem.getItemType(), currPlrBagItem.getItemID());
				Gdx.app.error("[ XYQ ]","[指令] 将一个["+name+"]放到背包里失败了，因为目标位置有一个不同的东西:"+toname);
				return false;
			}
		//如果当前位置有相同的东西，并开始堆叠了
		int currPlrBagItemCount=currPlrBagItem.getItemCount();
		int nowCount=item.getItemCount();
		int maxCount=game.itemDB.getMaxCount(item);
		if(nowCount+currPlrBagItemCount>maxCount){//如果堆叠后大于当前物品的最大堆叠数
			ItemStackData anotherItem=item.copy();
			anotherItem.setItemCount(nowCount+currPlrBagItemCount-maxCount);
			int anIndex=game.ls.ifm.PLAYER_getPlayerFirstEmptyItemStackIndex();
			//TODO 完善重复堆叠，堆叠99，再堆叠11个，再在11个上堆叠
			anotherItem.setStackIndex(anIndex);
			item.setItemCount(maxCount);
			ACT_putItem(item);
			if(anIndex!=-1)
				ACT_putItem(anotherItem);
			else{
				UI_showSystemMessage("多余的["+name+"]放不了,背包没空格了");
				if(game.is_Debug)
					Gdx.app.error("[ XYQ ]","[指令] 将多余的["+name+"]道具放到背包里失败了，因为背包满了");
			}
		}else{//如果没有超过最大堆叠数，则叠加数据，并
			item.setItemCount(nowCount+currPlrBagItemCount);
			ACT_putItem(item);
		}
		
		return true;
	}
	/**将一个道具删除,如果找不到，则返回false，删除成功则返回true*/
	public boolean ACT_delItem(int type,int id,int delCount) {
		ItemStackData currPlrBagItem=game.ls.player.getItem(type,id);
		if(currPlrBagItem==null){//如果没有东西
			return false;
		}
		int count=currPlrBagItem.getItemCount();
		if(count>=delCount){
			count-=delCount;
			if(count<=0){
				game.ls.player.removeItem(currPlrBagItem.getStackIndex());
			}else{
				currPlrBagItem.setItemCount(count);
			}
			UI_refreshPlrBag();
			return true;
		}
		return false;
	}
	public void ACT_removeItem(int index) {
		game.ls.player.removeItem(index);
		UI_refreshPlrBag();
		
	}
	public void ACT_confirmRemoveItem(int index) {
		UI_showDialog(DialogHud.confirmRmItemDlg_ID);
		game.senceStage.dialogUIHud.confirmRmItem(index);
	}
	/**将一个道具放到背包里,不管原来是啥，直接覆盖*/
	public void ACT_putItem(ItemStackData item) {
		game.ls.player.setItem(item.getStackIndex(), item);
		String name=game.itemDB.getItemName(item.getItemType(), item.getItemID());
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]","[指令] 将"+item.getItemCount()+"个["+name+"]道具放到背包里,覆盖位置"+item.getStackIndex());
		}

		UI_refreshPlrBag();
	}
	
	/**使用城间穿梭飞到某个地方*/
	public boolean ACT_flyTo(int mapID,int toX,int toY) {
		int fly_lim_level=9;
		if(game.ls.ifm.PLAYER_getLevel()>fly_lim_level){
			if(ACT_delItem(ItemDB.ITEM_BASIC, 94, 1)){
				MAP_SwitchMap(mapID, toX, toY);
				return true;
			}
			else{
        		UI_showSystemMessage("背包里没有飞行符呀");
        		if(game.is_Debug){
        			Gdx.app.log("[ XYQ ]","[指令] 城间穿梭失败了，因为背包里没有飞行符");
        		}
        		return false;
			}
		}else{
			UI_showSystemMessage("等级没有超过"+fly_lim_level+"级，飞来飞去太危险了");
			if(game.is_Debug){
    			Gdx.app.log("[ XYQ ]","[指令] 城间穿梭失败了，因为玩家等级不足10级");
    		}
			return false;
		}
	}

	/**在平时状态使用魔法*/
	public void ACT_normalUseMagic(MagicData mData) {
		UI_showSystemMessage("使用了技能 "+mData.name);
		
	}
	/**冻结玩家，不让玩家移动*/
	public void ACT_forzenPlayer() {
		game.ls.player.stopMoving();
		game.ls.player.canMove=false;
		
	}
	/**解冻玩家，让玩家可以移动*/
	public void ACT_deForzenPlayer() {
		game.ls.player.canMove=true;
	}
	/**使用藏宝图
	 * @param mapID 藏宝图标记的地图id
	 * @param x 藏宝图标记的逻辑坐标X
	 * @param y 藏宝图标记的逻辑坐标Y
	 * @return true 如果藏宝图被消耗(被挖掉)
	 * */
	public boolean ACT_treasureMapUse(int mapID, int x, int y) {
		//TODO
		if(game.ls.ifm.MAP_getCurrentMapID()==mapID&&game.ls.ifm.PLAYER_getLogicXPos()==x&&game.ls.ifm.PLAYER_getLogicYPos()==y){
			//如果玩家在藏宝图指定的位置，则开始挖宝
			UI_showSystemMessage("一铲子下去,你用力过猛,好像碰坏了什么建筑");
			

			return true;
		}
		else{
			//如果玩家不在藏宝图指定的位置，则显示地图位置
			String mapName=game.ls.ifm.MAP_getMapName(mapID);
			UI_showSystemMessage("藏宝图上的坐标位于"+mapName+"的"+x+","+y);
			return false;
		}
	}
	/**使用高级藏宝图
	 * @param mapID 高级藏宝图标记的地图id
	 * @param x 高级藏宝图标记的逻辑坐标X
	 * @param y 高级藏宝图标记的逻辑坐标Y
	 * @return true 如果藏宝图被消耗(被挖掉)
	 * */
	public boolean ACT_advancedTreasureMapUse(int mapID, int x, int y) {
		//TODO
		if(game.ls.ifm.MAP_getCurrentMapID()==mapID&&game.ls.ifm.PLAYER_getLogicXPos()==x&&game.ls.ifm.PLAYER_getLogicYPos()==y){
			//如果玩家在藏宝图指定的位置，则开始挖宝
			UI_showSystemMessage("恭喜你,你挖到一本妖怪遗留的书籍      才怪");		

			return true;
		}
		else{
			//如果玩家不在藏宝图指定的位置，则显示地图位置
			String mapName=game.ls.ifm.MAP_getMapName(mapID);
			UI_showSystemMessage("藏宝图上的坐标位于"+mapName+"的"+x+","+y);
			return false;
		}
		
	}
	/**想给予物品给谁谁谁*/
	public void ACT_wantGive(){
		game.ls.isGiving=true;
		UI_disableInfohudBtns_Ex_Give();
		UI_showSystemMessage("请点击要给予的对象");
	}
	/**想给予物品给谁谁谁*/
	public void ACT_wantTrade(){
		game.ls.isTrading=true;
		UI_disableInfohudBtns_Ex_Trade();
		UI_showSystemMessage("请点击要交易的对象");
	}
	/**将一个召唤兽加到携带了列表里*/
	public boolean ACT_addSummon(Summon summon) {
		int havedCount=game.ls.ifm.PLAYER_getPickSummonCount();
		if(havedCount>=5){
			if(game.is_Debug)
				Gdx.app.error("[ XYQ ]", "[ CommandSystem ]-> ACT -> 将一个召唤兽加到携带了列表里失败，因为身上带了5个召唤兽了，装不下了");
			return false;
		}
		game.ls.player.getSummons().add(summon);
		UI_refreshSummonBlg();
		return true;
	}
	/**显示内置游戏调试框*/
	public void ACT_superDebugger() {
		//game.IGD.setVisible(true);
		UI_showDialog(DialogHud.IGDDlg_ID);
	}
	/**切换隐藏玩家称谓*/
	public void Player_toggleTitle(final Player player){
		player.actor.toggleTitle();
	}
	/**移动某个玩家到某个地方*/
	public void Player_MoveTo(final Player player,float clickX,float clickY,int logicX,int logicY){
		player.moveTo(logicX, logicY);
	}
	public void Player_setPlayerLogicPosition(Player player,int x,int y){
		player.setLogicXY(x, y);
		player.updateXYByLogicXY();
		game.ls.ifm.MAP_getCurrentMap().lookAtPlayer();
		game.ls.ifm.MAP_getCurrentMap().smallMapAutoXY();
	}
	/**玩家移动一小格
	 * @param dir 移动的方向 0-上 1-下 2-左 3-右
	 * */
	public void Player_moveOneStep(int dir){
		int x=game.ls.ifm.PLAYER_getLogicXPos();
		int y=game.ls.ifm.PLAYER_getLogicYPos();
		if(dir==0){
			y++;
		}else if(dir==1){
			y--;
		}else if(dir==2){
			x--;
		}else if(dir==3){
			x++;
		}
		Player_setPlayerLogicPosition(game.ls.player, x, y);
	}
	public void Player_Say(final Player player,String words){
		if(words.equals("@debug@")){
			System_code_debug();
			return;
		}
		if(words.equals("```")){
			System_code_debug2();
			return;
		}
		if(words.equals("`1`")){
			Player_moveOneStep(0);
			return;
		}
		if(words.equals("`2`")){
			Player_moveOneStep(1);
			return;
		}
		if(words.equals("`3`")){
			Player_moveOneStep(2);
			return;
		}
		if(words.equals("`4`")){
			Player_moveOneStep(3);
			return;
		}
		if(player==null)
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[CommanSystem] -> 找不到Player，不能让空气说这些话："+words);
				return;
			}
		player.say(words);
	}
	public void Player_SwitchAction(String action){
		game.ls.player.actor.switchStat(action);
	}
	
	public void NPC_Say(final NPC npc,String words){
		if(npc==null){
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[CommanSystem] -> 找不到NPC，不能让空气说这些话："+words);
				return;
			}
			return;
		}
		npc.say(words);
	}
	public void NPC_MoveTo(final NPC npc,int logicX,int logicY){
		npc.moveTo(logicX, logicY);
	}
	public void Sound_playSE1(String url){
		game.sm.playSE1(url);
	}
	public void Sound_switchBGM(String string) {
		game.sm.switchBGM(string);
		
	}
	public void BATTLE_enterBattle(BattleData battle) {
		if(!game.ls.ifm.SYSTEM_isBattling())
			game.senceStage.battleHud.enterBattle(battle);
	}
	public void BATTLE_endBattle() {
		if(game.ls.ifm.SYSTEM_isBattling())
			game.senceStage.battleHud.endBattle(null);
	}
	public void System_timePass(String time) {
    	Gdx.app.log("[ XYQ ]", "[系统] -> 现在时间是："+time);
	}
	public void System_toggleBGM() {
		SoundManager.BGM_ON=!SoundManager.BGM_ON;
    	Gdx.app.log("[ XYQ ]", "[声音] -> 系统BGM开关改变，当前开关为："+SoundManager.BGM_ON);
	}
	public void System_toggleDebug(){
		game.is_Debug=!game.is_Debug;
    	Gdx.app.log("[ XYQ ]", "[系统] -> 系统调试开关改变，当前开关为："+ game.is_Debug);
	}
	public void System_toggleLightShadow() {
		game.showLightShadow=!game.showLightShadow;
    	Gdx.app.log("[ XYQ ]", "[系统] -> 系统光影开关改变，当前开关为："+ game.showLightShadow);
	}
	public void System_exit(){
		Gdx.app.log("[ XYQ ]", "[系统] -> 退出");
        Gdx.app.exit();
	}
	public void System_AI_debug(){
		MapGraphPath path=game.ls.ifm.MAP_pathFind(32, 128, 33, 121);
		game.cs.UI_showSystemMessage("新版A*寻路结果长度是"+path.getCount()+",与预期的长度8比较？");
		Gdx.app.log("[ debug ]", "[ 新寻路系统 ] -> "+path);
		Gdx.app.log("[ debug ]", "[ 新寻路系统 ] -> 长距离寻路性能测试");
		long time=System.currentTimeMillis();
		path=game.ls.ifm.MAP_pathFind(32, 128, 12, 4);
		long etime=System.currentTimeMillis();
		long useTime=etime-time;
		game.cs.UI_showSystemMessage("新版A*寻路结果消耗时间是"+useTime);
		Gdx.app.log("[ debug ]", "[ 新寻路系统 ] -> "+path);
		path=game.ls.ifm.MAP_pathFind(32, 128, 0, 0);
		game.cs.UI_showSystemMessage("新版A*寻路阻碍结果长度是"+path.getCount()+"");
	}
	public void System_code_debug2(){
		game.cs.ACT_superDebugger();
	}
	public void System_code_debug(){
        ChatOption[] opts=new ChatOption[3];
        opts[0]=new ChatOption("选项一",new LinkLabelClickAction() {
				@Override
				public void click() {
					//game.cs.UI_showSystemMessage("安咯囖囖咯");
					//game.cs.Player_Frozen(game.ls.player);
					
					ItemStackData bt=game.itemDB.makeTreasureMap(1501);
					ACT_addItem(bt);
					bt=game.itemDB.makeAdvancedTreasureMap(-1);
					ACT_addItem(bt);
					
					//bt=game.itemDB.makeTreasureMap(1506);
					//(bt);
					//ACT_addItem(game.itemDB.makeEdibleItem(13, 2, 4, 55));
					//ACT_addItem(game.itemDB.makeEdibleItem(13, 2, 4, 55));
					System_code_debug2();
					
					ACT_bagItemSwitch(6, 7);
				}
        });
        opts[1]=new ChatOption("选项二",new LinkLabelClickAction() {
				@Override
				public void click() {
					//game.cs.UI_showSystemMessage("安咯囖囖咯安咯囖囖咯安咯囖囖咯安咯囖囖咯...");
					//game.senceStage.map.setVisible(false);
					//UI_showDialog(DialogHud.playerInfoDlg_ID);
					//game.ls.player.playerData().Qianli+=5;
					//ACT_EXPUp(20);
					//ACT_MoneyUp(2000);
					//game.ls.player.switchItem(2,10);
					//game.ls.player.saleON("随便卖卖");
					game.cs.BATTLE_endBattle();
				}
       });
        opts[2]=new ChatOption("选项三",new LinkLabelClickAction(){
				@Override
				public void click() {
					
					NPC npc=game.ls.ifm.NPC_getCurrentMapNPC("王元家指导员");
					if(npc!=null){
						game.cs.NPC_Say(npc, "去那边看一看");
						int[] pos=game.ls.ifm.MAP_getMapRandomPassbleXY(game.ls.ifm.MAP_getCurrentMapID());
						game.cs.NPC_MoveTo(npc, pos[0], pos[1]);
					}
					game.cs.UI_hideChatDialog();
					//System.out.println(game.ls.player.playerData().toString());
					Player_SwitchAction(ShapeData.CRY );
					game.cs.UI_showSystemMessage("["+game.ts.getHour()+":"+game.ts.getMinute()+":"+game.ts.getSecond()+"，"+game.ts.getGameTime()+"]");
					
					//System_AI_debug();
					//game.cs.MAP_SwitchMap(1173, 24, 18);
					ACT_sellBaiTanChangeName("甩卖了");
					
										
				}
       });
       game.cs.UI_showChatDialogOptions("wzife.wdf","人物头像\\梦幻精灵","这里是调试对话框，用于在开发时及时调试一些代码",opts);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
