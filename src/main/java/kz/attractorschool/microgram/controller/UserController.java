package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.annotations.ApiPageable;
import kz.attractorschool.microgram.dto.UserDTO;
import kz.attractorschool.microgram.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiPageable
    @GetMapping
    public Slice<UserDTO> findUsers(@ApiIgnore Pageable pageable) {
        return userService.findUsers(pageable);
    }

    @ApiPageable
    @GetMapping("/{username}")
    public Slice<UserDTO> findOtherUsers(@ApiIgnore Pageable pageable, @PathVariable String username) {
        return userService.findOtherUsers(pageable, username);
    }

    @GetMapping("/username/{username}")
    public UserDTO findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @GetMapping("/email/{email}")
    public UserDTO findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @GetMapping("/email/exist/{email}")
    public String existUserByEmail(@PathVariable String email) {
        return userService.existsUserByEmail(email);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO addUser(@RequestBody UserDTO userData) {
        return userService.addUser(userData);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        if (userService.deleteUser(username))
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
