package xyq.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import xyq.game.XYQGame;

public class SoundManagerBackup {
	XYQGame game;
	public Music BGM;
	public String currentBGM;
	boolean BGM_ON;
	public SoundManagerBackup(XYQGame game){
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
		if(game.am.isLoaded(currentBGM)&&url!=currentBGM){
			game.am.get(currentBGM, Music.class).stop();
			game.am.unload(currentBGM);
			if(game.is_Debug)
				Gdx.app.log("[ XYQ ]", "[SoundManager] -> 停下当前BGM:"+currentBGM);
		}
		this.currentBGM=url;
		game.am.load(currentBGM, Music.class);
		BGM_ON=true;
		if(game.is_Debug)
			Gdx.app.log("[ XYQ ]", "[SoundManager] -> 加载BGM:"+currentBGM);
	}
	
	public void stopBGM() {
		if(game.am.isLoaded(currentBGM)){
			game.am.get(currentBGM, Music.class).stop();
    		if(game.is_Debug)
    			Gdx.app.log("[ XYQ ]", "[SoundManager] -> 停止播放BGM:"+currentBGM);
		}
		
	}
	public void update() {
		if(currentBGM!=null&&!currentBGM.equals("")&&game.am.isLoaded(currentBGM)){
			if(BGM_ON&&!game.am.get(currentBGM, Music.class).isPlaying()){
        		game.am.get(currentBGM, Music.class).setLooping(true);
        		game.am.get(currentBGM, Music.class).play();
        		if(game.is_Debug)
        			Gdx.app.log("[ XYQ ]", "[SoundManager] -> 播放BGM:"+currentBGM);
			}else if(!BGM_ON&&game.am.get(currentBGM, Music.class).isPlaying()){
				stopBGM();
			}
        }
	}
}
