package xyq.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import box2dLight.RayHandler;
import xyq.game.XYQGame;
import xyq.game.data.ItemData;
import xyq.game.data.ItemEquipData;
import xyq.game.data.ItemStackData;
import xyq.game.data.MagicData;
import xyq.game.data.PlayerData;
import xyq.game.data.ShapeData;
import xyq.game.data.SkillData;
import xyq.game.data.Summon;
import xyq.game.data.SummonData;
import xyq.game.data.SummonDataModel;
import xyq.game.stage.GameLight;
import xyq.system.map.MapInfo;

public class XYQDataBase {
	
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private static String protocol = "jdbc:derby:";
	String dbName = System.getProperty("user.dir")+"\\data\\database";
	
	public final static int ITEM_EQUIP=1;
	//public final static int ITEM_EDIBLE=2;
	public final static int ITEM_BASIC=2;
	public final static String[] WEAPON_TYPE={"剑","刀","锤","爪刺","斧钺","扇","枪矛","鞭","魔棒","飘带","环圈","双剑"};
	Connection conn = null;
	Statement s = null;
	ResultSet rs = null;
	
	HashMap<String,String> loadedIconName;
	HashMap<String,String> loadedBigIconName;
	
	XYQGame game;
	
	public XYQDataBase(XYQGame game){
		this.game=game;
		loadDriver();
		connect();
	}
	public XYQDataBase(){
		loadDriver();
		connect();
	}
	boolean loadDriver() {
		try {
			Class.forName(driver).newInstance();
			if(null!=game)
				Gdx.app.log("[ XYQ ]","[数据库] -> 数据库驱动加载完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadedIconName=new HashMap<String,String>();
		loadedBigIconName=new HashMap<String,String>();
		return true;
	}
	
	boolean connect(){
		conn = null;
		s = null;
		rs = null;
		if(null!=game)
			Gdx.app.log("[ XYQ ]","[数据库] -> 数据库启动中");
		try {
			conn = DriverManager.getConnection(protocol + dbName + ";create=false");
			s=conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			Gdx.app.error("[ XYQ ]","[数据库] -> 数据库链接成功,但是出了问题，可能是有另一个进程占用了数据库");
		}
		if(null!=game)
			Gdx.app.log("[ XYQ ]","[数据库] -> 数据库链接完毕");
		return true;
	}
	
	public boolean closeConnect(){
		try {
			Gdx.app.log("[ XYQ ]","[数据库] -> 断开数据库");
			conn.close();
			conn = null;
			s.close();
			s = null;
			if(rs!=null)
				rs.close();
			rs = null;
			Gdx.app.log("[ XYQ ]","[数据库] -> 断开完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	public HashMap<Integer,ItemStackData> loadRoleItemStackData(String id) {
		HashMap<Integer,ItemStackData> trans=new HashMap<Integer,ItemStackData>();
		ItemStackData tempOne;
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取角色拥有物品信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from ROLESAVE_ITEMSTACK where ROLEID="+id);
			}
			
			rs=s.executeQuery("select * from ROLESAVE_ITEMSTACK where ROLEID="+id);
			
			while (rs.next()){
				tempOne=new ItemStackData(rs.getInt("stackindex"),rs.getInt("itemtype"),rs.getInt("itemid"),rs.getInt("itemcount"));
				
				tempOne.setAttr1(rs.getInt("attr1"));
				tempOne.setAttr2(rs.getInt("attr2"));
				tempOne.setAttr3(rs.getInt("attr3"));
				tempOne.setAttr4(rs.getInt("attr4"));
				tempOne.setAttr5(rs.getInt("attr5"));
				tempOne.setAttr6(rs.getInt("attr6"));
				tempOne.setAttr7(rs.getInt("attr7"));
				tempOne.setAttr8(rs.getInt("attr8"));
				tempOne.setAttr9(rs.getInt("attr9"));
				
				tempOne.setLimitTime(rs.getLong("limittime"));
				
				tempOne.setTag1(rs.getString("tag1"));
				tempOne.setTag2(rs.getString("tag2"));
				tempOne.setTag3(rs.getString("tag3"));
				tempOne.setTag4(rs.getString("tag4"));
				tempOne.setTag5(rs.getString("tag5"));
				
				tempOne.function_Code=rs.getInt("functioncode");
				tempOne.function_Tag=rs.getString("functiontag");
				
				trans.put(rs.getInt("stackindex"),tempOne);
				tempOne=null;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return trans;
	}
	/**获取某个物品的图标，如果图标不在当前的已经读取的图标文件的缓存内，将从数据库里读取并生成
	 * @param tempItem 物品栏数据
	 * @param needSmallerOne 是否获取小图标
	 * */
	public String getItemIcon(ItemStackData tempItem,boolean needSmallerOne){
		String index;
		String loaded;
		if(needSmallerOne){
			index=tempItem.getItemID()+"@"+tempItem.getItemType();
			loaded=loadedIconName.get(index);
			if(loaded==null||loaded.equals("")){
				loaded=loadItemIconByTypeID(tempItem.getItemType(),tempItem.getItemID(),needSmallerOne);
				loadedIconName.put(index, loaded);
			}
		}
		else{
			index=tempItem.getItemID()+"@"+tempItem.getItemType();
			loaded=loadedBigIconName.get(index);
			if(loaded==null||loaded.equals("")){
				loaded=loadItemIconByTypeID(tempItem.getItemType(),tempItem.getItemID(),needSmallerOne);
				loadedBigIconName.put(index, loaded);
			}
		}
		return loaded;
		
	}
	
	/**根据数据库保存的字段判断是否是武器类型*/
	boolean isAWeaponTypeByType(String type){
		return Arrays.asList(WEAPON_TYPE).contains(type);
	}
	/**根据RES_NO资源编号字段判断是否是武器类型*/
	boolean isAWeaponTypeByRes_NO(int no){
		return  no==2;//itemstack 2编号是武器编号
	}
	String swbq(String number){
		if(number.length()==1)
			return "00"+number;
		else if(number.length()==2)
			return "0"+number;
		else
			return number;
	}
	
	/**从数据库读取玩家的货币数据
	 * @param id 玩家的id
	 * @return 返回玩家的货币信息[当前银两][存银][货币2][货币3][货币4]
	 * [钓鱼历史收获总数][钓鱼积分][钓鱼法宝1数量][钓鱼法宝2数量][钓鱼法宝3数量][钓鱼法宝4数量]
	 * */
	public int[] loadMoneyData(String id){
		int[] loaded=new int[15];
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取钱财信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from ROLESAVE_CURRENCY where ID="+id);
			}
			
			rs=s.executeQuery("select * from ROLESAVE_CURRENCY where ID="+id);
			
			while (rs.next()){
				loaded[0]=rs.getInt("money1");
				loaded[1]=rs.getInt("money2");
				loaded[2]=rs.getInt("money3");
				loaded[3]=rs.getInt("money4");
				loaded[4]=rs.getInt("money5");
				

				loaded[5]=rs.getInt("fishgot");
				loaded[6]=rs.getInt("fishpoint");
				loaded[7]=rs.getInt("fishtool1");
				loaded[8]=rs.getInt("fishtool2");
				loaded[9]=rs.getInt("fishtool3");
				loaded[10]=rs.getInt("fishtool4");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return loaded;
	}
	public String loadEquipName(String id){
		return game.itemDB.equipItems.get(id).getName();
	}
	public String loadEquipLevel(String id){
		return String.valueOf(game.itemDB.equipItems.get(id).getLevel());
	}
	public String loadEquipType(String id){
		return String.valueOf(game.itemDB.equipItems.get(id).getEquipType());
	}

	/**读取某个物品的图标
	 * @param type 物品类型
	 * @param id 物品ID
	 * @param needSmallerOne 是否获取小图标
	 * */
	public String loadItemIconByTypeID(int type,int id,boolean needSmallerOne){
		String loaded="";
		/*if(type==ITEM_EDIBLE){
			loaded= ((ItemData)game.itemDB.getItem(type, id)).getIconWas();
		}else */if(type==ITEM_BASIC){
			loaded= ((ItemData)game.itemDB.getItem(type, id)).getIconWas();
		}else if(type==ITEM_EQUIP){
			ItemEquipData item=((ItemEquipData)game.itemDB.getItem(type, id));
			String[] iconInfo=new String[4];
			iconInfo[0]=item.getName();
			iconInfo[1]=String.valueOf(item.getLevel());
			iconInfo[2]=String.valueOf(item.getEquipType());
			iconInfo[3]=item.getChatacter();
			if(isAWeaponTypeByType(iconInfo[2])){
				loaded="武器\\"+iconInfo[2]+"\\"+swbq(iconInfo[1])+iconInfo[0];//资源文件里，对武器的编排等级用的三位数字，0补齐，所以这里要三位补全
			}else if(iconInfo[2].equals("铠甲")){
				loaded="装备\\铠甲\\"+iconInfo[3]+"\\"+iconInfo[1]+iconInfo[0];//这些在资源文件里，不是三位补齐的编排了
			}else if(iconInfo[2].equals("头盔")){
				loaded="装备\\头盔\\"+iconInfo[3]+"\\"+iconInfo[1]+iconInfo[0];
			}else if(iconInfo[2].equals("饰品")){
				loaded="装备\\饰品\\"+iconInfo[1]+iconInfo[0];
			}else if(iconInfo[2].equals("鞋子")){
				loaded="装备\\鞋子\\"+iconInfo[1]+iconInfo[0];
			}else if(iconInfo[2].equals("腰带")){
				loaded="装备\\腰带\\"+iconInfo[1]+iconInfo[0];
			}
		}
		
		//资源文件里小图标的编排都是在后面加了一个"_小"
		if(needSmallerOne&&type==2)
			loaded=loaded+"_小";
		else if(needSmallerOne&&type==1)
			loaded=loaded+"_小";
		else if(needSmallerOne&&type==3)
			loaded=loaded+"_小";
		return loaded;
	}
	
	
	
	/**从数据库里读出玩家角色的角色信息
	 * @param id 玩家id
	 * @return 读取后的PlayerData
	 * */
	public PlayerData loadDataById(String id){
		PlayerData temp=new PlayerData();
		
		try {
			if(null!=game&&game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[数据库] -> 准备读取玩家角色数据信息");
				Gdx.app.log("[ XYQ ]", "[数据库] -> 执行：select * from ROLESAVE where ID='"+id+"'");
			}
			
			rs=s.executeQuery("select * from ROLESAVE where ID='"+id+"'");
			
			while (rs.next()){
				temp.id=rs.getString("id");
				temp.name=rs.getString("name");
				temp.level=rs.getInt("level");
				
				temp.popularoty=rs.getInt("popularoty");
				temp.Bangpai=rs.getString("bangpai");
				temp.Menpai=rs.getString("menpai");
				temp.Banggong=rs.getInt("Banggong");
				temp.Mengong=rs.getInt("Mengong");
				
				temp.HPMax=rs.getInt("HPMax");
				temp.HPTempMax=rs.getInt("HPTempMax");
				temp.HP=rs.getInt("HP");
				temp.MPMax=rs.getInt("MPMax");
				temp.MP=rs.getInt("MP");
				temp.SPMax=rs.getInt("SPMax");
				temp.SP=rs.getInt("SP");
				temp.Exp=rs.getInt("Exp");
				
				
				temp.TiliMax=rs.getInt("TiliMax");
				temp.Tili=rs.getInt("Tili");
				temp.HuoliMax=rs.getInt("HuoliMax");
				temp.Huoli=rs.getInt("Huoli");
				

				temp.Mingzhong=rs.getInt("Mingzhong");
				temp.Shanghai=rs.getInt("Shanghai");
				temp.Fangyu=rs.getInt("Fangyu");
				temp.Sudu=rs.getInt("Sudu");
				temp.Duoshan=rs.getInt("Duoshan");
				temp.Lingli=rs.getInt("Lingli");
				

				temp.Tizhi=rs.getInt("Tizhi");
				temp.Moli=rs.getInt("Moli");
				temp.Liliang=rs.getInt("Liliang");
				temp.Naili=rs.getInt("Naili");
				temp.Minjie=rs.getInt("Minjie");
				temp.Qianli=rs.getInt("Qianli");
				
				temp.Role=rs.getString("ROLE");
				temp.Title=rs.getString("Title");
				
				temp.inMapID=rs.getInt("map");
				
				String[] loc=rs.getString("location").split(",");
				int[] meLoc=new int[2];
				meLoc[0]=Integer.valueOf(loc[0]);
				meLoc[1]=Integer.valueOf(loc[1]);
				temp.location=meLoc;
				
				temp.jqPoint=rs.getInt("jqpoint");
				
				int[] colorations=new int[4];
				colorations[0]=rs.getInt("COLORATIONS1");
				colorations[1]=rs.getInt("COLORATIONS2");
				colorations[2]=rs.getInt("COLORATIONS3");
				colorations[3]=rs.getInt("COLORATIONS4");
				temp.colorations=colorations;
				
				HashMap<Integer,ItemStackData> items=loadRoleItemStackData(id);
				temp.items=items;
				
				temp.moneys=loadMoneyData(id);
				
				ArrayList<Summon> summons=game.db.loadSummonData(id);
				temp.summons=summons;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return temp;
	}
	public int loadLevelUpEXP(int currentLevel) {
		int loaded=-1;
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取升级需要的经验值");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select exp from RULE_EXP where level="+currentLevel);
			}
			
			rs=s.executeQuery("select exp from RULE_EXP where level="+currentLevel);
			
			while (rs.next()){
				return rs.getInt("exp");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return loaded;
	}
	public int[] loadMapTransConfig(int map,int x,int y) {
		int trans[]=null;
		trans=new int[3];
		trans[0]=-1;
		trans[1]=-1;
		trans[2]=-1;
		try {
			if(null!=game&&game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[数据库] -> 准备读取地图传送点信息");
				Gdx.app.log("[ XYQ ]", "[数据库] -> 执行：select * from MAPTRANSCONFIG where MAPID="+map+" AND LOCX="+x+" AND LOCY="+y);
			}
			
			rs=s.executeQuery("select * from MAPTRANSCONFIG where MAPID="+map+" AND LOCX="+x+" AND LOCY="+y);
			
			while (rs.next()){
				trans[0]=rs.getInt("tomapid");
				trans[1]=rs.getInt("tox");
				trans[2]=rs.getInt("toy");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return trans;
	}
	public String loadMapTransConfigTips(int map,int x,int y) {
		String temp="";
		try {
			if(null!=game&&game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[数据库] -> 准备读取地图传送点信息");
				Gdx.app.log("[ XYQ ]", "[数据库] -> 执行：select * from MAPTRANSCONFIG where MAPID="+map+" AND LOCX="+x+" AND LOCY="+y);
			}
			
			rs=s.executeQuery("select * from MAPTRANSCONFIG where MAPID="+map+" AND LOCX="+x+" AND LOCY="+y);
			
			while (rs.next()){
				temp=rs.getString("tips");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return temp;
	}
	public ArrayList<Integer> loadAllMapTransConfig(int map) {
		ArrayList<Integer> trans=new ArrayList<Integer>();
		try {
			if(null!=game&&game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[数据库] -> 准备读取地图传送点信息");
				Gdx.app.log("[ XYQ ]", "[数据库] -> 执行：select * from MAPTRANSCONFIG where MAPID="+map);
			}
			
			rs=s.executeQuery("select * from MAPTRANSCONFIG where MAPID="+map);
			
			while (rs.next()){
				trans.add(rs.getInt("locx"));
				trans.add(rs.getInt("locy"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return trans;
	}
	public boolean addMapTransConfig(int map,int x,int y,int toMap,int tox,int toy,String tips) {
		boolean flag=false;
		try {
			StringBuilder ab=new StringBuilder();
			ab.append("INSERT INTO MAPTRANSCONFIG VALUES (");
			ab.append(map);
			ab.append(",");
			ab.append(x);
			ab.append(",");
			ab.append(y);
			ab.append(",");
			ab.append(toMap);
			ab.append(",");
			ab.append(tox);
			ab.append(",");
			ab.append(toy);
			ab.append(",'");
			ab.append(tips);
			ab.append("')");
			if(null!=game&&game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[数据库] -> 准备添加地图传送点信息");
				Gdx.app.log("[ XYQ ]", "[数据库] -> 执行："+ab.toString());
			}
			
			flag=s.execute(ab.toString());
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return flag;
	}
	public boolean removeMapTransConfig(int map,int x,int y) {
		boolean flag=false;
		try {
			if(null!=game&&game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[数据库] -> 准备删除地图传送点信息");
				Gdx.app.log("[ XYQ ]", "[数据库] -> 执行：DELETE FROM MAPTRANSCONFIG where MAPID="+map+" AND LOCX="+x+" AND LOCY="+y);
			}
			
			flag=s.execute("DELETE FROM MAPTRANSCONFIG where MAPID="+map+" AND LOCX="+x+" AND LOCY="+y);
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return flag;
	}
	public boolean addMapLightConfig(int map,int x,int y,float dist,String color,boolean blink,String tips) {
		boolean flag=false;
		try {
			StringBuilder ab=new StringBuilder();
			ab.append("INSERT INTO MAPLIGHTCONFIG VALUES (");
			ab.append(map);
			ab.append(",");
			ab.append(x);
			ab.append(",");
			ab.append(y);
			ab.append(",");
			ab.append(dist);
			ab.append(",'");
			ab.append(color);
			ab.append("',");
			ab.append(blink);
			ab.append(",'");
			ab.append(tips);
			ab.append("',50)");
			if(null!=game&&game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[数据库] -> 准备添加地图静态光源点信息");
				Gdx.app.log("[ XYQ ]", "[数据库] -> 执行："+ab.toString());
			}
			System.out.println(ab.toString());
			flag=s.execute(ab.toString());
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return flag;
	}
	public boolean removeMapLightConfig(int map,int x,int y) {
		boolean flag=false;
		try {
			if(null!=game&&game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[数据库] -> 准备删除地图静态光源点信息");
				Gdx.app.log("[ XYQ ]", "[数据库] -> 执行：DELETE FROM MAPLIGHTCONFIG where MAPID="+map+" AND x="+x+" AND y="+y);
			}
			
			flag=s.execute("DELETE FROM MAPLIGHTCONFIG where MAPID="+map+" AND x="+x+" AND y="+y);
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return flag;
	}
	/**获取门派主技能用基本信息
	 * @return ArrayList[SkillData]
	 * */
	public ArrayList<SkillData> loadSMAllSkill(String menpai){
		ArrayList<SkillData> loaded=new ArrayList<SkillData>();
		ArrayList<String> ids=new ArrayList<String>();
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取门派主技能用基本信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SKILL_MAIN where SCHOOL='"+menpai+"'");
			}
			
			rs=s.executeQuery("select * from SKILL_MAIN where SCHOOL='"+menpai+"'");
			while (rs.next()){
				ids.add(rs.getString("id"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		for(int i=0;i<ids.size();i++){
			String id=ids.get(i);
			SkillData data=getSkillData_ByID(id);
			loaded.add(data);
		}
		return loaded;
	}
	/**获取门派法术信息
	 * @return ArrayList[MagicData]
	 * */
	public ArrayList<MagicData> loadSMAllMagic(String menpai){
		ArrayList<MagicData> loaded=new ArrayList<MagicData>();
		ArrayList<String> ids=new ArrayList<String>();
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取门派主技能用基本信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SKILL_MAGIC where SCHOOL='"+menpai+"'");
			}
			
			rs=s.executeQuery("select * from SKILL_MAGIC where SCHOOL='"+menpai+"'");
			while (rs.next()){
				ids.add(rs.getString("id"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		for(int i=0;i<ids.size();i++){
			String id=ids.get(i);
			MagicData data=getMagicData_ByID(id);
			loaded.add(data);
		}
		return loaded;
	}
	
	/**获取门派主技能基础技能基本信息
	 * @return SkillData {id，名称，描述，效果，解锁技能组(顿号、隔开),是否是基础技能}
	 * */
	public SkillData loadSMBasicSkill(String menpai){
		SkillData loaded=new SkillData();
		String id=null;
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取门派主技能用基本信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SKILL_MAIN where SCHOOL='"+menpai+"' and ISMAIN='是'");
			}
			
			rs=s.executeQuery("select * from SKILL_MAIN where SCHOOL='"+menpai+"' and ISMAIN='是'");

			while (rs.next()){
				id=rs.getString("id");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		loaded=getSkillData_ByID(id);
		return loaded;
	}
	
	/**获取门派主技能学会的点数
	 * @return HashMap 索引是（id, 学会的点数）
	 * */
	public HashMap<String, Integer> loadLearnedSMBasicSkillPoint_toMap() {
		HashMap<String, Integer> loaded=new HashMap<String, Integer>();
		try {
			String plrID=game.ls.player.id;
			String menpai=game.ls.player.playerData().Menpai;
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取学会的门派主技能点数");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from ROLESAVE_SKILL where ROLEID="+plrID+" and SKILLID in (select ID from SKILL_MAIN where school='"+menpai+"')");
			}
			rs=s.executeQuery("select * from ROLESAVE_SKILL where ROLEID="+plrID+" and SKILLID in (select ID from SKILL_MAIN where school='"+menpai+"')");

			
			while (rs.next()){
				loaded.put(rs.getString("skillid"), rs.getInt("level"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return loaded;
	}
	/**获取学会的技能点数
	 * @return HashMap 索引是（id, 学会的点数）
	 * */
	public HashMap<String, Integer> loadLearnedSkillPoint_toMap() {
		HashMap<String, Integer> loaded=new HashMap<String, Integer>();
		try {
			String plrID=game.ls.player.id;
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取学会的技能点数");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from ROLESAVE_SKILL where ROLEID="+plrID+"");
			}
			rs=s.executeQuery("select * from ROLESAVE_SKILL where ROLEID="+plrID+"");

			
			while (rs.next()){
				loaded.put(rs.getString("skillid"), rs.getInt("level"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return loaded;
	}
	/**获取技能信息数据，通过技能ID
	 * */
	public SkillData getSkillData_ByID(String id){
		SkillData skillData=new SkillData();
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取技能信息数据");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SKILL_MAIN where id="+id);
			}
			rs=s.executeQuery("select * from SKILL_MAIN where id="+id);

			
			while (rs.next()){
				skillData.id=rs.getString("id");
				skillData.name=rs.getString("name");
				skillData.school=rs.getString("school");
				skillData.description=rs.getString("description");
				skillData.effection=rs.getString("effection");
				skillData.magic_skill=rs.getString("magic_skill");
				if(skillData.magic_skill.equals("0")){//如果没有可解锁技能
					
				}else{
					String[] names=skillData.magic_skill.split("、");
					ArrayList<String> magicNames=new ArrayList<String>();
					for (int i = 0; i < names.length; i++) {
						magicNames.add(names[i]);
					}
					skillData.magicNames=magicNames;
				}
				
				String isMain=rs.getString("ismain");
				if(isMain!=null&&isMain.equals("是"))
					skillData.isBasic=true;
				else
					skillData.isBasic=false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return skillData;
	}
	/**获取召唤兽升级经验
	 * @param level 召唤兽登记
	 * @return int
	 * */
	public int loadSummonLevelUPExp(int level){
		int loaded=0;
		
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取召唤兽升级经验信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select exp from RULE_SUMMON_EXP where level="+level);
			}
			
			rs=s.executeQuery("select exp from RULE_SUMMON_EXP where level="+level);
			while (rs.next()){
				loaded=rs.getInt("exp");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return loaded;
	}
	/**根据技能名获取ID
	 * @param skillName 技能名
	 * @return id
	 * */
	public int loadSkillMainIDByName(String skillName) {
		int loaded=0;
		
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取技能的id");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select id from SKILL_MAIN where name='"+skillName+"'");
			}
			
			rs=s.executeQuery("select id from SKILL_MAIN where name='"+skillName+"'");
			while (rs.next()){
				loaded=rs.getInt("id");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return loaded;
	}
	
	/**获取某个技能的学会的技能点数
	 * @param id 技能id
	 * @return 学会的点数
	 * */
	public int loadLearnedSkillPoint(int id) {
		int loaded=0;
		
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取技能的id");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select level from ROLESAVE_SKILL where skillid="+id+" and roleid="+game.ls.player.id);
			}
			
			rs=s.executeQuery("select level from ROLESAVE_SKILL where skillid="+id+" and roleid="+game.ls.player.id);
			while (rs.next()){
				loaded=rs.getInt("level");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return loaded;
	}
	/**获取召唤兽参战等级
	 * @param id 召唤兽类型id
	 * @return int
	 * */
	public int loadSummonInBattleLevel(int id){
		int loaded=0;
		
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取召唤兽参战等级信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select level from SUMMON where id="+id);
			}
			
			rs=s.executeQuery("select level from SUMMON where id="+id);
			while (rs.next()){
				loaded=rs.getInt("level");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return loaded;
	}
	/**获取召唤兽基础数据
	 * @param id 召唤兽id
	 * @return SummonDataTemple
	 * */
	public SummonDataModel loadSummonModelData(int id){
		SummonDataModel loaded=new SummonDataModel(id);
		
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取召唤兽基础数据信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SUMMON where id="+id);
			}
			
			rs=s.executeQuery("select * from SUMMON where id="+id);
			while (rs.next()){
				loaded.setName(rs.getString("name"));
				loaded.setLevel(rs.getInt("level"));

				loaded.setAttack(rs.getInt("attack"));
				loaded.setDefence(rs.getInt("defence"));
				loaded.setHealthy(rs.getInt("healthy"));
				loaded.setMagic(rs.getInt("magic"));
				loaded.setSpeed(rs.getInt("speed"));
				loaded.setMiss(rs.getInt("miss"));
				
				loaded.setGrow_1(rs.getFloat("grow1"));
				loaded.setGrow_2(rs.getFloat("grow2"));
				loaded.setGrow_3(rs.getFloat("grow3"));
				loaded.setGrow_4(rs.getFloat("grow4"));
				loaded.setGrow_5(rs.getFloat("grow5"));
				
				loaded.setIs_Special(rs.getBoolean("special"));
				loaded.setShapeID(rs.getInt("shape_id"));
				loaded.setDescription(rs.getString("description"));
				
				ArrayList<String> mustHaveSkill=new ArrayList<String>();
				String muString=rs.getString("musthaveskill");
				String[] muString_a=muString.split("-");
				for(int i=0;i<muString_a.length;i++){
					mustHaveSkill.add(muString_a[i]);
				}
				loaded.setMustSkillName(mustHaveSkill);
				
				ArrayList<String> mayHaveSkill=new ArrayList<String>();
				String maString=rs.getString("mathaveskill");
				String[] maString_a=maString.split("-");
				for(int i=0;i<maString_a.length;i++){
					mayHaveSkill.add(maString_a[i]);
				}
				loaded.setMaySkillName(mayHaveSkill);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return loaded;
	}
	/**获取召唤兽技能的描述
	 * @param skillName 技能名
	 * @return String
	 * */
	public String loadSummonSkillDesc(String skillName) {
		String desc="";
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取召唤兽技能的描述");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SKILL_SUMMON where name='"+skillName+"'");
			}
			rs=s.executeQuery("select * from SKILL_SUMMON where name='"+skillName+"'");

			
			while (rs.next()){
				desc=rs.getString("description");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return desc;
	}
	/**获取玩家拥有的召唤兽所有基础数据
	 * @param id 角色id
	 * @return ArrayList<Summon>
	 * */
	public ArrayList<Summon> loadSummonData(String id){
		ArrayList<Summon> loaded=new ArrayList<Summon>();
		
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取玩家拥有召唤兽数据信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from ROLESAVE_SUMMON where role_id="+id);
			}
			
			rs=s.executeQuery("select * from ROLESAVE_SUMMON where role_id="+id);
			while (rs.next()){
				Summon summon=new Summon();
				summon.setCode_tag(rs.getString("code_tag"));
				summon.setSet_index(rs.getString("set_index"));
				//----------------------------------------------------------------------------
				SummonData data=new SummonData(rs.getInt("type_id"));
				data.setName(rs.getString("name"));
				data.setLevel(rs.getInt("level"));
				
				data.setAttack(rs.getInt("attack_zz"));
				data.setDefence(rs.getInt("defence_zz"));
				data.setHealthy(rs.getInt("healthy_zz"));
				data.setMagic(rs.getInt("magic_zz"));
				data.setSpeed(rs.getInt("speed_zz"));
				data.setMiss(rs.getInt("miss_zz"));
				
				data.setGrow(rs.getFloat("grow"));
				data.setLife(rs.getInt("life"));
				data.setElement(rs.getString("element"));
				
				data.setIs_BB(rs.getBoolean("is_bb"));
				data.setIs_Special(rs.getBoolean("is_special"));
				data.setIs_BY(rs.getBoolean("is_by"));
				
				data.setShapeID(rs.getInt("shape_id"));
				ArrayList<String> skillName=new ArrayList<String>();
				String[] skills=rs.getString("skill").split("-");
				for(int i=0;i<skills.length;i++){
					skillName.add(skills[i]);
				}
				data.setSkillName(skillName);
				
				data.setHP(rs.getInt("hp"));
				data.setMP(rs.getInt("MP"));
				data.setHPMax(rs.getInt("hpmax"));
				data.setMPMax(rs.getInt("MPmax"));
				
				data.setGongji(rs.getInt("gongji"));
				data.setFangyu(rs.getInt("fangyu"));
				data.setSudu(rs.getInt("sudu"));
				data.setLingli(rs.getInt("lingli"));
				
				data.setTizhi(rs.getInt("tizhi"));
				data.setLiliang(rs.getInt("liliang"));
				data.setMoli(rs.getInt("moli"));
				data.setNaili(rs.getInt("naili"));
				data.setMinjie(rs.getInt("minjie"));
				data.setQianli(rs.getInt("qianli"));
				
				data.setAddedTizhi(rs.getInt("added_tizhi"));
				data.setAddedLiliang(rs.getInt("added_liliang"));
				data.setAddedMoli(rs.getInt("added_moli"));
				data.setAddedNaili(rs.getInt("added_naili"));
				data.setAddedMinjie(rs.getInt("added_minjie"));
				
				data.setLoyal(rs.getInt("loyal"));
				data.setExp(rs.getInt("exp"));
				//----------------------------------------------------------------------------
				summon.setData(data);
				summon.setLevelUpExp(loadSummonLevelUPExp(data.level));
				loaded.add(summon);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return loaded;
	}
	/**根据某个召唤兽外形id获取外形名
	 * @param id 召唤兽外形id
	 * @return ArrayList<Summon>
	 * */
	public String loadSummonShapeName(int id){
		String loaded="";
		
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取某个召唤兽外形id获取外形名");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from shape where id="+id);
			}
			
			rs=s.executeQuery("select * from shape where id="+id);
			while (rs.next()){
				loaded=rs.getString("name");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return loaded;
	}
	/**获取技能信息数据，通过技能名
	 * */
	public SkillData getSkillData_ByName(String name){
		SkillData skillData=new SkillData();
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取技能信息数据");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SKILL_MAIN where name='"+name+"'");
			}
			rs=s.executeQuery("select * from SKILL_MAIN where name='"+name+"'");

			
			while (rs.next()){
				skillData.id=rs.getString("id");
				skillData.school=rs.getString("school");
				skillData.name=rs.getString("name");
				skillData.description=rs.getString("description");
				skillData.effection=rs.getString("effection");
				skillData.magic_skill=rs.getString("magic_skill");
				if(skillData.magic_skill.equals("0")){//如果没有可解锁技能
					
				}else{
					String[] names=skillData.magic_skill.split("、");
					ArrayList<String> magicNames=new ArrayList<String>();
					for (int i = 0; i < names.length; i++) {
						magicNames.add(names[i]);
					}
					skillData.magicNames=magicNames;
				}
				
				String isMain=rs.getString("ismain");
				if(isMain!=null&&isMain.equals("是"))
					skillData.isBasic=true;
				else
					skillData.isBasic=false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return skillData;
	}
	/**获取法术信息数据，通过法术ID
	 * */
	public MagicData getMagicData_ByID(String id){
		MagicData skillData=new MagicData();
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取技能信息数据");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SKILL_MAGIC where id="+id);
			}
			rs=s.executeQuery("select * from SKILL_MAGIC where id="+id);

			
			while (rs.next()){
				skillData.id=rs.getString("id");
				skillData.name=rs.getString("name");
				skillData.school=rs.getString("school");
				skillData.description=rs.getString("description");
				skillData.effection=rs.getString("effection");
				skillData.conditions=rs.getString("conditions");
				skillData.consumption=rs.getString("consumption");
				skillData.unlocker=rs.getString("unlocker");
				skillData.unlocker_level=rs.getInt("unlockerlevel");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return skillData;
	}
	/**获取法术信息数据，通过法术名
	 * */
	public MagicData getMagicData_ByName(String name){
		MagicData skillData=new MagicData();
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取技能信息数据");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SKILL_MAGIC where name='"+name+"'");
			}
			rs=s.executeQuery("select * from SKILL_MAGIC where name='"+name+"'");

			
			while (rs.next()){
				skillData.id=rs.getString("id");
				skillData.name=rs.getString("name");
				skillData.school=rs.getString("school");
				skillData.description=rs.getString("description");
				skillData.effection=rs.getString("effection");
				skillData.conditions=rs.getString("conditions");
				skillData.consumption=rs.getString("consumption");
				skillData.unlocker=rs.getString("unlocker");
				skillData.unlocker_level=rs.getInt("unlockerlevel");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return skillData;
	}
	/**获取解锁的师门法术
	 * */
	public ArrayList<MagicData> loadUnlockedSMMagic(){
		/*
		 * 先读取每个师门技能学习的点数
		 * 扫描每个师门法术，通过每个解锁法术，比对学习点数，过的就添加到学会列表里
		 */
		ArrayList<MagicData> skills=new ArrayList<MagicData>();
		ArrayList<MagicData> SMMagics=loadSMAllMagic(game.ls.player.playerData().Menpai);
		HashMap<String, Integer> learnedSkillLevel=loadLearnedSMBasicSkillPoint_toMap();
		
		for(int i=0;i<SMMagics.size();i++){
			MagicData mData=SMMagics.get(i);
			String toSkillID=getSkillData_ByName(mData.unlocker).id;
			
			int learnedPoint=learnedSkillLevel.get(toSkillID);
			if(learnedPoint>=mData.unlocker_level)
				skills.add(mData);
		}
		
		return skills;
	}
	/**获取学会的技能
	 * */
	public ArrayList<SkillData> loadLearnedSkill(){

		ArrayList<SkillData> skills=new ArrayList<SkillData>();
		String plrid=game.ls.player.id;
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取技能信息数据");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SKILL_MAIN where id in (select skillid from ROLESAVE_SKILL where ROLEID="+plrid+")");
			}
			rs=s.executeQuery("select * from SKILL_MAIN where id in (select skillid from ROLESAVE_SKILL where ROLEID="+plrid+")");

			
			while (rs.next()){
				SkillData skillData=new SkillData();
				skillData.id=rs.getString("id");
				skillData.name=rs.getString("name");
				skillData.school=rs.getString("school");
				skillData.description=rs.getString("description");
				skillData.effection=rs.getString("effection");
				skillData.magic_skill=rs.getString("magic_skill");
				if(skillData.magic_skill.equals("0")){//如果没有可解锁技能
					
				}else{
					String[] names=skillData.magic_skill.split("、");
					ArrayList<String> magicNames=new ArrayList<String>();
					for (int i = 0; i < names.length; i++) {
						magicNames.add(names[i]);
					}
					skillData.magicNames=magicNames;
				}
				
				String isMain=rs.getString("ismain");
				if(isMain!=null&&isMain.equals("是"))
					skillData.isBasic=true;
				else
					skillData.isBasic=false;
				
				skills.add(skillData);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return skills;
	}
	/**获取学会的辅助技能
	 * */
	public ArrayList<SkillData> loadLearnedFZSkill(){
		ArrayList<SkillData> skills=new ArrayList<SkillData>();
		ArrayList<SkillData> learned=loadLearnedSkill();
		if(learned.size()==0)
			return skills;
		for (int i = 0; i < learned.size(); i++) {
			SkillData data = learned.get(i);
			if(data.school.equals("辅助"))
				skills.add(data);
		}
		return skills;
	}
	/**获取学会的剧情技能
	 * */
	public ArrayList<SkillData> loadLearnedJQSkill(){
		ArrayList<SkillData> skills=new ArrayList<SkillData>();
		ArrayList<SkillData> learned=loadLearnedSkill();
		if(learned.size()==0)
			return skills;
		for (int i = 0; i < learned.size(); i++) {
			SkillData data = learned.get(i);
			if(data.school.equals("剧情"))
				skills.add(data);
		}
		return skills;
	}
	/**获取所有的装备原始信息*/
	public void loadAllEquip(HashMap<Integer, ItemEquipData> equipItems) {
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取所有的装备数据");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from ITEM_EQUIP");
			}
			rs=s.executeQuery("select * from ITEM_EQUIP");

			
			while (rs.next()){
				ItemEquipData newEquip=new ItemEquipData(ITEM_EQUIP, rs.getInt("id"));
				newEquip.setRES_no(rs.getInt("RES_NO"));
				newEquip.setName(rs.getString("name"));
				newEquip.setLevel(rs.getInt("level"));
				newEquip.setDecription(rs.getString("description"));
				newEquip.setChatacter(rs.getString("chatacter"));
				newEquip.setPrice(rs.getInt("price"));
				newEquip.setAddAttribute1(rs.getInt("add_attribute1"));
				newEquip.setAddAttribute2(rs.getInt("add_attribute2"));
				newEquip.setEquipType(rs.getString("type"));
				equipItems.put(rs.getInt("id"), newEquip);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	/**获取所有的实用道具原始信息*/
	public void loadAllItemsData(HashMap<Integer, ItemData> items) {
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取所有的非装备道具数据");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from ITEM_BASIC");
			}
			rs=s.executeQuery("select * from ITEM_BASIC");

			
			while (rs.next()){
				ItemData newEdible=new ItemData(ITEM_EQUIP, rs.getInt("id"));
				newEdible.setName(rs.getString("name"));
				newEdible.setAttr1(rs.getInt("attr1"));
				newEdible.setAttr2(rs.getInt("attr2"));
				newEdible.setAttr3(rs.getInt("attr3"));
				newEdible.setAttr4(rs.getInt("attr4"));
				newEdible.setAttr5(rs.getInt("attr5"));
				newEdible.setAttr6(rs.getInt("attr6"));
				newEdible.setAttr7(rs.getInt("attr7"));
				newEdible.setAttr8(rs.getInt("attr8"));
				newEdible.setAttr9(rs.getInt("attr9"));
				
				newEdible.setIconWas(rs.getString("icon"));
				newEdible.setFunctionDescription(rs.getString("functiondescription"));
				newEdible.setDescription(rs.getString("description"));
				newEdible.setUseAnimateWas(rs.getString("useanimation"));
				newEdible.setUseSE(rs.getString("useSE"));
				
				newEdible.setFunction_Code(rs.getInt("functioncode"));
				newEdible.setFunction_Tag(rs.getString("functiontag"));
				newEdible.setItemMaxCount(rs.getInt("maxcount"));
				
				items.put(rs.getInt("id"), newEdible);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	/**获取所有的基础道具原始信息*/
	/*
	public void loadAllBasicItem(HashMap<Integer, ItemBasicData> basicItems) {
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取所有的基础道具数据");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from ITEM_BASIC");
			}
			rs=s.executeQuery("select * from ITEM_BASIC");

			
			while (rs.next()){
				ItemBasicData newBasic=new ItemBasicData(ITEM_EQUIP, rs.getInt("id"));
				newBasic.setName(rs.getString("name"));
				newBasic.setAttr1(rs.getInt("attr1"));
				newBasic.setAttr2(rs.getInt("attr2"));
				newBasic.setAttr3(rs.getInt("attr3"));
				newBasic.setAttr4(rs.getInt("attr4"));
				newBasic.setAttr5(rs.getInt("attr5"));
				newBasic.setAttr6(rs.getInt("attr6"));
				newBasic.setAttr7(rs.getInt("attr7"));
				newBasic.setAttr8(rs.getInt("attr8"));
				newBasic.setAttr9(rs.getInt("attr9"));
				
				newBasic.setIconWas(rs.getString("icon"));
				newBasic.setDescription(rs.getString("description"));
				
				newBasic.setFunction_Code(rs.getInt("functioncode"));
				newBasic.setFunction_Tag(rs.getString("functiontag"));
				newBasic.setItemMaxCount(rs.getInt("maxcount"));
				
				basicItems.put(rs.getInt("id"), newBasic);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
	}
	*/
	public HashMap<Integer, MapInfo> loadAllMapInfo(HashMap<Integer, MapInfo> map_infos) {
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取所有的地图基础信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from MAPS");
			}
			rs=s.executeQuery("select * from MAPS");

			
			while (rs.next()){
				MapInfo info=new MapInfo();
				info.setMapID(rs.getInt("id"));
				info.setMapName(rs.getString("name"));
				info.setBGM(rs.getString("bgm"));
				info.setSmallMAP_pack(rs.getString("smallmap_pack"));
				info.setSmallMAP_was(rs.getString("smallmap_was"));
				info.setTips(rs.getString("tips"));
				
				map_infos.put(rs.getInt("id"), info);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return map_infos;
	}
	public HashMap<String, ShapeData> loadAllShapeInfo(HashMap<String, ShapeData> shapes) {
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取所有的外形分布信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from SHAPE");
			}
			rs=s.executeQuery("select * from SHAPE");

			
			while (rs.next()){
				ShapeData shape=new ShapeData();
				shape.setId(rs.getInt("id"));
				shape.setName(rs.getString("name"));
				shape.setPack(rs.getString("PACK"));
				shape.setMaxDirection(rs.getInt("MAXDIRECTION"));
				shape.setType(rs.getString("type"));
				shape.setOffsetX(rs.getFloat("offset_X"));
				shape.setOffsetY(rs.getFloat("offset_Y"));
				
				shape.setColoration_pack(rs.getString("coloration_pack"));
				shape.setColoration_was(rs.getString("coloration_was"));
				
				shape.wases.put(ShapeData.STAND, rs.getString("stand"));
				shape.wases.put(ShapeData.WALK, rs.getString("walk"));

				shape.wases.put(ShapeData.WAIT_1, rs.getString("WAIT_1"));
				shape.wases.put(ShapeData.WAIT_2, rs.getString("WAIT_2"));
				shape.wases.put(ShapeData.ATTACK_1, rs.getString("ATTACK_1"));
				shape.wases.put(ShapeData.ATTACK_2, rs.getString("ATTACK_2"));
				shape.wases.put(ShapeData.HITTEN_1, rs.getString("HITTEN_1"));
				shape.wases.put(ShapeData.HITTEN_2, rs.getString("HITTEN_2"));
				shape.wases.put(ShapeData.DEFENCE_1, rs.getString("DEFENCE_1"));
				shape.wases.put(ShapeData.DEFENCE_2, rs.getString("DEFENCE_2"));
				shape.wases.put(ShapeData.DEAD_1, rs.getString("DEAD_1"));
				shape.wases.put(ShapeData.DEAD_2, rs.getString("DEAD_2"));
				shape.wases.put(ShapeData.GOTO_1, rs.getString("GOTO_1"));
				shape.wases.put(ShapeData.GOTO_2, rs.getString("GOTO_2"));
				shape.wases.put(ShapeData.GOBACK_1, rs.getString("GOBACK_1"));
				shape.wases.put(ShapeData.GOBACK_2, rs.getString("GOBACK_2"));
				shape.wases.put(ShapeData.MAGIC_1, rs.getString("MAGIC_1"));
				shape.wases.put(ShapeData.MAGIC_2, rs.getString("MAGIC_2"));

				shape.wases.put(ShapeData.ANGRY, rs.getString("ANGRY"));
				shape.wases.put(ShapeData.CRY, rs.getString("CRY"));
				shape.wases.put(ShapeData.HELLO, rs.getString("HELLO"));
				shape.wases.put(ShapeData.HI, rs.getString("HI"));
				shape.wases.put(ShapeData.SITDOWN, rs.getString("SITDOWN"));
				shape.wases.put(ShapeData.DANCE, rs.getString("DANCE"));
				
				
				shapes.put(rs.getString("name"), shape);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return shapes;
	}
	/**读取所有的地图光点信息*/
	public ArrayList<GameLight> loadMapALLLight(int mapID,RayHandler rayHandler) {
		ArrayList<GameLight> lights=new ArrayList<GameLight>();
		try {
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]","[数据库] -> 准备读取所有的地图光源信息");
				Gdx.app.log("[ XYQ ]","[数据库] -> 执行：select * from MAPLIGHTCONFIG where mapid="+mapID);
			}
			rs=s.executeQuery("select * from MAPLIGHTCONFIG where mapid="+mapID);
			while (rs.next()){
				float dist=rs.getFloat("dist");
				float x=rs.getFloat("x");
				float y=rs.getFloat("y");
				String colorStr=rs.getString("color");
				boolean isblink=rs.getBoolean("blink");
				int rays=rs.getInt("rays");
				String tag=rs.getString("tags");
				GameLight light=new GameLight(rayHandler, dist, x, y);
		        light.setBlink(isblink);
		        if(colorStr.equals("GOLD"))
		        	light.setColor(Color.GOLD);
		        else if(colorStr.equals("WHITE"))
		        	light.setColor(Color.WHITE);
		        else if(colorStr.equals("GRAY"))
		        	light.setColor(Color.GRAY);
		        else if(colorStr.equals("BLUE"))
		        	light.setColor(Color.BLUE);
		        else if(colorStr.equals("BLACK"))
		        	light.setColor(Color.BLACK);
		        else if(colorStr.equals("RED"))
		        	light.setColor(Color.RED);
		        else
		        	light.setColor(Color.GRAY);
		        light.setRays(rays);
		        light.setTag(tag);
		        lights.add(light);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return lights;
	}

	
	
}
