package pop3;

import javax.swing.*;

public class User {
    private String name;
    private String password;
    private DefaultListModel<Mail> mails;
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        mails = new DefaultListModel<>();
    }

    public String getName() {
        return name;
    }

    public DefaultListModel<Mail> getMails() {
        return mails;
    }

    @Override
    public String toString() {
        return name;
    }
}
