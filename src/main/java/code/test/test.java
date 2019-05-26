package code.test;

import code.entity.UserContext;
import code.state.OnlineState;

public class test {

    public static void main(String[] args) {
        UserContext userContext = new UserContext();
        userContext.setUserState(new OnlineState());
        System.out.println("当前状态："+ userContext.getUserState().getClass().getSimpleName());
        System.out.println("================================");


        userContext.offline();
        System.out.println("当前状态："+ userContext.getUserState().getClass().getSimpleName());
        System.out.println("================================");
        userContext.online();
        System.out.println("当前状态："+ userContext.getUserState().getClass().getSimpleName());
        System.out.println("================================");
    }
}
