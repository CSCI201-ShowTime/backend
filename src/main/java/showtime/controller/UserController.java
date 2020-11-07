package showtime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import showtime.model.User;
import showtime.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/user")
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        Optional<User> userOpt = userRepo.findUserByEmail(user.getEmail());
        if(userOpt.isPresent()) {
            // user exists, 409 CONFLICT
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        else {
            try {
                // creates new user, 201 CREATED
                // new user is created by UserDeserializer which should not throw any error,
                // or is created by default fields in User class (choose 1 that applies)
                User newUser = userRepo.save(user);
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            }
            catch(Exception e) {
                // unhandled exception, 500 INTERNAL SERVER ERROR
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

/*    @GetMapping("/auth")
    public ResponseEntity<User> givenEmailVerifyPswd(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOpt = userRepository.findUserByEmail(email);
        if(userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return new ResponseEntity<>(userOpt.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
}