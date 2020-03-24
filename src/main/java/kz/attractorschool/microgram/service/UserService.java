package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.UserDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.PostRepo;
import kz.attractorschool.microgram.repository.SubscriptionRepo;
import kz.attractorschool.microgram.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final SubscriptionRepo subscriptionRepo;

    public Slice<UserDTO> findUsers(Pageable pageable) {
        Page<User> slice = userRepo.findAll(pageable);
        updateNumbers(slice);
        return slice.map(UserDTO::from);
    }

    public UserDTO findUserByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + username));
        updateNumbers(user);
        return UserDTO.from(user);
    }

    public UserDTO findUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the email: " + email));
        updateNumbers(user);
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
        updateNumbers(slice);
        return slice.map(UserDTO::from);
    }

    public UserDTO addUser(UserDTO userData) {
        User user = User.builder()
                .username(userData.getUsername())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .numOfPosts(userData.getNumOfPosts())
                .numOfFollowers(userData.getNumOfFollowers())
                .numOfFollowings(userData.getNumOfFollowings())
                .build();

        userRepo.save(user);
        return UserDTO.from(user);
    }
    public boolean deleteUser(String username) {
        userRepo.deleteByUsername(username);
        return true;
    }
    private void updateNumbers(Iterable<User> users){
        users.forEach(user -> {
            user.setNumOfPosts(postRepo.countByUserEmail(user.getEmail()));
            user.setNumOfFollowers(subscriptionRepo.countByFollowingEmail(user.getEmail()));
            user.setNumOfFollowings(subscriptionRepo.countByFollowerEmail(user.getEmail()));
        });
    }
    private void updateNumbers(User user){
        user.setNumOfPosts(postRepo.countByUserEmail(user.getEmail()));
        user.setNumOfFollowers(subscriptionRepo.countByFollowingEmail(user.getEmail()));
        user.setNumOfFollowings(subscriptionRepo.countByFollowerEmail(user.getEmail()));
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optUser = userRepo.findByEmail(email);
        if(optUser.isEmpty())
            throw new UsernameNotFoundException("Not found");
        return optUser.get();
    }
}
