package com.example.tour.controller;

import com.example.tour.model.dto.BookingDTO;
import com.example.tour.model.dto.CustomersDTO;
import com.example.tour.model.dto.ToursDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.linkbuilder.ILinkBuilder;
import org.thymeleaf.spring5.context.webmvc.SpringWebMvcThymeleafRequestContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

@Controller
public class PdfController {

    private final TemplateEngine templateEngine;

    public PdfController(TemplateEngine templateEngine) {
        templateEngine.setLinkBuilder(linkBuilder());
        this.templateEngine = templateEngine;

    }
    private ILinkBuilder linkBuilder(){
        return new CustomLinkBuilder();
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
    @PostMapping("/export/pdf")
    public void exportToPdf(HttpServletResponse response,HttpServletRequest request,
                            @ModelAttribute("customersDTO")CustomersDTO customersDTO,@ModelAttribute("toursDTO")ToursDTO toursDTO,
                            @ModelAttribute("bookingDTO")BookingDTO bookingDTO) throws Exception {
        // Load the Thymeleaf template
        Context context = new Context();
        BindingAwareModelMap bindingMap = new BindingAwareModelMap();
        bindingMap.addAttribute("customersDTO", customersDTO);
        bindingMap.addAttribute("toursDTO", toursDTO);
        bindingMap.addAttribute("bookingDTO", bookingDTO);
        String html = renderToString(request, response, "bill", bindingMap);

//        context.setVariable("customersDTO",customersDTO);
////        context.setVariable("toursDTO",toursDTO);
////        context.setVariable("bookingDTO",bookingDTO);
//        // Add any required variables to the context
//        // context.setVariable("variableName", variableValue);
//
//        String htmlContent = templateEngine.process("bill", context);

        // Generate PDF from HTML using Flying Saucer
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.createPDF(outputStream);
        renderer.finishPDF();

        // Set response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"hoadon.pdf\"");

        // Write the PDF to the response
        OutputStream responseOutputStream = response.getOutputStream();
        outputStream.writeTo(responseOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
