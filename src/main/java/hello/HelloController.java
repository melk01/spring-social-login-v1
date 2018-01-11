package hello;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public HelloController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping
    public String connect(Model model) {
        return "login";
    }

    @GetMapping("myURL")
    public String myCustomFlow(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/login";
        }

        model.addAttribute("authorized", facebook.isAuthorized());
        model.addAttribute("ageRange", facebook.userOperations().getUserProfile().getAgeRange());
        model.addAttribute("gender", facebook.userOperations().getUserProfile().getGender());

        return "hello";
    }

}
