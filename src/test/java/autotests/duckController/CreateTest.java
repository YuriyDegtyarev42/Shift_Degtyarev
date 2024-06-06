package autotests.duckController;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckControllerHelper;


public class CreateTest extends DuckControllerHelper {

    @Test(description = "Проверка создания уточки с материалом rubber")
    @CitrusTest
    public void createDuckRubber(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1.1, "rubber", "quack", "ACTIVE");
        validateDuckResponse(runner, 200, "yellow", 1.1, "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Проверка создания уточки с материалом wood")
    @CitrusTest
    public void createDuckWood(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1.1, "wood", "quack", "ACTIVE");
        validateDuckResponse(runner, 200, "yellow", 1.1, "wood", "quack", "ACTIVE");
    }

}
