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
}
