package dev.kamko.lnu_ass.core.domain.user.view;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserInfo {

    @Id
    String id;
    String name;
    String email;
    LocalDateTime registeredAt;
    LocalDateTime lastLoginAt;

    public UserInfo(String id, String name, String email, LocalDateTime registeredAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registeredAt = registeredAt;
    }
}
