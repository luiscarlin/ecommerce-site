package com.ecommerce.service.currentuser;

import com.ecommerce.domain.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);
}
