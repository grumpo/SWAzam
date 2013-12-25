package at.ac.tuwien.swa.SWAzam.Server.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import at.ac.tuwien.swa.SWAzam.Server.Model.LoginModel;
import at.ac.tuwien.swa.SWAzam.Server.Service.LoginService;



@Controller
@RequestMapping(value ="/login")
public class LoginController {
 
    //Creating the loginModel object.
    private LoginModel loginModel;
 
    @Autowired
    private LoginService loginService;
 
    /**
     * This method will load the login.jsp page when the application starts
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView init() {
        loginModel = new LoginModel();
        return new ModelAndView("login", "loginDetails", loginModel);
    }
 
    /**
     * This method will be called when the user submits the login details from login.jsp page.
     * If there is any error it will be displayed on the same page, if the user is valid then, will
     * be redirected to success page.
     *
     * @param loginModel
     * @param bindingResult
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("loginDetails")LoginModel loginModel, BindingResult bindingResult,
            HttpServletRequest request,    HttpServletResponse response)
    {
        try
        {
            // Using Spring ValidationUtils class to check for empty fields.
            // This will add the error if any in the bindingResult object.
            ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"userName","userName", "Username can not be empty.");
            ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"password","password", "Password not be empty");
 
            if (bindingResult.hasErrors())
            {
                //returning the errors on same page if any errors..
                return new ModelAndView("login", "loginDetails", loginModel);
            }
            else
            {
                // If the user details is validated then redirecting the user to success page,
                // else returning the error message on login page.
                if(loginService.validate(loginModel))
                {
                    request.getSession().setAttribute("user", loginModel);
                    //Creating a redirection view to success page. This will redirect to UsersController
                    RedirectView redirectView = new RedirectView("success.do", true);
                    return new ModelAndView(redirectView);
                }
                else
                {
                    bindingResult.addError(new ObjectError("Invalid", "Invalid credentials. " +
                            "Username or Password is incorrect."));
                    return new ModelAndView("login", "loginDetails", loginModel);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in LoginController "+e.getMessage());
            e.printStackTrace();
            return new ModelAndView("login", "loginDetails", loginModel);
        }
    }
}