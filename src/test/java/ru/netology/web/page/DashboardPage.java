package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement headingText = $("[data-test-id=dashboard] ~h1");
    private ElementsCollection cards = $$(".list__item");
    private String button = "[data-test-id=action-deposit]";
    private String startText = "баланс:";
    private String finishText = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
        headingText.shouldBe(visible).shouldHave(text("Ваши карты"));
    }

    public int getBalanceCard(int indexItem) {
        String cardInfo = cards.get(indexItem).text();
        int start = cardInfo.lastIndexOf(startText);
        int finish = cardInfo.lastIndexOf(finishText);
        String balanceText = cardInfo.substring(start + startText.length(), finish).trim();
        return Integer.parseInt(balanceText);
    }

    public TransferPage pressReplenishCard(int indexItem) {
        SelenideElement buttonReplenishCard = cards.get(indexItem).find(button);
        buttonReplenishCard.click();
        return new TransferPage();
    }
}
