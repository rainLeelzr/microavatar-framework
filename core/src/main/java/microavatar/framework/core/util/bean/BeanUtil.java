package microavatar.framework.core.util.bean;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

public class BeanUtil {

    /**
     * 创建一个java对象，并添加到spring，让spring管理
     */
    public static <T> T addBeanToSpringContext(ApplicationContext context, Class<T> beanClass) {
        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();

        //创建bean信息
        String beanName = getBeanNameByBeanType(beanClass);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);

        //动态注册bean
        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());

        @SuppressWarnings("unchecked")
        T bean = (T) context.getBean(beanName);
        return bean;
    }

    /**
     * 根据类名获取bean
     */
    public static <T> T getBeanByNameOfBeanType(ApplicationContext context, Class<T> beanClass) {
        String beanName = getBeanNameByBeanType(beanClass);
        @SuppressWarnings("unchecked")
        T bean = (T) context.getBean(beanName);
        return bean;
    }

    /**
     * 根据非限定类名获取bean的名称，即将类名的首字母小写
     */
    public static String getBeanNameByBeanType(Class beanClass) {
        String beanName = beanClass.getSimpleName();
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1, beanName.length());
    }

}
