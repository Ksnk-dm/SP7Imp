package ua.kiev.prog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/static/");
    }

    @Bean
    public CommandLineRunner demo(final ContactService contactService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                Group group = new Group("Test");
                Task task;

                contactService.addGroup(group);


                for (int i = 0; i < 13; i++) {
                    task = new Task(null, "Name" + i, "Surname" + i, "description" + i, "date" + i, "066227277"+i);
                    contactService.addContact(task);
                }
                for (int i = 0; i < 10; i++) {
                    task = new Task(group, "Name" + i, "Surname" + i, "description" + i, "date" + i, "066227277"+i);
                    contactService.addContact(task);
                }
            }
        };
    }
}
