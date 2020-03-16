package dev.sirosh.servlets.services;

import com.sun.mail.smtp.SMTPTransport;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import dev.sirosh.servlets.models.User;
import dev.sirosh.servlets.repositories.UserRepository;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Resource(name = "freemarkerConfig")
    private FreeMarkerConfigurer freemarkerConfig;

    @Resource(name = "mailSession")
    Session session;

    @Override
    public String register(User user) {

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getMail()));
            Template template = freemarkerConfig.getConfiguration().getTemplate("mail.ftl");
            Map<String,Object> model = new HashMap<>();
            model.put("user",user);
            Writer out = new StringWriter();
            template.process(model, out);
            BodyPart body = new MimeBodyPart();
            body.setContent(out.toString(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
            message.setContent(multipart);
            Transport.send(message);

        } catch (MessagingException | TemplateException | IOException e) {
            e.printStackTrace();
        }
        userRepository.save(user);
        return null;
    }
}
