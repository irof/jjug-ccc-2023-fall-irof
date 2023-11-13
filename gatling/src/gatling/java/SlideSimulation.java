import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * スライドの最後（完全にマスターした）のシナリオ
 */
public class SlideSimulation extends Simulation {

    {
        setUp(scenario("しなりおだよ")
                .exec(http("ひとつめ").get("/slide/1"))
                .pause(1)
                .exec(http("ふたつめ").get("/slide/2"))
                .injectOpen(atOnceUsers(1))
        ).protocols(http.baseUrl("http://localhost:8080"));
    }
}
