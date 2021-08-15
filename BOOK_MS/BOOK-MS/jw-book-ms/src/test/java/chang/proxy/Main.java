package chang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        ClassLoader classLoader = userService.getClass().getClassLoader();
        Class<?>[] interfaces = userService.getClass().getInterfaces();
        InvocationHandler userServiceProxy = new UserServiceProxy(userService);
        UserService proxy = (UserService)Proxy.newProxyInstance(classLoader, interfaces, userServiceProxy);
        proxy.add();
        proxy.find();
        //  ProxyUtils.generateClassFile(userService.getClass(), "UserServiceProxy1");
    }

}
