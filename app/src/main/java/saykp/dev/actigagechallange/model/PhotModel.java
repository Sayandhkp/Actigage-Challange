package saykp.dev.actigagechallange.model;

/**
 * Created by SAYANDH KP (sayandhkp59@gmail.com) on 15-03-2018.
 */

public class PhotModel {

    String photoId,photoSecret,photoServer,photoFarm,photopTitle;

    public PhotModel(){}

    public String getPhotoFarm() {
        return photoFarm;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getPhotopTitle() {
        return photopTitle;
    }

    public String getPhotoSecret() {
        return photoSecret;
    }

    public String getPhotoServer() {
        return photoServer;
    }

    public void setPhotoFarm(String photoFarm) {
        this.photoFarm = photoFarm;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public void setPhotopTitle(String photopTitle) {
        this.photopTitle = photopTitle;
    }

    public void setPhotoSecret(String photoSecret) {
        this.photoSecret = photoSecret;
    }

    public void setPhotoServer(String photoServer) {
        this.photoServer = photoServer;
    }
}
