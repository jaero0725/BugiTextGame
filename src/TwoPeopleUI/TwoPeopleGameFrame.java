package TwoPeopleUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;


/*
 * 텍스트 객체 
 */
class TextObject extends JLabel {
   private String word; // 단어
   private int x; // x값
   private int y = 5; // 기본 y값
   private int color;
   private Font font = new Font("휴먼엑스포", Font.PLAIN, 12);

   public TextObject(String word, int x, int color) {
      this.word = word;
      this.x = x;
      this.color = color;
   }

   public String getWord() {
      return word;
   }

   public void setWord(String word) {
      this.word = word;
   }

   public int getX() {
      return x;
   }

   public void setX(int x) {
      this.x = x;
   }

   public int getY() {
      return y;
   }

   public void setY(int y) {
      this.y = y;
   }

   public int getColor() {
      return color;
   }

   public void setColor(int color) {
      this.color = color;
   }
}

/*
 * 걍싹다 이 프레임에 넣는다!
 */
//************************** Start of TwoPeopleGameFrame **************************
public class TwoPeopleGameFrame extends JFrame {

   // 전반적으로 엑세스 할 수 있게 하기 위해서 빼놓는다.
   private JMenuItem exitItem = new JMenuItem("게임 종료(Q)");
   private JMenuItem setSpeedItem = new JMenuItem("게임 레벨(S)");
   private JMenuItem setLimitScoreItem = new JMenuItem("게임 점수(P)");
   private JMenuItem informationItem = new JMenuItem("게임 정보(A)");

   /*
    * GamePanel용
    */
   private JTextField input = new JTextField(25);
   private JLabel timeLabel = new JLabel("Time : 0:00:00");
   private TextSource textSource = new TextSource();
   // 초기 라이프 값 = 10
   private int life = 10;
   private Vector<LifeIcon> lifeIcons = new Vector<LifeIcon>();
   private Vector<LifePanel> lifePanels = new Vector<LifePanel>();

   private ImageIcon gameBackGround = new ImageIcon("resource/img/GameBackground2.jpg"); // 배경그림

   private Vector<String> words = new Vector<String>();
   private Vector<Integer> x = new Vector<>();
   private Vector<Integer> color = new Vector<>();

   // words, x, color값을 TextObject에 넣어서 만든다.
   private Vector<TextObject> textArr = new Vector<TextObject>(50); // JLabel들이 있음.

   private int number = 0;
   public int speed = 10; // 떨어지는 속도

   // Thread 재시작 하기 위해서 Vector를 사용.
   private int timeThreadCount = 0;
   private RainThread rainThread = new RainThread();
   private Vector<TimeThread> timeThreads = new Vector<TimeThread>();

   private int threadStateFlag = 2; // set ..

   /*
    * ScorePanel용
    */
   private String id;

   private int score = 0;
   private int otherScore = 0;
   private int level = 1;

   private JLabel levelLabel = new JLabel("Level " + level);
   private JButton startButton = new JButton("Start");
   private JButton resetButton = new JButton("Reset");

   private JLabel textLabel = new JLabel(" 점수 : ");
   private JLabel scoreLabel = new JLabel(Integer.toString(score));

   private JLabel textLabel2 = new JLabel(" 점수 : ");
   private JLabel scoreLabel2 = new JLabel(Integer.toString(otherScore));

   private WinGameFrame winGameFrame = new WinGameFrame();
   private LoseGameFrame loseGameFrame = new LoseGameFrame();

   /*
    * ChatClient 용
    */
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

   public String username;
   public String ip_addr;
   public String port_no;

   private JTextPane area;
   private int time = 0;
   
   // GamePanel
   private GamePanel gamePanel = new GamePanel();

   // ************************** Start of TimeThread **************************
   class TimeThread extends Thread {

      public int getTime() {
         return time;
      }

