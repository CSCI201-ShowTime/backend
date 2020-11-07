package showtime.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import showtime.model.User;
import showtime.repository.UserRepository;

import javax.validation.constraints.Null;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/user")
    public ResponseEntity<?> registerNewUser(@RequestBody User user) {
        // user is created by Jackson custom UserDeserializer, or custom @JsonSetter
        logger.debug(user.toString());
        Optional<User> userOpt = userRepo.findUserByEmail(user.getEmail());
        if(userOpt.isPresent()) {
            // user exists, 409 CONFLICT
            logger.debug(user.getEmail() + " 409 CONFLICT");
            return new ResponseEntity<>(user.getEmail() + " exists.", HttpStatus.CONFLICT);
        }
        else {
            try {
                // creates new user, 201 CREATED
                logger.debug(user.getEmail() + " 201 CREATED");
                User newUser = userRepo.save(user);
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            }
            catch(Exception e) {
                // unhandled exception, 500 INTERNAL SERVER ERROR
                logger.debug(user.getEmail() + " 500 INTERNAL SERVER ERROR");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    // unable to process Json, cannot be thrown in registerNewUser()
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<?> handleJsonProcessingException(JsonProcessingException jpe) {
        logger.debug(jpe.getMessage() + " 400 BAD REQUEST");
        return new ResponseEntity<>(jpe.getMessage() + " at \"/user.POST\"", HttpStatus.BAD_REQUEST);
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