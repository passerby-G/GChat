package code.state;

import code.entity.UserContext;

public abstract class UserState {

    protected UserContext userContext;

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    public abstract void online();

    public abstract void offline();

}
