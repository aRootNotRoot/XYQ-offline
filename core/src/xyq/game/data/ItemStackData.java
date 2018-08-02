package xyq.game.data;

/**实际的道具数据*/
public class ItemStackData {
	/**物品栏索引[0-5 为装备位置 6-25 为道具栏位置 26-45 为行囊位置 46之后待定]*/
	int stackIndex;
	/**物品类型 [1武器装备][2药品烹饪][3道具物品][4..待定]*/
	int itemType;
	/**物品的ID，根据物品类型不同，id有所不同*/
	int itemID;
	/**当前物品的数量*/
	int itemCount;

	/**当前物品的最大堆叠数量*/
	int itemMaxCount;
	
	/**当前物品附加属性1*/
	int attr1;
	/**当前物品附加属性2*/
	int attr2;
	/**当前物品附加属性3*/
	int attr3;
	/**当前物品附加属性4*/
	int attr4;
	/**当前物品附加属性5*/
	int attr5;
	/**当前物品附加属性6*/
	int attr6;
	/**当前物品附加属性7*/
	int attr7;
	/**当前物品附加属性8*/
	int attr8;
	/**当前物品附加属性9*/
	int attr9;
	

	/**当前物品过期时间的时间戳*/
	long limitTime;

	/**当前物品附加标注1*/
	String tag1="";
	/**当前物品附加标注2*/
	String tag2="";
	/**当前物品附加标注3*/
	String tag3="";
	/**当前物品附加标注4*/
	String tag4="";
	/**当前物品附加标注5*/
	String tag5="";
	
	public int function_Code;
	public String function_Tag;
	
	public ItemStackData copy(){
		ItemStackData newItem=new ItemStackData(0, 0, 0, 0);
		newItem.stackIndex=this.stackIndex;
		newItem.itemType=this.itemType;
		newItem.itemID=this.itemID;
		newItem.itemMaxCount=this.itemMaxCount;
		newItem.itemCount=this.itemCount;
		

		newItem.tag1=this.tag1;
		newItem.tag2=this.tag2;
		newItem.tag3=this.tag3;
		newItem.tag4=this.tag4;
		newItem.tag5=this.tag5;
		

		newItem.function_Code=this.function_Code;
		newItem.function_Tag=this.function_Tag;

		newItem.attr1=this.attr1;
		newItem.attr2=this.attr2;
		newItem.attr3=this.attr3;
		newItem.attr4=this.attr4;
		newItem.attr5=this.attr5;
		newItem.attr6=this.attr6;
		newItem.attr7=this.attr7;
		newItem.attr8=this.attr8;
		newItem.attr9=this.attr9;

		newItem.limitTime=this.limitTime;
		
		return newItem;
	}
	
	public String getTag4() {
		return tag4;
	}

	/**设定蓝色4标签*/
	public void setTag4(String tag4) {
		if(tag4==null)
			return;
		this.tag4 = tag4;
	}
	public String getTag5() {
		return tag5;
	}

	/**设定白色5标签*/
	public void setTag5(String tag5) {
		if(tag5==null)
			return;
		this.tag5 = tag5;
	}
	/**创建一个物品栏对象*/
	public ItemStackData(int stackIndex,int itemType,int itemID,int itemCount){
		this.stackIndex=stackIndex;
		this.itemType=itemType;
		this.itemID=itemID;
		this.itemCount=itemCount;
	}
	
	public int getStackIndex() {
		return stackIndex;
	}
	public void setStackIndex(int stackIndex) {
		this.stackIndex = stackIndex;
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
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public int getItemMaxCount() {
		return itemMaxCount;
	}
	public void setItemMAxCount(int itemCount) {
		this.itemMaxCount = itemCount;
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
	public long getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(long limitTime) {
		this.limitTime = limitTime;
	}
	public String getTag1() {
		return tag1;
	}
	/**设定黄色1标签*/
	public void setTag1(String tag1) {
		if(tag1==null)
			return;
		this.tag1 = tag1;
	}
	public String getTag2() {
		return tag2;
	}
	/**设定黄色2标签*/
	public void setTag2(String tag2) {
		if(tag2==null)
			return;
		this.tag2 = tag2;
	}
	public String getTag3() {
		return tag3;
	}
	/**设定绿色3标签*/
	public void setTag3(String tag3) {
		if(tag3==null)
			return;
		this.tag3 = tag3;
	}
	
	
}
