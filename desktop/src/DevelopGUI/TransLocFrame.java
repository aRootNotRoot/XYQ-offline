package DevelopGUI;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import xyq.system.XYQDataBase;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransLocFrame extends JFrame {

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


	/**
	 * Create the frame.
	 */
	public TransLocFrame(final XYQDataBase DB,final MapPanel me) {
		setTitle("\u5730\u56FE\u4F20\u9001\u6570\u636E\u64CD\u4F5C - \u5BF9\u6570\u636E\u5E93");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 479, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u4ECE \u5730\u56FEid");
		lblNewLabel.setBounds(10, 10, 86, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u4ECE \u5730\u56FEX\u5750\u6807");
		lblNewLabel_1.setBounds(10, 60, 94, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u4ECE \u5730\u56FEY\u5750\u6807");
		lblNewLabel_2.setBounds(10, 111, 86, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u5230 \u5730\u56FEid");
		lblNewLabel_3.setBounds(220, 10, 88, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u5230 \u5730\u56FEX\u5750\u6807");
		lblNewLabel_4.setBounds(220, 60, 88, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("\u5230 \u5730\u56FEY\u5750\u6807");
		lblNewLabel_5.setBounds(220, 111, 88, 15);
		contentPane.add(lblNewLabel_5);
		
		final JButton btnNewButton = new JButton("\u65B0\u589E\u4F20\u9001\u70B9");
		final JButton btnNewButton_1 = new JButton("\u79FB\u9664\u4F20\u9001\u70B9");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				String mapID=textField.getText();
				String x=textField_1.getText();
				String y=textField_2.getText();
				
				String tomapID=textField_3.getText();
				String tox=textField_4.getText();
				String toy=textField_5.getText();
				
				String tips=textField_6.getText();
				
				boolean flag=DB.addMapTransConfig(Integer.valueOf(mapID), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(tomapID), Integer.valueOf(tox), Integer.valueOf(toy), tips);
				if(!flag)
					JOptionPane.showMessageDialog(null,"执行成功", "添加地图传送点数据", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null,"执行失败", "添加地图传送点数据", JOptionPane.INFORMATION_MESSAGE);
				
				me.reloadTrans();
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
				
				boolean flag=DB.removeMapTransConfig(Integer.valueOf(mapID), Integer.valueOf(x), Integer.valueOf(y));
				if(!flag)
					JOptionPane.showMessageDialog(null,"执行成功", "移除地图传送点数据", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null,"执行失败", "移除地图传送点数据", JOptionPane.INFORMATION_MESSAGE);
				me.reloadTrans();
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
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("\u5907\u6CE8\u4FE1\u606F");
		lblNewLabel_6.setBounds(10, 145, 86, 15);
		contentPane.add(lblNewLabel_6);
		
		textField_6 = new JTextField();
		textField_6.setBounds(110, 143, 309, 21);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
	}
}
