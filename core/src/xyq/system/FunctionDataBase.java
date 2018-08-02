package xyq.system;


import java.util.HashMap;

import com.badlogic.gdx.Gdx;

import xyq.game.XYQGame;
import xyq.game.data.ItemData;
import xyq.game.data.ItemEquipData;
import xyq.game.data.ItemStackData;
import xyq.game.stage.UI.DialogHud;
import xyq.system.utils.RandomUT;

public class FunctionDataBase {
	XYQGame game;
	HashMap<Integer, FuncCode> funcs;
	public FunctionDataBase(XYQGame game){
		this.game=game;
		init();
	}
	public void init(){
		funcs=new HashMap<Integer, FuncCode>();
		
		funcs.put(0, new FuncCode(0, "无功能", new FunctionAdapter(),new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setTag1(model.getFunctionDescription());
				return item;
			}
			
		}));
		funcs.put(1, new FuncCode(1, "装备", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是装备");
				game.ls.player.equipItem(item);
				game.senceStage.dialogUIHud.refreshPlrBagDlg();
				return false;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Equip(ItemStackData item,ItemEquipData model){
				return item;
			}
		}));
		funcs.put(2, new FuncCode(2, "恢复气血魔法", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具恢复气血魔法");
				int HPup=item.getAttr1();
				int MPup=item.getAttr2();
				game.cs.ACT_HPHeal(HPup);
				game.cs.ACT_MPHeal(MPup);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();
				
				item.setAttr1(model.getAttr1());
				item.setAttr2(model.getAttr2());
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(3, new FuncCode(3, "恢复气血", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具恢复气血");
				int HPup=item.getAttr1();
				game.cs.ACT_HPHeal(HPup);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();
				
				item.setAttr1(model.getAttr1());
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(4, new FuncCode(4, "恢复法力", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具恢复魔法");
				int MPup=item.getAttr1();
				game.cs.ACT_MPHeal(MPup);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();
				
				item.setAttr1(model.getAttr1());
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(5, new FuncCode(5, "治疗伤势", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具治疗伤势");
				int HPTemMaxup=item.getAttr1();
				game.cs.ACT_HPTempMaxHeal(HPTemMaxup);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();
				
				item.setAttr1(model.getAttr1());
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(6, new FuncCode(6, "恢复气血并治疗伤势", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具治疗伤势");
				int HPup=item.getAttr1();
				int HPTempMaxup=item.getAttr2();
				game.cs.ACT_HPTempMaxHeal(HPTempMaxup);
				game.cs.ACT_HPHeal(HPup);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();
				
				item.setAttr1(model.getAttr1());
				item.setAttr2(model.getAttr2());
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(7, new FuncCode(7, "根据品质恢复气血", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具根据品质恢复气血");
				int qu=item.getAttr1();
				int HPup=qu*12+150;
				game.cs.ACT_HPHeal(HPup);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setAttr1(model.getAttr1());
				item.setTag1(model.getFunctionDescription());
				item.setTag2("品质 "+model.getAttr1());
				return item;
			}
		}));
		funcs.put(8, new FuncCode(8, "根据品质恢复气血并治疗伤势", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具根据品质恢复气血并治疗伤势");
				int qu=item.getAttr1();
				int HPup=qu*12+150;
				int HPTempMaxUp=qu+50;
				game.cs.ACT_HPTempMaxHeal(HPTempMaxUp);
				game.cs.ACT_HPHeal(HPup);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setAttr1(model.getAttr1());
				item.setTag1(model.getFunctionDescription());
				item.setTag2("品质 "+model.getAttr1());
				return item;
			}
		}));
		funcs.put(9, new FuncCode(9, "根据品质恢复法力", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具根据品质恢复法力");
				int qu=item.getAttr1();
				int MPup=qu*10+100;
				game.cs.ACT_MPHeal(MPup);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setAttr1(model.getAttr1());
				item.setTag1(model.getFunctionDescription());
				item.setTag2("品质 "+model.getAttr1());
				return item;
			}
		}));
		funcs.put(14, new FuncCode(14, "随机鱼效果", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具随机鱼效果");
				int code=RandomUT.getRandom(1, 5);
				if(code==1){//增加经验
					int exp=RandomUT.getRandom(8000, 30000);
					game.cs.ACT_EXPUp(exp);
					game.cs.UI_showSystemMessage("你获得了"+exp+"点经验");
				}else if(code==2){//恢复气血
					int hp=RandomUT.getRandom(50, 500);
					game.cs.ACT_HPHeal(hp);
				}else if(code==3){//恢复魔法
					int hp=RandomUT.getRandom(50, 500);
					game.cs.ACT_MPHeal(hp);
				}else if(code==4){//给现金
					int money=RandomUT.getRandom(150, 15000);
					game.cs.ACT_MoneyUp(money);
					game.cs.UI_showSystemMessage("你获得了"+money+"两银子");
				}else if(code==5){//给宝石
					int money=RandomUT.getRandom(150, 15000);
					game.cs.ACT_MoneyUp(money);
					game.cs.UI_showSystemMessage("宝石系统还未开放，给你点钱打发打发");
				}
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(15, new FuncCode(15, "恢复体力", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具恢复体力");
				int tl=item.getAttr1();
				game.cs.ACT_TiliHeal(tl);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setAttr1(model.getAttr1());
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(16, new FuncCode(16, "恢复活力", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具恢复活力");
				int hl=item.getAttr1();
				game.cs.ACT_HuoliHeal(hl);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setAttr1(model.getAttr1());
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(17, new FuncCode(17, "恢复愤怒", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具恢复愤怒");
				int hl=item.getAttr1();
				game.cs.ACT_AngryHeal(hl);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setAttr1(model.getAttr1());
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(18, new FuncCode(18, "增加经验", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具增加经验");
				int hl=item.getAttr1();
				game.cs.ACT_EXPUp(hl);
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setAttr1(model.getAttr1());
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(19, new FuncCode(19, "藏宝图", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是藏宝图");
				int mapID=item.getAttr1();
				int x=item.getAttr2();
				int y=item.getAttr3();
				return game.cs.ACT_treasureMapUse(mapID,x,y);
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();
				int[] loc=game.ls.ifm.ITEM_generateTreasureMapAttr(-1);
				item.setAttr1(loc[0]);
				item.setAttr2(loc[1]);
				item.setAttr3(loc[2]);
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(20, new FuncCode(20, "高级藏宝图", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是高级藏宝图");
				int mapID=item.getAttr1();
				int x=item.getAttr2();
				int y=item.getAttr3();
				return game.cs.ACT_advancedTreasureMapUse(mapID,x,y);
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();
				int[] loc=game.ls.ifm.ITEM_generateTreasureMapAttr(-1);
				item.setAttr1(loc[0]);
				item.setAttr2(loc[1]);
				item.setAttr3(loc[2]);
				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(21, new FuncCode(21, "摄妖香", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是摄妖香功能");
				game.cs.UI_showSystemMessage("点燃了摄妖香,但是因为代码不完善，并没有什么用");
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(22, new FuncCode(22, "洞冥草气血查看", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是洞冥草气血查看");
				game.cs.UI_showSystemMessage("好难吃！");
				return true;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(23, new FuncCode(23, "城间穿梭", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是城间穿梭");
				game.senceStage.dialogUIHud.showDialog(DialogHud.flyPaperDlg_ID);
				//game.cs.UI_showSystemMessage("我飞");
				return false;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(24, new FuncCode(24, "钓鱼", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是钓鱼用的");
				game.cs.UI_showFishDialog(game.maps.getMapName(game.ls.ifm.MAP_getCurrentMapID()),2);
				return false;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setTag1(model.getFunctionDescription());
				return item;
			}
		}));
		funcs.put(25, new FuncCode(25, "根据品质恢复愤怒", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是根据品质恢复愤怒用的");
				return false;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setTag1(model.getFunctionDescription());
				item.setTag2("品质 "+model.getAttr1());
				return item;
			}
		}));
		funcs.put(26, new FuncCode(26, "根据品质恢复参战召唤兽寿命", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是根据品质恢复参战召唤兽寿命用的");
				return false;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setTag1(model.getFunctionDescription());
				item.setTag2("品质 "+model.getAttr1());
				return item;
			}
		}));
		funcs.put(27, new FuncCode(27, "根据品质恢复气血与法力", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是根据品质恢复气血与法力用的");
				return false;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
				item.function_Code=model.getFunction_Code();
				item.function_Tag=model.getFunction_Tag();

				item.setTag1(model.getFunctionDescription());
				item.setTag2("品质 "+model.getAttr1());
				return item;
			}
		}));
		funcs.put(28, new FuncCode(28, "未鉴定装备", new FunctionAdapter(){
			@Override
			public boolean exe(ItemStackData item){
				if(game.is_Debug)
					Gdx.app.log("[ XYQ ]", "[ 道具功能数据 ]->当前道具是未鉴定装备");
				if(item.getAttr1()==ItemDB.IEQUIP_NORMAL_MADE)
					game.cs.UI_showSystemMessage("请先鉴定这个物品");
				else if(item.getAttr1()==ItemDB.IEQUIP_ADVANCED_MADE)
					game.cs.UI_showSystemMessage("请先鉴定这个物品");
					
				return false;
			}
		},new FunctionTagMakerAdapter(){
			@Override
			public ItemStackData makeTag_Equip(ItemStackData item,ItemEquipData model){
				return item;
			}
		}));
	}
	/**根据道具数据，功能代码，平时状态使用。返回是否消耗
	 * @return true 如果该物品被消耗、
	 * */
	public boolean useItem(ItemStackData item){
		if(item==null)
			return true;
		FuncCode funcCode=funcs.get(item.function_Code);
		if(funcCode==null){
			if(game.is_Debug)
				Gdx.app.error("[ XYQ ]", "[ 道具功能数据 ]->当前道具的功能未注册:"+item.function_Code+" | "+item.function_Tag);
			return false;
		}
		return funcCode.exe(item);
	}
	
	public class FuncCode{
		public int code;
		public String tag;
		public FunctionAdapter func;
		public FunctionTagMaker tagMaker;
		
		public FuncCode(int code,String tag,FunctionAdapter func,FunctionTagMaker tagMaker){
			this.code=code;
			this.tag=tag;
			this.func=func;
			this.tagMaker=tagMaker;
		}
		/**执行当前功能代码
		 * @param item 如果是道具的功能代码，则传入此道具
		 * @return true 如果道具被消耗
		 * */
		public boolean exe(ItemStackData item){
			return func.exe(item);
		}
		/**根据当前代码自动填充数据
		 * @param item 如果是道具的功能代码，则传入此道具
		 * @param model ItemData模板数据
		 * @return ItemStackData 填充后道具
		 * */
		public ItemStackData makeTag_Basic(ItemStackData item,ItemData model){
			return tagMaker.makeTag_Basic(item, model);
		}
		public ItemStackData makeTag_Equip(ItemStackData item,ItemEquipData model){
			return tagMaker.makeTag_Equip(item, model);
		}
	}
}
