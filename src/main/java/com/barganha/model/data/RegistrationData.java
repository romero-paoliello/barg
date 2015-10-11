package com.barganha.model.data;

import com.google.common.base.MoreObjects;

public class RegistrationData {

	private String name;
	private String email;
	private String password;
	private String confirmPassword;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("email", email)
                .add("password", (password != null ? "NOT_EMPTY" : "EMPTY"))
                .add("confirmPassword", (confirmPassword != null ? "NOT_EMPTY" : "EMPTY"))
                .toString();
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
