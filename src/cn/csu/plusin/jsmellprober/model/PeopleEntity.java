package cn.csu.plusin.jsmellprober.model;

import java.util.Date;

public class PeopleEntity {  
    private Long id; //惟一识别码，在数据库里常为自动递增的ID列  
    private String name; //姓名  
    private boolean sex; //性别 true男，flase女  
    private int age; //年龄  
    private Date createDate; //记录的建立日期。Date类型是java.util.Date，而不是java.sql.Date  
      
    //以下代码为字段各自的Setter/Geter方法。参考第3.5.2节，这些方法在Eclipse可自动生成。  
    public Long getId() { return id;}  
    public void setId(Long long1) {id = long1;}  
    public String getName() {return name;}  
    public void setName(String string) {name = string;}  
    public boolean isSex() { return sex;}  
    public void setSex(boolean sex) { this.sex = sex; }  
    public int getAge() {return age;}  
    public void setAge(int i) {age = i;}  
    public Date getCreateDate() {return createDate;}  
    public void setCreateDate(Date date) {createDate = date;}  
}  