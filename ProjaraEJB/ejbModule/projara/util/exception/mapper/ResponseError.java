package projara.util.exception.mapper;

import java.io.Serializable;

public class ResponseError implements Serializable {

	private String name;
	private String message;
	private int status;
	private Object errorObj;
	
	public ResponseError() {
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getErrorObj() {
		return errorObj;
	}

	public void setErrorObj(Object errorObj) {
		this.errorObj = errorObj;
	}

	public ResponseError(String message, int status, Object errorObj) {
		super();
		this.message = message;
		this.status = status;
		this.errorObj = errorObj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
