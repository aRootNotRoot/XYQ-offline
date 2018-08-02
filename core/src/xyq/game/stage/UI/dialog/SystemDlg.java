package xyq.game.stage.UI.dialog;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.stage.WasActor;

public class SystemDlg extends Group{
	XYQGame game;
	SystemDlg me;
	WasActor panel;
	Button clzBtn;
	Label label;
	public SystemDlg(final XYQGame game){
		super();
		this.game=game;
		me=this;
		setSize(482, 167);
		
		panel=new WasActor(game.rs,"wzife.wdf","UI\\通用对话框中");
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	else 
            		onTop();
            	return false;
            }
        });
		addActor(panel);
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(460, 147);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
        

        String text="Hello Label梦幻西游啊";
        int randomBG=(int)(Math.random()*(16-0)+0);
		switch(randomBG){
			case 1:text="梦幻西游offline V1.1 - 青春一去不复返啦";	break;
			case 2:text="梦幻西游offline V1.1 - 憋9级不要点卡哦";break;
			case 3:text="梦幻西游offline V1.1 - 龙太子龙鳞自产自销";break;
			case 4:text="梦幻西游offline V1.1 - 原来建邺可以出去";break;
			case 5:text="梦幻西游offline V1.1 - 呜呜呜,酒肉欺负我";break;
			case 6:text="梦幻西游offline V1.1 - 龙吟吓遍东海湾啦";break;
			case 7:text="梦幻西游offline V1.1 - 香喷喷的肉包子";break;
			case 8:text="梦幻西游offline V1.1 - 海毛虫不是舞狮子";break;
			case 9:text="梦幻西游offline V1.1 - 变异逍遥生唱歌";break;
			case 10:text="梦幻西游offline V1.1 - 东海湾青蛙养殖厂";break;
			case 11:text="梦幻西游offline V1.1 - 我看到了红色护卫";break;
			case 12:text="梦幻西游offline V1.1 - 北冥有鱼其名为鲲";break;
			case 13:text="梦幻西游offline V1.1 - 牛刀小试？没听过";break;
			case 14:text="梦幻西游offline V1.1 - 泡泡，易燃又美味";break;
			case 15:text="梦幻西游offline V1.1 - 野外猪树！人满开！";break;
			default:text="梦幻西游offline V1.1 - 不知道说什么了";break;
		}
        label = new Label(text, game.rs.getLabelStyle(text));
        label.setBounds(95, 100, 350, 30);
		addActor(label);
		
		TextButton  debugBtn = new TextButton("调试开关", game.rs.getLongCommonTextButtonStyle());
		debugBtn.pad(20);
		debugBtn.setPosition(50, 70);
		debugBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.cs.System_toggleDebug();
            }
        });
        addActor(debugBtn);
        
        TextButton  infoBtn = new TextButton("BGM开关", game.rs.getLongCommonTextButtonStyle());
        infoBtn.pad(20);
        infoBtn.setPosition(200, 70);
        infoBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.cs.System_toggleBGM();
            	
            }
        });
        addActor(infoBtn);
        
        TextButton  exitBtn = new TextButton("退出游戏", game.rs.getLongCommonTextButtonStyle());
        exitBtn.pad(20);
        exitBtn.setPosition(350, 70);
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.cs.System_exit();
            }
        });
        addActor(exitBtn);
		
        TextButton  lightBtn = new TextButton("光影开关", game.rs.getLongCommonTextButtonStyle());
        lightBtn.pad(20);
        lightBtn.setPosition(50, 40);
        lightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.cs.System_toggleLightShadow();
            }
        });
        addActor(lightBtn);
	}
	
	/**置顶显示*/
	public void onTop(){
		Group group=getParent();
		remove();
		group.addActor(me);
	}
	
}
