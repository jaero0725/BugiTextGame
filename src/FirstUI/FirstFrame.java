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

	// 전반적으로 엑세스 할 수 있게 하기 위해서 빼놓는다.
	private Clip clip;
	
	private FirstPanel firstPanel = new FirstPanel(clip);

	private JMenuItem informationItem = new JMenuItem("게임 정보(A)");
	
	public FirstFrame() {
		//이미지 아이콘 왼쪽위에 넣기!
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);

		setTitle("타자 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null); //화면 가운데 출력시키기.
		getContentPane().add(firstPanel);

		makeMenu();

		setResizable(false); // 함부로 크기를 변경하지 않게.
		setVisible(true);
		
		//loadAudio("resource/sound/file_example_WAV_1MG.wav");
		//clip.start();
	}

	// 다른곳에서도 사용할 함수.
	private void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);


		// 개발자 정보관련 메뉴
		JMenu informationMenu = new JMenu("도움(H)");
		informationMenu.add(informationItem);

		menuBar.add(informationMenu);

		informationItem.addActionListener(new InformationAction());
	}

	// 액션리스너 설정
	// 게임 정보
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




