package Logic.Users;

public class User extends AbstractUser {
    private int age;
    private boolean isChef = false;
    private int rating;


    public User(String email) {
        this.name = "Test User";
        this.email = email;
        this.age = 20;

    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }


    public void makeChef() {
        this.isChef = true;
    }
}
