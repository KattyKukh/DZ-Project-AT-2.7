package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPageV2 {
  private SelenideElement loginField = $("[data-test-id=login] input");
  private SelenideElement passwordField = $("[data-test-id=password] input");
  private SelenideElement loginButton = $("[data-test-id=action-login]");
  private SelenideElement notificationErrorText = $("[data-test-id=error-notification]");

  public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
    loginField.setValue(authInfo.getLogin());
    passwordField.setValue(authInfo.getPassword());
    loginButton.click();
    return new VerificationPage();
    }
  public VerificationPage invalidLogin(DataHelper.AuthInfo authInfo) {
    loginField.setValue(authInfo.getLogin());
    passwordField.setValue(authInfo.getPassword());
    loginButton.click();
    notificationErrorText.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    return new VerificationPage();
  }
}
