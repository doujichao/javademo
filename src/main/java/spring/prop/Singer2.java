package spring.prop;

import spring.prop.diy.CheckCountrySinger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@CheckCountrySinger
public class Singer2 {

    @NotNull
    @Size(min = 2,max = 60)
    private String firstName;
    private String lastName;

    @NotNull
    private Genre genre;

    private Gender gender;

    public boolean isCountrySinger(){
        return genre==Genre.COUNTRY;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
