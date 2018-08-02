package xyq.system.ai.npc;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import xyq.game.data.NPC;

/**吠叫的动作，继承叶子节点任务*/
public class WaitTask extends LeafTask<NPC> {

	@Override
	public void start () {
		super.start();
		
		
	}

	@Override
	public Status execute () {
		
		return Status.RUNNING;
	}

	@Override
	protected Task<NPC> copyTo (Task<NPC> task) {
		WaitTask news = (WaitTask)task;
		//复制当前任务给task
		return news;
	}

	@Override
	public void reset() {
		
		super.reset();
		//重置此任务
	}
	
}