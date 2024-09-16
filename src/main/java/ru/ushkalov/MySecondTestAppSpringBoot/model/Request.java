package ru.ushkalov.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotBlank
    private String uid;
    @NotBlank
    private String operationUid;
    private String systemName;
    @NotBlank
    private String systemTime;
    private String source;
    @Min(1)
    @Max(100000)
    private int communicationId;
    private int templateId;
    private int productCode;
    private int smsCode;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Request{");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", operationUid='").append(operationUid).append('\'');
        sb.append(", systemName='").append(systemName).append('\'');
        sb.append(", systemTime='").append(systemTime).append('\'');
        sb.append(", source='").append(source).append('\'');
        sb.append(", communicationId=").append(communicationId);
        sb.append(", templateId=").append(templateId);
        sb.append(", productCode=").append(productCode);
        sb.append(", smsCode=").append(smsCode);
        sb.append('}');
        return sb.toString();
    }
}
