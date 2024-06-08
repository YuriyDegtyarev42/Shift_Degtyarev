package autotests.duckActionController;

import autotests.clients.DuckControllerHelper;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class QuackTest extends DuckControllerHelper {

    @Test(description = "Проверка того, что уточка с четным id крякает")
    @CitrusTest
    public void quackEvenNumberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("wood").sound("quack").wingsState(WingsState.ACTIVE);
        createDuckEvenNumber(runner, duck);
        quack(runner, "${duckId}", "2", "2");
        validateDuckResponsePayload(runner, 200, "QuackResponses/EvenNumberQuackResponse.json");
    }

    @Test(description = "Проверка того, что уточка с нечетным id крякает")
    @CitrusTest
    public void quackOddNumberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.1).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuckOddNumber(runner, duck);
        quack(runner, "${duckId}", "2", "2");
        String string = "{\n" +
                "  \"sound\": \"quack\"\n" +
                "}";
        validateDuckResponseString(runner, 200, string);
    }
    
}
