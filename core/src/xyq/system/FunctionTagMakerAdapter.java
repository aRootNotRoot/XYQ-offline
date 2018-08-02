package xyq.system;

import xyq.game.data.ItemData;
import xyq.game.data.ItemEquipData;
import xyq.game.data.ItemStackData;

public class FunctionTagMakerAdapter implements FunctionTagMaker{

	@Override
	public ItemStackData makeTag_Basic(ItemStackData item, ItemData model) {
		// TODO Auto-generated method stub
		return item;
	}

	@Override
	public ItemStackData makeTag_Equip(ItemStackData item, ItemEquipData model) {
		// TODO Auto-generated method stub
		return item;
	}




}
