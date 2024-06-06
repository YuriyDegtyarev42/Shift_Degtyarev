package autotests.duckController;

import autotests.clients.DuckControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class DeleteTest extends DuckControllerHelper {

    @Test(description = "Проверка удаления уточки")
    @CitrusTest
    public void deleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 5.6, "rubber", "quack", "ACTIVE");
        getID(runner);
        duckDelete(runner);
        validateActionResponse(runner, 200, "Duck is deleted");
        checkDeleteFromDatabase(runner);
    }

}
