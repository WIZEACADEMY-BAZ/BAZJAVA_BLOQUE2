package com.wizeline.maven.learningjavamaven.patroniterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChannelCollectionImpl implements ChannelCollection{

    private List<Channel> channelsList;

    public ChannelCollectionImpl() {
        channelsList = new ArrayList<>();
    }

    public void addChannel(Channel c) {
        this.channelsList.add(c);
    }

    public void removeChannel(Channel c) {
        this.channelsList.remove(c);
    }

    @Override
    public Iterator iterator(String type) {
        return (Iterator) new IteratorImpl(type, this.channelsList);
    }
}
