package cn.csu.plusin.jsmellprober.model;

import java.util.Date;

public class PeopleEntity {  
    private Long id; //Ωһʶ���룬�����ݿ��ﳣΪ�Զ�������ID��  
    private String name; //����  
    private boolean sex; //�Ա� true�У�flaseŮ  
    private int age; //����  
    private Date createDate; //��¼�Ľ������ڡ�Date������java.util.Date��������java.sql.Date  
      
    //���´���Ϊ�ֶθ��Ե�Setter/Geter�������ο���3.5.2�ڣ���Щ������Eclipse���Զ����ɡ�  
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