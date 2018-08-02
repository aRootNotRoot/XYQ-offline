package xyq.game.data;

/**Basic表道具模板数据*/
public class ItemBasicData_NOTUSE{
	/**物品类型 [1武器装备][2药品烹饪等有功能描述的道具]【3道具物品】[4..待定]*/
	int itemType;
	/**物品的ID，根据物品类型不同，id有所不同*/
	int itemID;
	/**当前物品的最大堆叠数量*/
	int itemMaxCount;
	/**当前物品的名称*/
	String name;
	
	/**当前物品默认附加属性1*/
	int attr1;
	/**当前物品默认附加属性2*/
	int attr2;
	/**当前物品默认附加属性3*/
	int attr3;
	/**当前物品默认附加属性4*/
	int attr4;
	/**当前物品默认附加属性5*/
	int attr5;
	/**当前物品默认附加属性6*/
	int attr6;
	/**当前物品默认附加属性7*/
	int attr7;
	/**当前物品默认附加属性8*/
	int attr8;
	/**当前物品默认附加属性9*/
	int attr9;
	
	/**was图标名*/
	String iconWas;
	

	/**物品描述*/
	String description;

	/**使用功能代码*/
	public int function_Code;
	/**使用功能代码标注*/
	public String function_Tag;

	/**创建一个物品栏对象*/
	public ItemBasicData_NOTUSE(int itemType,int itemID){
		this.itemType=itemType;
		this.itemID=itemID;
	}
	
	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getItemMaxCount() {
		return itemMaxCount;
	}

	public void setItemMaxCount(int itemMaxCount) {
		this.itemMaxCount = itemMaxCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttr1() {
		return attr1;
	}

	public void setAttr1(int attr1) {
		this.attr1 = attr1;
	}

	public int getAttr2() {
		return attr2;
	}

	public void setAttr2(int attr2) {
		this.attr2 = attr2;
	}

	public int getAttr3() {
		return attr3;
	}

	public void setAttr3(int attr3) {
		this.attr3 = attr3;
	}

	public int getAttr4() {
		return attr4;
	}

	public void setAttr4(int attr4) {
		this.attr4 = attr4;
	}

	public int getAttr5() {
		return attr5;
	}

	public void setAttr5(int attr5) {
		this.attr5 = attr5;
	}

	public int getAttr6() {
		return attr6;
	}

	public void setAttr6(int attr6) {
		this.attr6 = attr6;
	}

	public int getAttr7() {
		return attr7;
	}

	public void setAttr7(int attr7) {
		this.attr7 = attr7;
	}

	public int getAttr8() {
		return attr8;
	}

	public void setAttr8(int attr8) {
		this.attr8 = attr8;
	}

	public int getAttr9() {
		return attr9;
	}

	public void setAttr9(int attr9) {
		this.attr9 = attr9;
	}

	public String getIconWas() {
		return iconWas;
	}

	public void setIconWas(String iconWas) {
		this.iconWas = iconWas;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public int getFunction_Code() {
		return function_Code;
	}

	public void setFunction_Code(int function_Code) {
		this.function_Code = function_Code;
	}

	public String getFunction_Tag() {
		return function_Tag;
	}

	public void setFunction_Tag(String function_Tag) {
		this.function_Tag = function_Tag;
	}
}
