package xyq.game.stage.UI;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;

import xyq.game.XYQGame;
import xyq.game.data.PlayerData;
import xyq.game.data.Summon;
import xyq.game.stage.XYQMapActor;
import xyq.game.stage.UI.dialog.*;
import xyq.game.stage.UI.dialog.comp.DialogHudCtrlAdapter;
import xyq.system.bussiness.ShopData;

public class DialogHud  extends Group{
	XYQGame game;
	public static final int systemDlg_ID=0;
	public static final int playerInfoDlg_ID=1;
	public static final int playerBagDlg_ID=2;
	public static final int smallMapDlg_ID=3;
	public static final int worldMapDlg_ID=4;
	public static final int smSkillDlg_ID=5;
	public static final int fzSkillDlg_ID=6;
	public static final int jqSkillDlg_ID=7;
	public static final int makePillDlg_ID=8;
	public static final int fishingDlg_ID=9;
	public static final int AITestDlg_ID=10;
	public static final int SummonInfoDlg_ID=11;
	public static final int SummonZiZhiDlg_ID=12;
	public static final int SummonSeeDlg_ID=13;
	public static final int flyPaperDlg_ID=14;
	public static final int IGDDlg_ID=15;
	public static final int confirmRmItemDlg_ID=16;
	
	int lastShowShopID=-1;
	
	SystemDlg systemDlg;
	PlayerInfoDlg playerInfoDlg;
	PlayerBagDlg playerBagDlg;
	SmallMapDlg smallMapDlg;
	WorldMapDlg worldMapDlg;
	SMSkillDlg smSkillDlg;
	FZSkillDlg fzSkillDlg;
	JQSkillDlg jqSkillDlg;
	FishingDlg fishingDlg;
	AITestDlg aiDlg;
	RoleShopDlg role_shopDlg;
	SystemShopDlg sys_shopDlg;
	SummonInfoDlg summonInfoDlg;
	SummonSeeDlg summonSeeDlg;
	FlyPaperDlg flyPaperDlg;
	
	public SummonZiZhiDlg summonZiZhiDlg;
	InGameDebugerDlg IGDDlg;
	ConfirmRemoveItemDlg confirmRemoveItemDlg;
	
	HashMap<Integer, DialogHudCtrlAdapter> ctrls;
	
