package ua.simpleservletframework.core.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ua.simpleservletframework.core.annotation.processor.component.AutowiredAnnotationProcessor;
import ua.simpleservletframework.core.annotation.processor.component.BeanAnnotationProcessor;

import java.util.Set;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //init beans
        BeanAnnotationProcessor beanAnnotationProcessor = new BeanAnnotationProcessor();
        Set<Class<?>> beans = beanAnnotationProcessor.getAllBeanClasses();
        beanAnnotationProcessor.initBeans(beans);

        //inject beans
        AutowiredAnnotationProcessor autowiredAnnotationProcessor = new AutowiredAnnotationProcessor();
        autowiredAnnotationProcessor.injectBeans();
    }
}
