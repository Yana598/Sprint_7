package praktikum.order;

public class Order {
    private String firstName;
    private String lastName;
    private String metroStation;
    private String[] color;
    private String address;
    private String phone;
    private int rentTime;
    private String comment;
    private String deliveryDate;
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

   public Order(String firstName, String lastName, String address,String metroStation,
    String phone,int rentTime,String deliveryDate, String comment,String[] color){
       this.firstName = firstName;
       this.lastName = lastName;
       this.metroStation = metroStation;
       this.color = color;
       this.address = address;
       this.phone = phone;
       this.rentTime = rentTime;
       this.comment = comment;
       this.deliveryDate=deliveryDate;
   }


}
