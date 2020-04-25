package ru.itis.servlets.services;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ru.itis.servlets.models.FileInfo;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
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

    @Resource(name = "mailSession")
    Session session;


    @Resource(name = "uploadsDir")
    String uploadsDir;

    @Override
    public boolean sendFileUrl(FileInfo fileInfo) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(fileInfo.getOwnerMail()));
            Template template = freemarkerConfig.getConfiguration().getTemplate("mail.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("fileInfo", fileInfo);
            Writer out = new StringWriter();
            template.process(model, out);
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(out.toString(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);

            bodyPart = new MimeBodyPart();
            String filename = uploadsDir + "/" + fileInfo.getStorageFileName();
            DataSource source = new FileDataSource(filename);
            bodyPart.setDataHandler(new DataHandler(source));
            bodyPart.setFileName(fileInfo.getOriginalFileName());
            multipart.addBodyPart(bodyPart);

            bodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource( servletContext.getRealPath("/WEB-INF/img/logo.png"));
            bodyPart.setDataHandler(new DataHandler(fds));
            bodyPart.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(bodyPart);


            message.setContent(multipart);
            Transport.send(message);
            return true;
        } catch (MessagingException | TemplateException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
