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

	// ���������� ������ �� �� �ְ� �ϱ� ���ؼ� �����´�.
	private Clip clip;
	
	private FirstPanel firstPanel = new FirstPanel(clip);

	private JMenuItem informationItem = new JMenuItem("���� ����(A)");
	
	public FirstFrame() {
		//�̹��� ������ �������� �ֱ�!
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);

		setTitle("Ÿ�� ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null); //ȭ�� ��� ��½�Ű��.
		getContentPane().add(firstPanel);

		makeMenu();

		setResizable(false); // �Ժη� ũ�⸦ �������� �ʰ�.
		setVisible(true);
		
		//loadAudio("resource/sound/file_example_WAV_1MG.wav");
		//clip.start();
	}

	// �ٸ��������� ����� �Լ�.
	private void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);


		// ������ �������� �޴�
		JMenu informationMenu = new JMenu("����(H)");
		informationMenu.add(informationItem);

		menuBar.add(informationMenu);

		informationItem.addActionListener(new InformationAction());
	}

	// �׼Ǹ����� ����
	// ���� ����
	private class InformationAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			InformationFrame informationFrame = new InformationFrame();
		}
	}
	
	private void loadAudio(String pathName) {
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
		}catch (Exception e) {

		}
	}
}




