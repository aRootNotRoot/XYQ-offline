package xyq.game.data;

import com.badlogic.gdx.ai.btree.BehaviorTree;

public class NPCBrain {

	public BehaviorTree<NPC> behaviorTree;
	NPC me;
	public NPCBrain(NPC me){
		this.me=me;
	}
}
