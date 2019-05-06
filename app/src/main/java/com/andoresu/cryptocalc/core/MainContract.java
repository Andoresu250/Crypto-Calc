package com.andoresu.cryptocalc.core;


import com.andoresu.cryptocalc.core.contact.ContactModel;
import com.andoresu.cryptocalc.utils.BaseUserActionListener;
import com.andoresu.cryptocalc.utils.BaseView;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {

    }

    interface ActionsListener extends BaseUserActionListener {

        void sendContact(List<ContactModel> contactModels);

    }

}