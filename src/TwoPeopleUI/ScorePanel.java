package TwoPeopleUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ScorePanel extends JPanel {

	private String id;
	private String otherId;
	private Vector name;
	
	private int score = 0;
	private int otherScore = 0;
	private int level = 1;

	private JLabel levelLabel = new JLabel("Level " + level);
	private JButton startButton = new JButton("Start");
	private JButton resetButton = new JButton("Reset");

	private JLabel textLabel = new JLabel(id + "의 점수 : ");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));

	private JLabel textLabel2 = new JLabel(otherId + "의 점수 : ");
	private JLabel scoreLabel2 = new JLabel(Integer.toString(otherScore));
	private GamePanel gamePanel;
	
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setOtherId(String otherId) {
		this.otherId = otherId;
		textLabel2.setText(otherId + " 점수 : ");
		System.out.println(name+"의 other = "+id);
	}
	
	// ScorePanel() 생성자..
	public ScorePanel() {
		
		System.out.println("otherId = "+ otherId);
		
		this.setBackground(new Color(136, 194, 234));
		setLayout(null);
		textLabel.setForeground(Color.WHITE);
		textLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));

		textLabel.setSize(127, 20);
		textLabel.setLocation(12, 28);
		add(textLabel); // scorePanel에 붙었음.
		scoreLabel.setForeground(Color.WHITE);

		scoreLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
		scoreLabel.setSize(63, 20);
		scoreLabel.setLocation(159, 30);
		add(scoreLabel); // scorePanel에 붙었음.

		textLabel2.setForeground(Color.WHITE);
		textLabel2.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
		textLabel2.setSize(127, 20);
		textLabel2.setLocation(12, 58);
		add(textLabel2); // scorePanel에 붙었음.
		scoreLabel2.setForeground(Color.WHITE);
		scoreLabel2.setFont(new Font("휴먼엑스포", Font.BOLD, 20));

		scoreLabel2.setSize(63, 20);
		scoreLabel2.setLocation(159, 60);
		add(scoreLabel2); // scorePanel에 붙었음.

		levelLabel.setForeground(Color.WHITE);
		levelLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
		levelLabel.setSize(117, 45);
		levelLabel.setLocation(71, 77);
		add(levelLabel); // scorePanel에 붙었음

		startButton.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 20));
		startButton.setBounds(12, 117, 195, 45);
		add(startButton);

		resetButton.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 20));
		resetButton.setBounds(12, 182, 195, 45);
		add(resetButton);

		startButton.addActionListener(new StartAction());
		resetButton.addActionListener(new ResetAction());
	}
	
	public void setEnabledStartButton() {
		System.out.println("startButton enabled");
		startButton.setEnabled(true);
	}

	public class StartAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gamePanel.startGame();
			startButton.setEnabled(false);
			resetButton.setEnabled(true);
		}
	}

	// 게임 리셋
	private class ResetAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gamePanel.resetGame();
			startButton.setEnabled(true);
			resetButton.setEnabled(false);
		}
	}
	
	public void increase(int n) {
		score += n;
		scoreLabel.setText(Integer.toString(score));
	}

	public void decrease(int n) {
		score -= n;
		scoreLabel.setText(Integer.toString(score));
	}

	public void setLevel(int level) {
		this.level = level;
		levelLabel.setText("Level " + level);
	}

	public int getScore() {
		return score;
	}

	public void setId(String id) {
		this.id = id;
		textLabel.setText(id + " 점수 : ");
	}
	

	
	public void resetLevelAndPoint() {
		this.score = 0;
		this.level = 1;
		levelLabel.setText("Level " + 1);
		scoreLabel.setText("0");
		scoreLabel2.setText("0");
	}

	// IP주소, port 번호를 어떻게 받아야될까
	class ConnectServer {
		String username;
		String ip_addr;
		String port_no;
		ChatClient c;
		private Socket client_socket;
		private DataInputStream dis;
		private DataOutputStream dos;
		private InputStream is;
		private OutputStream os;

		// 서버 연결 username 전달
		public ConnectServer(String username, String ip_addr, String port_no) {

			this.username = username;
			this.ip_addr = ip_addr;
			this.port_no = port_no;

			try {
				client_socket = new Socket(ip_addr, Integer.parseInt(port_no));
				is = client_socket.getInputStream();
				os = client_socket.getOutputStream();
				dis = new DataInputStream(is);
				dos = new DataOutputStream(os);

				// 연결 확인
				boolean result = client_socket.isConnected();
				if (result)
					System.out.println("Score Panel 서버 연결");
				else
					System.out.println("Score Panel 서버 연결 실패");

			} catch (NumberFormatException | IOException e) {
				System.out.println("연결 오류");
				e.printStackTrace();
			}
		}
	}


}
