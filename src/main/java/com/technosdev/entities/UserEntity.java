package com.technosdev.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_user")
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 1, max = 80, message = "O nome tem que ter no máximo 100 caracteres")
    @Column(nullable = false, length = 80 , name = "nm_user")
    private String name;

    @Column(length = 100 , name = "email_user")
    private String email;

    @Column(name = "password")
    private String password;

    @CPF(message = "Formato de CPF inválido. Certifique-se de que o CPF esteja no formato correto e tente novamente")
    @Column(unique = true, length = 20 , name = "nmr_cpf")
    private String cpf;

    @NotBlank(message = "O campo telefone é obrigatório")
    @Size(min = 12 , max = 12 , message = "Número de telefone mal formatado. Verifique o formato e tente novamente.")
    @Column(nullable = false, length = 20 , name = "nmr_phone")
    private String phone;

    @Column(nullable = false)
    private boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    @Transient
    private List<RoleEntity> roleEntities;

    public UserEntity() {
    }

    public UserEntity(String name, String email, String password, String cpf, String phone, boolean active) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phone = phone;
        this.active = active;
    }

    public UserEntity(Long codClient, String email, String nmClient, String nmrPhone, String password, boolean active, String nmrCpf, Date createdAt) {
        this.id = codClient;
        this.name = nmClient;
        this.email = email;
        this.phone = nmrPhone;
        this.active = active;
        this.password = password;
        this.cpf = nmrCpf;
        this.createdAt = createdAt;
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

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<RoleEntity> getRoles() { return roleEntities; }

    public void setRoles(List<RoleEntity> roleEntities) { this.roleEntities = roleEntities; }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(id, userEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cpf='" + cpf + '\'' +
                ", phone='" + phone + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}
