package ro.sd.a2.utils;

import ro.sd.a2.entity.Book;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class GeneratePDF implements Strategy {

    private static int countPDF = 1;

    @Override
    public void generateDocument(List<Book> books) {
        Document document = new Document();
        try {
            String name = "pdf_inventory" + countPDF + ".pdf";
            countPDF++;
            PdfWriter.getInstance(document, new FileOutputStream(name));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        document.open();

        PdfPTable table = new PdfPTable(7);
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(2);
        header.setPhrase(new Phrase("Id"));
        table.addCell(header);
        header.setPhrase(new Phrase("Title"));
        table.addCell(header);
        header.setPhrase(new Phrase("Author"));
        table.addCell(header);
        header.setPhrase(new Phrase("Description"));
        table.addCell(header);
        header.setPhrase(new Phrase("Price"));
        table.addCell(header);
        header.setPhrase(new Phrase("Promotion Price"));
        table.addCell(header);
        header.setPhrase(new Phrase("Genre"));
        table.addCell(header);

        for(Book x : books) {
            table.addCell(x.getId());
            table.addCell(x.getTitle());
            table.addCell(x.getAuthor());
            table.addCell(x.getDescription());
            table.addCell(x.getPrice() + "");
            if(x.getPromotionPrice() > 0) {
                table.addCell(x.getPromotionPrice() + "");
            }
            else{
                table.addCell("None");
            }
            table.addCell(x.getGenre().getType());
        }
        try {
            document.add(new Paragraph("Inventory:"));
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();

    }
}
