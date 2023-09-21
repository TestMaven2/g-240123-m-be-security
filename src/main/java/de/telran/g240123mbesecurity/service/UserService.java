package de.telran.g240123mbesecurity.service;

import de.telran.g240123mbesecurity.domain.entity.Role;
import de.telran.g240123mbesecurity.domain.entity.User;
import de.telran.g240123mbesecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return user;
    }

    public User saveUser(User user) {
        User foundUser = repository.findByUsername(user.getUsername());

        if (foundUser != null) {
            return null;
        }

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2, "ROLE_USER"));
        user.setRoles(roles);

        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}
