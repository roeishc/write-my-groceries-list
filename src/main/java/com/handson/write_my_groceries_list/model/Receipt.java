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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER) // always fetch the user when fetching a receipt
    @JoinColumn(name = "user_id")
    private DBUser user;

    @Column(nullable = false)   // max length by default is 255
    private String fileName;

    @Column
    private int totalCost;

    @Column
    private Date createdAt;


    @PrePersist
    public void generateUUID() {
        if (id == null)
            id = UUID.randomUUID();
    }

    public Receipt(DBUser user, String fileName, int totalCost, Date createdAt) {
        this.user = user;
        this.fileName = fileName;
        this.totalCost = totalCost;
        this.createdAt = createdAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}

