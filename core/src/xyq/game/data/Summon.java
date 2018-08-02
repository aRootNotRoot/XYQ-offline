package xyq.game.data;

/**召唤兽*/
public class Summon {
	/**召唤兽数据*/
	SummonData data;
	/**召唤兽标记代码*/
	String code_tag;
	/**召唤兽状态标记*/
	String set_index;
	int levelUpExp;
	
	public SummonData data() {
		return data;
	}

	public void setData(SummonData data) {
		this.data = data;
	}

	/**取标识符，如果是买卖的召唤兽则是价格，否则是防伪标识*/
	public String getCode_tag() {
		return code_tag;
	}

	/**设定标识符，如果是买卖的召唤兽则是价格，否则是防伪标识*/
	public void setCode_tag(String code_tag) {
		this.code_tag = code_tag;
	}

	public int getLevelUpExp() {
		return levelUpExp;
	}

	public void setLevelUpExp(int levelUpExp) {
		this.levelUpExp = levelUpExp;
	}

	public String getSet_index() {
		return set_index;
	}

	public void setSet_index(String set_index) {
		this.set_index = set_index;
	}

	public SummonData getData() {
		return data;
	}
	
}
