package TitanTest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunctionResult {
    public Long iterNumber;
    public String funcResult;
    public long calcTime;
}
