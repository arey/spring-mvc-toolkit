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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javaetmoi.core.mvc.demo.model.MyBean;

/**
 * This test shows how to handle between 2 controllers a model attribute store into the session.
 *
 */
public class SessionAttributesTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new MyController(), new OtherController()).build();
    }

    @Test
    public void reusingSessionBean() throws Exception {
        MvcResult result = mockMvc.perform(get("/dosomething"))
               .andDo(print())
               .andExpect(model().attributeExists("myBean1"))
               .andReturn();
        MyBean myBean1 = (MyBean) result.getModelAndView().getModel().get("myBean1");
        mockMvc.perform(get("/other")
               .sessionAttr("myBean1", myBean1))             
               .andDo(print())
               .andExpect(model().attributeExists("myBean3"));
        
    }
}
