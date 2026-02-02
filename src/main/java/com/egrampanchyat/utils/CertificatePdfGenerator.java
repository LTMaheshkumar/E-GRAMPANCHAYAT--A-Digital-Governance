package com.egrampanchyat.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.egrampanchyat.entity.CertificateApplication;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class CertificatePdfGenerator {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static ByteArrayInputStream generate(CertificateApplication app) {

        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            /* ================= BORDER ================= */
            PdfContentByte cb = writer.getDirectContent();
            Rectangle border = new Rectangle(30, 30, 565, 810);
            border.setBorder(Rectangle.BOX);
            border.setBorderWidth(2.5f);
            cb.rectangle(border);

            /* ================= FONTS ================= */
            Font headerFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 18);
            Font subHeaderFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
            Font labelFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
            Font valueFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
            Font footerFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);

            /* ================= HEADER ================= */
            Paragraph header = new Paragraph("GRAM PANCHAYAT CERTIFICATE", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph subHeader = new Paragraph(
                    "Issued by Local Self Government Authority",
                    subHeaderFont
            );
            subHeader.setAlignment(Element.ALIGN_CENTER);
            subHeader.setSpacingAfter(15);
            document.add(subHeader);

            /* ================= REF NO ================= */
            Paragraph ref = new Paragraph(
                    "Certificate Reference No : " + safe(app.getCertificateRefNo()),
                    labelFont
            );
            ref.setSpacingAfter(10);
            document.add(ref);

            /* ================= PHOTO ================= */
            PdfPTable photoTable = new PdfPTable(1);
            photoTable.setWidthPercentage(30);
            photoTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell photoCell = new PdfPCell();
            photoCell.setFixedHeight(95);
            photoCell.setBorder(Rectangle.BOX);
            photoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            photoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            if (app.getPhotoPath() != null) {
                File photoFile = new File(app.getPhotoPath());
                if (photoFile.exists()) {
                    Image photo = Image.getInstance(photoFile.getAbsolutePath());
                    photo.scaleToFit(90, 100);
                    photoCell.addElement(photo);
                } else {
                    photoCell.addElement(new Paragraph("Photo", valueFont));
                }
            } else {
                photoCell.addElement(new Paragraph("Photo", valueFont));
            }

            photoTable.addCell(photoCell);
            document.add(photoTable);
            document.add(Chunk.NEWLINE);

            /* ================= DETAILS ================= */
            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.setWidthPercentage(100);
            detailsTable.setWidths(new int[]{40, 60});

            addRow(detailsTable, "Applicant Name",
                    safe(app.getApplicantName()), labelFont, valueFont);

            addRow(detailsTable, "Date of Birth",
                    formatDate(app.getDateOfBirth()), labelFont, valueFont);

            addRow(detailsTable, "Certificate Type",
                    safe(app.getCertificateType() != null
                            ? app.getCertificateType().toString()
                            : null),
                    labelFont, valueFont);

            addRow(detailsTable, "Issue Date",
                    formatDateTime(app.getApprovedAt()), labelFont, valueFont);

            addRow(detailsTable, "Valid Till",
                    formatDate(app.getExpiryDate()), labelFont, valueFont);

            document.add(detailsTable);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            /* ================= CERTIFICATION TEXT ================= */
            document.add(new Paragraph(
                    "This is to certify that the above information has been verified "
                            + "from official records and is found to be correct. "
                            + "This certificate is issued on request of the applicant "
                            + "for official purposes only.",
                    valueFont
            ));

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            /* ================= SIGNATURE ================= */
            PdfPTable signTable = new PdfPTable(2);
            signTable.setWidthPercentage(100);

            PdfPCell left = new PdfPCell(new Paragraph(""));
            left.setBorder(Rectangle.NO_BORDER);

            PdfPCell right = new PdfPCell();
            right.setBorder(Rectangle.NO_BORDER);
            right.setHorizontalAlignment(Element.ALIGN_RIGHT);

            File signImg = new File("src/main/resources/static/signature.png");
            if (signImg.exists()) {
                Image sign = Image.getInstance(signImg.getAbsolutePath());
                sign.scaleToFit(120, 50);
                right.addElement(sign);
            }

            right.addElement(new Paragraph(
                    "Panchayat Officer\nAuthorized Signatory",
                    labelFont
            ));

            signTable.addCell(left);
            signTable.addCell(right);
            document.add(signTable);

            document.add(Chunk.NEWLINE);

            /* ================= FOOTER ================= */
            Paragraph footer = new Paragraph(
                    "This is a computer generated certificate.",
                    footerFont
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating certificate PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    /* ================= SAFE HELPERS ================= */

    private static String safe(String value) {
        return value != null ? value : "N/A";
    }

    private static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : "N/A";
    }

    private static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null
                ? dateTime.toLocalDate().format(DATE_FORMAT)
                : "N/A";
    }

    private static void addRow(
            PdfPTable table,
            String label,
            String value,
            Font labelFont,
            Font valueFont) {

        PdfPCell c1 = new PdfPCell(new Paragraph(label, labelFont));
        PdfPCell c2 = new PdfPCell(new Paragraph(value, valueFont));
        c1.setPadding(6);
        c2.setPadding(6);
        table.addCell(c1);
        table.addCell(c2);
    }
}
