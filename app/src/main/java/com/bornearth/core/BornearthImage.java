/*
 * Copyright - All Rights Reserved
 */
package com.bornearth.core;

/**
 * @author Marcellin RWEGO
 * @since 1.0.0 [19/02/2017]
 */
public class BornearthImage {
    private long id;
    private String nomImgMin = "";
    private String dateImage = "";
    private String descImage = "";
    private String nomImgReel;
    private long cleEtrangereUser;

    public BornearthImage() {}

    public BornearthImage(long cleEtrangereUser, String dateImage, String descImage, String nomImgMin, String nomImgReel) {
        super();
        this.dateImage = dateImage;
        this.descImage = descImage;
        this.nomImgMin = nomImgMin;
        this.nomImgReel = nomImgReel;
        this.cleEtrangereUser = cleEtrangereUser;
    }

    public long getCleEtrangereUser() {
        return cleEtrangereUser;
    }

    public String getDateImage() {
        return dateImage;
    }

    public String getDescImage() {
        return descImage;
    }

    public long getId() {
        return id;
    }

    public String getNomImgMin() {
        return nomImgMin;
    }

    public String getNomImgReel() {
        return nomImgReel;
    }

    public void setCleEtrangereUser(long cleEtrangereUser) {
        this.cleEtrangereUser = cleEtrangereUser;
    }

    public void setDateImage(String dateImage) {
        this.dateImage = dateImage;
    }

    public void setDescImage(String descImage) {
        this.descImage = descImage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNomImgMin(String nomImgMin) {
        this.nomImgMin = nomImgMin;
    }

    public void setNomImgReel(String nomImgReel) {
        this.nomImgReel = nomImgReel;
    }

    @Override
    public String toString() {
        return "BornearthImage { " + "dateImage : " + dateImage +
                ", nomImgReel : " + nomImgReel + "}";
    }
}
