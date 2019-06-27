package spring.task.entities;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LICENSE_PLATE")
    private String licensePlate;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "MANUFACTURE_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime manufactureDate;

    @Column(name = "AGE")
    private int age;

    @Version
    private int version;

    @Override
    public String toString() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return String.format("{License:%s,Manufacture:%s" +
                ",Manufacture date:%s,Age:%d}",licensePlate,manufacturer,
                sdf.format(manufactureDate.toDate()),age);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public DateTime getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(DateTime manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
