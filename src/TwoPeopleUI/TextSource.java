package TwoPeopleUI;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class TextSource {
	private Vector<String> v = new Vector<String>();

	private void readFile() {
		FileInputStream fstream;
		try {
			fstream = new FileInputStream("resource/word/koreanWord.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int i = 0;
			while ((strLine = br.readLine()) != null) {
				v.add(strLine);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 파일에서 읽기.
	public TextSource() {
		readFile();
	}

	// Vector에서 랜덤한 문자열을 리턴해주는 함수.
	public String get() {
		int index = (int) (Math.random() * v.size());
		return v.get(index);
	}
}
