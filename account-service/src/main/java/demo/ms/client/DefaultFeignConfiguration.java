package demo.ms.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class DefaultFeignConfiguration {

	@Bean
    public Logger.Level feignLogger() {
        return Logger.Level.BASIC;
    }
	
	/*@Bean
	public Retryer retryer() {
		Default retryer = new Retryer.Default();
		return retryer;
	}*/
}
