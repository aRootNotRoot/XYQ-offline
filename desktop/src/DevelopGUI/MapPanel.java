package DevelopGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import xyq.system.XYQDataBase;
import xyq.system.map.MapData;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;







import java.util.ArrayList;

public class MapPanel extends JPanel{
	private static final long serialVersionUID = -6352788025440244338L;  
	Image[][] img;
    
    public int locX;
    public int locY;
    
    public int[][] mapData;
    public int mx;
    public int my;
    
    public MapData map;
	private int xC;
	private int Yc;
    
    boolean showWhiteBlock=false;
    TransLocFrame tframe;
    MapLightFrame lFrame;
    ArrayList<Integer> trans;
    
    XYQDataBase DB;
	private Integer forMapId;
    public MapPanel(String filename,String mapName,final XYQDataBase DB) {  
    	try {
			map=new MapData(filename);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	xC=map.getHorSegmentCount();
    	Yc=map.getVerSegmentCount();
    	this.setPreferredSize(new Dimension(map.getWidth(),map.getHeight()));
    	this.setBounds(0, 0, map.getWidth(), map.getHeight());
    	img=new Image[map.getHorSegmentCount()][map.getVerSegmentCount()];
    	
    	mx=map.cellData.length;
    	my=map.cellData[0].length;
		mapData=new int[mx][my];
		for(int x=0;x<mapData.length;x++)
			for(int y=0;y<mapData[0].length;y++){
				mapData[x][y]=(int)map.cellData[x][y];
			}
    	/*
    	mapData=new int[Integer.valueOf(map.getWidth()/20)][Integer.valueOf(map.getHeight()/20)];
    	for(int as=0;as<Integer.valueOf(map.getWidth()/20);as++)
    		for(int bs=0;bs<Integer.valueOf(map.getHeight()/20);bs++)
    			mapData[as][bs]=0;
    	*/
    	mx=Integer.valueOf(map.getWidth()/20);
    	my=Integer.valueOf(map.getHeight()/20);
    	for(int y=0;y<map.getHorSegmentCount();y++)
    	{
    		for(int x=0;x<map.getVerSegmentCount();x++)
    		{
    			img[y][x]=Toolkit.getDefaultToolkit().createImage(map.getJpegData(y, x));
    		}
    	}
    	this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				 Point mousepoint = e.getPoint();
				 int xxx=(int) Math.floor(mousepoint.x/20);
				 int yyy=(int) Math.floor((map.getHeight()-mousepoint.y)/20);
				 //yyy=(int) Math.floor(map.getHeight()/20)-yyy;
				 System.out.println("-----------------------------------------------");
				 System.out.println("点击地图像素["+mousepoint.x+","+mousepoint.y+"]");
				 int mmmY=map.getHeight()-mousepoint.y;
				 System.out.println("Y翻转点击地图像素["+mousepoint.x+","+mmmY+"]");
				 System.out.println("点击逻辑坐标["+xxx+","+yyy+"]");
				
				 if(e.isShiftDown())
				 {
					 System.out.println("打开传送数据["+mousepoint.x+","+mousepoint.y+"]");
					 tframe.setVisible(true);
					 tframe.textField.setText(String.valueOf(forMapId));
					 tframe.textField_1.setText(String.valueOf(xxx));
					 tframe.textField_2.setText(String.valueOf(yyy));
					 
				 }
				 else if(e.isAltDown())
				 {
					 System.out.println("打开传送数据["+mousepoint.x+","+mousepoint.y+"]");
					 tframe.setVisible(true);
					 tframe.textField.setText(String.valueOf(forMapId));
					 tframe.textField_1.setText(String.valueOf(xxx));
					 tframe.textField_2.setText(String.valueOf(yyy));
					 int[] target=DB.loadMapTransConfig(forMapId, xxx, yyy);
					 tframe.textField_3.setText(String.valueOf(target[0]));
					 tframe.textField_4.setText(String.valueOf(target[1]));
					 tframe.textField_5.setText(String.valueOf(target[2]));
					 String tips=DB.loadMapTransConfigTips(forMapId, xxx, yyy);
					 tframe.textField_6.setText(String.valueOf(tips));
				 } else if(e.isControlDown())
				 {
					lFrame.setVisible(true);
					lFrame.textField.setText(String.valueOf(forMapId));
					lFrame.textField_1.setText(String.valueOf(mousepoint.x));
					lFrame.textField_2.setText(String.valueOf(mmmY));
				 }/* 
				 else
				 {
					 mapData[xxx][yyy]=1;
					 repaint();
				 }
				 */
			}
    	});
    	tframe=new TransLocFrame(DB,this);
    	lFrame=new MapLightFrame(DB, this);
    	
    	String[] strs=filename.split("/");
    	System.out.println("加载这个地图："+strs[strs.length-1]);
    	String strs2=strs[strs.length-1].substring(0, 4);
    	this.forMapId=Integer.valueOf(strs2);
    	this.trans=DB.loadAllMapTransConfig(Integer.valueOf(forMapId));
    	System.out.println("加载完毕");
    	this.DB=DB;
    }  
    
    // 固定背景图片，允许这个JPanel可以在图片上添加其他组件  
    protected void paintComponent(Graphics g) {
    	int x=0;
    	int y=0;
    	//Image img=Toolkit.getDefaultToolkit().getImage(image);
    	for(x=0;x<xC;x++)
    	{
    		for(y=0;y<Yc;y++)
    		{
				
    			g.drawImage(img[x][y], x*320, y*240, 320, 240, this);	
    		}
    	}
    	Graphics2D g2;
		g2 = (Graphics2D) g;
		if(showWhiteBlock){
		g2.setColor(Color.WHITE); 
    	for(x=0;x<this.getWidth();x+=20)
    		g2.drawLine(x, 0, x, this.getHeight());	
    	for(y=0;y<this.getHeight();y+=20)
    		g2.drawLine(0, y, this.getWidth(), y);
		}
    	
    	for(int as=0;as<mx;as++)
    		for(int bs=0;bs<my;bs++)
    			if(mapData[as][bs]==1)
    			{
    				g2.setColor(Color.RED);
    				g2.drawRect(as*20, bs*20, 20, 20);
    			}
    	
    	
    	for(int as=0;as<mx;as++)
    		for(int bs=0;bs<my;bs++)
    			if(mapData[as][bs]==2)
    			{
    				g2.setColor(Color.GREEN);
    				g2.drawRect(as*20, bs*20, 20, 20);
    			}
    			else if(mapData[as][bs]==3)
    			{
    				g2.setColor(Color.BLUE);
    				g2.drawRect(as*20, bs*20, 20, 20);
    			}
    	
    	for(int ii=0;ii<trans.size();ii+=2){
    		g2.setColor(Color.YELLOW);
			//g2.drawRect(as*20, bs*20, 20, 20);
			g2.drawRect(trans.get(ii)*20+2, (map.getHeight()-(trans.get(ii+1)+1)*20)+2, 16, 16);
    	}
    }
    public void reloadTrans(){
    	this.trans=this.DB.loadAllMapTransConfig(Integer.valueOf(forMapId));
    }
    public byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

}
