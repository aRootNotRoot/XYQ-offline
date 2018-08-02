package xyq.system.ai.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;

import xyq.system.utils.RandomUT;

/**用一条狗来测试行为树*/
public class Dog {
	/**狗狗的名字*/
	public String name;
	/**脑子在想什么*/
	public String brainLog;
	/**狗狗的行为树*/
	private BehaviorTree<Dog> behaviorTree;
	
	public Dog (String name, BehaviorTree<Dog> btree) {
		this.name = name;
		this.brainLog = name + "[ 脑子想的 ]";
		this.behaviorTree = btree;
		if(btree != null) 
			btree.setObject(this);
	}
	public Dog(String name) {
		this.name = name;
		this.brainLog = name + "[ 脑子想的 ]";
	}
	public BehaviorTree<Dog> getBehaviorTree () {
		return behaviorTree;
	}

	public void setBehaviorTree (BehaviorTree<Dog> behaviorTree) {
		this.behaviorTree = behaviorTree;

	}

	public void bark () {
		if (RandomUT.randomBoolean())
			log("Arf arf   嗷~~~");
		else
			log("Woof  汪！汪！");
	}

	public void startWalking () {
		log("Let's find a nice tree 走吧，去找一个漂亮的树");
	}

	public void randomlyWalk () {
		log("SNIFF SNIFF - Dog walks randomly around!狗狗在周围随意走动");
	}

	public void stopWalking () {
		log("This tree smells good 这个树闻起来不错，不走了 :)");
	}

	public Boolean markATree (int i) {
		if (i == 0) {
			log("Swoosh....尿尿尿");
			return null;
		}
		if (RandomUT.randomBoolean()) {
			log("MUMBLE MUMBLE - Still leaking out，不行还要继续尿");
			return Boolean.FALSE;
		}
		log("I'm ok now :) 尿完了，好开心，这个树是我的了");
		return Boolean.TRUE;
	}
	/**将消息打印出来*/
	public void log (String msg) {
		Gdx.app.log(name, msg);
	}

	/**将脑子里的消息打印出来*/
	public void brainLog (String msg) {
		Gdx.app.log(brainLog, msg);
	}
	
	public void act(int delta){
		
	}
}
