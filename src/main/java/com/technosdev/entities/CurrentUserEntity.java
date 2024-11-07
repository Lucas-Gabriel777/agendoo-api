package com.technosdev.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CurrentUserEntity {

    private Long id;
    private String name;
    private String login;
    private List<String> permissions;

    private CompanyEntity companyEntity;

    public CurrentUserEntity(Long id, String name, String login, List<String> permissions , CompanyEntity companyEntity) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.permissions = permissions;
        this.companyEntity = companyEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissions() {

        if(this.permissions == null){
            return new ArrayList<String>();
        }

        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

}

