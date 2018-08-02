package xyq.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import xyq.game.XYQGame;

public class SoundManager {
	XYQGame game;
	
	public Music BGM;
	public static boolean BGM_ON=true;
	boolean needChangeBGM;
	public String currentBGM;
	
	public Music BGS;
	
	public Sound SE1;
	public Sound SE2;
	public Sound SE3;
	

	public Sound ME;
	
	public SoundManager(XYQGame game){
		this.game=game;
	}
	public void switchBGM(String url){
		if("".equals(url)||null==url){
			if(game.is_Debug)
				Gdx.app.log("[ XYQ ]", "[SoundManager] -> 传入的BGM参数为空，不做操作");
			return;
		}
		if(null!=currentBGM&&currentBGM.equals(url)){
			if(game.is_Debug)
				Gdx.app.log("[ XYQ ]", "[SoundManager] -> 传入的BGM参数与当前BGM一致，不做操作");
			return;
		}
		
		this.currentBGM=url;
		game.am.load(currentBGM, Music.class);
		needChangeBGM=true;
		if(game.is_Debug)
			Gdx.app.log("[ XYQ ]", "[SoundManager] -> 加载BGM:"+currentBGM);
	}
	
	public void stopBGM() {
		if(BGM!=null&&BGM.isPlaying()){
			BGM.stop();
    		if(game.is_Debug)
    			Gdx.app.log("[ XYQ ]", "[SoundManager] -> 停止播放BGM , 当前BGM目标字符为:"+currentBGM);
		}
		
	}
	public void startBGM() {
		if(BGM!=null&&!BGM.isPlaying()){
			BGM.play();
    		if(game.is_Debug)
    			Gdx.app.log("[ XYQ ]", "[SoundManager] -> 开始播放BGM , 当前BGM目标字符为:"+currentBGM);
		}
		
	}
	public void playSE1(String url){
		if(SE1!=null){
			SE1.stop();
			SE1.dispose();
		}
		SE1=Gdx.audio.newSound(Gdx.files.internal(url));
		SE1.play();
	}
	public void playSE2(String url){
		if(SE2!=null){
			SE2.stop();
			SE2.dispose();
		}
		SE2=Gdx.audio.newSound(Gdx.files.internal(url));
		SE2.play();
	}
	public void playSE3(String url){
		if(SE3!=null){
			SE3.stop();
			SE3.dispose();
		}
		SE3=Gdx.audio.newSound(Gdx.files.internal(url));
		SE3.play();
	}
	public void update() {
		//如果BGM开关被打开，且当前BGM目标字符不为空
		if(BGM_ON&&currentBGM!=null&&!currentBGM.equals("")&&needChangeBGM){
			if(game.am.isLoaded(currentBGM)){
				stopBGM();
				BGM=game.am.get(currentBGM, Music.class);
				BGM.setLooping(true);
				startBGM();
				needChangeBGM=false;
			}
		}
		if (BGM_ON&&!needChangeBGM) {
			startBGM();
		}else{
			stopBGM();
		}
	}
}
