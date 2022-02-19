package com.alex.vis.voteApp.service.user;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.repository.UserRepository;
import com.alex.vis.voteApp.security.SecurityUser;
import com.alex.vis.voteApp.to.UserTo;
import com.alex.vis.voteApp.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User create(UserTo userTo) {
        return userRepository.save(UserUtil.createUserFromTo(userTo));
    }

    @Override
    public void delete(int id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(u -> userRepository.deleteById(id));
    }

    @Override
    public void update(UserTo userTo) {
        userRepository.save(UserUtil.createUserFromTo(userTo));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).
                orElseThrow(() -> new UsernameNotFoundException("User doesnt exists"));

        return SecurityUser.fromUser(user);
    }
}
