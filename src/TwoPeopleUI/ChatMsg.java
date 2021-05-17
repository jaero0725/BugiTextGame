package TwoPeopleUI;


import java.io.Serializable;

import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String code; 
	private String data;
	public ImageIcon img;

	public ChatMsg(String id, String code, String msg) {
		this.id = id;
		this.code = code;
		this.data = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}
}