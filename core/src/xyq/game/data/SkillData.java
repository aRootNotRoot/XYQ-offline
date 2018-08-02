package xyq.game.data;

import java.util.ArrayList;

/**技能的数据*/
public class SkillData {
	/**技能ID*/
	public String id;
	/**技能归属，门派*/
	public String school;
	/**技能名*/
	public String name;
	/**技能描述*/
	public String description;
	/**技能效果描述*/
	public String effection;
	/**技能解锁技能组*/
	public String magic_skill;
	/**技能解锁技能名*/
	public ArrayList<String> magicNames;
	/**是否是基础技能*/
	public boolean isBasic;
}
