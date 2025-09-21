package edu.umc.access_control.Auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.umc.access_control.User.UserService;
import edu.umc.access_control.payloads.UserRegistrationDTO;

@Controller("/")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Displays the user registration form.
   * 
   * @param model The Spring Model to pass data to the view.
   * @return The name of the Thymeleaf template to render.
   */
  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    // We add an empty RegisterRequest object to the model.
    // This allows the Thymeleaf form to bind its fields to this object.
    model.addAttribute("user", new UserRegistrationDTO());
    return "register"; // This should correspond to a file named "register.html" in your templates
                       // folder.
  }

  /**
   * Processes the registration form submission.
   * 
   * @param registerRequest    The object populated with form data.
   * @param bindingResult      Contains the results of the validation.
   * @param model              The Spring Model for adding attributes.
   * @param redirectAttributes For adding attributes to the redirect.
   * @return The name of the view or a redirect path.
   */
  @PostMapping("/register")
  public String registerUser(@ModelAttribute("user") UserRegistrationDTO registerRequest,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes) {

    // 1. Check for validation errors from @Valid (e.g., blank fields, invalid email
    // format)
    if (bindingResult.hasErrors()) {
      // If there are errors, we return to the same registration page.
      // Thymeleaf will be able to access the bindingResult object to display the
      // errors.
      System.out.println(bindingResult.getAllErrors());
      return "register";
    }

    // 2. Handle business logic errors (e.g., username or email already exists)
    try {
      userService.registerUser(
          registerRequest.getUsername(),
          registerRequest.getEmail(),
          registerRequest.getPassword(),
          registerRequest.getRole());
      System.out.println("User registered successfully");
    } catch (IllegalStateException e) {
      // If the user service throws an error, we add the error message to the model
      // and return to the registration page to display it.
      model.addAttribute("errorMessage", e.getMessage());
      System.out.println(e.getMessage());
      return "register";
    }

    // 3. Handle success
    // Use RedirectAttributes to add a "flash attribute". This attribute survives
    // the redirect and is available on the redirected-to page.
    redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");

    // Redirect to the login page to prevent duplicate form submissions on refresh.
    // This is the Post-Redirect-Get pattern.
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String showLoginForm() {
    return "login"; // This should correspond to a file named "login.html" in your templates folder.
  }

}
