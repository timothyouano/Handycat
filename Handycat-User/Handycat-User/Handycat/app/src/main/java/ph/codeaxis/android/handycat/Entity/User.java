package ph.codeaxis.android.handycat.Entity;

/**
 * Created by Maria Himaya on 26/09/2017.
 */

public class User {
    public String address;
    public long buy;
    public String course;
    public long donate;
    public long downvote;
    private String email;
    public String firstname;
    public String idNum;
    public String lastname;
    public String contact;
    public long sell;
    public long rent;
    public long trade;
    public long upvote;

    public User(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void countSold(){
        this.sell++;
    }

    public void countBought(){
        this.buy++;
    }

    public void countRent(){
        this.rent++;
    }

    public void countTrade(){
        this.trade++;
    }

    public void countDonate(){
        this.donate++;
    }

    public void countUpvote(){
        this.upvote++;
    }

    public void countDownvote(){
        this.downvote++;
    }


    public long getSell() {
        return sell;
    }

    public long getBuy() {
        return buy;
    }

    public long getRent() {
        return rent;
    }

    public long getDonate() {
        return donate;
    }

    public long getTrade() {
        return trade;
    }

    public long getUpvote() {
        return upvote;
    }

    public long getDownvote() {
        return downvote;
    }



}