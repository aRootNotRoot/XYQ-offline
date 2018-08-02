package xyq.system.ai.test;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;


public class CareTask extends LeafTask<Dog> {
	//在tree文件里，(required = true)代表树定义后面需要跟上这个参数
	@TaskAttribute(required = true) 
	public float urgentProb = 0.8f;
	//tree文件里care urgentProb:0.8

	@Override
	public Status execute () {
		if (Math.random() < urgentProb) {
			return Status.SUCCEEDED;
		}
		Dog dog = getObject();
		dog.brainLog("GASP - Something urgent 啊！尿急 :/");
		return Status.FAILED;
	}

	@Override
	protected Task<Dog> copyTo (Task<Dog> task) {
		CareTask care = (CareTask)task;
		care.urgentProb = urgentProb;

		return task;
	}

	@Override
	public void reset() {
		urgentProb = 0.8f;
		super.reset();
	}
	
}
