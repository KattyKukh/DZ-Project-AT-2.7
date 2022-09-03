package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.web.data.DataHelper.getTransferAmount;

class MoneyTransferTest {
    private DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    DashboardPage logIn() {
        var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        return verificationPage.validVerify(verificationCode);
    }

    void balansesEguals(DashboardPage dashboardPage) {
        var firstCardBalance = dashboardPage.getBalanceCard(0);
        var secondCardBalance = dashboardPage.getBalanceCard(1);
        if (firstCardBalance < secondCardBalance) {
            int alignTransfer = (firstCardBalance + secondCardBalance) / 2 - firstCardBalance;
            dashboardPage.pressReplenishCard(0)
                    .replenishCardBalance(alignTransfer, DataHelper.getSecondCardNumber());
        }
        if (firstCardBalance > secondCardBalance) {
            int alignTransfer = (firstCardBalance + secondCardBalance) / 2 - secondCardBalance;
            dashboardPage.pressReplenishCard(1)
                    .replenishCardBalance(alignTransfer, DataHelper.getFirstCardNumber());
        }
    }

    @Test
    void shouldNotLogInIfUsersDataIsWrong() {
        var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getWrongAuthInfo();
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecond() {
        DashboardPage dashboardPage = logIn();
        balansesEguals(dashboardPage);
        var firstCardBalance = dashboardPage.getBalanceCard(0);
        var secondCardBalance = dashboardPage.getBalanceCard(1);
        int transfer = getTransferAmount(firstCardBalance);
        dashboardPage.pressReplenishCard(1)
                .replenishCardBalance(transfer, DataHelper.getFirstCardNumber());
        assertEquals(firstCardBalance - transfer, dashboardPage.getBalanceCard(0));
        assertEquals(secondCardBalance + transfer, dashboardPage.getBalanceCard(1));
    }

    @Test
    void shouldNotTransferMoneyFromFirstCardToSecondIfCancelLed() {
        DashboardPage dashboardPage = logIn();
        balansesEguals(dashboardPage);
        var firstCardBalance = dashboardPage.getBalanceCard(0);
        var secondCardBalance = dashboardPage.getBalanceCard(1);
        int transfer = getTransferAmount(firstCardBalance);
        dashboardPage.pressReplenishCard(1)
                .replenishCardCancel(transfer, DataHelper.getFirstCardNumber());
        assertEquals(firstCardBalance, dashboardPage.getBalanceCard(0));
        assertEquals(secondCardBalance, dashboardPage.getBalanceCard(1));
    }

    @Test
    void shouldNotTransferMoneyFromFirstCardToSecondIfTransferMoreBalance() {
        DashboardPage dashboardPage = logIn();
        balansesEguals(dashboardPage);
        var firstCardBalance = dashboardPage.getBalanceCard(0);
        var secondCardBalance = dashboardPage.getBalanceCard(1);
        int transfer = firstCardBalance + 10_000;
        dashboardPage.pressReplenishCard(1)
                .replenishCardBalance(transfer, DataHelper.getFirstCardNumber());
        assertEquals(firstCardBalance, dashboardPage.getBalanceCard(0));
        assertEquals(secondCardBalance, dashboardPage.getBalanceCard(1));
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirst() {
        DashboardPage dashboardPage = logIn();
        balansesEguals(dashboardPage);
        var firstCardBalance = dashboardPage.getBalanceCard(0);
        var secondCardBalance = dashboardPage.getBalanceCard(1);
        int transfer = getTransferAmount(secondCardBalance);
        dashboardPage.pressReplenishCard(0)
                .replenishCardBalance(transfer, DataHelper.getSecondCardNumber());
        assertEquals(firstCardBalance + transfer, dashboardPage.getBalanceCard(0));
        assertEquals(secondCardBalance - transfer, dashboardPage.getBalanceCard(1));
    }

    @Test
    void shouldNotTransferMoneyFromSecondCardToFirstIfCancelled() {
        DashboardPage dashboardPage = logIn();
        balansesEguals(dashboardPage);
        var firstCardBalance = dashboardPage.getBalanceCard(0);
        var secondCardBalance = dashboardPage.getBalanceCard(1);
        int transfer = getTransferAmount(secondCardBalance);
        dashboardPage.pressReplenishCard(0)
                .replenishCardCancel(transfer, DataHelper.getSecondCardNumber());
        assertEquals(firstCardBalance, dashboardPage.getBalanceCard(0));
        assertEquals(secondCardBalance, dashboardPage.getBalanceCard(1));
    }

    @Test
    void shouldNotTransferMoneyFromSecondCardToFirstIfTransferMoreBalance() {
        DashboardPage dashboardPage = logIn();
        balansesEguals(dashboardPage);
        var firstCardBalance = dashboardPage.getBalanceCard(0);
        var secondCardBalance = dashboardPage.getBalanceCard(1);
        int transfer = secondCardBalance + 10_000;
        dashboardPage.pressReplenishCard(0)
                .replenishCardBalance(transfer, DataHelper.getSecondCardNumber());
        assertEquals(firstCardBalance, dashboardPage.getBalanceCard(0));
        assertEquals(secondCardBalance, dashboardPage.getBalanceCard(1));
    }
}

