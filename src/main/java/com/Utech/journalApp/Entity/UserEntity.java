package com.Utech.journalApp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @Field("UserName") // Stored as "UserName" in MongoDB, but field is userName
    private String userName;

    private String password;

    @DBRef
    private List<JournalEntity> journalEntries = new ArrayList<>();
    private List<String>roles = new ArrayList<>(); // List of roles for the user

}
