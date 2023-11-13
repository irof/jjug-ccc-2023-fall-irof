package item;

import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.rampConcurrentUsers;
import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * Closed Injectionの練習用
 */
public class SimpleClosedSimulation extends Simulation {

    {
        setUp(
                scenario("Simple-Closed").exec(http("simple").get("/simple"))
                        .injectClosed(
                                // 同時利用ユーザー10人、10秒続く
                                constantConcurrentUsers(10).during(10),
                                // 同時利用ユーザー10人から20人に徐々に増えていく
                                rampConcurrentUsers(10).to(20).during(10)
                        )
        ).protocols(http.baseUrl("http://localhost:8080"));
    }
}
