package com.zource.model.jsonViews;

public class View {

    //Enclosing type to define User views
    public static interface UserView {
        //External View for User
        public static interface NoPassword {
        }
        //Intenal View for User, will inherit all filds in External
        public static interface Full extends NoPassword {
        }
    }
}
