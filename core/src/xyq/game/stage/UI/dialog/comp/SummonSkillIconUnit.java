package xyq.game.stage.UI.dialog.comp;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import xyq.game.XYQGame;
import xyq.game.stage.WasActor;

public class SummonSkillIconUnit extends Group{
	XYQGame game;
	SummonSkillDetailPanel skillDetail;
	WasActor icon;
	
	public SummonSkillIconUnit(XYQGame game, String pack, String skillName) {
		this.game=game;
		
		setSize(40, 40);
		
		icon=new WasActor(game.rs, pack, "技能\\召唤兽\\"+skillName);
		addActor(icon);

		skillDetail=new SummonSkillDetailPanel(game);
		skillDetail.setVisible(false);
		skillDetail.setPosition(0, 0);
		skillDetail.updateSkill(skillName);
		addActor(skillDetail);
		
		addListener(new InputListener(){
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				skillDetail.setVisible(true);
				
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				skillDetail.setVisible(false);
			}
			
			public boolean mouseMoved (InputEvent event, float x, float y) {
				skillDetail.setPosition(x+20, y);
				return true;
			}
		});
	}

}
