package ru.itis.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.listeners.SpringContext;
import ru.itis.listeners.SpringContextServletContextListener;
import ru.itis.models.State;
import ru.itis.models.User;
import ru.itis.services.UsersService;

import javax.persistence.Transient;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {


    transient private User user;
    long id;

    @Autowired
    @Qualifier(value = "customUserDetailsService")
    transient UsersService usersService;

    public UserDetailsImpl(User user) {
        this.user = user;
        this.id = user.getId();
    }

    public User getUser() {
        if (usersService == null) {
            usersService= SpringContext.getBean(UsersService.class);
        }
        if (user == null)
            user = usersService.getUser(id).get();
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = getUser().getRole().toString();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);

        return Collections.singleton(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return getUser().getHashPassword();
    }

    @Override
    public String getUsername() {
        return getUser().getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getUser().getState().equals(State.CONFIRMED);
    }
}
