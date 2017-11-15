package hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController{

// method to map the original greeting page, which returns Hello World by default, or a specific
// name if entered in the url as http://localhost:8080/greeting?name=Meghan
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}