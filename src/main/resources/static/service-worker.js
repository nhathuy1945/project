self.addEventListener('push', function(event) {
    const data = event.data.json();
    self.registration.showNotification(data.title, {
        body: data.body,
        icon: '/images/pregnancy.png'
    });
});

self.addEventListener('notificationclick', function(event) {
    event.notification.close();
    event.waitUntil(
        clients.openWindow('/member/tracking')
    );
});