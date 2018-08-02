package xyq.system.assets;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import xyq.game.stage.MyActor;
import xyq.system.utils.RandomUT;

public class ResSystem {
	//保存UI里可能用到的字符，英文数字不算
	/*
	public final String UI_CHARS=
			"梦幻西游按钮未知位置长安城建邺业傲来北俱芦洲东海湾江南野外大雁塔长寿村大唐境国升杂货回春夏秋冬"
			+ "女儿西凉宝象朱紫方寸化生花果山庄观王魔地钓鱼女娲阴曹地府"
			+ "一二三四五六七八九十零层暗雷明中小"
			+"调试开关退出游戏项目信息称谓升级技能烹饪炼药摆摊更改属性现金师傅徒弟辅助剧情收取放鱼鹰叉网渔香油"
			+"无门派大唐官府华寺村龙宫天盘丝岭狮驼魔王寨"
			+"的一是在不了有和人这中大为上个国我以要他时来用们生到作地于出就分对成会可"
			;
*/
	public final String UI_CHARS=Gdx.files.internal("./assets/char.txt").readString("utf-8")
			+"邺栈衙麒麟驯狐狸蝴蝶托坨蛟蛤蟆丞妖犀蝙蝠蜘蛛骷髅凰券！，。、？【】；‘\"“《》￥·∞"
			;
	/**WDF 资源读取器，根据path直接读取was*/
	public WdfAssetsReader reader;
	/**缓存读取的帧数据，随着运行，内存消耗增加*/
	private Map<String, Map<String, FrameDatas>> frames = null;
	
	/**长通用按钮的按钮样式*/
	ButtonStyle longCommonButtonStyle;
	TextButtonStyle longCommonTextButtonStyle;
	/**通用按钮的按钮样式*/
	ButtonStyle commonButtonStyle;
	TextButtonStyle commonTextButtonStyle;
	/**短通用按钮的按钮样式*/
	ButtonStyle shortCommonButtonStyle;
	TextButtonStyle shortCommonTextButtonStyle;
	/**通用关闭按钮的按钮样式*/
	ButtonStyle clzCommonButtonStyle;
	/**通用蓝色关闭按钮的按钮样式*/
	ButtonStyle clzBlueCommonButtonStyle;
	
	TextFieldStyle whiteTextFieldStyle;
	TextFieldStyle blackTextFieldStyle;
	
	private BitmapFont xyqUIFont;
    private FreeTypeFontGenerator generatorFTF;
    private FreeTypeFontParameter parameterFTF; 
    
    
    Texture whiteLineBlockTexture;
    Texture redLineBlockTexture;
    Texture greenLineBlockTexture;
    Texture blueLineBlockTexture;
    
    Texture systemMessageTexture;
    
    public Texture tu_L;
    public Texture tu_R;
    public Texture tu_U;
    public Texture tu_D;
    public Texture tu_MD;
	
    public Texture tu_LU;
    public Texture tu_LD;
    public Texture tu_RU;
    public Texture tu_RD;
    
    public Texture chat_LU;
    public Texture chat_LD;
    public Texture chat_RU;
    public Texture chat_RD;
    public Texture chat_MD;
    
	public ResSystem(){
		reader= new WdfAssetsReader();
		reader.init("resWDF\\");
		frames=new HashMap<String, Map<String, FrameDatas>> ();
		////////////////////////
		generatorFTF = new FreeTypeFontGenerator(
	                Gdx.files.internal("assets/font.TTF"));
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 16;
		parameter.characters=FreeTypeFontGenerator.DEFAULT_CHARS+ UI_CHARS;
	        
		xyqUIFont=generatorFTF.generateFont(parameter);
		loadInfoPanelTexture();
		
	}
	public ResSystem(String resPath){
		reader= new WdfAssetsReader();
		reader.init(resPath);
		frames=new HashMap<String, Map<String, FrameDatas>> ();
		////////////////////////
		generatorFTF = new FreeTypeFontGenerator(Gdx.files.internal("assets/font.TTF"));
		parameterFTF = new FreeTypeFontParameter();
		parameterFTF.size = 16;
		parameterFTF.characters=FreeTypeFontGenerator.DEFAULT_CHARS+ "梦幻西游";
		
		xyqUIFont=generatorFTF.generateFont(parameterFTF);
	}
	public PPCData getPPData(String pack,String name,int[] colorations){
		return  new PPCData(reader.readByName(pack, name),colorations);
	}
	
