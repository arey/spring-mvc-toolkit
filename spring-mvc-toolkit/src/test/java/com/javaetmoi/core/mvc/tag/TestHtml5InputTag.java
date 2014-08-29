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
package com.javaetmoi.core.mvc.tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.StaticWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.support.JspAwareRequestContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import org.springframework.web.servlet.tags.form.FormTag;
import org.springframework.web.servlet.tags.form.TagWriter;
import org.springframework.web.servlet.theme.FixedThemeResolver;

import com.javaetmoi.core.mvc.tag.Html5InputTag;

public class TestHtml5InputTag {

    private Html5InputTag        inputTag;

    private StringWriter            writer;

    private MockPageContext pageContext;

    private FormTag         formTag;

    TagWriter               tagWriter;
    
    @Before
    public void setUp() {
        writer = new StringWriter();
        formTag = new FormTag();
        tagWriter = new TagWriter(writer);
        inputTag = new Html5InputTag() {

            @Override
            protected TagWriter createTagWriter() {
                return tagWriter;
            }
        };
        inputTag.setParent(formTag);
        pageContext = createAndPopulatePageContext(null);
        inputTag.setPageContext(pageContext);
    }

    @Test
    public void writeMaxLengthFromSizeMaxConstraint() throws JspException {
        inputTag.setPath("pet.name");
        Map<String, Object> model = new HashMap<String, Object>();
        Pet pet = new Pet();
        pet.name = "Medor";
        model.put("pet", pet);
        pageContext = createAndPopulatePageContext(model);
        inputTag.setPageContext(pageContext);

        inputTag.doStartTag();

        assertNotNull(writer.toString());
        assertEquals(
                "<input id=\"name\" name=\"name\" type=\"text\" value=\"Medor\" maxlength=\"40\"/>", writer.toString());
    }

    @Test
    public void writeMaxLengthFromTagValue() throws JspException {
        inputTag.setPath("pet.name");
        inputTag.setMaxlength("20");
        Map<String, Object> model = new HashMap<String, Object>();
        Pet pet = new Pet();
        pet.name = "Medor";
        model.put("pet", pet);
        pageContext = createAndPopulatePageContext(model);
        inputTag.setPageContext(pageContext);

        inputTag.doStartTag();

        assertNotNull(writer.toString());
        assertEquals(
                "<input id=\"name\" name=\"name\" type=\"text\" value=\"Medor\" maxlength=\"20\"/>", writer.toString());
    }
    
    @Test
    public void writeRequiredFromNotEmptyTag() throws JspException {
        inputTag.setPath("pet.surname");
        Map<String, Object> model = new HashMap<String, Object>();
        Pet pet = new Pet();
        pet.surname = "med";
        model.put("pet", pet);
        pageContext = createAndPopulatePageContext(model);
        inputTag.setPageContext(pageContext);

        inputTag.doStartTag();

        assertNotNull(writer.toString());
        assertEquals(
                "<input id=\"surname\" name=\"surname\" type=\"text\" value=\"med\" required=\"required\"/>", writer.toString());
    }
    
    @Test
    public void writeRequiredFromNotNullTag() throws JspException {
        inputTag.setPath("pet.ownerName");
        Map<String, Object> model = new HashMap<String, Object>();
        Pet pet = new Pet();
        pet.ownerName = "Dave";
        model.put("pet", pet);
        pageContext = createAndPopulatePageContext(model);
        inputTag.setPageContext(pageContext);

        inputTag.doStartTag();

        assertNotNull(writer.toString());
        assertEquals(
                "<input id=\"ownerName\" name=\"ownerName\" type=\"text\" value=\"Dave\" required=\"required\"/>", writer.toString());
    }
    
    @Test
    public void writeTypeFromEmailTag() throws JspException {
        inputTag.setPath("pet.ownerEmail");
        Map<String, Object> model = new HashMap<String, Object>();
        Pet pet = new Pet();
        pet.ownerEmail = "dave@mymail.com";
        model.put("pet", pet);
        pageContext = createAndPopulatePageContext(model);
        inputTag.setPageContext(pageContext);

        inputTag.doStartTag();

        assertNotNull(writer.toString());
        assertEquals(
                "<input id=\"ownerEmail\" name=\"ownerEmail\" type=\"email\" value=\"dave@mymail.com\"/>", writer.toString());
    }
    
    @Test
    public void writeTypeFromUrlTag() throws JspException {
        inputTag.setPath("pet.facebookUrl");
        Map<String, Object> model = new HashMap<String, Object>();
        Pet pet = new Pet();
        pet.facebookUrl = "https://www.facebook.com/medor2014.378";
        model.put("pet", pet);
        pageContext = createAndPopulatePageContext(model);
        inputTag.setPageContext(pageContext);

        inputTag.doStartTag();

        assertNotNull(writer.toString());
        assertEquals(
                "<input id=\"facebookUrl\" name=\"facebookUrl\" type=\"url\" value=\"https://www.facebook.com/medor2014.378\"/>", writer.toString());
    }
    
