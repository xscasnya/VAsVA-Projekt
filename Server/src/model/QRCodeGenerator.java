package model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Trieda ktorá sa stará o generovanie QR kódov
 */
public class QRCodeGenerator {

    /**
     * Metóda ktorá vracia bytové pole daného qr kódu
     * @param url URL na ktorú chceme odkazovať cez QR kód
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] GenerateQRCode(String url) throws WriterException, IOException {

        int size = 250;
       // String fileType = "png";

       // File qrFile = new File("images\\qrcode.png");

        Hashtable hintMap = new Hashtable();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        QRCodeWriter writer = new QRCodeWriter();

        BitMatrix byteMatrix = writer.encode(url, BarcodeFormat.QR_CODE, size, size, hintMap);

        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);

        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        if (!ImageIO.write(image, "png", stream)) {
            throw new IOException("Could not write an image of format " + "png");
        }

        return stream.toByteArray();


        //ImageIO.write(image, fileType, qrFile);

    }
}
