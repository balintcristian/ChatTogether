package com.example.demo.service;

import com.example.demo.model.UserDataDTO;
import com.example.demo.persistence.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Cauta un user in repository
     * @param id id-ul user-ului cautat
     * @return o instanta UserDTO daca a fost gasit user-ul, altfel returneaza null
     */
    public UserDataDTO findById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return convertEntityToDto(user);
        }
        return null;
    }

    /**
     * Actualizeaza nickname-ul utilizatorului
     * @param id id-ul utilizatorului caruia i se modifica nickname-ul
     * @param newNickname nickname-ul nou
     * @return o instanta UserDTO daca a fost gasit user-ul, altfel returneaza null
     */
    public UserDataDTO patchNickname(Long id, String newNickname){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setNickname(newNickname);
            userRepository.save(user);
            return convertEntityToDto(user);
        }
        return null;
    }

    public UserDataDTO findByLoginUsername(String loginUsername){
        Optional<User> optionalUser = userRepository.findByLoginUsername(loginUsername);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return convertEntityToDto(user);
        }
         return null;
    }

    private UserDataDTO convertEntityToDto(User user){
        return new UserDataDTO(user.getId(), user.getNickname(), user.getLoginUsername());
    }
}
