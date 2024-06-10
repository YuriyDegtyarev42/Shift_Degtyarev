package autotests.duckController;

import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckControllerHelper;


public class CreateTest extends DuckControllerHelper {

    @Test(description = "Проверка создания уточки с материалом rubber")
    @CitrusTest
    public void createDuckRubber(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(1.1)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        String expectedResponse = "{\n" +
                "  \"id\": \"@isNumber()@\",\n" +
                "  \"color\": \"" + duck.color() + "\",\n" +
                "  \"height\": " + duck.height() + ",\n" +
                "  \"material\": \"" + duck.material() + "\",\n" +
                "  \"sound\": \"" + duck.sound() + "\",\n" +
                "  \"wingsState\": \"" + duck.wingsState() + "\"\n" +
                "}";

        validateDuckResponseString(runner, HttpStatus.OK, expectedResponse);
    }

    @Test(description = "Проверка создания уточки с материалом wood")
    @CitrusTest
    public void createDuckWood(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(1.1)
                .material("wood")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        Duck resource = new Duck()
                .id("@isNumber()@")
                .color("yellow")
                .height(1.1)
                .material("wood")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        validateDuckResponsePayload(runner, HttpStatus.OK, resource);
    }

}