	/**
	 * 获取一个新的血条ui图像
	 * */
	public Image makeNewBarImage(float percent,int posX,int posY,String pack,String was){
		Texture texture = getFrames(pack, was).getFrame(0, 0).image;
		Image img;
        // 创建 Image
		img=null;
		int size=0;
		size=(int)((float)texture.getWidth()*percent);
        img = new Image(new TextureRegion(texture,size,8));
     // 设置 image 的相关属性
        img.setPosition(posX, posY);
        //if(image.clipBegin( image.getX(),  image.getY(), 25, 8))
        	//image.clipEnd();
        // 添加 image 到舞台
        return img;
	}
	/**
	 * 返回界面用的字体数据，不适用于大段文件显示，比如武器介绍，道具介绍
	 * */
	public BitmapFont getUIFont(){
		return xyqUIFont;
	}
	/**
	 * 返回数据文本的字体数据，不适用于UI按钮啥的显示，适合武器介绍，道具介绍
	 * */
	public BitmapFont getFont(String chars){
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 16;
		parameter.characters=FreeTypeFontGenerator.DEFAULT_CHARS+ chars;
		
		return generatorFTF.generateFont(parameter);
	}
	public BitmapFont getFont(String chars,int fontSize){
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = fontSize;
		parameter.characters=FreeTypeFontGenerator.DEFAULT_CHARS+ chars;
		
		return generatorFTF.generateFont(parameter);
	}
	public String getString(String pack,String was){
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载String : "+was+"@"+pack);
		String string=null;
		try {
			string=new String(reader.readByName(pack,was), "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(string==null)
			Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载String失败 : "+was+"@"+pack);
		return string;
	}
	
	public String getChatString(String pack,String was){
		String chatStr=getString(pack,was);
		String[] chats=chatStr.split("P N");
		int randomChat=RandomUT.getRandom(0, chats.length-1);
		while("".equals(chats[randomChat]))//保证不让其选到空的话
			randomChat=RandomUT.getRandom(0, chats.length-1);
		return chats[randomChat];
	}
	public String getChatString_noRowChange(String pack,String was){
		String chatStr=getString(pack,was);
		String[] chats=chatStr.split("P N");
		int randomChat=RandomUT.getRandom(0, chats.length-1);
		while("".equals(chats[randomChat]))//保证不让其选到空的话
			randomChat=RandomUT.getRandom(0, chats.length-1);
		return chats[randomChat].replaceAll("\r|\n", "");
	}
	public FrameDatas getFrames(String pack,String was){
		Map<String, FrameDatas> map=frames.get(pack);
		if(map==null){
			frames.put(pack, new HashMap<String, FrameDatas>());
			map=frames.get(pack);
		}
		FrameDatas temp=map.get(was);
		if(temp==null){
			Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载FrameDatas : "+was+"@"+pack);
			WasData data=new WasData(reader.readByName(pack,was));
			Frame[][] fr=new Frame[data.direct][data.frame];
			for(int i=0;i<data.direct;i++){
				fr[i]=Frame.createFrame(data, i);
			}
			temp=new FrameDatas(fr);
			map.put(was, temp);
		}
		return temp;
	}
	public FrameDatas getFrames(String pack,String was,PPCData pp){
		Map<String, FrameDatas> map=frames.get(pack);
		if(map==null){
			frames.put(pack, new HashMap<String, FrameDatas>());
			map=frames.get(pack);
		}
		FrameDatas temp=null;
		if(pp!=null)
			temp=map.get(was+pp.toString());//这么做是为了保证可以有同名但是不同一换色的数据存在。比如同时存在，变异海毛虫和普通海毛虫
		else
			temp=map.get(was);
		if(temp==null){
			Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载FrameDatas : "+was+"@"+pack);
			WasData data=new WasData(reader.readByName(pack,was));
			if(pp!=null)
				data.coloration(pp);
			Frame[][] fr=new Frame[data.direct][data.frame];
			for(int i=0;i<data.direct;i++){
				fr[i]=Frame.createFrame(data, i);
			}
			temp=new FrameDatas(fr);
			if(pp!=null)
				map.put(was+pp.toString(), temp);//这么做是为了保证可以有同名但是不同一换色的数据存在。比如同时存在，变异海毛虫和普通海毛虫
			else
				map.put(was, temp);
		}
		return temp;
	}
	public ButtonStyle getButtonStyle(String pack,String was,boolean hasDisabled){
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载 按钮样式 : "+was+"@"+pack);
		ButtonStyle style = new ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
		style.up = new TextureRegionDrawable(new TextureRegion(getFrames(pack,was).getFrame(0, 0).image));
		style.down = new TextureRegionDrawable(new TextureRegion(getFrames(pack,was).getFrame(0, 1).image));
		style.over = new TextureRegionDrawable(new TextureRegion(getFrames(pack,was).getFrame(0, 2).image));
		if(hasDisabled)
			style.disabled = new TextureRegionDrawable(new TextureRegion(getFrames(pack,was).getFrame(0, 3).image));
        return style;
	}
	public TextButtonStyle getTextButtonStyle(String pack,String was,boolean hasDisabled){
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载 按钮样式 : "+was+"@"+pack);
		TextButtonStyle style = new TextButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
		style.up = new TextureRegionDrawable(new TextureRegion(getFrames(pack,was).getFrame(0, 0).image));
		style.down = new TextureRegionDrawable(new TextureRegion(getFrames(pack,was).getFrame(0, 1).image));
		style.over = new TextureRegionDrawable(new TextureRegion(getFrames(pack,was).getFrame(0, 2).image));
		if(hasDisabled)
			style.disabled = new TextureRegionDrawable(new TextureRegion(getFrames(pack,was).getFrame(0, 3).image));
		
		style.font = getUIFont();
		
		return style;
	}
	public ButtonStyle getLongCommonButtonStyle(){
		if(longCommonButtonStyle!=null)
			return longCommonButtonStyle;
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载 通用长按钮样式 : 按钮\\通用加长按钮@wzife.wdf");
		longCommonButtonStyle = new ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
		longCommonButtonStyle.up = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用加长按钮").getFrame(0, 0).image));
		longCommonButtonStyle.down = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用加长按钮").getFrame(0, 1).image));
		longCommonButtonStyle.over = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用加长按钮").getFrame(0, 2).image));
		longCommonButtonStyle.disabled = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用加长按钮").getFrame(0, 3).image));
        return longCommonButtonStyle;
	}
	public TextButtonStyle getLongCommonTextButtonStyle(){
		if(longCommonTextButtonStyle!=null)
			return longCommonTextButtonStyle;
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载 通用带文本长按钮样式");
		longCommonTextButtonStyle = new TextButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
		longCommonTextButtonStyle.up = getLongCommonButtonStyle().up;
		longCommonTextButtonStyle.down = getLongCommonButtonStyle().down;
		longCommonTextButtonStyle.over =  getLongCommonButtonStyle().over;
		longCommonTextButtonStyle.disabled =  getLongCommonButtonStyle().disabled;

		longCommonTextButtonStyle.pressedOffsetX = 1;
		longCommonTextButtonStyle.pressedOffsetY = -1;
		longCommonTextButtonStyle.font = getUIFont();
		
        return longCommonTextButtonStyle;
	}
	public ButtonStyle getCommonButtonStyle(){
		if(commonButtonStyle!=null)
			return commonButtonStyle;
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载 通用按钮样式 : 按钮\\通用@wzife.wdf");
		commonButtonStyle = new ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
		commonButtonStyle.up = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用").getFrame(0, 0).image));
		commonButtonStyle.down = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用").getFrame(0, 1).image));
		commonButtonStyle.over = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用").getFrame(0, 2).image));
		commonButtonStyle.disabled = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用").getFrame(0, 3).image));
        return commonButtonStyle;
	}
	public TextButtonStyle getCommonTextButtonStyle(){
		if(commonTextButtonStyle!=null)
			return commonTextButtonStyle;
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载 通用按钮样式");
		commonTextButtonStyle = new TextButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的                                              
		commonTextButtonStyle.up = getCommonButtonStyle().up;               
		commonTextButtonStyle.down = getCommonButtonStyle().down;           
		commonTextButtonStyle.over = getCommonButtonStyle().over;          
		commonTextButtonStyle.disabled = getCommonButtonStyle().disabled;  

		commonTextButtonStyle.pressedOffsetX = 1;
		commonTextButtonStyle.pressedOffsetY = -1;
		commonTextButtonStyle.font = getUIFont();
		
        return commonTextButtonStyle;                                         
	}
	public ButtonStyle getShortCommonButtonStyle(){
		if(shortCommonButtonStyle!=null)
			return shortCommonButtonStyle;
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载 通用长按钮样式 : 按钮\\通用圆角小@wzife.wdf");
		shortCommonButtonStyle = new ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
		shortCommonButtonStyle.up = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用圆角小").getFrame(0, 0).image));
		shortCommonButtonStyle.down = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用圆角小").getFrame(0, 1).image));
		shortCommonButtonStyle.over = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用圆角小").getFrame(0, 2).image));
		shortCommonButtonStyle.disabled = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用圆角小").getFrame(0, 3).image));
        return shortCommonButtonStyle;
	}
	public TextButtonStyle getShortCommonTextButtonStyle(){
		if(shortCommonTextButtonStyle!=null)
			return shortCommonTextButtonStyle;
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载 通用带文本小按钮样式");
		shortCommonTextButtonStyle = new TextButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
		shortCommonTextButtonStyle.up = getShortCommonButtonStyle().up;            
		shortCommonTextButtonStyle.down = getShortCommonButtonStyle().down;        
		shortCommonTextButtonStyle.over = getShortCommonButtonStyle().over;        
		shortCommonTextButtonStyle.disabled = getShortCommonButtonStyle().disabled;
		
		shortCommonTextButtonStyle.pressedOffsetX = 1;
		shortCommonTextButtonStyle.pressedOffsetY = -1;
		shortCommonTextButtonStyle.font = getUIFont();
		
		
        return shortCommonTextButtonStyle;
	}
	public ButtonStyle getClzCommonButtonStyle(){
		if(clzCommonButtonStyle!=null)
			return clzCommonButtonStyle;
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载 通用长按钮样式 : 按钮\\通用关闭红@wzife.wdf");
		clzCommonButtonStyle = new ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
		clzCommonButtonStyle.up = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用关闭红").getFrame(0, 0).image));
		clzCommonButtonStyle.down = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用关闭红").getFrame(0, 1).image));
		clzCommonButtonStyle.over = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用关闭红").getFrame(0, 2).image));
		clzCommonButtonStyle.disabled = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\通用关闭红").getFrame(0, 3).image));
        return clzCommonButtonStyle;
	}
	public ButtonStyle getClzBlueCommonButtonStyle(){
		if(clzBlueCommonButtonStyle!=null)
			return clzBlueCommonButtonStyle;
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 加载 通用长按钮样式 : 按钮\\关闭蓝@wzife.wdf");
		clzBlueCommonButtonStyle = new ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
		clzBlueCommonButtonStyle.up = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\关闭蓝").getFrame(0, 0).image));
		clzBlueCommonButtonStyle.down = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\关闭蓝").getFrame(0, 1).image));
		clzBlueCommonButtonStyle.over = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\关闭蓝").getFrame(0, 2).image));
		clzBlueCommonButtonStyle.disabled = new TextureRegionDrawable(new TextureRegion(getFrames("wzife.wdf", "按钮\\关闭蓝").getFrame(0, 3).image));
        return clzBlueCommonButtonStyle;
	}
	public LabelStyle getLabelStyle(String text){
		// 要创建 Label 首先要创建一个 Label 的样式, 用于指明 Label 所使用的位图字体, 背景图片, 颜色等
        Label.LabelStyle style = new Label.LabelStyle();

        // 指定 Label 的背景, 可用纹理区域 textureRegion（在这里背景我就不再设置）
        // style.background = new TextureRegionDrawable(textureRegion);

        // 指定 Label 所使用的位图字体
        style.font = getFont(text);

        // 指定 Label 字体的 RGBA 颜色
        style.fontColor = new Color(1, 1, 1, 1);
        return style;
	}
	
	public LabelStyle getBlackLabelStyle(String text){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text);
        style.fontColor = new Color(0, 0, 0, 1);
        return style;
	}
	
	public LabelStyle getBlackLabelStyle(String text,int fontSize){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text,fontSize);
        style.fontColor = new Color(0, 0, 0, 1);
        return style;
	}
	
