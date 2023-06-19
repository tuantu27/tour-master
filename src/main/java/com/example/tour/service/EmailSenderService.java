package com.example.tour.service;

import com.example.tour.model.dto.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.support.RequestContext;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.context.webmvc.SpringWebMvcThymeleafRequestContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    public void sendEmail(HttpServletRequest request, HttpServletResponse response,Mail mail, BindingAwareModelMap bindingMap) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
     //   helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
//        Context context = new Context();
//        context.setVariables(mail.getProps());

        String html = renderToString(request,response,"view_mail",bindingMap);
        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        emailSender.send(message);
    }
    @Autowired
    ServletContext servletContext;

    private String renderToString(HttpServletRequest request, HttpServletResponse response, String viewName, Map<String, Object> parameters) {
        Context templateContext = new Context();
        templateContext.setVariables(parameters);

        RequestContext requestContext = new RequestContext(request, response, servletContext, parameters);
        SpringWebMvcThymeleafRequestContext thymeleafRequestContext = new SpringWebMvcThymeleafRequestContext(requestContext, request);
        templateContext.setVariable("thymeleafRequestContext", thymeleafRequestContext);

        return templateEngine.process(viewName, templateContext);
    }
}
