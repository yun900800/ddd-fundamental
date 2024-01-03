package org.ddd.fundamental.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_model")
public class UserModel implements Cloneable{

    @Id
    private String id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    public UserModel(){

    }

    public UserModel(String userName,String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public UserModel clone() {
        UserModel userModel;
        try {
            userModel = (UserModel) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
        return userModel;
    }
}
