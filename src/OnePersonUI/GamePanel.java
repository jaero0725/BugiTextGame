package OnePersonUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

class TextObject extends JLabel {
   private String name;
   private int number;
   private int ability; // , 2 = ability 1 , 3 = ability 2 , 4 = ability 3
   private Font font = new Font("휴먼엑스포", Font.PLAIN, 12);

   private int x = (int) (Math.random() * 480) + 10; // 첫 위치값 랜덤하게 준다.
   private int y = 5; // 기본 y값

   public TextObject(String name, int number) {
      super(name);
      this.setFont(font);
      this.number = number;
      this.ability = Math.random() < 0.7 ? 0 : -1;
      if (ability == 0) {
         ability = 0; // color == Black;
      }
      // 20프로 확률로 능력 랜덤으로 출력.
      else if (ability == -1) {
         double percent = Math.random();
         // 3가지 능력
         if (percent < 0.33) {
            ability = 1;
         } else if (percent > 0.33 && percent < 0.66) {
            ability = 2;
         } else if (percent < 1 && percent > 0.66) {
            ability = 3;
         }
      }
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

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getNumber() {
      return number;
   }

   public void setNumber(int number) {
      this.number = number;
   }

   public int getAbility() {
      return ability;
   }

   public void setAbility(int ability) {
      this.ability = ability;
   }
}

public class GamePanel extends JPanel {

   private JTextField input = new JTextField(25);
   private JLabel timeLabel = new JLabel("Time : 0:00:00");
   private ImageIcon gameBackGround = new ImageIcon("resource/img/GameBackground.jpg");

   private Vector<TextObject> textArr = new Vector<TextObject>(50);
   private ScorePanel scorePanel = null;
   private EditPanel editPanel = null;
   private TextSource textSource = new TextSource(); // 단어 백터 생성.
   private int number = 0;
   private int time = 0;

   // 초기 라이프 값 = 10
   private int life = 10;
   private Vector<LifeIcon> lifeIcons = new Vector<LifeIcon>();
   private Vector<LifePanel> lifePanels = new Vector<LifePanel>();
   
   public int pixel = 10;
   public int speed = 10;
   public int gameLevel = 1;

   // Thread 재시작 하기 위해서 Vector를 사용.
   private int threadCount = 0;
   private Vector<RainThread> rainThreads = new Vector<RainThread>();
   private Vector<TimeThread> timeThreads = new Vector<TimeThread>();
   private int threadStateFlag = 2; // set ..
   
   private EndGameFrame endGameFrame = new EndGameFrame();
		   
   public void setScorePanel(ScorePanel scorePanel) {
	 this.scorePanel = scorePanel;
	 System.out.println(scorePanel.getName() + " ScorePanel 만들어짐." +scorePanel.getScore());
   }
   
   private void saveRecord() {
      int scoreTmp = rainThreads.get(threadCount).getScore();
      String idTmp = editPanel.getId();
      int timeTmp = timeThreads.get(threadCount).getTime();
      
      System.out.println(scoreTmp + " "+ idTmp+ " "+ timeTmp);
      endGameFrame = new EndGameFrame(scoreTmp, idTmp, timeTmp);
   }
 
   private void makeThreads() {
      RainThread rainThread = new RainThread();
      TimeThread timeThread = new TimeThread();
      rainThreads.add(rainThread);
      timeThreads.add(timeThread);
   }

   private RainThread getRainThread() {
      return rainThreads.get(threadCount);
   }

   public TimeThread getTimeThread() {
      return timeThreads.get(threadCount);
   }

   public void paintComponent(Graphics g) {
      Dimension d = getSize();
      g.drawImage(gameBackGround.getImage(), 0, 0, d.width, d.height, null);
   }

   private String setTime(int sec) {
      int min, hour;
      min = sec / 60;
      hour = min / 60;
      sec = sec % 60;
      min = min % 60;
      return "Time : " + hour + ":" + min + ":" + sec;
   }

   class RainThread extends Thread { // 산성비 메소드 쓰레드를 상속받는다.
      private int score = 0;

      public int getScore() {
         return score;
      }

