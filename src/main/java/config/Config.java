package config;

import org.springframework.context.annotation.*;
import screensaver.ColorFrame;

import java.awt.*;
import java.util.Random;

@Configuration
@ComponentScan(basePackages = "screensaver")
public class Config {

    @Bean
    @Scope(value = "prototype")
    public Color color(){
        Random random = new Random();
        return new Color(random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255));
    }

    @Bean
    public ColorFrame frame(){
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                return color();     // но это не вызов метода color, а ссылка на бин / при вызове метода будет создан новый бин
            }
        };
    }



    public static void main(String[] args) throws InterruptedException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        while (true){
            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(300);
        }

    }

}
