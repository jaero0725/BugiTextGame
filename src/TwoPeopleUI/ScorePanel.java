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

	private JLabel textLabel = new JLabel(id + "�� ���� : ");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));

	private JLabel textLabel2 = new JLabel(otherId + "�� ���� : ");
	private JLabel scoreLabel2 = new JLabel(Integer.toString(otherScore));
	private GamePanel gamePanel;
	
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setOtherId(String otherId) {
		this.otherId = otherId;
		textLabel2.setText(otherId + " ���� : ");
		System.out.println(name+"�� other = "+id);
	}
	
	// ScorePanel() ������..
	public ScorePanel() {
		
		System.out.println("otherId = "+ otherId);
		
		this.setBackground(new Color(136, 194, 234));
		setLayout(null);
		textLabel.setForeground(Color.WHITE);
		textLabel.setFont(new Font("�޸տ�����", Font.BOLD, 20));

		textLabel.setSize(127, 20);
		textLabel.setLocation(12, 28);
		add(textLabel); // scorePanel�� �پ���.
		scoreLabel.setForeground(Color.WHITE);

		scoreLabel.setFont(new Font("�޸տ�����", Font.BOLD, 20));
		scoreLabel.setSize(63, 20);
		scoreLabel.setLocation(159, 30);
		add(scoreLabel); // scorePanel�� �پ���.

		textLabel2.setForeground(Color.WHITE);
		textLabel2.setFont(new Font("�޸տ�����", Font.BOLD, 20));
		textLabel2.setSize(127, 20);
		textLabel2.setLocation(12, 58);
		add(textLabel2); // scorePanel�� �پ���.
		scoreLabel2.setForeground(Color.WHITE);
		scoreLabel2.setFont(new Font("�޸տ�����", Font.BOLD, 20));

		scoreLabel2.setSize(63, 20);
		scoreLabel2.setLocation(159, 60);
		add(scoreLabel2); // scorePanel�� �پ���.

		levelLabel.setForeground(Color.WHITE);
		levelLabel.setFont(new Font("�޸տ�����", Font.BOLD, 20));
		levelLabel.setSize(117, 45);
		levelLabel.setLocation(71, 77);
		add(levelLabel); // scorePanel�� �پ���

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

	// ���� ����
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
		textLabel.setText(id + " ���� : ");
	}
	

	
	public void resetLevelAndPoint() {
		this.score = 0;
		this.level = 1;
		levelLabel.setText("Level " + 1);
		scoreLabel.setText("0");
		scoreLabel2.setText("0");
	}

	// IP�ּ�, port ��ȣ�� ��� �޾ƾߵɱ�
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

		// ���� ���� username ����
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

				// ���� Ȯ��
				boolean result = client_socket.isConnected();
				if (result)
					System.out.println("Score Panel ���� ����");
				else
					System.out.println("Score Panel ���� ���� ����");

			} catch (NumberFormatException | IOException e) {
				System.out.println("���� ����");
				e.printStackTrace();
			}
		}
	}


}
