package com.backend.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dao.CategoryDao;
import com.backend.dao.ProductDao;
import com.backend.domain.Category;
import com.backend.domain.Data;
import com.backend.domain.Field;
import com.backend.domain.Product;
import com.backend.json.ResponseList;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/products")
@Slf4j
public class ProductController {

    @Resource(name = "productDaoImpl")
    private ProductDao dao;

    @Resource(name = "categoryDaoImpl")
    private CategoryDao categoryDao;
    /**
     * create node with json
     * 
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "v1/json", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Product> create(HttpServletRequest request, @RequestBody Product body) {
        log.info("============= create with json ==========");
        dao.add(body);
        return new ResponseEntity<Product>(body, HttpStatus.OK);
    }

    /**
     * create node with form
     * 
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "v1", method = RequestMethod.POST)
    public ResponseEntity<Product> createWithForm(HttpServletRequest request, @ModelAttribute Product domain) {
        log.info("============= create ==========");
        dao.add(domain);
        return new ResponseEntity<Product>(domain, HttpStatus.OK);
    }

    /**
     * find node by id
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "v1/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findById(HttpServletRequest request, @PathVariable Long id) {
        return new ResponseEntity<Product>(dao.findById(id), HttpStatus.OK);
    }

    /**
     * update node with id
     * 
     * @param request
     * @param id
     * @param body
     * @return
     */
    @RequestMapping(value = "v1/{id}/json", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Product> update(HttpServletRequest request, @PathVariable Long id, @RequestBody Product body) {
        Product domain = dao.findById(id);
        if (domain == null) {
            // throw
        }
        
        domain.setName(body.getName());
        domain.setState(body.getState());
        domain.setCategoryId(body.getCategoryId());
        domain.setPrice1(body.getPrice1());
        domain.setPrice2(body.getPrice2());
        domain.setCode(body.getCode());
        domain.setDescription(body.getDescription());
        domain.setQty(body.getQty());
        // TODO : update properties
        dao.update(domain);
        return new ResponseEntity<Product>(domain, HttpStatus.OK);
    }

    /**
     * Get nodes with pagination
     * 
     * @param request
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "v1", method = RequestMethod.GET)
    public ResponseEntity<ResponseList<Product>> getPage(HttpServletRequest request,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") String offset) {
        log.info("============= getPage ==========");
        return new ResponseEntity<ResponseList<Product>>(dao.getPage(limit, offset), HttpStatus.OK);
    }

    /**
     * Get all notes
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "v1/all", method = RequestMethod.GET)
    public ResponseEntity<Collection<Product>> getAll(HttpServletRequest request) {
        log.info("============= getAll ==========");
        return new ResponseEntity<>(dao.getAll(), HttpStatus.OK);
    }

    /**
     * 
     * schema api : Content-Type: application/x-www-form-urlencoded 
     * example json value
     * 
     *   {
     *       primaryKeyName: "id",
     *       tableName: "Country",
     *       primaryKeyType: "long",
     *       columns: {
     *           comingSoon: "boolean",
     *           flagImageUrl: "text",
     *           isoCode: "text",
     *           name: "text",
     *           state: "long",
     *           tcsUrl: "text",
     *           price : "number",
     *           createdDate: "datetime"
     *        }
     *   }
     * 
     * @param request
     */
    @RequestMapping(value = "v1/schema", method = { RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(value="get product schema", response = Map.class,
            notes = "get category schema return as json key and value ex : {\" primaryKeyName: \"id\", tableName:country,...")
    public ResponseEntity<Map<String, Object>> getschma(HttpServletRequest request) {
        final Map<String, Object> body = new HashMap<String, Object>();

        
        final List<Field> columns = new ArrayList<>();
        
        List<Data> list = categoryDao.getAll()
                            .stream()
                            .map(c -> new Data(String.valueOf(c.getId()), c.getName()))
                            .collect(Collectors.toList());
        
        columns.add(new Field("categoryId", "select", list));
        columns.add(new Field("name", "text"));
        columns.add(new Field("code", "text"));
        columns.add(new Field("description", "text"));
        columns.add(new Field("price1", "number"));
        columns.add(new Field("price2", "number"));
        columns.add(new Field("qty", "long"));
        
        columns.add(new Field("createdDate", "datetime"));
        columns.add(new Field("updatedDate", "datetime"));
        // test
       // columns.add(new Field("gender", "select", Arrays.asList(new Data("1", "Female"), new Data("2", "Male"))));
        /*
        final Map<String, String> columns = new HashMap<>();
        columns.put("name", "text");
        columns.put("type", "text");
        */
        body.put("columns", columns);
        body.put("tableName", "product");
        body.put("primaryKeyName", "id");
        body.put("primaryKeyType", "long");
        
        return new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
    }
    
    @GetMapping(value = "/v1", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ApiOperation(value="get products by paging",nickname = "get products by paging")
    public ResponseList<Product> getPage(@RequestParam(value="pagesize", defaultValue="10") int pagesize,
            @RequestParam(value = "cursorkey", required = false) String cursorkey) {
        log.info("====get page {} , {} ====", pagesize, cursorkey);
        return dao.getPage(pagesize, cursorkey);
    }

}
