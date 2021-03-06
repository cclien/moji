/*
 * Copyright 2009 Last.fm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fm.last.moji.tracker.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fm.last.moji.tracker.Tracker;
import fm.last.moji.tracker.TrackerException;

/**
 * Provides some common {@link fm.last.moji.tracker.TrackerFactory TrackerFactory} functionality.
 */
public class AbstractTrackerFactory {

  private static final Logger log = LoggerFactory.getLogger(AbstractTrackerFactory.class);

  private final Proxy proxy;

  public AbstractTrackerFactory(Proxy proxy) {
    this.proxy = proxy;
  }

  public Tracker newTracker(InetSocketAddress newAddress) throws TrackerException {
    log.debug("new {}()", TrackerImpl.class.getSimpleName());
    Tracker tracker = null;
    BufferedReader reader = null;
    Writer writer = null;
    Socket socket = null;
    try {
      socket = new Socket(proxy);
      log.debug("Connecting to: {}:", newAddress, socket.getPort());
      socket.connect(newAddress);
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      RequestHandler requestHandler = new RequestHandler(writer, reader);
      tracker = new TrackerImpl(socket, requestHandler);
    } catch (IOException e) {
      IOUtils.closeQuietly(reader);
      IOUtils.closeQuietly(writer);
      IOUtils.closeQuietly(socket);
      throw new TrackerException(e);
    }
    return tracker;
  }

  public Proxy getProxy() {
    return proxy;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AbstractTrackerFactory [proxy=");
    builder.append(proxy);
    builder.append("]");
    return builder.toString();
  }

}
