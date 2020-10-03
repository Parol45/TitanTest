package TitanTest.Api;

import TitanTest.DTO.InputDTO;
import TitanTest.Service.CalculationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class CalculationController {

    final private CalculationService calculationService;

    @PostMapping(value = "/count")
    public Flux<String> count(@RequestBody InputDTO input) {
        if (input.getIsAligned() > 0) {
            return calculationService.aligned(input);
        } else {
            return calculationService.immediate(input);
        }
    }

}
