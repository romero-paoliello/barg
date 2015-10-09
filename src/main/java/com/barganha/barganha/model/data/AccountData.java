package com.barganha.barganha.model.data;

import com.barganha.barganha.model.User;
import com.google.common.base.MoreObjects;

public class AccountData {

	private Long userId;
	private String email;
	private String name;

	public AccountData() {
	}
	
	public AccountData(User user) {
		userId = user.getId();
		email = user.getEmail();
		name = user.getName();
	}

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("email", email)
                .add("name", name)
                .toString();
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
