package xyq.system.ai.npc;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;

import xyq.game.data.NPC;

/**吠叫的动作，继承叶子节点任务*/
public class SayTask extends LeafTask<NPC> {

	@TaskAttribute(required = true) 
	public String words = "";



	@Override
	public void start () {
		super.start();
		
		//开始叫的时候，随机生成叫的次数
	}

	@Override
	public Status execute () {
		NPC me=getObject();
		me.inGame().cs.NPC_Say(me, words);
		
		return Status.SUCCEEDED;
	}

	@Override
	protected Task<NPC> copyTo (Task<NPC> task) {
		SayTask news = (SayTask)task;
		news.words=words;
		//复制当前任务给task
		return news;
	}

	@Override
	public void reset() {
		
		super.reset();
		//重置此任务
	}
	
}