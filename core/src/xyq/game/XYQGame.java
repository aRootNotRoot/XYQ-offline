package xyq.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import xyq.game.rules.mapconfig.MapConfigManager;
import xyq.game.stage.LoginStage;
import xyq.game.stage.SenceStage;
import xyq.system.AISystem;
import xyq.system.BussinessSystem;
import xyq.system.CommandSystem;
import xyq.system.ItemDB;
import xyq.system.LogicSystem;
import xyq.system.MapManager;
import xyq.system.ShapeManager;
import xyq.system.SoundManager;
import xyq.system.SummonDB;
import xyq.system.TimeSystem;
import xyq.system.XYQDataBase;
import xyq.system.assets.ResSystem;


public class XYQGame extends ApplicationAdapter {
	
	public final static int V_WIDTH=1280;
	public final static int V_HEIGHT=720;
	public final static boolean maskOpcaity=false;
	public final static float PPM=20;
	public boolean showLightShadow=false;
	public boolean is_Debug=false;
	
	public SpriteBatch gameBatch;
	public OrthographicCamera camera;
	/**was ui 界面资源管理器*/
	public ResSystem rs;
	/**声音等其他资源管理器*/
	public AssetManager am;
	/**数据库管理器*/
	public XYQDataBase db;
	/**命令管理器*/
	public CommandSystem cs;
	/**逻辑管理器*/
	public LogicSystem ls;
	/**声音管理器*/
	public SoundManager sm;
	/**地图管理器*/
	public MapManager maps;
	/**地图附加配置管理器*/
	public MapConfigManager mapConfigs;
	/**时间任务管理器*/
	public TimeSystem ts;
	/**物品工厂，用于创建物品信息的*/
	public ItemDB itemDB;
	/**召唤兽工厂，用于处理召唤兽基础信息的*/
	public SummonDB summonDB;
	/**外形管理器，用于储存外形数据*/
	public ShapeManager shapeManager;
	/**AI管理器*/
	public AISystem ai;
	/**商业经济管理器*/
	public BussinessSystem bs;
	/**游戏内置调试器*/
	//public InGameDebuger IGD;
	
	
	long starTime;
	long endTime;
	long bootTime;

	boolean isLogging=true;
	boolean loaded=false;
	public LoginStage loginStage;
	/**游戏舞台*/
	public SenceStage senceStage;
	/**战斗舞台*/
	//Stage battleStage;
	//战斗舞台移到了游戏舞台里面

float i=1;
	@Override
	public void create () {
		starTime=System.currentTimeMillis();
		Gdx.app.log("[ XYQ ]","[系统] -> 当前资源路径为："+Gdx.files.internal("*/").file().getAbsolutePath());
		gameBatch = new SpriteBatch();
		camera = new OrthographicCamera(V_WIDTH, V_HEIGHT);
		camera.position.set(0, V_WIDTH / 2f, 0);
		camera.update();
		
		rs=new ResSystem();
		am = new AssetManager();
		db=new XYQDataBase(this);
		cs=new CommandSystem(this);
		shapeManager=new ShapeManager(this);
		sm=new SoundManager(this);
		ts=new TimeSystem();
		ai=new AISystem(this);
		bs=new BussinessSystem(this);
		
		//IGD=new InGameDebuger(this);
		//IGD.setVisible(false);
		
		loginStage = new LoginStage(this, new StretchViewport(V_WIDTH, V_HEIGHT));
		Gdx.input.setInputProcessor(loginStage);
		//logdone();
        
		endTime=System.currentTimeMillis();
		bootTime=endTime-starTime;
		Gdx.app.log("[ XYQ ]","[启动] -> 加载耗时:"+bootTime+"ms");
		
		
	}
	public void logdone(String id){
		if(id==null)
			id="100001";
		else if(id.equals(""))
			id="100001";
		isLogging=false;
		ls=new LogicSystem(this,id);
		maps=new MapManager(this);
		mapConfigs=new MapConfigManager(this);
		itemDB=new ItemDB(this);
		summonDB=new SummonDB(this);
		senceStage = new SenceStage(this,new StretchViewport(V_WIDTH, V_HEIGHT));
        maps.map().loadBGM();
        Gdx.input.setInputProcessor(senceStage);
       
        //int[] colorations = new int[]{4,2,2,0};
		//PPCData pp = new PPCData(rs.reader.readByName("shape.wd1", "人物换色6"),colorations);
		//PPCData pp=rs.getPPData("shape.wd1", "人物换色6",colorations);
        //Gdx.app.error("[ XYQ ]", " [PPData] length"+pp.getColorSchemes().length);
        ls.player.makeActor();
        loaded=true;
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update((int)(Gdx.graphics.getDeltaTime()*1000));
		gameBatch.begin();
       if(isLogging)
    	   loginStage.draw();
       else{
			senceStage.draw();
		}
		gameBatch.end();
		
		//rayHandler.setCombinedMatrix(camera);
	}
	public void update(int delta){
		checkInput();
		am.update();
		sm.update();
		float dtTime=Gdx.graphics.getDeltaTime();

		ts.add((long)(dtTime*1000));
		
		
		if(isLogging){
			loginStage.act(dtTime);
		}else{
			ls.update(dtTime);
			maps.update(dtTime);
			// 更新演员逻辑
			senceStage.act(dtTime);
			
		}
		
	}
	void checkInput(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.F9)){
			
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			
		}
	}
	

	@Override
	public void dispose () {
		gameBatch.dispose();
		am.dispose();
		if(isLogging){
			loginStage.dispose();
		}else{
			loginStage.dispose();
			senceStage.dispose();
		}
		db.closeConnect();
	}
}
