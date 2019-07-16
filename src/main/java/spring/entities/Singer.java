package spring.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Singer")
@NamedQueries({
        @NamedQuery(name = Singer.FIND_ALL_WITH_ALBUM,
                query = "select distinct s from Singer s " +
                        "left join fetch s.albums a " +
                        "left join fetch s.instruments i"),
        @NamedQuery(name = Singer.FIND_SINGER_BY_ID,query = "" +
                "select distinct s from Singer s " +
                "left join fetch s.albums a " +
                "left join fetch s.instruments i " +
                "where s.id=:id"),
        @NamedQuery(name = Singer.FIND_ALL,query = "select s from Singer s")
})
@SqlResultSetMapping(name = "singerResult",entities = @EntityResult(entityClass = Singer.class))
public class Singer implements Serializable {

    public static final String FIND_ALL="Singer.findAll";
    public static final String FIND_SINGER_BY_ID="Singer.findById";
    public static final String FIND_ALL_WITH_ALBUM="Singer.findAllWithAlbum";

    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private int version;

    private Set<Album> albums=new HashSet<>();


    private Set<Instrument> instruments=new HashSet<>();

    @ManyToMany
    @JoinTable(name = "singer_instrument",
            joinColumns = @JoinColumn(name = "SINGER_ID"),
            inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID"))
    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    @OneToMany(mappedBy = "singer",cascade = CascadeType.ALL,orphanRemoval = true)
    public Set<Album> getAlbums() {
        return albums;
    }

    public boolean addAlbum(Album album){
        album.setSinger(this);
        return albums.add(album);
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Singer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", version=" + version +
                '}';
    }

    public void removerAlbum(Album album1) {
        albums.remove(album1);
    }
}
