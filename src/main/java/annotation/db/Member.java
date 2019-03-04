package annotation.db;

@DBTable
public class Member {
    @SQLString(30) String firstName;
    @SQLString(50) String lastName;
    @SQLInteger Integer age;
    @SQLString(value = 30,constrains = @Constraints(primaryKey = true)) String hanlde;
    static int memberCount;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getHanlde() {
        return hanlde;
    }

    @Override
    public String toString() {
        return hanlde;
    }
}
