package cz.cvut.fel.jankupat.AlkoApp.security;


import cz.cvut.fel.jankupat.AlkoApp.exception.ResourceNotFoundException;
import cz.cvut.fel.jankupat.AlkoApp.model.User;
import cz.cvut.fel.jankupat.AlkoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 02/08/17.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
        );

        return UserPrincipal.create(user);
    }

    /**
     * Load user by id user details.
     *
     * @param id the id
     * @return the user details
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}