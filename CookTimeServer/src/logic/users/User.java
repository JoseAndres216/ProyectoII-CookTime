package logic.users;

public class User extends AbstractUser {
    private int age;

    public User(String email, String pass) {
        this.setName("Test User");
        this.setEmail(email);
        this.setPassword(pass);
        this.setAge(20);


    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
