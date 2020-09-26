package TitanTest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class FirstController {

    private final Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

    @PostMapping(value = "/count", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<String> count(@RequestParam int times, @RequestParam int isAligned) {
        if (isAligned > 0) {
            return aligned("var x = 45;var z = 2135;var y = 21350000*inp;while (y > 0) {y = y - 1;}x + z + inp;",
                    "var x = 45;var z = 2135;var y = 0;while (y > 0) {y = y - 1;}x + z + inp;", times);
        } else {
            return immediate("var x = 45;var z = 2135;var y = 21350000*inp;while (y > 0) {y = y - 1;}x + z + inp;",
                    "var x = 45;var z = 2135;var y = 0;while (y > 0) {y = y - 1;}x + z + inp;", times);
        }
    }

    //delaySubscription

    public Flux<String> immediate(String code1, String code2, int times) {
        Flux<String> firstCode = Flux.from(interval)
                .takeUntil((i) -> i > times - 2)
                .map((i) -> (new ImmediateResultDTO(i, 1, execJS(code1, i))).toString());
        Flux<String> secondCode = Flux.from(interval)
                .takeUntil((i) -> i > times - 2)
                .map((i) -> (new ImmediateResultDTO(i, 2, execJS(code2, i))).toString());
        return Flux.merge(firstCode, secondCode);
    }

    public Flux<String> aligned(String code1, String code2, int times) {
        AtomicInteger fr1Total = new AtomicInteger(0);
        AtomicInteger fr2Total = new AtomicInteger(0);
        Flux<FunctionResult> firstCode = Flux.from(interval)
                .takeUntil((i) -> i > times - 2)
                .doOnNext((i) -> fr1Total.getAndIncrement())
                .map((i) -> execJS(code1, i));
        Flux<FunctionResult> secondCode = Flux.from(interval)
                .takeUntil((i) -> i > times - 2)
                .doOnNext((i) -> fr2Total.getAndIncrement())
                .map((i) -> execJS(code2, i));
        return firstCode.zipWith(secondCode, (fr1, fr2) -> (new AlignedResultDTO(
                fr1, fr1Total.get() - fr2.iterNumber - 1,
                fr2, fr2Total.get() - fr1.iterNumber - 1)).toString());
    }

    public FunctionResult execJS(String code, Long iter) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            long t0 = System.nanoTime();
            engine.eval("var inp = " + iter + ";");
            String funcResult = engine.eval(code).toString();
            long t1 = System.nanoTime();
            return new FunctionResult(iter, funcResult, t1 - t0);
        } catch (ScriptException e) {
            e.printStackTrace();
            return new FunctionResult(iter, e.getMessage(), 0);
        }
    }
}
