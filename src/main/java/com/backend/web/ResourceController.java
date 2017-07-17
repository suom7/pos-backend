package com.backend.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.domain.Barcode;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("resources")
@Slf4j
public class ResourceController {

    /**
     * create node with form
     * 
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "v1", method = RequestMethod.POST)
    public ResponseEntity<Void> createWithForm(HttpServletRequest request, @ModelAttribute Barcode domain) {
        log.info("============= create ==========");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * find node by id
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "v1/{id}", method = RequestMethod.GET)
    public ResponseEntity<Void> findById(HttpServletRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "barcode", method = RequestMethod.GET)
    public ResponseEntity<Void> getBarcode(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("barcode") String barcode) throws Exception {
        AbstractBarcodeBean bean = new Code128Bean();
        bean.setHeight(10d);
        bean.doQuietZone(false);

        OutputStream out = new java.io.FileOutputStream(new File("output.png"));
        BitmapCanvasProvider provider = new BitmapCanvasProvider(out, "image/x-png", 110, BufferedImage.TYPE_BYTE_GRAY, false, 0);

        bean.generateBarcode(provider, barcode);
        provider.finish();

        BufferedImage barcodeImage = provider.getBufferedImage();
        response.setContentType("image/x-png");
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(barcodeImage, "png", outputStream);
        outputStream.close();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
