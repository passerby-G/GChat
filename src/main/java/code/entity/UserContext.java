package code.entity;


import code.state.OfflineState;
import code.state.OnlineState;
import code.state.UserState;

public class UserContext {
    private UserState userState;

    public final static OnlineState ONLINE_STATE = new OnlineState();
    public final static OfflineState OFFLINE_STATE = new OfflineState();

    public void setUserState(UserState userState) {
        this.userState = userState;
        this.userState.setUserContext(this);
    }

    public UserState getUserState() {
        return userState;
    }

    public void online() {
        this.userState.online();

    }

    public void offline() {
        this.userState.offline();
    }
}
