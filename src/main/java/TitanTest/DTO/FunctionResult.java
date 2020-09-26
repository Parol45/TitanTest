package TitanTest.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResult {
    public Long iterNumber;
    public String funcResult;
    public long calcTime;
}
