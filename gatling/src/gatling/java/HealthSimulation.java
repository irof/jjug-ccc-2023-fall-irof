import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * ヘルスチェックのシミュレーション
 *
 * 「2つのシナリオを同時に流す」ができるサンプルでもあります。
 * 「Tomcatのリクエストスレッドが占有されているとヘルスチェックが遅延する（遅れる）」が起こります。
 */
public class HealthSimulation extends Simulation {

    {
        int duration = Integer.getInteger("time", 60);

        setUp(
                scenario("ヘルスチェク")
                        .exec(http("health").get("/actuator/health"))
                        .pause(5)
                        .injectClosed(constantConcurrentUsers(1).during(duration)),
                scenario("なんらかの処理")
                        // 1秒かかる処理を
                        .exec(http("long time process").get("/sleep").queryParam("ms", "1500"))
                        // 25人が使い続ける
                        .injectClosed(constantConcurrentUsers(25).during(duration))
        ).protocols(http.baseUrl("http://localhost:8080"));
    }
}
