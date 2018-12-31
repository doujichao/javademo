package reflect;


public class Dog implements DagDao{
    private String name;
    private String age;

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public Dog() {
    }

    public Dog(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public void insert(Shi shi) {
        System.out.println("插入"+shi.getShi());
    }

    @Override
    public void update(Shi shi) {
        System.out.println("更新"+shi.getShi());
    }
}
