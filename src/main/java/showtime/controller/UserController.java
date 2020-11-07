package showtime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import showtime.model.User;
import showtime.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //PRG

    @GetMapping("/auth")
    public ResponseEntity<User> givenEmailVerifyPswd(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOpt = userRepository.findUserByEmail(email);
        if(userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return new ResponseEntity<>(userOpt.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}