      synchronized public void run() {
         while (true) {
            try {
               if (Thread.interrupted()) {
                  break;
               }
               // System.out.println("시간 : " + time + " 초");
               time++;
               timeLabel.setText(gamePanel.setTime(time));
               Thread.sleep(1000);
               switch(time){
                     case 10:   setLevel(2);   break;
                     case 20:   setLevel(3);   break;
                     case 30:   setLevel(4);   break;
                     case 40:   setLevel(5);   break;
                     case 50:   setLevel(6);   break;
                     case 60:   setLevel(7);   break;
                     case 70:   setLevel(8);   break;
                     case 80:   setLevel(9);   break;
                     case 90:   setLevel(10);   break;
               }
            } catch (InterruptedException e) {
               timeLabel.setText(gamePanel.setTime(0));
               System.out.println("Kill TimeThread");
               e.printStackTrace();
               break;
            }
         }
      }
   }// ************************** End of TimeThread **************************
   
   
   //레벨 구현.
   private void setLevel(int level) {
         switch (level) {
         case 1:
            speed = 10;
            levelLabel.setText("Level " + level);
            break;
         case 2:
            speed = 20;
            levelLabel.setText("Level " + level);
            break;
         case 3:
            speed = 25;
            levelLabel.setText("Level " + level);
            break;
         case 4:
            speed = 30;
            levelLabel.setText("Level " + level);
            break;
         case 5:
            speed = 35;
            levelLabel.setText("Level " + level);
            break;
         case 6:
            speed = 37;
            levelLabel.setText("Level " + level);
            break;
         case 7:
            speed = 40;
            levelLabel.setText("Level " + level);
            break;
         case 8:
            speed = 42;
            levelLabel.setText("Level " + level);
            break;
         case 9:
            speed = 44;
            levelLabel.setText("Level " + level);
            break;
         case 10:
            speed = 50;
            levelLabel.setText("Level " + level);
            break;
         }
    }
   
   private synchronized void changePosition() {
      // System.out.println("changePosition start");
      for (int i = 0; i < textArr.size(); i++) {
         TextObject textTmp = (TextObject) textArr.get(i);
         int y = textTmp.getY();
         
         // ****************************Life 감소 ***********************************//
         if (y > 500) {
            try { 
               // 못친 상황.
                textArr.remove(i);
                textTmp.setText("");
                LifeIcon icon;
               icon = (LifeIcon) lifeIcons.get(life - 1);
               icon.setText("");
                lifeIcons.remove(icon);
                life--;
                System.out.println("life 감소");
                repaint();
            } catch(ArrayIndexOutOfBoundsException e){
                
            }
         }
         textTmp.setY(y + speed);
      }
   }

   private int i = 0;

	class RainThread extends Thread {
		synchronized public void run() {
			System.out.println("rainThead 스타트 ");
			try {
				while (true) {
					// ****************************Game over ************************//
					if (life < 1) {
						// 내가 점수가 높을경우
						if (otherScore < score) {
							System.out.println("게임 승리");
							new WinGameFrame(score, id, time);
						}
						// 내가 점수가 더 낮을 경우
						else {
							System.out.println("게임 패배");
							new LoseGameFrame(score, id, time);
						}

						System.out.println("gameover");
						for (int j = 0; j < textArr.size(); j++) {
							TextObject tmp = (TextObject) textArr.get(j);
							tmp.setText("");
						}
						for (int n = 0; n < timeThreads.size(); n++) {
							time = 0;
							timeThreads.get(n).interrupt();
						}
						scoreLabel.setText("0");
						scoreLabel2.setText("0");
						levelLabel.setText("Level " + "0");
						textArr.removeAllElements();
						startButton.setEnabled(true);
					}
					if (Thread.interrupted()) {
						break;
					}
					// 단어고갈
					if (i >= 500) {
						break;
					}
					if (life <= 0) {
						System.out.println("Game over");
						// resetGame(); //게임끝내기
						break;
					}
					// 여기안에서 단어를 만들어서 내려준다.
					// System.out.println("단어 : " + words.elementAt(i) + "\t x 좌표 :" +
					// x.elementAt(i) + "\t 컬러: "
					// + color.elementAt(i));
					TextObject textTmp = new TextObject(words.elementAt(i), x.elementAt(i), color.elementAt(i));
					textTmp.setSize(100, 30); // 크기지정
					// 단어 특징
					switch (textTmp.getColor()) {
					case 0:
						textTmp.setForeground(Color.BLACK);
						break;
					case 1:
						textTmp.setForeground(Color.BLUE);
						break;
					case 2:
						textTmp.setForeground(Color.MAGENTA);
						break;
					case 3:
						textTmp.setForeground(Color.RED);
						break;
					}
					textTmp.setText(words.elementAt(i));
					textTmp.setLocation(textTmp.getX(), textTmp.getY()); // 위치 지정. x값은 넘어옴.
					gamePanel.add(textTmp);
					textArr.add(textTmp);// textArr에 textTmp를 넣어준다.
					changePosition(); // 텍스트 내려주기
					repaint(); // 다시 repaint
					Thread.sleep(1000);
					i++;
				}
			} catch (InterruptedException e) {
				System.out.println("Thread stop");
				e.printStackTrace();
			}
		}
	}

