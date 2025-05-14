package com.Utech.journalApp.Entity;

import org.bson.types.ObjectId;

import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;

@Document() //find the collection name in MongoDB
//@dcument means that this class is a document in MongoDB
public class JournalEntity {
    ObjectId id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    String title;
    String content;
    Date date;


}
