package com.youcode.petPlanet.auth.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.youcode.petPlanet.auth.user.Permission.*;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,
                  BLOGAUTHOR_READ,
                  BLOGAUTHOR_UPDATE,
                  BLOGAUTHOR_DELETE,
                  BLOGAUTHOR_CREATE,
                  CLIENT_READ,
                  CLIENT_UPDATE,
                  CLIENT_DELETE,
                  CLIENT_CREATE
          )
  ),
  BLOGAUTHOR(
          Set.of(
                  BLOGAUTHOR_READ,
                  BLOGAUTHOR_UPDATE,
                  BLOGAUTHOR_DELETE,
                  BLOGAUTHOR_CREATE
          )
  ),
  CLIENT(
          Set.of(
                  CLIENT_READ,
                  CLIENT_UPDATE,
                  CLIENT_DELETE,
                  CLIENT_CREATE
          )
  );

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
