package school.journal.controller;


import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.journal.controller.exception.ControllerException;
import school.journal.controller.util.ErrorObject;
import school.journal.entity.Role;
import school.journal.service.IRoleService;
import school.journal.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/roles")
public class RoleAPIController {
    private Logger LOGGER = Logger.getLogger(RoleAPIController.class);

    @Autowired
    @Qualifier("RoleService")
    private IRoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity get(HttpServletRequest req)
            throws ControllerException {
        try{
            LOGGER.info("get role list controller method");
            return new ResponseEntity(roleService.read(), HttpStatus.OK);
        } catch (ServiceException exc){
            return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
        } catch (Exception exc) {
            LOGGER.error(exc);
            return new ResponseEntity(new ErrorObject("Some critical error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity create(@RequestBody Role role)
            throws ControllerException {
        try{
            return new ResponseEntity(roleService.create(role), HttpStatus.OK);
        } catch (ServiceException exc){
            return new ResponseEntity(new ErrorObject("Error in role creating"), HttpStatus.BAD_REQUEST);
        } catch (Exception exc) {
            LOGGER.error(exc);
            return new ResponseEntity(new ErrorObject("Some critical error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity update(@RequestBody Role role)
            throws ControllerException {
        try{
            return new ResponseEntity(roleService.update(role), HttpStatus.OK);
        } catch (ServiceException exc){
            return new ResponseEntity(new ErrorObject("Error in role updating"), HttpStatus.BAD_REQUEST);
        } catch (Exception exc) {
            LOGGER.error(exc);
            return new ResponseEntity(new ErrorObject("Some critical error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity delete(@PathVariable("roleId") int roleId)
            throws ControllerException {
        try{
            roleService.delete(roleId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException exc){
            return new ResponseEntity(new ErrorObject("Error in role deleting"), HttpStatus.BAD_REQUEST);
        } catch (Exception exc) {
            LOGGER.error(exc);
            return new ResponseEntity(new ErrorObject("Some critical error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{roleId}")
    @ResponseBody
    public ResponseEntity getOne(@PathVariable("roleId") int roleId)
            throws ControllerException {
        try{
            return new ResponseEntity(roleService.getOne(roleId), HttpStatus.OK);
        } catch (ServiceException exc){
            return new ResponseEntity(new ErrorObject("Error in role getting"), HttpStatus.BAD_REQUEST);
        } catch (Exception exc) {
            LOGGER.error(exc);
            return new ResponseEntity(new ErrorObject("Some critical error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
