package autotests.duckActionController;

import autotests.duckActionController.helper.duckActionControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class Fly extends TestNGCitrusSpringSupport implements duckActionControllerHelper {

    @Test(description = "Проверка успешного полета")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1.1, "rubber", "quack", "ACTIVE");
        getID(runner);
        fly(runner);
        validateFlyResponse(runner, 200, "I am flying :)");
    }

    @Test(description = "Проверка неуспешного полета")
    @CitrusTest
    public void unsuccessfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1.1, "rubber", "quack", "FIXED");
        getID(runner);
        fly(runner);
        validateFlyResponse(runner, 200, "I can not fly :C");
    }

    @Test(description = "Проверка полета с неопределенным состоянием крыльев")
    @CitrusTest
    public void undefinedFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1.1, "rubber", "quack", "UNDEFINED");
        getID(runner);
        fly(runner);
        validateFlyResponse(runner, 200, "Wings are not detected :(");
    }

}
