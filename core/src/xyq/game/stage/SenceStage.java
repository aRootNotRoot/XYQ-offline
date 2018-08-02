package xyq.game.stage;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import xyq.game.XYQGame;
import xyq.game.stage.UI.BattleHud;
import xyq.game.stage.UI.DialogHud;
import xyq.game.stage.UI.InfoHud;
import xyq.game.stage.UI.MessageHud;
import xyq.game.stage.UI.SystemInfoHud;

public class SenceStage extends Stage{
	XYQGame game;
    
	/**消息显示面板*/
    public  MessageHud mesHud;
	/**信息显示面板*/
	public InfoHud infoHud;
	/**对话框面板*/
    public DialogHud dialogUIHud;
	/**游戏地图*/
    public XYQMapActor map;
	/**更改地图遮罩*/
    public MyActor mapChangingMask;
	/**系统消息面板*/
    public SystemInfoHud systemMessage;
    /**战斗面板*/
    public BattleHud battleHud;
	public SenceStage(final XYQGame game,StretchViewport sv){ 
		super(sv);
		this.game=game;
		this.addEventAdapter();
		
		map=game.maps.getMap(game.ls.player.getInMapID());
		addActor(map);
		
		battleHud=new BattleHud(game);
		addActor(battleHud);
		
		infoHud=new InfoHud(game);
		addActor(infoHud);
		
		dialogUIHud=new DialogHud(game);
		addActor(dialogUIHud);
		
		mesHud=new MessageHud(game);
		addActor(mesHud);
		
		mapChangingMask=new MyActor(new TextureRegion(new Texture(Gdx.files.internal("./assets/maploading1280.jpg"))));
		mapChangingMask.setX(1281);
		mapChangingMask.setY(0);
		addActor(mapChangingMask);
		
		systemMessage=new SystemInfoHud(game);
		addActor(systemMessage);

        Gdx.app.log("[ 提示 ]", "[ SenceStage ] -> 按下【F9】可以切换界面显示");
        Gdx.app.log("[ 提示 ]", "[ SenceStage ] -> 按下【F8】可以标记玩家位置");
        Gdx.app.log("[ 提示 ]", "[ SenceStage ] -> 按下【F7】可以切换是否显示遮罩");
        Gdx.app.log("[ 提示 ]", "[ SenceStage ] -> 按下【F6】可以切换游戏调试状态");
        Gdx.app.log("[ 提示 ]", "[ SenceStage ] -> 按下【F5】可以切换游戏光影状态");
	}
	public void setMap(XYQMapActor map) {
		if(this.map!=null){
			this.map.remove();
			this.map=null;
		}
		this.map = map;
		
		addActor(map);
		//为了保证层级，先把原来的移除在重新添加
		//这样来保证信息面板和对话框面板始终在最前面
		if(this.infoHud!=null){
			this.infoHud.remove();
			addActor(infoHud);
		}
		if(this.dialogUIHud!=null){
			this.dialogUIHud.remove();
			addActor(dialogUIHud);
		}
		if(this.mesHud!=null){
			this.mesHud.remove();
			addActor(mesHud);
		}
		if(this.mapChangingMask!=null){
			this.mapChangingMask.remove();
			addActor(mapChangingMask);
		}
		if(this.systemMessage!=null){
			this.systemMessage.remove();
			addActor(systemMessage);
		}
		
	}
	@Override
	public void draw () {
		super.draw();
		
	}
	public void addEventAdapter(){
		addListener(new MyInputListener(this));
	}
	
	/**
     * 输入事件监听器（包括触屏, 鼠标点击, 键盘按键 的输入）
     */
    private class MyInputListener extends InputListener {
    	SenceStage senceStage;
        /**
         * 当有键盘按键被按下时调用, 参数 keycode 是被按下的按键的键值, 
         * 所有键盘按键的键值常量定义在 com.badlogic.gdx.Input.Keys 类中
         * @param senceStage 
         */
    	public MyInputListener(SenceStage senceStage){
    		this.senceStage=senceStage;
    	}
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.F9: {
                	senceStage.infoHud.setVisible(!senceStage.infoHud.isVisible());
                    break;
                }
                case Input.Keys.F8: {
                	senceStage.infoHud.findMyRole.setVisible(true);
                    break;
                }
                case Input.Keys.F7: {
                	game.ls.ifm.MAP_getCurrentMap().toggleMask();
                    break;
                }
                case Input.Keys.F6: {
                	game.cs.System_toggleDebug();
                    break;
                }
                case Input.Keys.F5: {
                	game.cs.System_toggleLightShadow();
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
        @Override
        public boolean keyUp(InputEvent event, int keycode) {
        	if(keycode==Input.Keys.F8){
            	senceStage.infoHud.findMyRole.setVisible(false);
        		
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
