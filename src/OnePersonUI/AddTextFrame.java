package OnePersonUI;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddTextFrame extends JFrame{
	private JTextField textField;
	File file = new File("resource/word/koreanWord.txt");
	public AddTextFrame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);
		setTitle("단어 추가 하기");
		setSize(334, 88);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uCD94\uAC00\uD560 \uB2E8\uC5B4");
		lblNewLabel.setFont(new Font("휴먼엑스포", Font.PLAIN, 14));
		lblNewLabel.setBounds(12, 10, 78, 26);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("\uD655\uC778");
		btnNewButton.setFont(new Font("휴먼엑스포", Font.PLAIN, 14));
		btnNewButton.setBounds(238, 12, 78, 23);
		
		getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(102, 13, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 가운데 출력시키기
		setResizable(false);
		setVisible(true);
		
		btnNewButton.addActionListener(new addWordAction());
	}
	
	private void addWord(String word) {
		try {
			//파일에 문자열을 쓴다
            //하지만 파일이 존재한다면 덮어쓰는게 아니라 .
            //그 뒤에 문자열을 이어서 작성한다.
			FileWriter fw = new FileWriter(file, true);
			fw.write(word);
            fw.close();
			System.out.println(word +" 저장 완료.");
		} catch (Exception e) {
			System.out.println("단어 저장 오류");
			e.printStackTrace();
		}
	}
	private class addWordAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//새로운 창 추가.
			//단어를 추가할 수 있는 창. 	
			String word =  textField.getText();
			addWord(word);
			textField.setText("");
		}
	}
}
