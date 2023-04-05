package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static com.codeborne.selenide.Configuration.browserSize;
import static com.codeborne.selenide.Selenide.$;


public class JUniTests {
  @BeforeAll
  static void beforeAll() {
    browserSize = "1920 x 1080";
  }

  @BeforeEach
  void beforeEach() {
    Selenide.open("https://www.old-games.ru/");
  }


  @CsvSource(value = {
          "Street Fighter, Capcom Entertainment",
          "Dune, Virgin Games"
  })

  @CsvFileSource(resources = "/testdata/SearchResultsShouldContainExpectedText.csv")
  @ParameterizedTest(name = "Результат поиска по запросу {0} отображет текст {1} на странице")
  public void SearchResultsShouldContainExpectedText(String testData, String expectedText) {
    $("#quicksearchgame").setValue(testData).pressEnter();
    $(".main-content").shouldHave(Condition.text(expectedText));
  }

  @ValueSource(strings = {"Street Fighter", "Dune"})
  @ParameterizedTest(name = "Результат поиска по запросу {0} отображет текст {0} на странице")
  public void SearchResultsShouldContainExpectedGameName(String testData) {
    $("#quicksearchgame").setValue(testData).pressEnter();
    $(".main-content").shouldHave(Condition.text(testData));
  }

  static Stream<Arguments> SearchResultsShouldContainExpeсtedGameNameAndCompanyName() {
    return Stream.of(
            Arguments.of("Street Fighter", "Capcom Entertainment"),
            Arguments.of("Dune", "Virgin Games")

    );
  }

  @MethodSource
  @ParameterizedTest(name = "Результат поиска по запросу {0} отображет текст {1} на странице")
  public void SearchResultsShouldContainExpeсtedGameNameAndCompanyName(String testData, String expectedText) {
    $("#quicksearchgame").setValue(testData).pressEnter();
    $(".main-content").shouldHave(Condition.text(expectedText));

  }
}