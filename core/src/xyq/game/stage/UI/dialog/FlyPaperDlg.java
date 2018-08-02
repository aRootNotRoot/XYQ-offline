package xyq.game.stage.UI.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyq.game.XYQGame;
import xyq.game.stage.WasActor;

/**飞行符对话框*/
public class FlyPaperDlg extends Group{
	XYQGame game;
	
	WasActor panel;
	Button clzBtn;
	
	WasActor chang_an;
	WasActor jian_ye;
	WasActor ao_lai;
	WasActor chang_shou;
	WasActor xi_liang;
	WasActor zhu_zi;
	
	FlyPaperDlg me;
	public FlyPaperDlg(final XYQGame game){
		super();
		this.game=game;
		me=this;
		
		setSize(581, 309);
		
		panel=new WasActor(game.rs,"wzife.wdf","UI\\飞行符图");
		panel.setPosition(0, 0);
		panel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	return false;
            }
        });
		addActor(panel);
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(561, 289);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
        
        chang_an=new WasActor(game.rs,"wzife.wdf","小地图\\长安图标");
        chang_an.setPosition(308, 134);
        chang_an.addListener(new ClickListener() {
        	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
        		chang_an.setPosition(310, 134);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				chang_an.setPosition(308, 134);
			}
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
            		me.game.cs.ACT_flyTo(1001, 360, 244);
            		setVisible(false);
            	return false;
            }
        });
		addActor(chang_an);
		
		jian_ye=new WasActor(game.rs,"wzife.wdf","小地图\\建邺城贴图");
		jian_ye.setPosition(355, 76);
		jian_ye.addListener(new ClickListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				jian_ye.setPosition(357, 76);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				jian_ye.setPosition(355, 76);
			}
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
            		me.game.cs.ACT_flyTo(1501, 55, 25);
            		setVisible(false);
            	return false;
            }
        });
		addActor(jian_ye);
		
		zhu_zi=new WasActor(game.rs,"wzife.wdf","小地图\\朱紫国贴图");
		zhu_zi.setPosition(14, 125);
		zhu_zi.addListener(new ClickListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				zhu_zi.setPosition(16, 125);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				zhu_zi.setPosition(14, 125);
			}
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
            		me.game.cs.ACT_flyTo(1208, 126, 83);
            		setVisible(false);
            	return false;
            }
        });
		addActor(zhu_zi);
		
		ao_lai=new WasActor(game.rs,"wzife.wdf","图标\\傲来地图图标");
		ao_lai.setPosition(505, 19);
		ao_lai.addListener(new ClickListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				ao_lai.setPosition(507, 19);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				ao_lai.setPosition(505, 19);
			}
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
            		me.game.cs.ACT_flyTo(1092, 122, 94);
            		setVisible(false);
            	return false;
            }
        });
		addActor(ao_lai);
		
		chang_shou=new WasActor(game.rs,"wzife.wdf","小地图\\长寿图标");
		chang_shou.setPosition(24, 249);
		chang_shou.addListener(new ClickListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				chang_shou.setPosition(26, 249);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				chang_shou.setPosition(24, 249);
			}
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
            		me.game.cs.ACT_flyTo(1070, 106, 59);
            		setVisible(false);
            	return false;
            }
        });
		addActor(chang_shou);
		
		xi_liang=new WasActor(game.rs,"wzife.wd1","小地图\\西梁女国");
		xi_liang.setPosition(41, 176);
		xi_liang.addListener(new ClickListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				xi_liang.setPosition(43, 176);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				xi_liang.setPosition(41, 176);
			}
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
            		me.game.cs.ACT_flyTo(1040, 84, 62);
            		setVisible(false);
            	return false;
            }
        });
		addActor(xi_liang);
	}
	/**置顶显示*/
	public void onTop(){
		Group group=getParent();
		remove();
		group.addActor(me);
	}

}
