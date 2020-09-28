package TitanTest.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "calculations")
public class CalcProperties {
    String period;
}
