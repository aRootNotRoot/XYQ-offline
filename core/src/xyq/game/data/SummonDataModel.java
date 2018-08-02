package xyq.game.data;

import java.util.ArrayList;

/**召唤兽原始数据*/
public class SummonDataModel {
	/**召唤兽ID*/
	public int id;

	/**召唤兽名*/
	public String name;
	/**召唤兽携带等级*/
	public int level;
	
	/**最大攻击资质*/
	public int attack;
	/**最大防御资质*/
	public int defence;
	/**最大体力资质*/
	public int healthy;
	/**最大法力资质*/
	public int magic;
	/**最大速度资质*/
	public int speed;
	/**最大闪避资质*/
	public int miss;
	
	/**成长_1*/
	public float grow_1;
	/**成长_2*/
	public float grow_2;
	/**成长_3*/
	public float grow_3;
	/**成长_4*/
	public float grow_4;
	/**成长_5*/
	public float grow_5;
	
	/**是否是神兽*/
	public boolean is_Special;
	
	/**召唤兽外形数据id*/
	int shapeID;
	/**必带技能id列表*/
	ArrayList<String> mustSkillName;
	/**除必带外可能带技能id列表*/
	ArrayList<String> maySkillName;
	
	/**召唤兽介绍*/
	public String description;
	
	public SummonDataModel(int id){
		mustSkillName=new ArrayList<String>();
		maySkillName=new ArrayList<String>();
		setId(id);
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getHealthy() {
		return healthy;
	}

	public void setHealthy(int healthy) {
		this.healthy = healthy;
	}

	public int getMagic() {
		return magic;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getMiss() {
		return miss;
	}

	public void setMiss(int miss) {
		this.miss = miss;
	}

	public boolean isIs_Special() {
		return is_Special;
	}

	public void setIs_Special(boolean is_Special) {
		this.is_Special = is_Special;
	}

	public int getShapeID() {
		return shapeID;
	}

	public void setShapeID(int shapeID) {
		this.shapeID = shapeID;
	}

	public void setMustSkillID(ArrayList<String> mustSkillID) {
		this.mustSkillName = mustSkillID;
	}

	public void setMaySkillID(ArrayList<String> maySkillID) {
		this.maySkillName = maySkillID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public float getGrow_1() {
		return grow_1;
	}
	public void setGrow_1(float grow_1) {
		this.grow_1 = grow_1;
	}
	public float getGrow_2() {
		return grow_2;
	}
	public void setGrow_2(float grow_2) {
		this.grow_2 = grow_2;
	}
	public float getGrow_3() {
		return grow_3;
	}
	public void setGrow_3(float grow_3) {
		this.grow_3 = grow_3;
	}
	public float getGrow_4() {
		return grow_4;
	}
	public void setGrow_4(float grow_4) {
		this.grow_4 = grow_4;
	}
	public float getGrow_5() {
		return grow_5;
	}
	public void setGrow_5(float grow_5) {
		this.grow_5 = grow_5;
	}
	public ArrayList<String> getMustSkillName() {
		return mustSkillName;
	}
	public void setMustSkillName(ArrayList<String> mustSkillName) {
		this.mustSkillName = mustSkillName;
	}
	public ArrayList<String> getMaySkillName() {
		return maySkillName;
	}
	public void setMaySkillName(ArrayList<String> maySkillName) {
		this.maySkillName = maySkillName;
	}
	
	
}
