package org.example.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    private UUID userid;

    private Long chatId;

    @Autowired
    @Transient
    UserRepository userRepository;

    public User() {
    }

    public User(UUID userid, Long chatId) {
        this.userid = userid;
        this.chatId = chatId;
    }


}
