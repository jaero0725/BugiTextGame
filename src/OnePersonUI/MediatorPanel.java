package OnePersonUI;

import javax.swing.JFrame;

public class MediatorPanel extends JFrame {
	private ScorePanel scorePanel;
	private GamePanel gamePanel;
	
	public  MediatorPanel() {}
	public MediatorPanel(ScorePanel scorePanel, GamePanel gamePanel) {
		this.scorePanel = scorePanel;
		this.gamePanel = gamePanel;
	}
	public ScorePanel getScorePanel() {
		return scorePanel;
	}
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	public void setScorePanel(ScorePanel scorePanel) {
		this.scorePanel = scorePanel;
	}
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	public void setIncrease(int n) {
		this.scorePanel.increase(n);
	}
}

