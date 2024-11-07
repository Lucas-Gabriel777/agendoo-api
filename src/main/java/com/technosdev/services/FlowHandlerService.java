package com.technosdev.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technosdev.dto.CreateUserDto;
import com.technosdev.entities.*;
import com.technosdev.enums.*;
import com.technosdev.flow.entities.*;
import com.technosdev.flow.UtilsFlow;
import com.technosdev.resources.FlowHandlerResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;


@Service
public class FlowHandlerService {
    private static final Logger LOGGER = Logger.getLogger(FlowHandlerResource.class.getName());

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ConfigFlow configFlow;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeCompanyService employeeCompanyService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeServiceService employeeServiceService;

    @Autowired
    private UserService userService;

    @Autowired
    private SchedulingStatusService schedulingStatusService;

    @Autowired
    private SchedulingService schedulingService;

    @Autowired
    private ServiceOrderService serviceOrderService;

    @Autowired
    private FlowInformationsService flowInformationsService;

    private final NumberFormat formatBR = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public String encryptedStream(JsonNode jsonNode) {
        try {
            JsonNode encryptedFlowData = jsonNode.get("encrypted_flow_data");
            JsonNode encryptedAesKey = jsonNode.get("encrypted_aes_key");
            JsonNode initialVector = jsonNode.get("initial_vector");

            byte[] encrypted_flow_data = Base64.getDecoder().decode(encryptedFlowData.asText());
            byte[] encrypted_aes_key = Base64.getDecoder().decode(encryptedAesKey.asText());
            byte[] initial_vector = Base64.getDecoder().decode(initialVector.asText());

            LOGGER.info("Starting decryption process");
            DecryptionInfo decryptionInfo = UtilsFlow.decryptRequestPayload(encrypted_flow_data, encrypted_aes_key, initial_vector);

            System.out.println(decryptionInfo.clearPayload);

            ObjectMapper objectMapper = new ObjectMapper();
            BodyFlow bodyFlow = objectMapper.readValue(decryptionInfo.clearPayload, BodyFlow.class);

            String response = getResponse(bodyFlow);
            return UtilsFlow.encryptAndEncodeResponse(response, decryptionInfo.clearAesKey, UtilsFlow.flipIv(initial_vector));
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"version\": \"3.0\", \"data\": {\"acknowledged\": \"true\"}}";
        }
    }

    private String getResponse(BodyFlow bodyFlow) {
        switch (bodyFlow.getAction()) {
            case ActionMessageEnum.PING:
                return "{\"data\": {\"status\": \"active\"}}";
            case ActionMessageEnum.INIT:
                return "{\"screen\": \"INFORMATIONS\", \"data\": {\"company_phone_number\": \"" + bodyFlow.getData().getCompanyNumber() + "\"}}";
            case ActionMessageEnum.DATA_EXCHANGE:
                switch (bodyFlow.getScreen()) {
                    case ScreenFlowEnum.INFORMATIONS:
                        List<EmployeeScheduling> employeeSchedulingList = new ArrayList<>();
                        employeeSchedulingList.add(new EmployeeScheduling("1", MessageBodyEnum.DEFAULT_MESSAGE, MessageBodyEnum.DEFAULT_MESSAGE));

                        List<ServiceScheduling> serviceSchedulingList = new ArrayList<>();
                        serviceSchedulingList.add(new ServiceScheduling("1", MessageBodyEnum.DEFAULT_MESSAGE, MessageBodyEnum.DEFAULT_MESSAGE));

                        List<TimeScheduling> timeSchedulingList = new ArrayList<>();
                        timeSchedulingList.add(new TimeScheduling("1", MessageBodyEnum.DEFAULT_MESSAGE, false));

                        return showSchedulingScreen(false, false, bodyFlow, employeeSchedulingList, serviceSchedulingList, timeSchedulingList);
                    case ScreenFlowEnum.SCHEDULING:
                        return validationScheduling(bodyFlow);
                }
        }

        return "{\"version\": \"3.0\", \"data\": {\"acknowledged\": \"true\"}}";
    }

