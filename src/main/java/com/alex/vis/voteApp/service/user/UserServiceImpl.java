package com.alex.vis.voteApp.service.user;

import com.alex.vis.voteApp.exception.NotFoundException;
import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.repository.UserRepository;
import com.alex.vis.voteApp.security.AuthorizedUser;
import com.alex.vis.voteApp.to.UserTo;
import com.alex.vis.voteApp.util.UserUtil;
import com.alex.vis.voteApp.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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


@RequiredArgsConstructor
@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Cacheable("users")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found entity with id=" + id));
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(userRepository.delete(id) != 0, id);
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        user.getRoles().add(Role.USER);
        return userRepository.save(user);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void update(UserTo userTo) {
        User oldUser = get(userTo.id());
        User newUser = UserUtil.prepareToSave(UserUtil.updateFromTo(oldUser, userTo), passwordEncoder);
        userRepository.save(newUser);
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void update(User user, int id) {
        Assert.notNull(user, "user must not be null");
        ValidationUtil.assureIdConsistent(user, id);
        userRepository.save(UserUtil.prepareToSave(user, passwordEncoder));
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
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
