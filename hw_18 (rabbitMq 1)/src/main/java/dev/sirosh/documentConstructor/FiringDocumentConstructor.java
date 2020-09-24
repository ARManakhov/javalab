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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Map;

public class FiringDocumentConstructor implements DocumentConstructor {

    File folder;
    String srcPath = "src_documents/firing.pdf";

    public FiringDocumentConstructor(File distFolder){
        folder = distFolder;
        folder.mkdirs();
    }
    @Override
    public void construct(User user, String filename) throws IOException {

        File file = new File (folder.getAbsolutePath() + File.separator + filename);
        File src = new File (srcPath);

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(file));

        Document document = new Document(pdfDoc);

        document.add(new Paragraph(user.getLastName())
                .setFontSize(8).setFixedPosition(10,10,10));

        pdfDoc.close();

    }
}
