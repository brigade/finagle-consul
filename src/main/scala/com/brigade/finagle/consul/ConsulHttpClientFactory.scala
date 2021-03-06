package com.brigade.finagle.consul

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, Service}

import scala.collection.mutable

object ConsulHttpClientFactory {

  type Client = Service[Request, Response]

  private val clients: mutable.Map[String, Client] = mutable.Map()

  def getClient(hosts: String): Client = {
    synchronized {
      val client = clients.getOrElseUpdate(hosts, {
        Http.newService(hosts)
      })
      client
    }
  }
}
