package com.example.office.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "files")
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false, unique = true)
    private String name;

    private long size;

    @Column(name = "upload_time")
    private Date uploadTime;

    @Lob
    private byte[] content;
    public Documents(Long id, String name, long size){
        super();
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Documents () {
    }

    @Column(name = "user_id")
    private Long userId;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
