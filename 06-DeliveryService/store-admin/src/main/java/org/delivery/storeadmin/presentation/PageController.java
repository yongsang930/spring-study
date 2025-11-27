package org.delivery.storeadmin.presentation;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class PageController {

    @RequestMapping(path = {"","/main"})
    public ModelAndView main(){

        return new ModelAndView("main");
    }

    @RequestMapping("/order")
    public ModelAndView order(){

        return new ModelAndView("order/order");
    }
}
