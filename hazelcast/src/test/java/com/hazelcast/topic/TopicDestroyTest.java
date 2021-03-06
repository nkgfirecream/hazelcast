package com.hazelcast.topic;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import com.hazelcast.spi.EventRegistration;
import com.hazelcast.spi.EventService;
import com.hazelcast.test.HazelcastSerialClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.QuickTest;
import com.hazelcast.topic.impl.TopicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(HazelcastSerialClassRunner.class)
@Category(QuickTest.class)
public class TopicDestroyTest extends HazelcastTestSupport {

    HazelcastInstance instance;
    ITopic<Object> topic;
    String topicName;

    @Before
    public void setup() {
        instance = createHazelcastInstance();
        topicName = randomString();
        topic = instance.getTopic(topicName);
    }

    @Test
    public void testDestroyTopicRemovesListeners() {
        topic.addMessageListener(new EmptyListener());
        topic.destroy();

        assertRegistrationSize(0);
    }

    @Test
    public void testRemovingListenersRemovesRegistrations() {
        String registrationId = topic.addMessageListener(new EmptyListener());
        topic.removeMessageListener(registrationId);

        assertRegistrationSize(0);
    }

    void assertRegistrationSize(int size) {
        EventService eventService = getNode(instance).getNodeEngine().getEventService();
        Collection<EventRegistration> regs = eventService.getRegistrations(TopicService.SERVICE_NAME, topicName);

        assertEquals(size, regs.size());
    }

    static class EmptyListener implements MessageListener<Object> {
        @Override
        public void onMessage(Message message) {
        }
    }
}