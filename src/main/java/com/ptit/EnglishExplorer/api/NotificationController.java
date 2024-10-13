package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.entity.Notification;
import com.ptit.EnglishExplorer.data.service.LessonService;
import com.ptit.EnglishExplorer.data.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/notifications")
public class NotificationController extends BaseController<Notification, Long, NotificationService> {

    @Autowired
    public NotificationController(NotificationService service) {
        super(service);
    }
}
