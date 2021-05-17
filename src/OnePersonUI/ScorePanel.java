package OnePersonUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ScorePanel extends JPanel {
	private String id;

	private int score = 0;
	private int level = 1;
	private JLabel textLabel = null;
	private JLabel scoreLabel = new JLabel("0", SwingConstants.CENTER); // 기본점수.
	private JLabel levelLabel = new JLabel("Level " + level);
	private JButton startButton = new JButton("Start");
	private JButton resetButton = new JButton("Reset");
	private GamePanel gamePanel;
	JLabel lblNewLabel = new JLabel("New label");

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	// ScorePanel() 생성자.
	public ScorePanel() {
		this.setBackground(new Color(136, 194, 234));

		textLabel = new JLabel("점수 : ");
		textLabel.setForeground(Color.WHITE);
		setLayout(null);

		textLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
		add(textLabel);

		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 22));
		scoreLabel.setSize(80, 31);
		scoreLabel.setLocation(139, 32);
		add(scoreLabel); // scorePanel에 붙었음.

		levelLabel.setForeground(Color.WHITE);
		levelLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
		levelLabel.setSize(117, 45);
		levelLabel.setLocation(57, 62);
		add(levelLabel); // scorePanel에 붙었음.

		startButton.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 20));
		startButton.setBounds(12, 117, 195, 45);
		add(startButton);

		textLabel.setSize(135, 32);
		textLabel.setLocation(12, 32);
		add(textLabel); // scorePanel에 붙었음.

		resetButton.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 20));
		resetButton.setBounds(12, 182, 195, 45);
		add(resetButton);
		resetButton.setEnabled(false);

		startButton.addActionListener(new StartAction());
		resetButton.addActionListener(new ResetAction());
	}

	public synchronized void increase(int n) {
		score += n;
		scoreLabel.setText(Integer.toString(score));
	}

	public void decrease(int n) {
		score -= n;
		scoreLabel.setText(Integer.toString(score));
	}

	public synchronized void setLevel(int level) {
		this.level = level;
		levelLabel.setText("Level " + level);
	}

	public synchronized int getScore() {
		return score;
	}

	public void resetLevelAndPoint() {
		this.score = 0;
		this.level = 1;
		levelLabel.setText("Level " + 1);
		scoreLabel.setText("0");
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
}
