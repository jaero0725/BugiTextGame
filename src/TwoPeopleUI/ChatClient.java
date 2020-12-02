package TwoPeopleUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ChatClient extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private JTextField txtrSend;
	private JButton btnSend;
	private JScrollPane scrollPane;
	private Socket client_socket;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Vector<String> name = new Vector<>();

	private InputStream is;
	private OutputStream os;
	private Chat c;
	String username;
	String ip_addr;
	String port_no;
	private JTextPane area;
	
	public String getId() {
		return username;
	}
	public ChatClient(String username, String ip_addr, String port_no) {

		this.username = username;
		this.ip_addr = ip_addr;
		this.port_no = port_no;

		setBackground(new Color(0, 153, 255));
		setLayout(null);

		txtrSend = new JTextField();
		txtrSend.setBounds(12, 195, 131, 29);
		add(txtrSend);
		txtrSend.setColumns(10);

		btnSend = new JButton("SEND");
		btnSend.setBackground(new Color(0, 204, 255));
		btnSend.setBounds(150, 195, 63, 28);
		add(btnSend);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 198, 175);
		add(scrollPane);

		area = new JTextPane();
		area.setBackground(Color.WHITE);
		scrollPane.setViewportView(area);

		c = new Chat(username, ip_addr, port_no);
	}

	class Chat {

		String username;
		String ip_addr;
		String port_no;
		ChatClient c;

		public Chat(String username, String ip_addr, String port_no) {

			this.username = username;
			this.ip_addr = ip_addr;
			this.port_no = port_no;
			

			try {
				client_socket = new Socket(ip_addr, Integer.parseInt(port_no));
				AppendText("Welcome To Game!!! \n");

				oos = new ObjectOutputStream(client_socket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(client_socket.getInputStream());

				// 연결 확인
				boolean result = client_socket.isConnected();
				if (result)
					System.out.println("클라이언트 연결");
				else
					System.out.println("실패");

				ChatMsg cm = new ChatMsg(username, "100", "Hello");
				System.out.println("cm = " + cm);
				SendObject(cm);

				System.out.println("cm name = " + cm.getId());

				Client cli = new Client();
				cli.start();

				Myaction action = new Myaction();
				btnSend.addActionListener(action);
				txtrSend.addActionListener(action);
				txtrSend.requestFocus();

			} catch (NumberFormatException | IOException e) {
				System.out.println("연결 오류");
				e.printStackTrace();
			}
		}
	}

	// 수신해서 출력
	class Client extends Thread {

		ScorePanel score;
		
		public void run() {
			while (true) {
				try {
					Object ob = null;
					String msg = null;
					ChatMsg cm = null;

					try {
						ob = ois.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						break;
					}
					if (ob == null)
						break;
					if (ob instanceof ChatMsg) {
						cm = (ChatMsg) ob;
						msg = String.format("%s", cm.getData());
					} else {
						continue;
					}
					if (cm.getCode().matches("200")) {
						AppendText(msg);
					} else if (cm.getCode().matches("300")) {
						AppendImage(cm.img);
					} else if(cm.getCode().matches("101")) {
						String[] split = msg.split(" ");
						System.out.println("0 = "+split[0]);
						System.out.println("1 = "+split[1]);

						// 현재 스레드의 이름과 다른 상대 이름
						if(split[0].matches(username)) {
							score = new ScorePanel();
							score.setOtherId(split[1]);
							score.repaint();
						}
						if(split[1].matches(username)) {
							score = new ScorePanel();
							score.setOtherId(split[0]);
							score.repaint();
						}
					}
				} catch (IOException e) {
					AppendText("dis.read() error");
					try {
						oos.close();
						ois.close();
						client_socket.close();
						break;
					} catch (Exception ee) {
						AppendText("연결이 끊겼습니다\n");
						break;
					}
				}
			}
		}
	}

	class Myaction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnSend || e.getSource() == txtrSend) {
				String msg = null;
				msg = txtrSend.getText();
				sendMessage(msg);
				txtrSend.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtrSend.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
			}
		}
	}

	// 문자열 출력
	public void AppendText(String msg) {
		msg = msg.trim();
		int len = area.getDocument().getLength();
		area.setCaretPosition(len);
		area.replaceSelection(msg + "\n");
	}

	private void AppendImage(ImageIcon img) {
		int len = area.getDocument().getLength();
		area.setCaretPosition(len);

		Image image = img.getImage();
		int width, height;
		double ratio;
		width = img.getIconWidth();
		height = img.getIconHeight();
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(new_img);
			area.insertIcon(icon);
		} else {
			area.insertIcon(img);
			len = area.getDocument().getLength();
			area.setCaretPosition(len);
			area.replaceSelection("\n");
		}
	}

	public byte[] MakePacket(String msg) {
		byte[] packet = new byte[BUF_LEN];
		byte[] bb = null;
		int i;
		for (i = 0; i < BUF_LEN; i++)
			packet[i] = 0;
		try {
			bb = msg.getBytes("euc-kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.exit(0);
		}
		for (i = 0; i < bb.length; i++)
			packet[i] = bb[i];
		return packet;
	}

	public void sendMessage(String msg) {
		try {
			ChatMsg cm = new ChatMsg(username, "200", msg);
			oos.writeObject(cm);
		} catch (IOException e) {
			AppendText("dos.write() error");
			try {
				oos.close();
				ois.close();
				client_socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}

	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			System.out.println("send object");
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			AppendText("SendObject Error");
		}
	}
}
