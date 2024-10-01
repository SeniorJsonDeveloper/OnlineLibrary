package dn.onlinelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;

    private String password;

    private String email;

    private LocalDateTime createdAt = LocalDateTime.now();
}
