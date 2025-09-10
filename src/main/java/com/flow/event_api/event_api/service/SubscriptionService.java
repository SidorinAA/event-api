package com.flow.event_api.event_api.service;


import com.flow.event_api.event_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final OrganizationService organizationService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;


    public void sendNotification(Long organization, Collection<Long> categoriesId, String eventName) {
        var emails = userService.getEmailsBySubscriptions(categoriesId, organization);
        emails.forEach(email -> {
            String title = "New event for one of your subscrioption";
            String body = MessageFormat.format("New event! Name is: {0}", eventName);
            emailSenderService.send(email, title, body);
        });
    }

    public void subscribeOnOrganization(Long organizationId, Long userId) {
        var currentUser = userService.findById(userId);
        currentUser.addSubscription(organizationService.findById(organizationId));
    }

    public void subscribeOnCategory(Long categoryId, Long userId) {
        var currentUser = userService.findById(userId);
        currentUser.addSubscription(categoryService.findById(categoryId));
    }

    @Transactional
    public void unsubscribeOnOrganization(Long organizationId, Long userId) {
        var currentUser = userService.findById(userId);
        var removed = currentUser.removeOrganizationSubscription(organizationId);
        if (!removed) {
            return;
        }
        userService.save(currentUser);
    }

    @Transactional
    public void unsubscribeOnCategory(Long categoryId, Long userId) {
        var currentUser = userService.findById(userId);
        var removed = currentUser.removeCategorySubscription(categoryId);
        if (!removed) {
            return;
        }
        userService.save(currentUser);
    }

    public boolean hasCategorySubscription(Long categoryId, Long userId) {
        return userRepository.existsByIdAndSubscribedCategoriesId(categoryId, userId);
    }

    public boolean hasOrganizationSubscription(Long organizationId, Long userId) {
        return userRepository.existsByIdAndSubscribedOrganizationsId(organizationId, userId);
    }
}

