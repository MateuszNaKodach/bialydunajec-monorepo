package org.bialydunajec.loadtest.gatling

import java.util.Locale

import com.devskiller.jfairy.Fairy
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.io.Source

class CampParticipantRegistrationGatlingTest extends Simulation {
  private val baseUrl = "https://rest.bialydunajec.org"
  //private val baseUrl = "http://localhost:3344"
  private val contentType = "application/json"
  //private val endpoint = "/rest-api/v1/camp-edition"
  private val endpoint = "/rest-api/v1/camp-registrations/in-progress/camp-participant"
  private val requestCount = 30

  val fairy = Fairy.create(Locale.forLanguageTag("PL"))

  //val req: String = ElFileBody("camper-registration.json").toString()
  val req: String = Source.fromResource("camper-registration.json").getLines.mkString


  var randomPeselRequest =
    Iterator.continually(Map("randnumber" -> req.replace("75032497551", fairy.person().getNationalIdentificationNumber)))

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .inferHtmlResources()
    .acceptHeader("*/*")
    //.authorizationHeader(basicAuthHeader)
    .contentTypeHeader(contentType)
    .userAgentHeader("curl/7.54.0")

  val scn: ScenarioBuilder = scenario("Symulacja jednoczesnego zapisu uczestników")
    .feed(randomPeselRequest)
    .exec(http("register_camper_request")
    .post(endpoint)
    .body(StringBody("""${randnumber}"""))
    //.body(ElFileBody("camper-registration.json"))
    .check(status.is(200)))


  setUp(scn.inject(atOnceUsers(requestCount))).protocols(httpProtocol)
}