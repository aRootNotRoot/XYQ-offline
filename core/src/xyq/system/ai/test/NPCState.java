package xyq.system.ai.test;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

import xyq.game.data.NPC;

public enum NPCState implements State<NPC> {
	WORKING{
		@Override
		public void enter(NPC entity) {
			
		}
	
		@Override
		public void update(NPC entity) {
			//entity.getStateMachine().changeState(PersonState.RUN);
		}
	
		@Override
		public void exit(NPC entity) {
			
		}
	
		@Override
		public boolean onMessage(NPC entity, Telegram telegram) {
			return false;
		}
	},
	GOING_TO_WORK{
		@Override
		public void enter(NPC entity) {
			
		}
	
		@Override
		public void update(NPC entity) {
			
		}
	
		@Override
		public void exit(NPC entity) {
			
		}
	
		@Override
		public boolean onMessage(NPC entity, Telegram telegram) {
			return false;
		}
	}
}
