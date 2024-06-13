package autotests.duckActionController;

import autotests.clients.DuckControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import com.consol.citrus.context.TestContext;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Duck action controller")
@Feature("/api/duck/action/swim")
public class SwimTest extends DuckControllerHelper {
    @Test(description = "Проверка плавания у существующей уточки")
    @CitrusTest
    public void swimExistDuck(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(4, false)");
        clearDB(runner, "${duckId}");
        dbUpdate(runner, "insert into duck values (${duckId}, 'yellow', 1.1, 'rubber', 'quack', 'ACTIVE')");

        swim(runner, "${duckId}");

        validateDuckResponseResources(runner, HttpStatus.OK, "SwimResponses/ExistSwimResponse.json");
    }

    @Test(description = "Проверка плавания у несуществующей уточки")
    @CitrusTest
    public void swimNotExistDuck(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        swim(runner, String.valueOf(481516234));

        validateDuckResponseResources(runner, HttpStatus.NOT_FOUND, "SwimResponses/NotExistSwimResponse.json");
    }

}
