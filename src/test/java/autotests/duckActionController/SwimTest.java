package autotests.duckActionController;

import autotests.clients.DuckControllerHelper;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import com.consol.citrus.context.TestContext;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class SwimTest extends DuckControllerHelper {
    @Test(description = "Проверка плавания у существующей уточки")
    @CitrusTest
    public void swimExistDuck(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(1.1)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        getID(runner);
        swim(runner, "${duckId}");

        validateDuckResponseResources(runner, HttpStatus.OK, "SwimResponses/ExistSwimResponse.json");
    }

    @Test(description = "Проверка плавания у несуществующей уточки")
    @CitrusTest
    public void swimNotExistDuck(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        Duck duck = new Duck()
                .color("yellow")
                .height(1.1)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        getID(runner);
        int idDuckNotExist = Integer.parseInt(context.getVariable("duckId")) + 1;
        swim(runner, String.valueOf(idDuckNotExist));

        validateDuckResponseString(runner, HttpStatus.NOT_FOUND, "Duck with id = " + idDuckNotExist + " is not found");
    }

}