   // ************************** Start of Chat **************************
   class Chat {
      public Chat() {
         try {
            System.out.println(port_no + " 포트넘버로 넘어옴");
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
   } // ************************** End of Chat **************************

   // 수신해서 출력
   // ************************** Start of Client **************************
   class Client extends Thread {

      synchronized public void run() {

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
               } else
                  continue;

               if (cm.getCode().matches("101")) {
                  String[] split = msg.split(" ");
                  if (split[0].matches(username)) {
                     textLabel2.setText(split[1] + " 점수 :");
                     repaint();
                  }
                  if (split[1].matches(username)) {
                     textLabel2.setText(split[0] + " 점수 :");
                     repaint();
                  }
               } else if (cm.getCode().matches("102")) {
                  int num = JOptionPane.showConfirmDialog(null, "Game Start???");
                  if (num == 0) {
                     ChatMsg ready = new ChatMsg(username, "103", "ready");
                     SendObject(ready);
                  }
               } else if (cm.getCode().matches("103")) {
                  AppendText("Game Start!!");
                  ChatMsg word = new ChatMsg(username, "300", "word");
                  SendObject(word);
                  gamePanel.checkStartGame();
               } else if (cm.getCode().matches("200")) {
                  AppendText(msg);
               } else if (cm.getCode().matches("301")) {
                  String[] split = msg.split(" ");
                  words.add(split[0]);
                  x.add(Integer.parseInt(split[1]));
                  color.add(Integer.parseInt(split[2]));
               }
               // 두 번쨰 클라가 단어를 받으면 첫 번쨰 클라의 벡터에 단어 저장
               else if (cm.getCode().matches("302")) {
                  String[] split = msg.split(" ");
                  words.add(split[0]);
                  x.add(Integer.parseInt(split[1]));
                  color.add(Integer.parseInt(split[2]));
                  words.add(msg);
               }
               // 단어 전송 확인 프로토콜 rainTread start
               else if (cm.getCode().matches("303")) {
                 startButton.setEnabled(false); //btn못누르게 만듬.
                  rainThread.start(); // 단어가 내려오기 시작
               }
               // 점수 프로토콜
               else if (cm.getCode().matches("400")) {
                  int score = Integer.parseInt(cm.getData());
                  System.out.println("받은 점수 확인 === " + score);
                  gamePanel.increaseOther(score);
               }

               // 기본 검정 단어 삭제용 프로토콜 500
               else if (cm.getCode().matches("500")) {
                  for (int i = 0; i < textArr.size(); i++) {
                     TextObject textTmp = (TextObject) textArr.get(i);
                     String text = textTmp.getText();
                     System.out.println("받은 단어 == " + cm.getData());
                     if (cm.getData().matches(text)) {
                        System.out.println("삭제 단어 == " + text);
                        textArr.get(i);
                        textTmp.setText("");
                        repaint();
                     }
                  }
               } else if (cm.getCode().matches("501")) { // Blue Item
                  String[] split = msg.split(" ");
                  String delete = split[0];
                  int score = Integer.parseInt(split[1]);
                  for (int i = 0; i < textArr.size(); i++) {
                     TextObject textTmp = (TextObject) textArr.get(i);
                     String text = textTmp.getText();
                     if (delete.matches(text)) {
                        textArr.get(i);
                        textTmp.setText("");
                        repaint();
                     }
                  }
                  gamePanel.increaseOther(score);
               } else if (cm.getCode().matches("502")) { // Pink Item
                  int score = Integer.parseInt(msg);
                  gamePanel.increaseOther(score);
                  for (int j = 0; j < textArr.size(); j++) {
                      TextObject tmp = (TextObject) textArr.get(j);
                      tmp.setText("");
                   }
                  textArr.removeAllElements();
                  repaint();
               } else if (cm.getCode().matches("503")) { // Red Item
                  String[] split = msg.split(" ");
                  String delete = split[0];
                  int score = Integer.parseInt(split[1]);
                  for (int i = 0; i < textArr.size(); i++) {
                     TextObject textTmp = (TextObject) textArr.get(i);
                     String text = textTmp.getText();
                     if (delete.matches(text)) {
                        textArr.get(i);
                        textTmp.setText("");
                        repaint();
                     }
                  }
                  
                  gamePanel.decrease(score);
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
   }// ************************** End of Client **************************

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

   /*
    * 모든게임이 진행되는 GamePanel 내부 클래스! 모든 게임이 여기서 진행됨 1. 게임구현 2. 점수구현 3. 채팅구현 - ok
    * 
    */
   
   // Life Logic
   class LifeIcon extends JLabel {
      private ImageIcon lifeIcon = new ImageIcon("resource/img/life.png");
      private Image lifeImg = lifeIcon.getImage();
      public void paintComponent(Graphics g) {
         setOpaque(false);
         super.paintComponent(g);
         g.drawImage(lifeImg, 0, 0, this.getWidth(), this.getHeight(), null);
      }

      public LifeIcon() {
         this.setSize(40, 30);
         this.setForeground(new Color(136, 194, 234));
         this.setText("000");
         System.out.println("그림 아이콘그림");
      }
   }
   class LifePanel extends JPanel {

      public void makeIcon() {
         JLabel tmp = new JLabel();
         tmp.setSize(40, 30);
      }

      public LifePanel() {
         this.setBounds(162, 0, 400, 30);
         this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
         this.setBackground(new Color(255, 0, 0, 0)); // 투명

         for (int i = 0; i < life; i++) {
            LifeIcon lifeIcon = new LifeIcon();
            lifeIcons.add(lifeIcon);
            add(lifeIcon);
         }
      }
   }
   
   // ************************** Start of GamePanel **************************
   class GamePanel extends JPanel {

      // 배경그리기
      public void paintComponent(Graphics g) {
         Dimension d = getSize();
         g.drawImage(gameBackGround.getImage(), 0, 0, d.width, d.height, null);
         setOpaque(false);
      }

      // time, game thread 생성
      private synchronized void makeThreads() {
         TimeThread timeThread = new TimeThread();
         timeThreads.add(timeThread);
      }

      public synchronized TimeThread getTimeThread() {
         return timeThreads.get(timeThreadCount);
      }

      // Start 버튼을 누르면 발생하는 메서드
      public synchronized void readyGame() {
         ChatMsg cm = new ChatMsg(username, "102", "start");
         SendObject(cm);
      }
      //reset 버튼을 누르면 발생하는 메서드
      public synchronized void resetGame() {
         // 싹다지운뒤 시작한다.
         getTimeThread().interrupt();
         for (int j = 0; j < textArr.size(); j++) {
            TextObject tmp = (TextObject) textArr.get(j);
            tmp.setText("");
         }
         for (int n = 0; n < timeThreads.size(); n++) {
            time = 0;
            timeThreads.get(n).interrupt();
         }
         scoreLabel.setText("0");
         scoreLabel2.setText("0");
         levelLabel.setText("Level " + "0");
         textArr.removeAllElements();
         startButton.setEnabled(true);
      }
    
      // 상대가 ok를 누르면 실행되는 메서드
      public synchronized void checkStartGame() {
         System.out.println("click startGame");

         // 1. 서버에게 게임 스타트한다는걸 알려준다.
         makeThreads();
         getTimeThread().start();

         for (int i = 0; i < timeThreads.size(); i++)
            System.out.println("time thread = " + timeThreads.elementAt(i));
      }

      public synchronized String getText() {
         String newWord = textSource.get();
         return newWord;
      }

      private String setTime(int sec) {
         int min, hour;
         min = sec / 60;
         hour = min / 60;
         sec = sec % 60;
         min = min % 60;
         return "Time : " + hour + ":" + min + ":" + sec;
      }

   

      // 점수구현을 위함.
      public void increase(int n) {
         score += n;
         scoreLabel.setText(Integer.toString(score));
      }

      public void increaseOther(int n) {
         otherScore += n;
         scoreLabel2.setText(Integer.toString(otherScore));
      }

      public void decrease(int n) {
         score -= n;
         scoreLabel.setText(Integer.toString(score));
      }

      public void decreaseOther(int n) {
         otherScore -= n;
         scoreLabel2.setText(Integer.toString(otherScore));
      }

      // Gamepanel 생성자
      public GamePanel() {
         System.out.println("GamePanel 생성");
         this.setBackground(new Color(136, 194, 234));
         setLayout(null);
         // GamePanel 용
         input.setBounds(221, 486, 116, 21);
         getContentPane().add(input);
         input.setColumns(10);
         timeLabel.setFont(new Font("Eras Bold ITC", Font.PLAIN, 15));
         timeLabel.setSize(127, 24);
         timeLabel.setLocation(12, 10);
         getContentPane().add(timeLabel);

         textLabel.setBounds(562, 31, 196, 24);
         textLabel.setForeground(Color.WHITE);
         textLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
         getContentPane().add(textLabel);

         scoreLabel.setForeground(Color.WHITE);
         scoreLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
         scoreLabel.setSize(63, 20);
         scoreLabel.setLocation(707, 31);
         getContentPane().add(scoreLabel);

         textLabel2.setForeground(Color.WHITE);
         textLabel2.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
         textLabel2.setBounds(562, 62, 196, 24);
         getContentPane().add(textLabel2);

         scoreLabel2.setForeground(Color.WHITE);
         scoreLabel2.setFont(new Font("휴먼엑스포", Font.BOLD, 20));

         scoreLabel2.setSize(63, 20);
         scoreLabel2.setLocation(707, 62);
         getContentPane().add(scoreLabel2);

         levelLabel.setForeground(Color.WHITE);
         levelLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
         levelLabel.setSize(117, 45);
         levelLabel.setLocation(618, 92);
         getContentPane().add(levelLabel);

         startButton.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 20));
         startButton.setBounds(562, 147, 208, 45);
         getContentPane().add(startButton);

         resetButton.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 20));
         resetButton.setBounds(563, 202, 207, 45);
         getContentPane().add(resetButton);

