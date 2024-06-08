package autotests.duckController;

import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckControllerHelper;


public class CreateTest extends DuckControllerHelper {

    @Test(description = "Проверка создания уточки с материалом rubber")
    @CitrusTest
    public void createDuckRubber(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        String string = "{\n" +
                "  \"id\": \"@isNumber()@\",\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": 1.1,\n" +
                "  \"material\": \"rubber\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}";
        validateDuckResponseString(runner, 200, string);
    }

    @Test(description = "Проверка создания уточки с материалом wood")
    @CitrusTest
    public void createDuckWood(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("wood").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        Duck resource = new Duck().id("@isNumber()@").color("yellow").height(1.1).material("wood").sound("quack").wingsState(WingsState.ACTIVE);
        validateDuckResponseResources(runner, 200, resource);
    }

}
