package OnePersonUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {
	private JTextField textField;

	public LoginFrame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);

		setTitle("로그인");
		setSize(233, 131);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 가운데 출력시키기
		textField = new JTextField();
		textField.setBounds(87, 10, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);

		JButton registerButton = new JButton("\uB4F1\uB85D");
		registerButton.setFont(new Font("휴먼엑스포", Font.PLAIN, 16));

		registerButton.addActionListener(new ActionListener() {
			String id = (String) textField.getText();

			public void actionPerformed(ActionEvent e) {

				String id = textField.getText();
				System.out.println("id : " + id + " 입니다.");
				OnePersonFrame onePersonFrame = new OnePersonFrame(id);
				setVisible(false);
			}
		});

		registerButton.setBounds(24, 41, 179, 35);
		getContentPane().add(registerButton);

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					System.out.println("click enter");
					String id = textField.getText();
					System.out.println("id : " + id + " 입니다.");
					OnePersonFrame onePersonFrame = new OnePersonFrame(id);
					setVisible(false);
				}
			}
		});

		textField.requestFocus();

		JLabel registerLabel = new JLabel("\uC544\uC774\uB514  :");
		registerLabel.setFont(new Font("휴먼엑스포", Font.PLAIN, 12));
		registerLabel.setBounds(24, 13, 81, 15);

		getContentPane().add(registerLabel);
		setResizable(false);
		setVisible(true);

	}

	private class RegisterAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String id = textField.getText();
			System.out.println("id : " + id + " 입니다.");
			OnePersonFrame onePersonFrame = new OnePersonFrame(id);
			setVisible(false);

		}
	}
}