         startButton.addActionListener(new StartAction());
         resetButton.addActionListener(new ResetAction());

         LifePanel lifePanel = new LifePanel();
         lifePanels.add(lifePanel);
         add(lifePanel);
         
         // ChatClient 용
         txtrSend = new JTextField();
         txtrSend.setBounds(562, 482, 131, 29);
         getContentPane().add(txtrSend);
         txtrSend.setColumns(10);

         btnSend = new JButton("SEND");
         btnSend.setBackground(new Color(176, 196, 231));
         btnSend.setBounds(695, 482, 75, 28);
         getContentPane().add(btnSend);

         area = new JTextPane();
         area.setBounds(562, 275, 208, 200);
         getContentPane().add(area);
         area.setBackground(Color.WHITE);

         // 여기서 이제 맞추면 점수를 올리는걸 구현한다.
         // 보내줘야함.
         // input.setFocusable(true);
         // input.requestFocus();
         // enter를 눌렀을 경우.
         input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JTextField t = (JTextField) e.getSource();
               String inWord = t.getText();// 여기의 값과. 비교

               for (int i = 0; i < textArr.size(); i++) {
                  TextObject textTmp = (TextObject) textArr.get(i);
                  String text = textTmp.getText();
                  if (text.equals(inWord)) {
                     // color를 보고 점수를 결정한다.
                     switch (textTmp.getColor()) {
                     // 이 단어를 지웠다는 걸 server에게 보내줌으로써 client 둘다 지워질 수 있게 만든다.
                     case 0: // Black
                        // server에 이 단어 지웠다고 보내준다.
                        ChatMsg delete = new ChatMsg(username, "500", text);
                        SendObject(delete);

                        increase(10);// 자기 점수 바꾸기.
                        ChatMsg score = new ChatMsg(username, "400", "10");
                        SendObject(score);
                        textTmp.setText("");
                        repaint();
                        break;

                        /*
                           	아이템 
                        1. 검정색 ->  기본
                        2. 파란색 ->  점수 2배 - +20점
                        3. 핑크색 ->  싹다지우기
                        4. 빨간색 ->  상대방 점수 까기 -20
                        */
                        
                        
                     case 1: // Blue
                        increase(20);
                        ChatMsg BlueItem = new ChatMsg(username, "501", text + " 20");
                        SendObject(BlueItem);
                        textTmp.setText("");
                        repaint();
                        break;
                     case 2: // Pink
                        int size = textArr.size()*10;
                        increase(size);
                        for (int j = 0; j < textArr.size(); j++) {
                            TextObject tmp = (TextObject) textArr.get(j);
                            tmp.setText("");
                         }
                        textArr.removeAllElements();
                        ChatMsg PinkItem = new ChatMsg(username, "502", Integer.toString(size));
                        SendObject(PinkItem);
                        //textTmp.setText("");
                        repaint();
                        break;
                     case 3: // Red
                        ChatMsg RedItem = new ChatMsg(username, "503", text+" 20");
                        SendObject(RedItem);
                        decreaseOther(20);
                        textTmp.setText("");
                        repaint();
                        break;

                     }
                  } else {
                     t.setText("");
                  }
               }
            }
         });
      }
   }// ************************** End of GamePanel **************************

   // 문자열 출력
   public synchronized void AppendText(String msg) {
      msg = msg.trim();
      int len = area.getDocument().getLength();
      area.setCaretPosition(len);
      area.replaceSelection(msg + "\n");
   }

   public synchronized void sendMessage(String msg) {
      try {
         ChatMsg cm = new ChatMsg(username, "200", msg);
         oos.writeObject(cm);
      } catch (IOException e) {
         AppendText("oos.write() error");
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

   public synchronized void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
      try {
         oos.writeObject(ob);
         System.out.println("send");
      } catch (IOException e) {
         AppendText("SendObject Error");
         System.exit(0);
      }
   }

   // TwoPeoPleGameFrame 생성자
   public TwoPeopleGameFrame(String username, String ip_addr, String port_no) {

      this.username = username;
      this.ip_addr = ip_addr;
      this.port_no = port_no;
      this.id = username;

      // **************************Chat을 여기서만든다.**************************
      c = new Chat();

      textLabel.setText(id + " 점수  :");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setTitle("2p 타이핑 게임");
      setSize(800, 600);
      setLocationRelativeTo(null); // 화면 가운데 출력시키기

      // GamePanel 넣기
      Container c = getContentPane();
      c.add(gamePanel);

      // 이미지아이콘 넣기
      Toolkit toolkit = Toolkit.getDefaultToolkit();
      Image img = toolkit.getImage("resource/img/favcion_bugi.png");
      setIconImage(img);

      // 메뉴 만들기
      makeMenu();
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setResizable(false); // 함부로 크기를 변경하지 않게.
      setVisible(true);
   }

   private void makeMenu() {
      JMenuBar menuBar = new JMenuBar();
      this.setJMenuBar(menuBar);

      // 게임 관련 메뉴
      JMenu gameMenu = new JMenu("게임(G)");
      gameMenu.add(exitItem);

      // 설정 관련 메뉴
      JMenu setMenu = new JMenu("설정(C)");
      setMenu.add(setSpeedItem);
      setMenu.add(setLimitScoreItem);

      // 개발자 정보관련 메뉴
      JMenu informationMenu = new JMenu("도움(H)");
      informationMenu.add(informationItem);
      menuBar.add(gameMenu);
      menuBar.add(setMenu);
      menuBar.add(informationMenu);

      // 액션리스너
      exitItem.addActionListener(new ExitAction());
      informationItem.addActionListener(new InformationAction());
   }

   // 액션 리스너 작성
   // 게임 시작
   public class StartAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         gamePanel.readyGame();
         
      }
   }

   // 게임 리셋
   private class ResetAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         gamePanel.resetGame();

      }
   }

   // 게임 종료
   private class ExitAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         System.exit(0);
      }
   }

   // 게임 레벨 설정
   private class SetLevelAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
   }

   // 게임 레벨 설정
   private class setLimitScoreAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
   }

   // 게임 정보
   private class InformationAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         InformationFrame informationFrame = new InformationFrame();
      }
   }

}
//************************** End of TwoPeopleGameFrame **************************