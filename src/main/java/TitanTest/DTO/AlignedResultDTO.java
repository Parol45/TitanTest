package TitanTest.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlignedResultDTO {
    public Long iterNumber;
    public String funcResult1;
    public long calcTime1;
    public long calculatedAhead1;

    public String funcResult2;
    public long calcTime2;
    public long calculatedAhead2;

    @Override
    public String toString() {
        return String.format("%d, %s, %d ns, %d, %s, %d ns, %d%n",
                iterNumber, funcResult1, calcTime1, calculatedAhead1,
                funcResult2, calcTime2, calculatedAhead2);
    }

    public AlignedResultDTO(FunctionResult fr1, long calculatedAhead1, FunctionResult fr2, long calculatedAhead2) {
        this.iterNumber = fr1.iterNumber;
        this.funcResult1 = fr1.funcResult;
        this.calcTime1 = fr1.calcTime;
        this.calculatedAhead1 = calculatedAhead1;
        this.funcResult2 = fr2.funcResult;
        this.calcTime2 = fr2.calcTime;
        this.calculatedAhead2 = calculatedAhead2;
    }
}
