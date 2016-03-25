package com.zhenglee.langfangfavor.android.modules.user.dao;

import com.j256.ormlite.field.DatabaseField;
import com.zhenglee.framework.persistence.PersistentObject;
import com.zhenglee.langfangfavor.android.modules.persistence.FavorStore;

import cn.bmob.v3.BmobUser;

/**
 * Created by zhenglee on 15/12/25.
 */
public class LocalUser extends BmobUser implements PersistentObject {

    @DatabaseField(columnName = FavorStore.Users.UserColumns._ID, canBeNull = false, id = true)
    private String id;

    @DatabaseField(columnName = FavorStore.Users.UserColumns.PHONE, canBeNull = false)
    private String phone;

    @DatabaseField(columnName = FavorStore.Users.UserColumns.USERNAME, canBeNull = false)
    private String username;

    @DatabaseField(columnName = FavorStore.Users.UserColumns.EMAIL)
    private String email;

    @DatabaseField(columnName = FavorStore.Users.UserColumns.NICKNAME)
    private String nickname;

    @DatabaseField(columnName = FavorStore.Users.UserColumns.AVATAR)
    private String avatar;

    @DatabaseField(columnName = FavorStore.Users.UserColumns.SEX)
    private int sex;

    @DatabaseField(columnName = FavorStore.Users.UserColumns.HEIGHT)
    private int height;

    @DatabaseField(columnName = FavorStore.Users.UserColumns.AREA)
    private String area;

    @DatabaseField(columnName = FavorStore.Users.UserColumns.COMMUNITY)
    private String community;

    @DatabaseField(columnName = FavorStore.Users.UserColumns.SLOGAN)
    private String slogan;

    private transient String password;

    public LocalUser() {
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
