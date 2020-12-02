package TwoPeopleUI;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

public class TwoPeopleGameFrame extends JFrame {

	private String id = "";

	// ���������� ������ �� �� �ְ� �ϱ� ���ؼ� �����´�.
	private JMenuItem exitItem = new JMenuItem("���� ����(Q)");
	private JMenuItem setSpeedItem = new JMenuItem("���� ����(S)");
	private JMenuItem setLimitScoreItem = new JMenuItem("���� ����(P)");
	private JMenuItem informationItem = new JMenuItem("���� ����(A)");

	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Stop");

	private String username;
	private String ip_addr;
	private String port_no;
	private String otherId;

	private ScorePanel scorePanel = new ScorePanel();
	private GamePanel gamePanel = new GamePanel();
	private MediatorPanel mediatorPanel; 
	
	public TwoPeopleGameFrame(String username, String ip_addr, String port_no) {

		mediatorPanel = new MediatorPanel();
		mediatorPanel.setGamePanel(gamePanel);
		mediatorPanel.setScorePanel(scorePanel);
		gamePanel.setScorePanel(scorePanel);
		scorePanel.setGamePanel(mediatorPanel.getGamePanel());
		
		this.username = username;
		this.ip_addr = ip_addr;
		this.port_no = port_no;

		this.id = username;
		scorePanel.setId(username);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("2p Ÿ���� ����");
		setSize(800, 600);
		setLocationRelativeTo(null); // ȭ�� ��� ��½�Ű��
		// �̹��������� �ֱ�
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);

		// username �� ���ڷ� ����
		splitPane(username, ip_addr, port_no); // JSplitPane�� �����Ͽ� ����Ʈ�ҿ� CENTER�� ����.
		makeMenu();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false); // �Ժη� ũ�⸦ �������� �ʰ�.
		setVisible(true);
	}

	// username �� ���⼭ �޾Ƽ� �ʱ�ȭ�ϰ� chatClient ����
	private void splitPane(String username, String ip_addr, String port_no) {

		this.username = username;
		this.ip_addr = ip_addr;
		this.port_no = port_no;

		JSplitPane hPane = new JSplitPane();

		ChatClient chatPanel = new ChatClient(username, ip_addr, port_no);

		getContentPane().add(hPane, BorderLayout.CENTER);

		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(550);

		hPane.setEnabled(false);

		// ������ ����Ǵ� Panel
		hPane.setLeftComponent(gamePanel);

		JSplitPane pPane = new JSplitPane();
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane.setDividerLocation(300);
		pPane.setEnabled(false);

		// ScorePanel���̱� ��.
		pPane.setTopComponent(scorePanel);

		// EditPanel���̱� �Ʒ�.
		pPane.setBottomComponent(chatPanel);

		// �����ʿ� pPane �ֱ�.
		hPane.setRightComponent(pPane);

	}

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
		startButton.addActionListener(new StartAction());
		exitItem.addActionListener(new ExitAction());
		informationItem.addActionListener(new InformationAction());
	}

	// �׼� ������ �ۼ�
	// ���� ����
	public class StartAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gamePanel.startGame();
		}

	}

	// ���� ����
	private class ResetAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gamePanel.resetGame();
		}
	}

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
