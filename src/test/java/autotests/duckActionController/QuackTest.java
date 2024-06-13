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
@Feature("/api/duck/action/quack")
public class QuackTest extends DuckControllerHelper {

    @Test(description = "Проверка того, что уточка с четным id крякает")
    @CitrusTest
    public void quackEvenNumberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(4, false)2");
        clearDB(runner, "${duckId}");
        dbUpdate(runner, "insert into duck values (${duckId}, 'yellow', 1.1, 'rubber', 'quack', 'ACTIVE')");

        quack(runner, "${duckId}", "1", "1");

        validateDuckResponseResources(runner, HttpStatus.OK, "QuackResponses/EvenNumberQuackResponse.json");
    }

    @Test(description = "Проверка того, что уточка с нечетным id крякает")
    @CitrusTest
    public void quackOddNumberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(4, false)1");
        clearDB(runner, "${duckId}");
        dbUpdate(runner, "insert into duck values (${duckId}, 'yellow', 1.1, 'rubber', 'quack', 'ACTIVE')");

        quack(runner, "${duckId}", "1", "1");

        validateDuckResponseString(runner, HttpStatus.OK, "{\n" +
                "  \"sound\": \"quack\"\n" +
                "}");
    }
    
}
