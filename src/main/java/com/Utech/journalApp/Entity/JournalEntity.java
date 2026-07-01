package com.Utech.journalApp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("journal") // optional: name the collection
@Data
@NoArgsConstructor
public class JournalEntity {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private Date date;
    private List<String> tags = new ArrayList<>();
    private Sentiment sentiment = Sentiment.NEUTRAL;
    private int sentimentScore;
}
