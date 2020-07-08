package Logic.Users;

public class User extends AbstractUser {
    private int age;

    private int rating;

    public User(String email, String pass) {
        this.name = "Test User";
        this.email = email;
        this.password = pass;
        this.setAge(20);


    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
