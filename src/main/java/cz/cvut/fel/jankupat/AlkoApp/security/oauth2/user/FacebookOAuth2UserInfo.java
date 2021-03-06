package cz.cvut.fel.jankupat.AlkoApp.security.oauth2.user;

import java.util.Map;

/**
 * The type Facebook o auth 2 user info.
 */
public class FacebookOAuth2UserInfo extends OAuth2UserInfo {
    /**
     * Instantiates a new Facebook o auth 2 user info.
     *
     * @param attributes the attributes
     */
    FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        if(attributes.containsKey("picture")) {
            Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
            if(pictureObj.containsKey("data")) {
                Map<String, Object>  dataObj = (Map<String, Object>) pictureObj.get("data");
                if(dataObj.containsKey("url")) {
                    return (String) dataObj.get("url");
                }
            }
        }
        return null;
    }
}
