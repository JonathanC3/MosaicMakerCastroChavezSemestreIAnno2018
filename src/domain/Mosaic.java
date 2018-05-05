/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author faubricioch
 */
public class Mosaic {
    private String name, pathImage, pathMosaic;
    private long pixels;

    public Mosaic() {
        this.name="";
        this.pixels=0;
    }

    public Mosaic(String name, int pixels) {
        this.name = name;
        this.pixels = pixels;
    }

    public String getName() {
        return this.name;
    }

    public long getPixels() {
        return this.pixels;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPixels(long pixels) {
        this.pixels = pixels;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getPathMosaic() {
        return pathMosaic;
    }

    public void setPathMosaic(String pathMosaic) {
        this.pathMosaic = pathMosaic;
    }
    
    public int sizeInBytes(){
        return this.getName().length()*2 + this.getPathImage().length()*2 + 
                this.getPathMosaic().length()*2 + 8;
    }
}
