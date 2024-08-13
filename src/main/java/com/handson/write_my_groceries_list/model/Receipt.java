package com.handson.write_my_groceries_list.model;

import com.handson.write_my_groceries_list.jwt.DBUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "receipts")
public class Receipt implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER) // always fetch the user when fetching a receipt
    @JoinColumn(name = "user_id")
    private DBUser user;

    @Column(nullable = false)   // max length by default is 255
    private String fileName;

    @Column
    private int totalCost;

    @Column(nullable = false)
    private String fullPathInBucket;

    @Column(nullable = false)
    private boolean isActive;

    @Column
    private Date createdAt;


    @PrePersist
    public void generateUUID() {
        if (id == null)
            id = UUID.randomUUID();
    }

    public Receipt() {}

    public Receipt(DBUser user, String fileName, int totalCost, boolean isActive, String pathInS3, Date createdAt) {
        this.user = user;
        this.fileName = fileName;
        this.totalCost = totalCost;
        this.isActive = isActive;
        this.fullPathInBucket = pathInS3;
        this.createdAt = createdAt;
    }

    public Receipt(DBUser user, String fileName, int totalCost, boolean isActive, Date createdAt) {
        this.user = user;
        this.fileName = fileName;
        this.totalCost = totalCost;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public DBUser getUser() {
        return user;
    }

    public void setUser(DBUser user) {
        this.user = user;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getFullPathInBucket() {
        return fullPathInBucket;
    }

    public void setFullPathInBucket(String fullPathInBucket) {
        this.fullPathInBucket = fullPathInBucket;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public static final class ReceiptBuilder {

        private @NotNull DBUser user;

        private String fileName;

        private int totalCost;

        private boolean isActive;

        private String pathInS3;

        private Date createdAt;


        private ReceiptBuilder() {}

        public static ReceiptBuilder aReceipt() {
            return new ReceiptBuilder();
        }

        public ReceiptBuilder withUser(DBUser user) {
            this.user = user;
            return this;
        }

        public ReceiptBuilder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public ReceiptBuilder withTotalCost(int totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public ReceiptBuilder withIsActive(boolean isActive){
            this.isActive = isActive;
            return this;
        }

        public ReceiptBuilder withPathInS3(String pathInS3){
            this.pathInS3 = pathInS3;
            return this;
        }

        public ReceiptBuilder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Receipt build() {
            return new Receipt(user, fileName, totalCost, isActive, pathInS3, createdAt);
        }
    }

}