    @Test
    public void writeNumberTypeAndMaxFromMaxTag() throws JspException {
        inputTag.setPath("pet.age");
        Map<String, Object> model = new HashMap<String, Object>();
        Pet pet = new Pet();
        pet.age = 10;
        model.put("pet", pet);
        pageContext = createAndPopulatePageContext(model);
        inputTag.setPageContext(pageContext);

        inputTag.doStartTag();

        assertNotNull(writer.toString());
        assertEquals(
                "<input id=\"age\" name=\"age\" type=\"number\" value=\"10\" max=\"30\"/>", writer.toString());
    }      
    
    @Test
    public void writeNumberTypeAndMinFromMinTag() throws JspException {
        inputTag.setPath("pet.weight");
        Map<String, Object> model = new HashMap<String, Object>();
        Pet pet = new Pet();
        pet.weight = 6;
        model.put("pet", pet);
        pageContext = createAndPopulatePageContext(model);
        inputTag.setPageContext(pageContext);

        inputTag.doStartTag();

        assertNotNull(writer.toString());
        assertEquals(
                "<input id=\"weight\" name=\"weight\" type=\"number\" value=\"6\" min=\"0\"/>", writer.toString());
    }
    
    @Test
    public void writeNumberTypeFromPropertyType() throws JspException {
        inputTag.setPath("pet.birthYear");
        Map<String, Object> model = new HashMap<String, Object>();
        Pet pet = new Pet();
        pet.birthYear = 2014;
        model.put("pet", pet);
        pageContext = createAndPopulatePageContext(model);
        inputTag.setPageContext(pageContext);

        inputTag.doStartTag();

        assertNotNull(writer.toString());
        assertEquals(
                "<input id=\"birthYear\" name=\"birthYear\" type=\"number\" value=\"2014\"/>", writer.toString());
    }      
    

    protected JspAwareRequestContext getRequestContext() {
        return (JspAwareRequestContext) pageContext.getAttribute(RequestContextAwareTag.REQUEST_CONTEXT_PAGE_ATTRIBUTE);
    }

    protected MockPageContext createAndPopulatePageContext(Map<String, Object> model) {
        MockPageContext pc = createPageContext();
        JspAwareRequestContext requestContext = new JspAwareRequestContext(pc, model);
        pc.setAttribute(RequestContextAwareTag.REQUEST_CONTEXT_PAGE_ATTRIBUTE, requestContext);
        return pc;
    }

    protected MockPageContext createPageContext() {
        MockServletContext sc = new MockServletContext();
        StaticWebApplicationContext wac = new StaticWebApplicationContext();
        wac.setServletContext(sc);
        wac.setNamespace("test");
        wac.registerSingleton("validator",
                org.springframework.validation.beanvalidation.LocalValidatorFactoryBean.class);
        wac.refresh();

        MockHttpServletRequest request = new MockHttpServletRequest(sc);
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
        LocaleResolver lr = new AcceptHeaderLocaleResolver();
        request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, lr);
        ThemeResolver tr = new FixedThemeResolver();
        request.setAttribute(DispatcherServlet.THEME_RESOLVER_ATTRIBUTE, tr);
        request.setAttribute(DispatcherServlet.THEME_SOURCE_ATTRIBUTE, wac);

        return new MockPageContext(sc, request, response);
    }

    static class Pet {
        @Size(max=40)
        String name;
        
        @NotEmpty
        String surname;
        
        @NotNull
        String ownerName;
        
        @Email
        String ownerEmail;
        
        @URL
        String facebookUrl;
        
        @Max(value=30)
        Integer age;
        
        @Min(value=0)
        Integer weight;
        
        Integer birthYear;
        
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getSurname() {
            return surname;
        }
        
        public void setSurname(String surname) {
            this.surname = surname;
        }
        
        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }
        
        public String getOwnerEmail() {
            return ownerEmail;
        }
        
        public void setOwnerEmail(String ownerEmail) {
            this.ownerEmail = ownerEmail;
        }

        public String getFacebookUrl() {
            return facebookUrl;
        }
        
        public void setFacebookUrl(String facebookUrl) {
            this.facebookUrl = facebookUrl;
        }
        
        public Integer getAge() {
            return age;
        }
        
        public void setAge(Integer age) {
            this.age = age;
        }
        
        public Integer getWeight() {
            return weight;
        }
        
        public void setWeight(Integer weight) {
            this.weight = weight;
        }
        
        public Integer getBirthYear() {
            return birthYear;
        }
        
        public void setBirthYear(Integer birthYear) {
            this.birthYear = birthYear;
        }
        
    }

}
