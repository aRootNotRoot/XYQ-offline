package xyq.game.stage.UI.dialog;

import java.io.Reader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.StreamUtils;

import xyq.game.XYQGame;
import xyq.game.stage.WasActor;
import xyq.system.ai.test.Dog;

public class AITestDlg extends Group{
	XYQGame game;
	
	WasActor panel;
	Button clzBtn;
	Label label;
	
	Dog dog;
	AITestDlg me;
	public AITestDlg(final XYQGame game){
		super();
		this.game=game;
		me=this;
		setSize(248, 420);
		
		panel=new WasActor(game.rs,"wzife.wdf","UI\\国画1");
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	else if(button==0){
            		Group group=getParent();
            		remove();
            		group.addActor(me);
            	}
            	return false;
            }
        });
		addActor(panel);
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(228, 400);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
        
        TextButton stepBtn=new TextButton("逻辑一步", game.rs.getLongCommonTextButtonStyle());
        stepBtn.setPosition(50, 20);
        stepBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(dog!=null)
        			dog.getBehaviorTree().step();
            }
        });
        addActor(stepBtn); 
        
        label = new Label("", game.rs.getRedLabelStyle(""));
        label.setBounds(95, 100, 350, 30);
		addActor(label);
	
		Gdx.app.log("[ debug ]", "[ 行为树 ] -> 使用测试");
		Reader reader = null;
		try {
			reader = Gdx.files.internal("./assets/ai/dog.tree").reader();
			BehaviorTreeParser<Dog> parser = new BehaviorTreeParser<Dog>(BehaviorTreeParser.DEBUG_NONE);
			dog=new Dog("阿黄");
			BehaviorTree<Dog> tree = parser.parse(reader,dog);
			dog.setBehaviorTree(tree);
			

		} finally {
			StreamUtils.closeQuietly(reader);
		}
		
	}
	
	@Override
	public void act(float delta){

		//GdxAI.getTimepiece().update(delta);
		
	}
}