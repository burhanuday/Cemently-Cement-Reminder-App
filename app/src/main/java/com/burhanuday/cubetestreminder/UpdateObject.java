package com.burhanuday.cubetestreminder;

public class UpdateObject {
    int app_version;
    String changelog;

    public UpdateObject(){

    }

    public UpdateObject(int app_version, String changelog){
        this.app_version = app_version;
        this.changelog = changelog;
    }

    public void setApp_version(int app_version) {
        this.app_version = app_version;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public int getApp_version() {
        return app_version;
    }

    public String getChangelog() {
        return changelog;
    }
}
