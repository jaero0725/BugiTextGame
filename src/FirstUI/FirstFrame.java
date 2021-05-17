package FirstUI;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import OnePersonUI.InformationFrame;

public class FirstFrame extends JFrame {

	private Clip clip;
	private FirstPanel firstPanel = new FirstPanel(clip);
	private JMenuItem informationItem = new JMenuItem("게임 정보(A)");

	public FirstFrame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);

		setTitle("타자 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);
		getContentPane().add(firstPanel);
		makeMenu();
		setResizable(false);
		setVisible(true);
	}
	
	private void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu informationMenu = new JMenu("도움(H)");
		informationMenu.add(informationItem);
		menuBar.add(informationMenu);
		informationItem.addActionListener(new InformationAction());
	}
	
	private class InformationAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			InformationFrame informationFrame = new InformationFrame();
		}
	}
}
