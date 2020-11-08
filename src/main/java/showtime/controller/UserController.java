package showtime.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import showtime.model.User;
import showtime.repository.UserRepository;

import javax.validation.constraints.Null;
import java.security.Principal;
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
        // user exists, 409 CONFLICT
        if(userOpt.isPresent()) {
            logger.debug(user.getEmail() + " 409 CONFLICT");
            return new ResponseEntity<>(user.getEmail() + " exists.", HttpStatus.CONFLICT);
        }
        else {
            // creates new user, 201 CREATED
            try {
                logger.debug(user.getEmail() + " 201 CREATED");
                User newUser = userRepo.save(user);
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            }
            // unable to handle exception, 500 INTERNAL SERVER ERROR
            catch(Exception e) {
                logger.debug(user.getEmail() + " 500 INTERNAL SERVER ERROR");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    // unable to process Json, cannot be thrown in registerNewUser(), 400 BAD REQUEST
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<?> handleJsonProcessingException(JsonProcessingException jpe) {
        logger.debug(jpe.getMessage() + " 400 BAD REQUEST");
        return new ResponseEntity<>(jpe.getMessage() + " at \"/user.POST\".", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user")
    public ResponseEntity<?> retrieveUserByEmail(@RequestParam String email, Principal principal) {
        // locate session user from Principal, safe type casting
        Authentication authentication = (Authentication) principal;
        // no session information, perhaps no Cookie information?
        if(authentication == null) {
            return new ResponseEntity<>("Unauthorized access of non-login user.", HttpStatus.UNAUTHORIZED);
        }
        org.springframework.security.core.userdetails.User authorizedUser =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        // accessing user is not session user
        if(!authorizedUser.getUsername().equals(email)) {
            return new ResponseEntity<>("Unauthorized access of other user.", HttpStatus.UNAUTHORIZED);
        }
        Optional<User> userOpt = userRepo.findUserByEmail(email);
        if(userOpt.isPresent()) {
            return new ResponseEntity<>(userOpt.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}