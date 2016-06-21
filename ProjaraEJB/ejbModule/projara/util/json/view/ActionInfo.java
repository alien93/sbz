package projara.util.json.view;

import java.io.Serializable;
import java.util.Date;

public class ActionInfo implements Serializable {

	private int id;
	private String name;
	private Date from;
	private Date until;
	private double dicount;
	
	public ActionInfo() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getUntil() {
		return until;
	}

	public void setUntil(Date until) {
		this.until = until;
	}

	public double getDicount() {
		return dicount;
	}

	public void setDicount(double dicount) {
		this.dicount = dicount;
	}

	public ActionInfo(int id, String name, Date from, Date until, double dicount) {
		super();
		this.id = id;
		this.name = name;
		this.from = from;
		this.until = until;
		this.dicount = dicount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ActionInfo)) {
			return false;
		}
		ActionInfo other = (ActionInfo) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	
	

}
