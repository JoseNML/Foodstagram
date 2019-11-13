package wgs01.hva.nl.foodstagrm.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * @author José Niemel
 * Het model van een Recipe die opgeslagen zal worden in de database
 */
@IgnoreExtraProperties
public class Recipe {
    private String imageId;
    private String userId;
    private String wishedUserId;
    private String parentImageId;
    private String full_name;
    private String description;
    private String imageLink;
    private String recipeUrl;
    private int replies;
    private long createdTime;
    private int likes;
    private int dislikes;

    public Recipe() {
    }

    public Recipe(String full_name, String description, String imageId, String imageLink, String recipeUrl) {
        this();
        this.setFull_name(full_name);
        this.setDescription(description);
        this.setImageId(imageId);
        this.setImageLink(imageLink);
        this.setRecipeUrl(recipeUrl);
    }

    public Recipe(String full_name, String description, String imageId, String imageLink, String pRecipeUrl, int likes, int dislikes) {
        this(full_name, description,imageId ,imageLink, pRecipeUrl);
        this.setLikes(likes);
        this.setDislikes(dislikes);
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getRecipeUrl(){return recipeUrl;}

    public void setRecipeUrl(String pRecipeUrl){this.recipeUrl = pRecipeUrl;}

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getParentImageId() {
        return parentImageId;
    }

    public void setParentImageId(String pParentImageId) {
        this.parentImageId = pParentImageId;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWishedBy() {
        return wishedUserId;
    }

    public void setWishedBy(String pWishedUser) {
        this.wishedUserId = pWishedUser;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }
}
