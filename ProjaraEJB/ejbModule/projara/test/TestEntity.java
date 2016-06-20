package projara.test;

import java.io.Serializable;

import javax.ws.rs.FormParam;

public class TestEntity implements Serializable {

	@FormParam("name")
	private String name;
	
	@FormParam("file")
	private byte[] file;
	
	public TestEntity() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
	
	

}
