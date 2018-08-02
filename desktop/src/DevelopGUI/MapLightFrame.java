package DevelopGUI;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import xyq.system.XYQDataBase;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Checkbox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapLightFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JTextField textField_3;
	public JTextField textField_4;
	public JTextField textField_5;
	public JTextField textField_6;
	public Checkbox blinkCB;

	/**
	 * Create the frame.
	 */
	public MapLightFrame(final XYQDataBase DB,final MapPanel me) {
		setTitle("地图静态点光源编辑");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 479, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("地图 id");
		lblNewLabel.setBounds(10, 10, 86, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("光源X坐标");
		lblNewLabel_1.setBounds(10, 60, 94, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("光源Y坐标");
		lblNewLabel_2.setBounds(10, 111, 86, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("光源强度");
		lblNewLabel_3.setBounds(220, 10, 88, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("光源颜色");
		lblNewLabel_4.setBounds(220, 60, 88, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("是否闪烁");
		lblNewLabel_5.setBounds(220, 111, 88, 15);
		//contentPane.add(lblNewLabel_5);
		
		final JButton btnNewButton = new JButton("新增光源");
		final JButton btnNewButton_1 = new JButton("移除光源");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				String mapID=textField.getText();
				String x=textField_1.getText();
				String y=textField_2.getText();
				
				float dist=Float.valueOf(textField_3.getText());
				String color=textField_4.getText();
				boolean blink=blinkCB.getState();
				
				String tips=textField_6.getText();
				
				boolean flag=DB.addMapLightConfig(Integer.valueOf(mapID), Integer.valueOf(x), Integer.valueOf(y), dist, color, blink, tips);
				if(!flag)
					JOptionPane.showMessageDialog(null,"执行成功", "添加地图光源数据", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null,"执行失败", "添加地图光源数据", JOptionPane.INFORMATION_MESSAGE);
				
				btnNewButton.setEnabled(true);
				btnNewButton_1.setEnabled(true);
				setVisible(false);
			}
		});
		btnNewButton.setBounds(83, 174, 93, 23);
		contentPane.add(btnNewButton);
		
		
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				
				String mapID=textField.getText();
				String x=textField_1.getText();
				String y=textField_2.getText();
				
				boolean flag=DB.removeMapLightConfig(Integer.valueOf(mapID), Integer.valueOf(x), Integer.valueOf(y));
				if(!flag)
					JOptionPane.showMessageDialog(null,"执行成功", "移除地图光源数据", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null,"执行失败", "移除地图光源数据", JOptionPane.INFORMATION_MESSAGE);
				
				btnNewButton.setEnabled(true);
				btnNewButton_1.setEnabled(true);
				setVisible(false);
			}
		});
		btnNewButton_1.setBounds(215, 174, 93, 23);
		contentPane.add(btnNewButton_1);
		
		textField = new JTextField();
		textField.setBounds(110, 7, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(110, 57, 66, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(110, 108, 66, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(318, 7, 66, 21);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(318, 57, 66, 21);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(318, 108, 66, 21);
		//contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		blinkCB=new Checkbox("blink? 是否闪烁", false);
		blinkCB.setBounds(220, 108, 166, 21);
		contentPane.add(blinkCB);
		
		JLabel lblNewLabel_6 = new JLabel("\u5907\u6CE8\u4FE1\u606F");
		lblNewLabel_6.setBounds(10, 145, 86, 15);
		contentPane.add(lblNewLabel_6);
		
		textField_6 = new JTextField();
		textField_6.setBounds(110, 143, 309, 21);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
	}
}
