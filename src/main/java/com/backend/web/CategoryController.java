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

import com.backend.dao.CategoryDao;
import com.backend.domain.Category;
import com.backend.json.ResponseList;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("catagories")
@Slf4j
public class CategoryController {

    @Resource(name = "categoryDaoImpl")
    protected CategoryDao dao;

    /**
     * create node with json
     * 
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "v1/json", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Category> create(HttpServletRequest request, @RequestBody Category body) {
        log.info("============= create ==========");
        dao.add(body);
        return new ResponseEntity<Category>(body, HttpStatus.OK);
    }

    /**
     * create node with form
     * 
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "v1", method = RequestMethod.POST)
    public ResponseEntity<Category> createWithForm(HttpServletRequest request, @ModelAttribute Category domain) {
        log.info("============= create ==========");
        dao.add(domain);
        return new ResponseEntity<Category>(domain, HttpStatus.OK);
    }

    /**
     * find node by id
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "v1/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> findById(HttpServletRequest request, @PathVariable Long id) {
        return new ResponseEntity<Category>(dao.findById(id), HttpStatus.OK);
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
    public ResponseEntity<Category> update(HttpServletRequest request, @PathVariable Long id, @RequestBody Category body) {
        Category domain = dao.findById(id);
        if (domain == null) {
            // throw
        }
        domain.setName(body.getName());
        domain.setState(body.getState());
        // TODO : update properties
        dao.update(domain);
        return new ResponseEntity<Category>(domain, HttpStatus.OK);
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
    public ResponseEntity<ResponseList<Category>> getPage(HttpServletRequest request,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") String offset) {
        log.info("============= getPage ==========");
        return new ResponseEntity<ResponseList<Category>>(dao.getPage(limit, offset), HttpStatus.OK);
    }

    /**
     * Get all notes
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "v1/all", method = RequestMethod.GET)
    public ResponseEntity<Collection<Category>> getAll(HttpServletRequest request) {
        log.info("============= getAll ==========");
        return new ResponseEntity<>(dao.getAll(), HttpStatus.OK);
    }

}
