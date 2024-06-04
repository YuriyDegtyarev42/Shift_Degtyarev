package autotests.duckController;

import autotests.duckController.helper.duckControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class Delete extends TestNGCitrusSpringSupport implements duckControllerHelper {

    @Test(description = "Проверка удаления уточки")
    @CitrusTest
    public void deleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 5.6, "rubber", "quack", "ACTIVE");
        getID(runner);
        duckDelete(runner);
        validateDeleteResponse(runner, 200, "Duck is deleted");
        checkDeleteFromDatabase(runner);
    }

}
