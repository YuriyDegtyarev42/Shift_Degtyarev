package autotests.duckController;

import autotests.clients.DuckControllerHelper;
import autotests.payloads.Duck;
import autotests.payloads.Message;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import com.consol.citrus.context.TestContext;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class UpdateTest extends DuckControllerHelper {

    @Test(description = "Проверка обновления уточки (высота и цвет)")
    @CitrusTest
    public void updateDuckHeightColor(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        Duck duck = new Duck()
                .color("yellow")
                .height(1.1)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        getID(runner);
        int id = Integer.parseInt(context.getVariable("duckId"));
        updateDuck(runner, "red", "8.2", "${duckId}", "rubber", "quack", "ACTIVE");

        validateDuckResponsePayload(runner, HttpStatus.OK, new Message().message("Duck with id = " + id + " is updated"));
    }

    @Test(description = "Проверка обновления уточки (звук и цвет)")
    @CitrusTest
    public void updateDuckSoundColor(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        Duck duck = new Duck()
                .color("yellow")
                .height(1.1)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        getID(runner);
        int id = Integer.parseInt(context.getVariable("duckId"));
        updateDuck(runner, "purple", "1.1", "${duckId}", "rubber", "bark", "ACTIVE");

        validateDuckResponseString(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Duck with id = " + id + " is updated\"\n" +
                "}");
    }

}
