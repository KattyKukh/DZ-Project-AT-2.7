package ru.netology.web.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getWrongAuthInfo() {
        return new AuthInfo("sonya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        String cardNumber;
    }

    public static CardInfo getFirstCardNumber() {
        return new CardInfo("5559 0000 0000 0001");
    }

    public static CardInfo getSecondCardNumber() {
        return new CardInfo("5559 0000 0000 0002");
    }

    public static int getTransferAmount(int balance) {
        return new Random().nextInt(balance) + 1;
    }

}
