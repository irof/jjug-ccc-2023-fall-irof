package dev.irof;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {

    private final Tracer tracer;

    public MyService(Tracer tracer) {
        this.tracer = tracer;
    }

    @Transactional
    public void transactional() throws Exception {
        // Zipkinで見るためにDBアクセスを模したSpanを作っています。
        // 実際このようなSpanをServiceなどで個別に作成するのではなく、AOPやDBアクセスライブラリの拡張で対応するものだと思います。
        Span databaseSpan = this.tracer.nextSpan().name("database access");
        try (AutoCloseable ac = databaseSpan::end;
             Tracer.SpanInScope ws = this.tracer.withSpan(databaseSpan.start())) {
            // 100msかかるDBアクセス（のつもり）
            // H2だと100msかけるのも難しいのでsleepでブロックをエミュレーションしてます。
            Thread.sleep(100);
        }
    }
}
