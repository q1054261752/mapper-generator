package com.dhcc.business.de.api.entity;

import java.io.Serializable;


public class MyUserItem implements Serializable {
	private static final long serialVersionUID = 1L;

  	private String user_name;
  	private String password;
  	private Long _id;

  	public String getUser_name() {
    	return this.user_name;
  	}

  	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
  
  	public String getPassword() {
    	return this.password;
  	}

  	public void setPassword(String password) {
		this.password = password;
	}
  
  	public Long get_id() {
    	return this._id;
  	}

  	public void set_id(Long _id) {
		this._id = _id;
	}
  
  	public MyUserItem() {
  		init();
  	}
  
  	public void init() {
    	user_name = "";
    	password = "";
  		_id = 0L;
	}
}
