package DevelopGUI;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;

import xyq.system.XYQDataBase;

public class DevKitGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	public XYQDataBase DB;
	public static String[] DEFAULT_FONT = new String[] { "Table.font",
		"TableHeader.font", "CheckBox.font", "Tree.font", "Viewport.font",
		"ProgressBar.font", "RadioButtonMenuItem.font",
		"ToolBar.font",
		"ColorChooser.font",
		"ToggleButton.font",
		"Panel.font",
		"TextArea.font",
		"Menu.font",
		"TableHeader.font"
		// ,"TextField.font"
		, "OptionPane.font", "MenuBar.font", "Button.font", "Label.font",
		"PasswordField.font", "ScrollPane.font", "MenuItem.font",
		"ToolTip.font", "List.font", "EditorPane.font", "Table.font",
		"TabbedPane.font", "RadioButton.font", "CheckBoxMenuItem.font",
		"TextPane.font", "PopupMenu.font", "TitledBorder.font",
		"ComboBox.font" };
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.put("RootPane.setupButtonVisible", false);

					for (int i = 0; i < DEFAULT_FONT.length; i++)
						UIManager.put(DEFAULT_FONT[i], new Font("微软雅黑",
								Font.PLAIN, 14));
			
					DevKitGUI frame = new DevKitGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DevKitGUI() {
		DB=new XYQDataBase();
		setTitle("XYQ 辅助开发工具");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, -15, 1440, 900);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 47, 1387, 773);
		mainPane.add(tabbedPane);
		
		JScrollPane contentPane = new JScrollPane();
		tabbedPane.addTab("地图", null, contentPane, null);
		contentPane.setViewportBorder(null);
		contentPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		MapEditorPanel MEP =new MapEditorPanel(DB);
		contentPane.setViewportView(MEP.contentPane);
		
		
		JScrollPane dataPane = new JScrollPane();
		tabbedPane.addTab("数据库", null, dataPane, null);
		dataPane.setViewportBorder(null);
		dataPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		dataPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		

		JScrollPane itemPane = new JScrollPane();
		tabbedPane.addTab("物品数据库", null, itemPane, null);
		itemPane.setViewportBorder(null);
		itemPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		itemPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		JScrollPane skillPane = new JScrollPane();
		tabbedPane.addTab("技能数据库", null, skillPane, null);
		skillPane.setViewportBorder(null);
		skillPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		skillPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		JScrollPane sysPane = new JScrollPane();
		tabbedPane.addTab("系统", null, sysPane, null);
		sysPane.setViewportBorder(null);
		sysPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sysPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JScrollPane mediaPane = new JScrollPane();
		tabbedPane.addTab("声音媒体库", null, mediaPane, null);
		mediaPane.setViewportBorder(null);
		mediaPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		mediaPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		JScrollPane imgPane = new JScrollPane();
		tabbedPane.addTab("图形媒体库", null, imgPane, null);
		imgPane.setViewportBorder(null);
		imgPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		imgPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		imgManPanel IMP =new imgManPanel();
		imgPane.setViewportView(IMP.contentPane);
		
		JButton btnNewButton = new JButton("奇点");
		btnNewButton.setBounds(68, 10, 93, 30);
		mainPane.add(btnNewButton);
		
		
	}
}
