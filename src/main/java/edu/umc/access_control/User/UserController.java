package edu.umc.access_control.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

  @GetMapping
  public String getWord(Model model) {
    model.addAttribute("word", "user");
    return "word";
  }

  @GetMapping("/home")
  public String home() {
    return "home";
  }

  @GetMapping("/employees")
  public String employees() {
    return "employees";
  }

  @GetMapping("/admin")
  public String admin() {
    return "admin";
  }

  @GetMapping("/leader")
  public String leader() {
    return "leader";
  }
}
