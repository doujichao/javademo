package spring.hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "instrument")
public class Instrument implements Serializable {


    private String instrumentId;

    private Set<Singer> singers=new HashSet<>();

    @ManyToMany
    @JoinTable(name = "singer_instrument",
    joinColumns = @JoinColumn(name = "INSTRUMENT_ID"),
    inverseJoinColumns = @JoinColumn(name = "SINGER_ID"))
    public Set<Singer> getSingers() {
        return singers;
    }

    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }

    @Id
    @Column(name = "Instrument_id")
    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "instrumentId='" + instrumentId + '\'' +
                '}';
    }
}
