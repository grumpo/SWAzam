package at.ac.tuwien.swa.SWAzam.Server.Common;

public class UserInformation {
    private String username;
    private Integer credits;

    public UserInformation() {
    }

    public UserInformation(String username, Integer credits) {
        this.username = username;
        this.credits = credits;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInformation that = (UserInformation) o;

        if (credits != null ? !credits.equals(that.credits) : that.credits != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (credits != null ? credits.hashCode() : 0);
        return result;
    }
}
