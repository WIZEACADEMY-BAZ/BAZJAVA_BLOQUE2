package com.wizeline.maven.learningjavamaven.utils.patroncomportamiento;

public interface ChannelCollection {
    public void addChannel(Channel c);

    public void removeChannel(Channel c);

    public Iterator iterator(String type);
}