      synchronized public void run() {
         try {
            while (true) {
               // ****************************Game over ************************//
               if (Thread.interrupted()) {
                  break;
               }
               if (life <= 0) {
                  System.out.println("Game over");
                  resetGame();
                  break;
               }
               score = scorePanel.getScore();
               if (score > 100 && score <= 200) {
                  setLevel(2);
               } else if (score > 200 && score <= 300) {
                  setLevel(3);
               } else if (score > 300 && score <= 400) {
                  setLevel(4);
               } else if (score > 400 && score <= 500) {
                  setLevel(5);
               } else if (score > 500 && score <= 600) {
                  setLevel(6);
               } else if (score > 600 && score <= 700) {
                  setLevel(7);
               } else if (score > 700 && score <= 800) {
                  setLevel(8);
               } else if (score > 800 && score <= 900) {
                  setLevel(9);
               } else if (score > 900 && score <= 1000) {
                  setLevel(10);
               }
               // System.out.println("RainThread start");
               setText();
               changePosition();
               GamePanel.this.repaint();
               // 능력사용 - 속도를 변경 한다.
               if (threadStateFlag >= 200) { // Blue Ability
                  System.out.println("** blue text ability run ***");
                  Thread.sleep(3000);
                  threadStateFlag = 0;
               } else if (threadStateFlag < 0) {
                  Thread.sleep(500);
               } else {
                  threadStateFlag = 0;
                  Thread.sleep(1000);
               }
               System.out.println("ability protocol :" + threadStateFlag);
               threadStateFlag++;
            }
         } catch (InterruptedException e) {
            System.out.println("Thread stop");
            e.printStackTrace();
         }
      }

   }

   class TimeThread extends Thread {
      private int time = 0;

      public int getTime() {
         return time;
      }

      synchronized public void run() {
         while (true) {
            try {
               if (Thread.interrupted()) {
                  break;
               }
               System.out.println("시간 : " + time + " 초");
               time++;
               timeLabel.setText(setTime(time));
               Thread.sleep(1000);
            } catch (InterruptedException e) {
               timeLabel.setText(setTime(0));
               System.out.println("Kill TimeThread");
               e.printStackTrace();
               break;
            }
         }
      }
   }

   // Score를 기준으로 Level이 변경된다.
   private void setLevel(int level) {
      switch (level) {
      case 1:
         scorePanel.setLevel(1);
         this.speed = 10;
         break;
      case 2:
         scorePanel.setLevel(2);
         this.speed = 15;
         break;
      case 3:
         scorePanel.setLevel(3);
         this.speed = 20;
         break;
      case 4:
         scorePanel.setLevel(4);
         this.speed = 25;
         break;
      case 5:
         scorePanel.setLevel(5);
         this.speed = 30;
         break;
      case 6:
         scorePanel.setLevel(6);
         this.speed = 33;
         break;
      case 7:
         scorePanel.setLevel(7);
         this.speed = 35;
         break;
      case 8:
         scorePanel.setLevel(8);
         this.speed = 37;
         break;
      case 9:
         scorePanel.setLevel(9);
         this.speed = 40;
         break;
      case 10:
         scorePanel.setLevel(10);
         this.speed = 45;
         break;
      }
   }

   private void setText() {
      // System.out.println("SetText");
      String newWord = textSource.get();
      TextObject textTmp = new TextObject(newWord, number);
      // System.out.println(textTmp.getText() + " 단어 출력.");

      for (int i = 0; i < textArr.size(); i++) {
         TextObject tmp = (TextObject) textArr.get(i);
         tmp.setSize(100, 30);

         // 단어 특징
         switch (tmp.getAbility()) {
         case 0:
            tmp.setForeground(Color.BLACK);
            break;
         case 1:
            tmp.setForeground(Color.BLUE);
            break;
         case 2:
            tmp.setForeground(Color.MAGENTA);
            break;
         case 3:
            tmp.setForeground(Color.RED);
            break;
         }
         tmp.setLocation(tmp.getX(), tmp.getY());
         add(tmp);
      }
      textArr.add(textTmp);
      number++;
   }
   