    private String validationScheduling(BodyFlow bodyFlow) {
        boolean validDay = isValidDay(bodyFlow);
        boolean validEmployee = isValidEmployee(bodyFlow);

        boolean isService = false;
        boolean isTime = false;

        if (validEmployee && validDay) {
            isService = true;
            isTime = true;
        }

        List<EmployeeScheduling> employeeSchedulingList = getEmployeeSchedulingList(bodyFlow.getData().getCompanyNumber());
        List<ServiceScheduling> serviceSchedulingList = getServicesByEmployeeId(bodyFlow.getData().getEmployeeId());
        List<TimeScheduling> timeSchedulingList = getTimesByEmployeeId(bodyFlow.getData());

        return showSchedulingScreen(isService, isTime, bodyFlow, employeeSchedulingList, serviceSchedulingList, timeSchedulingList);
    }

    private List<EmployeeScheduling> getEmployeeSchedulingList(String companyNumber) {
        FlowInformationsEntity flowInformationsEntity = flowInformationsService.findByPhone(companyNumber);
        CompanyEntity companyEntity = companyService.findByCodFlowInformations(flowInformationsEntity.getId());

        List<EmployeeScheduling> employeeSchedulingList = new ArrayList<>();
        List<EmployeeCompany> employeeCompanyList = employeeCompanyService.findByCodCompany(companyEntity.getId());

        for (EmployeeCompany employeeCompany : employeeCompanyList) {
            EmployeeScheduling employeeScheduling = new EmployeeScheduling();

            employeeScheduling.setId(String.valueOf(employeeCompany.getId()));
            employeeScheduling.setTitle(employeeCompany.getEmployee().getUserEntity().getName());
            employeeScheduling.setMetadata(employeeCompany.getEmployee().getContactUser());

            employeeSchedulingList.add(employeeScheduling);
        }

        return employeeSchedulingList;
    }

    private List<ServiceScheduling> getServicesByEmployeeId(String employeeId) {
        List<ServiceScheduling> serviceSchedulingList = new ArrayList<>();

        if (employeeId != null && !employeeId.isEmpty()) {
            EmployeeEntity employeeEntity = employeeService.findById(Long.parseLong(employeeId));

            List<EmployeeServiceEntity> employeeServiceEntityList = employeeServiceService.listServicesByCodEmployee(employeeEntity.getId());

            for (EmployeeServiceEntity employeeServiceEntity : employeeServiceEntityList) {
                ServiceScheduling serviceScheduling = new ServiceScheduling();

                serviceScheduling.setId(String.valueOf(employeeServiceEntity.getId()));
                serviceScheduling.setTitle(employeeServiceEntity.getService().getName());
                serviceScheduling.setMetadata(formatBR.format(employeeServiceEntity.getService().getPrice()));

                serviceSchedulingList.add(serviceScheduling);
            }
        } else {
            serviceSchedulingList.add(new ServiceScheduling("1", MessageBodyEnum.DEFAULT_MESSAGE, MessageBodyEnum.DEFAULT_MESSAGE));
        }

        return serviceSchedulingList;
    }

    private List<TimeScheduling> getTimesByEmployeeId(Data data) {
        FlowInformationsEntity flowInformationsEntity = flowInformationsService.findByPhone(data.getCompanyNumber());
        CompanyEntity companyEntity = companyService.findByCodFlowInformations(flowInformationsEntity.getId());

        List<TimeScheduling> timeSchedulingList = new ArrayList<>();

        if (!data.getEmployeeId().isEmpty()) {
            Date schedulingDate = new Date(Long.parseLong(data.getDay()));
            List<SchedulingEntity> schedulingList = schedulingService.getSchedulingByEmployeeAndDate(Long.parseLong(data.getEmployeeId()), schedulingDate);

            LocalTime startTime = companyEntity.getCompanyInformationsEntity().getStartTime().toLocalTime();
            LocalTime endTime = companyEntity.getCompanyInformationsEntity().getEndTime().toLocalTime();

            while (!startTime.isAfter(endTime)) {
                String formattedTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime currentStartTime = startTime;

                boolean isAvailable = schedulingList.stream()
                        .noneMatch(s -> s.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime().equals(currentStartTime));

                timeSchedulingList.add(new TimeScheduling(formattedTime, formattedTime, isAvailable));

                startTime = startTime.plusMinutes(companyEntity.getCompanyInformationsEntity().getAverageTimeWork());
            }
        } else {
            timeSchedulingList.add(new TimeScheduling("1", MessageBodyEnum.DEFAULT_MESSAGE, false));
        }

        return timeSchedulingList;
    }

