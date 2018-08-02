package xyq.system.ai.test;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

public class WalkTask extends LeafTask<Dog> {

	private int i = 0;

	@Override
	public void start () {
		i = 0;
		Dog dog = getObject();
		dog.startWalking();
	}

	@Override
	public Status execute () {
		i++;
		Dog dog = getObject();
		dog.randomlyWalk();
		//随机走动三次
		return i < 3 ? Status.RUNNING : Status.SUCCEEDED;
	}

	@Override
	public void end () {
		getObject().stopWalking();
	}

	@Override
	protected Task<Dog> copyTo (Task<Dog> task) {
		return task;
	}
	
	@Override
	public void reset() {
		i = 0;
		super.reset();
	}

}
