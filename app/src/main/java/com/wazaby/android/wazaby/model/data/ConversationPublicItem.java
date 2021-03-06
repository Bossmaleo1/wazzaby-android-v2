package com.wazaby.android.wazaby.model.data;

import android.content.Context;

/**
 * Created by bossmaleo on 09/11/17.
 */

public class ConversationPublicItem {

    private String nameMembreProb;
    private String Datetime;
    private String ImageID;
    private String Contenu;
    private int IconComment;
    private String commentnumber;
    private int ID_EME;
    private int ID;
    private Context context;
    private String etat_photo_status;
    private String status_photo;

    public ConversationPublicItem(Context context,int ID_EME,int ID,String Contenu,String nameMembreProb,String datetime,String commentnumber
            ,String ImageID,int IconComment,String etat_photo_status,String status_photo)
    {
        this.Contenu = Contenu;
        this.nameMembreProb = nameMembreProb;
        this.Datetime = datetime;
        this.commentnumber = commentnumber;
        this.ImageID = ImageID;
        this.IconComment = IconComment;
        this.ID = ID;
        this.ID_EME = ID_EME;
        this.context = context;
        this.etat_photo_status = etat_photo_status;
        this.status_photo = status_photo;
    }

    public Context getContext1() {
        return context;
    }

    public void setContext1(Context context) {
        this.context = context;
    }

    public String getCommentnumber() {
        return commentnumber;
    }

    public void setCommentnumber(String commentnumber) {
        this.commentnumber = commentnumber;
    }

    public void setContenu(String contenu) {
        Contenu = contenu;
    }

    public String getContenu() {
        return Contenu;
    }

    public void setImageID(String imageID) {
        ImageID = imageID;
    }

    public String getImageID() {
        return ImageID;
    }

    public int getIconComment() {
        return IconComment;
    }

    public String getDatetime() {
        return Datetime;
    }

    public String getNameMembreProb() {
        return nameMembreProb;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    public void setIconComment(int iconComment) {
        IconComment = iconComment;
    }

    public void setNameMembreProb(String nameMembreProb) {
        this.nameMembreProb = nameMembreProb;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_EME() {
        return ID_EME;
    }

    public int getID() {
        return ID;
    }

    public void setID_EME(int ID_EME) {
        this.ID_EME = ID_EME;
    }

    public String getEtat_photo_status() {
        return etat_photo_status;
    }

    public String getStatus_photo() {
        return status_photo;
    }


    public void setEtat_photo_status(String etat_photo_status) {
        this.etat_photo_status = etat_photo_status;
    }

    public void setStatus_photo(String status_photo) {
        this.status_photo = status_photo;
    }
}
