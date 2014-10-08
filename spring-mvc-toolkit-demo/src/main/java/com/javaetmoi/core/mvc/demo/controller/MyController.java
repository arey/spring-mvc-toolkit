/**
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.javaetmoi.core.mvc.demo.controller;

import static com.javaetmoi.core.mvc.demo.controller.DataLogHelper.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.javaetmoi.core.mvc.demo.model.MyBean;
import com.javaetmoi.core.mvc.demo.model.MyOtherBean;

@Controller
@SessionAttributes( value="myBean1", types={MyOtherBean.class} )
public class MyController {
    
    private static final Logger LOG = LoggerFactory.getLogger(MyController.class);
 
    @ModelAttribute("myBean1")
    public MyBean addMyBean1ToSessionScope() {
        LOG.info("Inside of addMyBean1ToSessionScope");
        return new MyBean("My Bean 1");
    }
    
    @ModelAttribute("myBean2")
    public MyBean addMyBean2ToRequestScope() {
        LOG.info("Inside of addMyBean2ToRequestScope");
        return new MyBean("My Bean 2");
    }
    
    @ModelAttribute("myOtherBeanA")
    public MyOtherBean addMyOtherBeanAToSessionScope() {
        LOG.info("Inside of addMyOtherBeanAToSessionScope");
        return new MyOtherBean("My Other Bean A");
    }
    
    @ModelAttribute("myOtherBeanB")
    public MyOtherBean addMyOtherBeanBToSessionScope() {
        LOG.info("Inside of addMyOtherBeanBToSessionScope");
        return new MyOtherBean("My Other Bean B");
    }        
 
    @RequestMapping("/dosomething")
    public String doSomethingtHandlingMethod(Model model, HttpServletRequest request, HttpSession session) {
        LOG.info("Inside of dosomething handler method");
        printModel(model);
        printRequest(request);
        printSession(session);
        return "sessionsattributepage";
    }
    
    @RequestMapping("/endsession")
    public String endSessionHandlingMethod(SessionStatus status, Model model, HttpServletRequest request, HttpSession session){
        status.setComplete();
        printModel(model);
        printRequest(request);
        printSession(session);        
        return "sessionsattributepage";
      }
}

