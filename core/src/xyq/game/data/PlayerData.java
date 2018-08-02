package xyq.game.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;

public class PlayerData {
	/**玩家ID*/
	public String id;
	/**玩家名*/
	public String name;
	/**等级*/
	public int level;
	
	/**人气*/
	public int popularoty;
	
	/**拥有的称谓总数*/
	public int titleCount;
	/**帮派*/
	public String Bangpai;
	/**门派*/
	public String Menpai;
	
	/**帮派贡献*/
	public int Banggong;
	/**门派贡献*/
	public int Mengong;
	
	/**最大气血值*/
	public int HPMax;
	/**临时最大气血值*/
	public int HPTempMax;
	/**当前气血值*/
	public int HP;
	/**最大法力值*/
	public int MPMax;
	/**当前体力值*/
	public int MP;
	/**最大愤怒值*/
	public int SPMax;
	/**当前愤怒值*/
	public int SP;
	/**当前经验*/
	public int Exp;
	/**最大活力值*/
	public int HuoliMax;
	/**当前活力值*/
	public int Huoli;
	/**最大体力值*/
	public int TiliMax;
	/**当前体力值*/
	public int Tili;
	
	/**命中*/
	public int Mingzhong;
	/**伤害*/
	public int Shanghai;
	/**防御*/
	public int Fangyu;
	/**速度*/
	public int Sudu;
	/**躲闪*/
	public int Duoshan;
	/**灵力*/
	public int Lingli;
	
	/**体质*/
	public int Tizhi;
	/**魔力*/
	public int Moli;
	/**力量*/
	public int Liliang;
	/**耐力*/
	public int Naili;
	/**敏捷*/
	public int Minjie;
	/**潜力*/
	public int Qianli;
	
	/**被分配给体质的潜力*/
	public int addedTizhi;
	/**被分配给魔力的潜力*/
	public int addedMoli;
	/**被分配给力量的潜力*/
	public int addedLiliang;
	/**被分配给耐力的潜力*/
	public int addedNaili;
	/**被分配给敏捷的潜力*/
	public int addedMinjie;
	
	/**角色*/
	public String Role;
	/**称谓*/
	public String Title;
	
	/**所在地图id*/
	public int inMapID;
	/**玩家所在坐标*/
	public int[] location=new int[2];
	
	/**剧情点*/
	public int jqPoint;
	/**人物换色数据*/
	public int[] colorations;

	/**玩家的所有物品信息*/
	public HashMap<Integer,ItemStackData> items;
	
	/**玩家的货币信息[当前银两][存银][货币2][货币3][货币4]
	 * [钓鱼历史收获总数][钓鱼积分][钓鱼法宝1数量][钓鱼法宝2数量][钓鱼法宝3数量][钓鱼法宝4数量]*/
	public int[] moneys;
	

	/**玩家召唤兽数据*/
	public ArrayList<Summon> summons;
	
	public PlayerData(){
		items=new HashMap<Integer,ItemStackData>();
		summons=new ArrayList<Summon>();
	}

