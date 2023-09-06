import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WildberriesTest {

    @BeforeEach
    void setUp(){
        open("https://www.wildberries.by/");
    }

    @DisplayName("Проверка поиска канцелярских товаров на сайте Wildberries")
    @ValueSource( strings = {"Ручка","Карандаш"})
    @ParameterizedTest(name = "В поисковой выдаче Wildberries не пустая для запроса {0}")
    void searchResultsShouldNotBeEmpty(String searchQuery) {
        $("input[type='text']").click();
        $("input[type='text']")
                .setValue(searchQuery)
                .pressEnter();

        $(".page__heading").shouldHave(text(searchQuery));
    }


    @CsvSource(value = {
            "мужские,     носки",
            "женская,     футболка"
    })
    @DisplayName("Проверка поиска одежды на сайте Wildberries")
    @ParameterizedTest(name = "В поисковой выдаче Wildberries присутствует значение {0} для запроса {1}")
    void successfulSearchResultTest(String search, String searchQuary){
        $("input[type='text']").click();
        $("input[type='text']")
                .setValue(searchQuary)
                .pressEnter();

        $(".search-tags").shouldHave(text(search));
    }


    static Stream<Arguments> searchResultFilterDisplayed(){
        return Stream.of(
                Arguments.of("пенал", "Пенал черный Пенал школьный Пенал для мальчика Пенал для девочки Пенал прозрачный"),
                Arguments.of("рюкзак","Рюкзак школьный Рюкзак женский Рюкзак мужской Рюкзак школьный для девочки Рюкзак черный")
        );
    }

    @MethodSource
    @ParameterizedTest(name = "При поиске товара {0} в предложенный товарах отображаются {1}")
    void searchResultFilterDisplayed(String product,String anotherProducts){
        $("input[type='text']").click();
        $("input[type='text']")
                .setValue(product)
                .pressEnter();

        $(".search-tags").shouldHave(text(anotherProducts));
    }

}
