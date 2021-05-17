package OnePersonUI;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlSoundFrame extends JFrame {

	public ControlSoundFrame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);
		setTitle("배경 음악 소리 조절");
		setSize(403, 146);
		setVisible(true);
		getContentPane().setLayout(null);
		
		JSlider slider = new JSlider();
		slider.setMinimum(-80);
		slider.setMaximum(6);
		slider.setFont(new Font("휴먼엑스포", Font.PLAIN, 14));
		slider.setPaintLabels(true);
		slider.setMinorTickSpacing(1);
		slider.setBounds(34, 46, 320, 45);
		
		getContentPane().add(slider);
		JLabel lblNewLabel = new JLabel("\uBC30\uACBD\uC74C\uC545 \uBCFC\uB968 \uC870\uC808");
		lblNewLabel.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(34, 10, 305, 26);
		getContentPane().add(lblNewLabel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 가운데 출력시키기
		setResizable(false);

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				float value = (float) slider.getValue();
				FloatControl volume = (FloatControl) OnePersonFrame.clip.getControl(FloatControl.Type.MASTER_GAIN);
				// System.out.println("최솟값 : " + volume.getMinimum()+ "최대값 : "
				// +volume.getMaximum());
				volume.setValue((float) value);
			}

		});
	}
}
