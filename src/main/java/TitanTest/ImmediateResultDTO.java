package TitanTest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImmediateResultDTO {
    public Long iterNumber;
    public int funcNumber;
    public String funcResult;
    public long calcTime;

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
