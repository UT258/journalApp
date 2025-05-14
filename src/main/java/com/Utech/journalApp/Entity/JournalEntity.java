package com.Utech.journalApp.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document() //find the collection name in MongoDB
//@dcument means that this class is a document in MongoDB
public class JournalEntity {
    String id;
    String title;
    String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
