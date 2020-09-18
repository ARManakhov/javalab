package dev.sirosh.documentConstructor;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import dev.sirosh.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

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
        PdfPage page = pdfDoc.getFirstPage();
        PdfDictionary dict = page.getPdfObject();

        PdfObject object = dict.get(PdfName.Contents);
        if (object instanceof PdfStream) {
            PdfStream stream = (PdfStream) object;
            byte[] data = stream.getBytes();
            stream.setData(new String(data).replace("{name}", user.getFirstName() + user.getLastName()).getBytes(StandardCharsets.UTF_8));
            stream.setData(new String(data).replace("{signature}", user.getFirstName() + user.getLastName()).getBytes(StandardCharsets.UTF_8));
        }

        pdfDoc.close();

    }
}
