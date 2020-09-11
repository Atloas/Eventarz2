package com.agh.eventarz2;

import com.agh.eventarz2.model.User;
import com.agh.eventarz2.model.UserForm;
import com.agh.eventarz2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * This class handles User registration.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new User.
     *
     * @param userForm UserForm object containing the relevant data from the frontend, already verified.
     * @return A newly created registered User object.
     * @throws UserAlreadyExistsException When the username is already taken.
     */
    @Transactional
    public User registerNewUserAccount(UserForm userForm)
            throws UserAlreadyExistsException {

        if (usernameExists(userForm.getUsername())) {
            throw new UserAlreadyExistsException("There is an account with that username:" + userForm.getUsername());
        }
        User user = new User(
                userForm.getUsername(),
                passwordEncoder.encode(userForm.getPassword()),
                Arrays.asList("USER"));
        return userRepository.save(user);
    }

    /**
     * Checks if the provided username already exists in the database.
     *
     * @param username Username to check.
     * @return Whether it's taken or not.
     */
    private boolean usernameExists(String username) {
        return userRepository.findIdByUsername(username) != null;
    }
}