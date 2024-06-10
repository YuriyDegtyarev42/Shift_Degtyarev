package autotests.duckActionController;

import autotests.clients.DuckControllerHelper;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class PropertiesTest extends DuckControllerHelper {

    @Test(description = "Проверка получения свойств уточки (четный id, материал wood)")
    @CitrusTest
    public void getDuckEvenNumberWoodProperties(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(1.1)
                .material("wood")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuckEvenNumber(runner, duck);
        getDuckProperties(runner,"${duckId}");
        String expectedString = "{\n" +
                "  \"color\": \"" + duck.color() + "\",\n" +
                "  \"height\": " + duck.height() + ",\n" +
                "  \"material\": \"" + duck.material() + "\",\n" +
                "  \"sound\": \"" + duck.sound() + "\",\n" +
                "  \"wingsState\": \"" + duck.wingsState() + "\"\n" +
                "}";

        validateDuckResponseString(runner, HttpStatus.OK, expectedString);
    }

    @Test(description = "Проверка получения свойств уточки (нечетный id, материал rubber)")
    @CitrusTest
    public void getDuckOddNumberRubberProperties(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(1.1)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuckOddNumber(runner, duck);
        getDuckProperties(runner,"${duckId}");

        validateDuckResponseResources(runner, HttpStatus.OK, "PropertiesResponses/OddNumberResponse.json");
    }

}
