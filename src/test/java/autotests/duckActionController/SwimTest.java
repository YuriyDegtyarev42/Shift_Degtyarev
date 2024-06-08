package autotests.duckActionController;

import autotests.clients.DuckControllerHelper;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class SwimTest extends DuckControllerHelper {
    @Test(description = "Проверка плавания у существующей уточки")
    @CitrusTest
    public void swimExistDuck(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        getID(runner);
        swim(runner, "${duckId}");
        validateDuckResponsePayload(runner, 200, "SwimResponses/ExistSwimResponse.json");
    }

    @Test(description = "Проверка плавания у несуществующей уточки")
    @CitrusTest
    public void swimNotExistDuck(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        getID(runner);
        AtomicInteger idDuckNotExist = new AtomicInteger(0);
        run(testContext -> idDuckNotExist.set(Integer.parseInt(testContext.getVariable("duckId")) + 80));
        swim(runner, idDuckNotExist.toString());
        String string = "{\n" +
                "  \"timestamp\": \"@ignore()@\",\n" +
                "  \"status\": 404,\n" +
                "  \"error\": \"Duck not found\",\n" +
                "  \"message\": \"Duck with id = " + idDuckNotExist + " is not found\",\n" +
                "  \"path\": \"/api/duck/action/fly\"\n" +
                "}";
        validateDuckResponseString(runner, 404, string);
    }

}
