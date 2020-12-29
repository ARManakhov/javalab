package dev.sirosh.poshlopoehalo.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.activation.URLDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    ServletContext servletContext;
    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;
    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendTokenMail(String token, String email) {
        try {

            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Template template = freemarkerConfig.getConfiguration().getTemplate("mail.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("token", token);
            Writer out = new StringWriter();
            template.process(model, out);

            helper.setTo(email);
            helper.setSubject("токен для входа");
            helper.setText(out.toString(),true);
            URLDataSource logo = new URLDataSource(this.getClass().getResource("/img/logo.png"));
            helper.addInline("logo.png", logo);
            emailSender.send(message);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}