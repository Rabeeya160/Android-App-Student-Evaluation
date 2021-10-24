package com.jyaconsulting.aashal1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.OrderModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GeneratePdfReceipt {
    String path, signature_pdf_ = "Invoice";
    Context context;
    String outletName, Address, owner, contact, DistributerName,discount;

    public GeneratePdfReceipt(Context context, String outletName, String address, String owner, String contact, String distributerName,String discount) {
        this.context = context;
        this.outletName = outletName;
        Address = address;
        this.owner = owner;
        this.contact = contact;
        DistributerName = distributerName;
        this.discount=discount;
    }

    public void createPdf() throws IOException, DocumentException {
//
//        PdfDocument document = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            document = new PdfDocument();
//            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(b.getWidth(), b.getHeight(), 1).create();
//            PdfDocument.Page page = document.startPage(pageInfo);
//
//            Canvas canvas = page.getCanvas();
//
//
//            Paint paint = new Paint();
//            paint.setColor(Color.parseColor("#ffffff"));
//            canvas.drawPaint(paint);
//
//
//            Bitmap bitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);
//
//            paint.setColor(Color.BLUE);
//            canvas.drawBitmap(bitmap, 0, 0, null);
//            document.finishPage(page);
//            File filePath = new File(path);
//            try {
//                document.writeTo(new FileOutputStream(filePath));
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//            }
//
//            // close the document
//            document.close();
//        }
//
//        openPdf(path);// You can open pdf after complete

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Signature/");

        if (!folder.exists()) {
            boolean success = folder.mkdir();
        }

        path = folder.getAbsolutePath();
        path = path + "/" + signature_pdf_ + ".pdf";// path where pdf will be stored

        /**
         * Creating Document
         */
        Document doc = new Document();
// Location to save
        PdfWriter.getInstance(doc, new FileOutputStream(path));

// Open to write
        doc.open();

        // Document Settings
        doc.setPageSize(PageSize.HALFLETTER);
        doc.addCreationDate();
        doc.addAuthor("JYA Consulting");
        doc.addCreator("JYA Consulting");


/***
 * Variables for further use....
 */
        BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
        float mHeadingFontSize = 14.0f;
        float mValueFontSize = 11.0f;

        /**
         * How to USE FONT....
         */
        BaseFont urName = BaseFont.createFont("res/font/consola.ttf", "UTF-8", BaseFont.EMBEDDED);

        // Title Order Details...
        // Adding Title....
        Font mOrderDetailsTitleFont = new Font(urName, 36.0f, Font.NORMAL, BaseColor.BLACK);
        // Creating Chunk
        Chunk mOrderDetailsTitleChunk = new Chunk("INVOICE", mOrderDetailsTitleFont);
        // Creating Paragraph to add...
        Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
        // Setting Alignment for Heading
        mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
        // Finally Adding that Chunk
        doc.add(mOrderDetailsTitleParagraph);

        Font mOrderIdFont = new Font(urName, mHeadingFontSize, Font.NORMAL, BaseColor.BLACK);
        Chunk mOrderIdChunk00 = new Chunk(" ", mOrderIdFont);
        Paragraph mOrderIdParagraph00 = new Paragraph(mOrderIdChunk00);
        doc.add(mOrderIdParagraph00);

        // Fields of Order Details...
        // Adding Chunks for Title and value
        Chunk mOrderIdChunk = new Chunk("Invoice No: INV-201912346", mOrderIdFont);
        Paragraph mOrderIdParagraph = new Paragraph(mOrderIdChunk);
        doc.add(mOrderIdParagraph);

        Chunk mOrderIdChunk1 = new Chunk("Outlet: " + outletName, mOrderIdFont);
        Paragraph mOrderIdParagraph1 = new Paragraph(mOrderIdChunk1);
        doc.add(mOrderIdParagraph1);

        Chunk mOrderIdChunk2 = new Chunk("Outlet Address: " + Address, mOrderIdFont);
        Paragraph mOrderIdParagraph2 = new Paragraph(mOrderIdChunk2);
        doc.add(mOrderIdParagraph2);

        Chunk mOrderIdChunk3 = new Chunk("Contact Person: " + owner + " - " + contact, mOrderIdFont);
        Paragraph mOrderIdParagraph3 = new Paragraph(mOrderIdChunk3);
        doc.add(mOrderIdParagraph3);

        Chunk mOrderIdChunk4 = new Chunk("Distributor Name: " + DistributerName, mOrderIdFont);
        Paragraph mOrderIdParagraph4 = new Paragraph(mOrderIdChunk4);
        doc.add(mOrderIdParagraph4);

        // TODO Get Current Date and Month To generate invoice
        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("dd");
        String Year = format1.format(today);
        String day = format2.format(today);

        //TODO Get Current Month Name
        Calendar c = Calendar.getInstance();
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        String Month = monthName[c.get(Calendar.MONTH)];


        Chunk mOrderIdChunk5 = new Chunk("Date: " + day + " " + Month + " " + Year, mOrderIdFont);
        Paragraph mOrderIdParagraph5 = new Paragraph(mOrderIdChunk5);
        doc.add(mOrderIdParagraph5);

        mOrderIdFont = new Font(urName, mHeadingFontSize, Font.NORMAL, BaseColor.BLACK);
        Chunk mOrderIdChunk10 = new Chunk("  ", mOrderIdFont);
        Paragraph mOrderIdParagraph10 = new Paragraph(mOrderIdChunk10);
        doc.add(mOrderIdParagraph10);


        PdfPTable table = new PdfPTable(new float[]{(float) 0.5, (float) 1.5, 1, 1, 1});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Sr#");
        table.addCell("SKU");
        table.addCell("U. Price");
        table.addCell("Qty.");
        table.addCell("Amount");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        int gross_total = 0;
        AppDataBase db = new AppDataBase(context, "");
        db.open();
        String outletId=Utils.getPreferences("pjpID",context);
        List<OrderModel> order = db.getAllOrder(outletId);
        for (int i = 0; i <= order.size() - 1; i++) {
//            String link = Utils.getPreferences("link", context);
            OrderModel current = order.get(i);
            String pro = current.getItem();
            int qty = Integer.parseInt(current.getOrderProductQuantity());
            int amount = Integer.parseInt(current.getOrderPrice());
            table.addCell(String.valueOf(i));
            table.addCell(pro);
            table.addCell("" + amount);
            table.addCell("" + qty);
            table.addCell("" + amount * qty);
            gross_total = gross_total + (amount + qty);
        }
        doc.add(table);

        mOrderIdFont = new Font(urName, mHeadingFontSize, Font.NORMAL, BaseColor.BLACK);
        Chunk mOrderIdChunk11 = new Chunk("  ", mOrderIdFont);
        Paragraph mOrderIdParagraph11 = new Paragraph(mOrderIdChunk11);
        doc.add(mOrderIdParagraph11);

        Chunk mOrderIdChunk6 = new Chunk("Remarks: ", mOrderIdFont);
        Paragraph mOrderIdParagraph6 = new Paragraph(mOrderIdChunk6);
        doc.add(mOrderIdParagraph6);

        Chunk mOrderIdChunk7 = new Chunk("Gross Amount: "+gross_total, mOrderIdFont);
        Paragraph mOrderIdParagraph7 = new Paragraph(mOrderIdChunk7);
        doc.add(mOrderIdParagraph7);

        Chunk mOrderIdChunk8 = new Chunk("Discount: "+discount, mOrderIdFont);
        Paragraph mOrderIdParagraph8 = new Paragraph(mOrderIdChunk8);
        doc.add(mOrderIdParagraph8);

        double discountedAmount=gross_total-(gross_total*(Integer.parseInt(discount)/100));

        Chunk mOrderIdChunk9 = new Chunk("Net Payable: "+discountedAmount, mOrderIdFont);
        Paragraph mOrderIdParagraph9 = new Paragraph(mOrderIdChunk9);
        doc.add(mOrderIdParagraph9);

//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        b.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        Image image = Image.getInstance(stream.toByteArray());


        doc.close();

//        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Signature/jya");
//        String path=folder.getAbsolutePath();
        openPdf(path);
    }

    public void openPdf(String path) {
//        PrintManager printManager=(PrintManager)
//        PrintManager printManager=(PrintManager) .getActivityContext().getSystemService(Context.PRINT_SERVICE);
//        try
//        {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                PrintDocumentAdapter printAdapter = new PdfDocumentAdapter(context,path);
////                printManager.print("Document", printAdapter,new PrintAttributes.Builder().build());
//
//            }
//        }
//catch (Exception e)
//    {
////        Logger.logError(e);
//    }
//
        File file = new File(Environment.getExternalStorageDirectory() + "/Signature" + "/Invoice.pdf");
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

//        File file = new File(path, signature_pdf_ + ".pdf");
//
//        intent.setDataAndType(uri, "application/pdf");
//        startActivity(intent);

        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
