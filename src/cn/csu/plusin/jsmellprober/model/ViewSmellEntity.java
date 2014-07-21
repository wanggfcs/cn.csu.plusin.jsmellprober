package cn.csu.plusin.jsmellprober.model;

import java.util.Date;

public class ViewSmellEntity {  
    private Long id; //惟一识别码，在数据库里常为自动递增的ID列  
    private String className; 
    private String smellName;
    private int start,end;  
    private String location;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSmellName() {
		return smellName;
	}
	public void setSmellName(String smellName) {
		this.smellName = smellName;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	} 

  
}  