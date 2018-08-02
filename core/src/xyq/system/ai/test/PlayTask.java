package xyq.system.ai.test;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

public class PlayTask extends LeafTask<Dog> {

	public void start () {
		Dog dog = getObject();
		dog.brainLog("WOW - Lets play! 让我们开始玩吧");
	}

	@Override
	public Status execute () {
		Dog dog = getObject();
		dog.brainLog("PANT PANT - So fun 玩啊玩啊");
		return Status.RUNNING;
	}

	@Override
	public void end () {
		Dog dog = getObject();
		dog.brainLog("SIC - No time to play 没时间玩了 :(");
	}

	@Override
	protected Task<Dog> copyTo (Task<Dog> task) {
		return task;
	}
}
