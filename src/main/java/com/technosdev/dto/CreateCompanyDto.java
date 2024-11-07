package com.technosdev.dto;

public class CreateCompanyDto {
    private Long codAddress;
    private Long codUser;
    private Long codFlowInformations;
    private Long codCompanyInformations;

    public CreateCompanyDto(Long codAddress, Long codUser, Long codFlowInformations, Long codCompanyInformations) {
        this.codAddress = codAddress;
        this.codUser = codUser;
        this.codFlowInformations = codFlowInformations;
        this.codCompanyInformations = codCompanyInformations;
    }

    public Long getCodAddress() {
        return codAddress;
    }

    public void setCodAddress(Long codAddress) {
        this.codAddress = codAddress;
    }

    public Long getCodUser() {
        return codUser;
    }

    public void setCodUser(Long codUser) {
        this.codUser = codUser;
    }

    public Long getCodFlowInformations() {
        return codFlowInformations;
    }

    public void setCodFlowInformations(Long codFlowInformations) {
        this.codFlowInformations = codFlowInformations;
    }

    public Long getCodCompanyInformations() {
        return codCompanyInformations;
    }

    public void setCodCompanyInformations(Long codCompanyInformations) {
        this.codCompanyInformations = codCompanyInformations;
    }
}
