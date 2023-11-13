import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * 呼び出しパターンを選べるシミュレーション
 *
 * 同じインジェクションで別APIを見比べるデモのために作りました。
 * 「APIの動きでこんな差がでるんだ」と感じると良いと思います。
 *
 * db,externalはボトルネックに引っ掛かります。
 * ボトルネックを見つける、解消するの練習に。
 *
 * <code>./gradlew gatlingRun-PatternSimulation -Dpattern=db</code>
 */
public class PatternSimulation extends Simulation {

    {
        String pattern = System.getProperty("pattern", "simple");

        var scn = switch (pattern) {
            case "simple" -> scenario(pattern).exec(http("simple").get("/simple"));
            case "db" -> scenario(pattern).exec(http("transaction").get("/transaction"));
            case "external" -> scenario(pattern).exec(http("external").get("/external"));
            case "sleep" -> scenario(pattern).exec(http("sleep").get("/sleep").queryParam("ms", "100"));
            default -> throw new IllegalStateException("Unexpected value: " + pattern);
        };

        setUp(
                scn.injectClosed(rampConcurrentUsers(10).to(30).during(60))
        ).protocols(http.baseUrl("http://localhost:8080"));
    }
}
