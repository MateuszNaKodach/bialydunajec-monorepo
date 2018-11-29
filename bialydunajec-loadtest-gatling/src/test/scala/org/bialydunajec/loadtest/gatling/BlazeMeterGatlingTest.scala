package org.bialydunajec.loadtest.gatling

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class BlazeMeterGatlingTest extends Simulation {
  //private val baseUrl = "https://rest.bialydunajec.org"
  private val baseUrl = "https://new.bialydunajec.org"
  private val contentType = "text/html"
  //private val endpoint = "/rest-api/v1/camp-edition"
  private val endpoint = "/aktualnosci"


  private val requestCount = 100


  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .inferHtmlResources()
    .acceptHeader("*/*")
    //.authorizationHeader(basicAuthHeader)
    .contentTypeHeader(contentType)
    .userAgentHeader("curl/7.54.0")

  val headers_0 = Map("Expect" -> "100-continue")

  val scn: ScenarioBuilder = scenario("RecordedSimulation")
    .exec(http("main_page_request")
      .get(endpoint)
      //.headers(headers_0)
      //.basicAuth(authUser, authPass)
      .check(status.is(200)))


  setUp(scn.inject(atOnceUsers(requestCount))).protocols(httpProtocol)
}