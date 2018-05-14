package olga.maslova.outerspacemanager.responsesRetroFit;

/**
 * Created by omaslova on 16/01/2018.
 */

public class authResponse {
    public String getToken() {
        return token;
    }

    private String token;
    private String expires;

        public authResponse (String pToken, String pExpires) {
            this.token = pToken;
            this.expires = pExpires;
        }


}
