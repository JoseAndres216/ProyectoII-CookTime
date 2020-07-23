package logic.users;

import logic.ServerSettings;
import logic.structures.simplelist.SimpleList;

import java.util.Objects;

public class Enterprise extends AbstractUser {
    private String contactInfo;
    private String schedule;
    private SimpleList<AbstractUser> members;

    public Enterprise(String email, String pass) {
        this.setEmail(email);
        this.setPassword(pass);
    }

    public void addMember(AbstractUser member) {
        this.members.append(member);
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
                "email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enterprise that = (Enterprise) o;
        return Objects.equals(contactInfo, that.contactInfo) &&
                Objects.equals(schedule, that.schedule) &&
                Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contactInfo, schedule, members);
    }

    public String getMembers() {
        return ServerSettings.GSON_INSTANCE.toJson(this.members, ServerSettings.USER_LIST_TYPE);
    }
}
