package autotests.duckController;

import autotests.clients.DuckControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Duck controller")
@Feature("/api/duck/delete")
public class DeleteTest extends DuckControllerHelper {

    @Test(description = "Проверка удаления уточки")
    @CitrusTest
    public void deleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(4, false)");
        clearDB(runner, "${duckId}");
        dbUpdate(runner, "insert into duck values (${duckId}, 'yellow', 5.6, 'wood', 'quack', 'ACTIVE')");

        duckDelete(runner, "${duckId}");

        validateDuckResponseResources(runner, HttpStatus.OK, "DeleteResponses/DeleteResponse.json");
        dbSQLQueryValidate(runner, "select count(*) as ducks_count from duck where id = ${duckId}", "ducks_count", "0");
    }

}
