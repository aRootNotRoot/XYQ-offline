package xyq.game.stage;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 自定义演员
 */
public class MyActor extends Actor {

    private TextureRegion region;

    public MyActor(TextureRegion region) {
        super();
        this.region = region;
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }
    public MyActor(FileHandle file){
    	super();
        this.region = new TextureRegion(new Texture(file));
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }
    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (region == null || !isVisible()) {
            return;
        }
        batch.draw(
                region, 
                getX(), getY(), 
                getOriginX(), getOriginY(), 
                getWidth(), getHeight(), 
                getScaleX(), getScaleY(), 
                getRotation()
        );
    }
}
