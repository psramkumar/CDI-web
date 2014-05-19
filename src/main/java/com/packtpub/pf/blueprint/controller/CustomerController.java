package com.packtpub.pf.blueprint.controller;

import com.packtpub.pf.blueprint.model.Customer;
import com.packtpub.pf.blueprint.persistence.CustomerDAO;
import com.packtpub.pf.blueprint.qualifiers.BusinessController;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by psramkumar on 4/21/14.
 */
@Named
@RequestScoped
public class CustomerController implements Serializable {

    private static final long serialVersionUID = -1890125026548028469L;

    private Logger _log = Logger.getLogger(CustomerController.class);

    @Getter
    @Setter
    private Customer customer;

    @Getter
    @Setter
    private Customer newCustomer = new Customer();

    @Getter
    @Setter
    private String useremail;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private boolean loggedIn = false;

    @Inject
    CustomerDAO customerDAO;

    Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();

    public String loginMeIn() throws ServletException {
        customer = customerDAO.validateUser(useremail, password);
        _log.info("Trying to Logging in now with UserName : " + customer.getEmail());
        this.loggedIn = customer != null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest rq = (HttpServletRequest) request;
            rq.setAttribute("useremail", useremail);
        }
        return this.loggedIn ? "/location.jsf?faces-redirect=true" : "/logmein.jsf?error=true";
    }

    public String loginMeOut() throws ServletException {
        _log.info("Trying to LogOut now.....");
        this.loggedIn = false;
        customer = null;
        newCustomer = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest rq = (HttpServletRequest) request;
            rq.logout();
        }
        return "/index.jsf?faces-redirect=true";
    }

    public void prepareAddNewUser(){
        newCustomer = new Customer();
    }

    public void saveCustomerInfo(){
        customerDAO.persist(this.newCustomer);
    }
}
