package xyq.system.ai.npc.condition;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import xyq.game.data.NPC;

/**吠叫的动作，继承叶子节点任务*/
public class NearPlrCondition extends LeafTask<NPC> {


	@Override
	public void start () {
		super.start();
		
		//开始叫的时候，随机生成叫的次数
	}

	@Override
	public Status execute () {
		NPC me=getObject();
		if(me.inGame().ls.ifm.NPC_isNearByPlr(me)){
			return Status.SUCCEEDED;
		}else{
			return Status.FAILED;
		}
	}

	@Override
	protected Task<NPC> copyTo (Task<NPC> task) {
		NearPlrCondition news = (NearPlrCondition)task;
		//复制当前任务给task
		return news;
	}

	@Override
	public void reset() {
		
		super.reset();
		//重置此任务
	}
	
}