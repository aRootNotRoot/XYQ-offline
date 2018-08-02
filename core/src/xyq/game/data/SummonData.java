package xyq.game.data;

import java.util.ArrayList;

/**召唤兽*/
public class SummonData {
	/**召唤兽ID*/
	public int id;
	/**召唤兽类型ID*/
	public int type_id;
	/**召唤兽名字*/
	public String name;
	/**召唤兽等级*/
	public int level;
	
	/**攻击资质*/
	public int attack;
	/**防御资质*/
	public int defence;
	/**体力资质*/
	public int healthy;
	/**法力资质*/
	public int magic;
	/**速度资质*/
	public int speed;
	/**闪避资质*/
	public int miss;
	/**成长*/
	public float grow;
	/**寿命*/
	public int life;
	/**五行*/
	public String element;
	
	/**是否是神兽*/
	public boolean is_Special;
	/**是否是宝宝*/
	public boolean is_BB;
	/**是否是变异*/
	public boolean is_BY;
	
	/**召唤兽外形数据id*/
	int shapeID;
	/**技能列表*/
	ArrayList<String> skillName;
	
	public int HPMax;
	public int HP;
	public int MPMax;
	public int MP;
	
	public int Gongji;
	public int Fangyu;
	public int Sudu;
	public int Lingli;
	
	public int Tizhi;
	public int Moli;
	public int Liliang;
	public int Naili;
	public int Minjie;
	public int Qianli;
	
	public int addedTizhi;
	public int addedMoli;
	public int addedLiliang;
	public int addedNaili;
	public int addedMinjie;
	
	/**忠诚*/
	public int loyal;
	/**经验*/
	public int exp;
	
	public SummonData(int id){
		skillName=new ArrayList<String>();
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
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public float getGrow() {
		return grow;
	}
	public void setGrow(float grow) {
		this.grow = grow;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public ArrayList<String> getSkillName() {
		return skillName;
	}
	public void setSkillName(ArrayList<String> skillName) {
		this.skillName = skillName;
	}
	public boolean isIs_BB() {
		return is_BB;
	}
	public void setIs_BB(boolean is_BB) {
		this.is_BB = is_BB;
	}
	public boolean isIs_BY() {
		return is_BY;
	}
	public void setIs_BY(boolean is_BY) {
		this.is_BY = is_BY;
	}
	public int getHPMax() {
		return HPMax;
	}
	public void setHPMax(int hPMax) {
		HPMax = hPMax;
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public int getMPMax() {
		return MPMax;
	}
	public void setMPMax(int mPMax) {
		MPMax = mPMax;
	}
	public int getMP() {
		return MP;
	}
	public void setMP(int mP) {
		MP = mP;
	}
	public int getGongji() {
		return Gongji;
	}
	public void setGongji(int gongji) {
		Gongji = gongji;
	}
	public int getFangyu() {
		return Fangyu;
	}
	public void setFangyu(int fangyu) {
		Fangyu = fangyu;
	}
	public int getSudu() {
		return Sudu;
	}
	public void setSudu(int sudu) {
		Sudu = sudu;
	}
	public int getLingli() {
		return Lingli;
	}
	public void setLingli(int lingli) {
		Lingli = lingli;
	}
	public int getTizhi() {
		return Tizhi;
	}
	public void setTizhi(int tizhi) {
		Tizhi = tizhi;
	}
	public int getMoli() {
		return Moli;
	}
	public void setMoli(int moli) {
		Moli = moli;
	}
	public int getLiliang() {
		return Liliang;
	}
	public void setLiliang(int liliang) {
		Liliang = liliang;
	}
	public int getNaili() {
		return Naili;
	}
	public void setNaili(int naili) {
		Naili = naili;
	}
	public int getMinjie() {
		return Minjie;
	}
	public void setMinjie(int minjie) {
		Minjie = minjie;
	}
	public int getQianli() {
		return Qianli;
	}
	public void setQianli(int qianli) {
		Qianli = qianli;
	}
	public int getAddedTizhi() {
		return addedTizhi;
	}
	public void setAddedTizhi(int addedTizhi) {
		this.addedTizhi = addedTizhi;
	}
	public int getAddedMoli() {
		return addedMoli;
	}
	public void setAddedMoli(int addedMoli) {
		this.addedMoli = addedMoli;
	}
	public int getAddedLiliang() {
		return addedLiliang;
	}
	public void setAddedLiliang(int addedLiliang) {
		this.addedLiliang = addedLiliang;
	}
	public int getAddedNaili() {
		return addedNaili;
	}
	public void setAddedNaili(int addedNaili) {
		this.addedNaili = addedNaili;
	}
	public int getAddedMinjie() {
		return addedMinjie;
	}
	public void setAddedMinjie(int addedMinjie) {
		this.addedMinjie = addedMinjie;
	}
	public int getLoyal() {
		return loyal;
	}
	public void setLoyal(int loyal) {
		this.loyal = loyal;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}

	

}
