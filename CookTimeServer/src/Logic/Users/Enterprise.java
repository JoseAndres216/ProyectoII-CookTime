package Logic.Users;

public class Enterprise extends AbstractUser {
    private int rating;
    private String contactInfo;
    private String schedule;

    public Enterprise(String email) {
        this.email = email;
    }
}
