package com.alex.projectrapi.utils;

import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PhoneNumberGenerator {

    private static final int PHONE_NUMBER_LENGTH = 9;
    private static final int MIN_PHONE_NUMBER = 100_000_000; // 100000000
    private static final int MAX_PHONE_NUMBER = 999_999_999; // 999999999

    private final UsuarioRepository usuarioRepository;
    private final Random random = new Random();

    @Autowired
    public PhoneNumberGenerator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String generatePhoneNumber() {
        String phoneNumber = generateRandomPhoneNumber();
        return checkAndGenerate(phoneNumber);
    }

    private String generateRandomPhoneNumber() {
        int number = random.nextInt(MAX_PHONE_NUMBER - MIN_PHONE_NUMBER + 1) + MIN_PHONE_NUMBER;
        return String.valueOf(number);
    }

    private String checkAndGenerate(String phoneNumber) {
        if (usuarioRepository.existsByPhoneNumber(phoneNumber)) {
            return generatePhoneNumber(); // Llamada recursiva si existe
        }
        return phoneNumber;
    }
}
