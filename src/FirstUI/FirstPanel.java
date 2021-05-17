package FirstUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import OnePersonUI.LoginFrame;
import TwoPeopleUI.TwoPeopleFrame;

public class FirstPanel extends JPanel {
	private JLabel titleLabel = new JLabel("부기 타자 게임");

	private JLabel onePersonLabel = new JLabel("혼자서 재미있게");
	private JLabel twoPeopleLabel = new JLabel("둘이서 신나게");
	private JLabel copyRightLabel = new JLabel("(C) 2020 ChoiJaeho");

	private Font titleFont = new Font("휴먼엑스포", Font.BOLD | Font.ITALIC, 50);
	private Font firstFont = new Font("휴먼엑스포", Font.BOLD | Font.ITALIC, 20);
	private Font changeFont = new Font("휴먼엑스포", Font.BOLD | Font.ITALIC, 22);
	private Font secondFont = new Font("휴먼엑스포", Font.BOLD | Font.ITALIC, 10);
	private ImageIcon backgroundIcon = new ImageIcon("resource/img/FirstBackground.jpg");

	public void paintComponent(Graphics g) {
		Image originImg = backgroundIcon.getImage();
		g.drawImage(originImg, 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}

	public FirstPanel(Clip clip) {
		setLayout(null);
		titleLabel.setFont(new Font("휴먼엑스포", Font.BOLD | Font.ITALIC, 47));

		titleLabel.setSize(402, 100);
		titleLabel.setLocation(65, 98);
		add(titleLabel);
		onePersonLabel.setForeground(Color.BLACK);

		onePersonLabel.setFont(new Font("휴먼엑스포", Font.BOLD | Font.ITALIC, 20));
		onePersonLabel.setSize(175, 50);
		onePersonLabel.setLocation(155, 260);
		add(onePersonLabel); //
		twoPeopleLabel.setForeground(Color.BLACK);

		twoPeopleLabel.setFont(firstFont);
		twoPeopleLabel.setSize(175, 50);
		twoPeopleLabel.setLocation(155, 312);
		add(twoPeopleLabel); 

		copyRightLabel.setFont(secondFont);
		copyRightLabel.setSize(220, 50);
		copyRightLabel.setLocation(155, 372);
		add(copyRightLabel);

		onePersonLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Click onePersonLabel");

				LoginFrame loginFrame = new LoginFrame();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				onePersonLabel.setFont(changeFont);
				onePersonLabel.setForeground(Color.BLUE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				onePersonLabel.setFont(firstFont);
				onePersonLabel.setForeground(Color.BLACK);
			}
		});

		twoPeopleLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Click twoPeopleLabel");
				TwoPeopleFrame twoPeopleFrame = new TwoPeopleFrame();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				twoPeopleLabel.setFont(changeFont);
				twoPeopleLabel.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				twoPeopleLabel.setFont(firstFont);
				twoPeopleLabel.setForeground(Color.BLACK);
			}

		});
	}
}
