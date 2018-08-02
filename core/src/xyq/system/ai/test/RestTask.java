package xyq.system.ai.test;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

public class RestTask extends LeafTask<Dog> {

	@Override
	public void start () {
		getObject().brainLog("YAWN - So tired...啊~~~好累了");
	}

	@Override
	public Status execute () {
		getObject().brainLog("zz zz zz ZZZZ");
		return Status.RUNNING;
	}

	@Override
	public void end () {
		getObject().brainLog("SOB - Time to wake up 该起床了");
	}

	@Override
	protected Task<Dog> copyTo (Task<Dog> task) {
		return task;
	}

}
