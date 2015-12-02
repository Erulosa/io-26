package mainJava;

import java.io.Serializable;

public class Task implements Serializable {
	private String subject;
	private Integer deadline;
	private Integer count;

	public Task(String subject, Integer deadline,Integer count) {
		this.subject = subject;
		this.deadline = deadline;
		this.count = count;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String Subject) {
		this.subject = subject;
	}
	public Integer getDeadline() {
		return deadline;
	}
	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
