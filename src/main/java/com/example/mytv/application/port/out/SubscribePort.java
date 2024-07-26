package com.example.mytv.application.port.out;

import com.example.mytv.domain.User;
import com.example.mytv.domain.channel.Channel;
import java.util.List;

public interface SubscribePort {
    String insertSubscribeChannel(Channel channel, User user);

    List<Channel> listSubscribeChannel(String userId);
}
