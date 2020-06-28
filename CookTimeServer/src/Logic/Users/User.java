package Logic.Users;

public class User extends AbstractUser {
    public int age;
    public boolean isChef = false;
    public int rating;


    public User(String email, String pass) {
        this.name = "Test User";
        this.email = email;
        this.password = pass;
        this.age = 20;


    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void makeChef() {
        this.isChef = true;
    }
}
