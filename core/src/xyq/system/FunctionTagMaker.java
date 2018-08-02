package xyq.system;

import xyq.game.data.ItemData;
import xyq.game.data.ItemEquipData;
import xyq.game.data.ItemStackData;

/**功能代码的接口*/
public interface FunctionTagMaker {
	public ItemStackData makeTag_Basic(ItemStackData item,ItemData model);
	public ItemStackData makeTag_Equip(ItemStackData item,ItemEquipData model);
}
