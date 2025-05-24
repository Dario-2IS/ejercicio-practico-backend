package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Movement;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfReportservice {
    public ByteArrayInputStream generateTransactionReport(List<Movement> movements) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Transaction Report", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Tabla con columnas
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2, 2, 2, 2, 2, 2, 2, 2});

            Stream.of("ID", "Date", "Client", "Account Type", "Account Number", "Initial Balance", "Amount", "Movement","Final Balance")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(Color.LIGHT_GRAY);
                        header.setPhrase(new Phrase(headerTitle));
                        table.addCell(header);
                    });

            // Agregar filas
            for (Movement tx : movements) {
                table.addCell(String.valueOf(tx.getId()));
                table.addCell(tx.getDate());
                table.addCell(tx.getClientName());
                table.addCell(tx.getAccountType());
                table.addCell(tx.getAccountNumber());
                table.addCell(String.valueOf(tx.getInitialBalance()));
                table.addCell(String.valueOf(tx.getAmount()));
                table.addCell(tx.getTransactionType());
                table.addCell(String.valueOf(tx.getFinalBalance()));
            }

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
