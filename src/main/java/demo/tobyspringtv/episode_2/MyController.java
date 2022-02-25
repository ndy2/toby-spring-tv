package demo.tobyspringtv.episode_2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MyController {

    @RequestMapping("/")
    public List<User> users(){
        return Arrays.asList(new User("A"), new User("B"), new User("C"));
    }




    static class User {
        String name;

        public User(){
        }

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
