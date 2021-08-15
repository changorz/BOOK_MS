package chang.proxy;

import java.io.Serializable;

public class UserServiceImpl implements UserService, UserServicePlus{


    @Override
    public void add() {
        System.out.println("add");
    }

    @Override
    public void find() {
        System.out.println("find");
    }

    @Override
    public void adds() {
        System.out.println("addsssssssssssssssssss");
    }
}
