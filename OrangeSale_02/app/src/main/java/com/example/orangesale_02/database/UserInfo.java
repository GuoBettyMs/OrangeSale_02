package com.example.orangesale_02.database;

public class UserInfo {
    private int id;
    private String username;
    private String password;
    private String sex;
    private String city;

    //构造方法
    public UserInfo(int id, String username, String password, String sex, String city){
        this.id = id;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.city = city;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setUsername( String username){
        this.username = username;
    }

    public void setPaswd( String password){
        this.password = password;
    }

    public void setSex( String sex){
        this.sex = sex;
    }

    public void setCity( String city){
        this.city = city;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getSex(){
        return sex;
    }

    public String getCity(){
        return city;
    }

    @Override
    public String toString(){
        return "userInfo{ "+" id="+id+", username="+username+", paswd="+password
                +", sex="+sex+", city="+city+"}";
    }
}
