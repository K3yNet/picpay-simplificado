package com.pagamentos.picpay_simplificado.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notificationClient", url = "https://util.devi.tools/api/v1")
public interface NotificationClient {
    @PostMapping("/notify")
    void sendNotification();
}
