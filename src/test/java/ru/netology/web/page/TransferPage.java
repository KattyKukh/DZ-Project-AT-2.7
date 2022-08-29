package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard] ~h1");

    public TransferPage() {
        heading.shouldBe(visible).shouldHave(Condition.text("Пополнение карты"));
    }

    private SelenideElement amount = $("[data-test-id=amount] .input__control");
    private SelenideElement fromCard = $("[data-test-id=from] .input__control");
    private SelenideElement toCard = $("[data-test-id=to] .input__control");
    private SelenideElement buttonTransfer = $("[data-test-id=action-transfer]");
    private SelenideElement buttonCancel = $("[data-test-id=action-cancel]");

    public void fillInForm(Integer sum, DataHelper.CardInfo card) {
        amount.sendKeys(Keys.chord(Keys.CONTROL + "A"), Keys.DELETE);
        amount.setValue(Integer.toString(sum));
        fromCard.sendKeys(Keys.chord(Keys.CONTROL + "A"), Keys.DELETE);
        fromCard.sendKeys(card.getCardNumber());
    }

    public DashboardPage replenishCardBalance(Integer sum, DataHelper.CardInfo card) {
        fillInForm(sum, card);
        buttonTransfer.click();
        return new DashboardPage();
    }

    public DashboardPage replenishCardCancel(Integer sum, DataHelper.CardInfo card) {
        fillInForm(sum, card);
        buttonCancel.click();
        return new DashboardPage();
    }
}
