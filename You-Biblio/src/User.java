public class User {
    private String name;
    private String memberNumber;

    public User(String name, String memberNumber) {
        this.name = name;
        this.memberNumber = memberNumber;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMemberNumber(){
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber){
        this.memberNumber = memberNumber;
    }
}
