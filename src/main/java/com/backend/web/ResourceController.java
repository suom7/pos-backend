package com.backend.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.domain.Barcode;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@RestController
@RequestMapping("resources")
public class ResourceController {
    
    /**
     * 
     * @param request
     * @param response
     * @param barcode
     * @return
     * @throws Exception
     */
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
    
    /**
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "import/csv", method = RequestMethod.POST)
    public ResponseEntity<List<Barcode>> importCsvFile(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return new ResponseEntity<>(readWithCsvToBeanBuilder("barcode.csv"), HttpStatus.OK);
    }

    /**
     * 
     * @param filename
     * @return
     * @throws IOException
     * @Ref http://opencsv.sourceforge.net/
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Barcode> readWithCsvToBeanBuilder(String filename) throws IOException {
        File file = new ClassPathResource(filename).getFile();
        FileInputStream fis = new FileInputStream(file);
        return new CsvToBeanBuilder(new InputStreamReader(fis)).withType(Barcode.class).build().parse();
    }

    /**
     * 
     * @param filename
     * @return
     * @throws IOException
     */
    public List<Barcode> readWithCsvToBean(String filename) throws IOException {
        File file = new ClassPathResource(filename).getFile();
        FileInputStream fis = new FileInputStream(file);

        CSVReader reader = new CSVReader(new InputStreamReader(fis), ',', '\'', 1);
        
        ColumnPositionMappingStrategy<Barcode> beanStrategy = new ColumnPositionMappingStrategy<Barcode>();
        beanStrategy.setType(Barcode.class);
        beanStrategy.setColumnMapping(new String[]{"BarcodeId", "Description"});

        CsvToBean<Barcode> csvToBean = new CsvToBean<Barcode>();
        return csvToBean.parse(beanStrategy, reader);
    }
}
