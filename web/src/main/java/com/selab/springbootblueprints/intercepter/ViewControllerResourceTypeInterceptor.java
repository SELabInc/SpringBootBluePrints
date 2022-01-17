package com.selab.springbootblueprints.intercepter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RequiredArgsConstructor
public class ViewControllerResourceTypeInterceptor implements HandlerInterceptor {

    public final String resourceType;

    public final String templateStyle;

    public String resourceTypeCheck() {

        if (resourceType.equalsIgnoreCase("wan") || resourceType.equalsIgnoreCase("lan")) {
            return resourceType;
        }
        return "wan";
    }

    public String templateTypeCheck() {

        if (templateStyle.equalsIgnoreCase("adminlte") || templateStyle.equalsIgnoreCase("inspinia")) {
            return templateStyle;
        }
        return "inspinia";
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            modelAndView.addObject("resourceType",resourceTypeCheck());
            modelAndView.addObject("templateStyle",templateTypeCheck());
        }
    }

}
