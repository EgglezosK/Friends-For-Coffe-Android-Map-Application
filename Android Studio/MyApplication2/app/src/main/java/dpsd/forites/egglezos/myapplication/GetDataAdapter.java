package dpsd.forites.egglezos.myapplication;

/**
 * Created by JUNED on 6/16/2016.
 */
public class GetDataAdapter {

    public String ImageServerUrl;
    public String ImageTitleName;
    public String UserLon;
    public String UserLat;
    public String email;
    public String haslike;

    public String getImageServerUrl() {
        return ImageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.ImageServerUrl = imageServerUrl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHasLike(String haslike) {
        this.haslike = haslike;
    }

    public void setLONuser(String UserLon) {
        this.UserLon = UserLon;
    }

    public void setLATuser(String UserLat) {
        this.UserLat = UserLat;
    }

    public String getImageTitleName() {
        return ImageTitleName;
    }

    public String getHaslike() {
        return haslike;
    }

    public String getUserLon() {
        return UserLon;
    }

    public String getEmail() {
        return email;
    }

    public String getUserlat(){
        return UserLat;
    }

    public void setImageTitleNamee(String Imagetitlename) {
        this.ImageTitleName = Imagetitlename;
    }

}