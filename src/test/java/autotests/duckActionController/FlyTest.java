package autotests.duckActionController;

import autotests.clients.DuckControllerHelper;
import autotests.payloads.Duck;
import autotests.payloads.Message;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class FlyTest extends DuckControllerHelper {

    @Test(description = "Проверка успешного полета")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        getID(runner);
        fly(runner,"${duckId}");
        String message = "{\n" +
                "  \"message\": \"I am flying\"\n" +
                "}";
        validateDuckResponseResources(runner, 200, new Message().message(message));
    }

    @Test(description = "Проверка неуспешного полета")
    @CitrusTest
    public void unsuccessfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("rubber").sound("quack").wingsState(WingsState.FIXED);
        createDuck(runner, duck);
        getID(runner);
        fly(runner,"${duckId}");
        String string = "{\n" +
                "  \"message\": \"I can not fly\"\n" +
                "}";
        validateDuckResponseString(runner, 200, string);
    }

    @Test(description = "Проверка полета с неопределенным состоянием крыльев")
    @CitrusTest
    public void undefinedFly(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("rubber").sound("quack").wingsState(WingsState.UNDEFINED);
        createDuck(runner, duck);
        getID(runner);
        fly(runner,"${duckId}");
        validateDuckResponsePayload(runner, 200, "FlyResponses/UndefinedResponse.json");
    }

}