	public LabelStyle getBlueLabelStyle(String text){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text);
        style.fontColor = new Color(0.2f,0.74f,0.9f,1);
        return style;
	}
	public LabelStyle getBlueLabelStyle(String text,int fontSize){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text,fontSize);
        style.fontColor = new Color(0.2f,0.74f,0.9f,1);
        return style;
	}
	public LabelStyle getYellowLabelStyle(String text){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text);
        style.fontColor = new Color(1,1,0,1);
        return style;
	}
	public LabelStyle getYellowLabelStyle(String text,int fontSize){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text,fontSize);
        style.fontColor = new Color(1,1,0,1);
        return style;
	}
	public LabelStyle getGreenLabelStyle(String text){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text);
        style.fontColor = new Color(0,1,0,1);
        return style;
	}
	public LabelStyle getGreenLabelStyle(String text,int fontSize){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text,fontSize);
        style.fontColor = new Color(0,1,0,1);
        return style;
	}
	public LabelStyle getDarkBlueLabelStyle() {
		 Label.LabelStyle style = new Label.LabelStyle();
	     style.font = getFont(UI_CHARS);
	     style.fontColor = new Color(0.04f,0.64f,0.26f,1);
	     return style;
	}
	//0aa344
	public LabelStyle getDarkGreenLabelStyle(String text){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text);
        style.fontColor = new Color(0.04f,0.64f,0.26f,1);
        return style;
	}
	public LabelStyle getDarkGreenLabelStyle(String text,int fontSize){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text,fontSize);
        style.fontColor = new Color(0.04f,0.64f,0.26f,1);
        return style;
	}
	//177cb0
	public LabelStyle getDarkBlueLabelStyle(String text){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text);
        style.fontColor = new Color(0.09f,0.2f,0.79f,1);
        return style;
	}
	public LabelStyle getDarkBlueLabelStyle(String text,int fontSize){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text,fontSize);
        style.fontColor = new Color(0.09f,0.2f,0.79f,1);
        return style;
	}
	public LabelStyle getRedLabelStyle(String text){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text);
        style.fontColor = new Color(1,0,0,1);
        return style;
	}
	public LabelStyle getRedLabelStyle(String text,int fontSize){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text,fontSize);
        style.fontColor = new Color(1,0,0,1);
        return style;
	}
	//ca6924
	public LabelStyle getBrownLabelStyle(String text){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text);
        style.fontColor = new Color(0.79f,0.41f,0.14f,1);
        return style;
	}
	public LabelStyle getBrownLabelStyle(String text,int fontSize){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text,fontSize);
        style.fontColor = new Color(0.79f,0.41f,0.14f,1);
        return style;
	}
	//8d4bbb
	public LabelStyle getPurpleLabelStyle(String text){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text);
        style.fontColor = new Color(0.55f,0.29f,0.73f,1);
        return style;
	}
	public LabelStyle getPurpleLabelStyle(String text,int fontSize){
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont(text,fontSize);
        style.fontColor = new Color(0.55f,0.29f,0.73f,1);
        return style;
	}
	public LabelStyle getLabelStyle(String text,int fontSize){
		// 要创建 Label 首先要创建一个 Label 的样式, 用于指明 Label 所使用的位图字体, 背景图片, 颜色等
        Label.LabelStyle style = new Label.LabelStyle();

        // 指定 Label 的背景, 可用纹理区域 textureRegion（在这里背景我就不再设置）
        // style.background = new TextureRegionDrawable(textureRegion);

        // 指定 Label 所使用的位图字体
        style.font = getFont(text,fontSize);

        // 指定 Label 字体的 RGBA 颜色
        style.fontColor = new Color(1, 1, 1, 1);
        return style;
	}
	public LabelStyle getUILabelStyle(){
		// 要创建 Label 首先要创建一个 Label 的样式, 用于指明 Label 所使用的位图字体, 背景图片, 颜色等
        Label.LabelStyle style = new Label.LabelStyle();
        // 指定 Label 所使用的位图字体
        style.font = getUIFont();
        // 指定 Label 字体的 RGBA 颜色
        style.fontColor = new Color(1, 1, 1, 1);
        return style;
	}
	public LabelStyle getBlackUILabelStyle(){
		// 要创建 Label 首先要创建一个 Label 的样式, 用于指明 Label 所使用的位图字体, 背景图片, 颜色等
        Label.LabelStyle style = new Label.LabelStyle();
        // 指定 Label 所使用的位图字体
        style.font = getUIFont();
        // 指定 Label 字体的 RGBA 颜色
        style.fontColor = new Color(0, 0, 0, 1);
        return style;
	}
	public TextFieldStyle getWhiteTextFieldStyle(int height){
		if(whiteTextFieldStyle!=null)
			return whiteTextFieldStyle;
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 计算生成白色文本输入框样式");
		whiteTextFieldStyle = new TextFieldStyle();

        // 设置光标纹理区域
		whiteTextFieldStyle.cursor = new TextureRegionDrawable(new TextureRegion(createWhiteCursorTexture(height)));
        // 设置文本框显示文本的字体来源
		whiteTextFieldStyle.font = getUIFont();
        // 设置文本框字体颜色为白色
		whiteTextFieldStyle.fontColor = new Color(1, 1, 1, 1);
        return whiteTextFieldStyle;
	}
	public TextFieldStyle getBlackTextFieldStyle(int height){
		if(blackTextFieldStyle!=null)
			return blackTextFieldStyle;
		Gdx.app.log("[ XYQ ]", "[ ResSystem ] -> 计算生成黑色文本输入框样式");
		blackTextFieldStyle = new TextFieldStyle();

        // 设置光标纹理区域
		blackTextFieldStyle.cursor = new TextureRegionDrawable(new TextureRegion(createBlackCursorTexture(height)));
        // 设置文本框显示文本的字体来源
		blackTextFieldStyle.font = getUIFont();
        // 设置文本框字体颜色为白色
		blackTextFieldStyle.fontColor = new Color(0, 0, 0, 1);
        return blackTextFieldStyle;
	}
	private Texture createWhiteCursorTexture(int height) {
		if(height<=4)
			height=5;
        Pixmap pixmap = new Pixmap(1, height - 4, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
	private Texture createBlackCursorTexture(int height) {
		if(height<=4)
			height=5;
        Pixmap pixmap = new Pixmap(1, height - 4, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
	public Texture getWhiteLineBlockTexture() {
		if(whiteLineBlockTexture!=null)
			return whiteLineBlockTexture;
        Pixmap pixmap = new Pixmap(20,20, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.drawRectangle(0, 0, 20, 20);
        whiteLineBlockTexture= new Texture(pixmap);
        pixmap.dispose();
        return whiteLineBlockTexture;
    }
	public Texture getRedLineBlockTexture() {
		if(redLineBlockTexture!=null)
			return redLineBlockTexture;
        Pixmap pixmap = new Pixmap(20,20, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1);
        pixmap.drawRectangle(0, 0, 20, 20);
        redLineBlockTexture= new Texture(pixmap);
        pixmap.dispose();
        return redLineBlockTexture;
    }
	public Texture getGreenLineBlockTexture() {
		if(greenLineBlockTexture!=null)
			return greenLineBlockTexture;
        Pixmap pixmap = new Pixmap(20,20, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 1, 0, 1);
        pixmap.drawRectangle(0, 0, 20, 20);
        greenLineBlockTexture= new Texture(pixmap);
        pixmap.dispose();
        return greenLineBlockTexture;
    }
	public Texture getBlueLineBlockTexture() {
		if(blueLineBlockTexture!=null)
			return blueLineBlockTexture;
        Pixmap pixmap = new Pixmap(20,20, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 1, 1);
        pixmap.drawRectangle(0, 0, 20, 20);
        blueLineBlockTexture= new Texture(pixmap);
        pixmap.dispose();
        return blueLineBlockTexture;
    }
	public void loadInfoPanelTexture(){
		if(tu_L==null||tu_R==null||tu_MD==null||tu_LU==null||tu_LD==null||tu_RU==null||tu_RD==null||tu_U==null||tu_D==null){
			 tu_L=new Texture(Gdx.files.internal("./assets/infobg/info_L.png"));
		     tu_R=new Texture(Gdx.files.internal("./assets/infobg/info_R.png"));
		     tu_U=new Texture(Gdx.files.internal("./assets/infobg/info_U.png"));
		     tu_D=new Texture(Gdx.files.internal("./assets/infobg/info_D.png"));
		     tu_MD=new Texture(Gdx.files.internal("./assets/infobg/info_MD.png"));

		     tu_LU=new Texture(Gdx.files.internal("./assets/infobg/info_LU.png"));
		     tu_LD=new Texture(Gdx.files.internal("./assets/infobg/info_LD.png"));
		     tu_RU=new Texture(Gdx.files.internal("./assets/infobg/info_RU.png"));
		     tu_RD=new Texture(Gdx.files.internal("./assets/infobg/info_RD.png"));
		}
		if(chat_LU==null||chat_LD==null||chat_MD==null||chat_RU==null||chat_RD==null){
			chat_LU=new Texture(Gdx.files.internal("./assets/infobg/chat_LU.png"));
			chat_LD=new Texture(Gdx.files.internal("./assets/infobg/chat_LD.png"));
			chat_MD=new Texture(Gdx.files.internal("./assets/infobg/chat_MD.png"));
			chat_RU=new Texture(Gdx.files.internal("./assets/infobg/chat_RU.png"));
			chat_RD=new Texture(Gdx.files.internal("./assets/infobg/chat_RD.png"));
		}
	}
	public TextureRegion getRect(float width, float height) {
		Pixmap pixmap=new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 0, 0, 1);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		return new TextureRegion(new Texture(pixmap));
	}
	public TextureRegion getRect(float width, float height,float colorR,float colorG,float colorB,float colorA) {
		Pixmap pixmap=new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
		pixmap.setColor(colorR, colorG, colorB, colorA);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		return new TextureRegion(new Texture(pixmap));
	}
	public MyActor getDebugAreaActor(int width,int height,int posX,int posY){
		MyActor debugPanel=new MyActor(getRect(width+2,height+2));
		debugPanel.setPosition(posX-1, posY-1);
		return debugPanel;
	}
	/**输出wdf所有的名字索引*/
	public void debugAllWdfWasIndex(String wdf) {
		System.out.println("==============开始Debug==========="); 
		System.out.println("输出所有资源索引->"); 
		for (Map.Entry<String, String> entry : reader.files_ini_index.get(wdf).entrySet()) { 
			  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
		}
		
	}
	
}
