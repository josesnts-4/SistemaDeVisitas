package com.inter.SistemaDeVisitas.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements UserDetails {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private boolean enabled = true;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RoleGroup roleGroup = RoleGroup.USER;

  // ===== UserDetails =====
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + roleGroup.name()));
  }

  @Override public String getUsername() { return email; }
  @Override public String getPassword() { return password; }
  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return enabled; }

  // ===== Getters/Setters =====
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public void setPassword(String password) { this.password = password; }
  public boolean getEnabled() { return enabled; }
  public void setEnabled(boolean enabled) { this.enabled = enabled; }
  public RoleGroup getRoleGroup() { return roleGroup; }
  public void setRoleGroup(RoleGroup roleGroup) { this.roleGroup = roleGroup; }
}
