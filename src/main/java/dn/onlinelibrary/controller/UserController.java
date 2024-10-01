package dn.onlinelibrary.controller;

import dn.onlinelibrary.dto.UserDto;
import dn.onlinelibrary.entity.UserEntity;
import dn.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public void delete(@PathVariable String id) {
        userService.deleteById(id);
    }
}
