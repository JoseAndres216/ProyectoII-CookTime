package Logic.Users;

public class Enterprise extends AbstractUser {
    private int rating;
    private String contactInfo;
    private String schedule;

    @Override

    public String toString() {
        return "Enterprise{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Enterprise(String email, String pass) {
        this.email = email;
        this.password = pass;
    }
}
