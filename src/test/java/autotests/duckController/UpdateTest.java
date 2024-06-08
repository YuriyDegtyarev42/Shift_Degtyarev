package autotests.duckController;

import autotests.clients.DuckControllerHelper;
import autotests.payloads.Duck;
import autotests.payloads.Message;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;


public class UpdateTest extends DuckControllerHelper {

    @Test(description = "Проверка обновления уточки (высота и цвет)")
    @CitrusTest
    public void updateDuckHeightColor(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        getID(runner);
        AtomicInteger id = new AtomicInteger(0);
        run(testContext -> id.set(Integer.parseInt(testContext.getVariable("duckId"))));
        updateDuck(runner, "red", "8.2", "${duckId}", "rubber", "quack", "ACTIVE");
        String message = String.format("Duck with id = %d is updated", id.get());
        validateDuckResponseResources(runner, 200, new Message().message(message));
    }

    @Test(description = "Проверка обновления уточки (звук и цвет)")
    @CitrusTest
    public void updateDuckSoundColor(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        getID(runner);
        AtomicInteger id = new AtomicInteger(0);
        run(testContext -> id.set(Integer.parseInt(testContext.getVariable("duckId"))));
        updateDuck(runner, "purple", "1.1", "${duckId}", "rubber", "bark", "ACTIVE");
        String message = String.format("Duck with id = %d is updated", id.get());
        validateDuckResponseString(runner, 200, "{\n" +
                "  \"message\": \"" + message + "\"\n" +
                "}");
    }

}
