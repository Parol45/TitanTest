package TitanTest.Service;

import TitanTest.Config.CalcProperties;
import TitanTest.DTO.AlignedResultDTO;
import TitanTest.DTO.FunctionResult;
import TitanTest.DTO.ImmediateResultDTO;
import TitanTest.DTO.InputDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CalculationService {

    private final Flux<Long> interval;

    public CalculationService(CalcProperties calcProperties) {
        this.interval = Flux.interval(Duration.ofSeconds(calcProperties.getPeriod()));
    }

    public Flux<String> immediate(InputDTO input) {
        int times = input.getTimes();
        Flux<String> firstCode = Flux.from(interval)
                .takeUntil((i) -> i > times - 2)
                .map((i) -> (new ImmediateResultDTO(i, 1, execJS(input.getCode1(), i))).toString());
        Flux<String> secondCode = Flux.from(interval)
                .takeUntil((i) -> i > times - 2)
                .map((i) -> (new ImmediateResultDTO(i, 2, execJS(input.getCode2(), i))).toString());
        return Flux.merge(firstCode, secondCode);
    }

    public Flux<String> aligned(InputDTO input) {
        int times = input.getTimes();
        AtomicInteger fr1Total = new AtomicInteger();
        AtomicInteger fr2Total = new AtomicInteger();
        Flux<FunctionResult> firstCode = Flux.from(interval)
                .takeUntil((i) -> i > times - 2)
                .doOnNext((i) -> fr1Total.getAndIncrement())
                .map((i) -> execJS(input.getCode1(), i));
        Flux<FunctionResult> secondCode = Flux.from(interval)
                .takeUntil((i) -> i > times - 2)
                .doOnNext((i) -> fr2Total.getAndIncrement())
                .map((i) -> execJS(input.getCode2(), i));
        return firstCode.zipWith(secondCode, (fr1, fr2) -> (new AlignedResultDTO(
                fr1, fr1Total.get() - fr2.iterNumber - 1,
                fr2, fr2Total.get() - fr1.iterNumber - 1)).toString());
    }

    private FunctionResult execJS(String code, Long iter) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        try {
            long t0 = System.nanoTime();
            engine.eval(code);
            Invocable invokable = (Invocable) engine;
            String funcResult = invokable.invokeFunction("func", iter).toString();
            long t1 = System.nanoTime();
            return new FunctionResult(iter, funcResult, t1 - t0);
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
            return new FunctionResult(iter, e.getMessage(), 0);
        }
    }
}
