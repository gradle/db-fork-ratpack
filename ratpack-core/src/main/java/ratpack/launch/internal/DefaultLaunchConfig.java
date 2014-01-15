/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.launch.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.netty.buffer.ByteBufAllocator;
import ratpack.file.FileSystemBinding;
import ratpack.handling.Context;
import ratpack.launch.HandlerFactory;
import ratpack.launch.LaunchConfig;
import ratpack.util.internal.ThreadLocalBackedProvider;

import javax.inject.Provider;
import javax.net.ssl.SSLContext;
import java.net.InetAddress;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class DefaultLaunchConfig implements LaunchConfig, LaunchConfigInternal {

  private final FileSystemBinding baseDir;
  private final HandlerFactory handlerFactory;
  private final int port;
  private final InetAddress address;
  private final boolean reloadable;
  private final int mainThreads;
  private final ExecutorService backgroundExecutorService;
  private final ByteBufAllocator byteBufAllocator;
  private final URI publicAddress;
  private final ImmutableList<String> indexFiles;
  private final ImmutableMap<String, String> other;
  private final SSLContext sslContext;
  private final int maxContentLength;

  private final ThreadLocal<Context> contextThreadLocal = new ThreadLocal<>();
  private final Provider<Context> contextProvider = new ThreadLocalBackedProvider<>(contextThreadLocal);

  public DefaultLaunchConfig(FileSystemBinding baseDir, int port, InetAddress address, boolean reloadable, int mainThreads, ExecutorService backgroundExecutorService, ByteBufAllocator byteBufAllocator, URI publicAddress, ImmutableList<String> indexFiles, ImmutableMap<String, String> other, SSLContext sslContext, int maxContentLength, HandlerFactory handlerFactory) {
    this.baseDir = baseDir;
    this.port = port;
    this.address = address;
    this.reloadable = reloadable;
    this.mainThreads = mainThreads;
    this.backgroundExecutorService = backgroundExecutorService;
    this.byteBufAllocator = byteBufAllocator;
    this.publicAddress = publicAddress;
    this.indexFiles = indexFiles;
    this.other = other;
    this.handlerFactory = handlerFactory;
    this.sslContext = sslContext;
    this.maxContentLength = maxContentLength;
  }

  @Override
  public FileSystemBinding getBaseDir() {
    return baseDir;
  }

  @Override
  public HandlerFactory getHandlerFactory() {
    return handlerFactory;
  }

  @Override
  public int getPort() {
    return port;
  }

  @Override
  public InetAddress getAddress() {
    return address;
  }

  @Override
  public boolean isReloadable() {
    return reloadable;
  }

  @Override
  public int getMainThreads() {
    return mainThreads;
  }

  @Override
  public ExecutorService getBackgroundExecutorService() {
    return backgroundExecutorService;
  }

  @Override
  public ByteBufAllocator getBufferAllocator() {
    return byteBufAllocator;
  }

  @Override
  public URI getPublicAddress() {
    return publicAddress;
  }

  @Override
  public List<String> getIndexFiles() {
    return indexFiles;
  }

  @Override
  public SSLContext getSSLContext() {
    return sslContext;
  }

  public String getOther(String key, String defaultValue) {
    String value = other.get(key);
    return value == null ? defaultValue : value;
  }

  @Override
  public int getMaxContentLength() {
    return maxContentLength;
  }

  @Override
  public Provider<Context> getContextProvider() {
    return contextProvider;
  }

  @Override
  public ThreadLocal<Context> getContextThreadLocal() {
    return contextThreadLocal;
  }
}
