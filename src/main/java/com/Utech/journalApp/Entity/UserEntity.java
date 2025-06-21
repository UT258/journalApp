package com.Utech.journalApp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Document(collection = "users") // Specify the collection name in MongoDB
@Data
public class UserEntity {
    @Id
    private ObjectId id;
    @NonNull
    @Indexed(unique = true)//must be unique
    private String name;
    //private String email;
    private String password;
   // private String phoneNumber;
   // private String address;
    @DBRef // This annotation is used to reference other documents in MongoDB
    //refers to the JournalEntity collection
private List<JournalEntity> journalEntries=new ArrayList<>();

}
