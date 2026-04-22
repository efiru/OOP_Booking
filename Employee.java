public class Employee extends Person {
    private String position;

    public Employee(int id, String name, String email, String position) {
        super(id, name, email);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + getName() + "', email='" + getEmail()
                + "', position='" + position + "'}";
    }
}
