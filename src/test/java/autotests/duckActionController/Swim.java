package autotests.duckActionController;

import autotests.duckActionController.helper.duckActionControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class Swim extends TestNGCitrusSpringSupport implements duckActionControllerHelper {
    @Test(description = "Проверка плавания у существующей уточки")
    @CitrusTest
    public void swimExistDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1.1, "rubber", "quack", "ACTIVE");
        getID(runner);
        swim(runner, "${duckId}");
        validateSwimResponse(runner, 200, "I'm swimming");
    }

    @Test(description = "Проверка плавания у несуществующей уточки")
    @CitrusTest
    public void swimNotExistDuck(@Optional @CitrusResource TestCaseRunner runner) {
        swim(runner, "4815");
        validateSwimResponse(runner, 404, "Paws are not found ((((");
    }

}
