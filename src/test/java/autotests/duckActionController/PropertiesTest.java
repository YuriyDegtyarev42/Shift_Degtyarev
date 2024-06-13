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
@Feature("/api/duck/action/properties")
public class PropertiesTest extends DuckControllerHelper {

    @Test(description = "Проверка получения свойств уточки (четный id, материал wood)")
    @CitrusTest
    public void getDuckEvenNumberWoodProperties(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(4, false)1");
        clearDB(runner, "${duckId}");
        dbUpdate(runner, "insert into duck values (${duckId}, 'yellow', 1.1, 'wood', 'quack', 'ACTIVE')");

        getDuckProperties(runner,"${duckId}");

        validateDuckResponsePayload(runner, HttpStatus.OK, "PropertiesResponses/EvenNumberResponse.json");
    }

    @Test(description = "Проверка получения свойств уточки (нечетный id, материал rubber)")
    @CitrusTest
    public void getDuckOddNumberRubberProperties(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(4, false)2");
        clearDB(runner, "${duckId}");
        dbUpdate(runner, "insert into duck values (${duckId}, 'yellow', 1.1, 'rubber', 'quack', 'ACTIVE')");

        getDuckProperties(runner,"${duckId}");

        validateDuckResponseResources(runner, HttpStatus.OK, "PropertiesResponses/OddNumberResponse.json");
    }

}
