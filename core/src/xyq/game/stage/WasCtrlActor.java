package xyq.game.stage;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import xyq.system.assets.PPCData;
import xyq.system.assets.ResSystem;
import xyq.system.assets.WasCtrlImage;

/**
 * 自定义演员（绘制时处理 位置，尺寸，缩放比，旋转角度 和 color/alpha 等属性,同时可以确定起始与结束帧）
 */
public class WasCtrlActor extends Actor {

    public WasCtrlImage img;
    float drawOffsetX;
	float drawOffsetY;
    public WasCtrlActor(ResSystem RS,String pack,String was) {
        super();
        this.img = new WasCtrlImage(RS,pack,was,null);
        setSize(this.img.getW(), this.img.getH());
    }
    public WasCtrlActor(ResSystem RS,String pack,String was,PPCData pp) {
        super();
        this.img = new WasCtrlImage(RS,pack,was,pp);
        setSize(this.img.getW(), this.img.getH());
    }
    public WasCtrlImage getImg() {
        return img;
    }
    public void setFaceTo(int face){
    	img.setDirect(face);
    }
    public void setImg(WasCtrlImage region) {
        this.img = region;
        setSize(this.img.getW(), this.img.getH());
    }
    
    public float getDrawOffsetX() {
		return drawOffsetX;
	}

	public void setDrawOffsetX(float drawOffsetX) {
		this.drawOffsetX = drawOffsetX;
	}

	public float getDrawOffsetY() {
		return drawOffsetY;
	}

	public void setDrawOffsetY(float drawOffsetY) {
		this.drawOffsetY = drawOffsetY;
	}
	
	public void setIndexArea(int begin,int end){
		this.img.setIndexArea(begin, end);
	}
	public void setCurrentIndex(int i){
		this.img.setCurrent(i);
	}
	
    @Override
    public void act(float delta) {
        super.act(delta);
        this.img.update((int)(delta*1000));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (img == null || !isVisible()) {
            return;
        }

        /*
         * 先把 batch 原本的 color 保存起来, 因为 batch 是从外部传进来的, 最好不要改变它原本的状态,
         * 但在这里需要重新设置 batch 的 color, 所以先保存起来, 等当前方法执行完时再将 batch 原本的 color 设置回去。
         */
        Color tempBatchColor = batch.getColor();

        /*
         * 实际上演员并没有单独的 alpha 属性, alpha 包含在颜色(color)属性中, rgba color 中的 a 表示 alpha;
         * 演员有 alpha 值, 而父节点(舞台/演员组)中也有 alpha 值(parentAlpha)。 由于最终在演员节点中才真正把纹理
         * 绘制在屏幕上, 才是真正绘制的地方, 而父节点一般用于组织演员, 不会直接绘制任何纹理, 透明度 alpha 值只有在绘制
         * 时才能体现出来, 所以父节点无法体现自己的 alpha 值, 因此父节点会将自己的 alpha 值(就是draw方法中的参数 parentAlpha)
         * 传递给它自己的所有子节点，即最终直接绘制纹理的演员, 让演员结合自身的 alpha 值在绘制时综合体现。
         */

        // 获取演员的 color 属性
        Color color = getColor();

        /*
         * 处理 color/alpha 属性, 即将演员的 rgba color 设置到纹理画布 batch。
         * 其中的 alpha 需要结合演员和父节点的 alpha, 即演员的 alpha 与父节点的 alpha 相乘,
         * 例如父节点的 alpha 为 0.5, 演员的 alpha 为 0.5, 那么最终的显示效果就是 0.5 * 0.5 = 0.25
         */
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        // 处理 位置, 缩放和旋转的支点, 尺寸, 缩放比, 旋转角度
        this.img.render((SpriteBatch)batch, 
        		getX()+drawOffsetX, getY()+drawOffsetY
        		);
        /*
        batch.draw(
                region, 
                getX(), getY(), 
                getOriginX(), getOriginY(), 
                getWidth(), getHeight(), 
                getScaleX(), getScaleY(), 
                getRotation()
        );
*/
        // 将 batch 原本的 color 设置回去
        batch.setColor(tempBatchColor);
    }
	public void restart() {
		img.restart();
		
	}
	public int getCurrentIndex() {
		return img.getCurrentIndex();
	}
}