package projara.rest.manager;

import java.io.Serializable;

public class ThresholdJson implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3891525013899329271L;
	private int id;
	private double from;
	private double to;
	private double percentage;
	
	public ThresholdJson() {}
	public ThresholdJson(int id, double from, double to, double percentage) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.percentage = percentage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getFrom() {
		return from;
	}
	public void setFrom(double from) {
		this.from = from;
	}
	public double getTo() {
		return to;
	}
	public void setTo(double to) {
		this.to = to;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
}
