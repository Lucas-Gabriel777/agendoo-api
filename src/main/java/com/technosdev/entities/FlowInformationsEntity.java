package com.technosdev.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "flow_informations")
public class FlowInformationsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_flow_informations")
    private Long id;

    @NotBlank(message = "O campo telefone é obrigatório")
    @Size(min = 12 , max = 12 , message = "Número de telefone mal formatado. Verifique o formato e tente novamente.")
    @Column(nullable = false, length = 20 , name = "nmr_phone")
    private String phone;

    @NotBlank(message = "O campo mensagem padrão é obrigatório")
    @Column(nullable = false, length = 150 , name = "default_message")
    private String defaultMessage;

    @NotBlank(message = "O campo mensagem do flow é obrigatório")
    @Column(nullable = false, length = 45 , name = "flow_message")
    private String flowMessage;

    @NotBlank(message = "O campo mensagem do botão do flow é obrigatório")
    @Column(nullable = false, length = 10 , name = "flow_cta")
    private String flowCta;

    @NotBlank(message = "O campo id do número do telefone é obrigatório")
    @Column(nullable = false, length = 45 , name = "phone_number_id")
    private String phoneNumberId;

    @Column(nullable = false, length = 45 , name = "flow_id")
    private String flowId;

    public FlowInformationsEntity() {
    }

    public FlowInformationsEntity(Long id, String phone, String defaultMessage, String flowMessage, String phoneNumberId, String flowId, String flowCta) {
        this.id = id;
        this.phone = phone;
        this.defaultMessage = defaultMessage;
        this.flowMessage = flowMessage;
        this.phoneNumberId = phoneNumberId;
        this.flowId = flowId;
        this.flowCta = flowCta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getFlowMessage() {
        return flowMessage;
    }

    public void setFlowMessage(String flowMessage) {
        this.flowMessage = flowMessage;
    }

    public String getFlowCta() {
        return flowCta;
    }

    public void setFlowCta(String flowCta) {
        this.flowCta = flowCta;
    }

    public String getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(String phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowInformationsEntity that = (FlowInformationsEntity) o;
        return Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(phone);
    }

    @Override
    public String toString() {
        return "FlowInformationsEntity{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", defaultMessage='" + defaultMessage + '\'' +
                ", flowMessage='" + flowMessage + '\'' +
                ", flowCta='" + flowCta + '\'' +
                ", phoneNumberId='" + phoneNumberId + '\'' +
                ", flowId='" + flowId + '\'' +
                '}';
    }
}
