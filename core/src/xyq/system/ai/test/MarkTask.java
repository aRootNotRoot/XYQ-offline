package xyq.system.ai.test;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;


public class MarkTask extends LeafTask<Dog> {

	int i;

	@Override
	public void start () {
		i = 0;
		getObject().log("Dog lifts a leg and pee! 狗狗抬起了腿开始尿尿了");
	}

	@Override
	public Status execute () {
		Dog dog = getObject();
		Boolean result = dog.markATree(i++);
		//保障至少尿一棵树以上，随机尿几颗
		if (result == null) {//第一次尿是返回null，接下来随机尿
			return Status.RUNNING;
		}
		return result ? Status.SUCCEEDED : Status.FAILED;
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
