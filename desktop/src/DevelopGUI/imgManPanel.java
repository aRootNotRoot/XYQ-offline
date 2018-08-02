package DevelopGUI;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JList;
import javax.swing.AbstractListModel;
public class imgManPanel{
	public JPanel contentPane;
	String[] filename={
			"wzife.wdf【UI,图标，NPC头像】",
			"addon.wdf【战斗效果】",
			"atom.wdf【家具，施法场景】",
			"chat.wdf【聊天文本】",
			"firework.wdf【烟花】",
			"goods.wdf【道具静态图片和丢弃图片】",
			"item.wdf【道具图】",
			"magic.wdf【法术效果】",
			"mapani.wdf【地图装饰】",
			"mhimage.wdf【未知】",
			"misc.wdf【帮助文本】",
			"sence.wdf【未知场景数据】",
			"shape.wdf【角色，NPC人物】",
			"shape.wd1【换色调色板】",
			"shape.wd2【135以上召唤兽宝宝NPC小孩乌鸡】",
			"shape.wd3【120武器小雷鬼城召唤兽】",
			"shape.wd4【社区房子大鹏】",
			"shape.wd5【坐骑动画】",
			"shape.wd6【墨家召唤兽】",
			"shape.wd7【新召唤兽】",
			"smap.wdf【小地图】",
			"stock.wdf【股票文本】",
			"waddon.wdf【特技装备法术技能法术】",
			"wzife.wd1【其他UI头像】",
			"WZIMAGE.wdf【未知】"
	};
	int selectedIndex=0;
	String fileDir="./assets/";
	
	@SuppressWarnings("unchecked")
	public imgManPanel()
	{
		this.contentPane=new JPanel();
		contentPane.setLayout(null);
		contentPane.setBounds(0, 0, 1280, 670);
	
		JLabel wdfFileList = new JLabel("WDF 文件列表");
		wdfFileList.setBounds(10, 10, 161, 15);
		contentPane.add(wdfFileList);

		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 30, 270, 670);
		contentPane.add(scrollPane_1);
		
		@SuppressWarnings("rawtypes")
		JList list = new JList();
		scrollPane_1.setViewportView(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 
				      @SuppressWarnings("rawtypes")
				      JList source = (JList) e.getSource();
				      //@SuppressWarnings("deprecation")
				     // Object[] selection = 
				      //System.out.println(source.getSelectedIndex());
				      selectedIndex=source.getSelectedIndex();
				     
				   
			}
		});
		list.setModel(new AbstractListModel<Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] values = filename;
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		
		JButton button = new JButton("查看WDF");
		button.setBounds(130, 5, 100, 21);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {/*
				Runtime rn = Runtime.getRuntime();
				  Process p = null;
				  try {
				  p = rn.exec("\""+System.getProperty("user.dir") +"\\assets\\@WdfResource.exe"+"\""+"-F:\\Code\\XYQ\\assets\\chat.wdf");
				  } catch (Exception e) {
				  System.out.println("Error exec!");
				  }
				  */
				String filen=filename[selectedIndex].split("【")[0];
				 String cmdStr1=System.getProperty("user.dir") +"\\assets\\@WdfResource.exe "+System.getProperty("user.dir") +"\\assets\\"+filen;  
		          
		           
		         try {
					Runtime.getRuntime().exec("cmd.exe /c start "+cmdStr1);
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
		});
		contentPane.add(button);
	
	}
}