	public String toString(){
		StringBuilder sBuilder=new StringBuilder();
		sBuilder.append("--------玩家信息-------\n");
		sBuilder.append("玩家名："+name+"\n");
		sBuilder.append("ID："+id+"\n");
		sBuilder.append("角色："+Role+"\n");
		sBuilder.append("所在地图ID："+inMapID+"\n");
		sBuilder.append("所在地图X坐标："+location[0]+"\n");
		sBuilder.append("所在地图Y坐标："+location[1]+"\n");
		sBuilder.append("称谓："+Title+"\n");
		sBuilder.append("称谓总数："+titleCount+"\n");
		sBuilder.append("帮派："+Bangpai+"\n");
		sBuilder.append("门派："+Menpai+"\n");
		sBuilder.append("人气："+popularoty+"\n");
		sBuilder.append("已加体质："+addedTizhi+"\n");
		sBuilder.append("已加魔力："+addedMoli+"\n");
		sBuilder.append("已加力量："+addedLiliang+"\n");
		sBuilder.append("已加耐力："+addedNaili+"\n");
		sBuilder.append("已加敏捷："+addedMinjie+"\n");
		return sBuilder.toString();
	}
	/**获取当前玩家的种族，比如龙太子是仙族
	 * @return 人-魔-仙
	 * */
	public String PLAYER_getRoleNation(String Role){
		if(Role.equals("龙太子")||Role.equals("玄彩娥")||Role.equals("舞天姬")||Role.equals("神天兵")||Role.equals("羽灵神"))
			return "仙";
		else if(Role.equals("狐美人")||Role.equals("骨精灵")||Role.equals("巨魔王")||Role.equals("虎头怪")||Role.equals("杀破狼"))
			return "魔";
		else if(Role.equals("飞燕女")||Role.equals("英女侠")||Role.equals("逍遥生")||Role.equals("剑侠客")||Role.equals("巫蛮儿"))
			return "人";
		return null;
	}
	/**制作初始化玩家数据
	 * @param role 玩家角色
	 * @param name 玩家名
	 * */
	public void makeInitialData(String role,String id,String name){
		String nation=PLAYER_getRoleNation(role);
		if(nation.equals("魔")){
			//体12-魔力11-力量11-耐力8-敏8
			Tizhi=12;Moli=22;Liliang=11;Naili=8;Minjie=8;
			//血172-魔法值107-命中55-伤害43-防御11-速度8-躲闪18-灵力17
			HPMax=172;HP=172;MPMax=107;MP=107;
			Mingzhong=55;Shanghai=43;Fangyu=11;Sudu=8;Duoshan=18;Lingli=17;
		}else if(nation.equals("人")){
			//体10-魔力10-力量10-耐力10-敏10
			Tizhi=10;Moli=10;Liliang=10;Naili=10;Minjie=10;
			//血150-魔法值110-命中50-伤害41-防御15-速度10-躲闪30-灵力16
			HPMax=150;HP=150;MPMax=110;MP=110;
			Mingzhong=50;Shanghai=41;Fangyu=15;Sudu=10;Duoshan=30;Lingli=16;
		}else if(nation.equals("仙")){
			//体12-魔力5-力量11-耐力12-敏10
			Tizhi=12;Moli=5;Liliang=11;Naili=12;Minjie=10;
			//血154-魔法值97-命中48-伤害46-防御19-速度10-躲闪20-灵力13
			HPMax=154;HP=154;MPMax=97;MP=97;
			Mingzhong=48;Shanghai=46;Fangyu=19;Sudu=10;Duoshan=20;Lingli=13;
		}else{
			Gdx.app.error("[ XYQ ]", "[ PlayerData ] -> 生成玩家数据有错误，通过角色【"+role+"】查找的种族失败了");
			return;
		}
		this.id=id;
		this.name=name;
		this.Role=role;
		level=0;
		SPMax=150;
		inMapID=1501;
		location=new int[2];
		location[0]=36;location[1]=130;
		popularoty=700;
		
		Bangpai="无帮派";
		Menpai="无门派";
		
		HuoliMax=50;
		Huoli=50;
		TiliMax=50;
		Tili=50;
		
		colorations=new int[4];
		items=new HashMap<Integer, ItemStackData>();
		
		moneys=new int[15];
		
		summons=new ArrayList<Summon>();
	}
	
	public void calcAttribute(){
		//速度=体力*0.1+耐力*0.1+力量*0.1+敏捷*0.7+魔力*0（魔力不加速度）
		Sudu=(int)(Tizhi*0.1f+Naili*0.1f+Liliang*0.1f+Minjie*0.7f);
		//灵力=体力*0.3+魔力*0.7+耐力*0.2+力量*0.4+敏捷*0（敏捷不加灵力）	
		Lingli=(int)(Tizhi*0.3f+Moli*0.7f+Naili*0.2f+Liliang*0.4f);
		//躲避=敏捷*1
		Duoshan=(int)(Minjie);
		//TODO 装备影响数值
		/*
		 	命中：
			人：力量*2+30
			魔：力量*2.3+27
			仙：力量*1.7+30
			
			伤害：
			人：力量*0.7+34
			魔：力量*0.8+34
			仙：力量*0.6+40
			
			防御：
			人：耐力*1.5
			魔：耐力*1.3
			仙：耐力*1.6
			
			气血：
			人：体力*5+100
			魔：体力*6+100
			仙：体力*4.5+100
			
			魔法：
			人：魔力*3+80
			魔：魔力*2.5+80
			仙：魔力*3.5+80
		 */
		String nation=PLAYER_getRoleNation(Role);
		if(nation.equals("人")){
			Mingzhong=Liliang*2+30;
			Shanghai=(int)(Liliang*0.7f+34);
			Fangyu=(int)(Naili*1.5f);
			HPMax=Tizhi*5+100;
			MPMax=Moli*3+80;
		}else if(nation.equals("魔")){
			Mingzhong=(int)(Liliang*2.3f+27);
			Shanghai=(int)(Liliang*0.8f+34);
			Fangyu=(int)(Naili*1.3f);
			HPMax=Tizhi*6+100;
			MPMax=(int)(Moli*2.5f+80);
		}else if(nation.equals("仙")){
			Mingzhong=(int)(Liliang*1.7f+30);
			Shanghai=(int)(Liliang*0.6f+40);
			Fangyu=(int)(Naili*1.6f);
			HPMax=(int)(Tizhi*4.5f+100);
			MPMax=(int)(Moli*3.5f+80);
		}
		//TODO 技能影响数值
	}
}
