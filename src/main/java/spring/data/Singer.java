package spring.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Singer implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private List<Album> albums;

    public boolean addAlum(Album album) {
        if (albums == null) {
            albums = new ArrayList<>();
            albums.add(album);
            return true;
        } else {
            if (albums.contains(album)) {
                return false;
            }
        }
        albums.add(album);
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return String.format("{First name:%s,Last name:%s,Birthday:%s}",
                firstName,lastName,sdf.format(birthDate));
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
