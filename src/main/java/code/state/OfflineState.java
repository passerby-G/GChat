package code.state;

import code.entity.UserContext;

public class OfflineState extends UserState {

    public void online() {
        super.userContext.setUserState(UserContext.ONLINE_STATE);
        super.userContext.online();
    }

    public void offline() {

        System.out.println("用户下线");
    }
}
