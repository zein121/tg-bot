package students.javabot.Config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class AnecdoteConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String token;

}



