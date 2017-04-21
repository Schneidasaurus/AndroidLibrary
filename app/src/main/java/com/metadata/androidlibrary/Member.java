package com.metadata.androidlibrary;

/**
 * Created by plee on 4/12/2017.
 */

public class Member {
    private String name;
    private String id;
    private String password;
    private String privilege;

    public Member(String name, String id, String password, String privilege){
        this.name = name;
        this.id = id;
        this.password = password;
        this.privilege = privilege;
    }

    public void setName(String name){this.name = name;}
    public void setID(String id){this.id = id;}
    public void setPassword(String password){this.password = password;}
    public void setPrivilege(String privilege){this.privilege = privilege;}

    public String getName(){return name;}
    public String getID(){return id;}
    public String getPassword(){return password;}
    public String getPrivilege(){return privilege;}

}
