package TitanTest;

import TitanTest.Config.CalcProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/*
function func(inp) {
var y = 21350000*inp;
while (y > 0) {y = y - 1;}
return (x + z + inp);
}
*/


@SpringBootApplication
@EnableConfigurationProperties(CalcProperties.class)
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
    
}
