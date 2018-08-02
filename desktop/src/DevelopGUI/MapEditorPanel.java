package DevelopGUI;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JList;
import javax.swing.AbstractListModel;

import xyq.system.XYQDataBase;
public class MapEditorPanel{
	public JPanel contentPane;
	private JTextField XtextField;
	private JTextField YtextField;
	private JTextField NametextField;
	MapPanel Mappanel;
	JScrollPane scrollPane;
	private JTextField textField;
	private JTextField textField_1;
	
	@SuppressWarnings("unchecked")
	public MapEditorPanel(final XYQDataBase DB)
	{
		this.contentPane=new JPanel();
		contentPane.setLayout(null);
		contentPane.setBounds(0, 0, 1280, 768);
		contentPane.setPreferredSize(new Dimension(1280,688));
		JButton btnNewButton = new JButton("加载地图");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(NametextField.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "请选择地图", "错误！", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Mappanel = new MapPanel("./scene/"+NametextField.getText(),"地图名",DB);
				scrollPane.setViewportView(Mappanel);
				
				XtextField.setText(String.valueOf(Mappanel.map.getHorSegmentCount()));
				YtextField.setText(String.valueOf(Mappanel.map.getVerSegmentCount()));
				textField.setText(String.valueOf(Mappanel.map.getWidth()));
				textField_1.setText(String.valueOf(Mappanel.map.getHeight()));
				System.out.println("宽高度:"+"["+Mappanel.getWidth()+"-"+Mappanel.getHeight()+"]");
				System.out.println("坐标数:"+"["+Mappanel.mx+"-"+Mappanel.my+"]");
			}
		});
		btnNewButton.setBounds(1067, 196, 100, 23);
		contentPane.add(btnNewButton);
		
		
		JButton switchButton = new JButton("显示/隐藏白格");
		switchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(Mappanel!=null){
					if(Mappanel.showWhiteBlock)
						Mappanel.showWhiteBlock=false;
					else
						Mappanel.showWhiteBlock=true;
					Mappanel.repaint();
				}
				
			}
		});
		switchButton.setBounds(1170, 196, 140, 23);
		contentPane.add(switchButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 1024, 670);
		//scrollPane.setPreferredSize(new Dimension(1024,768));
		scrollPane.setViewportBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);

		
		XtextField = new JTextField();
		XtextField.setBounds(1067, 51, 66, 21);
		contentPane.add(XtextField);
		XtextField.setColumns(10);
		
		YtextField = new JTextField();
		YtextField.setBounds(1162, 51, 66, 21);
		contentPane.add(YtextField);
		YtextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("X\u5730\u56FE\u5757\u6570\u91CF     Y\u5730\u56FE\u5757\u6570\u91CF");
		lblNewLabel.setBounds(1067, 26, 181, 15);
		contentPane.add(lblNewLabel);
		
		NametextField = new JTextField();
		NametextField.setBounds(1067, 106, 161, 21);
		contentPane.add(NametextField);
		NametextField.setColumns(10);
		
		JLabel label = new JLabel("\u5730\u56FE\u6587\u4EF6\u540D");
		label.setBounds(1067, 82, 105, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(1067, 165, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(1162, 165, 66, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblxY = new JLabel("\u5730\u56FE\u603BX\u50CF\u7D20          Y\u50CF\u7D20");
		lblxY.setBounds(1067, 140, 161, 15);
		contentPane.add(lblxY);
		
		JLabel label_1 = new JLabel("选择地图 ：");
		label_1.setBounds(1077, 226, 147, 15);
		contentPane.add(label_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(1067, 252, 260, 250);
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
				      /*
				      int a=source.getSelectedIndex();
				      switch(a){
				      	case 0:NametextField.setText("1092.map");
				      	case 1:NametextField.setText("1001.map");
				      	case 2:NametextField.setText("1501.map");
				      	case 3:NametextField.setText("1070.map");
				      	case 4:NametextField.setText("xiliangnvguo.map");
				      	case 5:NametextField.setText("1208.map");
				      }*/
				      String sN=(String) source.getSelectedValue(); 
				      //System.out.println("选中了："+sN.split("=")[0]+".map");
				      NametextField.setText(sN.split("=")[0]+".map");
				   
			}
		});
		list.setModel(new AbstractListModel<Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String configTxt=read(System.getProperty("user.dir").replaceAll("core", "desktop")+"\\scene\\1000地图编号对应.txt");
			//String[] values = new String[] {"\u50B2\u6765\u56FD", "\u957F\u5B89\u57CE", "\u5EFA\u90BA\u57CE", "\u957F\u5BFF\u6751", "\u897F\u6881\u5973\u56FD", "\u6731\u7D2B\u56FD", "1002=\u95E8\u6D3E__\u5316\u751F\u5BFA", "1004=\u957F\u5B89_\u5927\u96C1\u58541\u5C42", "1005=\u957F\u5B89_\u5927\u96C1\u58542\u5C42", "1006=\u957F\u5B89_\u5927\u96C1\u58543\u5C42", "1007=\u957F\u5B89_\u5927\u96C1\u58544\u5C42", "1008=\u957F\u5B89_\u5927\u96C1\u58545\u5C42", "1009=\u957F\u5B89_\u5927\u96C1\u58547\u5C42", "1013=\u957F\u5B89_\u5E7F\u6E90\u94B1\u5E84", "1014=\u957F\u5B89_\u798F\u9686\u5F53\u94FA", "1015=\u957F\u5B89_\u5357\u5317\u6742\u8D27\u5E97", "1016=\u957F\u5B89_\u56DE\u6625\u5802\u836F\u5E97", "1017=\u957F\u5B89_\u9526\u7EE3\u9970\u54C1\u5E97", "1018=\u957F\u5B89_\u6E05\u97F3\u574A", "1019=\u957F\u5B89_\u4E66\u9999\u658B", "1020=\u957F\u5B89_\u4E07\u80DC\u6B66\u5668\u5E97", "1021=\u957F\u5B89_\u5E73\u5B89\u798F\u5BFF\u5E97", "1022=\u957F\u5B89_\u5F20\u8BB0\u5E03\u5E84", "1023=\u957F\u5B89_\u9547\u8FDC\u6B66\u9986", "1024=\u957F\u5B89_\u957F\u98CE\u9556\u5C40", "1025=\u957F\u5B89_\u51AF\u8BB0\u94C1\u94FA", "1026=\u957F\u5B89_\u56FD\u5B50\u76D1\u4E66\u5E93", "1028=\u957F\u5B89_\u957F\u5B89\u9152\u5E971\u697C", "1029=\u957F\u5B89_\u957F\u5B89\u9152\u5E972\u697C", "1030=\u957F\u5B89_\u4E91\u6765\u9152\u5E97", "1031=\u957F\u5B89_\u7559\u9999\u96012\u697C_\u601C\u9999\u8F69", "1032=\u957F\u5B89_\u7559\u9999\u96012\u697C_\u60DC\u7389\u8F69", "1033=\u957F\u5B89_\u7559\u9999\u96011\u697C", "1034=\u957F\u5B89_\u957F\u4E50\u8D4C\u574A", "1035=\u957F\u5B89_\u957F\u5B89\u6C11\u5C45(\u5916\u5750\u6807:\u957F\u5B89\u57CE534.110)", "1036=\u957F\u5B89_\u957F\u5B89\u6C11\u5C45(\u5916\u5750\u6807:\u957F\u5B89\u57CE370.30)\u8D75\u59E8\u5A18", "1037=\u957F\u5B89_\u957F\u5B89\u6C11\u5C45(\u5916\u5750\u6807:\u957F\u5B89\u57CE506.105)", "1038=\u957F\u5B89_\u957F\u5B89\u6C11\u5C45(\u5916\u5750\u6807:\u957F\u5B89\u57CE545.188)\u7D2B\u8863\u76D7", "1040=\u897F\u6881\u5973\u56FD", "1041=\u5B50\u6BCD\u6CB3\u5E95", "1042=\u89E3\u9633\u5C71", "1043=\u95E8\u6D3E_\u5316\u751F\u5BFA_\u85CF\u7ECF\u9601(\u7A7A\u5EA6)", "1044=\u957F\u5B89_\u91D1\u92AE\u6BBF", "1046=\u957F\u5B89_\u7687\u5BAB\u53A2\u623F", "1049=\u957F\u5B89_\u4E1E\u76F8\u5E9C", "1050=\u957F\u5B89_\u8859\u95E8", "1051=\u957F\u5B89_\u8859\u95E8\u5730\u7262", "1052=\u95E8\u6D3E_\u5927\u5510\u5B98\u5E9C_\u7A0B\u5E9C\u5185\u5BA4", "1054=\u95E8\u6D3E_\u5927\u5510\u5B98\u7B26_\u7A0B\u54AC\u91D1\u5E9C", "1056=\u957F\u5B89_\u79E6\u5E9C\u5185\u5BA4", "1057=\u957F\u5B89_\u79E6\u743C\u5E9C", "1070=\u957F\u5BFF\u6751\u5168\u666F", "1072=\u957F\u5BFF\u6751\u5F53\u94FA", "1075=\u957F\u5BFF\u6751\u9152\u9986", "1077=\u957F\u5BFF\u6751\u836F\u5E97", "1078=\u957F\u5BFF\u6751\u6C11\u5C45(\u5916\u5750\u6807\u957F\u5BFF\u6751148.144)\u9C81\u6210", "1079=\u957F\u5BFF\u6751\u6C11\u5C45(\u5916\u5750\u6807\u957F\u5BFF\u675124.179)\u8336\u5BA2", "1080=\u957F\u5BFF\u6751_\u6751\u957F\u5BB6", "1081=\u957F\u5BFF\u6751\u94B1\u5E84", "1082=\u957F\u5BFF\u6751_\u795E\u5E99", "1083=\u957F\u5BFF\u6751\u670D\u88C5\u5E97", "1085=\u957F\u5BFF\u6751\u6B66\u5668\u5E97", "1087=\u957F\u5BFF\u6751\u6742\u8D27\u5E97", "1090=\u5927\u96C1\u58546\u5C42", "1091=\u957F\u5BFF\u90CA\u5916\u5168\u666F", "1092=\u50B2\u6765\u56FD\u5168\u666F", "1093=\u50B2\u6765\u56FD\u5BA2\u6808", "1094=\u50B2\u6765\u56FD\u5F53\u94FA", "1095=\u50B2\u6765\u56FD\u670D\u9970\u5E97", "1098=\u50B2\u6765\u56FD\u6C11\u5C45(\u5916\u5750\u6807\u50B2\u6765\u56FD190.73)\u8001\u5F20\u5934", "1099=\u50B2\u6765\u56FD\u94B1\u5E84", "1100=\u50B2\u6765\u56FD_\u5723\u6BBF", "1101=\u50B2\u6765\u56FD\u6B66\u5668\u5E97", "1103=\u82B1\u679C\u5C71_\u6C34\u5E18\u6D1E", "1104=\u50B2\u6765\u56FD\u836F\u5E97", "1105=\u50B2\u6765\u56FD\u6742\u8D27\u5E97", "1106=\u50B2\u6765\u56FD\u6C11\u5C45(\u5916\u5750\u6807\u50B2\u6765\u56FD71.107)", "1107=\u50B2\u6765\u56FD\u6C11\u5C45(\u5916\u5750\u6807\u50B2\u6765\u56FD14.25)\u6587\u8001\u4F2F", "1110=\u5927\u5510\u56FD\u5883\u5168\u666F", "1111=\u95E8\u6D3E_\u5929\u5BAB\u5168\u666F", "1112=\u95E8\u6D3E_\u5929\u5BAB_\u51CC\u5BB5\u5B9D\u6BBF", "1113=\u95E8\u6D3E_\u5929\u5BAB_\u515C\u7387\u5BAB", "1114=\u6708\u5BAB\u5168\u666F", "1115=\u82E6\u884C\u865A\u7A7A", "1116=\u95E8\u6D3E_\u9F99\u5BAB\u5168\u666F", "1117=\u95E8\u6D3E_\u9F99\u5BAB_\u6C34\u6676\u5BAB", "1118=\u9F99\u5BAB_\u6D77\u5E95\u8FF7\u5BAB1", "1119=\u9F99\u5BAB_\u6D77\u5E95\u8FF7\u5BAB2", "1120=\u9F99\u5BAB_\u6D77\u5E95\u8FF7\u5BAB3", "1121=\u9F99\u5BAB_\u6D77\u5E95\u8FF7\u5BAB4", "1122=\u95E8\u6D3E_\u5730\u5E9C\u5168\u666F", "1123=\u95E8\u6D3E_\u5730\u5E9C_\u68EE\u7F57\u6BBF", "1124=\u95E8\u6D3E_\u5730\u5E9C_\u5730\u85CF\u738B\u5E9C", "1125=\u95E8\u6D3E_\u5730\u5E9C_\u8F6E\u56DE\u53F8", "1126=\u4E1C\u6D77\u6E7E_\u4E1C\u6D77\u5CA9\u6D1E", "1127=\u5730\u5E9C_\u5730\u72F1\u8FF7\u5BAB1", "1128=\u5730\u5E9C_\u5730\u72F1\u8FF7\u5BAB2", "1129=\u5730\u5E9C_\u5730\u72F1\u8FF7\u5BAB3", "1130=\u5730\u5E9C_\u5730\u72F1\u8FF7\u5BAB4", "1131=\u95E8\u6D3E_\u72EE\u9A7C\u5CAD\u5168\u666F", "1132=\u95E8\u6D3E_\u72EE\u9A7C\u5CAD_\u5927\u8C61\u6D1E", "1133=\u95E8\u6D3E_\u72EE\u9A7C\u5CAD_\u8001\u96D5\u6D1E", "1134=\u95E8\u6D3E_\u72EE\u9A7C\u5CAD_\u72EE\u738B\u6D1E", "1135=\u95E8\u6D3E_\u65B9\u5BF8\u5C71\u5168\u666F", "1137=\u95E8\u6D3E_\u65B9\u5BF8\u5C71_\u7075\u53F0\u5BAB", "1140=\u95E8\u6D3E_\u666E\u9640\u5C71\u5168\u666F", "1141=\u95E8\u6D3E_\u666E\u9640\u5C71_\u6F6E\u97F3\u6D1E", "1142=\u95E8\u6D3E_\u5973\u513F\u6751\u5168\u666F", "1143=\u95E8\u6D3E_\u5973\u513F\u6751\u6751\u957F\u5BB6", "1144=\u95E8\u6D3E_\u76D8\u4E1D\u5CAD_\u76D8\u4E1D\u6D1E", "1145=\u95E8\u6D3E_\u9B54\u738B\u8930_\u9B54\u738B\u5C45", "1146-\u95E8\u6D3E_\u4E94\u5E84\u89C2", "1147-\u95E8\u6D3E_\u4E94\u5E84\u89C2_\u4E7E\u5764\u6BBF", "1149-\u5927\u5510\u56FD\u5883_\u6C5F\u5DDE\u6C11\u5C45(\u5916\u5750\u6807\u5927\u5510\u56FD\u5883259.239)\u5434\u8001\u7239", "1152-\u5927\u5510\u56FD\u5883_\u6C5F\u5DDE\u53A2\u623F(\u5916\u5750\u6807\u5927\u5510\u56FD\u5883305.253)", "1153-\u5927\u5510\u56FD\u5883_\u91D1\u5C71\u5BFA", "1165-\u672A\u77E5", "1167-\u5927\u5510\u56FD\u5883_\u6C5F\u5DDE\u6C11\u5C45(\u5916\u5750\u6807\u5927\u5510\u56FD\u5883231.284)\u6587\u79C0", "1168-\u5927\u5510\u56FD\u5883_\u6C5F\u5DDE\u8859\u95E8", "1170-\u5927\u5510\u5883\u5916_\u9AD8\u8001\u5E84\u5927\u5385", "1171-\u5927\u5510\u5883\u5916_\u9AD8\u5C0F\u59D0\u95FA\u623F", "1173-\u5927\u5510\u5883\u5916\u5168\u666F", "1174-\u5317\u4FF1\u82A6\u6D32\u5168\u666F", "1175-\u5927\u5510\u56FD\u5883_\u770B\u5B88\u6240", "1177-\u9F99\u7A9F\u4E00\u5C42", "1178-\u9F99\u7A9F\u4E8C\u5C42", "1179-\u9F99\u7A9F\u4E09\u5C42", "1180-\u9F99\u7A9F\u56DB\u5C42", "1181-\u9F99\u7A9F\u4E94\u5C42", "1182-\u9F99\u7A9F\u516D\u5C42", "1183-\u9F99\u7A9F\u4E03\u5C42", "1186-\u51E4\u5DE2\u4E00\u5C42", "1187-\u51E4\u5DE2\u4E8C\u5C42", "1188-\u51E4\u5DE2\u4E09\u5C42", "1189-\u51E4\u5DE2\u56DB\u5C42", "1190-\u51E4\u5DE2\u4E94\u5C42", "1191-\u51E4\u5DE2\u516D\u5C42", "1192-\u51E4\u5DE2\u4E03\u5C42", "1193-\u6C5F\u5357\u91CE\u5916\u5168\u666F", "1197-\u6BD4\u6B66\u573A", "1198-\u95E8\u6D3E_\u5927\u5510\u5B98\u5E9C\u5168\u666F", "1201-\u5973\u5A32\u795E\u8FF9", "1202-\u65E0\u540D\u9B3C\u57CE", "1203-\u5C0F\u897F\u5929", "1204-\u5C0F\u96F7\u97F3", "1205-\u6218\u795E\u5C71", "1206-\u795E\u6B66\u575B", "1207-\u84EC\u83B1\u4ED9\u5C9B", "1208-\u6731\u7D2B\u56FD", "1209-\u6731\u7D2B\u56FD\u7687\u5BAB", "1210-\u9E92\u9E9F\u5C71", "1211-\u592A\u5C81\u5E9C", "1212-\u7405\u5B1B\u798F\u5730", "1213-\u70BC\u7130\u8C37\u5730", "1214-\u51B0\u98CE\u79D8\u5883", "1215-\u949F\u4E73\u77F3\u7A9F", "1216-\u4ED9\u7F18\u6D1E\u5929", "1217-", "1218-\u58A8\u5BB6\u6751", "1219-\u58A8\u5BB6\u6751_\u623F\u95F4\u4E00", "1220-\u58A8\u5BB6\u6751_\u623F\u95F4\u4E8C", "1221-\u58A8\u5BB6\u7981\u5730", "1223-", "1224-", "1225-", "1300-\u957F\u5B89\u793E\u533A", "1301-\u50B2\u6765\u793E\u533A", "1302-\u957F\u5BFF\u6751\u793E\u533A", "1400-\u82E6\u884C\u865A\u7A7A", "1401-\u666E\u901A\u6C11\u5B85", "1402-\u9AD8\u7EA7\u534E\u5B85", "1403-\u9876\u7EA7\u8C6A\u5B85", "1404-\u666E\u901A\u6C11\u5B85+1J\u5730\u677F", "1405-\u9AD8\u7EA7\u534E\u5B85+1J\u5730\u677F", "1406-\u9876\u7EA7\u8C6A\u5B85+1J\u5730\u677F", "1407-\u666E\u901A\u6C11\u5B85+2J\u5730\u677F", "1408-\u9AD8\u7EA7\u534E\u5B85+2J\u5730\u677F", "1409-\u9876\u7EA7\u8C6A\u5B85+2J\u5730\u677F", "1410-\u666E\u901A\u6C11\u5B85+3J\u5730\u677F", "1411-\u9AD8\u7EA7\u534E\u5B85+3J\u5730\u677F", "1412-\u9876\u7EA7\u8C6A\u5B85+3J\u5730\u677F", "1413-\u6D77\u84DD\u7CFB\u6C11\u5B85", "1414-\u6D77\u84DD\u7CFB\u534E\u5B85", "1415-\u6D77\u84DD\u7CFB\u8C6A\u5B85", "1420-\u666E\u901A\u5EAD\u9662", "1421-\u4E2D\u7EA7\u5EAD\u9662", "1422-\u9AD8\u7EA7\u5EAD\u9662", "1424-\u957F\u5B89\u793E\u533A\u623F\u5C4B\u7528\u5730", "1425-\u50B2\u6765\u793E\u533A\u623F\u5C4B\u7528\u5730", "1426-\u957F\u5BFF\u6751\u793E\u533A\u623F\u5C4B\u7528\u5730", "1501-\u5EFA\u90BA\u57CE\u5168\u666F", "1502-\u5EFA\u90BA_\u5175\u94C1\u94FA\uFF08\u5EFA\u90BA\u57CE186.112\uFF09\u6B66\u5668\u5E97\u8001\u677F\u3001\u6B66\u5668\u5E97\u638C\u67DC", "1503-\u5EFA\u90BA_\u674E\u79EF\u5E03\u5E84\uFF08\u5EFA\u90BA\u57CE169.33\uFF09\u670D\u88C5\u5E97\u8001\u677F", "", "1504-\u5EFA\u90BA_\u56DE\u6625\u5802\u5206\u5E97\uFF08\u5EFA\u90BA\u57CE215.5\uFF09\u836F\u5E97\u8001\u677F", "", "1505-\u5EFA\u90BA_\u4E1C\u5347\u8D27\u6808\uFF08\u5EFA\u90BA\u57CE117.12\uFF09\u6742\u8D27\u5E97\u8001\u677F", "", "1506-\u4E1C\u6D77\u6E7E\u5168\u666F", "", "1507-\u4E1C\u6D77\u6C89\u8239", "", "1508-\u4E1C\u6D77\u6C89\u8239\u4E8C\u5C42", "", "1509-\u4E1C\u6D77\u6C89\u8239\u5E95\u8231", "", "1511-\u87E0\u6843\u56ED", "", "1512=\u95E8\u6D3E_\u9B54\u738B\u5BE8", "", "1513=\u95E8\u6D3E_\u76D8\u4E1D\u5CAD", "", "1514=\u82B1\u679C\u5C71", "", "1523=\u5EFA\u90BA_\u5408\u751F\u8BB0\uFF08\u5EFA\u90BA\u57CE126.39\uFF09\u5F53\u94FA\u8001\u677F", "", "1524=\u5EFA\u90BA_\u4E07\u5B87\u94B1\u5E84\uFF08\u5EFA\u90BA\u57CE233.76\uFF09\u94B1\u5E84\u8001\u677F", "", "1525=\u5EFA\u90BA_\u6C11\u5C45\uFF08\u5EFA\u90BA\u57CE252.87\uFF09", "", "1526=\u5EFA\u90BA_\u6C11\u5C45\uFF08\u5EFA\u90BA\u57CE242.19\uFF09\u5468\u730E\u6237", "", "1527=\u5EFA\u90BA_\u6C11\u5C45\uFF08\u5EFA\u90BA\u57CE7.79\uFF09", "", "1528=\u95E8\u6D3E_\u5316\u751F\u5BFA_\u5149\u534E\u6BBF", "", "1529=\u95E8\u6D3E_\u5973\u513F\u6751_\u6C11\u5C45\uFF08\u5973\u513F\u675129.21\uFF09", "", "1531=\u6708\u5BAB_\u5E7F\u5BD2\u5BAB", "", "1532=\u6D77\u5E95\u8FF7\u5BAB5\u5C42", "", "1533=\u5927\u5510\u56FD\u5883_\u6C5F\u6D32\u6742\u8D27\u5E97\uFF08\u5927\u5510\u56FD\u5883259.296\uFF09\u6742\u8D27\u5E97\u8001\u677F", "", "1534=\u5EFA\u90BA_\u6C11\u5C45\u5185\u5BA4\uFF08\u5EFA\u90BA\u57CE7.79\uFF09\u674E\u5584\u4EBA", "", "1535=\u50B2\u6765_\u5BA2\u6808\u4E8C\u697C\uFF08\u50B2\u6765\u56FD182.27\uFF09", "", "1536=\u957F\u5BFF_\u9152\u5E97\u4E0A\u623F\uFF08\u957F\u5BFF\u675198.141\uFF09", "", "1537=\u5EFA\u90BA_\u8859\u95E8\uFF08\u5EFA\u90BA\u57CE143.80\uFF09", "", "1810=\u4E00\u7EA7\u5E2E\u6D3E\u91D1\u5E93_\u95E8\u5DE6", "", "1811=\u4E00\u7EA7\u5E2E\u6D3E\u91D1\u5E93_\u95E8\u53F3", "", "1812=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u91D1\u5E93_\u95E8\u5DE6", "", "1813=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u91D1\u5E93_\u95E8\u53F3", "", "1814=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u91D1\u5E93_\u95E8\u5DE6", "", "1815=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u91D1\u5E93_\u95E8\u53F3", "", "1820=\u4E00\u7EA7\u5E2E\u6D3E\u4E66\u9662_\u95E8\u5DE6", "", "1821=\u4E00\u7EA7\u5E2E\u6D3E\u4E66\u9662_\u95E8\u53F3", "", "1822=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u4E66\u9662_\u95E8\u5DE6", "", "1823=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u4E66\u9662_\u95E8\u53F3", "", "1824=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u4E66\u9662_\u95E8\u5DE6", "", "1825=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u4E66\u9662_\u95E8\u53F3", "", "1830=\u4E00\u7EA7\u5E2E\u6D3E\u517D\u5BA4_\u95E8\u5DE6", "", "1831=\u4E00\u7EA7\u5E2E\u6D3E\u517D\u5BA4_\u95E8\u53F3", "", "1832=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u517D\u5BA4_\u95E8\u5DE6", "", "1833=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u517D\u5BA4_\u95E8\u53F3", "", "1834=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u517D\u5BA4_\u95E8\u5DE6", "", "1835=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u517D\u5BA4_\u95E8\u53F3", "", "1840=\u4E00\u7EA7\u5E2E\u6D3E\u53A2\u623F_\u95E8\u5DE6", "", "1841=\u4E00\u7EA7\u5E2E\u6D3E\u53A2\u623F_\u95E8\u53F3", "", "1842=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u53A2\u623F_\u95E8\u5DE6", "", "1843=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u53A2\u623F_\u95E8\u53F3", "", "1844=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u53A2\u623F_\u95E8\u5DE6", "", "1845=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u53A2\u623F_\u95E8\u53F3", "", "1850=\u4E00\u7EA7\u5E2E\u6D3E\u836F\u623F_\u95E8\u5DE6", "", "1851=\u4E00\u7EA7\u5E2E\u6D3E\u836F\u623F_\u95E8\u53F3", "", "1852=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u836F\u623F_\u95E8\u5DE6", "", "1853=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u836F\u623F_\u95E8\u53F3", "", "1854=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u836F\u623F_\u95E8\u5DE6", "", "1855=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u836F\u623F_\u95E8\u53F3", "", "1860=\u4E00\u7EA7\u5E2E\u6D3E\u9752\u9F99\u5802_\u95E8\u5DE6", "", "1861=\u4E00\u7EA7\u5E2E\u6D3E\u9752\u9F99\u5802_\u95E8\u53F3", "", "1862=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u9752\u9F99\u5802_\u95E8\u5DE6", "", "1863=\u4E8C\u3001\u4E09\u7EA7\u5E2E\u6D3E\u9752\u9F99\u5802_\u95E8\u53F3", "", "1864=\u56DB\u3001\u4E94\u7EA7\u5E2E\u6D3E\u9752\u9F99\u5802_\u95E8\u5DE6"};
			String[] values =configTxt.split("\r\n");
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JLabel label_2 = new JLabel("\u64CD\u4F5C\u63D0\u793A\uFF1A");
		label_2.setBounds(1067, 522, 200, 15);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u3010\u7EA2\u8272\u3011 - \u963B\u6321\u5757");
		label_3.setBounds(1067, 547, 200, 15);
		contentPane.add(label_3);
		
		JLabel lblShift = new JLabel("\u3010\u7EFF\u8272\u3011 - 传送块  按住Shift添删  ALT查看");
		lblShift.setBounds(1067, 572, 300, 15);
		contentPane.add(lblShift);
		
		JLabel lblAlt = new JLabel("\u3010黄色小\u3011 - 数据库内有传送数据的传送块");
		lblAlt.setBounds(1067, 597, 300, 15);
		contentPane.add(lblAlt);
		
		JLabel lblAlt_1 = new JLabel("\u3010白色\u3011 - 通行块");
		lblAlt_1.setBounds(1067, 622, 200, 15);
		contentPane.add(lblAlt_1);
		
		JButton button = new JButton("输出地图配置");
		button.setBounds(1048, 658, 150, 21);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//Mappanel;
				File file = new File("d://MapData.txt");  //存放数组数据的文件
				 
				FileWriter out=null;
				try {
					out = new FileWriter(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}  //文件写入流
				 
				//将数组中的数据写入到文件中。每行各数据之间TAB间隔
				for(int i=0;i<Mappanel.mx;i++)
				{
					for(int j=0;j<Mappanel.my;j++)
					{
						try {
							out.write(Mappanel.mapData[i][j]+"");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try {
						out.write("\r\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, "输出完毕", "提示", JOptionPane.INFORMATION_MESSAGE);
				/*
				BufferedReader in = new BufferedReader(new FileReader(file));  //
  				String line;  //一行数据
  				int row=0;
  				//逐行读取，并将每个数组放入到数组中
  				while((line = in.readLine()) != null){
   					String[] temp = line.split("t"); 
   					for(int j=0;j<temp.length;j++){
    					arr2[row][j] = Double.parseDouble(temp[j]);
   					}
   					row++;
  				}
  				in.close(); 
				 */
			}
		});
		contentPane.add(button);
		
		JButton button2 = new JButton("载入地图配置");
		button2.setBounds(1200, 658, 150, 21);
		button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(Mappanel==null)
				{
					JOptionPane.showMessageDialog(null, "请先加载地图", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// 创建一个文件选择对象
				JFileChooser fd = new JFileChooser();
				// 设置默认路径
				File temploc = new File("D:\\");
				fd.setCurrentDirectory(temploc);
				// 设置对话框标题
				fd.setDialogTitle("从本地文件读取地图数据");
				// -----------------------------------
				fd.showOpenDialog(null);
				// 显示对话框
				String loc = fd.getSelectedFile().toString();
				System.out.println("载入地图数据:"+loc);
				
				
				
				BufferedReader in = null;
				try {
					in = new BufferedReader(new FileReader(loc));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}  //
  				String line;  //一行数据
  				int row=0;
  				//逐行读取，并将每个数组放入到数组中
  				try {
					while((line = in.readLine()) != null){
						for(int j=0;j<line.length();j++){
							Mappanel.mapData[row][j] = Integer.valueOf(String.valueOf(line.charAt(j)));
						}
						row++;
					}
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "文件可能不是对应这个地图的", "提示", JOptionPane.ERROR_MESSAGE);
				}
  				System.out.println(row);
  				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
  				Mappanel.repaint();
				JOptionPane.showMessageDialog(null, "载入完毕", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		contentPane.add(button2);
		
	}
	
	
	
	/**
     * 主要是输入流的使用，最常用的写法
     * @param filePath
     * @return
     */
    public static String read(String filePath)
    {
        // 读取txt内容为字符串
        StringBuffer txtContent = new StringBuffer();
        // 每次读取的byte数
        byte[] b = new byte[8 * 1024];
        InputStream in = null;
        try
        {
            // 文件输入流
            in = new FileInputStream(filePath);
            while (in.read(b) != -1)
            {
                // 字符串拼接
                txtContent.append(new String(b));
            }
            // 关闭流
            in.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return txtContent.toString();
    }
}
