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

import static com.javaetmoi.core.mvc.demo.controller.DataLogHelper.printModel;
import static com.javaetmoi.core.mvc.demo.controller.DataLogHelper.printRequest;
import static com.javaetmoi.core.mvc.demo.controller.DataLogHelper.printSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.javaetmoi.core.mvc.demo.model.MyBean;

@Controller
@SessionAttributes({"myBean1", "myBean3"})
public class OtherController {
    
    private static final Logger LOG = LoggerFactory.getLogger(MyController.class);
     
    @ModelAttribute("myBean3")
    public MyBean addMyBean3ToSessionScope() {
        LOG.info("Inside of addMyBean3ToSessionScope");
        return new MyBean("My Bean 3");
    }
 
    @RequestMapping("/other")
    public String otherHandlingMethod(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute("myBean1") MyBean myBean) {
        LOG.info("Inside of other handler method");
        LOG.info(myBean.toString());
 
        printModel(model);
        printRequest(request);
        printSession(session);
 
        return "sessionsattributepage";
    }
}

