package ru.itis.servlets.aspects;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import java.util.Optional;

@Aspect
@Component
public class MailServiceAspect {
    @Autowired
    ServletContext servletContext;
    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;

    @Resource(name = "mailSession")
    Session session;


    @Resource(name = "uploadsDir")
    String uploadsDir;

    @AfterReturning(value = "execution(* ru.itis.servlets.services.FileService.fileSave(..))", returning = "opFileInfo")
    public void sendFileUrl(JoinPoint joinPoint, Optional<FileInfo> opFileInfo) {
        if (opFileInfo.isPresent()) {
            try {
                FileInfo fileInfo = opFileInfo.get();
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
                DataSource fds = new FileDataSource(servletContext.getRealPath("/WEB-INF/img/logo.png"));
                bodyPart.setDataHandler(new DataHandler(fds));
                bodyPart.setHeader("Content-ID", "<image>");
                multipart.addBodyPart(bodyPart);


                message.setContent(multipart);
                Transport.send(message);
            } catch (MessagingException | TemplateException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
