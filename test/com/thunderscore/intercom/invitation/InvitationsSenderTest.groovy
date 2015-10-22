package com.thunderscore.intercom.invitation

class InvitationsSenderTest extends GroovyTestCase {
    void testSend() {

        def file = CustomerReader.class.getResource("customers.json").getFile();
        def sender = new InvitationsSender((double)53.3381985, (double)-6.2592576, (double) 100,
                1, 1);
        sender.invite(file);
        assertEquals(sender.getInvitedCount(), 16);
        assertEquals(sender.getErrorsCount(), 0);
        assertFalse(sender.isInterrupted());

        sender = new InvitationsSender((double)53.3381985, (double)-6.2592576, (double) 50,
                100, 100);
        sender.invite(file);
        assertEquals(sender.getInvitedCount(), 8);
        assertEquals(sender.getErrorsCount(), 0);
        assertFalse(sender.isInterrupted());

        sender = new InvitationsSender((double)53.3381985, (double)-6.2592576, (double) 1000,
                100, 100);
        sender.invite(file);
        assertEquals(sender.getInvitedCount(), 32);
        assertEquals(sender.getErrorsCount(), 0);
        assertFalse(sender.isInterrupted());

        file = CustomerReader.class.getResource("customers_errors.json").getFile();
        sender = new InvitationsSender((double)53.3381985, (double)-6.2592576, (double) 100,
                100, 100);
        sender.invite(file);
        assertEquals(sender.getInvitedCount(), 16);
        assertEquals(sender.getErrorsCount(), 7);
        assertFalse(sender.isInterrupted());

        sender = new InvitationsSender((double)53.3381985, (double)-6.2592576, (double) 100,
                100, 100);
        TestUtils.assertFileNotFoundException({-> sender.invite("not_existing_file.json")})
    }
}
