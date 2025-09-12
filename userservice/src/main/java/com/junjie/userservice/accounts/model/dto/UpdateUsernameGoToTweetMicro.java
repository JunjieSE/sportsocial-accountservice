package com.junjie.userservice.accounts.model.dto;

public class UpdateUsernameGoToTweetMicro {
    private String newUsername;
    private String oldUsername;

    public UpdateUsernameGoToTweetMicro(String oldUsername, String newUsername) {
        this.newUsername = newUsername;
        this.oldUsername = oldUsername;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getOldUsername() {
        return oldUsername;
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }
}
