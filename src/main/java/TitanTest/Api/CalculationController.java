package TitanTest.Api;

import TitanTest.Service.CalculationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class CalculationController {

    final private CalculationService calculationService;

    @PostMapping(value = "/count")
    public Flux<String> count(@RequestParam int times, @RequestParam int isAligned) {
        if (isAligned > 0) {
            return calculationService.aligned("var x = 45;var z = 2135;var y = 21350000*inp;while (y > 0) {y = y - 1;}x + z + inp;",
                    "var x = 45;var z = 2135;var y = 0;while (y > 0) {y = y - 1;}x + z + inp;", times);
        } else {
            return calculationService.immediate("var x = 45;var z = 2135;var y = 21350000*inp;while (y > 0) {y = y - 1;}x + z + inp;",
                    "var x = 45;var z = 2135;var y = 0;while (y > 0) {y = y - 1;}x + z + inp;", times);
        }
    }

}
