package spring.prop;

import org.joda.time.DateTime;

import java.net.URL;
import java.text.SimpleDateFormat;

public class AnotherSinger {
    private String firstName;
    private String lastName;
    private DateTime birthDate;
    private URL personalSite;


    @Override
    public String toString() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return String.format("{First name:%s,Last name:%s,Birthday:%s,Site:%s}",
                firstName,lastName,sdf.format(birthDate.toDate()),personalSite);
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

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public URL getPersonalSite() {
        return personalSite;
    }

    public void setPersonalSite(URL personalSite) {
        this.personalSite = personalSite;
    }

}
