package ua.kiev.prog;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class MyController {
    static final int DEFAULT_GROUP_ID = -1;
    static final int ITEMS_PER_PAGE = 6;

    //@Autowired
    private final ContactService contactService;

    public MyController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(required = false,
                                defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        List<Task> contacts = contactService
                .findAll(PageRequest.of(page,
                        ITEMS_PER_PAGE,
                        Sort.Direction.DESC, "id"));
        model.addAttribute("groups", contactService.findGroups());
        model.addAttribute("contacts", contacts);
        model.addAttribute("allPages", getPageCount());

        return "index";
    }

    @RequestMapping("/reset")
    public String reset() {
        contactService.reset();
        return "redirect:/";
    }

    @RequestMapping("/contact_add_page")
    public String contactAddPage(Model model) {
        model.addAttribute("groups", contactService.findGroups());
        return "task_add_page";
    }

    @RequestMapping("/group_add_page")
    public String groupAddPage() {
        return "group_add_page";
    }

    @RequestMapping("/group/{id}")
    public String listGroup(
            @PathVariable(value = "id") long groupId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model)
    {
        Group group = (groupId != DEFAULT_GROUP_ID) ? contactService.findGroup(groupId) : null;
        if (page < 0) page = 0;

        List<Task> contacts = contactService
                .findByGroup(group, PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));
        model.addAttribute("groups", contactService.findGroups());
        model.addAttribute("contacts", contacts);
        model.addAttribute("byGroupPages", getPageCount(group));
        model.addAttribute("groupId", groupId);
        return "index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("groups", contactService.findGroups());
        model.addAttribute("contacts", contactService.findByPattern(pattern, null));
        return "index";
    }

    @RequestMapping(value = "/contact/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> delete(@RequestParam(value = "toDelete[]", required = false)
                                                   long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            contactService.deleteContacts(toDelete);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/contact/add", method = RequestMethod.POST)
    public String contactAdd(@RequestParam(value = "group") long taskId,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String description,
                             @RequestParam String date,
                             @RequestParam String phone
                             )
    {
        Group group = (taskId != DEFAULT_GROUP_ID) ? contactService.findGroup(taskId) : null;
        Status status= saveSender(date, phone, description);
        Task contact = new Task(group, name, surname, description, date,phone,status.getStatus());
        contactService.addContact(contact);
        return "redirect:/";
    }

    @RequestMapping(value="/group/add", method = RequestMethod.POST)
    public String groupAdd(@RequestParam String name) {
        contactService.addGroup(new Group(name));
        return "redirect:/";
    }

    private long getPageCount() {
        long totalCount = contactService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCount(Group group) {
        long totalCount = contactService.countByGroup(group);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private Status saveSender(String startTime, String number, String text) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.turbosms.ua/message/send.json";
        String json = "{\n" +
                "  \"start_time\": \"" + startTime + "\",\n" +
                "   \"recipients\":[\n" +
                "      \"" + number + "\"\n" +
                "   ],\n" +
                "   \"sms\":{\n" +
                "      \"sender\": \"TAXI\",\n" +
                "      \"text\":\"" + text + "\"\n" +
                "   }\n" +
                "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + "e736b42e815a8635ef3270b26f429cb69634d7c0");
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
        Status answer = restTemplate.postForObject(url, entity, Status.class);
        System.out.println(json);
        return answer;
    }


}
