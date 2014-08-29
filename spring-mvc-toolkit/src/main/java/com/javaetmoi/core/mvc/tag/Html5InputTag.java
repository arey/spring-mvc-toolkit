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

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.validation.Validator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.InputTag;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * Add HTML5 form validation to the default Spring MVC input tag by using Bean Validation
 * constraints.
 * 
 * <p>
 * Supports {@link Max}, {@link Min}, {@link NotNull} and {@link Size} form the Bean Validation API.<br/>
 * Supports custom {@link Email}, {@link NotEmpty} and {@link URL} annotations from the Hibernate Validator implementation.
 * 
 */
public class Html5InputTag extends InputTag {

    public static final String                                        MAX_ATTRIBUTE = "max";

    public static final String                                        MIN_ATTRIBUTE = "min";

    private Map<Class<? extends Annotation>, ConstraintDescriptor<?>> annotations;

    private Class<?> valueType;
    
    private static final Map<Class<?>, String> javaToHtmlTypes;
    
    static {
        javaToHtmlTypes = new HashMap<Class<?>, String>();
        javaToHtmlTypes.put(Integer.class, "number");
        javaToHtmlTypes.put(Long.class, "number");
    }

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor();
        if ((propertyDescriptor != null) && !propertyDescriptor.getConstraintDescriptors().isEmpty()) {
            annotations = constrainteByAnnotationType(propertyDescriptor);
        }
        valueType = getBindStatus().getValueType();
        return super.writeTagContent(tagWriter);
    }

    @Override
    protected void writeValue(TagWriter tagWriter) throws JspException {
        super.writeValue(tagWriter);
        if (annotations != null) {
            writeHtml5Attributes(tagWriter);
        }
    }

    protected void writeHtml5Attributes(TagWriter tagWriter) throws JspException {
        writeMaxLengthAttribute(tagWriter);
        writeRequiredAttribute(tagWriter);
        writeMaxAttribute(tagWriter);
        writeMinAttribute(tagWriter);
    }

    protected void writeMaxLengthAttribute(TagWriter tagWriter) throws JspException {
        if (annotations.containsKey(Size.class)) {
            Size size = getAnnotation(Size.class);
            if ((size.max() != Integer.MAX_VALUE) && StringUtils.isEmpty(getMaxlength())) {
                writeOptionalAttribute(tagWriter, MAXLENGTH_ATTRIBUTE, String.valueOf(size.max()));
            }
        }
    }

    protected void writeRequiredAttribute(TagWriter tagWriter) throws JspException {
        if (annotations.containsKey(NotEmpty.class) || annotations.containsKey(NotNull.class)) {
            writeOptionalAttribute(tagWriter, "required", "required");
        }
    }

    protected void writeMaxAttribute(TagWriter tagWriter) throws JspException {
        if (annotations.containsKey(Max.class)) {
            Max max = getAnnotation(Max.class);
            writeOptionalAttribute(tagWriter, MAX_ATTRIBUTE, String.valueOf(max.value()));
        }
    }

    protected void writeMinAttribute(TagWriter tagWriter) throws JspException {
        if (annotations.containsKey(Min.class)) {
            Min min = getAnnotation(Min.class);
            writeOptionalAttribute(tagWriter, MIN_ATTRIBUTE, String.valueOf(min.value()));
        }
    }

    @Override
    protected String getType()  {
        String type = javaToHtmlTypes.get(valueType);
        if (type != null) {
            return type;
        }
        type = "text";
        if (annotations != null) {
            if (annotations.containsKey(Email.class)) {
                type = "email";
            } else if (annotations.containsKey(URL.class)) {
                type = "url";
            } else if (annotations.containsKey(Max.class) || annotations.containsKey(Min.class)) {
                type = "number";
            }
        }
        return type;
    }

    protected Object getBean(String beanName, Map<String, Object> model) {
        Object bean;
        if (model != null) {
            bean = model.get(beanName);
        } else {
            ConfigurablePropertyAccessor bw = PropertyAccessorFactory.forDirectFieldAccess(getRequestContext());
            HttpServletRequest request = (HttpServletRequest) bw.getPropertyValue("request");
            bean = request.getAttribute(beanName);
        }
        return bean;
    }

    private Map<Class<? extends Annotation>, ConstraintDescriptor<?>> constrainteByAnnotationType(
            PropertyDescriptor propertyDescriptor) {
        Map<Class<? extends Annotation>, ConstraintDescriptor<?>> annotationMap = new HashMap<Class<? extends Annotation>, ConstraintDescriptor<?>>();
        Set<ConstraintDescriptor<?>> constraintDescriptors = propertyDescriptor.getConstraintDescriptors();
        for (ConstraintDescriptor<?> constraintDescriptor : constraintDescriptors) {
            annotationMap.put(constraintDescriptor.getAnnotation().annotationType(), constraintDescriptor);
        }
        return annotationMap;
    }

    /**
     * @return PropertyDescriptor may be null when JavaBean do not have any Bean Validation
     *         annotations.
     */
    private PropertyDescriptor getPropertyDescriptor() throws JspException {
        String path = getBindStatus().getPath();
        int dotPos = path.indexOf('.');
        if (dotPos == -1) {
            return null;
        }
        String beanName = path.substring(0, dotPos);
        String expression = path.substring(dotPos + 1);

        Map<String, Object> model = getRequestContext().getModel();
        Object bean = getBean(beanName, model);

        Validator validator = getRequestContext().getWebApplicationContext().getBean(Validator.class);
        BeanDescriptor constraints = validator.getConstraintsForClass(bean.getClass());
        return constraints.getConstraintsForProperty(expression);
    }

    @SuppressWarnings("unchecked")
    private <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return (T) annotations.get(annotationClass).getAnnotation();
    }
}