import org.apache.rocketmq.common.protocol.RequestCode;
import org.apache.rocketmq.common.protocol.header.SendMessageRequestHeader;
import org.apache.rocketmq.common.protocol.header.SendMessageRequestHeaderV2;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;
import org.apache.rocketmq.remoting.protocol.RocketMQSerializable;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class MQNormal {


    /**
     * 测试编码
     * 序列化的时候会
     */
    @Test
    public void testEncode() {
        SendMessageRequestHeader messageRequestHeader = new SendMessageRequestHeader();
        messageRequestHeader.setTopic("topicC");
        RemotingCommand remotingCommand = RemotingCommand.createRequestCommand(RequestCode.SEND_MESSAGE_V2, SendMessageRequestHeaderV2.createSendMessageRequestHeaderV2(messageRequestHeader));
        byte[] body = "hello,world".getBytes();
        remotingCommand.setBody(body);

        ByteBuffer buf = remotingCommand.encodeHeader();
        int length = buf.getInt();

        byte[] headerData = RemotingSerializable.encode(remotingCommand);
        int protocolType = buf.getInt();
        byte[] bytes = new byte[headerData.length];
        buf.get(bytes);
        System.out.println(new String(bytes));
        int except = 4 + headerData.length + body.length;
        Assert.assertEquals(except,length);

    }
}
