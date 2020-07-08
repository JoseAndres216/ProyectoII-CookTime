package Logic.Users;

public class Enterprise extends AbstractUser {
    private int rating;
    private String contactInfo;
    private String schedule;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

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
