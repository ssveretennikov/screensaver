package config;

import org.springframework.context.annotation.*;
import screensaver.ColorFrame;

import java.awt.*;
import java.util.Random;

@Configuration
@ComponentScan(basePackages = "screensaver")
public class Config {

    @Bean
    @Scope("prototype")
    public Color color(){

        Random random = new Random();
        return new Color(random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255));

    }

    public static void main(String[] args) throws InterruptedException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        while (true){
            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(50);
        }

    }

}
