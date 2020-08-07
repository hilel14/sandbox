package org.hilel14.tesseract.bytedeco.demo;

import org.bytedeco.javacpp.*;
import org.bytedeco.leptonica.*;
import org.bytedeco.tesseract.*;
import static org.bytedeco.leptonica.global.lept.*;

public class BasicExample {

    public static void main(String[] args) {
        getText("eng", "1.png");
        getText("heb", "2.jpg");
    }

    static void getText(String lang, String file) {
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with tessdata path and language
        if (api.Init("src/main/resources/tessdata/best", lang) != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }
        // Open input image with leptonica library
        PIX image = pixRead("src/test/resources/samples/" + file);
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        System.out.println("OCR output:\n" + outText.getString());

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
    }
}
