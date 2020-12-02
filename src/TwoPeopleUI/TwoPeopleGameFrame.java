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

	// 전반적으로 엑세스 할 수 있게 하기 위해서 빼놓는다.
	private JMenuItem exitItem = new JMenuItem("게임 종료(Q)");
	private JMenuItem setSpeedItem = new JMenuItem("게임 레벨(S)");
	private JMenuItem setLimitScoreItem = new JMenuItem("게임 점수(P)");
	private JMenuItem informationItem = new JMenuItem("게임 정보(A)");

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
		setTitle("2p 타이핑 게임");
		setSize(800, 600);
		setLocationRelativeTo(null); // 화면 가운데 출력시키기
		// 이미지아이콘 넣기
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);

		// username 등 인자로 전달
		splitPane(username, ip_addr, port_no); // JSplitPane을 생성하여 컨테트팬에 CENTER에 부착.
		makeMenu();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false); // 함부로 크기를 변경하지 않게.
		setVisible(true);
	}

	// username 등 여기서 받아서 초기화하고 chatClient 생성
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

		// 게임이 진행되는 Panel
		hPane.setLeftComponent(gamePanel);

		JSplitPane pPane = new JSplitPane();
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane.setDividerLocation(300);
		pPane.setEnabled(false);

		// ScorePanel붙이기 위.
		pPane.setTopComponent(scorePanel);

		// EditPanel붙이기 아래.
		pPane.setBottomComponent(chatPanel);

		// 오른쪽에 pPane 넣기.
		hPane.setRightComponent(pPane);

	}

	private void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// 게임 관련 메뉴
		JMenu gameMenu = new JMenu("게임(G)");
		gameMenu.add(exitItem);

		// 설정 관련 메뉴
		JMenu setMenu = new JMenu("설정(C)");
		setMenu.add(setSpeedItem);
		setMenu.add(setLimitScoreItem);

		// 개발자 정보관련 메뉴
		JMenu informationMenu = new JMenu("도움(H)");
		informationMenu.add(informationItem);
		menuBar.add(gameMenu);
		menuBar.add(setMenu);
		menuBar.add(informationMenu);

		// 액션리스너
		startButton.addActionListener(new StartAction());
		exitItem.addActionListener(new ExitAction());
		informationItem.addActionListener(new InformationAction());
	}

	// 액션 리스너 작성
	// 게임 시작
	public class StartAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gamePanel.startGame();
		}

	}

	// 게임 리셋
	private class ResetAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gamePanel.resetGame();
		}
	}

	// 게임 종료
	private class ExitAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	// 게임 레벨 설정
	private class SetLevelAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	// 게임 레벨 설정
	private class setLimitScoreAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	// 게임 정보
	private class InformationAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			InformationFrame informationFrame = new InformationFrame();
		}
	}

}