   public void resetGame() {
	  scorePanel.setEnabledStartButton();
      //life객체 지우기
      LifePanel lifePanel = lifePanels.get(threadCount);
      lifePanel.removeAll();
      remove(lifePanel);
      
      number = 0;
      time = 0;
      life = 10;
      pixel = 10;
      speed = 10;
      gameLevel = 1;
      for (int i = 0; i < textArr.size(); i++) {
         TextObject textTmp = (TextObject) textArr.get(i);
         textTmp.setText("");
      }
      getTimeThread().interrupt();
      getRainThread().interrupt();
      scorePanel.resetLevelAndPoint();
      textArr.removeAllElements();

      saveRecord();
      threadCount++;
      
   }

   public void startGame() {
      LifePanel lifePanel = new LifePanel();
      lifePanels.add(lifePanel);
      add(lifePanel);
      
      makeThreads();
      getTimeThread().start();
      getRainThread().start();
      System.out.println((threadCount + 1) + "번째 시도 시작.(threadMake)");
   }

   private synchronized void changePosition() {
      for (int i = 0; i < textArr.size(); i++) {
         TextObject textTmp = (TextObject) textArr.get(i);
         int y = textTmp.getY();

         // ****************************Life 감소 ***********************************//
         if (y > 500) {
            // 못친 상황.
            textArr.remove(i);
            textTmp.setText("");
            GamePanel.this.repaint();

            LifeIcon icon = (LifeIcon) lifeIcons.get(life - 1);
            icon.setText("");
            lifeIcons.remove(icon);

            System.out.println("life 감소.");
            life--;
         }
         textTmp.setY(y + speed);
      }
   }

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
   
   public GamePanel() {}
   public GamePanel(ScorePanel scorePanel, EditPanel editPanel) {
      this.scorePanel = scorePanel;
      this.editPanel = editPanel;
    
      setLayout(null);

      input.setBounds(216, 481, 116, 21);
      add(input);
      input.setColumns(10);
      timeLabel.setFont(new Font("Eras Bold ITC", Font.PLAIN, 15));
      timeLabel.setSize(127, 24);
      timeLabel.setLocation(0, 0);
      add(timeLabel);

      /*
       * 단어별 기능 검정색단어 : 일반 단어 – 점수 +10 ~ 1 = nomal 파란색단어 : 모든 단어를 5초간 정지시키는 기능을 가짐 –
       * 점수 +10 ~ 2 = ability 1 초록색단어 : 모든 단어를 삭제시킨다. - 점수 +50 ~ 2 = ability 2 빨간색단어 :
       * 단어가 떨어지는 속도가 5초간 빨라진다. - 점수 +10 ~ 4 = ability 3
       */
      input.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JTextField t = (JTextField) e.getSource();
            String inWord = t.getText();
            for (int i = 0; i < textArr.size(); i++) {
               TextObject textTmp = (TextObject) textArr.get(i);
               if (textTmp.getText().equals(inWord)) {
                  switch (textTmp.getAbility()) {
                  case 0: // Black
                     scorePanel.increase(10);
                     //scorePanel.paintImmediately(scorePanel.getVisibleRect());
                     textTmp.setText("");
                     textArr.remove(i); // 지우기
                     repaint();
                     t.setText("");
                     // System.out.println("black ability");
                     break;

                  case 1: // Blue
                     threadStateFlag = 200; // 5초간 stop
                     scorePanel.increase(20);
                     textTmp.setText("");
                     textArr.remove(i); // 지우기
                     repaint();
                     t.setText("");
                     // System.out.println("blue ability");
                     break;

                  case 2: // Pink
                     scorePanel.increase(50);
                     textTmp.setText("");
                     // 모든 내용 지우기.
                     for (int j = 0; j < textArr.size(); j++) {
                        TextObject tmp = (TextObject) textArr.get(j);
                        tmp.setText("");
                     }
                     t.setText("");
                     textArr.removeAllElements();
                     repaint();
                     // System.out.println("Green ability");
                     break;

                  case 3: // RED
                     threadStateFlag = -10; // 5초간 빨라짐.
                     scorePanel.increase(10);
                     textTmp.setText("");
                     textArr.remove(i); // 지우기
                     repaint();
                     t.setText("");
                     // System.out.println("Red ability");
                     break;
                  }
                  // 능력 별로 지우기 .

               } else {
                  t.setText("");
               }
            }
         }
      });
   }

}