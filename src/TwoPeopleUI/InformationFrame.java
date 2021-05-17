package TwoPeopleUI;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class InformationFrame extends JFrame{
	private TextPanel txtPanel = new TextPanel();
	
	public InformationFrame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("favcion_bugi.png");
		setIconImage(img);
		setTitle("타자 게임 정보");
		setSize(288, 142);
		setContentPane(txtPanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 가운데 출력시키기
		setResizable(false);	//함부로 크기를 변경하지 않게. 
		setVisible(true);
	}
	class TextPanel extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("2020 (C) Hansung UNIV / JAVA Programming", 15, 30);
			g.drawString("개발 by 1514043 최재호", 65, 50);
			g.drawString("Ver 1.0", 120, 70);
		}
	}
}

