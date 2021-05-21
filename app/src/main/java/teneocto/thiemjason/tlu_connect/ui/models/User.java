package teneocto.thiemjason.tlu_connect.ui.models;

import android.graphics.Bitmap;

public class User {
    private String firstName;
    private String lastName;
    private String bitmap;
    private String location;
    private String email;

    public User(String firstName, String lastName, String bitmap, String location, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bitmap = bitmap;
        this.location = location;
        this.email = email;
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

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
