package logic.users;

public class Enterprise extends AbstractUser {
    private int rating;
    private String contactInfo;
    private String schedule;


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

    @Override
    public int compareTo(AbstractUser user) {
        return super.compareTo(user);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Enterprise(String email, String pass) {
        this.email = email;
        this.password = pass;
    }
}
