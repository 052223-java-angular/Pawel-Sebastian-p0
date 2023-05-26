package com.revature.ecommerce_cli.services;

import java.util.Optional;
import com.revature.ecommerce_cli.models.User;
import org.mindrot.jbcrypt.BCrypt;
import com.revature.ecommerce_cli.DAO.UserDAO;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UserService {
    private final UserDAO userDao;

    

    public User register(String username, String password) {
        
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User(username, hashed);
        userDao.save(newUser);
        return newUser;
    }

    public boolean isValidUsername(String username){

        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    
    
    }

    public boolean isUniqueUsername(String username){
        Optional<User> userOpt = userDao.findByUsername(username);

        if(userOpt.isEmpty()){

            return true;
        }
        return false;
    }

    public Optional<User> login(String username, String password){

        //query db for presence of username
        Optional<User> user = userDao.findByUsername(username);
        
        if(user.isEmpty()){
            return Optional.empty();
        }
        boolean isMatched = BCrypt.checkpw(password, user.get().getPassword());

            if(isMatched){
                return user;
            }
            return Optional.empty();
        }


    

    public boolean isValidPassword(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    public boolean isSamePassword(String password, String confirmPassword){

        return password.equals(confirmPassword);
    }
}

