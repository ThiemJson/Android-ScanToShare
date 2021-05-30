package teneocto.thiemjason.tlu_connect.models;

public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private String company;
    private String imageBase64;

    public UserDTO() {
        this.firstName = "Your name";
        this.lastName = "Your last name";
        this.company = "Your company";
        this.position = "Your position";
        this.email = " ";
    }

    public UserDTO(int id, String firstName, String lastName, String email, String position, String company, String imageBase64) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
        this.company = company;
        this.imageBase64 = imageBase64;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
