package xyq.game.rules.mapconfig;

import com.badlogic.gdx.Gdx;

import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;
import xyq.game.data.NPC;
import xyq.game.data.NPCClickEventAdapter;
import xyq.game.data.ShapeData;
import xyq.game.stage.ChatOption;
import xyq.game.stage.LinkLabelClickAction;
import xyq.game.stage.XYQMapActor;
import xyq.system.TimeEventAdapter;
import xyq.system.assets.PPCData;
import xyq.system.utils.RandomUT;

public class JianYeChengMapConfig{
	public static void config(final XYQGame game,final XYQMapActor map){
		NPC npc;

        npc=null;
        int[] cc={2,1,3,0};
        PPCData pp=game.rs.getPPData("shape.wd1", "人物换色6",cc);
		
        npc=new NPC(game,"王元家指导员","龙太子",pp);
        //npc.setNPCTitle("贪吃的小朋友");
        npc.setNPCPosition(16, 124);
		npc.setNPCFaceTo(RandomUT.getRandom(0, 3));
        npc.addThisNPC(map);
        npc.useGreenName(true);
        npc.beginRandomWalk(1000, 10000, 10);
        npc.addClickEventListener(new NPCClickEventAdapter(){
			@Override
			public void click(){
				game.cs.UI_showChatDialog("wzife.wdf", "人物头像\\龙太子", "说实话我没想到有那么多人会真的去加这个QQ，我是打广告的！");

			}
			@Override
			public void give(){
				game.cs.UI_hideSystemMessage();
				game.cs.UI_showChatDialog("wzife.wdf", "人物头像\\龙太子", "别给我东西，我不想要");
			}
			@Override
			public void trade(){
				game.cs.UI_hideSystemMessage();
				game.cs.UI_showChatDialog("wzife.wdf", "人物头像\\龙太子", "拿着这个，欢迎来到梦幻西游单机版");
				ItemStackData itemStackData=game.itemDB.makeTreasureMap(1501);
				game.cs.ACT_addItem(itemStackData);
			}
		});
        game.ts.addCycleTask("王元家指导员广告", 12000, new TimeEventAdapter(){
        	@Override
        	public void act(){
        		if(game.ls.ifm.MAP_getCurrentMapID()!=1501)
        			return;
        		int qq=RandomUT.getRandom(1000001, 9999999);
        		game.cs.NPC_Say(game.ls.ifm.NPC_getCurrentMapNPC("王元家指导员"), "新手加Q"+qq+"有 礼包相送,泡泡,点卡,银两,经验++++++++++");
        	}
        });
        
        npc=null;
        npc=new NPC(game,"※欧阳子默△","舞天姬");
        //npc.setNPCTitle("贪吃的小朋友");
        npc.setNPCPosition(45, 126);
        npc.useGreenName(true);
		npc.setNPCFaceTo(RandomUT.getRandom(0, 3));
        npc.addThisNPC(map);
        npc.saleON("新手杂货");
        //给这个npc一个ID为1的商店
        npc.setShopData(game.bs.getShop(1));
        npc.switchAction(ShapeData.WALK);
        
        npc=null;
        npc=new NPC(game,"测试", "飞燕女");
        npc.setNPCTitle("明焱之阳");
        npc.setNPCPosition(45, 119);
		npc.setNPCFaceTo(ShapeData.SOUTH_EAST);
        npc.addThisNPC(map);
        game.ai.readAI(Gdx.files.internal("./assets/ai/npcTest.tree"), npc);
        npc.addClickEventListener(new NPCClickEventAdapter(){
			@Override
			public void click(){
				
			}
		});
        
        
        npc=null;
        npc=new NPC(game,"建邺守卫", "衙役");
        npc.setNPCPosition(10, 6);
		npc.setNPCFaceTo(ShapeData.SOUTH_WEST);
        npc.addThisNPC(map);
        npc.addClickEventListener(new NPCClickEventAdapter(){
			@Override
			public void click(){
				ChatOption[] opts=new ChatOption[2];
		        opts[0]=new ChatOption("好的，谢谢",new LinkLabelClickAction() {
						@Override
						public void click() {
							game.cs.UI_hideChatDialog();
							game.cs.MAP_SwitchMap(1193, 147, 54);
						}
		        });
		        opts[1]=new ChatOption("不用了",new LinkLabelClickAction() {
						@Override
						public void click() {
							game.cs.UI_showChatDialog("wzife.wdf","人物头像\\衙役","少侠请自便。");
						}
		       });
		       game.cs.UI_showChatDialogOptions("wzife.wdf","人物头像\\衙役","城外有好多野猪，出行请注意安全。少侠要我送你出城吗？",opts);
			
			}
		});
        
	}
}
