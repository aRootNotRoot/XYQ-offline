package xyq.game.stage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import xyq.system.assets.ResSystem;

/**
 * 自定义聊天泡泡演员（绘制时处理 位置，尺寸，缩放比，旋转角度 和 color/alpha 等属性）
 */
public class ChatActor extends Actor {
	ResSystem rs;
    public ChatActor(ResSystem RS) {
        super();
        this.rs=RS;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float x=getX();
        float y=getY()-getHeight();
        
        batch.draw(rs.chat_LD ,x,y);
        batch.draw(rs.chat_MD   ,x+8,y,getWidth()-16,8);
        batch.draw(rs.chat_RD ,x+getWidth()-8,y);
        
        batch.draw(rs.chat_MD    ,x,y+8,8,getHeight()-16);
        batch.draw(rs.chat_MD,x+8,y+8,getWidth()-16,getHeight()-16);
        batch.draw(rs.chat_MD   ,x+getWidth()-8,y+8,8,getHeight()-16);
        
        batch.draw(rs.chat_LU ,x,y+getHeight()-8);
        batch.draw(rs.chat_MD   ,x+8,y+getHeight()-8,getWidth()-16,8);
        batch.draw(rs.chat_RU ,x+getWidth()-8,y+getHeight()-8);
    }
}