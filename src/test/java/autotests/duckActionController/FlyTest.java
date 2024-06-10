package autotests.duckActionController;

import autotests.clients.DuckControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Duck action controller")
@Feature("/api/duck/action/fly")
public class FlyTest extends DuckControllerHelper {

    @Test(description = "Проверка успешного полета")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(4, false)");
        clearDB(runner, "${duckId}");
        dbUpdate(runner, "insert into duck values (${duckId}, 'yellow', 1.1, 'rubber', 'quack', 'ACTIVE')");

        fly(runner, "${duckId}");

        validateDuckResponseResources(runner, HttpStatus.OK, "FlyResponses/ActiveResponse.json");
    }

    @Test(description = "Проверка неуспешного полета")
    @CitrusTest
    public void unsuccessfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(4, false)");
        clearDB(runner, "${duckId}");
        dbUpdate(runner, "insert into duck values (${duckId}, 'yellow', 1.1, 'rubber', 'quack', 'FIXED')");

        fly(runner,"${duckId}");

        validateDuckResponseString(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"I can not fly\"\n" +
                "}");
    }

    @Test(description = "Проверка полета с неопределенным состоянием крыльев")
    @CitrusTest
    public void undefinedFly(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(4, false)");
        clearDB(runner, "${duckId}");
        dbUpdate(runner, "insert into duck values (${duckId}, 'yellow', 1.1, 'rubber', 'quack', 'UNDEFINED')");

        fly(runner,"${duckId}");

        validateDuckResponseResources(runner, HttpStatus.OK, "FlyResponses/UndefinedResponse.json");
    }

}
