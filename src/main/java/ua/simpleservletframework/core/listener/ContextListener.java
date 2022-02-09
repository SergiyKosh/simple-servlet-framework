package ua.simpleservletframework.core.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ua.simpleservletframework.core.annotation.processor.BeanAnnotationProcessor;

import java.util.Set;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BeanAnnotationProcessor beanAnnotationProcessor = new BeanAnnotationProcessor();
        Set<Class<?>> beans = beanAnnotationProcessor.getAllBeanClasses();
        beanAnnotationProcessor.initAllBeans(beans);
    }
}
