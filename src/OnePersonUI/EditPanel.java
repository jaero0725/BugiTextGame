package OnePersonUI;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class EditPanel extends JPanel{
	// 이미지 관련 필드
	private ImageIcon normalChar = new ImageIcon("resource/img/char_normal.png");
	private JLabel textLabel = new JLabel("");
	private String id = " ";
	
	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(normalChar.getImage(), 0, 0, d.width, d.height, null);
	}
	
	public EditPanel() {
		this.setLayout(null);
		textLabel = new JLabel("\uC544\uC774\uB514 \uC790\uB9AC");
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setFont(new Font("휴먼엑스포", Font.PLAIN, 17));
		textLabel.setBounds(52, 173, 109, 37);
		add(textLabel);
	}

	public void setId(String id) {
		this.id = id;
		textLabel.setText(id);
	}
	
	public String getId() {
		return id;
	}
}
