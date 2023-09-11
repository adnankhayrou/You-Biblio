import java.util.Date;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String memberNumber;

    public User() {
    }

    public User(int id, String firstName, String lastName, String memberNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberNumber = memberNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public int getId() {
        return id;
    }
}
