package terremoti;

import java.time.LocalDateTime;

public class EQEvent implements Comparable<EQEvent> {
    private long eventID;
    private LocalDateTime time;
    private double latitude;
    private double longitude;
    private double depthKm;
    private String author;
    private String catalog;
    private String contributor;
    private long contributorID;
    private String magType;
    private double magnitude;
    private String magAuthor;
    private String eventLocationName;

    public EQEvent(){}

    public EQEvent (long eventID, LocalDateTime time, double latitude, double longitude, double depthKm, String author,
            String catalog, String contributor, long contributorID, String magType, double magnitude, String magAuthor,
            String eventLocationName) {
        this.eventID = eventID;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.depthKm = depthKm;
        this.author = author;
        this.catalog = catalog;
        this.contributor = contributor;
        this.contributorID = contributorID;
        this.magType = magType;
        this.magnitude = magnitude;
        this.magAuthor = magAuthor;
        this.eventLocationName = eventLocationName;
    }
    public long getEventID() {
        return eventID;
    }
    public void setEventID(long eventID) {
        this.eventID = eventID;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getDepthKm() {
        return depthKm;
    }
    public void setDepthKm(double depthKm) {
        this.depthKm = depthKm;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCatalog() {
        return catalog;
    }
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    public String getContributor() {
        return contributor;
    }
    public void setContributor(String contributor) {
        this.contributor = contributor;
    }
    public long getContributorID() {
        return contributorID;
    }
    public void setContributorID(long contributorID) {
        this.contributorID = contributorID;
    }
    public String getMagType() {
        return magType;
    }
    public void setMagType(String magType) {
        this.magType = magType;
    }
    public double getMagnitude() {
        return magnitude;
    }
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    public String getMagAuthor() {
        return magAuthor;
    }
    public void setMagAuthor(String magAuthor) {
        this.magAuthor = magAuthor;
    }
    public String getEventLocationName() {
        return eventLocationName;
    }
    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    @Override
    public int compareTo(EQEvent o){
        return (int) (this.eventID - o.getEventID());
    }
}