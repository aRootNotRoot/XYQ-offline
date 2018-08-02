package xyq.system.utils;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;

public class InGameDebuger extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	XYQGame game;
	
	// 得到显示器屏幕的宽高
    public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    // 定义窗体的宽高
    public int windowsWedth = 280;
    public int windowsHeight = 720;

    
    JTextField equipID;
    JCheckBox advancedMake;
    
	public InGameDebuger(XYQGame xyqgame){
		this.game=xyqgame;
		setTitle("XYQ 内置调试器");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
		
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 270, 710);
		mainPane.add(tabbedPane);
		
		JPanel firstP = new JPanel();
		firstP.setBorder(new EmptyBorder(5, 5, 5, 5));
		firstP.setLayout(null);
		tabbedPane.addTab("快速调试", null, firstP, null);
		
		
		JButton btnNewButton = new JButton("进入空战斗");
		btnNewButton.setBounds(15, 10, 100, 30);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				game.cs.BATTLE_enterBattle(null);
			}
		});
		firstP.add(btnNewButton);
		
		JButton btnNewButton2 = new JButton("结束空战斗");
		btnNewButton2.setBounds(130, 10, 100, 30);
		btnNewButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				game.cs.BATTLE_endBattle();
			}
		});
		firstP.add(btnNewButton2);
		
		JButton btnNewButton3 = new JButton("随机召唤兽");
		btnNewButton3.setBounds(15, 45, 100, 30);
		btnNewButton3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//UI_showDialog(DialogHud.AITestDlg_ID);
				
				
				
			}
		});
		firstP.add(btnNewButton3);
		
		
		
		
		
		JPanel secondP = new JPanel();
		secondP.setBorder(new EmptyBorder(5, 5, 5, 5));
		secondP.setLayout(null);
		tabbedPane.addTab("调试", null, secondP, null);
		
		JButton btnNewButton4 = new JButton("生成未鉴定装备");
		btnNewButton4.setBounds(15, 10, 100, 30);
		btnNewButton4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int ID=Integer.valueOf(equipID.getText());
				ItemStackData itemStackData=game.itemDB.makeUndefinedEquip(ID,advancedMake.isSelected());
				game.cs.ACT_addItem(itemStackData);
			}
		});
		secondP.add(btnNewButton4);
		
		equipID=new JTextField();
		equipID.setBounds(130, 10, 100, 30);
		secondP.add(equipID);
		
		advancedMake=new JCheckBox("是否强化打造");
		advancedMake.setBounds(15, 45, 100, 30);
		secondP.add(advancedMake);
	}
	
}
