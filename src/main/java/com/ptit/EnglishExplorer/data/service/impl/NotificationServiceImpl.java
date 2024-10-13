package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.entity.Notification;
import com.ptit.EnglishExplorer.data.repository.LessonRepository;
import com.ptit.EnglishExplorer.data.repository.NotificationRepository;
import com.ptit.EnglishExplorer.data.service.LessonService;
import com.ptit.EnglishExplorer.data.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends BaseServiceImpl<Notification, Long, NotificationRepository> implements NotificationService {

    @Autowired
    public NotificationServiceImpl(NotificationRepository repository) {
        super(repository);
    }

}
