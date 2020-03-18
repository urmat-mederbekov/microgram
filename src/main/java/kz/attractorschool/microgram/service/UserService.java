package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.UserDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Slice<UserDTO> findUsers(Pageable pageable) {
        Page<User> slice = userRepo.findAll(pageable);
        return slice.map(UserDTO::from);
    }

    public UserDTO findUserByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + username));
        return UserDTO.from(user);
    }

    public UserDTO findUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the email: " + email));
        return UserDTO.from(user);
    }

    public String existsUserByEmail(String email) {
        if (userRepo.existsByEmail(email)) {
            return "There's a user with email: " + email;
        } else {
            return "There's no user with email: " + email;
        }
    }

    public Slice<UserDTO> findOtherUsers(Pageable pageable, String username) {
        Page<User> slice = userRepo.findAllByUsernameNotContains(pageable, username);
        return slice.map(UserDTO::from);
    }

    public UserDTO addUser(UserDTO userData) {
        User user = User.builder()
                .id(userData.getId())
                .username(userData.getUsername())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .numOfPosts(userData.getNumOfPosts())
                .numOfFollowers(userData.getNumOfFollowers())
                .numOfFollowings(userData.getNumOfFollowings())
                .posts(userData.getPosts())
                .followers(userData.getFollowers())
                .followings(userData.getFollowings())
                .build();

        userRepo.save(user);
        return UserDTO.from(user);
    }
    public boolean deleteUser(String username) {
        userRepo.deleteByUsername(username);
        return true;
    }

}
