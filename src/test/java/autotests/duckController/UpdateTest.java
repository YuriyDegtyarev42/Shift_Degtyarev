package autotests.duckController;

import autotests.clients.DuckControllerHelper;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import com.consol.citrus.context.TestContext;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Duck controller")
@Feature("/api/duck/update")
public class UpdateTest extends DuckControllerHelper {

    @Test(description = "Проверка обновления уточки (высота и цвет)")
    @CitrusTest
    public void updateDuckHeightColor(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(4, false)");
        clearDB(runner);
        databaseUpdate(runner, "insert into duck values (${duckId}, 'yellow', 1.1, 'rubber', 'quack', 'ACTIVE')");

        updateDuckByRequest(runner, "purple", 1.4, "${duckId}", "rubber", "quack", WingsState.ACTIVE);

        validateDuckResponseString(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Duck with id = ${duckId} is updated\"\n" +
                "}");
        validateDBDuck(runner, "${duckId}", "purple", 1.4, "rubber", "quack", WingsState.ACTIVE);
    }

    @Test(description = "Проверка обновления уточки (звук и цвет)")
    @CitrusTest
    public void updateDuckSoundColor(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        runner.variable("duckId", "citrus:randomNumber(4, false)");
        clearDB(runner);
        databaseUpdate(runner, "insert into duck values (${duckId}, 'yellow', 1.1, 'rubber', 'quack', 'ACTIVE')");

        updateDuckByRequest(runner, "purple", 1.1, "${duckId}", "rubber", "bark", WingsState.ACTIVE);

        validateDuckResponseString(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Duck with id = ${duckId} is updated\"\n" +
                "}");
        validateDBDuck(runner, "${duckId}", "purple", 1.1, "rubber", "bark", WingsState.ACTIVE);
    }

}
