package xyq.game.stage;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import xyq.game.XYQGame;
import xyq.game.stage.UI.dialog.comp.ShadowLabel;
import xyq.system.utils.RandomUT;

public class LoginStage extends Stage{
	XYQGame game;
	float bgx;
	MyActor bg;
    
	Group firstG;
	Group charactG;
	
	public LoginStage(final XYQGame game,StretchViewport sv){ 
		super(sv);
		this.game=game;
		this.addEventAdapter();
		
		bg=new MyActor(Gdx.files.internal("./assets/UI-启动-主背景3.jpg"));
		bg.setPosition(-10666.66f, -40);
		addActor(bg);
		
		firstG=new Group();
		firstG.setPosition(0, 0);
		firstG.setSize(1280, 720);
		addActor(firstG);
		
		MyActor logo=new MyActor(Gdx.files.internal("./assets/logo2.png"));
		logo.setPosition(443, 500);
		firstG.addActor(logo);
		
		WasActor swk=new WasActor(game.rs, "wzife.wdf", "UI\\启动\\孙悟空");
		swk.setPosition(200, 15);
		firstG.addActor(swk);
		
		WasActor zbj=new WasActor(game.rs, "wzife.wdf", "UI\\启动\\猪八戒");
		zbj.setPosition(400, 5);
		firstG.addActor(zbj);
		
		WasActor ts=new WasActor(game.rs, "wzife.wdf", "UI\\唐僧");
		ts.setPosition(600, 20);
		firstG.addActor(ts);
		
		WasActor shs=new WasActor(game.rs, "wzife.wdf", "UI\\启动\\沙和尚");
		shs.setPosition(850, 5);
		firstG.addActor(shs);
		
		Button enterGameBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\进入游戏", false));
		enterGameBtn.setPosition(1050, 300);
		enterGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	firstG.setVisible(false);
            	charactG.setVisible(true);
            }
        });
		firstG.addActor(enterGameBtn);
		
		/*
		Button regBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\注册账号", false));
		regBtn.setPosition(1050, 250);
		regBtn.setVisible(false);
		regBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Gdx.app.exit();
            }
        });
		firstG.addActor(regBtn);
		*/
		
		Button exitGameBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\启动\\退出按钮", false));
		exitGameBtn.setPosition(1050, 250);
		exitGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Gdx.app.exit();
            }
        });
		firstG.addActor(exitGameBtn);
		
		charactG=new Group();
		charactG.setPosition(0, 0);
		charactG.setSize(1280, 720);
		charactG.setVisible(false);
		addActor(charactG);
		
		Button newRoleBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\启动\\创建按钮", false));
		newRoleBtn.setPosition(590, 100);
		newRoleBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	
            }
        });
		charactG.addActor(newRoleBtn);
		
		Button beginGameBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "按钮\\进入游戏", false));
		beginGameBtn.setPosition(720, 100);
		beginGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.logdone("100001");
            }
        });
		charactG.addActor(beginGameBtn);
		
		Button backBtn = new Button(game.rs.getButtonStyle("wzife.wdf", "UI\\启动\\上一步按钮", false));
		backBtn.setPosition(460, 100);
		backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	firstG.setVisible(true);
            	charactG.setVisible(false);
            }
        });
		charactG.addActor(backBtn);
		
		WasActor todaybg=new WasActor(game.rs, "wzife.wdf", "UI\\今日信息");
		todaybg.setPosition(280, 220);
		charactG.addActor(todaybg);
		
		String year=game.ts.getYear()+"";
		String month=game.ts.getMonth()+"";
		String day=game.ts.getDay()+"";
		String week=game.ts.getWeek();
		
		ShadowLabel yearL=new ShadowLabel(year, game, 380, 535, 80, 20);
		charactG.addActor(yearL);
		
		ShadowLabel monthL=new ShadowLabel(month, game, 300, 503, 80, 20);
		charactG.addActor(monthL);
		
		ShadowLabel dayL=new ShadowLabel(day, game, 365, 503, 80, 20);
		charactG.addActor(dayL); 
		
		ShadowLabel weekL=new ShadowLabel(week, game, 490, 503, 80, 20);
		charactG.addActor(weekL);
		/*
		Label yearL=new Label(year, game.rs.getUILabelStyle());
		yearL.setPosition(380, 537);
		charactG.addActor(yearL);
		*/
		
		WasActor roleInfo=new WasActor(game.rs, "wzife.wdf", "UI\\启动\\名下");
		roleInfo.setPosition(605, 200);
		charactG.addActor(roleInfo);
		
		
		WasActor rolebg=new WasActor(game.rs, "wzife.wdf", "UI\\启动\\角色框");
		rolebg.setPosition(640, 300);
		charactG.addActor(rolebg);
		
		WasActor conlabelbg=new WasActor(game.rs, "wzife.wdf", "文字\\请继续");
		conlabelbg.setPosition(583, 600);
		charactG.addActor(conlabelbg);
		
		
		randomBGMon();
	}
	public void randomBGMon(){
		int i=RandomUT.getRandom(0, 1);
		if(i==0)
			game.sm.switchBGM("./sound/BGM/长寿郊外.ogg");
		else
			game.sm.switchBGM("./sound/BGM/长寿村.ogg");
	}
	@Override
	public void draw () {
		super.draw();
		bgx+=0.5f;
		if(bgx>0f)
			bgx=-10666.66f;
		bg.setX(bgx);
	}
	public void addEventAdapter(){
		addListener(new MyInputListener(this));
	}
	
	/**
     * 输入事件监听器（包括触屏, 鼠标点击, 键盘按键 的输入）
     */
    private class MyInputListener extends InputListener {
    	LoginStage senceStage;
        /**
         * 当有键盘按键被按下时调用, 参数 keycode 是被按下的按键的键值, 
         * 所有键盘按键的键值常量定义在 com.badlogic.gdx.Input.Keys 类中
         * @param senceStage 
         */
    	public MyInputListener(LoginStage senceStage){
    		this.senceStage=senceStage;
    	}
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.F9: {
                	game.logdone("100001");
                	senceStage.getHeight();
                    break;
                }
                /*
                case Input.Keys.RIGHT: {
                	map.mapDrawX--;
                    break;
                }
                case Input.Keys.LEFT: {
                	map.mapDrawX++;
                    break;
                }
                case Input.Keys.UP: {
                	map.mapDrawY--;
                    break;
                }
                case Input.Keys.DOWN: {
                	map.mapDrawY++;
                    break;
                }
                */
                default: {
                    //Gdx.app.log("[ XYQ ]", "其他按键, KeyCode: " + keycode);
                    break;
                }
            }
            return false;
        }

        /**
         * 手指/鼠标 按下时调用
         * 
         * @param x 
         *      按下时的 X 轴坐标, 相对于被触摸对象（监听器注册者）的左下角
         * 
         * @param y 
         *      按下时的 Y 轴坐标, 相对于被触摸对象（监听器注册者）的左下角
         * 
         * @param pointer 
         *      按下手指的ID, 用于多点触控时辨别按下的是第几个手指, 
         *      一般情况下第一只手指按下时 pointer 为 0, 手指未抬起前又有一只手指按下, 则后按下的手指 pointer 为 1。
         *      同一只手指的 按下（touchDown）, 拖动（touchDragged）, 抬起（touchUp）属于同一次序列动作（pointer 值相同）,
         *      pointer 的值在 按下 时被确定, 之后这只手指产生的的 拖动 和 抬起 动作将会把该已确定的 pointer 值传递给其事件方法
         *      touchDragged() 和 touchUp() 方法。 
         * 
         * @return
         *      返回值为 boolean 类型, 用于告诉上一级当前对象（演员/舞台）是否需要处理该次事件。 <br/><br/>
         * 
         *      返回 true: 表示当前对象需要处理该次事件, 则之后这只手指产生的 拖动（touchDragged）和 抬起（touchUp）事件
         *          也会传递到当前对象。<br/><br/>
         * 
         *      返回 false: 表示当前对象不处理该次事件, 既然不处理, 则之后这只手指产生的 拖动（touchDragged）和 抬起（touchUp）事件 
         *          也将不会再传到到当前对象。<br/><br/>
         *      
         *      PS: 当前对象是否处理一只手指的触摸事件（按下, 拖动, 抬起）只在 按下时（touchDown）确定, 
         *          所以之后的 touchDragged() 和 touchUp() 方法中就不再判断, 因此返回类型为 void。
         */
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //Gdx.app.log("[ XYQ ]", "touchDown: " + x + ", " + y + "; pointer: " + pointer);
            return false;
        }

        /**
         * 手指/鼠标 按下后拖动时调用
         */
        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            //Gdx.app.log("[ XYQ ]", "touchDragged: " + x + ", " + y + "; pointer: " + pointer);
        }

        /**
         * 手指/鼠标 抬起时调用
         */
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //Gdx.app.log("[ XYQ ]", "touchUp: " + x + ", " + y + "; pointer: " + pointer);
        }
    }
}
