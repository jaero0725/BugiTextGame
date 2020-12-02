package OnePersonUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;


public class OnePersonFrame extends JFrame {
	private String id = "";

	// ���������� ������ �� �� �ְ� �ϱ� ���ؼ� �����´�.
	private JMenuItem exitItem = new JMenuItem("���� ����(Q)");
	private JMenuItem setSpeedItem = new JMenuItem("���� ����(S)");
	private JMenuItem setLimitScoreItem = new JMenuItem("���� ����(P)");
	private JMenuItem informationItem = new JMenuItem("���� ����(A)");

	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Stop");

	private ScorePanel scorePanel = new ScorePanel();
	private EditPanel editPanel = new EditPanel();
	private GamePanel gamePanel = new GamePanel(scorePanel, editPanel);
	private MediatorPanel mediatorPanel; 

	public OnePersonFrame(String id) {
		mediatorPanel = new MediatorPanel();
		mediatorPanel.setGamePanel(gamePanel);
		mediatorPanel.setScorePanel(scorePanel);
		gamePanel.setScorePanel(scorePanel);
		scorePanel.setGamePanel(mediatorPanel.getGamePanel());
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);

		this.id = id;
		editPanel.setId(id);
		setTitle("1p Ÿ���� ����");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ��â��������
		setSize(800, 600);

		splitPane(); // JSplitPane�� �����Ͽ� ����Ʈ�ҿ� CENTER�� ����.
		makeMenu();

		setResizable(false); // �Ժη� ũ�⸦ �������� �ʰ�.
		setVisible(true);
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	// ContentPane�� SplitPanne���� ��������.
	private void splitPane() {
		JSplitPane hPane = new JSplitPane();
		// ContentPane()�� BorderLayout�� �⺻.
		getContentPane().add(hPane, BorderLayout.CENTER);

		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(550);

		// �������̰� ������Ű��. - �̰� ��� ������Ʈ�� �� ����ȴ�.
		hPane.setEnabled(false);

		// ���ʿ� �����г� �ֱ�.
		hPane.setLeftComponent(gamePanel);

		// ���ʿ� ���� �гΰ� �������� ���� �г��� �������ߵȴ�.
		JSplitPane pPane = new JSplitPane();
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane.setDividerLocation(300);
		pPane.setEnabled(false);

		// ScorePanel���̱�, id ���̱�
		pPane.setTopComponent(scorePanel);

		// EditPanel���̱�
		pPane.setBottomComponent(editPanel);

		// �����ʿ� pPane �ֱ�.
		hPane.setRightComponent(pPane);
	}

	// �޴��ٸ� ����� �Լ�. - 14�� 1��
	private void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// ���� ���� �޴�
		JMenu gameMenu = new JMenu("����(G)");
		gameMenu.add(exitItem);

		// ���� ���� �޴�
		JMenu setMenu = new JMenu("����(C)");
		setMenu.add(setSpeedItem);
		setMenu.add(setLimitScoreItem);

		// ������ �������� �޴�
		JMenu informationMenu = new JMenu("����(H)");
		informationMenu.add(informationItem);

		menuBar.add(gameMenu);
		menuBar.add(setMenu);
		menuBar.add(informationMenu);

		// �׼Ǹ�����
		exitItem.addActionListener(new ExitAction());
		informationItem.addActionListener(new InformationAction());
	}

	// �׼� ������ �ۼ�
	// ���� ����
	private class ExitAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	// ���� ���� ����
	private class SetLevelAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	// ���� ���� ����
	private class setLimitScoreAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	// ���� ����
	private class InformationAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			InformationFrame informationFrame = new InformationFrame();
		}
	}

}
