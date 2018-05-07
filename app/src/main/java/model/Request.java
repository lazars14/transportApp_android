package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Request {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("startLocation")
    private Location startLocation;
    @JsonProperty("endLocation")
    private Location endLocation;
    @JsonProperty("startDate")
    private Date startDate;
    @JsonProperty("endDate")
    private Date endDate;
    @JsonProperty("price")
    private float price;
    @JsonProperty("discount")
    private float discount;
    @JsonProperty("status")
    private int status;
    @JsonProperty("destinationId")
    private String destinationId;
    @JsonProperty("submissionDate")
    private Date submissionDate;
    @JsonProperty("confirmationRequestDate")
    private Date confirmationRequestDate;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("destinationOrder")
    private int destinationOrder;
    @JsonProperty("distance")
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

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("startLocation")
    public Location getStartLocation() {
        return startLocation;
    }

    @JsonProperty("startLocation")
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    @JsonProperty("endLocation")
    public Location getEndLocation() {
        return endLocation;
    }

    @JsonProperty("endLocation")
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    @JsonProperty("startDate")
    public Date getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("endDate")
    public Date getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("price")
    public float getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(float price) {
        this.price = price;
    }

    @JsonProperty("discount")
    public float getDiscount() {
        return discount;
    }

    @JsonProperty("discount")
    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @JsonProperty("status")
    public int getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(int status) {
        this.status = status;
    }

    @JsonProperty("destinationId")
    public String getDestinationId() {
        return destinationId;
    }

    @JsonProperty("destinationId")
    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    @JsonProperty("submissionDate")
    public Date getSubmissionDate() {
        return submissionDate;
    }

    @JsonProperty("submissionDa")
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    @JsonProperty("confirmationRequestDate")
    public Date getConfirmationRequestDate() {
        return confirmationRequestDate;
    }

    @JsonProperty("confirmationRequestDate")
    public void setConfirmationRequestDate(Date confirmationRequestDate) {
        this.confirmationRequestDate = confirmationRequestDate;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("destinationOrder")
    public int getDestinationOrder() {
        return destinationOrder;
    }

    @JsonProperty("destinationOrder")
    public void setDestinationOrder(int destinationOrder) {
        this.destinationOrder = destinationOrder;
    }

    @JsonProperty("distance")
    public float getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(float distance) {
        this.distance = distance;
    }
}
