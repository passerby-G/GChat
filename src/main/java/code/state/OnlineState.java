package code.state;

import code.entity.UserContext;

public class OnlineState extends UserState {


    public void online() {

        System.out.println("用户在线");

    }

    public void offline() {
        super.userContext.setUserState(UserContext.OFFLINE_STATE);
        super.userContext.offline();
    }
}
