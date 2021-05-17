package OnePersonUI;

import java.awt.Font;

import javax.swing.JLabel;

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
	   public int getX() { return x;}
	   public void setX(int x) {this.x = x;}
	   public int getY() {return y;}
	   public void setY(int y) {this.y = y;}
	   public String getName() { return name;
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
