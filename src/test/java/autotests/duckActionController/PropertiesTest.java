package autotests.duckActionController;

import autotests.clients.DuckControllerHelper;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class PropertiesTest extends DuckControllerHelper {

    @Test(description = "Проверка получения свойств уточки (четный id, материал wood)")
    @CitrusTest
    public void getDuckEvenNumberWoodProperties(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("wood").sound("quack").wingsState(WingsState.ACTIVE);
        createDuckEvenNumber(runner, duck);
        getDuckProperties(runner,"${duckId}");
        String expectedString = "{\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": 1.1,\n" +
                "  \"material\": \"wood\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}";
        validateDuckResponseString(runner, 200, expectedString);
    }

    @Test(description = "Проверка получения свойств уточки (нечетный id, материал rubber)")
    @CitrusTest
    public void getDuckOddNumberRubberProperties(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuckOddNumber(runner, duck);
        getDuckProperties(runner,"${duckId}");
        validateDuckResponsePayload(runner, 200, "PropertiesResponses/OddNumberResponse.json");
    }

}