	public DialogHud(XYQGame xyqgame){
		super();
		this.game=xyqgame;
		ctrls=new HashMap<Integer, DialogHudCtrlAdapter>();
		this.setPosition(0, 0);
		
		/*
		systemDlg=new SystemDlg(game);
        systemDlg.setPosition(400, 276);
        addActor(systemDlg);
        */
		ctrls.put(systemDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(systemDlg==null){
					systemDlg=new SystemDlg(game);
					systemDlg.setZIndex(3);
			        systemDlg.setPosition(400, 276);
			        addActor(systemDlg);
				}
				systemDlg.setVisible(true);
				systemDlg.remove();
		        addActor(systemDlg);
			};
			@Override
			public void hide(){
				if(systemDlg!=null)
					systemDlg.setVisible(false);
			};
		});
		ctrls.put(playerInfoDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(playerInfoDlg==null){
					playerInfoDlg=new PlayerInfoDlg(game);
					playerInfoDlg.setPosition(1000, 200);
			        addActor(playerInfoDlg);
				}
				playerInfoDlg.setNameSoSo(game.ls.player.playerData().name, game.ls.player.playerData().Title, game.ls.player.playerData().Bangpai);
				playerInfoDlg.setVisible(true);
				playerInfoDlg.remove();
		        addActor(playerInfoDlg);
			};
			@Override
			public void hide(){
				if(playerInfoDlg!=null)
					playerInfoDlg.setVisible(false);
			};
		});
		ctrls.put(playerBagDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(playerBagDlg==null){
					playerBagDlg=new PlayerBagDlg(game);
					playerBagDlg.setPosition(100, 180);
			        addActor(playerBagDlg);
				}
				playerBagDlg.setVisible(true);
				playerBagDlg.remove();
		        addActor(playerBagDlg);
			};
			@Override
			public void hide(){
				if(playerBagDlg!=null)
					playerBagDlg.setVisible(false);
			};
		});
		ctrls.put(smallMapDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(smallMapDlg==null){
					smallMapDlg=new SmallMapDlg(game);
					smallMapDlg.setPosition(100, 180);
					smallMapDlg.setZIndex(3);
			        addActor(smallMapDlg);
				}
				smallMapDlg.setVisible(true);
				smallMapDlg.remove();
		        addActor(smallMapDlg);
			};
			@Override
			public void hide(){
				if(smallMapDlg!=null)
					smallMapDlg.setVisible(false);
			};
		});
		ctrls.put(smSkillDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(smSkillDlg==null){
					smSkillDlg=new SMSkillDlg(game);
					smSkillDlg.setPosition(414, 135);
			        addActor(smSkillDlg);
			        smSkillDlg.load();
				}
				smSkillDlg.setVisible(true);
		        smSkillDlg.load();
		        smSkillDlg.remove();
		        addActor(smSkillDlg);
			};
			@Override
			public void hide(){
				if(smSkillDlg!=null)
					smSkillDlg.setVisible(false);
			};
		});
		ctrls.put(fzSkillDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(fzSkillDlg==null){
					fzSkillDlg=new FZSkillDlg(game);
					fzSkillDlg.setPosition(414, 135);
			        addActor(fzSkillDlg);
			        fzSkillDlg.load();
				}
				fzSkillDlg.setVisible(true);
		        fzSkillDlg.load();
		        fzSkillDlg.remove();
		        addActor(fzSkillDlg);
			};
			@Override
			public void hide(){
				if(fzSkillDlg!=null)
					fzSkillDlg.setVisible(false);
			};
		});
		ctrls.put(jqSkillDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(jqSkillDlg==null){
					jqSkillDlg=new JQSkillDlg(game);
					jqSkillDlg.setPosition(414, 135);
			        addActor(jqSkillDlg);
			        jqSkillDlg.load();
				}
				jqSkillDlg.setVisible(true);
		        jqSkillDlg.load();
		        jqSkillDlg.remove();
		        addActor(jqSkillDlg);
			};
			@Override
			public void hide(){
				if(jqSkillDlg!=null)
					jqSkillDlg.setVisible(false);
			};
		});
		ctrls.put(worldMapDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(worldMapDlg==null){
					worldMapDlg=new WorldMapDlg(game);
					worldMapDlg.setPosition(240, 60);
					worldMapDlg.setZIndex(3);
			        addActor(worldMapDlg);
				}
				worldMapDlg.setVisible(true);
				worldMapDlg.remove();
		        addActor(worldMapDlg);
			};
			@Override
			public void hide(){
				if(worldMapDlg!=null)
					worldMapDlg.setVisible(false);
			};
		});
		ctrls.put(fishingDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(fishingDlg==null){
					fishingDlg=new FishingDlg(game,"",2);
					fishingDlg.setPosition(320, 150);
			        addActor(fishingDlg);
				}
				fishingDlg.setVisible(true);
				fishingDlg.remove();
		        addActor(fishingDlg);
				game.cs.ACT_forzenPlayer();
			};
			@Override
			public void hide(){
				if(fishingDlg!=null)
					fishingDlg.setVisible(false);
			};
		});
		ctrls.put(AITestDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(aiDlg==null){
					aiDlg=new AITestDlg(game);
					aiDlg.setPosition(100, 150);
			        addActor(aiDlg);
				}
				aiDlg.setVisible(true);
				aiDlg.remove();
		        addActor(aiDlg);
			};
			@Override
			public void hide(){
				if(aiDlg!=null)
					aiDlg.setVisible(false);
			};
		});
		ctrls.put(SummonInfoDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(summonInfoDlg==null){
					summonInfoDlg=new SummonInfoDlg(game);
					summonInfoDlg.setPosition(90, 170);
			        addActor(summonInfoDlg);
				}
				summonInfoDlg.setVisible(true);
				summonInfoDlg.updataInfo();
				summonInfoDlg.onTop();
			};
			@Override
			public void hide(){
				if(summonInfoDlg!=null)
					summonInfoDlg.setVisible(false);
			};
		});
		ctrls.put(SummonZiZhiDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(summonZiZhiDlg==null){
					summonZiZhiDlg=new SummonZiZhiDlg(game);
					summonZiZhiDlg.setPosition(372, 170);
			        addActor(summonZiZhiDlg);
				}
				summonZiZhiDlg.onTop();
				summonZiZhiDlg.setVisible(true);
			};
			@Override
			public void hide(){
				if(summonZiZhiDlg!=null)
					summonZiZhiDlg.setVisible(false);
			};
		});
		ctrls.put(SummonSeeDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(summonSeeDlg==null){
					summonSeeDlg=new SummonSeeDlg(game);
					summonSeeDlg.setPosition(332, 170);
			        addActor(summonSeeDlg);
				}
				summonSeeDlg.onTop();
				summonSeeDlg.setVisible(true);
			};
			@Override
			public void hide(){
				if(summonSeeDlg!=null)
					summonSeeDlg.setVisible(false);
			};
		});
		ctrls.put(flyPaperDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(flyPaperDlg==null){
					flyPaperDlg=new FlyPaperDlg(game);
					flyPaperDlg.setPosition(352, 220);
			        addActor(flyPaperDlg);
				}
				flyPaperDlg.onTop();
				flyPaperDlg.setVisible(true);
			};
			@Override
			public void hide(){
				if(flyPaperDlg!=null)
					flyPaperDlg.setVisible(false);
			};
		});
		ctrls.put(IGDDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(IGDDlg==null){
					IGDDlg=new InGameDebugerDlg(game);
					IGDDlg.setPosition(0, 30);
			        addActor(IGDDlg);
				}
				game.cs.UI_topShowDlg(IGDDlg);
				IGDDlg.setVisible(true);
			};
			@Override
			public void hide(){
				if(IGDDlg!=null)
					IGDDlg.setVisible(false);
			};
		});
		ctrls.put(confirmRmItemDlg_ID, new DialogHudCtrlAdapter(){
			@Override
			public void show(){
				if(confirmRemoveItemDlg==null){
					confirmRemoveItemDlg=new ConfirmRemoveItemDlg(game);
					confirmRemoveItemDlg.setPosition(480, 290);
			        addActor(confirmRemoveItemDlg);
				}
				game.cs.UI_topShowDlg(confirmRemoveItemDlg);
				confirmRemoveItemDlg.setVisible(true);
			};
			@Override
			public void hide(){
				if(confirmRemoveItemDlg!=null)
					confirmRemoveItemDlg.setVisible(false);
			};
		});
		
		
	}
	public void showFishDlg(String where,int pos){
		if(fishingDlg!=null){
			fishingDlg.remove();
		}
		fishingDlg=new FishingDlg(game,where,pos);
		fishingDlg.setPosition(320, 150);
		fishingDlg.setZIndex(1);
        addActor(fishingDlg);
		fishingDlg.setVisible(true);
	}
	public void showDialog(int id){
		if(ctrls.get(id)==null){
			Gdx.app.error("[ XYQ ]", "[ DialogHud ] ->显示ID为"+id+"的对话框失败了，没有预定义这个id的方法");
		}else {
			ctrls.get(id).show();
		}
	}
	public void hideDialog(int id){
		if(ctrls.get(id)==null){
			Gdx.app.error("[ XYQ ]", "[ DialogHud ] ->关闭显示ID为"+id+"的对话框失败了，没有预定义这个id的方法");
		}else {
			ctrls.get(id).hide();
		}
	}
	public void updataPlrBasicInfo(PlayerData data) {
		if(playerInfoDlg!=null){
			playerInfoDlg.updataPlrBasicInfo(data);
		}
		
	}
	public void refreshPlrBagDlg(){
		if(playerBagDlg!=null){
			playerBagDlg.refreshAll();
		}
	}
	public void refreshSummonBlg(){
		if(summonInfoDlg!=null)
			summonInfoDlg.updataInfo();
	}
	/**重绘包裹面板*/
	public void reloadItemInfoPanel() {
		
		
	}
	public void showSmallMap(XYQMapActor currentMap) {
		showDialog(smallMapDlg_ID);
		smallMapDlg.showMapSmallMap(currentMap);
		smallMapDlg.setPosition((1280-smallMapDlg.getWidth())/2,(720-smallMapDlg.getHeight())/2);
	}
	public void callShop(ShopData shop) {
		//0-摊位 1-系统商店 2-店面
		if(shop.getShopType()==0){
			role_shopDlg=new RoleShopDlg(game,shop);
			role_shopDlg.setVisible(true);
			addActor(role_shopDlg);
		}else if(shop.getShopType()==1){
			sys_shopDlg=new SystemShopDlg(game,shop);
			sys_shopDlg.setVisible(true);
			addActor(sys_shopDlg);
		}else if(shop.getShopType()==2){
			
		}
		/*
		if(lastShowShopID==shop.getShopID()){
			shopDlg.setVisible(true);
		}else{
			lastShowShopID=shop.getShopID();
			if(shopDlg!=null)
				shopDlg.remove();
			//TODO 重新制作
			ShopData shopData=game.ls.ifm.shopManager.getShop(shop.getShopID());
			shopDlg=new ShopDlg(game, shopData);
			shopDlg.setVisible(true);
			addActor(shopDlg);
		}
		*/
	}
	/**设定观察的召唤兽的数据*/
	public void setSeenSummon(Summon summon) {
		if(summonSeeDlg!=null){
			summonSeeDlg.setSeenSummon(summon);
		}
		
	}
	public void confirmRmItem(int index) {
		if(confirmRemoveItemDlg!=null)
			confirmRemoveItemDlg.confirmRemoveItem(index);
	}
}
