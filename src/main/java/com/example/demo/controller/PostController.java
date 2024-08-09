import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("posts", postService.listAllPosts());
        return "postlist"; 
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "postshow"; // 
        } else {
            return "redirect:/posts/"; 
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "postform"; 
        } else {
            return "redirect:/posts/"; /
        }
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post()); 
        return "postform";
    }

    @PostMapping("/")
    public String savePost(@ModelAttribute Post post) {
        postService.savePost(post);
        return "redirect:/posts/" + post.getPostId(); 
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts/";
    }