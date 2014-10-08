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

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.javaetmoi.core.mvc.demo.model.MyBean;
import com.javaetmoi.core.mvc.demo.model.MyOtherBean;

abstract class DataLogHelper {

    private static final Logger LOG = LoggerFactory.getLogger(DataLogHelper.class);

    static void printSession(HttpSession session) {
        LOG.info("*** Session data ***");
        Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String s = e.nextElement();
            Object val = session.getAttribute(s);
            if (val instanceof MyBean || val instanceof MyOtherBean) {
                LOG.info(s + " -- " + val);
            }
        }
    }

    static void printRequest(HttpServletRequest request) {
        LOG.info("=== Request data ===");
        Enumeration<String> reqEnum = request.getAttributeNames();
        while (reqEnum.hasMoreElements()) {
            String s = reqEnum.nextElement();
            Object val = request.getAttribute(s);
            if (val instanceof MyBean || val instanceof MyOtherBean) {
                LOG.info(s + " -- " + val);
            }
        }
    }

    static void printModel(Model model) {
        LOG.info("--- Model data ---");
        Map<String, Object> modelMap = model.asMap();
        for (Object modelKey : modelMap.keySet()) {
            Object modelValue = modelMap.get(modelKey);
            if (modelValue instanceof MyBean || modelValue instanceof MyOtherBean) {
                LOG.info(modelKey + " -- " + modelValue);
            }
        }
    }

}
