package xyq.game.data;

/**Equip表道具模板数据*/
public class ItemEquipData{
	/**物品类型 [1武器装备][2药品烹饪等有功能描述的道具]【3道具物品】[4..待定]*/
	int itemType;
	/**物品的ID，根据物品类型不同，id有所不同*/
	int itemID;
	/**当前物品的名称*/
	String name;
	/**当前物品的最大堆叠数量*/
	int itemMaxCount;

	/**资源编号：[0头盔][1饰品][2武器][3盔甲][4腰带][5鞋子]*/
	int RES_no;
	/**装备等级*/
	int level;
	/**当前装备的描述*/
	String decription;
	/**当前装备的角色对象/性别*/
	String chatacter;
	/**默认价格*/
	int price;
	/**默认增加属性一底数*/
	int addAttribute1;
	/**默认增加属性二底数*/
	int addAttribute2;
	/**装备类型*/
	String equipType;
	
	/**创建一个物品栏对象*/
	public ItemEquipData(int itemType,int itemID){
		this.itemType=itemType;
		this.itemID=itemID;
		itemMaxCount=1;
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

	public int getRES_no() {
		return RES_no;
	}

	public void setRES_no(int rES_no) {
		RES_no = rES_no;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public String getChatacter() {
		return chatacter;
	}

	public void setChatacter(String chatacter) {
		this.chatacter = chatacter;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAddAttribute1() {
		return addAttribute1;
	}

	public void setAddAttribute1(int addAttribute1) {
		this.addAttribute1 = addAttribute1;
	}

	public int getAddAttribute2() {
		return addAttribute2;
	}

	public void setAddAttribute2(int addAttribute2) {
		this.addAttribute2 = addAttribute2;
	}

	public String getEquipType() {
		return equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}
	
}
