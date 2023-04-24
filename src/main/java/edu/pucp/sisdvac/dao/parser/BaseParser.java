package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.exception.GenericException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class BaseParser {
    public BaseParser() {
    }

    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static <In, Out> Out parse(In src, Class<Out> clazz){
        try {
            Out target = clazz.newInstance();
            BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new GenericException(e.getMessage());
        }
    }

    public static <In, Out> Out copyProperties(In src, Out target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
        return target;
    }
}
