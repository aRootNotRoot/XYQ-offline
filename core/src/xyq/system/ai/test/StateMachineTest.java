package xyq.system.ai.test;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

import xyq.game.data.NPC;

public class StateMachineTest {
	StateMachine<NPC, NPCState> stateMachine;
 
	StateMachineTest(){
		NPC npc=null;
		stateMachine = new DefaultStateMachine<NPC, NPCState>(npc, NPCState.WORKING);
		stateMachine.changeState(NPCState.GOING_TO_WORK);
	}
}
