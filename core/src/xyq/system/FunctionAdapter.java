package xyq.system;

import xyq.game.data.ItemStackData;

public class FunctionAdapter implements FunctionCodeFunc{
	@Override
	/**执行当前代码，如果物品被消耗，则返回真*/
	public boolean exe(ItemStackData item) {
		return false;
	}

}
