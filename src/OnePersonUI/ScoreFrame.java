package OnePersonUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import OnePersonUI.InformationFrame.TextPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Color;

class Score {
	private String id;
	private int score;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Score(String id, int score) {
		super();
		this.id = id;
		this.score = score;
	}

}

public class ScoreFrame extends JFrame {

	private File file = new File("resource/record/record.txt");
	private Vector<Score> scores = new Vector<>();

	private void recordScore() {
		try {
			// 입력 스트림 생성
			FileReader filereader = new FileReader(file);

			// 입력 버퍼 생성
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";

			// 한줄씩 입력
			while ((line = bufReader.readLine()) != null) {
				String arr[] = line.split("/");
				String id = arr[0];
				String score = arr[1];

				// 일단 모든 score들 저장한다.
				Score scoretmp = new Score(id, Integer.parseInt(score));
				scores.add(scoretmp);
			}

			// 점수 높은걸로 바꿔치기.
			System.out.println("------점수 로직 ------");
			for (int i = 0; i < scores.size(); i++) {
				Score scoretmp = scores.get(i); // 두번 바뀌는 경우를 고려 x
				String id = scoretmp.getId();
				int score = scoretmp.getScore();
				for (int j = i + 1; j < scores.size(); j++) {
					Score scoretmpRef = scores.get(j);
					String idRef = scoretmpRef.getId();
					int scoreRef = scoretmpRef.getScore();

					if (id.equals(idRef)) {
						// 같으면, 점수비교
						if (score >= scoreRef) {
							// 기존 점수가 높은경우 그냥 넘김.
							scores.remove(scoretmpRef); // 같은 경우도 지워버린다.
						} else {
							// 기존점수가 더 낮은 경우. 바꿔준다.
							score = scoreRef;
							scoretmp.setScore(score);
							scores.remove(scoretmpRef); // 지워버린다.
							i--;
						}
					}
				}
			}
			for (int i = 0; i < scores.size(); i++) {
				System.out.println(i + "\t " + scores.get(i).getId() + "\t\t " + scores.get(i).getScore());
			}
		} catch (Exception e) {
			System.out.println("파일 오픈 오류");
		}
	}

	public ScoreFrame() {
		recordScore();// 점수기록하기.

		setForeground(Color.CYAN);
		getContentPane().setForeground(Color.CYAN);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);
		setTitle("점수 기록");
		setSize(290, 448);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("\uC544\uC774\uB514");
		lblNewLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
		lblNewLabel.setBounds(41, 10, 98, 28);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\uC810\uC218");
		lblNewLabel_1.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
		lblNewLabel_1.setBounds(151, 10, 98, 28);
		getContentPane().add(lblNewLabel_1);

		System.out.println("-------------------점수출력-------------------");

		// 정렬 필요.
		JLabel[] tmp = new JLabel[scores.size()];
		String[] tmpId = new String[scores.size()];
		int[] tmpScore = new int[scores.size()];
		
		// 배열에 저장
		for (int i = 0; i < scores.size() - 1; i++) {
			Score scoretmp = scores.get(i);
			tmpId[i]  = scoretmp.getId();
			tmpScore[i] = scoretmp.getScore();
		}
		
		for (int i = 0; i < scores.size() - 1; i++) {
			int indexMax = i; //점수
			// 최솟값 검색
			for (int j = i + 1; j < scores.size(); j++) {
				if (tmpScore[j] > tmpScore[i]) {
					indexMax = j;
				}
			}
			//swap 
			int tmpInt = tmpScore[indexMax];
			tmpScore[indexMax] = tmpScore[i];
			tmpScore[i] = tmpInt;
			
			String tmpString = tmpId[indexMax];
			tmpId[indexMax] = tmpId[i];
			tmpId[i] = tmpString;
		}
		
		for (int i = 0; i < scores.size(); i++) {
			String id = tmpId[i];
			int score = tmpScore[i];

			JLabel idLabel = new JLabel(id);
			idLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
			idLabel.setBounds(41, 10 + ((i + 1) * 27), 98, 28); // x좌표 그대로 ;
			getContentPane().add(idLabel);

			JLabel scoreLabel = new JLabel(Integer.toString(score));
			scoreLabel.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
			scoreLabel.setBounds(151, 10 + ((i + 1) * 27), 98, 28);
			getContentPane().add(scoreLabel);

			System.out.println("아이디:" + id + "\t점수 :" + score);
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 가운데 출력시키기
		// setResizable(false); // 함부로 크기를 변경하지 않게.
		setVisible(true);
	}
}
