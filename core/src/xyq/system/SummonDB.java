package xyq.system;

import java.util.ArrayList;
import java.util.HashMap;

import xyq.game.XYQGame;
import xyq.game.data.Summon;
import xyq.game.data.SummonData;
import xyq.game.data.SummonDataModel;
import xyq.system.utils.RandomUT;

/**召唤兽数据管理器*/
public class SummonDB {
	/**资质下限比率*/
	final float LOW_LIM=0.8f;
	/**爆资质上限比率*/
	final float HIGH_LIM=1.1f;
	/**随机寿命下限*/
	final int LOW_LIFE=5000;
	/**随机寿命上限*/
	final int HIGH_LIFE=9000;
	/**变异几率,如果为50则为50分之1的概率变异*/
	final int BY_LIM=128;
	/**宝宝几率,如果为50则为50分之1的概率变异*/
	final int BB_LIM=100;
	/**技能获得几率,如果为3则为3分之1的概率获得*/
	final int SKILL_LIM=4;
	
	XYQGame game;
	public HashMap<Integer, SummonDataModel> summons;
	
	public SummonDB(XYQGame game){
		this.game=game;
		summons=new HashMap<Integer, SummonDataModel>();
	}
	/**按照id获取一个召唤兽数据*/
	public SummonDataModel getModel(int id) {
		SummonDataModel su=summons.get(id);
		if(su==null){
			su=game.db.loadSummonModelData(id);
			if(su!=null)
				summons.put(id, su);
		}
		return su;
	}
	/**计算气血=等级*体力资质/1000+体质点数*成长*6*/
	public int calcHP(SummonData data){
		float hp;
		hp=data.level*data.healthy/1000+data.Tizhi*data.grow*6;
		return (int)hp;
	}
	/**计算魔法=等级*法力资质/500+法力点数*成长*3*/
	public int calcMP(SummonData data){
		float mp;
		mp=data.level*data.magic/500+data.Moli*data.grow*3;
		return (int)mp;
	}
	/**计算攻击力=等级*攻击资质*(14+10*成长)/7500+力量点数*成长*/
	public int calcGongji(SummonData data){
		float attack;
		attack=data.level*data.attack*(14+10*data.grow)/7500+data.Liliang*data.grow;
		return (int)attack;
	}
	/**计算防御=等级*防御资质*(9.4+19/3*成长)/7500+耐力点数*成长*4/3*/
	public int calcFangyu(SummonData data){
		float attack;
		attack=data.level*data.defence*(9.4f+19f/3f*data.grow)/7500f+data.Naili*data.grow*4f/3f;
		return (int)attack;
	}
	/**计算速度=敏捷点数*速度资质/1000*/
	public int calcSudu(SummonData data){
		int speed;
		speed=data.Minjie*data.speed/1000;
		return speed;
	}
	/**计算灵力=等级*(法力资质+1662)*(1+成长)/7500+体质点数*0.3+耐力点数*0.2+力量点数*0.4+法力点数*0.7*/
	public int calcLingli(SummonData data){
		float speed;
		speed=data.level*(data.magic+1662)*(1+data.grow)/7500+data.Tizhi*0.3f+data.Naili*0.2f+data.Liliang*0.4f+data.Moli*0.7f;
		return (int)speed;
	}
	/**制作一个0级非宝宝召唤兽数据,全随机
	 * @param id 召唤兽类型id
	 * */
	public SummonData makeSummonData(int id){
		return makeSummonData(id,0,false,false);
	}
	/**制作一个召唤兽数据,全随机
	 * @param id 召唤兽类型id
	 * @param level 召唤兽等级
	 * @param is_BB 是否是宝宝
	 * @param is_BY 是否是变异
	 * */
	public SummonData makeSummonData(int id,int level,boolean is_BB,boolean is_BY){
		SummonData summon=new SummonData(id);
		
		SummonDataModel model=getModel(id);
		//设定资质
		if(!model.is_Special){//如果不是神兽
			if(!is_BY){//如果不是变异的就不会爆资质
				summon.setAttack((int)RandomUT.getRandom(Float.valueOf(model.getAttack())*LOW_LIM, Float.valueOf(model.getAttack())));
				summon.setDefence((int)RandomUT.getRandom(Float.valueOf(model.getDefence())*LOW_LIM, Float.valueOf(model.getDefence())));
				summon.setHealthy((int)RandomUT.getRandom(Float.valueOf(model.getHealthy())*LOW_LIM, Float.valueOf(model.getHealthy())));
				summon.setMagic((int)RandomUT.getRandom(Float.valueOf(model.getMagic())*LOW_LIM, Float.valueOf(model.getMagic())));
				summon.setSpeed((int)RandomUT.getRandom(Float.valueOf(model.getSpeed())*LOW_LIM, Float.valueOf(model.getSpeed())));
				summon.setMiss((int)RandomUT.getRandom(Float.valueOf(model.getMiss())*LOW_LIM, Float.valueOf(model.getMiss())));
			}else{//如果是变异的则会突破上限
				summon.setAttack((int)RandomUT.getRandom(Float.valueOf(model.getAttack())*LOW_LIM, HIGH_LIM*Float.valueOf(model.getAttack())));
				summon.setDefence((int)RandomUT.getRandom(Float.valueOf(model.getDefence())*LOW_LIM, HIGH_LIM*Float.valueOf(model.getDefence())));
				summon.setHealthy((int)RandomUT.getRandom(Float.valueOf(model.getHealthy())*LOW_LIM, HIGH_LIM*Float.valueOf(model.getHealthy())));
				summon.setMagic((int)RandomUT.getRandom(Float.valueOf(model.getMagic())*LOW_LIM, HIGH_LIM*Float.valueOf(model.getMagic())));
				summon.setSpeed((int)RandomUT.getRandom(Float.valueOf(model.getSpeed())*LOW_LIM, HIGH_LIM*Float.valueOf(model.getSpeed())));
				summon.setMiss((int)RandomUT.getRandom(Float.valueOf(model.getMiss())*LOW_LIM, HIGH_LIM*Float.valueOf(model.getMiss())));
			
			}
		}
		else{//如果是神兽
			summon.setAttack(model.getAttack());
			summon.setDefence(model.getDefence());
			summon.setHealthy(model.getHealthy());
			summon.setMagic(model.getMagic());
			summon.setSpeed(model.getSpeed());
			summon.setMiss(model.getMiss());
		
		}
		//设定召唤兽类型id
		summon.setType_id(id);
		
		//设定五行
		String[] element={"金","木","水","火","土"};
		int i=RandomUT.getRandom(0, 4);
		summon.setElement(element[i]);
		
		//设定成长
		i=RandomUT.getRandom(0, 5);
		switch(i){
			case 0:
				summon.setGrow(model.getGrow_1());
				break;
			case 1:
				summon.setGrow(model.getGrow_2());
				break;
			case 2:
				summon.setGrow(model.getGrow_3());
				break;
			case 3:
				summon.setGrow(model.getGrow_4());
				break;
			case 4:
				summon.setGrow(model.getGrow_5());
				break;
			default:
				summon.setGrow(model.getGrow_1());
				break;
		}
		
		//设定寿命
		if(model.is_Special)
			summon.setLife(99999);
		else
			summon.setLife(RandomUT.getRandom(LOW_LIFE, HIGH_LIFE));
		
		//设定属性
		int[] attr;
		int point;
		if(is_BB){
			point=100+level*10;
		}else{//非宝宝50点属性点
			point=50+level*10;
		}
		attr=RandomUT.getDevide(5, point,5);
		
		summon.setTizhi(attr[0]);
		summon.setMoli(attr[1]);
		summon.setLiliang(attr[2]);
		summon.setNaili(attr[3]);
		summon.setMinjie(attr[4]);
		
		//设定等级
		summon.setLevel(level);
		
		//设定属性结果
		summon.setGongji(calcGongji(summon));
		summon.setFangyu(calcFangyu(summon));
		summon.setSudu(calcSudu(summon));
		summon.setLingli(calcLingli(summon));
		
		summon.setHP(calcHP(summon));
		summon.setHPMax(calcHP(summon));
		summon.setMP(calcMP(summon));
		summon.setMPMax(calcMP(summon));
		
		//设定忠诚
		summon.setLoyal(100);
		
		//设定名字
		summon.setName(model.name);
		
		//设定外形id
		summon.setShapeID(model.getShapeID());
		
		ArrayList<String> skillName=new ArrayList<String>();
		ArrayList<String> mustSkill=model.getMustSkillName();
		ArrayList<String> maySkill=model.getMaySkillName();
		for(int j=0;j<maySkill.size();j++){
			if(RandomUT.isGot(SKILL_LIM)&&!maySkill.get(j).equals(""))
				skillName.add(maySkill.get(j));
		}
		for(int j=0;j<mustSkill.size();j++){
			if(!mustSkill.get(j).equals(""))
				skillName.add(mustSkill.get(j));
		}
		summon.setSkillName(skillName);
		
		summon.setIs_BB(is_BB);
		summon.setIs_BY(is_BY);
		
		return summon;
	}
	/**制作一个召唤兽数据,全随机数据，随机变异
	 * @param id 召唤兽类型id
	 * @param level 召唤兽等级
	 * @param is_BB 是否是宝宝
	 * */
	public SummonData makeSummonData(int id,int level,boolean is_BB){
		boolean is_BY=false;
		if(RandomUT.isGot(BY_LIM)){
			is_BY=true;
			is_BB=true;
		}
		return makeSummonData(id,level,is_BB,is_BY);
		
	}
	/**制作一个召唤兽数据,全随机数据，随机变异,随机宝宝
	 * @param id 召唤兽类型id
	 * @param level 召唤兽等级
	 * */
	public SummonData makeSummonData(int id,int level){
		boolean is_BY=false;
		boolean is_BB=false;
		if(RandomUT.isGot(BY_LIM)){//如果变异了，那一定是宝宝
			is_BY=true;
			is_BB=true;
		}else if(RandomUT.isGot(BB_LIM)){//如果没变异，再看宝宝了不
			is_BB=true;
		}
		return makeSummonData(id,level,is_BB,is_BY);
		
	}
	/**制作一个0级非宝宝召唤兽,全随机*/
	public Summon makeSummon(SummonData data){
		Summon summon=new Summon();
		summon.setData(data); 
		summon.setCode_tag(RandomUT.getRandomStr(8));
		summon.setSet_index("携带");
		
		return summon;
	}
	/**制作一个召唤兽,全随机*/
	public Summon makeSummon(int id,int level,boolean is_BB,boolean is_BY){
		Summon summon=new Summon();
		SummonData data=makeSummonData(id,level,is_BB,is_BY);
		summon.setData(data); 
		summon.setCode_tag(RandomUT.getRandomStr(8));
		summon.setSet_index("携带");
		summon.setLevelUpExp(game.db.loadSummonLevelUPExp(level));
		return summon;
	}
	public SummonData reCalcAttr(SummonData data) {
		data.HPMax=calcHP(data);
		data.MPMax=calcMP(data);
		data.Gongji=calcGongji(data);
		data.Fangyu=calcFangyu(data);
		data.Sudu=calcSudu(data);
		data.Lingli=calcLingli(data);
		return data;
	}
}

/*
召唤兽气血=等级*体力资质/1000+体质点数*成长*6
召唤兽魔法=等级*法力资质/500+法力点数*成长*3
召唤兽攻击=等级*攻击资质*(14+10*成长)/7500+力量点数*成长
召唤兽防御=等级*防御资质*(9.4+19/3*成长)/7500+耐力点数*成长*4/3
召唤兽速度=敏捷点数*速度资质/1000
召唤兽灵力=等级*(法力资质+1662)*(1+成长)/7500+体质点数*0.3+耐力点数*0.2+力量点数*0.4+法力点数*0.7

野生的0级召唤兽 0级的时候 所有属性点加起来是50点，也就是五项都是10；
宝宝的0级召唤兽 0级的时候 所有属性点加起来是100点，五项属性点随机给的！
 */
