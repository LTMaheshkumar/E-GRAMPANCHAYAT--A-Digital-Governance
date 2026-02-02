package com.egrampanchyat.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.egrampanchyat.entity.Payment;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Override
    public byte[] generateReceipt(Payment p) throws Exception {

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        // ================= FONTS =================
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

        // ================= HEADING =================
        String heading =
                p.getTaxType().name().equals("WATER_BILL")
                        ? "WATER TAX PAYMENT RECEIPT"
                        : "PROPERTY TAX PAYMENT RECEIPT";

        Paragraph title = new Paragraph(heading, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Gram Panchayat, Government of Maharashtra",
                normalFont));
        document.add(new Paragraph(" "));
        document.add(new LineSeparator());

        document.add(new Paragraph(" "));

        // ================= TABLE =================
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{3f, 5f});

        addRow(table, "Receipt No", String.valueOf(p.getId()), headerFont, normalFont);
        addRow(table, "Citizen Email", p.getUserEmail(), headerFont, normalFont);
        addRow(table, "Tax Type", p.getTaxType().name(), headerFont, normalFont);
        addRow(table, "Payment Method", p.getMethod(), headerFont, normalFont);
        addRow(table, "Amount Paid", "₹ " + p.getAmount(), headerFont, normalFont);
        addRow(table, "Payment Date",
                p.getCreatedAt().toLocalDate().toString(),
                headerFont, normalFont);

        document.add(table);

        document.add(new Paragraph(" "));
        document.add(new LineSeparator());
        document.add(new Paragraph(" "));

        // ================= FOOTER =================
        Paragraph footer = new Paragraph(
                "This is a system generated receipt.\nNo signature required.",
                normalFont);
        footer.setAlignment(Element.ALIGN_CENTER);

        document.add(footer);

        document.close();
        return out.toByteArray();
    }

    // ================= HELPER METHOD =================
    private void addRow(
            PdfPTable table,
            String key,
            String value,
            Font keyFont,
            Font valueFont) {

        PdfPCell cell1 = new PdfPCell(new Phrase(key, keyFont));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, valueFont));

        cell1.setPadding(8);
        cell2.setPadding(8);

        cell1.setBorder(Rectangle.BOX);
        cell2.setBorder(Rectangle.BOX);

        table.addCell(cell1);
        table.addCell(cell2);
    }
}
