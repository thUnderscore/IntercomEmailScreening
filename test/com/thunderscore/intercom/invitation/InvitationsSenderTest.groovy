package com.thunderscore.intercom.invitation

import com.thunderscore.intercom.mapper.ReaderMapper

class InvitationsSenderTest extends GroovyTestCase {
    void testSend() {

        def file = ReaderMapper.class.getResource("input.json").getFile();
        def sender = new InvitationsSender((double)53.3381985, (double)-6.2592576, (double) 100);
        sender.invite(file);
        assertEquals(sender.getInvitedCount(), 16);
        assertEquals(sender.getLastErrorsCount(), 0);
        assertFalse(sender.isLastFailed());

        sender = new InvitationsSender((double)53.3381985, (double)-6.2592576, (double) 50);
        sender.invite(file);
        assertEquals(sender.getInvitedCount(), 8);
        assertEquals(sender.getLastErrorsCount(), 0);
        assertFalse(sender.isLastFailed());

        sender = new InvitationsSender((double)53.3381985, (double)-6.2592576, (double) 1000);
        sender.invite(file);
        assertEquals(sender.getInvitedCount(), 32);
        assertEquals(sender.getLastErrorsCount(), 0);
        assertFalse(sender.isLastFailed());

        file = InvitationsSenderTest.class.getResource("customers_errors.json").getFile();
        sender = new InvitationsSender((double)53.3381985, (double)-6.2592576, (double) 100);
        sender.invite(file);
        assertEquals(sender.getInvitedCount(), 16);
        assertEquals(sender.getLastErrorsCount(), 7);
        assertFalse(sender.isLastFailed());

        sender = new InvitationsSender((double)53.3381985, (double)-6.2592576, (double) 100);
        TestUtils.assertFileNotFoundException({-> sender.invite("not_existing_file.json")})
    }
}
