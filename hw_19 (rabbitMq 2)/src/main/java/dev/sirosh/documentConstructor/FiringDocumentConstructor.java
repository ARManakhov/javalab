package dev.sirosh.documentConstructor;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import dev.sirosh.model.User;
import com.itextpdf.kernel.geom.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class FiringDocumentConstructor implements DocumentConstructor {

    File folder;
    String srcPath = "pdf_templates/firing.pdf";

    public FiringDocumentConstructor(File distFolder){
        folder = distFolder;
        folder.mkdirs();
    }
    @Override
    public void construct(User user, String filename) throws IOException {

        File file = new File (folder.getAbsolutePath() + File.separator + filename);
        File src = null;
        try {
            src = new File(getClass().getClassLoader().getResource(srcPath).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(file));

        Document document = new Document(pdfDoc);
        float documentHeight = pdfDoc.getDefaultPageSize().getHeight();

        document.add(new Paragraph(user.getLastName() + " " + user.getFirstName())
                .setFontSize(10).setFixedPosition(328.11F,documentHeight - 133.73F,50));

        document.add(new Paragraph(user.getLastName())
                .setFontSize(10).setFixedPosition(470,450,50));

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");

        document.add(new Paragraph(dateFormat.format(new Date()))
                .setFontSize(10).setFixedPosition(100,450,1000));

        pdfDoc.close();

    }
}
