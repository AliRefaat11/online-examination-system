
package onlineexamination;

import java.io.Serializable;

public abstract class Person implements Serializable {

    protected String name;
    protected int id;
    protected String username;
    protected String password;

    public Person(String name, int id, String username, String password) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public abstract void display();

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void welcome() {
        System.out.println("Welcome, " + name + "!");
    }
}