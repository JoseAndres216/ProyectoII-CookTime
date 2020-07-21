package logic.users;

import logic.structures.simplelist.SimpleList;

public class Enterprise extends AbstractUser {
    private String contactInfo;
    private String schedule;
    private SimpleList<AbstractUser> members;

    public Enterprise(String email, String pass) {
        this.email = email;
        this.password = pass;
    }

    public void addMember(AbstractUser member) {
        this.members.append(member);
    }

    public int getRating() {
        return this.rating;
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
}
