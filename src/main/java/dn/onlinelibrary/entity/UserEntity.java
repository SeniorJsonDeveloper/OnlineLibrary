package dn.onlinelibrary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(schema = "online_library",name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String username;

    public String password;

    public String email;

    @CreationTimestamp
    private LocalDateTime createdAt;



    public UserEntity(String userId, String username, String email) {
    }

    public UserEntity(String userId, String username, String encodePassword, String email) {
    }

    public UserEntity(String id, String username, String password, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public UserEntity() {}
}
