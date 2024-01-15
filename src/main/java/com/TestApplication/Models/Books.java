package com.TestApplication.Models;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue
    private int Id;
    private String status;
    private String name;

    private String uuid;

    private String author_name;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Books() {
    }


    public Books(String status, String name, String uuid, String author_name) {
        this.status = status;
        this.name = name;
        this.uuid = uuid;
        this.author_name = author_name;
        this.createdAt = ZonedDateTime.now(ZoneId.of("Z"));
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Z"));
    }

}
