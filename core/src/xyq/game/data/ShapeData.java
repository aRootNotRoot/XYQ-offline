package xyq.game.data;

import java.util.HashMap;


/**外形数据类，用于定义各种游戏角色外形*/
public class ShapeData {
	public static final String RENWU="人物";
	public static final String ROLE="角色";
	public static final String NPC="NPC";
	public static final String WWAPON="武器";

	public static final String STAND="静立";
	public static final String WALK="行走";
	
	public static final String WAIT_1="待战1";
	public static final String WAIT_2="待战2";
	public static final String ATTACK_1="攻击1";
	public static final String ATTACK_2="攻击2";
	public static final String HITTEN_1="被击中1";
	public static final String HITTEN_2="被击中2";
	public static final String DEFENCE_1="防御1";
	public static final String DEFENCE_2="防御2";
	public static final String DEAD_1="倒地1";
	public static final String DEAD_2="倒地2";
	public static final String GOTO_1="跑去1";
	public static final String GOTO_2="跑去2";
	public static final String GOBACK_1="跑回1";
	public static final String GOBACK_2="跑回2";
	public static final String MAGIC_1="施法1";
	public static final String MAGIC_2="施法2";
	
	public static final String ANGRY="生气";
	public static final String CRY="哭泣";
	public static final String HELLO="行礼";
	public static final String HI="招呼";
	public static final String SITDOWN="坐下";
	public static final String DANCE="舞蹈";
	

	public static final int SOUTH_EAST=0;
	public static final int SOUTH_WEST=1;
	public static final int NORTH_WEST=2;
	public static final int NORTH_EAST=3;
	public static final int SOUTH=4;
	public static final int WEST=5;
	public static final int NORTH=6;
	public static final int EAST=7;
	
	
	/**外形id*/
	public int id;
	/**外形名称*/
	public String name;
	/**外形方向总数（行走，静立），战斗喝彩等动作都是4向*/
	public int maxDirection;
	/**外形类型*/
	public String type;
	/**当前外形绘制偏移X量*/
	public float offsetX;
	/**当前外形绘制偏移Y量*/
	public float offsetY;
	/**当前外形所在的资源包，默认同一个外形都在同一个包里面*/
	public String pack;
	/**储存was的数据【动作，was名】，索引是动作名*/
	public HashMap<String, String> wases;
	/**当前外形换色数据包*/
	public String coloration_pack;
	/**当前外形换色数据文件名*/
	public String coloration_was;
	/*
	 外形如果有多动作，一般是以|为分隔符，{平时状态,战斗武器1状态,战斗武器2状态}
	 */
	public ShapeData(){
		wases=new HashMap<String, String>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxDirection() {
		return maxDirection;
	}

	public void setMaxDirection(int maxDirection) {
		this.maxDirection = maxDirection;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public HashMap<String, String> getWases() {
		return wases;
	}

	public void setWases(HashMap<String, String> wases) {
		this.wases = wases;
	}

	public String getWas(String action) {
		return wases.get(action);
	}

	public String getColoration_pack() {
		return coloration_pack;
	}

	public void setColoration_pack(String coloration_pack) {
		this.coloration_pack = coloration_pack;
	}

	public String getColoration_was() {
		return coloration_was;
	}

	public void setColoration_was(String coloration_was) {
		this.coloration_was = coloration_was;
	}

	public String toString(){
		return "[id="+id+";name="+name+";type="+type+";stand="+wases.get(STAND)+";walk="+wases.get(WALK)+"]";
	}
	
	public static boolean isFourDirAction(String action){
		if(STAND.equals(action)||WALK.equals(action)){
			return false;
		}
		else {
			return true;
		}
	}
}
