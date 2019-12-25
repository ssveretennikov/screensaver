package screensaver;

import javafx.util.Pair;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalTime.now;

@Component
public class PeriodicalScopeConfigurer implements Scope {

    Map<String, Pair<LocalTime, Object>> map = new HashMap<>();

    public Object get(String name, ObjectFactory<?> objectFactory) {

        // spring будет делегировать свою работу в этот метод
        // а тут уже сами решаем, создаем и возвращаем новый бин или из кеша

        if (map.containsKey(name)){
            Pair<LocalTime, Object> pair = map.get(name);
            // если объект создан давно, то создаем новый, заменяем в этой map и возвращаем уже его
            int secondsSinceLastRequest = now().getSecond() - pair.getKey().getSecond();
            if (secondsSinceLastRequest > 3){
                map.put(name, new Pair(now(), objectFactory.getObject()));
            }
        } else {
            map.put(name, new Pair(now(), objectFactory.getObject()));
        }

        return map.get(name).getValue();

    }

    public Object remove(String s) {
        return null;
    }

    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    public Object resolveContextualObject(String s) {
        return null;
    }

    public String getConversationId() {
        return null;
    }

}

// теперь этот класс надо зарегистрировать
// при чем не как обычно, ведь это же кастомная аннотация scope
// имплементим BeanFactoryPostProcessor и регистрируем наш бин там