    private boolean isValidDay(BodyFlow bodyFlow) {
        if (bodyFlow.getData().getDay() == null) {
            bodyFlow.getData().setDay("");
        }

        return !bodyFlow.getData().getDay().replace(" ", "").equals("");
    }

    private boolean isValidEmployee(BodyFlow bodyFlow) {
        if (bodyFlow.getData().getEmployeeId() == null) {
            bodyFlow.getData().setEmployeeId("");
        }

       return !bodyFlow.getData().getEmployeeId().replace(" ", "").equals("");
    }

    private String showSchedulingScreen(boolean isServices, boolean isTime, BodyFlow bodyFlow, List<EmployeeScheduling> employeeSchedulingList, List<ServiceScheduling> serviceSchedulingList, List<TimeScheduling> timeSchedulingList) {
        StringBuilder jsonBuilder = new StringBuilder();

        jsonBuilder.append("{")
                .append("\"screen\": \"SCHEDULING\", ")
                .append("\"data\": {")
                .append("\"clientName\": \"").append(bodyFlow.getData().getClientName()).append("\", ")
                .append("\"cpf\": \"").append(bodyFlow.getData().getCpf()).append("\", ")
                .append("\"termsAccept\": ").append(bodyFlow.getData().isTermsAccept()).append(", ")
                .append("\"companyNumber\": \"").append(bodyFlow.getData().getCompanyNumber()).append("\", ")
                .append("\"is_services\": ").append(isServices).append(", ")
                .append("\"is_time\": ").append(isTime).append(", ")
                .append("\"employees\": [");

        for (int i = 0; i < employeeSchedulingList.size(); i++) {
            EmployeeScheduling barber = employeeSchedulingList.get(i);
            jsonBuilder.append("{")
                    .append("\"id\": \"").append(barber.getId()).append("\", ")
                    .append("\"title\": \"").append(barber.getTitle()).append("\", ")
                    .append("\"metadata\": \"").append(barber.getMetadata()).append("\"")
                    .append("}");

            if (i < employeeSchedulingList.size() - 1) {
                jsonBuilder.append(", ");
            }
        }

        jsonBuilder.append("], ")
                .append("\"services\": [");

        for (int i = 0; i < serviceSchedulingList.size(); i++) {
            ServiceScheduling service = serviceSchedulingList.get(i);
            jsonBuilder.append("{")
                    .append("\"id\": \"").append(service.getId()).append("\", ")
                    .append("\"title\": \"").append(service.getTitle()).append("\", ")
                    .append("\"metadata\": \"").append(service.getMetadata()).append("\"")
                    .append("}");

            if (i < serviceSchedulingList.size() - 1) {
                jsonBuilder.append(", ");
            }
        }

        jsonBuilder.append("],")
                   .append("\"times\": [");

        for (int i = 0; i < timeSchedulingList.size(); i++) {
            TimeScheduling timeScheduling = timeSchedulingList.get(i);
            jsonBuilder.append("{")
                    .append("\"id\": \"").append(timeScheduling.getId()).append("\", ")
                    .append("\"title\": \"").append(timeScheduling.getTitle()).append("\", ")
                    .append("\"enabled\": ").append(timeScheduling.isEnabled())
                    .append("}");

            if (i < timeSchedulingList.size() - 1) {
                jsonBuilder.append(", ");
            }
        }

        jsonBuilder.append("]")
                   .append("}}");

        return jsonBuilder.toString();
    }

