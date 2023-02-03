
package com.hetun.datacenter.bean;


import jakarta.persistence.*;

@Entity
@Table(name = "login_table")
public class LoginBean {
    public LoginBean() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginBean loginBean)) return false;

        if (getUsername() != null ? !getUsername().equals(loginBean.getUsername()) : loginBean.getUsername() != null)
            return false;
        return getPassword() != null ? getPassword().equals(loginBean.getPassword()) : loginBean.getPassword() == null;
    }
}
