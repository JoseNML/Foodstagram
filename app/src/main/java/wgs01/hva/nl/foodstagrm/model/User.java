package wgs01.hva.nl.foodstagrm.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private   String id; 
    private   String username;
    private   String fullName;
    private   String profilePicture;

    public User(){}
    public User(String pId, String pUsername, String pFullName) {
        this.setId(pId);
        this.setUsername(pUsername);
        this.setFullName(pFullName);
    }

    public User(String pId, String pUsername, String pFullName, String pProfilePictureUrl) {
        this.setId(pId);
        this.setUsername(pUsername);
        this.setFullName(pFullName);
        this.setProfilePicture(pProfilePictureUrl);
    }


    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
