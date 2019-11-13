package wgs01.hva.nl.foodstagrm.network.instagramObject;

public class Data {

    private String id;
    private Images images;
    private User user;
    private Caption caption;

    public String getId() {
        return id;
    }
    public Images getImages() {
        return images;
    }

    public User getUser() {
        return user;
    }

    public Caption getCaption() {
        return caption;
    }

    public class User {

        private String profile_picture;
        private String username;
        private String full_name;
        private String id;

        public String getProfile_picture() {
            return profile_picture;
        }

        public String getUsername() {
            return username;
        }
        public String getFull_name() {
            return full_name;
        }
        public String getId() {
            return id;
        }
    }

    public class Caption {

        private String text;
        private long created_time;

        public String getText() {
            return text;
        }

        public long getCreated_time() {
            return created_time;
        }
    }

    public class Images {
        private Standard_resolution standard_resolution;

        public Standard_resolution getStandard_resolution() {
            return standard_resolution;
        }


        public class Standard_resolution {

            private String url;

            public String getUrl() {
                return url;
            }
        }
    }
}
