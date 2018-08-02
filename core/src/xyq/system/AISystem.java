package xyq.system;

import java.io.Reader;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.StreamUtils;

import xyq.game.XYQGame;
import xyq.game.data.NPC;

public class AISystem {
	XYQGame game;
	Reader reader = null;
	public AISystem(XYQGame game){
		this.game=game;
		
	}
	
	public BehaviorTree<NPC> readAI(FileHandle file,NPC npc){
		BehaviorTree<NPC> tree =null;
		try {
			reader = file.reader();
			BehaviorTreeParser<NPC> parser = new BehaviorTreeParser<NPC>(BehaviorTreeParser.DEBUG_NONE);
			tree = parser.parse(reader,npc);
			npc.setBehaviorTree(tree);
			

		} finally {
			StreamUtils.closeQuietly(reader);
		}
		
		return tree;
	}
	
}
