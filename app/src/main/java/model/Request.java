package model;

import java.util.Date;

public class Request {

    private String id;
    private Location startLocation;
    private Location endLocation;
    private Date startDate;
    private Date endDate;
    private float price;
    private float discount;
    private int status;
    private String destinationId;
    private Date submissionDate;
    private Date confirmationRequestDate;
    private String userId;
    private int destinationOrder;
    private float distance;

    public Request(String id, Location startLocation, Location endLocation, Date startDate, Date endDate, float price, float discount, int status,
                   String destinationId, Date submissionDate, Date confirmationRequestDate, String userId, int destinationOrder, float distance) {
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.discount = discount;
        this.status = status;
        this.destinationId = destinationId;
        this.submissionDate = submissionDate;
        this.confirmationRequestDate = confirmationRequestDate;
        this.userId = userId;
        this.destinationOrder = destinationOrder;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Date getConfirmationRequestDate() {
        return confirmationRequestDate;
    }

    public void setConfirmationRequestDate(Date confirmationRequestDate) {
        this.confirmationRequestDate = confirmationRequestDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDestinationOrder() {
        return destinationOrder;
    }

    public void setDestinationOrder(int destinationOrder) {
        this.destinationOrder = destinationOrder;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
