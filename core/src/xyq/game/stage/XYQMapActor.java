package xyq.game.stage;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import box2dLight.RayHandler;
import xyq.game.XYQGame;
import xyq.game.data.NPC;
import xyq.system.ai.AstarPathFinder;
import xyq.system.ai.MapGraph;
import xyq.system.ai.MapGraphPath;
import xyq.system.ai.MapNode;
import xyq.system.map.MapData;
import xyq.system.map.MaskUnit;
import xyq.system.utils.RandomUT;

/**
 * 自定义地图演员
 */
public class XYQMapActor extends Group {
	final int mapMoveStep=1;
	final int mapMoveBigStep=2;
	
	public int mapID;
	public XYQGame game;
	
	public MapData data;
	
	MaskUnit tempmu;
	public String mapName="未知位置";
	public String mapBGM="";
	
	/**小地图储存位置，可能没有小地图*/
	public String smallMapPack="";
	public String smallMapWas="";
	
	int tempx;
	int tempy;
	
	ArrayList<NPC> NPCList;
	//NPC aNpc;
	
	/**地图数据：1-阻挡 0-通行 2-传送 [Y坐标][X坐标]*/
	short[][] mapData;
	public int maxLocX;
	public int maxLocY;

	
	
	public boolean justClickARole;
	boolean showMask=true;
	////////////////////////////////////
	//AI
	MapGraph mapGraph;
	AstarPathFinder pathFinder;
	/////////////////////////////////////
	//动态光影
	////////////////////////////////////
	/**光影存在的世界*/
	World world;
	/**光影的射线处理器*/
	RayHandler rayHandler;
	/**光影s*/
	ArrayList<GameLight> lights;
    public XYQMapActor(final XYQGame game,int id) {
        super();
        try {
			data=new MapData("./scene/"+id+".map");
			//loadAllMaskBigShadow();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.mapID=id;
		this.game=game;
		this.maxLocX=data.getWidth()/20;
		this.maxLocY=data.getHeight()/20;
		
		NPCList=new ArrayList<NPC>();
		
		loadMapInfo();
		loadMapANI("./scene/"+id+".mapani");
		
		mapData=new short[maxLocX][maxLocY];
		for(int x=0;x<maxLocX;x++){
			for(int y=0;y<maxLocY;y++){
				mapData[x][y]=(short)data.cellData[x][y];
			}
		}
		
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]", "[地图] - >加载地图："+"./scene/"+id+".map");
			Gdx.app.log("[ XYQ ]", "[地图] - >地图大小："+data.getWidth()+"*"+data.getHeight());
			Gdx.app.log("[ XYQ ]", "[地图] - >地图分块数："+data.getHorSegmentCount()+"*"+data.getVerSegmentCount());
			Gdx.app.log("[ XYQ ]", "[地图] - >地图遮罩图层数："+data.getMaskCount());
			Gdx.app.log("[ XYQ ]", "[地图] - >地图逻辑坐标数："+maxLocX+"*"+maxLocY);
			
		}
		setPosition(0, 0);
        setSize(data.getWidth(), data.getHeight());
        addListener(new InputListener(){
        	@Override
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		if(button==0){
	        		if(!justClickARole){
	        			int[] logicPos=getClickInMapLogicPos(x,y);
	        			if(game.is_Debug){
	            			Gdx.app.log("[ XYQ ]", "[地图Debug] - >点击了地图位置："+x+","+y);
	            			Gdx.app.log("[ XYQ ]", "[地图Debug] - >点击了地图逻辑坐标位置："+logicPos[0]+","+logicPos[1]);
	            			//int[] xy=getXYByLookPos((int)x,(int)y);
	            			//autoMoveToPosition((int)xy[0],(int)xy[1]);
	            			//testMoveToAction(x,y);
	            		}
	        			game.cs. Player_MoveTo(game.ls.player,x,y,logicPos[0],logicPos[1]);
	        			//game.ls. npcMoveTo(aNpc,logicPos[0],logicPos[1]);
	        		}else{
	        			justClickARole=false;
	        		}
	        		return false;
        		}
        		return false;
        	}
        });
        
        game.mapConfigs.loadConfig(this.mapID, this);
        
        
        /*
        testActor = new WasActor(game.rs,"shape.wdf", "BB\\超级泡泡\\待战");
        testActor.setColor(0.9f,0.9f,0.9f,1);
        testActor.setPosition(0, 0);
        addActor(testActor);
        */
        /*
        aNpc=new NPC(game, "shape.wdf", "考官");
        aNpc.setNPCName("钓鱼NPC");
        aNpc.setNPCTitle("钓鱼活动使者");
        aNpc.setNPCPosition(25, 126);
        aNpc.addThisNPC(this);
        */
        
        mapGraph=new MapGraph(this);
        pathFinder=new AstarPathFinder(mapGraph);
        
        world=new World(new Vector2(0, 0), false);
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.3f);
        
        //pointLight=new PointLight(rayHandler, 50,Color.GRAY,10f/XYQGame.PPM,0,0);
        //pointLight.setSoft(true);
        //pointLight.setSoftnessLength(100);
        
        //light=new GameLight(rayHandler, 10f, 1076, 2066);
       // light.setBlink(true);
        //light.setColor(Color.GOLD);
        
        lights=game.db.loadMapALLLight(mapID,rayHandler);
        
    }
    /**使用GDX启发式路线搜索，根据起点逻辑坐标和目标逻辑坐标，查找到路线*/
    public MapGraphPath pathFound(int logicStartX,int logicStartY,int logicEndX,int logicEndY){
    	MapGraphPath foundPath=new MapGraphPath();
    	pathFinder.pathFind(getMapNode(logicStartX,logicStartY), getMapNode(logicEndX,logicEndY), foundPath);
    	return foundPath;
    }
    public MapNode getMapNode(int logicX,int logicY){
    	return mapGraph.getNode(logicX, logicY);
    }
    public void addNPC(NPC npc){
    	if(NPCList!=null){
    		this.NPCList.add(npc);
    	}
    }
    public NPC getNPC(String name){
    	NPC temp=null;
		if(NPCList!=null){
			for(int i=0,size=NPCList.size();i<size;i++){
				temp=NPCList.get(i);
				if(temp.name().equals(name)&&mapID==temp.getInMapID())
					return temp;
			}
		}
		return temp;
	}
    public void removeNPC(String name){
    	NPC temp=null;
		if(NPCList!=null){
			for(int i=0,size=NPCList.size();i<size;i++){
				temp=NPCList.get(i);
				if(temp.name().equals(name)){
					NPCList.remove(i);
					return;
				}
			}
		}
    }
    public ArrayList<NPC> NPCList(){
		return this.NPCList;
	}
    public void autoMoveToPosition(float x,float y) {
    	//float time=game.ls.getTwoMapPointDest(x,y,getX(),getY())/LogicSystem.plrMoveSpeed*2;
    	MoveToAction action = Actions.moveTo(x, y, 4.0F);
        clearActions();
        addAction(action);
    }
    public void autoMoveToPosition(float x,float y,float time) {
    	//float time=game.ls.getTwoMapPointDest(x,y,getX(),getY())/LogicSystem.plrMoveSpeed*2;
    	MoveToAction action = Actions.moveTo(x, y, time);
        clearActions();
        addAction(action);
    }
    /**获取点击所在地图的逻辑坐标位置*/
    public int[] getClickInMapLogicPos(float x,float y){
    	int[] pos=new int[2];
    	/*
    	if(game.is_Debug){
    		Gdx.app.log("[ XYQ ]", "[地图Debug] - >点击了地图演员位置："+x+","+y);
    		Gdx.app.log("[ XYQ ]", "[地图Debug] - >当前地图演员位置："+getX()+","+getY());
    	}
    	*/
    	pos[0]=(int)(x/20);
    	pos[1]=(int)(y/20);
    	return pos;
    }
    public int getDrawStartY(){
    	return (int) (getY()+data.getHeight()-240);
    }
    /**获取地图数据的某个逻辑坐标的值，是否是阻挡什么的
     * @return 地图数据：1-阻挡 0-通行 2-传送
     * */
    public short getMapData(int x,int y){
		if(x>this.maxLocX){
			if(game.is_Debug)
				Gdx.app.error("[ XYQ ]","[地图] -> getMapData传递的参数越界了，x超过了最大宽度，但是影响不大_(:3 」∠)_通常是随机查找坐标造成的");
			return 1;
		}
		if(y>this.maxLocY){
			if(game.is_Debug)
				Gdx.app.error("[ XYQ ]","[地图] -> getMapData传递的参数越界了，y超过了最大高度，但是影响不大_(:3 」∠)_通常是随机查找坐标造成的");
			return 1;
		}
		if(x<0){
			if(game.is_Debug)
				Gdx.app.error("[ XYQ ]","[地图] -> getMapData传递的参数越界了，x小于了0，但是影响不大_(:3 」∠)_通常是随机查找坐标造成的");
			return 1;
		}
		if(y<0){
			if(game.is_Debug)
				Gdx.app.error("[ XYQ ]","[地图] -> getMapData传递的参数越界了，y小于了0，但是影响不大_(:3 」∠)_通常是随机查找坐标造成的");
			return 1;
		}

		if(x<1||y<1||x==this.maxLocX||y==this.maxLocY)
			return 1;
		return mapData[x][maxLocY-1-y];
	}
    /**获取当前地图的材质高度*/
	public int getMapHeight(){
		return this.data.getHeight();
	}
	/**获取当前地图的材质宽度*/
	public int getMapWidth(){
		return this.data.getWidth();
	}
	/**
	 * 获取一个地图内的可到达的点的坐标
	 * */
	public int[] getRandomXY(){
		int[] pos=new int[2];
		int randomX=RandomUT.getRandom(1, maxLocX-1);
		int randomY=RandomUT.getRandom(1, maxLocY-1);
		int limitControl=500;//尝试50次后取消
		int z=0;
		while(getMapData(randomX,randomY)!=0&&z<limitControl){
			z++;
			randomX=RandomUT.getRandom(1, maxLocX-1);
			randomY=RandomUT.getRandom(1, maxLocY-1);
		}
		if(z>=limitControl){
			Gdx.app.error("[ XYQ ]", "[ MAP ] -> 获取随机可到达坐标失败，超过了尝试次数");
		}
		
		pos[0]=randomX;pos[1]=randomY;
		return pos;
	}
	public int[] getRandomXY(int logicX, int logicY, int area) {
		if(game.is_Debug)
			Gdx.app.log("[ XYQ ]", "[ MAP ] -> 尝试获取范围"+area+"内随机可到达坐标，基准["+logicX+","+logicY+"]");
		int[] pos=new int[2];
		int i=0;
		int randomX=logicX;
		int randomY=logicY;
		
		int limitControl=500;//尝试50次后取消
		int z=0;
		i=RandomUT.getRandom(0, 1);
		if(i==1){//正向查找
			int plus=RandomUT.getRandom(0, area);
			randomX+=plus;
		}else{
			int plus=RandomUT.getRandom(0, area);
			randomX-=plus;
		}
		i=RandomUT.getRandom(0, 1);
		if(i==1){//正向查找
			int plus=RandomUT.getRandom(0, area);
			randomY+=plus;
		}else{
			int plus=RandomUT.getRandom(0, area);
			randomY-=plus;
		}
		while(getMapData(randomX,randomY)!=0&&z<limitControl){
			z++;
			i=RandomUT.getRandom(0, 1);
			if(i==1){//正向查找
				int plus=RandomUT.getRandom(0, area);
				randomX+=plus;
			}else{
				int plus=RandomUT.getRandom(0, area);
				randomX-=plus;
			}
			i=RandomUT.getRandom(0, 1);
			if(i==1){//正向查找
				int plus=RandomUT.getRandom(0, area);
				randomY+=plus;
			}else{
				int plus=RandomUT.getRandom(0, area);
				randomY-=plus;
			}
		}
		if(z>=limitControl)
			Gdx.app.error("[ XYQ ]", "[ MAP ] -> 获取某范围的随机可到达坐标失败，超过了尝试次数");
		pos[0]=randomX;pos[1]=randomY;
		return pos;
	}
	boolean isInArea(int me,int him,int area){
		area=Math.abs(area);
		if(me>=him)
			return me-him<=area;
		else
			return me-him>=-area;
	}
	/**
	 * 获取一个地图内的随机可到达的点的模糊坐标（比如抓鬼的坐标）
	 * @param area 模糊范围
	 * @return int[4]，分别为模糊x坐标，模糊y坐标，精确x坐标，精确y坐标
	 * */
	public int[] getRandomUnClearXY(int area){
		int[] pos=new int[4];
		int randomX=RandomUT.getRandom(1, maxLocX-1);
		int randomY=RandomUT.getRandom(1, maxLocY-1);
		int offsetX=RandomUT.getRandom(-area, area);
		int offsetY=RandomUT.getRandom(-area, area);
		while(getMapData(randomX,randomY)!=0){
			randomX=RandomUT.getRandom(1, maxLocX-1);
			randomY=RandomUT.getRandom(1, maxLocY-1);
		}
		pos[0]=randomX;pos[1]=randomY;
		while((randomX+offsetX>=maxLocX||randomY+offsetY>=maxLocY)||getMapData(randomX+offsetX,randomY+offsetY)!=0){
			offsetX=RandomUT.getRandom(-area, area);
			offsetY=RandomUT.getRandom(-area, area);
		}
		pos[2]=randomX+offsetX;pos[3]=randomY+offsetY;
		return pos;
	}
	/**
	 * 获取一个地图内的可到达的点的模糊坐标（如果给定的xy坐标不可到达将随机再生成）
	 * @param x 基础X坐标（模糊）
	 * @param y 基础Y坐标（模糊）
	 * @param area 模糊范围
	 * @return int[4]，分别为模糊x坐标，模糊y坐标，精确x坐标，精确y坐标
	 * */
	public int[] getUnClearXY(int x,int y,int area){
		int[] pos=new int[4];
		int randomX=x;
		int randomY=y;
		int offsetX=RandomUT.getRandom(-area, area);
		int offsetY=RandomUT.getRandom(-area, area);
		while(getMapData(randomX,randomY)!=0){
			randomX=RandomUT.getRandom(1, maxLocX-1);
			randomY=RandomUT.getRandom(1, maxLocY-1);
		}
		pos[0]=randomX;pos[1]=randomY;
		while((randomX+offsetX>=maxLocX||randomY+offsetY>=maxLocY)||getMapData(randomX+offsetX,randomY+offsetY)!=0){
			offsetX=RandomUT.getRandom(-area, area);
			offsetY=RandomUT.getRandom(-area, area);
		}
		pos[2]=randomX+offsetX;pos[3]=randomY+offsetY;
		return pos;
	}
	/**根据目标地图内坐标点，得到地图应该移动位置点，把目标点放在屏幕中央*/
	public int[] getXYByLookPos(int x,int y){
		int[] pos=new int[2];
		pos[0]=640-x;
		pos[1]=360-y;
		if(pos[0]>0)
			pos[0]=0;
		if(pos[1]>0)
			pos[1]=0;
		if(pos[0]<-getWidth()+1280)
			pos[0]=(-(int)getWidth())+1280;
		if(pos[1]<-getHeight()+720)
			pos[1]=(-(int)getHeight())+720;
		return pos;
	}
	public void mapMoveUP(){
		if(getY()>-getHeight()+720)
			moveBy(0, -mapMoveStep);
	}
	public void mapMoveDOWN(){
		if(getY()<0)
			moveBy(0, mapMoveStep);
	}
	public void mapMoveLEFT(){
		if(getX()<0)
			moveBy(mapMoveStep, 0);
	}
	public void mapMoveRIGHT(){
		if(getX()>-getWidth()+1280)
			moveBy(-mapMoveStep, 0);
	}

	public void mapMoveUPBig(){
		if(getY()>-getHeight()+720)
			moveBy(0, -mapMoveBigStep);
	}
	public void mapMoveDOWNBig(){
		if(getY()<0)
			moveBy(0, mapMoveBigStep);
	}
	public void mapMoveLEFTBig(){
		if(getX()<0)
			moveBy(mapMoveBigStep, 0);
	}
	public void mapMoveRIGHTBig(){
		if(getX()>-getWidth()+1280)
			moveBy(-mapMoveBigStep, 0);
	}
    /**绘制整张地图*/
    public void drawWholeMap(Batch batch){
    	for(int i=0;i<data.getHorSegmentCount();i++)
    		for(int j=0;j<data.getVerSegmentCount();j++)
    			batch.draw(data.texture(i, j), getX()+i*320, getDrawStartY()+(-1)*j*240);
    }
    /**绘制地图遮罩*/
    public void drawMaskShadow(Batch batch){
    	if(!showMask)
    		return;
		int maskCount=data.getMaskCount();
		MaskUnit temp;
		for(int i=0;i<maskCount;i++){
			temp=data.getMaskUnit(i);
			if(isMaskInView(temp)){//TODO 需要优化，当角色的材质穿过，或者说角色在地图里，mask下面的时候，不应该绘制此mask
				batch.draw(temp.getTexture(),temp.getX()+getX(),getMaskY_LeftButtom(temp)+getY());
			}
				
		}
	}
    public boolean isMaskInView(MaskUnit mu){
		boolean in=false;
		if(XYQGame.maskOpcaity){
			if(getMaskY_LeftButtom(mu) +mu.getHeight()>-getY()&&getMaskY_LeftButtom(mu) <-getY()+720&&mu.getX()+mu.getWidth()>-getX()&&mu.getX()<-getX()+1280)
				in=true;
		}
		else{
			if(getMaskY_LeftButtom(mu) +mu.getHeight()>-getY()&&getMaskY_LeftButtom(mu) <-getY()+720&&mu.getX()+mu.getWidth()>-getX()&&mu.getX()<-getX()+1280)
				if(game.ls.player.actor.getY()-5>getMaskY_LeftButtom(mu))
					in=true;
		}
		return in;
	}
    float getMaskY_LeftButtom(MaskUnit mu){
    	return (float) (getHeight()-mu.getY()-mu.getHeight());
    }
    /**加载所有的遮罩单元*/
	boolean loadAllMaskBigShadow(){
		if(data.bigMapShadowTexture!=null)
			return true;
		MaskUnit maskUnit;
		Pixmap texture=new Pixmap(data.getWidth(),data.getHeight(),Format.RGBA8888);
		for(int i=0;i<data.getMaskCount();i++){
				maskUnit = data.ReadMask(i);
				Pixmap pixmap = maskUnit.getTexture().getTextureData().consumePixmap();
				texture.drawPixmap(pixmap, (int)maskUnit.getX(), (int)getMaskY_LeftButtom(maskUnit));
		}
		
		return true;
	}
	public void  loadMapInfo(){
		this.mapName=game.maps.getMapName(mapID);
		this.mapBGM=game.maps.getMapBGM(mapID);
		this.smallMapPack=game.maps.getMapSmallMapPack(mapID);
		this.smallMapWas=game.maps.getMapSmallMapWas(mapID);
	}
	/**加载地图附加配置文件*/
	public void loadMapANI(String name){
		 try {
			 	File file=new File(name);    
				if(!file.exists()){
					Gdx.app.log("[ XYQ ]", "[地图] -> 找不到当前地图的附加配置文件:"+name);
					file=null;
					return;
				}
            //解析文档  
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();  
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();  
            Document doc = builder.parse(name);  
              
             XPathFactory factory = XPathFactory.newInstance(); //创建 XPathFactory  
             XPath xpath = factory.newXPath();//用这个工厂创建 XPath 对象  
              
             NodeList nodes = (NodeList)xpath.evaluate("mapani/map", doc, XPathConstants.NODESET); 
             Node node = nodes.item(0);
             //this.mapName=getNodeAttrValue(node,"name");
             //this.mapBGM=getNodeAttrValue(node,"BGM");
             //Gdx.app.error("[ XYQ ]","[地图] -> "+mapBGM);
 			
             nodes = (NodeList)xpath.evaluate("mapani/smallmap", doc, XPathConstants.NODESET); 
             node = nodes.item(0);
             /*
             if(node==null){
            	 if(game.is_Debug)
            		 Gdx.app.log("[ XYQ ]", "[地图] -> 当前地图没有小地图数据:"+this.mapName);
             }
             else{
            	 this.smallMapPack=getNodeAttrValue(node,"pack");
            	 this.smallMapWas=getNodeAttrValue(node,"was");
             }
             */
             //读取地图装饰效果
             nodes = (NodeList)xpath.evaluate("mapani/ani", doc, XPathConstants.NODESET); 
             for (int i = 0; i < nodes.getLength(); i++) {  
                  node = nodes.item(i);
                  int XYBySceen=Integer.valueOf(getNodeAttrValue(node,"XYBySceen"));
                  if(XYBySceen==0){
                	  
                	  int x=Integer.valueOf(getNodeAttrValue(node,"x"));
                      int y=Integer.valueOf(getNodeAttrValue(node,"y"));
                      int randomx=Integer.valueOf(getNodeAttrValue(node,"randomXArea"));
                      int randomy=Integer.valueOf(getNodeAttrValue(node,"randomYArea"));
                      int randomDelayMin=Integer.valueOf(getNodeAttrValue(node,"randomDelayMin"));
                      int randomDelayMax=Integer.valueOf(getNodeAttrValue(node,"randomDelayMax"));
                      int autoHide=Integer.valueOf(getNodeAttrValue(node,"autoHide"));
                      String pack=getNodeAttrValue(node,"wasPack");
                      String wasname=getNodeAttrValue(node,"wasName");
                      int direct=Integer.valueOf(getNodeAttrValue(node,"direct"));
                      
	                  MapAniActor abctor = new MapAniActor(game.rs,pack, wasname);
	                  abctor.setAnimate(x, y, randomx, randomy, randomDelayMin, randomDelayMax, direct,autoHide);
	                  addActor(abctor);
                  }
             } 
             
             /*
             nodes = (NodeList)xpath.evaluate("mapani/npc", doc, XPathConstants.NODESET); 
             for (int i = 0; i < nodes.getLength(); i++) {  
                  node = nodes.item(i);
                  int XYBySceen=Integer.valueOf(getNodeAttrValue(node,"XYBySceen"));
                  if(XYBySceen==0){
                	  
                	  int x=Integer.valueOf(getNodeAttrValue(node,"x"));
                      int y=Integer.valueOf(getNodeAttrValue(node,"y"));
                      int randomx=Integer.valueOf(getNodeAttrValue(node,"randomXArea"));
                      int randomy=Integer.valueOf(getNodeAttrValue(node,"randomYArea"));
                      int randomDelayMin=Integer.valueOf(getNodeAttrValue(node,"randomDelayMin"));
                      int randomDelayMax=Integer.valueOf(getNodeAttrValue(node,"randomDelayMax"));
                      int autoHide=Integer.valueOf(getNodeAttrValue(node,"autoHide"));
                      String pack=getNodeAttrValue(node,"wasPack");
                      String wasname=getNodeAttrValue(node,"wasName");
                      int direct=Integer.valueOf(getNodeAttrValue(node,"direct"));
                      
	                  MapAniActor abctor = new MapAniActor(game.rs,pack, wasname);
	                  abctor.setAnimate(x, y, randomx, randomy, randomDelayMin, randomDelayMax, direct,autoHide);
	                  addActor(abctor);
                  }
             }  
              */
      } catch (Exception e) {  
         
          e.printStackTrace();  
      }  
	}
	public void loadBGM(){
		if(this.mapBGM!=null&&!this.mapBGM.equals(""))
			game.sm.switchBGM(mapBGM);
           // game.am.load(mapBGM, Music.class);
	}
	public void lookAtPlayer(){
		int[] xy=getXYByLookPos((int)game.ls.player.actor.getX(),(int)game.ls.player.actor.getY());
		setX((int)xy[0]);
		setY((int)xy[1]);
	}
	public void smallMapAutoXY(){
		if(getWidth()<1280)
			setX((int)((1280-getWidth())/2));
		if(getHeight()<720)
			setY((int)((720-getHeight())/2));
	}
	public String getNodeAttrValue(Node node,String attr){
        return node.getAttributes().getNamedItem(attr).getNodeValue();
	}

    /**切换是否显示地图遮罩*/
	public void toggleMask() {
		this.showMask=!this.showMask;
		
	}
    /**绘制阻挡方框*/
    public void drawBoxLine(Batch batch){
    	for(int as=0;as<maxLocX;as++)
	    	for(int bs=0;bs<maxLocY;bs++)
	    		if(getMapData(as,bs)==1)
	    		{
	    			batch.draw(game.rs.getRedLineBlockTexture()   , getX()+as*20, getY()+bs*20);
	    		}
	    		else if(getMapData(as,bs)==2)
	    		{
	    			batch.draw(game.rs.getGreenLineBlockTexture(), getX()+as*20, getY()+bs*20);
	    		}
	    		else if(getMapData(as,bs)==3)
	    		{
	    			batch.draw(game.rs.getBlueLineBlockTexture()  , getX()+as*20, getY()+bs*20);
	    		}
				else
				{
					
				}
    }
    
    @Override
    public void act(float delta) {
    	if(game.ls.ifm.SYSTEM_isBattling())
    		return;
        super.act(delta);
        //mapMoveUP();
        //mapMoveRIGHT();
        if(mapID==game.maps.currentMapID){
        	int len=NPCList.size();
        	for(int i=0;i<len;i++){
        		NPCList.get(i).update(delta);
        	}
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
    	/**绘制地图背景*/
    	drawWholeMap(batch);
    	
        if(game.is_Debug)
        	drawBoxLine(batch);
        
        
        super.draw(batch, parentAlpha);
        if(game.ls.ifm.SYSTEM_isBattling())
    		return;
        drawMaskShadow(batch);
        //drawMapAni(batch);
        //内存内生成一个整张地图的遮罩试试
        //if(data.bigMapShadowTexture!=null)
        	//batch.draw(data.bigMapShadowTexture, getX(), getY());
        /*
        if (data == null || !isVisible()) {
            return;
        }
        */

        //testActor.draw(batch, parentAlpha);
        if(game.showLightShadow){
        	batch.end();
        	drawLightAndShadow();
        	batch.begin();
        }
        drawAllBubble(batch,parentAlpha);
    }
    public void drawAllBubble(Batch batch, float parentAlpha){
    	for(int i=0;i<NPCList().size();i++){
    		NPC npc=NPCList.get(i);
    		if(npc!=null)
    			npc.drawBubble(batch, parentAlpha);
    	}
    	if(game.ls.player!=null)
    		game.ls.player.drawBubble(batch, parentAlpha);
    }
    public void drawLightAndShadow(){
    	float am=game.ts.getALight();
    	if(lights!=null){
    		for(int i=0;i<lights.size();i++){
    			if(am>0.7f)
    				lights.get(i).setActive(false);
    			else
    				lights.get(i).setActive(true);
    			lights.get(i).update(this);
    		}
    	}
    	rayHandler.setAmbientLight(am);
    	rayHandler.updateAndRender();
    }
	
    
}
