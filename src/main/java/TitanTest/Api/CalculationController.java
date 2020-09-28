package TitanTest.Api;

import TitanTest.Service.CalculationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class CalculationController {

    final private CalculationService calculationService;

    @PostMapping(value = "/count")
    public Flux<String> count(@RequestParam int times, @RequestParam int isAligned, @RequestParam String code1, @RequestParam String code2) {
        if (isAligned > 0) {
            return calculationService.aligned(code1, code2, times);
        } else {
            return calculationService.immediate(code1, code2, times);
        }
    }

}