    public String receivedMessage(JsonNode jsonNode) {
        try {
            JsonNode entryNode = jsonNode.path("entry").get(0);
            JsonNode changesNode = entryNode.path("changes").get(0);
            JsonNode valueNode = changesNode.path("value");
            JsonNode messagesNode = valueNode.path("messages").get(0);
            JsonNode metadata = valueNode.path("metadata");

            if (messagesNode != null) {
                String messageType = messagesNode.path("type").asText();

                String phoneNumber = messagesNode.path("from").asText();
                String companyPhoneNumber = metadata.path("display_phone_number").asText();

                String message = treatMessage(messageType, messagesNode, phoneNumber, companyPhoneNumber);

                switch (message) {
                    case MessageBodyEnum.SCHEDULING:
                        sendFlow(phoneNumber, companyPhoneNumber);
                        break;
                    case MessageBodyEnum.TALK_TO_COMPANY:
                        sendContact(phoneNumber, companyPhoneNumber);
                        break;
                    case MessageBodyEnum.FLOW_COMPLETED:
                        break;
                    case MessageBodyEnum.COMPANY_LOCATION:
                        sendCompanyLocation(phoneNumber, companyPhoneNumber);
                        break;
                    default:
                        sendDefaultMessage(phoneNumber, companyPhoneNumber);
                }

                return MessageStatusEnum.SUCCESS_MESSAGE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MessageStatusEnum.ERROR_MESSAGE;
        }

        return MessageStatusEnum.ERROR_MESSAGE;
    }

    private String treatMessage(String messageType, JsonNode messagesNode, String clientPhoneNumber, String companyPhoneNumber) {
        String message;

        if ("interactive".equals(messageType)) {
            JsonNode interactiveNode = messagesNode.path("interactive");
            String interactiveType = interactiveNode.path("type").asText();

            if ("button_reply".equals(interactiveType)) {
                message = interactiveNode.path("button_reply").path("title").asText();
            } else if ("nfm_reply".equals(interactiveType)) {
                ServiceOrderEntity serviceOrderEntity = flowCompleted(interactiveNode, clientPhoneNumber);
                sendConfirmationMessage(clientPhoneNumber, companyPhoneNumber, serviceOrderEntity);
                message = "Flow completed";
            } else {
                message = "Unknown interactive type";
            }
        } else {
            JsonNode textNode = messagesNode.path("text");
            message = textNode.path("body").asText();
        }

        return message;
    }

    private ServiceOrderEntity flowCompleted(JsonNode interactiveNode, String clientPhoneNumber) {
        try {
            String responseJson = interactiveNode.path("nfm_reply").path("response_json").asText();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(responseJson);

            String selectTime = responseNode.path("selectTime").asText();
            String employeeId = responseNode.path("employeeId").asText();
            String selectService = responseNode.path("selectService").asText();
            String clientName = responseNode.path("clientName").asText();
            String cpf = responseNode.path("cpf").asText();
            String selectDay = responseNode.path("selectDay").asText();
            String companyNumber = responseNode.path("companyNumber").asText();

            SchedulingEntity schedulingEntity = createScheduling(cpf, clientName, clientPhoneNumber, companyNumber, selectDay, selectTime);
            return createServiceOrder(employeeId, selectService, schedulingEntity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ServiceOrderEntity createServiceOrder(String employeeId, String selectService, SchedulingEntity schedulingEntity) {
        EmployeeEntity employeeEntity = employeeService.findById(Long.parseLong(employeeId));
        EmployeeServiceEntity employeeServiceEntity = employeeServiceService.findById(Long.parseLong(selectService));

        ServiceOrderEntity serviceOrderEntity = new ServiceOrderEntity();
        serviceOrderEntity.setScheduling(schedulingEntity);
        serviceOrderEntity.setService(employeeServiceEntity.getService());
        serviceOrderEntity.setEmployee(employeeEntity);

        serviceOrderService.insert(serviceOrderEntity);

        return serviceOrderEntity;
    }

    private SchedulingEntity createScheduling(String cpf, String clientName, String clientPhoneNumber, String companyNumber, String selectDay, String selectTime) {
        UserEntity userEntity = userService.findByCPF(cpf);

        if (userEntity == null) {
            CreateUserDto createUserDto = new CreateUserDto(clientName, null, null, cpf, clientPhoneNumber, UserRolesEnum.CLIENT);
            userEntity = userService.insert(createUserDto);
        }

        FlowInformationsEntity flowInformationsEntity = flowInformationsService.findByPhone(companyNumber);
        CompanyEntity companyEntity = companyService.findByCodFlowInformations(flowInformationsEntity.getId());

        SchedulingStatusEntity schedulingStatusEntity = schedulingStatusService.findById(SchedulingStatusEnum.SCHEDULED);
        Date date = getSchedulingDate(selectDay, selectTime);

        SchedulingEntity schedulingEntity = new SchedulingEntity(userEntity, schedulingStatusEntity, companyEntity, date);
        schedulingService.insert(schedulingEntity);

        return schedulingEntity;
    }

    private Date getSchedulingDate(String selectDay, String selectTime) {
        Long selectedDayMillis = Long.parseLong(selectDay);

        Instant instant = Instant.ofEpochMilli(selectedDayMillis);

        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        String[] timeParts = selectTime.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);

        dateTime = dateTime.withHour(hours).withMinute(minutes);

        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
        Instant finalInstant = zonedDateTime.toInstant();

        return Date.from(finalInstant);
    }

    private String formatDate(Date date) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return dateTime.format(outputFormatter);
    }

    private void sendMessage(String body, String phoneNumberId) {
        WebClient webClient = webClientBuilder.baseUrl("https://graph.facebook.com/v20.0").build();

        webClient.post()
                .uri("/" + phoneNumberId + "/messages")
                .header("Authorization", "Bearer " + configFlow.getACCESS_TOKEN())
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Response: " + response))
                .doOnError(error -> System.err.println("Request failed: " + error.getMessage()))
                .block();
    }

    private void sendFlow(String clientPhoneNumber, String companyPhoneNumber) {
        FlowInformationsEntity flowInformationsEntity = flowInformationsService.findByPhone(companyPhoneNumber);

        StringBuilder body = new StringBuilder();
        body.append("{\n")
            .append("    \"messaging_product\": \"whatsapp\",\n")
            .append("    \"to\": \"").append(clientPhoneNumber).append("\",\n")
            .append("    \"type\": \"interactive\",\n")
            .append("    \"interactive\": {\n")
            .append("        \"type\": \"flow\",\n")
            .append("        \"body\": {\n")
            .append("            \"text\": \"").append(flowInformationsEntity.getFlowMessage()).append("\"\n")
            .append("        },\n")
            .append("        \"footer\": {\n")
            .append("            \"text\": \"Powered by technosdev\"\n")
            .append("        },\n")
            .append("        \"action\": {\n")
            .append("            \"name\": \"flow\",\n")
            .append("            \"parameters\": {\n")
            .append("                \"flow_message_version\": \"3\",\n")
            .append("                \"flow_token\": \"FLOW_TOKEN\",\n")
            .append("                \"flow_id\": \"").append(flowInformationsEntity.getFlowId()).append("\",\n")
            .append("                \"flow_cta\": \"").append(flowInformationsEntity.getFlowCta()).append("\",\n")
            .append("                \"flow_action\": \"navigate\",\n")
            .append("                \"flow_action_payload\": {\n")
            .append("                    \"screen\": \"INFORMATIONS\",\n")
            .append("                    \"data\": {\n")
            .append("                        \"company_phone_number\": \"").append(companyPhoneNumber).append("\"\n")
            .append("                    }\n")
            .append("                }\n")
            .append("            }\n")
            .append("        }\n")
            .append("    }\n")
            .append("}");

        sendMessage(body.toString(), flowInformationsEntity.getPhoneNumberId());
    }

    private void sendDefaultMessage(String clientPhoneNumber, String companyPhoneNumber) {
        FlowInformationsEntity flowInformationsEntity = flowInformationsService.findByPhone(companyPhoneNumber);

        StringBuilder body = new StringBuilder();
        body.append("{\n")
                .append("  \"messaging_product\": \"whatsapp\",\n")
                .append("  \"recipient_type\": \"individual\",\n")
                .append("  \"to\": \"").append(clientPhoneNumber).append("\",\n")
                .append("  \"type\": \"interactive\",\n")
                .append("  \"interactive\": {\n")
                .append("    \"type\": \"button\",\n")
                .append("    \"body\": {\n")
                .append("      \"text\": \"").append(flowInformationsEntity.getDefaultMessage()).append("\"\n")
                .append("    },\n")
                .append("    \"footer\": {\n")
                .append("      \"text\": \"Powered by technosdev\"\n")
                .append("    },\n")
                .append("    \"action\": {\n")
                .append("      \"buttons\": [\n")
                .append("        {\n")
                .append("          \"type\": \"reply\",\n")
                .append("          \"reply\": {\n")
                .append("            \"id\": \"scheduling\",\n")
                .append("            \"title\": \"Agendamento\"\n")
                .append("          }\n")
                .append("        },\n")
                .append("        {\n")
                .append("          \"type\": \"reply\",\n")
                .append("          \"reply\": {\n")
                .append("            \"id\": \"talkToCompany\",\n")
                .append("            \"title\": \"Falar com a empresa\"\n")
                .append("          }\n")
                .append("        },\n")
                .append("        {\n")
                .append("          \"type\": \"reply\",\n")
                .append("          \"reply\": {\n")
                .append("            \"id\": \"companyLocation\",\n")
                .append("            \"title\": \"Localização\"\n")
                .append("          }\n")
                .append("        }\n")
                .append("      ]\n")
                .append("    }\n")
                .append("  }\n")
                .append("}");

        sendMessage(body.toString(), flowInformationsEntity.getPhoneNumberId());
    }

    private void sendContact(String clientPhoneNumber, String companyPhoneNumber) {
//        String body = "{\n" +
//                "  \"messaging_product\": \"whatsapp\",\n" +
//                "  \"to\": \"" + phoneNumber + "\",\n" +
//                "  \"type\": \"contacts\",\n" +
//                "  \"contacts\": [\n" +
//                "    {\n" +
//                "      \"addresses\": [\n" +
//                "        {\n" +
//                "          \"street\": \"123 Main St\",\n" +
//                "          \"city\": \"Metropolis\",\n" +
//                "          \"state\": \"NY\",\n" +
//                "          \"zip\": \"12345\",\n" +
//                "          \"country\": \"USA\",\n" +
//                "          \"country_code\": \"US\",\n" +
//                "          \"type\": \"home\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"emails\": [\n" +
//                "        {\n" +
//                "          \"email\": \"pedro@technosdev.com\",\n" +
//                "          \"type\": \"personal\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"name\": {\n" +
//                "        \"formatted_name\": \"Pedro Teloeken\",\n" +
//                "        \"first_name\": \"Pedro\",\n" +
//                "        \"last_name\": \"Teloeken\",\n" +
//                "      },\n" +
//                "      \"org\": {\n" +
//                "        \"company\": \"technosdev\",\n" +
//                "        \"department\": \"Desenvolvimento\",\n" +
//                "        \"title\": \"Software Engineer\"\n" +
//                "      },\n" +
//                "      \"phones\": [\n" +
//                "        {\n" +
//                "          \"phone\": \"+554784555508\",\n" +
//                "          \"type\": \"mobile\",\n" +
//                "          \"wa_id\": \"554784555508\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"urls\": [\n" +
//                "        {\n" +
//                "          \"url\": \"https://technosdev.com.br/\",\n" +
//                "          \"type\": \"website\"\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";

        FlowInformationsEntity flowInformationsEntity = flowInformationsService.findByPhone(companyPhoneNumber);
        CompanyEntity companyEntity = companyService.findByCodFlowInformations(flowInformationsEntity.getId());

        StringBuilder body = new StringBuilder();
        body.append("{\n")
            .append("  \"messaging_product\": \"whatsapp\",\n")
            .append("  \"to\": \"").append(clientPhoneNumber).append("\",\n")
            .append("  \"type\": \"contacts\",\n")
            .append("  \"contacts\": [\n")
            .append("    {\n")
            .append("      \"name\": {\n")
            .append("        \"formatted_name\": \"").append(companyEntity.getCompanyInformationsEntity().getName()).append("\",\n")
            .append("        \"first_name\": \"Empresa\",\n")
            .append("        \"last_name\": \"").append(companyEntity.getCompanyInformationsEntity().getName()).append("\"\n")
            .append("      },\n")
            .append("      \"phones\": [\n")
            .append("        {\n")
            .append("          \"phone\": \"+").append(companyEntity.getCompanyInformationsEntity().getPhone()).append("\",\n")
            .append("          \"type\": \"mobile\",\n")
            .append("          \"wa_id\": \"").append(companyEntity.getCompanyInformationsEntity().getPhone()).append("\"\n")
            .append("        }\n")
            .append("      ]\n")
            .append("    }\n")
            .append("  ]\n")
            .append("}");

        sendMessage(body.toString(), flowInformationsEntity.getPhoneNumberId());
    }

    private void sendConfirmationMessage(String clientPhoneNumber, String companyPhoneNumber, ServiceOrderEntity serviceOrderEntity) {
        FlowInformationsEntity flowInformationsEntity = flowInformationsService.findByPhone(companyPhoneNumber);

        StringBuilder body = new StringBuilder();
        body.append("{\n");
        body.append("    \"messaging_product\": \"whatsapp\",\n");
        body.append("    \"recipient_type\": \"individual\",\n");
        body.append("    \"to\": \"" + clientPhoneNumber + "\",\n");
        body.append("    \"type\": \"text\",\n");
        body.append("    \"text\": {\n");
        body.append("        \"body\": \"*Agendamento concluído:*\\n\\n");
        body.append("*Serviço:* ");
        body.append(serviceOrderEntity.getService().getName());
        body.append("\\n*Funcionário(a):* ");
        body.append(serviceOrderEntity.getEmployee().getUserEntity().getName());
        body.append("\\n*Data e horário:* ");
        body.append(formatDate(serviceOrderEntity.getScheduling().getDate()));
        body.append("\\n*Valor:* ");
        body.append(formatBR.format(serviceOrderEntity.getService().getPrice()));
        body.append("\"\n");
        body.append("    }\n");
        body.append("}");

        sendMessage(body.toString(), flowInformationsEntity.getPhoneNumberId());
    }

    private void sendCompanyLocation(String clientPhoneNumber, String companyPhoneNumber) {
        FlowInformationsEntity flowInformationsEntity = flowInformationsService.findByPhone(companyPhoneNumber);
        CompanyEntity companyEntity = companyService.findByCodFlowInformations(flowInformationsEntity.getId());

        StringBuilder body = new StringBuilder();
        body.append("{")
            .append("\"messaging_product\": \"whatsapp\",")
            .append("\"recipient_type\": \"individual\",")
            .append("\"to\": \"").append(clientPhoneNumber).append("\",")
            .append("\"type\": \"location\",")
            .append("\"location\": {")
            .append("\"latitude\": \"").append(companyEntity.getAddressEntity().getLatitude()).append("\",")
            .append("\"longitude\": \"").append(companyEntity.getAddressEntity().getLongitude()).append("\",")
            .append("\"name\": \"").append(companyEntity.getCompanyInformationsEntity().getName()).append("\",")
            .append("\"address\": \"").append(companyEntity.getAddressEntity().getFullAddress()).append("\"")
            .append("}")
            .append("}");

        sendMessage(body.toString(), flowInformationsEntity.getPhoneNumberId());
    }
}
