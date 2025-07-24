/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Asus
 */
public class Slider {
    private int slider_id;
    private String title;
    private String image;
    private String backlink;
    private String status;
    private String notes;

    public Slider() {
    }

    public Slider(int slider_id, String title, String image, String backlink, String status, String notes) {
        this.slider_id = slider_id;
        this.title = title;
        this.image = image;
        this.backlink = backlink;
        this.status = status;
        this.notes = notes;
    }

    // Getters & Setters
    public int getSlider_id() { return slider_id; }
    public void setSlider_id(int slider_id) { this.slider_id = slider_id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getBacklink() { return backlink; }
    public void setBacklink(String backlink) { this.backlink = backlink; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
