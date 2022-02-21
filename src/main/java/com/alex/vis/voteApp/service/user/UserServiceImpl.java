package com.alex.vis.voteApp.service.user;

import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.repository.UserRepository;
import com.alex.vis.voteApp.security.AuthorizedUser;
import com.alex.vis.voteApp.util.UserUtil;
import com.alex.vis.voteApp.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }

    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        ValidationUtil.checkNew(user);
        user.getRoles().add(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(u -> userRepository.deleteById(id));
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        User user = getByName(name);
        ValidationUtil.checkNull(user);
        delete(user.getId());
    }

    @Override
    @Transactional
    public void updateByName(User newUser, String name) {
        ValidationUtil.checkNull(newUser);
        User oldUser = getByName(name);
        ValidationUtil.checkNull(oldUser);

        userRepository.save(UserUtil.prepareToUpdate(oldUser, newUser));
    }

    //TODO Refactor update/create methods.

    @Override
    public void update(User user, int id) {
        Assert.notNull(user, "user must not be null");
        ValidationUtil.assureIdConsistent(user, id);
        userRepository.save(UserUtil.prepareToSave(user, passwordEncoder));
    }

    @Override
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).
                orElseThrow(() ->  new UsernameNotFoundException("User " + username + " is not found"));

        return new AuthorizedUser(user);
    }
}
