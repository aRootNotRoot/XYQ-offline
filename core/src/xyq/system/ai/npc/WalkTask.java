package xyq.system.ai.npc;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;

import xyq.game.data.NPC;

/**吠叫的动作，继承叶子节点任务*/
public class WalkTask extends LeafTask<NPC> {

	@TaskAttribute(required = true) 
	public int moveToX = 0;
	@TaskAttribute(required = true) 
	public int moveToY = 0;



	@Override
	public void start () {
		super.start();
		
		//开始叫的时候，随机生成叫的次数
	}

	@Override
	public Status execute () {
		NPC me=getObject();
		if(me.isMovingToTarget())
			return Status.RUNNING;
		else if(me.isMovedToTarget())
			return Status.SUCCEEDED;
		else{
			me.game.cs.NPC_MoveTo(me, moveToX, moveToY);
			return Status.RUNNING;
		}
	}

	@Override
	protected Task<NPC> copyTo (Task<NPC> task) {
		WalkTask news = (WalkTask)task;
		news.moveToX=moveToX;
		news.moveToY=moveToY;
		//复制当前任务给task
		return news;
	}

	@Override
	public void reset() {
		
		super.reset();
		//重置此任务
	}
	
}