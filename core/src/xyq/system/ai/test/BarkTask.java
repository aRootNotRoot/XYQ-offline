package xyq.system.ai.test;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.ai.utils.random.ConstantIntegerDistribution;
import com.badlogic.gdx.ai.utils.random.IntegerDistribution;

/**吠叫的动作，继承叶子节点任务*/
public class BarkTask extends LeafTask<Dog> {

	@TaskAttribute
	public IntegerDistribution times = ConstantIntegerDistribution.ONE;
	//我怀疑这个是生成随机数的东西
	private int t;

	@Override
	public void start () {
		super.start();
		t = times.nextInt();
		//开始叫的时候，随机生成叫的次数
	}

	@Override
	public Status execute () {
		//获取狗狗对象
		Dog dog = getObject();
		//叫几次
		for (int i = 0; i < t; i++)
			dog.bark();
		//返回执行成功
		return Status.SUCCEEDED;
	}

	@Override
	protected Task<Dog> copyTo (Task<Dog> task) {
		BarkTask bark = (BarkTask)task;
		bark.times = times;
		//复制当前任务给task
		return task;
	}

	@Override
	public void reset() {
		times = ConstantIntegerDistribution.ONE;
		t = 0;
		super.reset();
		//重置此任务
	}
	
}