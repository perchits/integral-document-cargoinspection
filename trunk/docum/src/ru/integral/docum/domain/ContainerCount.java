package ru.integral.docum.domain;

import java.io.Serializable;
import java.util.Random;

public class ContainerCount implements Serializable{
	private static final long serialVersionUID = 3021774242493702063L;
	private Integer total;
	private Integer checked;
	private Integer unchecked;
	private Integer processed;
	public ContainerCount(){			
		Random generator = new Random();		
		total = generator.nextInt(100) + 10;		
		checked = total - generator.nextInt(total);
		unchecked =  total - checked;		
		processed = total - generator.nextInt(checked);
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getTotal() {
		return total;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	public Integer getChecked() {
		return checked;
	}
	public void setUnchecked(Integer unchecked) {
		this.unchecked = unchecked;
	}
	public Integer getUnchecked() {
		return unchecked;
	}
	public void setProcessed(Integer processed) {
		this.processed = processed;
	}
	public Integer getProcessed() {
		return processed;
	}
	

}
