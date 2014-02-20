package com.fractaloop.scarbound.server

import java.io.File
import java.net.InetSocketAddress
import java.util.concurrent.{Executor, Executors}

import com.fractaloop.scarbound.netty.ScarboundPipelineFactory
import com.typesafe.config.ConfigFactory
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.socket.ClientSocketChannelFactory
import org.jboss.netty.channel.socket.nio.{NioClientSocketChannelFactory, NioServerSocketChannelFactory}

object ScarboundServer {
  def run(configFile: Option[File]) = {
    // Load config
    val defaults = ConfigFactory.load()
    val config = configFile match {
      case Some(file) => ConfigFactory.load(ConfigFactory.parseFile(file)).withFallback(defaults)
      case None => defaults
    }

    val executor: Executor = Executors.newCachedThreadPool
    val sb = new ServerBootstrap(
      new NioServerSocketChannelFactory(executor, executor))

    // Set up the event pipeline factory.
    val cf: ClientSocketChannelFactory = new NioClientSocketChannelFactory(executor, executor)

    sb.setPipelineFactory(
      new ScarboundPipelineFactory(
        cf,
        config.getString("scarbound.remote.host"),
        config.getInt("scarbound.remote.port")))

    // Start up the server.
    sb.bind(new InetSocketAddress(config.getInt("scarbound.server.port")))
  }
}
