package projara.rest.manager;

public class RestMsg {
	
	private String msg;
	private Object value;
	public RestMsg(){}
	public RestMsg(String msg, Object value) {
		super();
		this.msg = msg;
		this.value = value;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
