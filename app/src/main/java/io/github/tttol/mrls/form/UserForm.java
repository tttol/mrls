package io.github.tttol.mrls.form;

public record UserForm(int id, String username, String name, String state, String avatarUrl, String webUrl) {
    public static UserForm empty() {
        return new UserForm(0, null, null, null, null, null);
    }
}
