package TitanTest.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImmediateResultDTO extends FunctionResult{
    public int funcNumber;

    @Override
    public String toString() {
        return String.format("%d, %d, %s, %d ns%n", iterNumber, funcNumber, funcResult, calcTime);
    }

    public ImmediateResultDTO(Long iterNumber, int funcNumber, FunctionResult functionResult) {
        this.iterNumber = iterNumber;
        this.funcNumber = funcNumber;
        this.funcResult = functionResult.funcResult;
        this.calcTime = functionResult.calcTime;
    }
}
