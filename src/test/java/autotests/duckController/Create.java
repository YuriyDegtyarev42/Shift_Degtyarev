package autotests.duckController;

import autotests.duckController.helper.duckControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class Create extends TestNGCitrusSpringSupport implements duckControllerHelper {

    @Test(description = "Проверка создания уточки с материалом rubber")
    @CitrusTest
    public void createDuckRubber(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1.1, "rubber", "quack", "ACTIVE");
        validateCreateResponse(runner, 200, "yellow", 1.1, "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Проверка создания уточки с материалом wood")
    @CitrusTest
    public void createDuckWood(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1.1, "wood", "quack", "ACTIVE");
        validateCreateResponse(runner, 200, "yellow", 1.1, "wood", "quack", "ACTIVE");
    }

}
