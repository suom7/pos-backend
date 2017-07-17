package com.backend.web;

import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dao.ProductDao;
import com.backend.domain.Product;
import com.backend.json.ResponseList;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("products")
@Slf4j
public class ProductController {

    @Resource(name = "productDaoImpl")
    protected ProductDao dao;

    /**
     * create node with json
     * 
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "v1/json", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Product> create(HttpServletRequest request, @RequestBody Product body) {
        log.info("============= create ==========");
        dao.add(body);
        return new ResponseEntity<>(body, HttpStatus.OK);
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
        return new ResponseEntity<>(domain, HttpStatus.OK);
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
        return new ResponseEntity<>(dao.findById(id), HttpStatus.OK);
    }

    /**
     * update node with id
     * 
     * @param request
     * @param id
     * @param body
     * @return
     */
    @RequestMapping(value = "v1/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Product> update(HttpServletRequest request, @PathVariable Long id, @RequestBody Product body) {
        Product domain = dao.findById(id);
        if (domain == null) {
            // throw
        }
        domain.setName(body.getName());
        domain.setState(body.getState());
        // TODO : update properties
        dao.update(domain);
        return new ResponseEntity<>(domain, HttpStatus.OK);
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
        return new ResponseEntity<>(dao.getPage(limit, offset), HttpStatus.OK);
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

}
