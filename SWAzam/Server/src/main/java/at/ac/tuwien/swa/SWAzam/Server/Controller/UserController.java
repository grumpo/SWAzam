package at.ac.tuwien.swa.SWAzam.Server.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import at.ac.tuwien.swa.SWAzam.Server.Model.ApplicationModel;
import at.ac.tuwien.swa.SWAzam.Server.Model.LoginModel;

@Controller
public class UserController {
 
    private LoginModel loginModel;
 
    /**
     * This method will be called from LoginController if the user is validated.
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/success", method = RequestMethod.GET)
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response)
    {
        ApplicationModel applicationModel = new ApplicationModel();
        loginModel = (LoginModel)request.getSession().getAttribute("user");
        applicationModel.setLoginModel(loginModel);
        return new ModelAndView("success", "applicationModel", applicationModel);
    }
}