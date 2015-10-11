package com.barganha.model.data;

import com.google.common.base.MoreObjects;

public class ChangePasswordData {

	private Long userId;
	private String resetPasswordToken;
	private String currentPassword;
	private String password;
	private String confirmPassword;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("resetPasswordToken", resetPasswordToken)
                .add("currentPassword", (currentPassword != null ? "NOT_EMPTY" : "EMPTY"))
                .add("password", (password != null ? "NOT_EMPTY" : "EMPTY"))
                .add("confirmPassword", (confirmPassword != null ? "NOT_EMPTY" : "EMPTY"))
                .toString();
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
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

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
}
