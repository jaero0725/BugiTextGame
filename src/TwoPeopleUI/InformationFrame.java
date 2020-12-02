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
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);
		setTitle("Ÿ�� ���� ����");
		setSize(288, 142);
		setContentPane(txtPanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // ȭ�� ��� ��½�Ű��
		setResizable(false);	//�Ժη� ũ�⸦ �������� �ʰ�. 
		setVisible(true);
	}
	
	class TextPanel extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("2020 (C) Hansung UNIV / JAVA Programming", 15, 30);
			g.drawString("���� by 1514043 ����ȣ", 65, 50);
			g.drawString("Ver 1.0", 120, 70);
		}
	}
	
}
