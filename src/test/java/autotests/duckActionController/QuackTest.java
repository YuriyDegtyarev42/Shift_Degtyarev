package autotests.duckActionController;

import autotests.clients.DuckControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class QuackTest extends DuckControllerHelper {

    @Test(description = "Проверка того, что уточка с четным id крякает")
    @CitrusTest
    public void quackEvenNumberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckEvenNumber(runner, "yellow", 1.1, "wood", "quack", "ACTIVE");
        quack(runner, "${duckId}", "1", "1");
        validateQuackResponse(runner, 200, "quack");
    }

    @Test(description = "Проверка того, что уточка с нечетным id крякает")
    @CitrusTest
    public void quackOddNumberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckOddNumber(runner, "yellow", 1.1, "rubber", "quack", "ACTIVE");
        quack(runner, "${duckId}", "1", "1");
        validateQuackResponse(runner, 200, "quack");
    }
    
}
