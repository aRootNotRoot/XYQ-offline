package xyq.game.stage.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

import xyq.game.XYQGame;
import xyq.game.data.BattleData;
import xyq.game.stage.MyActor;

public class BattleHud extends Group{
	public final static int MAX_MEMBER_NUM=20;
	
	/**战斗地图遮罩*/
    public MyActor mapMask;
	
	XYQGame game;
	public BattleHud(XYQGame game){
		super();
		this.game=game;
		
		mapMask=new MyActor(new TextureRegion(new Texture(Gdx.files.internal("./assets/battleBG.png"))));
		mapMask.setX(0);
		mapMask.setY(0);
		addActor(mapMask);
		
		setVisible(false);
	}
	
	public void enterBattle(BattleData battle){
		setVisible(true);
		game.cs.Sound_switchBGM("./sound/BGM/战斗01森林.ogg");
		ctrlUI_enter();
	}
	public void endBattle(BattleData battle){
		setVisible(false);
		game.cs.Sound_switchBGM(game.ls.ifm.MAP_getCurrentMapBGM());
		ctrlUI_end();
	}
	public void ctrlUI_enter(){
		game.ls.player.actor.setVisible(false);
		game.cs.UI_disableInfohudBtns_Battle();
		game.cs.ACT_forzenPlayer();
		game.cs.UI_forzenMapCamera();
		game.cs.UI_hideDialog(DialogHud.playerBagDlg_ID);
		game.cs.UI_hideDialog(DialogHud.playerInfoDlg_ID);
		game.cs.UI_hideDialog(DialogHud.SummonInfoDlg_ID);
		game.cs.UI_hideDialog(DialogHud.SummonSeeDlg_ID);
		game.cs.UI_hideDialog(DialogHud.SummonZiZhiDlg_ID);
		game.cs.UI_hideDialog(DialogHud.smSkillDlg_ID);
		game.cs.UI_hideDialog(DialogHud.fzSkillDlg_ID);
		game.cs.UI_hideDialog(DialogHud.jqSkillDlg_ID);
		game.cs.UI_hideDialog(DialogHud.fishingDlg_ID);
		game.cs.UI_hideDialog(DialogHud.flyPaperDlg_ID);
	}
	
	public void ctrlUI_end(){
		game.ls.player.actor.setVisible(true);
		game.cs.UI_activeInfohudBtns_Battle();
		game.cs.ACT_deForzenPlayer();
		game.cs.UI_deforzenMapCamera();
	}
}
