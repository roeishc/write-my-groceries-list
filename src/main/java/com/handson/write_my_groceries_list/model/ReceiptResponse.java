package com.handson.write_my_groceries_list.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.handson.write_my_groceries_list.util.Dates;
import org.joda.time.LocalDateTime;

import java.util.Date;
import java.util.UUID;

public class ReceiptResponse {

    private UUID id;

    private String fileName;

    private float totalCost;

    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdAt")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdAt);
    }

    public static ReceiptResponse of(Receipt receipt){
        ReceiptResponse res = new ReceiptResponse();
        res.id = receipt.getId();
        res.fileName = receipt.getFileName();
        res.totalCost = receipt.getTotalCost();
        res.createdAt = receipt.getCreatedAt();
        return res;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
