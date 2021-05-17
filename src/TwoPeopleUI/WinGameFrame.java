package TwoPeopleUI;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class WinGameFrame extends JFrame {

	private WinGamePanel winGamePanel;
	private ImageIcon gameOver = new ImageIcon("resource/img/win.jpg");
	
	public WinGameFrame() {}
	public WinGameFrame(int score, String id, int time) {
		winGamePanel = new WinGamePanel(score, id, time);

		Toolkit toolkit = Toolkit.getDefaultToolkit();

		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);
		setSize(380, 280);
		setTitle("You Win");

		getContentPane().add(winGamePanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null); // 화면 가운데 출력시키기
		setVisible(true);
		setResizable(false); // 함부로 크기를 변경하지 않게.
	}

	class WinGamePanel extends JPanel {
		
		private int score;
		private String id;
		private int time;

		private String getTime(int sec) {
			int min, hour;
			min = sec / 60;
			hour = min / 60;
			sec = sec % 60;
			min = min % 60;
			return min + " 분 " + sec + " 초";
		}

		public void paintComponent(Graphics g) {
			Dimension d = getSize();
			g.drawImage(gameOver.getImage(), 0, 0, d.width, d.height, null);
		}

		public WinGamePanel(int score, String id, int time) {
			this.score = score;
			this.id = id;
			this.time = time;

			setLayout(null);
			setBounds(0, 0, 392, 261);

			JLabel timeLabel = new JLabel("");
			timeLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 18));
			timeLabel.setForeground(Color.WHITE);
			timeLabel.setBounds(114, 145, 178, 26);
			timeLabel.setText(getTime(time));
			add(timeLabel);

			JLabel idLabel = new JLabel(id);
			idLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 18));
			idLabel.setForeground(Color.WHITE);
			idLabel.setBounds(114, 96, 178, 26);
			add(idLabel);

			JLabel scoreLabel = new JLabel("0");
			scoreLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 18));
			scoreLabel.setForeground(Color.WHITE);
			scoreLabel.setBounds(114, 193, 178, 26);
			String strScore = Integer.toString(score);
			scoreLabel.setText(strScore + " 점");
			add(scoreLabel);
		}
	}
}
