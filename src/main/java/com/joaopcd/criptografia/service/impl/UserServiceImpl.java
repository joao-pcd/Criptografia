package com.joaopcd.criptografia.service.impl;

import com.joaopcd.criptografia.domain.model.Card;
import com.joaopcd.criptografia.domain.model.User;
import com.joaopcd.criptografia.domain.repository.CardRepository;
import com.joaopcd.criptografia.domain.repository.UserRepository;
import com.joaopcd.criptografia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CardRepository cardRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(User user) throws NoSuchAlgorithmException {
        criptographyUserDoc(user);
        user.getCards().forEach(card -> {
            try {
                criptographyCard(card);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        User savedUser = userRepository.save(user);
        user.getCards().forEach(card -> card.setUserId(savedUser.getId()));
        return userRepository.save(savedUser);
    }

    @Override
    public User update(Long id, User user) throws NoSuchAlgorithmException {
        User userToUpdate = this.findById(id);

        userToUpdate.setUserDocument(user.getUserDocument());
        criptographyUserDoc(userToUpdate);

        if (!user.getCards().isEmpty()){
            userToUpdate.getCards().clear();
        }

        user.getCards().forEach(card -> {
            try {
                criptographyCard(card);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            Card existingCard = cardRepository.findById(card.getCreditCardToken()).orElse(null);
            if (existingCard == null){
                existingCard = new Card();
                existingCard.setCreditCardToken(card.getCreditCardToken());
            }
            existingCard.setValue(card.getValue());
            existingCard.setUserId(userToUpdate.getId());

            userToUpdate.getCards().add(existingCard);
        });

        return userRepository.save(userToUpdate);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        user.getCards().clear();
        userRepository.deleteById(id);
    }

    private static byte[] getSHA(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHex(byte[] hash){
        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString =  new StringBuilder(number.toString(16));

        while (hexString.length() < 64){
            hexString.insert(0,"0");
        }
        return hexString.toString();
    }

    private static void criptographyUserDoc(User user) throws NoSuchAlgorithmException {
        String criptoDoc = toHex(getSHA(user.getUserDocument()));
        user.setUserDocument(criptoDoc);
    }

    private static void criptographyCard(Card card) throws NoSuchAlgorithmException {
        String criptoCard = toHex(getSHA(card.getCreditCardToken()));
        card.setCreditCardToken(criptoCard);
    }
}

