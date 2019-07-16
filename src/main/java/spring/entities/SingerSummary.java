package spring.entities;

import java.io.Serializable;

public class SingerSummary implements Serializable {
    private String firstName;
    private String lastName;
    private String latestAlbum;

    public SingerSummary(String firstName, String lastName, String latestAlbum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.latestAlbum = latestAlbum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLatestAlbum() {
        return latestAlbum;
    }

    public void setLatestAlbum(String latestAlbum) {
        this.latestAlbum = latestAlbum;
    }

    @Override
    public String toString() {
        return "SingerSummary{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", latestAlbum='" + latestAlbum + '\'' +
                '}';
    }
}
